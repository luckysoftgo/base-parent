package com.application.base.dubbo.monitor.controller;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.remoting.exchange.ExchangeChannel;
import com.alibaba.dubbo.remoting.exchange.ExchangeServer;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;
import com.application.base.dubbo.monitor.entity.DubboServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ServersController
 *
 *@author admin
 */
@Controller
@RequestMapping("/servers")
public class ServersController {

    @RequestMapping(method = RequestMethod.GET)
    public String servers(Model model) {
        List<DubboServer> rows = new ArrayList<DubboServer>();
        Collection<ExchangeServer> servers = DubboProtocol.getDubboProtocol().getServers();
        if (servers != null && servers.size() > 0) {
            DubboServer dubboServer;
            for (ExchangeServer server : servers) {
                dubboServer = new DubboServer();
                dubboServer.setAddress(server.getUrl().getAddress());
                dubboServer.setPort(server.getUrl().getPort());
                dubboServer.setHostname(NetUtils.getHostName(dubboServer.getAddress()));
                dubboServer.setClientCount(server.getExchangeChannels().size());

                rows.add(dubboServer);
            }
        }
        model.addAttribute("rows", rows);
        return "server/servers";
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String clients(@RequestParam int port, Model model) {
        Collection<ExchangeServer> servers = DubboProtocol.getDubboProtocol().getServers();

        ExchangeServer server = null;
        String serverAddress = "";
        if (servers != null && servers.size() > 0) {
            for (ExchangeServer s : servers) {
                int sp = s.getUrl().getPort();
                if (port == 0 && server == null || port == sp) {
                    server = s;
                    serverAddress = NetUtils.getHostName(s.getUrl().getAddress()) + "/" + s.getUrl().getAddress();
                }
            }
        }

        List<String> rows = new ArrayList<String>();

        if (server != null) {
            Collection<ExchangeChannel> channels = server.getExchangeChannels();
            for (ExchangeChannel c : channels) {
                String address = NetUtils.toAddressString(c.getRemoteAddress());
                rows.add(NetUtils.getHostName(address) + "/" + address);
            }
        }

        model.addAttribute("port", port);
        model.addAttribute("server", serverAddress);
        model.addAttribute("rows", rows);
        return "server/clients";
    }
}
