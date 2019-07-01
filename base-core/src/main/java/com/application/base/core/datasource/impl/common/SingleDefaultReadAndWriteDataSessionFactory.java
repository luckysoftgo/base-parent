package com.application.base.core.datasource.impl.common;

import com.application.base.all.elastic.factory.ElasticSessionFactory;
import com.application.base.core.datasource.api.ReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.api.SqlSessionFactorySupport;
import com.application.base.core.datasource.session.DataSession;
import com.application.base.utils.common.BaseStringUtil;

/**
 * @desc 读写dataSessionFactory实现,同时具备操作es的能力
 * @ClassName:  DefaultReadAndWriteDataSessionFactory
 * @author 孤狼
 */
public class SingleDefaultReadAndWriteDataSessionFactory implements ReadAndWriteDataSessionFactory {
	
	/**
	 * 数据源配置的类.
	 */
	private SqlSessionFactorySupport factorySupport;
	
	/**
	 * 默认的数据源设置.
	 */
	private String defaultDataSource;
	
	/**
	 * 读的数据源设置.
	 */
	private String readDataSource;
	
	/**
	 * 写的数据源设置.
	 */
	private String writeDataSource;
	
	/**
	 * 读的数据源
	 */
	private DataSession readDataSession;
	
	/**
	 * 写的数据源
	 */
	private DataSession writeDataSession;
	
	/**
	 * elasticsearch 工厂.
	 */
	private ElasticSessionFactory elasticSessionFactory;
	
	@Override
	public DataSession getDaoByDataSourceName(String dataSourceName) {
		DataSession dataSession = new DefaultDataSession(factorySupport.getSqlSessionFacotry(dataSourceName));
		return dataSession;
	}

	
	@Override
	public DataSession getReadDataSession() {
		if (readDataSession != null) {
			return readDataSession;
		}
		if (BaseStringUtil.isEmpty(readDataSource) && BaseStringUtil.isNotEmpty(defaultDataSource)) {
			readDataSource = defaultDataSource;
		}
		readDataSession = new DefaultDataSession(factorySupport.getSqlSessionFacotry(readDataSource));
		return readDataSession;
	}
	
	
	@Override
	public DataSession getWriteDataSession() {
		if (writeDataSession != null) {
			return writeDataSession;
		}
		if (BaseStringUtil.isEmpty(writeDataSource) && BaseStringUtil.isNotEmpty(defaultDataSource)) {
			writeDataSource = defaultDataSource;
		}
		writeDataSession = new DefaultDataSession(factorySupport.getSqlSessionFacotry(writeDataSource));
		return writeDataSession;
	}
	
	public SqlSessionFactorySupport getFactorySupport() {
		return factorySupport;
	}
	
	public void setFactorySupport(SqlSessionFactorySupport factorySupport) {
		this.factorySupport = factorySupport;
	}
	
	public String getDefaultDataSource() {
		return defaultDataSource;
	}

	public void setDefaultDataSource(String defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

	public String getReadDataSource() {
		return readDataSource;
	}

	public void setReadDataSource(String readDataSource) {
		this.readDataSource = readDataSource;
	}

	public String getWriteDataSource() {
		return writeDataSource;
	}

	public void setWriteDataSource(String writeDataSource) {
		this.writeDataSource = writeDataSource;
	}
	
	public ElasticSessionFactory getElasticSessionFactory() {
		return elasticSessionFactory;
	}
	
	public void setElasticSessionFactory(ElasticSessionFactory elasticSessionFactory) {
		this.elasticSessionFactory = elasticSessionFactory;
	}
}
