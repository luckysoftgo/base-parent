package com.application.base.rpc.client.proxy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author : 孤狼
 * @NAME: NettyRpcClientFactoryBean
 * @DESC: NettyRpcClientFactoryBean类设计
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class NettyRpcClientFactoryBean implements FactoryBean<Object> {
	
	private Class<?> type;
	
	@Override
	public Object getObject() throws Exception {
		return Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new NettyRpcInvocationHandler(type));
	}
	
	@Override
	public Class<?> getObjectType() {
		return this.type;
	}
	
}
