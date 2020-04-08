package com.application.base.test;

import com.application.base.rpc.NettyRpcBootApplication;
import com.application.base.rpc.service.RpcDemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author : 孤狼
 * @NAME: RpcDemoTest
 * @DESC: RpcDemoTest类设计
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= NettyRpcBootApplication.class)
public class RpcDemoTest {
	
	@Autowired
	private RpcDemoService rpcDemoService;
	
	@Test
	public void rpcCall(){
		String msg = rpcDemoService.rpcCall("use netty build rpc server!");
		System.out.println("调用服务得到的结果是:"+msg);
	}
}
