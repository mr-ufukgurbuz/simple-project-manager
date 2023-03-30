package com.company.department.nexusservice.exception.common;

import com.company.department.nexusservice.exception.base.BusinessException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BusinessException {

    public BadRequestException(String parameterName) {
        super(HttpStatus.BAD_REQUEST, parameterName);
    }
}
