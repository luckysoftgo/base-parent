package com.application.base.cache.redis.jedis;

import com.application.base.cache.redis.exception.RedisException;
import org.slf4j.Logger;

import java.util.Objects;

/**
 * @desc jedis 工具类
 * @author 孤狼
 */
public class JedisValidUtil {
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @throws RedisException
	 */
	public static void redisValidated(Logger logger, String key) throws RedisException {
		if(key == null || "".equals(key)){
			logger.info("[redis操作:存入key:{}为空！]",key);
			throw new RedisException("存入键为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param keys
	 * @throws RedisException
	 */
	public static void redisValidated(Logger logger, String... keys) throws RedisException {
		if(keys == null){
			logger.info("[redis操作:存入key:{}为空！]",keys.toString());
			throw new RedisException("存入键为空!");
		}
		if (keys.length==0){
			logger.info("[codis操作:存入key:{}为空！]",keys.toString());
			throw new RedisException("存入键为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public static void redisValidated(Logger logger, String key, Object value) throws RedisException {
		if(key == null || "".equals(key)){
			logger.info("[redis操作:存入value:{},key:{}为空！]",Objects.toString(value,""),key);
			throw new RedisException("存入键为空!");
		}
		if (value == null || "".equals(value)) {
			logger.info("[redis操作:存入key:{},value:{}为空！]",key,Objects.toString(value,""));
			throw new RedisException("存入值为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public static void redisValidated(Logger logger, String key, String... value) throws RedisException {
		if(key == null || "".equals(key)){
			logger.info("[redis操作:存入value:{},key:{}为空！]",Objects.toString(value,""),key);
			throw new RedisException("存入键为空!");
		}
		if (value == null) {
			logger.info("[redis操作:存入key:{},value:{}为空！]",key,Objects.toString(value,""));
			throw new RedisException("存入值为空!");
		}
		if (value.length == 0) {
			logger.info("[redis操作:存入key:{},value:{}为空！]",key,value);
			throw new RedisException("存入值为空!");
		}
	}
	
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @throws RedisException
	 */
	public static void codisValidated(Logger logger, String key) throws RedisException {
		if(key == null || "".equals(key)){
			logger.info("[codis操作:存入key:{}为空！]",key);
			throw new RedisException("存入键为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param keys
	 * @throws RedisException
	 */
	public static void codisValidated(Logger logger, String... keys) throws RedisException {
		if(keys == null ){
			logger.info("[codis操作:存入key:{}为空！]",keys.toString());
			throw new RedisException("存入键为空!");
		}
		if (keys.length==0){
			logger.info("[codis操作:存入key:{}为空！]",keys.toString());
			throw new RedisException("存入键为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public static void codisValidated(Logger logger, String key, Object value) throws RedisException {
		if(key == null || "".equals(key)){
			logger.info("[codis操作:存入value:{},key:{}为空！]",Objects.toString(value,""),key);
			throw new RedisException("存入键为空!");
		}
		if (value == null || "".equals(value)) {
			logger.info("[codis操作:存入key:{},value:{}为空！]",key,Objects.toString(value,""));
			throw new RedisException("存入值为空!");
		}
	}
	
	/**
	 * 校验输入.
	 * @param logger
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public static void codisValidated(Logger logger, String key, String... value) throws RedisException {
		if(key == null || "".equals(key)){
			logger.info("[codis操作:存入value:{},key:{}为空！]",Objects.toString(value,""),key);
			throw new RedisException("存入键为空!");
		}
		if (value == null) {
			logger.info("[codis操作:存入key:{},value:{}为空！]",key,Objects.toString(value,""));
			throw new RedisException("存入值为空!");
		}
		if (value.length == 0) {
			logger.info("[codis操作:存入key:{},value:{}为空！]",key,value);
			throw new RedisException("存入值为空!");
		}
	}
}
