package com.application.base.rpc.client.anno;

/**
 * @author : 孤狼
 * @NAME: EnableNettyRpcClient
 * @DESC: EnableNettyRpcClient类设计
 **/

import com.application.base.rpc.client.NettyRpcClientRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(NettyRpcClientRegistrar.class)
public @interface EnableNettyRpcClient {
	
	
	/**
	 * 扫描的包名，如果为空，则根据启动类所在的包名扫描
	 * @return
	 */
	String[] basePackages() default {};
	
	/**
	 * 扫描的字节码类型，可根据字节码类型获取对应的包路径
	 * @return
	 */
	Class<?>[] basePackageClasses() default {};
	
}
