package com.application.base.dubbo.monitor.config;

import jetbrick.template.web.springmvc.JetTemplateViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *@author admin
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public JetTemplateViewResolver viewResolver() {
        JetTemplateViewResolver jetTemplateViewResolver = new JetTemplateViewResolver();
        jetTemplateViewResolver.setOrder(1);
        jetTemplateViewResolver.setContentType("text/html; charset=utf-8");
        jetTemplateViewResolver.setSuffix(".html");
        jetTemplateViewResolver.setConfigLocation("/WEB-INF/jetbrick-template.properties");
        return jetTemplateViewResolver;
    }

}
