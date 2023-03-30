package com.company.department.nexusservice.service;

import com.company.department.nexusservice.common.NullAwareBeanUtilsBean;
import com.company.department.nexusservice.entity.Project;
import com.company.department.nexusservice.exception.common.BadRequestException;
import com.company.department.nexusservice.exception.common.InternalServerException;
import com.company.department.nexusservice.exception.project.ProjectNotFoundException;
import com.company.department.nexusservice.model.request.ProjectSaveRequest;
import com.company.department.nexusservice.model.request.ProjectUpdateRequest;
import com.company.department.nexusservice.model.response.common.CommonApiResponse;
import com.company.department.nexusservice.repository.ProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class ProjectService {

    private static Logger logger = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void save(String assetName, String projectJson) {
        ProjectSaveRequest projectSaveRequest;
        try {
            projectSaveRequest = new ObjectMapper().readValue(projectJson, ProjectSaveRequest.class);

            if (StringUtils.isBlank(projectSaveRequest.getName())) {
                throw new BadRequestException("project_name");
            }

            if (StringUtils.isBlank(projectSaveRequest.getDescription())) {
                throw new BadRequestException("project_description");
            }

            if (StringUtils.isBlank(projectSaveRequest.getOwnerId())) {
                throw new BadRequestException("project_ownerId");
            }

            Project project = new Project(projectSaveRequest.getName(), assetName, projectSaveRequest.getDescription(), projectSaveRequest.getOwnerId());
            projectRepository.save(project);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new InternalServerException();
        }
    }

    public CommonApiResponse<Project> update(ProjectUpdateRequest projectUpdateRequest) {
        Project projectToBeUpdated = projectRepository.findByAssetName(projectUpdateRequest.getAssetName()).orElseThrow(() ->
                new ProjectNotFoundException(projectUpdateRequest.getAssetName()));

        BeanUtilsBean notNull = new NullAwareBeanUtilsBean();

        try {
            notNull.copyProperties(projectToBeUpdated, projectUpdateRequest);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return CommonApiResponse.response(projectRepository.save(projectToBeUpdated));
    }
}
