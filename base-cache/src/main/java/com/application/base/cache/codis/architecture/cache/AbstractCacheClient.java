package com.application.base.cache.codis.architecture.cache;

import com.application.base.cache.codis.architecture.enumer.ListPosition;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @desc codis 缓存顶级接口
 * @author 孤狼
 */
public interface AbstractCacheClient {
	
	/**
	 * set value
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Boolean set(String key, String value);
	
	/**
	 * get value
	 * @param key
	 * @return
	 */
	public abstract String get(String key);
	
	/**
	 * set nx
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Long setnx(String key, String value);
	
	/**
	 * get set集合
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract String getSet(String key, String value);
	
	/**
	 *是否存在
	 * @param key
	 * @return
	 */
	public abstract Boolean exists(String key);
	
	/**
	 * 是否失效.
	 * @param key
	 * @param time
	 * @return
	 */
	public abstract Boolean expire(String key, int time);
	
	/**
	 * 失效
	 * @param key
	 * @param time
	 * @return
	 */
	public abstract Long expireAt(String key, long time);
	
	/**
	 * 追加value
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Long append(String key, String value);
	
	/**
	 * 减数
	 * @param key
	 * @return
	 */
	public abstract Long decr(String key);
	
	/**
	 * 从key减数
	 * @param key
	 * @param time
	 * @return
	 */
	public abstract Long decrBy(String key, long time);
	
	/**
	 * 加数
	 * @param key
	 * @return
	 */
	public abstract Long incr(String key);
	
	/**
	 * 从key加数
	 * @param key
	 * @param time
	 * @return
	 */
	public abstract Long incrBy(String key, long time);
	
	/**
	 * 删除
	 * @param arrays
	 * @return
	 */
	public abstract Long del(String[] arrays);
	
	/**
	 * 获得范围
	 * @param key
	 * @param param1
	 * @param param2
	 * @return
	 */
	public abstract String getrange(String key, long param1, long param2);
	
	/**
	 * 回复字符串值关联字段
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract String hget(String key, String value);
	
	/**
	 * 从存储在键散列删除指定的字段
	 * @param key
	 * @param array
	 * @return
	 */
	public abstract Long hdel(String key, String[] array);
	
	/**
	 * 从存储在键散列判断是否存在
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Boolean hexists(String key, String value);
	
	/**
	 *用于获取存储在键的散列的所有字段和值
	 * @param key
	 * @return
	 */
	public abstract Map<String, String> hgetAll(String key);
	
	/**
	 *增加存储在字段中存储由增量键哈希的数量
	 * @param key
	 * @param value
	 * @param time
	 * @return
	 */
	public abstract Long hincrBy(String key, String value, long time);
	
	/**
	 *用来获取所有字段名保存在键的哈希值
	 * @param key
	 * @return
	 */
	public abstract Set<String> hkeys(String key);
	
	/**
	 * 用来获取所有字段名保存在键的长度
	 * @param key
	 * @return
	 */
	public abstract Long hlen(String key);
	
	/**
	 *用于获取与存储在键散列指定的字段相关联的值
	 * @param key
	 * @param array
	 * @return
	 */
	public abstract List<String> hmget(String key, String[] array);
	
	/**
	 * 用于设置指定字段各自的值，在存储于键的散列
	 * @param key
	 * @param paramMap
	 * @return
	 */
	public abstract String hmset(String key, Map<String, String> paramMap);
	
	/**
	 *用于在存储的关键值的散列设置字段。
	 * @param key
	 * @param value
	 * @param key3
	 * @return
	 */
	public abstract Long hset(String key, String value, String key3);
	
	/**
	 * 用于在存储的关键值的散列设置字段
	 * @param key
	 * @param value
	 * @param key3
	 * @return
	 */
	public abstract Long hsetnx(String key, String value, String key3);
	
	/**
	 *用于获取在存储于 key的散列的所有值
	 * @param key
	 * @return
	 */
	public abstract List<String> hvals(String key);
	
	/**
	 * 令用于获取在存储于列表的key索引的元素
	 * @param key
	 * @param time
	 * @return
	 */
	public abstract String lindex(String key, long time);
	
	/**
	 * 插入值在存储在key之前或参考值支点后
	 * @param key
	 * @param position
	 * @param value
	 * @param key3
	 * @return
	 */
	public abstract Long linsert(String key, ListPosition position, String value, String key3);
	
	/**
	 * 返回数组，指定键的值列表
	 * @param arrays
	 * @return
	 */
	public abstract List<String> mget(String[] arrays);
	
	/**
	 * 一次多个键设置它们的值
	 * @param arrays
	 * @return
	 */
	public abstract Boolean mset(String[] arrays);
	
	/**
	 *返回存储在key列表的长度
	 * @param key
	 * @return
	 */
	public abstract Long llen(String key);
	
	/**
	 * 命令删除，并返回保存列表在key的第一个元素
	 * @param key
	 * @return
	 */
	public abstract String lpop(String key);
	
	/**
	 *在保存在key列表的头部插入所有指定的值
	 * @param key
	 * @param arrays
	 * @return
	 */
	public abstract Long lpush(String key, String[] arrays);
	
	/**
	 * 插入存储在列表的头部的键值，仅当键已经存在，并持有(它是)列表
	 * @param key
	 * @param arrays
	 * @return
	 */
	public abstract Long lpushx(String key, String[] arrays);
	
	/**
	 *返回存储在key列表的特定元素
	 * @param key
	 * @param param1
	 * @param param2
	 * @return
	 */
	public abstract List<String> lrange(String key, long param1, long param2);
	
	/**
	 * lrem
	 * @param key
	 * @param time
	 * @param value
	 * @return
	 */
	public abstract Long lrem(String key, long time, String value);
	
	/**
	 * 返回字符串str去掉前导空格字符
	 * @param key
	 * @param paramLong1
	 * @param paramLong2
	 * @return
	 */
	public abstract String ltrim(String key, long paramLong1, long paramLong2);
	
	/**
	 *将在索引值的列表元素
	 * @param key
	 * @param paramLong
	 * @param value
	 * @return
	 */
	public abstract String lset(String key, long paramLong, String value);
	
	/**
	 * 并返回列表保存在key的最后一个元素
	 * @param key
	 * @return
	 */
	public abstract String rpop(String key);
	
	/**
	 * 插入所有指定的值，在存储在列表的key尾部
	 * @param key
	 * @param arrays
	 * @return
	 */
	public abstract Long rpush(String key, String[] arrays);
	
	/**
	 * 在插入存储在关键列表的尾部，仅当键已经存在，并持有列表值。
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Long rpushx(String key, String value);
	
	/**
	 * 用于添加成员设置保存在key。
	 * @param key
	 * @param arrays
	 * @return
	 */
	public abstract Long sadd(String key, String[] arrays);
	
	/**
	 * 用于获取存储在集合中的元素的数量
	 * @param key
	 * @return
	 */
	public abstract Long scard(String key);
	
	/**
	 * 所有的元素存在于集存储在指定的键
	 * @param key
	 * @return
	 */
	public abstract Set<String> smembers(String key);
	
	/**
	 * 失效时间
	 * @param key
	 * @param paramInt
	 * @param value
	 * @return
	 */
	public abstract String setExpire(String key, int paramInt, String value);
	
	/**
	 * spop
	 * @param key
	 * @return
	 */
	public abstract String spop(String key);
	
	/**
	 *setrange
	 * @param key
	 * @param paramLong
	 * @param value
	 * @return
	 */
	public abstract Long setrange(String key, long paramLong, String value);
	
	/**
	 *sismember
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Boolean sismember(String key, String value);
	
	/**
	 *strlen
	 * @param key
	 * @return
	 */
	public abstract Long strlen(String key);
	
	/**
	 *ttl
	 * @param key
	 * @return
	 */
	public abstract Long ttl(String key);
	/**
	 *sort
	 * @param key
	 * @return
	 */
	public abstract List<String> sort(String key);
	
	/**
	 * sort
	 * @param key
	 * @param params
	 * @return
	 */
	public abstract List<String> sort(String key, SortingParams params);
	
	/**
	 * sort
	 * @param key
	 * @param params
	 * @param value
	 * @return
	 */
	public abstract Long sort(String key, SortingParams params, String value);
	
	/**
	 * sort
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Long sort(String key, String value);
	
	/**
	 *srandmember
	 * @param key
	 * @return
	 */
	public abstract String srandmember(String key);
	
	/**
	 * srandmember
	 * @param key
	 * @param paramInt
	 * @return
	 */
	public abstract List<String> srandmember(String key, int paramInt);
	/**
	 * srem
	 * @param key
	 * @param paramArrayOfString
	 * @return
	 */
	public abstract Long srem(String key, String[] paramArrayOfString);
	
	/**
	 *substr
	 * @param key
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract String substr(String key, int paramInt1, int paramInt2);
	
	/**
	 *type
	 * @param key
	 * @return
	 */
	public abstract String type(String key);
	
	/**
	 *zadd
	 * @param key
	 * @param paramDouble
	 * @param value
	 * @return
	 */
	public abstract Long zadd(String key, double paramDouble, String value);
	
	/**
	 *zadd
	 * @param key
	 * @param paramMap
	 * @return
	 */
	public abstract Long zadd(String key, Map<String, Double> paramMap);
	/**
	 *zcard
	 * @param key
	 * @return
	 */
	public abstract Long zcard(String key);
	
	/**
	 * zcount
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @return
	 */
	public abstract Long zcount(String key, double paramDouble1, double paramDouble2);
	
	/**
	 *zcount
	 * @param key
	 * @param value
	 * @param key3
	 * @return
	 */
	public abstract Long zcount(String key, String value, String key3);
	
	/**
	 * zincrby
	 * @param key
	 * @param paramDouble
	 * @param value
	 * @return
	 */
	public abstract Double zincrby(String key, double paramDouble, String value);
	
	/**
	 *zrange
	 * @param key
	 * @param paramLong1
	 * @param paramLong2
	 * @return
	 */
	public abstract Set<String> zrange(String key, long paramLong1, long paramLong2);
	
	/**
	 * zrangeByScore
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @return
	 */
	public abstract Set<String> zrangeByScore(String key, double paramDouble1, double paramDouble2);
	
	/**
	 * zrangeByScore
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract Set<String> zrangeByScore(String key, double paramDouble1, double paramDouble2, int paramInt1,
											  int paramInt2);
	
	/**
	 * zrangeByScore
	 * @param key
	 * @param value
	 * @param key3
	 * @return
	 */
	public abstract Set<String> zrangeByScore(String key, String value, String key3);
	
	/**
	 * zrangeByScore
	 * @param key
	 * @param value
	 * @param key3
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract Set<String> zrangeByScore(String key, String value, String key3, int paramInt1,
											  int paramInt2);
	
	/**
	 * zrangeByScoreWithScores
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @return
	 */
	public abstract Set<Tuple> zrangeByScoreWithScores(String key, double paramDouble1, double paramDouble2);
	
	/**
	 * zrangeByScoreWithScores
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract Set<Tuple> zrangeByScoreWithScores(String key, double paramDouble1, double paramDouble2,
													   int paramInt1, int paramInt2);
	
	/**
	 * zrangeByScoreWithScores
	 * @param key
	 * @param value
	 * @param key3
	 * @return
	 */
	public abstract Set<Tuple> zrangeByScoreWithScores(String key, String value, String key3);
	
	/**
	 * zrangeByScoreWithScores
	 * @param key
	 * @param value
	 * @param key3
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract Set<Tuple> zrangeByScoreWithScores(String key, String value, String key3,
													   int paramInt1, int paramInt2);
	
	/**
	 * zrangeWithScores
	 * @param key
	 * @param paramLong1
	 * @param paramLong2
	 * @return
	 */
	public abstract Set<Tuple> zrangeWithScores(String key, long paramLong1, long paramLong2);
	
	/**
	 * zrank
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Long zrank(String key, String value);
	
	/**
	 * zrem
	 * @param key
	 * @param paramArrayOfString
	 * @return
	 */
	public abstract Long zrem(String key, String[] paramArrayOfString);
	
	/**
	 * zremrangeByRank
	 * @param key
	 * @param paramLong1
	 * @param paramLong2
	 * @return
	 */
	public abstract Long zremrangeByRank(String key, long paramLong1, long paramLong2);
	
	/**
	 * zremrangeByScore
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @return
	 */
	public abstract Long zremrangeByScore(String key, double paramDouble1, double paramDouble2);
	
	/**
	 * zremrangeByScore
	 * @param key
	 * @param value
	 * @param key3
	 * @return
	 */
	public abstract Long zremrangeByScore(String key, String value, String key3);
	
	/**
	 * zrevrange
	 * @param key
	 * @param paramLong1
	 * @param paramLong2
	 * @return
	 */
	public abstract Set<String> zrevrange(String key, long paramLong1, long paramLong2);
	
	/**
	 * zrevrangeByScore
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @return
	 */
	public abstract Set<String> zrevrangeByScore(String key, double paramDouble1, double paramDouble2);
	
	/**
	 * zrevrangeByScore
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract Set<String> zrevrangeByScore(String key, double paramDouble1, double paramDouble2,
												 int paramInt1, int paramInt2);
	
	/**
	 * zrevrangeByScore
	 * @param key
	 * @param value
	 * @param key3
	 * @return
	 */
	public abstract Set<String> zrevrangeByScore(String key, String value, String key3);
	
	/**
	 * zrevrangeByScore
	 * @param key
	 * @param value
	 * @param key3
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract Set<String> zrevrangeByScore(String key, String value, String key3, int paramInt1,
												 int paramInt2);
	
	/**
	 * zrevrangeByScoreWithScores
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @return
	 */
	public abstract Set<Tuple> zrevrangeByScoreWithScores(String key, double paramDouble1, double paramDouble2);
	
	/**
	 * zrevrangeByScoreWithScores
	 * @param key
	 * @param paramDouble1
	 * @param paramDouble2
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract Set<Tuple> zrevrangeByScoreWithScores(String key, double paramDouble1, double paramDouble2,
														  int paramInt1, int paramInt2);
	
	/**
	 * zrevrangeByScoreWithScores
	 * @param key
	 * @param value
	 * @param key3
	 * @return
	 */
	public abstract Set<Tuple> zrevrangeByScoreWithScores(String key, String value, String key3);
	
	/**
	 * zrevrangeByScoreWithScores
	 * @param key
	 * @param value
	 * @param key3
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract Set<Tuple> zrevrangeByScoreWithScores(String key, String value, String key3,
														  int paramInt1, int paramInt2);
	
	/**
	 * zrevrangeWithScores
	 * @param key
	 * @param paramLong1
	 * @param paramLong2
	 * @return
	 */
	public abstract Set<Tuple> zrevrangeWithScores(String key, long paramLong1, long paramLong2);
	
	/**
	 * zrevrank
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Long zrevrank(String key, String value);
	
	/**
	 * zscore
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Double zscore(String key, String value);
}
