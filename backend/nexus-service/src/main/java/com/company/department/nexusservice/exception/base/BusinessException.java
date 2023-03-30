package com.company.department.nexusservice.exception.base;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public BusinessException(HttpStatus status, String message)
    {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus()
    {
        return status;
    }

    public String getMessage()
    {
        return message;
    }
}
