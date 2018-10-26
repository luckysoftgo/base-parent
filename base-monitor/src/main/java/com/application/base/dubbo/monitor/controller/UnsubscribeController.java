package com.application.base.dubbo.monitor.controller;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.NotifyListener;
import com.application.base.dubbo.monitor.RegistryContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * UnsubscribeController
 *
 *@author admin
 */
@Controller
@RequestMapping("/unsubscribe")
public class UnsubscribeController {

    @Autowired
    private RegistryContainer registryContainer;

    @RequestMapping(method = RequestMethod.GET)
    public String unsubscribe(@RequestParam String consumer, HttpServletRequest request) {
        URL consumerUrl = URL.valueOf(consumer);
        registryContainer.getRegistry().unsubscribe(consumerUrl, NotifyListenerAdapter.NOTIFY_LISTENER);

        String page;
        Map<String, String[]> params = request.getParameterMap();

        if (params.containsKey("service")) {
            page = "services/consumers?service=" + request.getParameter("service");
        } else if (params.containsKey("host")) {
            page = "hosts/consumers?host=" + request.getParameter("host");
        } else if (params.containsKey("application")) {
            page = "applications/consumers?application=" + request.getParameter("application");
        } else {
            page = "services/consumers?service=" + consumerUrl.getServiceInterface();
        }

        return "redirect:" + page;
    }

    private static class NotifyListenerAdapter implements NotifyListener {

        public static final NotifyListener NOTIFY_LISTENER = new NotifyListenerAdapter();

        private NotifyListenerAdapter() {
        }

        public void notify(List<URL> urls) {
        }

    }
}
