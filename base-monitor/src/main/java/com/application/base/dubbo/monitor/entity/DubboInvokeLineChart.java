package com.application.base.dubbo.monitor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * LineChart
 *
 *@author admin
 */
public class DubboInvokeLineChart implements Serializable {

    private String method;

    private String chartType;

    private String title;

    private String subtitle;

    private List<String> xAxisCategories;

    private String yAxisTitle;

    private List<LineChartSeries> seriesData;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getxAxisCategories() {
        return xAxisCategories;
    }

    public void setxAxisCategories(List<String> xAxisCategories) {
        this.xAxisCategories = xAxisCategories;
    }

    public String getyAxisTitle() {
        return yAxisTitle;
    }

    public void setyAxisTitle(String yAxisTitle) {
        this.yAxisTitle = yAxisTitle;
    }

    public List<LineChartSeries> getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(List<LineChartSeries> seriesData) {
        this.seriesData = seriesData;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }
}
