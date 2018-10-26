package com.application.base.dubbo.monitor.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * DubboConfig
 * @author admin
 */
@Configuration
public class DubboConfig {

    private static final String REGISTRY_ADDRESS = "dubbo.registry.address";
    private static final String APPLICATION_NAME = "dubbo.application.name";
    private static final String APPLICATION_OWNER = "dubbo.application.owner";
    private static final String PROTOCOL_PORT = "dubbo.protocol.port";

    @Autowired
    private Environment env;

    @Bean
    public static AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.application.base.dubbo.monitor");
        return annotationBean;
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(env.getProperty(APPLICATION_NAME, "dubbo-monitor"));
        applicationConfig.setOwner(env.getProperty(APPLICATION_OWNER));
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(env.getProperty(REGISTRY_ADDRESS));
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig("dubbo");
        protocolConfig.setPort(Integer.parseInt(env.getProperty(PROTOCOL_PORT, "20880")));
        return protocolConfig;
    }
}
