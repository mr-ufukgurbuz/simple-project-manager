package com.company.department.nexusservice.model.response.common;

import com.company.department.nexusservice.model.response.NexusResponse;

import java.util.Date;
import java.util.List;

public class CommonListResponse<T> implements NexusResponse
{
    private List<T> data;
    private String continuationToken;
    private Date date;

    public static <T> CommonListResponse<T> response(List<T> data, String continuationToken)
    {
        CommonListResponse<T> commonListResponse = new CommonListResponse<>();
        commonListResponse.setData(data);
        commonListResponse.setDate(new Date());
        commonListResponse.setContinuationToken(continuationToken);

        return commonListResponse;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }
}
