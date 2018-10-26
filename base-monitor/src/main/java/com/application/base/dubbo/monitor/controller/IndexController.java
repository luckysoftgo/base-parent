package com.application.base.dubbo.monitor.controller;

import com.application.base.dubbo.monitor.DubboMonitorService;
import com.application.base.dubbo.monitor.entity.DubboInvoke;
import com.application.base.dubbo.monitor.entity.DubboInvokeLineChart;
import com.application.base.dubbo.monitor.entity.LineChartSeries;
import com.application.base.dubbo.monitor.support.CommonResponse;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Home Controller
 *
 *@author admin
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private DubboMonitorService dubboMonitorService;

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "loadTopData")
    public CommonResponse loadTopDate(@ModelAttribute DubboInvoke dubboInvoke) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        List<DubboInvokeLineChart> dubboInvokeLineChartList = new ArrayList<DubboInvokeLineChart>();
        DubboInvokeLineChart successDubboInvokeLineChart = new DubboInvokeLineChart();
        List<String> sxAxisCategories = Lists.newArrayList();
        LineChartSeries slineChartSeries = new LineChartSeries();
        List<double[]> sdataList = Lists.newArrayList();
        double[] data;
        Map dubboInvokeMap = dubboMonitorService.countDubboInvokeTopTen(dubboInvoke);
        List<DubboInvoke> success = (List<DubboInvoke>) dubboInvokeMap.get("success");
        for (DubboInvoke di : success) {
            sxAxisCategories.add(di.getMethod());
            data = new double[]{di.getSuccess()};
            sdataList.add(data);
        }
        slineChartSeries.setData(sdataList);
        slineChartSeries.setName("provider");

        successDubboInvokeLineChart.setxAxisCategories(sxAxisCategories);
        successDubboInvokeLineChart.setSeriesData(Arrays.asList(slineChartSeries));
        successDubboInvokeLineChart.setChartType("SUCCESS");
        successDubboInvokeLineChart.setTitle("The Top 20 of Invoke Success");
        successDubboInvokeLineChart.setyAxisTitle("t");
        dubboInvokeLineChartList.add(successDubboInvokeLineChart);

        DubboInvokeLineChart failureDubboInvokeLineChart = new DubboInvokeLineChart();
        List<String> fxAxisCategories = Lists.newArrayList();
        LineChartSeries flineChartSeries = new LineChartSeries();
        List<double[]> fdataList = Lists.newArrayList();
        List<DubboInvoke> failure = (List<DubboInvoke>) dubboInvokeMap.get("failure");
        for (DubboInvoke di : failure) {
            fxAxisCategories.add(di.getMethod());
            data = new double[]{di.getFailure()};
            fdataList.add(data);
        }
        flineChartSeries.setData(fdataList);
        flineChartSeries.setName("provider");

        failureDubboInvokeLineChart.setxAxisCategories(fxAxisCategories);
        failureDubboInvokeLineChart.setSeriesData(Arrays.asList(flineChartSeries));
        failureDubboInvokeLineChart.setChartType("FAILURE");
        failureDubboInvokeLineChart.setTitle("The Top 20 of Invoke Failure");
        failureDubboInvokeLineChart.setyAxisTitle("t");
        dubboInvokeLineChartList.add(failureDubboInvokeLineChart);

        commonResponse.setData(dubboInvokeLineChartList);
        return commonResponse;
    }
}
