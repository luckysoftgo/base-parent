
package com.application.base.dubbo.monitor.config;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * MonitorConfig
 *
 *@author admin
 */
@Configuration
@ComponentScan(basePackages = {"com.application.base.dubbo.monitor"}, includeFilters = {@ComponentScan.Filter(value = Service.class)})
@Import({WebConfig.class, DubboConfig.class, MyBatisConfig.class})
@PropertySource("classpath:/properties/application.properties")
public class MonitorConfig {

}
