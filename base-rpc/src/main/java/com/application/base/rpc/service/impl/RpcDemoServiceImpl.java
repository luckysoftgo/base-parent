package com.application.base.rpc.service.impl;

import com.application.base.rpc.service.RpcDemoService;

/**
 * @author : 孤狼
 * @NAME: RpcDemoServiceImpl
 * @DESC: RpcDemoServiceImpl类设计
 **/
public class RpcDemoServiceImpl implements RpcDemoService {
	
	@Override
	public String rpcCall(String param) {
		System.out.println("prc 接收的参数是:"+param);
		return "seccess";
	}
}
