package com.company.department.nexusservice.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "All response details about the asset list.")
public class AssetListResponse<T> implements NexusResponse {

    @ApiModelProperty(notes = "The asset list")
    private List<T> items;

    @ApiModelProperty(notes = "The continuationToken of asset list")
    private String continuationToken;

    private AssetListResponse() {
    }

    private AssetListResponse(List<T> items, String continuationToken) {
        super();
        this.items = items;
        this.continuationToken = continuationToken;
    }

    public List<T> getItems() {
        return items;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    @Override
    public String toString() {
        return String.format("AssetList [items=%s, continuationToken=%s]", items.toString(), continuationToken);
    }
}
