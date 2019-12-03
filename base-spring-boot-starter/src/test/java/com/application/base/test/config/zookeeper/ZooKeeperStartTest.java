package com.application.base.test.config.zookeeper;

import com.application.base.config.zookeeper.ZooKeeperConfigProperties;
import com.application.base.config.zookeeper.curator.factory.ZooKeeperSimpleSessionFactory;
import com.application.base.pool.GenericPool;
import com.application.base.test.BasicStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author : 孤狼
 * @NAME: ZooKeeperStartTest
 * @DESC:
 **/
public class ZooKeeperStartTest extends BasicStartTest {
	
	@Resource
	private ZooKeeperConfigProperties configProperties;
	
	@Autowired
	private ZooKeeperSimpleSessionFactory zooKeeperFactory;
	
	@Test
	public void test() {
		GenericPool pool = configProperties.getPool();
		System.out.println(pool.toString());
	}
	
	@Test
	public void testzk() {
		for (int i = 0; i < 500 ; i++) {
			try {
				String node = "/super/testNode"+i;
				boolean result = zooKeeperFactory.getZooKeeperSession().createNode(node);
				if (result){
					System.out.println("节点"+node+"存在!");
					result = zooKeeperFactory.getZooKeeperSession().deleteNode(node,false);
					if (result){
						System.out.println(node+"删除成功");
					}
				}else{
					System.out.println("不存在!");
				}
			}catch (Exception e){
				System.out.println("获取配置信息异常!");
			}
		}
	}
}
