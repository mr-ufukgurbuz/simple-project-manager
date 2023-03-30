package com.company.department.nexusservice.exception.project;

import com.company.department.nexusservice.exception.base.BusinessException;
import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends BusinessException {

    public ProjectNotFoundException(String assetName) {
        super(HttpStatus.NOT_FOUND, String.format("Asset %s not found", assetName));
    }
}
