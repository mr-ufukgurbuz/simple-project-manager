package com.company.department.nexusservice.controller;

import com.company.department.nexusservice.service.ComponentService;
import com.company.department.nexusservice.service.ProjectService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("components")
@Api(value = "Nexus Component API")
@Validated
public class ComponentController {

    private final ComponentService componentService;

    private final ProjectService projectService;

    public ComponentController(ComponentService componentService, ProjectService projectService) {
        this.componentService = componentService;
        this.projectService = projectService;
    }

    @PostMapping(value = "upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadAsset(@RequestPart(value = "file") @NotNull MultipartFile file,
                                      @RequestPart @NotBlank String directory,
                                      @RequestPart(required = false) String fileName,
                                      @RequestPart @NotBlank String project) {
        String assetName = directory + "/";
        assetName = StringUtils.isBlank(fileName) ? assetName + file.getOriginalFilename() : assetName + fileName;

        projectService.save(assetName, project);

        componentService.uploadComponent(file, directory, fileName);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
