package com.company.department.nexusservice.controller;

import com.company.department.nexusservice.model.request.ProjectUpdateRequest;
import com.company.department.nexusservice.model.response.common.CommonApiResponse;
import com.company.department.nexusservice.service.ProjectService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("projects")
@Api(value = "Project API")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PutMapping
    public ResponseEntity<CommonApiResponse> update(@RequestBody @Valid ProjectUpdateRequest projectUpdateRequest) {
        return new ResponseEntity<>(projectService.update(projectUpdateRequest), HttpStatus.OK);
    }
}
