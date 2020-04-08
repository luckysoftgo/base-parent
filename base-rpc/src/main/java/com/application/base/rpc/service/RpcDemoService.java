package com.application.base.rpc.service;

import com.application.base.rpc.client.anno.NettyRpcClient;

/**
 * @author : 孤狼
 * @NAME: RpcDemoService
 * @DESC: RpcDemoService类设计
 **/
@NettyRpcClient
public interface RpcDemoService {
	
	/**
	 * 测试服务.
	 * @param param
	 * @return
	 */
	String rpcCall(String param);
	
}
