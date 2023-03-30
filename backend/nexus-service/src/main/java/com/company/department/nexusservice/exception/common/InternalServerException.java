package com.company.department.nexusservice.exception.common;

import com.company.department.nexusservice.exception.base.BusinessException;
import org.springframework.http.HttpStatus;

public class InternalServerException extends BusinessException {

    public InternalServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server exception");
    }
}
