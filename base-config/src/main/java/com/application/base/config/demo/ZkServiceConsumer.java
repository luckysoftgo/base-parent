package com.application.base.config.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: 孤狼
 * @desc: ZK 服务消费者
 */
public class ZkServiceConsumer implements Watcher {
	
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
			zk=new ZooKeeper("118.24.157.96:2181",5000000,new ZkServiceConsumer());
			System.out.println(zk.getState());
			//等待zk连接成功的通知.
			connectedSemaphore.await();
			List<String> childrens = zk.getChildren(rootPath,true);
			System.out.println("服务提供者是:"+childrens);
			for (String children : childrens) {
				System.out.println(children+"--"+new String(zk.getData(rootPath+"/"+children, false, new Stat())));
			}
			
	        Thread.sleep(Integer.MAX_VALUE);
		} catch (IOException | InterruptedException | KeeperException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(WatchedEvent event) {
		if (event.getType()==EventType.None && event.getPath()==null) {
			connectedSemaphore.countDown();
		}else if (event.getType()==EventType.NodeDataChanged) {
			try {
				List<String> childrens = zk.getChildren(rootPath,true);
				System.out.println("服务提供者是:"+childrens);
				for (String children : childrens) {
					System.out.println(children+"--"+new String(zk.getData(rootPath+"/"+children, false, new Stat())));
				}
			} catch (InterruptedException | KeeperException e) {
				e.printStackTrace();
			}
		}
	}

}
