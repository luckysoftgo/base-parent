package com.application.base.core.datasource.impl.common;


import com.application.base.utils.common.BaseStringUtil;
import org.springframework.util.StringUtils;

import com.application.base.core.datasource.api.ReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.api.SqlSessionFactorySupport;
import com.application.base.core.datasource.session.DataSession;

/**
 * @desc 读写dataSessionFactory实现
 * @ClassName: MutilDefaultReadAndWriteDataSessionFactory   
 * @author 孤狼
 */
public class MutilDefaultReadAndWriteDataSessionFactory implements ReadAndWriteDataSessionFactory {
	
	/**
	 * 数据源配置的类.
	 */
	private SqlSessionFactorySupport support;
	
	/**
	 * 数据的 xml 中的标识A,B,C,D,E,F...
	 */
	private String factoryTag = "";
	
	/**
	 * 默认的数据源设置.
	 */
	private String defaultDataSource;
	
	/**
	 * 读的数据源
	 */
	private DataSession readDataSession;
	
	/**
	 * 写的数据源
	 */
	private DataSession writeDataSession;
	
	String defaultSession = "sqlSessionFactoryDefault";
	
	/**
	 * DataSession 创建.
	 */
	@Override
	public DataSession getDaoByDataSourceName(String dataSourceName) {
		System.out.println("=============================="+dataSourceName+"==============================");
		DataSession dataSession = new DefaultDataSession(support.getSqlSessionFacotry(dataSourceName));
		return dataSession;
	}
	
	/**
	 * 读 session 的创建.
	 */
	@Override
	public DataSession getReadDataSession() {
		if (readDataSession != null){
			return readDataSession;
		}else{
			String dataSource = getReadDataSource();
			if (StringUtils.isEmpty(dataSource)) {
				readDataSession = new DefaultDataSession(support.getSqlSessionFacotry(getDefaultDataSource()));
			}else{
				readDataSession = new DefaultDataSession(support.getSqlSessionFacotry(dataSource));
			}
			return readDataSession;
		}
	}
	
	/**
	 * 写 session 的创建.
	 */
	@Override
	public DataSession getWriteDataSession() {
		if (writeDataSession != null){
			return writeDataSession;
		}else{
			String dataSource = getWriteDataSource();
			if (StringUtils.isEmpty(dataSource)) {
				writeDataSession = new DefaultDataSession(support.getSqlSessionFacotry(getDefaultDataSource()));
			}else{
				writeDataSession = new DefaultDataSession(support.getSqlSessionFacotry(dataSource));
			}
			return writeDataSession;
		}
	}

	public SqlSessionFactorySupport getSupport() {
		return support;
	}

	public void setSupport(SqlSessionFactorySupport support) {
		this.support = support;
	}

	/**
	 * 读数据 tag.
	 * @return
	 */
	public String getReadDataSource() {
		String factoryTag = getFactoryTag();
		if (StringUtils.isEmpty(factoryTag)) {
			//不指定具体的数据源
			return defaultSession;
		}
		return "sqlSessionFactory"+factoryTag+"read";
	}

	/**
	 * 写数据 tag.
	 * @return
	 */
	public String getWriteDataSource() {
		String factoryTag = getFactoryTag();
		if (StringUtils.isEmpty(factoryTag)) {
			//不指定具体的数据源
			return defaultSession;
		}
		return "sqlSessionFactory"+factoryTag+"write";
	}

    public String getFactoryTag() {
		return factoryTag;
	}
    
    /**
     * 设置:
     * 增删改查操作时候使用的数据源.
     * 如果没有手动设置,就使用默认的数据源.
     * @param factoryTag
     */
	public void setFactoryTag(String factoryTag) {
		if (StringUtils.isEmpty(factoryTag)) {
			this.factoryTag = "";
		}else{
			this.factoryTag = factoryTag.toUpperCase();
		}
	}
	
	/**
	 * 设置默认的数据源.
	 * @return
	 */
	public String getDefaultDataSource() {
		//default setting for one datasource
		if (BaseStringUtil.isEmpty(defaultDataSource)) {
			return defaultSession;
		}else{
			return defaultDataSource;
		}
	}

	public void setDefaultDataSource(String defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

}
