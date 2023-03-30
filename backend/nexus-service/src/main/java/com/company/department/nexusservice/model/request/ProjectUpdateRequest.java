package com.company.department.nexusservice.model.request;

import com.company.department.nexusservice.entity.Status;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ProjectUpdateRequest implements Request {

    private Status status;

    private Date uploadDate;

    @NotNull
    private String assetId;

    @NotNull
    private String assetName;

    public ProjectUpdateRequest() {
    }

    public ProjectUpdateRequest(Status status, Date uploadDate, String assetId, String assetName) {
        this.status = status;
        this.uploadDate = uploadDate;
        this.assetId = assetId;
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
}
