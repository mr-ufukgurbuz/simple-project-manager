package com.company.department.nexusservice.exception.common;

import com.company.department.nexusservice.exception.base.BusinessException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(String id)
    {
        super(HttpStatus.FORBIDDEN, String.format("%s 403 forbidden", id));
    }
}
