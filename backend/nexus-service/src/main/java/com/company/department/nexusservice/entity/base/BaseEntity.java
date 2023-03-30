package com.company.department.nexusservice.entity.base;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class BaseEntity {

    @Id
    private String id;

    private Date createdDate = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
