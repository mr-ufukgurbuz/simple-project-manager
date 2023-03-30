package com.company.department.nexusservice.model.request;

import java.util.List;

public class DeleteFolderRequest {

    private String action;
    private String method;
    private List<String> data;
    private String type;
    private Integer tid;

    public DeleteFolderRequest(List<String> data) {
        this.action = "coreui_Component";
        this.method = "deleteFolder";
        this.type = "rpc";
        this.tid = 9;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public String getMethod() {
        return method;
    }

    public List<String> getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public Integer getTid() {
        return tid;
    }
}
