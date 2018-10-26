package com.application.base.cache.codis.architecture.cache;

import com.application.base.cache.codis.architecture.enumer.CacheEnvironment;
import com.application.base.cache.codis.architecture.enumer.ListPosition;
import com.application.base.utils.common.BaseStringUtil;
import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * codis 的缓存 client
 * @author 孤狼
 */
public class CacheClient implements AbstractCacheClient {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * jedis 实例.
	 */
	private static JedisResourcePool jedisPool = null;
	/**
	 * config 实例.
	 */
	private CacheConfig config;

	public CacheClient(CacheConfig config) {
		this.config = config;
		//初始化 jedis 池
		initPools();
	}

	protected JedisResourcePool initPools() {
		if (jedisPool == null) {
			synchronized (CacheClient.class) {
				if (jedisPool == null) {
					jedisPool = RoundRobinJedisPool.create().poolConfig(this.config.cacheConfig2JedisPoolConfig())
							.curatorClient(this.config.getZkAddressAndPort(), this.config.getZkSessionTimeOutMs())
							.zkProxyDir(this.config.getZkProxyDir()).build();
				}
			}
		}
		return jedisPool;
	}

	@Override
	public Boolean set(String key, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return Boolean.valueOf("OK".equals(jedis.set(key, value)));
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String get(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.get(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long setnx(String key, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.setnx(key, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String getSet(String key, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.getSet(key, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Boolean exists(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.exists(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Boolean expire(String key, int seconds) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return Boolean.valueOf(jedis.expire(key, seconds).longValue() == 1L);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.expireAt(key, unixTime);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long ttl(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.ttl(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long append(String key, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.append(key, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long strlen(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.strlen(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long decr(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.decr(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long decrBy(String key, long integer) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.decrBy(key, integer);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long del(String[] keys) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.del(keys);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.getrange(key, startOffset, endOffset);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.setrange(key, offset, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long hset(String key, String field, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hset(key, field, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String hget(String key, String field) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hget(key, field);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hgetAll(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> hkeys(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hkeys(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long hlen(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hlen(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public List<String> hmget(String key, String[] fields) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hmget(key, fields);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hmset(key, hash);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public List<String> mget(String[] keys) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.mget(keys);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Boolean mset(String[] keysvalues) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return Boolean.valueOf("OK".equals(jedis.mset(keysvalues)));
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	private String keyWapper(String key) {
		if (!BaseStringUtil.isEmpty(key)) {
			key = CacheEnvironment.env(this.config.getEnvrionment()).encode(key);
			return key;
		}
		return null;
	}

	@Override
	public Long incr(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.incr(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long incrBy(String key, long integer) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.incrBy(key, integer);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long hdel(String key, String[] fields) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hdel(key, fields);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Boolean hexists(String key, String field) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hexists(key, field);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hincrBy(key, field, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hsetnx(key, field, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public List<String> hvals(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.hvals(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String lindex(String key, long index) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.lindex(key, index);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long linsert(String key, ListPosition where, String pivot, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.linsert(key, where.warp(), pivot, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long llen(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.llen(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String lpop(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.lpop(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long lpush(String key, String[] strings) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.lpush(key, strings);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long lpushx(String key, String[] string) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.lpushx(key, string);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.lrange(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long lrem(String key, long count, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.lrem(key, count, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String ltrim(String key, long start, long end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.ltrim(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String lset(String key, long index, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.lset(key, index, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String rpop(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.rpop(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long rpush(String key, String[] strings) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.rpush(key, strings);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long rpushx(String key, String string) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.rpushx(key, new String[] { string });
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long sadd(String key, String[] members) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.sadd(key, members);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long scard(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.scard(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> smembers(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.smembers(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String setExpire(String key, int seconds, String value) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.setex(key, seconds, value);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String spop(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.spop(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Boolean sismember(String key, String member) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.sismember(key, member);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public List<String> sort(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.sort(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.sort(key, sortingParameters);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long sort(String key, SortingParams sortingParameters, String dstkey) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.sort(key, sortingParameters, dstkey);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long sort(String key, String dstkey) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.sort(key, dstkey);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String srandmember(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.srandmember(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public List<String> srandmember(String key, int count) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.srandmember(key, count);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long srem(String key, String[] members) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.srem(key, members);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	@Deprecated
	public String substr(String key, int start, int end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.substr(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public String type(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.type(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zadd(String key, double score, String member) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zadd(key, score, member);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zadd(key, scoreMembers);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zcard(String key) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zcard(key);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zcount(String key, double min, double max) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zcount(key, min, max);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zcount(String key, String min, String max) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zcount(key, min, max);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zincrby(key, score, member);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrange(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrangeByScore(key, min, max);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrangeByScore(key, min, max);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrangeWithScores(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zrank(String key, String member) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrank(key, member);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zrem(String key, String[] members) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrem(key, members);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zremrangeByRank(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zremrangeByScore(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zremrangeByScore(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrange(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrangeByScore(key, max, min);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrangeByScore(key, max, min);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrangeWithScores(key, start, end);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Long zrevrank(String key, String member) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zrevrank(key, member);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

	@Override
	public Double zscore(String key, String member) {
		try {
			Jedis jedis = jedisPool.getResource();
			Throwable localThrowable3 = null;
			try {
				return jedis.zscore(key, member);
			}
			catch (Throwable localThrowable4) {
				localThrowable3 = localThrowable4;
				throw localThrowable4;
			}
			finally {
				if (jedis != null) {
					if (localThrowable3 != null) {
						try {
							jedis.close();
						} catch (Throwable localThrowable2) {
							localThrowable3.addSuppressed(localThrowable2);
						}
					} else {
						jedis.close();
					}
				}
			}
		}
		catch (Exception e) {
			if (this.config.getDebug()) {
				this.logger.info(e.getMessage());
			}
			throw e;
		}
	}

}
