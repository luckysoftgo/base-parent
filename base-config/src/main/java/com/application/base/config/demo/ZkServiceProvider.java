package com.application.base.config.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: 孤狼
 * @desc: ZK 服务提供者
 */
public class ZkServiceProvider implements Watcher {
	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk=null;
	private static String rootPath="/groupMembers";
	
	/**
	* @Title: main 
	* @Description:获得实例. 
	* @param args    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void main(String[] args) {
		try {
			//注册一个默认的监听器.
			zk=new ZooKeeper("118.24.157.96:2181",5000000,new ZkServiceProvider());
			System.out.println(zk.getState());
			//等待zk连接成功的通知.
			connectedSemaphore.await();
			
			String path =zk.create(rootPath+"/test1","100.50.1.10:8080".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			
	        System.out.println("Success create znode: " + path);
			
	        Thread.sleep(Integer.MAX_VALUE);
			
		} catch (IOException | InterruptedException | KeeperException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(WatchedEvent event) {
		if (event.getType()==EventType.None && event.getPath()==null) {
			connectedSemaphore.countDown();
		}
	}

}
