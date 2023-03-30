package com.company.department.nexusservice.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "All response details about the asset.")
public class AssetResponse implements NexusResponse {

    private String id;

    @ApiModelProperty(notes = "The Path of asset")
    private String path;

    @ApiModelProperty(notes = "The DownloadUrl of asset")
    private String downloadUrl;

    @ApiModelProperty(notes = "The Uploader of asset")
    private String uploader;

    @ApiModelProperty(notes = "The FileSize of asset should be greater than 0 bytes")
    private Long fileSize;

    private AssetResponse() {
        this(null, null, null, null, null);
    }

    public AssetResponse(String id, String path, String downloadUrl, String uploader, Long fileSize) {
        super();
        this.id = id;
        this.path = path;
        this.downloadUrl = downloadUrl;
        this.uploader = uploader;
        this.fileSize = fileSize;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getUploader() {
        return uploader;
    }

    public Long getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return String.format("Asset [id=%s, path=%s, downloadUrl=%s, uploader=%s, fileSize=%s]", id, path, downloadUrl, uploader, fileSize);
    }

}
