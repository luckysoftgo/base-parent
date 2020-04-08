package com.application.base.rpc.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author : 孤狼
 * @NAME: RpcClientHandler
 * @DESC: RpcClientHandler类设计
 **/
public class RpcClientHandler extends ChannelHandlerAdapter {
	
	/**
	 * RPC调用返回的结果
	 */
	private Object rpcResult;
	
	public Object getRpcResult() {
		return rpcResult;
	}
	
	public void setRpcResult(Object rpcResult) {
		this.rpcResult = rpcResult;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
		setRpcResult(msg);
		context.flush();
		context.close();
	}
}
