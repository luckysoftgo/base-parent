package com.application.base.config.zookeeper.curator.session;

import com.application.base.config.zookeeper.ZooKeeperValidUtil;
import com.application.base.config.zookeeper.api.ZkApiSession;
import com.application.base.config.zookeeper.exception.ZooKeeperException;
import com.application.base.utils.common.BaseStringUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: 孤狼
 * @desc: zookeeper session的实现.
 */
public class ZooKeeperSimpleSession implements ZkApiSession{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 被 装配到 Spring 工厂
	 */
	private CuratorFramework client;
	
	public CuratorFramework getClient() {
		if (null==client){
			logger.error("[zookeeper错误:{}]","获得zookeeper实例对象为空");
			throw new ZooKeeperException("获得zookeeper实例对象为空");
		}
		//开启服务.
		client.start();
		return client;
	}
	
	public void setClient(CuratorFramework client) {
		this.client = client;
	}
	
	@Override
	public CuratorFramework getCuratorClient() throws ZooKeeperException {
		return getClient();
	}
	
	@Override
	public boolean createNode(String nodeName) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		try {
			String value = client.create().forPath(nodeName);
			if (BaseStringUtil.isNotEmpty(value)){
				return true;
			}
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
		return false;
	}
	
	@Override
	public boolean createNode(CreateMode mode, String nodeName) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		String value = "";
		try {
			if (mode==null){
				value = client.create().forPath(nodeName);
			}else{
				value = client.create().withMode(mode).forPath(nodeName);
			}
			if (BaseStringUtil.isNotEmpty(value)){
				return true;
			}
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
		return false;
	}
	
	@Override
	public boolean createNodeAndVal(CreateMode mode, String nodeName, String data) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		ZooKeeperValidUtil.zkValidated(logger,data);
		String result = "";
		try {
			if (mode==null){
				result = client.create().forPath(nodeName,data.getBytes(ENDCODING));
			}else{
				result = client.create().withMode(mode).forPath(nodeName,data.getBytes(ENDCODING));
			}
			if (BaseStringUtil.isNotEmpty(result)){
				return true;
			}
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
		return false;
	}
	
	@Override
	public boolean updateNodeData(String nodeName,String value) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		ZooKeeperValidUtil.zkValidated(logger,value);
		try {
			Stat stat = client.setData().forPath(nodeName,value.getBytes(ENDCODING));
			if (stat!=null){
				return true;
			}
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
		return false;
	}
	
	@Override
	public boolean updateNodeData(String nodeName,String value, Integer version) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		ZooKeeperValidUtil.zkValidated(logger,value);
		try {
			Stat stat = null;
			if (version==null){
				stat = client.setData().forPath(nodeName,value.getBytes(ENDCODING));
			}else{
				stat = client.setData().withVersion(version).forPath(nodeName,value.getBytes(ENDCODING));
			}
			if (stat!=null){
				return true;
			}
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
		return false;
	}
	
	@Override
	public boolean deleteNode(String nodeName,boolean children) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		try {
			if (children){
				client.delete().forPath(nodeName);
			}else{
				client.delete().deletingChildrenIfNeeded().forPath(nodeName);
			}
			return true;
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
	}
	
	@Override
	public boolean deleteNodeByPros(String nodeName, Integer version, boolean children, boolean guaranteed) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		try {
			if (version==null && children==false && guaranteed==false){
				client.delete().forPath(nodeName);
			}else if (version==null && children && guaranteed==false){
				client.delete().deletingChildrenIfNeeded().forPath(nodeName);
			}else if (version==null && children && guaranteed){
				client.delete().guaranteed().deletingChildrenIfNeeded().forPath(nodeName);
			}else if (version==null && children==false && guaranteed){
				client.delete().guaranteed().forPath(nodeName);
			}else if (version.intValue()>0 && children==false && guaranteed==false){
				client.delete().withVersion(version).forPath(nodeName);
			}else if (version.intValue()>0 && children && guaranteed==false){
				client.delete().deletingChildrenIfNeeded().withVersion(version).forPath(nodeName);
			}else if (version.intValue()>0 && children && guaranteed){
				client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(version).forPath(nodeName);
			}else if (version.intValue()>0 && children==false && guaranteed){
				client.delete().guaranteed().withVersion(version).forPath(nodeName);
			}
			return true;
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
	}
	
	@Override
	public String getNodeData(String nodeName) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		try {
			byte[] values = client.getData().forPath(nodeName);
			return new String(values,ENDCODING);
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
	}
	
	@Override
	public String getNodeData(String nodeName, Stat stat) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		try {
			byte[] values = client.getData().storingStatIn(stat).forPath(nodeName);
			return new String(values,ENDCODING);
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
	}

	@Override
	public boolean exists(String nodeName) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		try {
			Stat stat = client.checkExists().forPath(nodeName);
			if(stat.getCtime()!=0){
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
	}
	
	@Override
	public List<String> getNodeChildren(String nodeName) throws ZooKeeperException {
		CuratorFramework client = getClient();
		ZooKeeperValidUtil.zkValidated(logger, nodeName);
		try {
			List<String> nodes = client.getChildren().forPath(nodeName);
			return nodes;
		} catch (Exception e) {
			logger.error("[zookeeper错误:{}]", e);
			throw new ZooKeeperException(e);
		}
	}
	
}
