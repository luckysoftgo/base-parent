package com.application.base.pool;

/**
 * @author : 孤狼
 * @NAME: GenericPool
 * @DESC: 基础pool属性设置.
 *  https://blog.csdn.net/qq_32924343/article/details/100108734
 **/
public class GenericPool {
	
	/**
	 * GenericObjectPoolConfig 池中最大链接数，默认是8
	 */
	private Integer maxTotal=100;
	
	/**
	 * GenericObjectPoolConfig 池中最大空闲链接数目，默认是8
	 */
	private Integer maxIdle=50;
	
	/**
	 * GenericObjectPoolConfig 池中最小空闲链接数目，默认为0
	 */
	private Integer minIdle=50;
	
	/**
	 * BaseObjectPoolConfig 当链接池资源耗尽时，等待时间，超出抛异常，默认为-1，阻塞。
	 */
	private Integer maxWaitMillis=18000;
	
	/**
	 * BaseObjectPoolConfig 默认是false，创建一个链接时检测是否链接有效，无效则剔除，并尝试继续获取新链接。
	 */
	private Boolean testOnCreate=true;
	/**
	 * BaseObjectPoolConfig 默认是false，借取一个链接时检测是否有效，无效则剔除，并尝试继续获取新链接。
	 */
	private Boolean testOnBorrow=true;
	/**
	 * BaseObjectPoolConfig 默认为false，归还一个对象时检测是否有效，无效则不放入链接池内。
	 */
	private Boolean testOnReturn=true;
	/**
	 * BaseObjectPoolConfig 默认为false，指明空闲链接是否需要被【空闲链接回收器】【evict方法】检测，检测出链接无效则被移除。
	 */
	private Boolean testWhileIdle=true;
	/**
	 * BaseObjectPoolConfig .空闲链接回收器运行的周期，单位ms，默认-1，永不执行检测。
	 */
	private Integer timeBetweenEvictionRunsMillis=60000;
	/**
	 * BaseObjectPoolConfig 空闲链接回收器运行时检查的空闲链接数量，默认是3个。
	 */
	private Integer numTestsPerEvictionRun=-1;
	
	public Integer getMaxTotal() {
		return maxTotal;
	}
	
	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	public Integer getMaxIdle() {
		return maxIdle;
	}
	
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	
	public Integer getMinIdle() {
		return minIdle;
	}
	
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
	
	public Integer getMaxWaitMillis() {
		return maxWaitMillis;
	}
	
	public void setMaxWaitMillis(Integer maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
	
	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}
	
	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	
	public Boolean getTestOnReturn() {
		return testOnReturn;
	}
	
	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	
	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}
	
	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
	
	public Boolean getTestOnCreate() {
		return testOnCreate;
	}
	
	public void setTestOnCreate(Boolean testOnCreate) {
		this.testOnCreate = testOnCreate;
	}
	
	public Integer getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	
	public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	
	public Integer getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}
	
	public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}
}
