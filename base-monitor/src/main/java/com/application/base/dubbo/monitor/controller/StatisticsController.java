package com.application.base.dubbo.monitor.controller;

import com.application.base.dubbo.monitor.DubboMonitorService;
import com.application.base.dubbo.monitor.entity.DubboInvoke;
import com.application.base.dubbo.monitor.entity.DubboStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Statistics Controller
 *@author admin
 */
@Controller
@RequestMapping("/services/statistics")
public class StatisticsController {

    @Autowired
    private DubboMonitorService dubboMonitorService;

    @RequestMapping()
    public String index(@ModelAttribute DubboInvoke dubboInvoke, Model model) {
        // Set default Search Date
        if (dubboInvoke.getInvokeDate() == null && dubboInvoke.getInvokeDateFrom() == null && dubboInvoke.getInvokeDateTo() == null) {
            dubboInvoke.setInvokeDate(new Date());
        }
        //获取Service方法
        List<String> methods = dubboMonitorService.getMethodsByService(dubboInvoke);
        List<DubboInvoke> dubboInvokes;
        List<DubboStatistics> dubboStatisticses = new ArrayList<DubboStatistics>();
        DubboStatistics dubboStatistics;
        for (String method : methods) {
            dubboStatistics = new DubboStatistics();
            dubboStatistics.setMethod(method);
            dubboInvoke.setMethod(method);
            dubboInvoke.setType("provider");
            dubboInvokes = dubboMonitorService.countDubboInvokeInfo(dubboInvoke);
            for (DubboInvoke di : dubboInvokes) {
                if (di == null) {
                    continue;
                }
                dubboStatistics.setProviderSuccess(di.getSuccess());
                dubboStatistics.setProviderFailure(di.getFailure());
                dubboStatistics.setProviderAvgElapsed(di.getSuccess() != 0 ? Double.valueOf(String.format("%.4f", di.getElapsed() / di.getSuccess())) : 0);
                dubboStatistics.setProviderMaxElapsed(di.getMaxElapsed());
                dubboStatistics.setProviderMaxConcurrent(di.getMaxConcurrent());
            }
            dubboInvoke.setType("consumer");
            dubboInvokes = dubboMonitorService.countDubboInvokeInfo(dubboInvoke);
            for (DubboInvoke di : dubboInvokes) {
                if (di == null) {
                    continue;
                }
                dubboStatistics.setConsumerSuccess(di.getSuccess());
                dubboStatistics.setConsumerFailure(di.getFailure());
                dubboStatistics.setConsumerAvgElapsed(di.getSuccess() != 0 ? Double.valueOf(String.format("%.4f", di.getElapsed() / di.getSuccess())) : 0);
                dubboStatistics.setConsumerMaxElapsed(di.getMaxElapsed());
                dubboStatistics.setConsumerMaxConcurrent(di.getMaxConcurrent());
            }
            dubboStatisticses.add(dubboStatistics);
        }
        model.addAttribute("rows", dubboStatisticses);
        model.addAttribute("service", dubboInvoke.getService());
        return "service/statistics";
    }

}
