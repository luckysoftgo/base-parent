package com.application.base.dubbo.monitor.controller;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.status.Status;
import com.alibaba.dubbo.common.status.StatusChecker;
import com.application.base.dubbo.monitor.entity.DubboStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * StatusController
 *
 *@author admin
 */
@Controller
@RequestMapping("/status")
public class StatusController {

    @RequestMapping(method = RequestMethod.GET)
    public String status(Model model) {
        List<DubboStatus> rows = new ArrayList<DubboStatus>();
        Set<String> names = ExtensionLoader.getExtensionLoader(StatusChecker.class).getSupportedExtensions();
        DubboStatus dubboStatus;
        for (String name : names) {
            StatusChecker checker = ExtensionLoader.getExtensionLoader(StatusChecker.class).getExtension(name);
            Status status = checker.check();
            if (status != null && !Status.Level.UNKNOWN.equals(status.getLevel())) {
                dubboStatus = new DubboStatus();
                dubboStatus.setName(name);
                dubboStatus.setStatus(status);
                dubboStatus.setDescription(status.getMessage());
                rows.add(dubboStatus);
            }
        }
        model.addAttribute("rows", rows);
        return "status";
    }

}
