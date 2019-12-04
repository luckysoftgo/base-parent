package com.application.base.config.zookeeper;

import com.application.base.pool.GenericPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : 孤狼
 * @NAME: ZooKeeperConfigProperties
 * @DESC: 属性获取 https://blog.csdn.net/qq_32924343/article/details/100108734
 **/
@ConfigurationProperties("zookeeper")
public class ZooKeeperConfigProperties {
	
	/**
	 * 连接池对象
	 */
	private GenericPool pool =new GenericPool();
	
	/**
	 * 连接对象
	 */
	private Connect connect =new Connect();
	
	/**
	 * session 会话
	 */
	private Session session =new Session();
	
	/**
	 * 连接配置
	 */
	private Connection connection =new Connection();
	
	/**
	 * 工作空间
	 */
	private Name name =new Name();
	
	public GenericPool getPool() {
		return pool;
	}
	
	public void setPool(GenericPool pool) {
		this.pool = pool;
	}
	
	public Connect getConnect() {
		return connect;
	}
	
	public void setConnect(Connect connect) {
		this.connect = connect;
	}
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Name getName() {
		return name;
	}
	
	public void setName(Name name) {
		this.name = name;
	}

	/**
	 * 连接字符串.
	 */
	public static class Connect {
	    private String strings;
		
		public String getStrings() {
			return strings;
		}
		
		public void setStrings(String strings) {
			this.strings = strings;
		}
	}
	
	/**
	 * zk session会话
	 */
	public static class Session {
		private Integer timeoutms;
		
		public Integer getTimeoutms() {
			return timeoutms;
		}
		
		public void setTimeoutms(Integer timeoutms) {
			this.timeoutms = timeoutms;
		}
	}
	
	/**
	 * zk连接超时
	 */
	public static class Connection {
		private Integer timeoutms;
		
		public Integer getTimeoutms() {
			return timeoutms;
		}
		
		public void setTimeoutms(Integer timeoutms) {
			this.timeoutms = timeoutms;
		}
	}
	
	/**
	 * zk工作空间
	 */
	public static class Name {
		private String space;
		
		public String getSpace() {
			return space;
		}
		
		public void setSpace(String space) {
			this.space = space;
		}
	}
}
