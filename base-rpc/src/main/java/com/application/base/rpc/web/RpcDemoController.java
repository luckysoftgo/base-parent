package com.application.base.rpc.web;

import com.application.base.rpc.service.RpcDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 孤狼
 * @NAME: RpcDemoController
 * @DESC: RpcDemoController类设计
 **/
@RestController
public class RpcDemoController {
	
	@Autowired
	private RpcDemoService rpcDemoService;
	
	@RequestMapping(value = "/rpcCall",method = RequestMethod.GET)
	public String rpcCall(){
		String msg = rpcDemoService.rpcCall("use netty build rpc server!");
		System.out.println(msg);
		return "OK";
	}
}
