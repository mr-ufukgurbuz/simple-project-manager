package com.company.department.nexusservice.model.response.common;

import java.util.Date;

public class CommonApiResponse<T>
{
    private T data;
    private Date date;

    public static <T> CommonApiResponse<T> response(T data)
    {
        CommonApiResponse<T> commonApiResponse = new CommonApiResponse<>();
        commonApiResponse.setData(data);
        commonApiResponse.setDate(new Date());

        return commonApiResponse;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
