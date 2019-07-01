package com.application.base.core.datasource.impl.common;


import com.application.base.all.elastic.factory.ElasticSessionFactory;
import com.application.base.core.datasource.api.ReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.api.SqlSessionFactorySupport;
import com.application.base.core.datasource.cons.DefaultConst;
import com.application.base.core.datasource.session.DataSession;
import com.application.base.utils.common.BaseStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @desc 读写dataSessionFactory实现,同时具备操作es的能力
 * @ClassName: MutilDefaultReadAndWriteDataSessionFactory   
 * @author 孤狼
 */
public class MutilDefaultReadAndWriteDataSessionFactory implements ReadAndWriteDataSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 数据源配置的类.
	 */
	private SqlSessionFactorySupport factorySupport;
	
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
	
	/**
	 * elasticsearch 工厂.
	 */
	private ElasticSessionFactory elasticSessionFactory;
	
	/**
	 * DataSession 创建.
	 */
	@Override
	public DataSession getDaoByDataSourceName(String dataSourceName) {
		logger.info("默认的数据名称是:"+dataSourceName+"");
		DataSession dataSession = new DefaultDataSession(factorySupport.getSqlSessionFacotry(dataSourceName));
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
				readDataSession = new DefaultDataSession(factorySupport.getSqlSessionFacotry(getDefaultDataSource()));
			}else{
				readDataSession = new DefaultDataSession(factorySupport.getSqlSessionFacotry(dataSource));
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
				writeDataSession = new DefaultDataSession(factorySupport.getSqlSessionFacotry(getDefaultDataSource()));
			}else{
				writeDataSession = new DefaultDataSession(factorySupport.getSqlSessionFacotry(dataSource));
			}
			return writeDataSession;
		}
	}

	public SqlSessionFactorySupport getFactorySupport() {
		return factorySupport;
	}
	
	public void setFactorySupport(SqlSessionFactorySupport factorySupport) {
		this.factorySupport = factorySupport;
	}
	
	/**
	 * 读数据 tag.
	 * @return
	 */
	public String getReadDataSource() {
		String factoryTag = getFactoryTag();
		if (StringUtils.isEmpty(factoryTag)) {
			//不指定具体的数据源
			return DefaultConst.DEFAULT_SQL_SESSION_FACTORY_NAME;
		}
		return DefaultConst.SQL_SESSION_FACTORY_NAME_HEAD +factoryTag+DefaultConst.SQL_SESSION_FACTORY_NAME_AFTER_READ;
	}

	/**
	 * 写数据 tag.
	 * @return
	 */
	public String getWriteDataSource() {
		String factoryTag = getFactoryTag();
		if (StringUtils.isEmpty(factoryTag)) {
			//不指定具体的数据源
			return DefaultConst.DEFAULT_SQL_SESSION_FACTORY_NAME;
		}
		return DefaultConst.SQL_SESSION_FACTORY_NAME_HEAD +factoryTag+DefaultConst.SQL_SESSION_FACTORY_NAME_AFTER_WRITE;
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
			return DefaultConst.DEFAULT_SQL_SESSION_FACTORY_NAME;
		}else{
			return defaultDataSource;
		}
	}

	public void setDefaultDataSource(String defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}
	
	public ElasticSessionFactory getElasticSessionFactory() {
		return elasticSessionFactory;
	}
	
	public void setElasticSessionFactory(ElasticSessionFactory elasticSessionFactory) {
		this.elasticSessionFactory = elasticSessionFactory;
	}
}
