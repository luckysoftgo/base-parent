package com.application.base.dubbo.monitor.entity;

import java.io.Serializable;

/**
 * DubboServer
 *
 *@author admin
 */
public class DubboServer implements Serializable {

    private String address;
    private int port;
    private String hostname;
    private int clientCount;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getClientCount() {
        return clientCount;
    }

    public void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }
}
