package com.application.base.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author : 孤狼
 * @NAME: KylinTaskApplication
 * @DESC:
 **/
@SpringBootApplication
@ImportResource(locations = {"classpath*:kylin.xml","classpath*:elastic.xml"})
public class KylinTaskApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KylinTaskApplication.class, args);
	}

}
