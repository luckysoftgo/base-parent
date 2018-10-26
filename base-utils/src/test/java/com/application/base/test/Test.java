package com.application.base.test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 孤狼
 */
public class Test {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	/** 唯一标识 */
	private Integer id ;
	/** 唯一标识uuid */
	private String uuid;
	/** 创建人 */
	private Integer createUser;
	/** 创建时间 */
	private Date createTime;
	/** 修改人 */
	private Integer updateUser;
	/** 修改人时间 */
	private Date updateTime;
	/** 是否启用 */
	private Integer isDelete;
	/** 描述 */
	private String infoDesc;
	
	private  String name ;
	private  int age;
	
	private String aaa;
	private Double bbb;
	
	private String d1;
	private BigDecimal d2;
	
	public String getD1() {
		return d1;
	}
	
	public void setD1(String d1) {
		this.d1 = d1;
	}
	
	public BigDecimal getD2() {
		return d2;
	}
	
	public void setD2(BigDecimal d2) {
		this.d2 = d2;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getCreateUser() {
		return createUser;
	}
	
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getUpdateUser() {
		return updateUser;
	}
	
	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getIsDelete() {
		return isDelete;
	}
	
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public String getInfoDesc() {
		return infoDesc;
	}
	
	public void setInfoDesc(String infoDesc) {
		this.infoDesc = infoDesc;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getAaa() {
		return aaa;
	}
	
	public void setAaa(String aaa) {
		this.aaa = aaa;
	}
	
	public Double getBbb() {
		return bbb;
	}
	
	public void setBbb(Double bbb) {
		this.bbb = bbb;
	}
}
