package com.application.base.dubbo.monitor.entity;

import com.alibaba.dubbo.common.status.Status;

import java.io.Serializable;

/**
 *@author admin
 */
public class DubboStatus implements Serializable {

    private String name;
    private Status status;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
