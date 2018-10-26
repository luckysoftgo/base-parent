package com.application.base.generate.javabase.rights;

import com.application.base.generate.javabase.bean.BaseEntity;

/**
 * SystemSource实体
 * 
 * @author 系统生成
 *
 */
public class SystemSource extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String TABLE_NAME = "SYSTEM_SOURCE";
	
	/**资源名称*/
	private String  sourceName = null;
	/**资源名称 对应的静态变量值*/
	public static final String FIELD_SOURCE_NAME = "sourceName";
	/**资源父级Id*/
	private int  parentId = -1;
	/**资源父级Id 对应的静态变量值*/
	public static final String FIELD_PARENT_ID = "parentId";
	/**资源key,是唯一的访问Key*/
	private String  sourceKey = null;
	/**资源key,是唯一的访问Key 对应的静态变量值*/
	public static final String FIELD_SOURCE_KEY = "sourceKey";
	/**资源类型,0:菜单,1:按钮,2:链接*/
	private String  sourceType = null;
	/**资源类型,0:菜单,1:按钮,2:链接 对应的静态变量值*/
	public static final String FIELD_SOURCE_TYPE = "sourceType";
	/**资源访问的url*/
	private String  sourceUrl = null;
	/**资源访问的url 对应的静态变量值*/
	public static final String FIELD_SOURCE_URL = "sourceUrl";
	/**资源等级,从1开始,1,2,3,4*/
	private int  sourceLevel = -1;
	/**资源等级,从1开始,1,2,3,4 对应的静态变量值*/
	public static final String FIELD_SOURCE_LEVEL = "sourceLevel";
	/**父级的名称*/
	private String parentName = null;
	/**资源介绍*/
	private String  description = null;
	/**资源介绍 对应的静态变量值*/
	public static final String FIELD_DESCRIPTION = "description";
	/**资源状态;1:启用,0:弃用*/
	private int  sourceStatus = -1;
	/**资源状态;1:启用,0:弃用 对应的静态变量值*/
	public static final String FIELD_SOURCE_STATUS = "sourceStatus";
	/**是否是iframe;1:是,0:否*/
	private int  isIframe = -1;
	/**是否是iframe;1:是,0:否 对应的静态变量值*/
	public static final String FIELD_IS_IFRAME = "isIframe";
	
	public SystemSource () {
		super();
	}
	
	public SystemSource (int id, String sourceName ,int parentId ,String sourceKey ,String sourceType ,String sourceUrl ,int sourceLevel ,String description ) {
		 super();
		 super.setId(id);
		 this.sourceName = sourceName;
		 this.parentId = parentId;
		 this.sourceKey = sourceKey;
		 this.sourceType = sourceType;
		 this.sourceUrl = sourceUrl;
		 this.sourceLevel = sourceLevel;
		 this.description = description;
	}
	
	public SystemSource (int id, String sourceName ,int parentId ,String sourceKey ,String sourceType ,String sourceUrl ,int sourceLevel ,String description ,int sourceStatus ) {
		 super();
		 super.setId(id);
		 this.sourceName = sourceName;
		 this.parentId = parentId;
		 this.sourceKey = sourceKey;
		 this.sourceType = sourceType;
		 this.sourceUrl = sourceUrl;
		 this.sourceLevel = sourceLevel;
		 this.description = description;
		 this.sourceStatus = sourceStatus;
	}
	
	public SystemSource (int id, String sourceName ,int parentId ,String sourceKey ,String sourceType ,String sourceUrl ,int sourceLevel ,String description ,int sourceStatus ,int isIframe ) {
		 super();
		 super.setId(id);
		 this.sourceName = sourceName;
		 this.parentId = parentId;
		 this.sourceKey = sourceKey;
		 this.sourceType = sourceType;
		 this.sourceUrl = sourceUrl;
		 this.sourceLevel = sourceLevel;
		 this.description = description;
		 this.sourceStatus = sourceStatus;
		 this.isIframe = isIframe;
	}
	
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getSourceKey() {
		return sourceKey;
	}
	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public int getSourceLevel() {
		return sourceLevel;
	}
	public void setSourceLevel(int sourceLevel) {
		this.sourceLevel = sourceLevel;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSourceStatus() {
		return sourceStatus;
	}
	public void setSourceStatus(int sourceStatus) {
		this.sourceStatus = sourceStatus;
	}
	public int getIsIframe() {
		return isIframe;
	}
	public void setIsIframe(int isIframe) {
		this.isIframe = isIframe;
	}
}
