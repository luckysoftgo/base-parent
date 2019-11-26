package com.application.base.demo.web;

import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务Handler示例（Bean模式）
 *
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 */
@RestController
public class TestController {

	@RequestMapping("/info")
	public ReturnT<String> info() throws Exception {
		System.out.println("测试系统是否可用!");
		return new ReturnT(ReturnT.SUCCESS_CODE,"系统正常!");
	}
	
}