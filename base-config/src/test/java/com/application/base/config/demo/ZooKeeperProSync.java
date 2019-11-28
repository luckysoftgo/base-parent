package com.application.base.config.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: 孤狼
 * @desc: 测试 ZK 的入口程序.
 */
public class ZooKeeperProSync implements Watcher {
	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk=null;
	private static Stat stat=new Stat();
	
	/**
	* @Title: main 
	* Description:主要程序入口.
	* @param args    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void main(String[] args) {
		//配置 zk 配置存放路径.
		String path="/groupMembers/test";
		try {
			//注册一个默认的监听器.
			zk=new ZooKeeper("118.24.157.96:2181",5000000,new ZooKeeperProSync());
			//等待zk连接成功的通知.
			connectedSemaphore.await();
			//获取 path 目录节点的配置数据，并注册默认的监听器.
			String result = new String(zk.getData(path, true, stat));
			//几点信息.
			System.out.println("注册节点信息:result = "+result);
			
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
		//节点数据变化监听
		else if (event.getType()==EventType.NodeDataChanged) {
			try {
				String result=new String(zk.getData(event.getPath(), true, stat));
				if (result.equalsIgnoreCase("/test")) {
					System.out.println("配置已经修改,修改的数据是:"+result);
				}else {
					System.out.println("配置已经修改,修改的数据是:"+result);
				}
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}