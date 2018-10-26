package com.application.base.core.datasource.param;

/**
 * @desc 定义一个接口.
 * @author 孤狼
 */
public enum ESQL {
	
	/**
	 * 保存单个对象
	 */
	SAVE("saveObject"),
	
	/**
	 *批量保存对象
	 */
	SAVEBATCH("saveBatchObject"),
	
	/**
	 *通过id修改对象
	 */
	UPDATEBYID("updateObjectById"),
	
	/**
	 *通过uuid修改对象
	 */
	UPDATEBYUUID("updateObjectByUUId"),
	
	/**
	 *逻辑删除
	 */
	LOGICDELETE("logicDelete"),
	
	/**
	 * 物理删除
	 */
	PHYSICALDELETE("physicalDelete"),
	
	/**
	 * 通过id查询
	 */
	QUERYSINGLERESULTBYID("querySingleResultById"),
	
	/**
	 * 通过uuid查询
	 */
	QUERYSINGLERESULTBYUUID("querySingleResultByUUId"),
	
	/**
	 * 通过属性查询
	 */
	QUERYSINGLERESULTBYPARAMS("querySingleResultByParams"),
	
	/**
	 * 查询对象集合
	 */
	QUERYLISTRESULT("queryListResult"),
	
	/**
	 * 查询所有对象集合
	 */
	QUERYALLLISTRESULT("queryAllListResult"),
	
	/**
	 * 查询结果的count数
	 */
	QUERYLISTRESULTCOUNT("queryListResultCount"),
	
	/**
	 * 通过 where 修改对象
	 */
	UPDATECUSTOMCOLUMNBYWHERE("updateCustomColumnByWhere"),
	
	/**
	 * 逻辑删除
	 */
	LOGICWHEREDELETE("logicWhereDelete"),
	
	/**
	 * 物理删除
	 */
	PHYSICALWHEREDELETE("physicalWhereDelete"),
	
	/**
	 * 通过where条件查询集合
	 */
	QUERYLISTRESULTBYWHERE("queryListResultByWhere"),
	
	/**
	 *通过where条件查询 count
	 */
    QUERYLISTRESULTCOUNTBYWHERE("queryListResultCountByWhere"),
	
	;
    
	private String sqlId;
	
	ESQL(String sql) {
		this.sqlId = sql;
	}
	
	public String getSqlId() {
		return sqlId;
	}
	
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
	
}
