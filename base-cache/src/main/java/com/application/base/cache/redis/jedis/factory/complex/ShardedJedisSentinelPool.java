package com.application.base.cache.redis.jedis.factory.complex;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * @NAME: ShardedJedisSentinelPool
 * @DESC:  如果生产环境的redis是致力于高可用,我们一般是使用的哨兵模式,但是为避免单点风险,我们会创建多个redis服务使其形成一组redis集群（非集群模式）,
 * 即是使哨兵模式,又是多节点的,那么你的链接池是否就应该是ShardedJedisSentinelPool了,这种JedisSentinelPool与ShardedJedisPool  构建的混合体
 *
 * @author : 孤狼
 * https://www.cnblogs.com/lianshan/p/11640777.html
 */
public class ShardedJedisSentinelPool extends Pool<ShardedJedis> {
	
	protected static final Logger logger = LoggerFactory.getLogger(ShardedJedisSentinelPool.class.getName());

	public static final int MAX_RETRY_SENTINEL = 30;
	/**
	 * 连接池配置.
	 */
	protected GenericObjectPoolConfig poolConfig;
	/**
	 * 失效时间.
	 */
	protected int timeout = Protocol.DEFAULT_TIMEOUT;
	/**
	 * 哨兵策略.
	 */
	private int sentinelRetry = 0;
	/**
	 * 登录密码.
	 */
	protected String password;
	/**
	 * 默认数据库.
	 */
	protected int database = Protocol.DEFAULT_DATABASE;
	/**
	 * 监听器.
	 */
	protected Set<MasterListener> masterListeners = new HashSet<MasterListener>();
	/**
	 * 当前ip端口的映射.
	 */
	private volatile List<HostAndPort> currentHostMasters;
	
	/**
	 * 构造函数
	 * @param masters
	 * @param sentinels
	 */
	public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels) {
		this(masters, sentinels, new GenericObjectPoolConfig(),Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
	}
	
	/**
	 * 构造函数
	 * @param masters
	 * @param sentinels
	 * @param password
	 */
	public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, String password) {
		this(masters, sentinels, new GenericObjectPoolConfig(),Protocol.DEFAULT_TIMEOUT, password);
	}
	
	/**
	 * 构造函数
	 * @param poolConfig
	 * @param masters
	 * @param sentinels
	 */
	public ShardedJedisSentinelPool(final GenericObjectPoolConfig poolConfig, List<String> masters, Set<String> sentinels) {
		this(masters, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, null,Protocol.DEFAULT_DATABASE);
	}
	
	/**
	 * 构造函数
	 * @param masters
	 * @param sentinels
	 * @param poolConfig
	 * @param timeout
	 * @param password
	 */
	public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, int timeout, final String password) {
		this(masters, sentinels, poolConfig, timeout, password,Protocol.DEFAULT_DATABASE);
	}
	
	/**
	 * 构造函数
	 * @param masters
	 * @param sentinels
	 * @param poolConfig
	 * @param timeout
	 */
	public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, final int timeout) {
		this(masters, sentinels, poolConfig, timeout, null,Protocol.DEFAULT_DATABASE);
	}
	
	/**
	 * 构造函数
	 * @param masters
	 * @param sentinels
	 * @param poolConfig
	 * @param password
	 */
	public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, final String password) {
		this(masters, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT,password);
	}
	
	/**
	 * 构造函数
	 * @param masters
	 * @param sentinels
	 * @param poolConfig
	 * @param timeout
	 * @param password
	 * @param database
	 */
	public ShardedJedisSentinelPool(List<String> masters, Set<String> sentinels, final GenericObjectPoolConfig poolConfig, int timeout, final String password, final int database) {
		this.poolConfig = poolConfig;
		this.timeout = timeout;
		this.password = password;
		this.database = database;
		List<HostAndPort> masterList = initSentinels(sentinels, masters);
		initPool(masterList);
	}
	
	@Override
	public void destroy() {
		for (MasterListener m : masterListeners) {
			m.shutdown();
		}
		super.destroy();
	}
	
	public List<HostAndPort> getCurrentHostMaster() {
		return currentHostMasters;
	}
	
	private void initPool(List<HostAndPort> masters) {
		if (!masterEquals(currentHostMasters, masters)) {
			StringBuffer sb = new StringBuffer();
			for (HostAndPort master : masters) {
				sb.append(master.toString());
				sb.append(" ");
			}
			logger.info("Created ShardedJedisSentinelPool to master at [" + sb.toString() + "]");
			List<JedisShardInfo> shardMasters = makeShardInfoList(masters);
			initPool(poolConfig, new ShardedJedisFactory(shardMasters, Hashing.MURMUR_HASH, null));
			currentHostMasters = masters;
		}
	}
	
	private boolean masterEquals(List<HostAndPort> currentShardMasters, List<HostAndPort> shardMasters) {
		if (currentShardMasters != null && shardMasters != null) {
			if (currentShardMasters.size() == shardMasters.size()) {
				for (int i = 0; i < currentShardMasters.size(); i++) {
					if (!currentShardMasters.get(i).equals(shardMasters.get(i))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	private List<JedisShardInfo> makeShardInfoList(List<HostAndPort> masters) {
		List<JedisShardInfo> shardMasters = new ArrayList<JedisShardInfo>();
		for (HostAndPort master : masters) {
			JedisShardInfo jedisShardInfo = new JedisShardInfo(master.getHost(), master.getPort(), timeout);
			if (StringUtils.isNotBlank(password)){
				jedisShardInfo.setPassword(password);
			}
			shardMasters.add(jedisShardInfo);
		}
		return shardMasters;
	}
	
	/**
	 * 初始化哨兵
	 * @param sentinels
	 * @param masters
	 * @return
	 */
	private List<HostAndPort> initSentinels(Set<String> sentinels, final List<String> masters) {
		Map<String, HostAndPort> masterMap = new HashMap<String, HostAndPort>();
		List<HostAndPort> shardMasters = new ArrayList<HostAndPort>();
		logger.info("Trying to find all master:" + masters + "from available Sentinels:" + sentinels);
		for (String masterName : masters) {
			HostAndPort master = null;
			boolean fetched = false;
			while (!fetched && sentinelRetry < MAX_RETRY_SENTINEL) {
				for (String sentinel : sentinels) {
					final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
					logger.info("Connecting to Sentinel " + hap);
					try {
						Jedis jedis = new Jedis(hap.getHost(), hap.getPort());
						master = masterMap.get(masterName);
						if (master == null) {
							List<String> hostAndPort = jedis.sentinelGetMasterAddrByName(masterName);
							if (hostAndPort != null && hostAndPort.size() > 0) {
								master = toHostAndPort(hostAndPort);
								logger.info("Found Redis master at " + master);
								shardMasters.add(master);
								masterMap.put(masterName, master);
								fetched = true;
								jedis.disconnect();
								break;
							}
						}
					} catch (JedisConnectionException e) {
						logger.warn("Cannot connect to sentinel running @ " + hap + ". Trying next one.");
					}
				}
				if (null == master) {
					try {
						logger.info("All sentinels down, cannot determine where is " + masterName + " master is running... sleeping 1000ms, Will try again.");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fetched = false;
					sentinelRetry++;
				}
			}
			// Try MAX_RETRY_SENTINEL times.
			if (!fetched && sentinelRetry >= MAX_RETRY_SENTINEL) {
				logger.info("All sentinels down and try " + MAX_RETRY_SENTINEL + " times, Abort.");
				throw new JedisConnectionException("Cannot connect all sentinels, Abort.");
			}
		}
		// All shards master must been accessed.
		if (masters.size() != 0 && masters.size() == shardMasters.size()) {
			logger.info("Starting Sentinel listeners...");
			for (String sentinel : sentinels) {
				final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
				MasterListener masterListener = new MasterListener(masters, hap.getHost(), hap.getPort());
				masterListeners.add(masterListener);
				masterListener.start();
			}
		}
		return shardMasters;
	}
	
	private HostAndPort toHostAndPort(List<String> masterAddrByNameResult) {
		String host = masterAddrByNameResult.get(0);
		int port = Integer.parseInt(masterAddrByNameResult.get(1));
		return new HostAndPort(host, port);
	}
	
	/**
	 * PoolableObjectFactory custom impl.
	 * 分片工厂.
	 */
	protected static class ShardedJedisFactory implements PooledObjectFactory<ShardedJedis> {
		private List<JedisShardInfo> shards;
		private Hashing algo;
		private Pattern keyTagPattern;
		
		public ShardedJedisFactory(List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
			this.shards = shards;
			this.algo = algo;
			this.keyTagPattern = keyTagPattern;
		}
		
		@Override
		public PooledObject<ShardedJedis> makeObject() throws Exception {
			ShardedJedis jedis = new ShardedJedis(shards, algo, keyTagPattern);
			return new DefaultPooledObject<ShardedJedis>(jedis);
		}
		
		@Override
		public void destroyObject(PooledObject<ShardedJedis> pooledShardedJedis) throws Exception {
			final ShardedJedis shardedJedis = (ShardedJedis)pooledShardedJedis.getObject();
			Iterator iterator = shardedJedis.getAllShards().iterator();
			while(iterator.hasNext()) {
				Jedis jedis = (Jedis)iterator.next();
				try {
					jedis.quit();
				} catch (Exception e) {
					logger.error("退出错误信息是:"+e.getMessage());
				}
				try {
					jedis.disconnect();
				} catch (Exception e) {
					logger.error("断开连接错误信息是:",e.getMessage());
				}
			}
		}
		
		@Override
		public boolean validateObject(PooledObject<ShardedJedis> pooledShardedJedis) {
			try {
				ShardedJedis jedis = (ShardedJedis)pooledShardedJedis.getObject();
				Iterator iterator = jedis.getAllShards().iterator();
				Jedis shard;
				do {
					if (!iterator.hasNext()) {
						return true;
					}
					shard = (Jedis)iterator.next();
				} while("PONG".equals(shard.ping()));
				
				return false;
			} catch (Exception e) {
				logger.error("校验信息是:"+e.getMessage());
				return false;
			}
		}
		
		@Override
		public void activateObject(PooledObject<ShardedJedis> p) throws Exception {
		
		}
		
		@Override
		public void passivateObject(PooledObject<ShardedJedis> p) throws Exception {
		
		}
	}
	
	/**
	 * redis 订阅
	 */
	protected class JedisPubSubAdapter extends JedisPubSub {
		//便于实例化.
	}
	
	/**
	 * 监听器.
	 */
	protected class MasterListener extends Thread {
		/**
		 * master 节点
		 */
		protected List<String> masters;
		/**
		 * host
		 */
		protected String host;
		/**
		 * 端口
		 */
		protected int port;
		protected long subscribeRetryWaitTimeMillis = 5000;
		/**
		 * 实例
		 */
		protected Jedis jedis;
		protected AtomicBoolean running = new AtomicBoolean(false);
		
		protected MasterListener() {
		}
		
		public MasterListener(List<String> masters, String host, int port) {
			this.masters = masters;
			this.host = host;
			this.port = port;
		}
		
		public MasterListener(List<String> masters, String host, int port,
		                      long subscribeRetryWaitTimeMillis) {
			this(masters, host, port);
			this.subscribeRetryWaitTimeMillis = subscribeRetryWaitTimeMillis;
		}
		
		@Override
		public void run() {
			running.set(true);
			while (running.get()) {
				jedis = new Jedis(host, port);
				try {
					jedis.subscribe(new JedisPubSubAdapter() {
						@Override
						public void onMessage(String channel, String message) {
							logger.info("Sentinel " + host + ":" + port + " published: " + message + ".");
							String[] switchMasterMsg = message.split(" ");
							if (switchMasterMsg.length > 3) {
								int index = masters.indexOf(switchMasterMsg[0]);
								if (index >= 0) {
									HostAndPort newHostMaster = toHostAndPort(Arrays.asList(switchMasterMsg[3], switchMasterMsg[4]));
									List<HostAndPort> newHostMasters = new ArrayList<HostAndPort>();
									for (int i = 0; i < masters.size(); i++) {
										newHostMasters.add(null);
									}
									Collections.copy(newHostMasters, currentHostMasters);
									newHostMasters.set(index, newHostMaster);
									
									initPool(newHostMasters);
								} else {
									StringBuffer sb = new StringBuffer();
									for (String masterName : masters) {
										sb.append(masterName);
										sb.append(",");
									}
									logger.info("Ignoring message on +switch-master for master name "
											+ switchMasterMsg[0]
											+ ", our monitor master name are ["
											+ sb + "]");
								}
								
							} else {
								logger.info("Invalid message received on Sentinel "
										+ host
										+ ":"
										+ port
										+ " on channel +switch-master: "
										+ message);
							}
						}
					}, "+switch-master");
					
				} catch (JedisConnectionException e) {
					
					if (running.get()) {
						logger.error("Lost connection to Sentinel at " + host
								+ ":" + port
								+ ". Sleeping 5000ms and retrying.");
						try {
							Thread.sleep(subscribeRetryWaitTimeMillis);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else {
						logger.info("Unsubscribing from Sentinel at " + host + ":"
								+ port);
					}
				}
			}
		}
		public void shutdown() {
			try {
				logger.info("Shutting down listener on " + host + ":" + port);
				running.set(false);
				// This isn't good, the Jedis object is not thread safe
				jedis.disconnect();
			} catch (Exception e) {
				logger.error("Caught exception while shutting down: ", e.getMessage());
			}
		}
	}
}