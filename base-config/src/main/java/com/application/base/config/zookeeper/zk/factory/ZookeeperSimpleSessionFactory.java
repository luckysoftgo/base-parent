package com.application.base.config.zookeeper.zk.factory;

import com.application.base.config.zookeeper.api.ZkApiSession;
import com.application.base.config.zookeeper.exception.ZookeeperException;
import com.application.base.config.zookeeper.factory.ZookeeperSessionFactory;
import com.application.base.config.zookeeper.zk.session.ZookeeperSimpleSession;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: 孤狼
 * @desc: 创建 zookeeper 的工厂实例.
 */
public class ZookeeperSimpleSessionFactory implements ZookeeperSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private CuratorFramework client;
	
	public ZookeeperSimpleSessionFactory() {
	}
	
	public ZookeeperSimpleSessionFactory(CuratorFramework client) {
		this.client = client;
	}
	
	@Override
	public ZkApiSession getZookeeperSession() throws ZookeeperException {
		ZkApiSession session = null;
		try {
			session = (ZkApiSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{ZkApiSession.class}, new ZookeeperSimpleSessionProxy(new ZookeeperSimpleSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	public CuratorFramework getClient() {
		return client;
	}
	
	public void setClient(CuratorFramework client) {
		this.client = client;
	}
	
	private class ZookeeperSimpleSessionProxy implements InvocationHandler {
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private ZookeeperSimpleSession zookeeperSimpleSession;
		
		public ZookeeperSimpleSessionProxy(ZookeeperSimpleSession zookeeperSimpleSession) {
			this.zookeeperSimpleSession = zookeeperSimpleSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized CuratorFramework getZKClient() {
			logger.debug("获取zookeeper链接");
			CuratorFramework zkClient = null;
			try {
				zkClient = ZookeeperSimpleSessionFactory.this.getClient();
			}
			catch (Exception e) {
				logger.error("获取zookeeper链接错误,{}", e);
				throw new ZookeeperException(e);
			}
			if (null==zkClient){
				logger.error("[zookeeper错误:{}]","获得zookeeper实例对象为空");
				throw new ZookeeperException("获得zookeeper实例对象为空");
			}
			return zkClient;
		}
		
		/**
		 * Redis方法的代理实现
		 *
		 * @param proxy
		 * @param method
		 * @param args
		 * @return
		 * @throws Throwable
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			CuratorFramework zkClient = null;
			boolean success = true;
			try {
				if (client == null) {
					logger.error("获取 zookeeper 连接池失败");
					throw new ZookeeperException("获取zookeeper连接池失败");
				}
				zkClient = getZKClient();
				zookeeperSimpleSession.setClient(zkClient);
				return method.invoke(zookeeperSimpleSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (zkClient != null) {
					zkClient.close();
				}
				logger.error("[zookeeper执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && zkClient != null) {
					logger.debug("zookeeper 链接关闭");
					zkClient.close();
				}
			}
		}
	}
	
}
