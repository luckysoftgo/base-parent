package com.application.base.config.zookeeper;

import org.slf4j.Logger;
import com.application.base.config.zookeeper.exception.ZooKeeperException;
import java.util.Objects;

/**
 * @desc zookeeper 验证工具类
 * @author 孤狼
 */
public class ZooKeeperValidUtil {
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @throws ZooKeeperException
	 */
	public static void zkValidated(Logger logger, String key) throws ZooKeeperException {
		if(key == null || "".equals(key)){
			logger.info("[zookeeper操作:存入key:{}为空！]",key);
			throw new ZooKeeperException("存入键为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param keys
	 * @throws ZooKeeperException
	 */
	public static void zkValidated(Logger logger, String... keys) throws ZooKeeperException {
		if(keys == null){
			logger.info("[zookeeper操作:存入key:{}为空！]",keys.toString());
			throw new ZooKeeperException("存入键为空!");
		}
		if (keys.length==0){
			logger.info("[zookeeper操作:存入key:{}为空！]",keys.toString());
			throw new ZooKeeperException("存入键为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @param value
	 * @throws ZooKeeperException
	 */
	public static void zkValidated(Logger logger, String key, Object value) throws ZooKeeperException {
		if(key == null || "".equals(key)){
			logger.info("[zookeeper操作:存入value:{},key:{}为空！]",Objects.toString(value,""),key);
			throw new ZooKeeperException("存入键为空!");
		}
		if (value == null || "".equals(value)) {
			logger.info("[zookeeper操作:存入key:{},value:{}为空！]",key,Objects.toString(value,""));
			throw new ZooKeeperException("存入值为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @param value
	 * @throws ZooKeeperException
	 */
	public static void zkValidated(Logger logger, String key, String... value) throws ZooKeeperException {
		if(key == null || "".equals(key)){
			logger.info("[zookeeper操作:存入value:{},key:{}为空！]",Objects.toString(value,""),key);
			throw new ZooKeeperException("存入键为空!");
		}
		if (value == null) {
			logger.info("[zookeeper操作:存入key:{},value:{}为空！]",key,Objects.toString(value,""));
			throw new ZooKeeperException("存入值为空!");
		}
		if (value.length == 0) {
			logger.info("[zookeeper操作:存入key:{},value:{}为空！]",key,value);
			throw new ZooKeeperException("存入值为空!");
		}
	}
}
