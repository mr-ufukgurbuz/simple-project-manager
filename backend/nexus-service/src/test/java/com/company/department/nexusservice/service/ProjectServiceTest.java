package com.company.department.nexusservice.service;

import com.company.department.nexusservice.entity.Project;
import com.company.department.nexusservice.entity.Status;
import com.company.department.nexusservice.exception.common.BadRequestException;
import com.company.department.nexusservice.exception.project.ProjectNotFoundException;
import com.company.department.nexusservice.model.request.ProjectUpdateRequest;
import com.company.department.nexusservice.model.response.common.CommonApiResponse;
import com.company.department.nexusservice.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void save_shouldCreateProject() {
        String assetName = "assetName";
        String projectJson = "{\"name\": \"helloworld\", \"description\": \"my desc\", \"ownerId\": \"id\"}";

        Project project = createProject();
        project.setId(null);

        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        projectService.save(assetName, projectJson);
    }

    @Test
    void save_nameIsNull_shouldThrowBadRequestException() {
        String expectedParamName = "project_name";
        String assetName = "assetName";
        String projectJson = "{\"description\": \"my desc\", \"ownerId\": \"id\"}";

        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            projectService.save(assetName, projectJson);
        });

        Assertions.assertNotNull(badRequestException);
        Assertions.assertEquals(expectedParamName, badRequestException.getMessage());
    }

    @Test
    void save_descriptionIsNull_shouldThrowBadRequestException() {
        String expectedParamName = "project_description";
        String assetName = "assetName";
        String projectJson = "{\"name\": \"helloworld\", \"ownerId\": \"id\"}";

        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            projectService.save(assetName, projectJson);
        });

        Assertions.assertNotNull(badRequestException);
        Assertions.assertEquals(expectedParamName, badRequestException.getMessage());
    }

    @Test
    void save_ownerIdIsNull_shouldThrowBadRequestException() {
        String expectedParamName = "project_ownerId";
        String assetName = "assetName";
        String projectJson = "{\"name\": \"helloworld\", \"description\": \"my desc\"}";

        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> {
            projectService.save(assetName, projectJson);
        });

        Assertions.assertNotNull(badRequestException);
        Assertions.assertEquals(expectedParamName, badRequestException.getMessage());
    }

    @Test
    void update__shouldUpdateStatusToSuccess() {
        Project project = createProject();

        ProjectUpdateRequest projectUpdateRequest = createProjectUpdateRequest();

        Mockito.when(projectRepository.findByAssetName(Mockito.anyString())).thenReturn(Optional.of(project));

        project.setStatus(Status.SUCCESS);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        CommonApiResponse<Project> update = projectService.update(projectUpdateRequest);

        Assertions.assertEquals(Status.SUCCESS, update.getData().getStatus());
    }

    @Test
    void update_thereIsNoSpecifiedProject_shouldThrowProjectNotFoundException() {
        ProjectUpdateRequest projectUpdateRequest = createProjectUpdateRequest();
        String expectedMessage = String.format("Asset %s not found", projectUpdateRequest.getAssetName());


        ProjectNotFoundException projectNotFoundException = Assertions.assertThrows(ProjectNotFoundException.class, () -> {
            projectService.update(projectUpdateRequest);
        });

        Assertions.assertNotNull(projectNotFoundException);
        Assertions.assertEquals(expectedMessage, projectNotFoundException.getMessage());
    }

    private Project createProject() {
        String assetName = "assetName";

        Project project = new Project("name", assetName, "description", "ownerId");
        project.setId("id");
        return project;
    }

    private ProjectUpdateRequest createProjectUpdateRequest() {
        ProjectUpdateRequest projectUpdateRequest = new ProjectUpdateRequest();
        projectUpdateRequest.setStatus(Status.SUCCESS);
        projectUpdateRequest.setAssetName("assetName");
        projectUpdateRequest.setAssetId("assetId");
        projectUpdateRequest.setUploadDate(new Date());
        return projectUpdateRequest;
    }

}
