package com.application.base.dubbo.monitor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Line Chart Series
 *
 *@author admin
 */
public class LineChartSeries implements Serializable {

    private String name;

    private List<double[]> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<double[]> getData() {
        return data;
    }

    public void setData(List<double[]> data) {
        this.data = data;
    }

}
