package com.application.base.generate.javabase.rights;

import com.application.base.generate.javabase.bean.BaseEntity;

/**
 * SystemSourceRole实体
 * @author 系统生成
 *
 */
public class SystemSourceRole extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String TABLE_NAME = "SYSTEM_SOURCE_ROLE";
	
	/**资源id*/
	private int  sourceId = -1;
	/**资源id 对应的静态变量值*/
	public static final String FIELD_SOURCE_ID = "sourceId";
	/**角色id*/
	private int  roleId = -1;
	/**角色id 对应的静态变量值*/
	public static final String FIELD_ROLE_ID = "roleId";

	public SystemSourceRole () {
		super();
	}
	
	public SystemSourceRole (int sourceId ,int roleId ) {
		super();
		 this.sourceId = sourceId;
		 this.roleId = roleId;
	}
	
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
