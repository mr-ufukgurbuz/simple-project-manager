package com.company.department.nexusservice.entity;

import com.company.department.nexusservice.entity.base.BaseEntity;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@RedisHash("Project")
public class Project extends BaseEntity {

    private String name;
    private String description;
    private String assetId;
    @Indexed
    private String assetName;
    private Status status = Status.PENDING;
    private Date uploadDate;
    private String ownerId;

    public Project() {
    }

    public Project(String name, String assetName, String description, String ownerId) {
        this.name = name;
        this.assetName = assetName;
        this.description = description;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
