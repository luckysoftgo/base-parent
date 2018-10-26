package com.application.base.core.datasource.impl.common;

import com.application.base.core.datasource.api.ReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.api.SqlSessionFactorySupport;
import com.application.base.core.datasource.session.DataSession;
import com.application.base.utils.common.BaseStringUtil;

/**
 * @desc 读写dataSessionFactory实现
 * @ClassName:  DefaultReadAndWriteDataSessionFactory
 * @author 孤狼
 */
public class SingleDefaultReadAndWriteDataSessionFactory implements ReadAndWriteDataSessionFactory {
	
	/**
	 * 数据源配置的类.
	 */
	private SqlSessionFactorySupport support;
	
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

	
	@Override
	public DataSession getDaoByDataSourceName(String dataSourceName) {
		DataSession dataSession = new DefaultDataSession(support.getSqlSessionFacotry(dataSourceName));
		return dataSession;
	}

	
	@Override
	public DataSession getReadDataSession() {
		if (readDataSession != null) {
			return readDataSession;
		}
		if (BaseStringUtil.isEmpty(readDataSource) && !BaseStringUtil.isEmpty(defaultDataSource)) {
			readDataSource = defaultDataSource;
		}
		readDataSession = new DefaultDataSession(support.getSqlSessionFacotry(readDataSource));
		return readDataSession;
	}
	
	
	@Override
	public DataSession getWriteDataSession() {
		if (writeDataSession != null) {
			return writeDataSession;
		}
		if (BaseStringUtil.isEmpty(writeDataSource) && !BaseStringUtil.isEmpty(defaultDataSource)) {
			writeDataSource = defaultDataSource;
		}
		writeDataSession = new DefaultDataSession(support.getSqlSessionFacotry(writeDataSource));
		return writeDataSession;
	}

	public SqlSessionFactorySupport getSupport() {
		return support;
	}

	public void setSupport(SqlSessionFactorySupport support) {
		this.support = support;
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

}
