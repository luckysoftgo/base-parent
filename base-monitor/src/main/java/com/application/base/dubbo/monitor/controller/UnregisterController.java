package com.application.base.dubbo.monitor.controller;

import com.alibaba.dubbo.common.URL;
import com.application.base.dubbo.monitor.RegistryContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * UnregisterController
 *@author admin
 */
@Controller
@RequestMapping("/unregister")
public class UnregisterController {

    @Autowired
    private RegistryContainer registryContainer;

    @RequestMapping(method = RequestMethod.GET)
    public String unregister(@RequestParam String provider, HttpServletRequest request) {
        URL providerUrl = URL.valueOf(provider);
        registryContainer.getRegistry().unregister(providerUrl);

        String page;
        Map<String, String[]> params = request.getParameterMap();

        if (params.containsKey("service")) {
            page = "services/providers?service=" + request.getParameter("service");
        } else if (params.containsKey("host")) {
            page = "hosts/providers?host=" + request.getParameter("host");
        } else if (params.containsKey("application")) {
            page = "applications/providers?application=" + request.getParameter("application");
        } else {
            page = "services/providers?service=" + providerUrl.getServiceInterface();
        }

        return "redirect:" + page;
    }
}
