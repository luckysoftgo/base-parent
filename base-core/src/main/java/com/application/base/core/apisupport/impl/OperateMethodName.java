package com.application.base.core.apisupport.impl;

/**
 * @NAME: OperateMethodName
 * @DESC: 操作 mybatis 的名称.
 * @USER: 孤狼
 **/
public enum OperateMethodName {
	
	/**
	 *通过Id查找对象
	 */
	FINDBYID("findById","通过Id查找对象."),
	/**
	 *通过名称查找对象
	 */
	FINDBYNAME("findByName","通过名称查找对象."),
	/**
	 *通过属性查找对象
	 */
	FINDBYPROPS("findByProps","通过属性查找对象."),
	/**
	 *通过查找对象
	 */
	FIND("find","通过查找对象."),
	/**
	 *通过查找所有对象
	 */
	FINDALL("findAll","通过查找所有对象."),
	/**
	 *通过属性查找对象
	 */
	FINDALLBYPROS("findAllByPros","通过属性查找对象."),
	/**
	 *添加一个对象
	 */
	ADDONE("addOne","添加一个对象."),
	/**
	 *添加所有对象
	 */
	ADDALL("addAll","添加所有对象."),
	/**
	 *修改一个对象
	 */
	UPDATEONE("updateOne","修改一个对象."),
	/**
	 *修改所有对象
	 */
	UPDATEALL("updateAll","修改所有对象."),
	/**
	 *通过Id删除对象
	 */
	DELETEBYID("deleteById","通过Id删除对象."),
	/**
	 *删除所有对象
	 */
	DELETEALL("deleteAll","删除所有对象."),
	/**
	 *获得对象的条数
	 */
	GETOBJSCOUNT("getObjsCount","获得对象的条数."),
	/**
	 *通过属性获得对象的条数
	 */
	GETOBJSBYPROSCOUNT("getObjsByProsCount","通过属性获得对象的条数."),
	;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 描述
	 */
	private String desc;
	
	OperateMethodName(String name,String desc){
		this.name = name;
		this.desc = desc;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
