package com.application.base.dubbo.monitor.entity;

import java.io.Serializable;

/**
 * Dubbo Application Entity
 *
 *@author admin
 */
public class DubboApplication implements Serializable {

    private String name;

    private String owner;

    private String organization;

    private int providerCount;

    private int consumerCount;

    private int efferentCount;

    private int afferentCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getProviderCount() {
        return providerCount;
    }

    public void setProviderCount(int providerCount) {
        this.providerCount = providerCount;
    }

    public int getConsumerCount() {
        return consumerCount;
    }

    public void setConsumerCount(int consumerCount) {
        this.consumerCount = consumerCount;
    }

    public int getEfferentCount() {
        return efferentCount;
    }

    public void setEfferentCount(int efferentCount) {
        this.efferentCount = efferentCount;
    }

    public int getAfferentCount() {
        return afferentCount;
    }

    public void setAfferentCount(int afferentCount) {
        this.afferentCount = afferentCount;
    }
}
