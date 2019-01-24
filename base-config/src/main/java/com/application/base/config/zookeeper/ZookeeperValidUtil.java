package com.application.base.config.zookeeper;

import com.application.base.config.zookeeper.exception.ZookeeperException;
import org.slf4j.Logger;

import java.util.Objects;

/**
 * @desc zookeeper 验证工具类
 * @author 孤狼
 */
public class ZookeeperValidUtil {
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @throws ZookeeperException
	 */
	public static void zkValidated(Logger logger, String key) throws ZookeeperException {
		if(key == null || "".equals(key)){
			logger.info("[zookeeper操作:存入key:{}为空！]",key);
			throw new ZookeeperException("存入键为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param keys
	 * @throws ZookeeperException
	 */
	public static void zkValidated(Logger logger, String... keys) throws ZookeeperException {
		if(keys == null){
			logger.info("[zookeeper操作:存入key:{}为空！]",keys.toString());
			throw new ZookeeperException("存入键为空!");
		}
		if (keys.length==0){
			logger.info("[zookeeper操作:存入key:{}为空！]",keys.toString());
			throw new ZookeeperException("存入键为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @param value
	 * @throws ZookeeperException
	 */
	public static void zkValidated(Logger logger, String key, Object value) throws ZookeeperException {
		if(key == null || "".equals(key)){
			logger.info("[zookeeper操作:存入value:{},key:{}为空！]",Objects.toString(value,""),key);
			throw new ZookeeperException("存入键为空!");
		}
		if (value == null || "".equals(value)) {
			logger.info("[zookeeper操作:存入key:{},value:{}为空！]",key,Objects.toString(value,""));
			throw new ZookeeperException("存入值为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @param value
	 * @throws ZookeeperException
	 */
	public static void zkValidated(Logger logger, String key, String... value) throws ZookeeperException {
		if(key == null || "".equals(key)){
			logger.info("[zookeeper操作:存入value:{},key:{}为空！]",Objects.toString(value,""),key);
			throw new ZookeeperException("存入键为空!");
		}
		if (value == null) {
			logger.info("[zookeeper操作:存入key:{},value:{}为空！]",key,Objects.toString(value,""));
			throw new ZookeeperException("存入值为空!");
		}
		if (value.length == 0) {
			logger.info("[zookeeper操作:存入key:{},value:{}为空！]",key,value);
			throw new ZookeeperException("存入值为空!");
		}
	}
}
