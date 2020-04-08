package com.application.base.rpc;


import com.application.base.rpc.client.anno.EnableNettyRpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
/**
 * @author : 孤狼
 * @NAME: NettyRpcBootApplication1
 * @DESC: NettyRpcBootApplication1类设计
 **/
@SpringBootApplication
@EnableNettyRpcClient(basePackages = {"com.application.base.rpc"})
public class NettyRpcBootApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
	
	/**
	 * 启动服务.
	 * @param args
	 */
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(NettyRpcBootApplication.class);
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(9999);
    }
}