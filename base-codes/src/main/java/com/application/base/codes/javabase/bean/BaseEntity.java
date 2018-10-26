package com.application.base.codes.javabase.bean;

import com.application.base.utils.common.Constants;
import com.application.base.utils.common.UUIDProvider;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @desc 所有类的基类
 * @author 孤狼
 */
public class BaseEntity implements Serializable,Cloneable {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	/** 唯一标识 */
	private Integer id ;
	public static String FIELD_ID = "id";
	/** 唯一标识uuid */
	private String uuid;
	public static String FIELD_UUID = "uuid";
	/** 创建人 */
	private String createUser;
	public static String FIELD_CREATE_USER = "createUser";
	/** 创建时间 */
	private Date createTime;
	public static String FIELD_CREATE_TIME = "createTime";
	/** 修改人 */
	private String updateUser;
	public static String FIELD_UPDATE_USER = "updateUser";
	/** 修改人时间 */
	private Date updateTime;
	public static String FIELD_UPDATE_TIME = "updateTime";
	/** 是否删除：0启用;1删除 */
	private Integer disabled;
	public static String FIELD_DISABLED = "disabled";
	/** 描述 */
	private String infoDesc;
	public static String FIELD_INFODESC = "infoDesc";
	
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
	
	public String getCreateUser() {
		return createUser;
	}
	
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getUpdateUser() {
		return updateUser;
	}
	
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getDisabled() {
		return disabled;
	}
	
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	
	public String getInfoDesc() {
		return infoDesc;
	}
	
	public void setInfoDesc(String infoDesc) {
		this.infoDesc = infoDesc;
	}
	
	/**
	 * 获得一个实例.
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getSimpleInstance(Class<T> clazz) {
		com.application.base.utils.common.BaseEntity entity;
		try {
			entity = (com.application.base.utils.common.BaseEntity) clazz.newInstance();
			return clazz.cast(entity);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获得一个实例.
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getInstance(Class<T> clazz) {
		com.application.base.utils.common.BaseEntity entity;
		try {
			entity = (com.application.base.utils.common.BaseEntity) clazz.newInstance();
			entity.setUuid(UUIDProvider.uuid());
			entity.setDisabled(Constants.DeleteStatus.ENABLED);
			entity.setCreateTime(new Date());
			entity.setUpdateTime(new Date());
			return clazz.cast(entity);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取基本信息接口.
	 *
	 * @param entity
	 * @return
	 */
	public String getBeanInfos(com.application.base.utils.common.BaseEntity entity) {
		StringBuffer buffer = new StringBuffer(entity.getClass().getName() + ",infos : \n");
		Map<Class<?>,List<Field>> resultMap = getAllFields(entity);
		try {
			List<Field> resultFields = new ArrayList<>();
			if (resultMap!=null && resultMap.size()>0){
				for (Map.Entry<Class<?>,List<Field>> entry : resultMap.entrySet()){
					resultFields.addAll(entry.getValue());
				}
			}
			buffer.append(printBeanInfo(resultFields,entity));
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 打印出所有的字段的值.
	 * @param fields
	 * @param entity
	 * @return
	 */
	private String printBeanInfo(List<Field> fields, com.application.base.utils.common.BaseEntity entity){
		// 排查...
		String name;
		StringBuffer buffer = new StringBuffer("");
		try {
			for (int i=0;i<fields.size();i++) {
				Field field = fields.get(i);
				name = field.getName();
				field.setAccessible(true);
				Object val = field.get(entity);
				if (name.startsWith("serial") || name.startsWith("FIELD") || name.startsWith("tableName") || name.startsWith("orderBy")) {
					continue;
				}
				if (name.startsWith("createTime") || name.startsWith("updateTime") || name.startsWith("createDate") || name.startsWith("updateDate")) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (val != null) {
						val = (Date) val;
						val = format.format(val);
					}
				}
				if (i == fields.size() - 1) {
					buffer.append(name + ":" + val);
				}
				else {
					buffer.append(name + ":" + val + ",");
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 获得对象的 Field
	 * @param object
	 * @return
	 */
	private static Map<Class<?>,List<Field>> getAllFields(Object object){
		Map<Class<?>,List<Field>> resultMap = new HashMap<>(Constants.MapSize.MAP_MAX_SIZE);
		try {
			Class clazz = object.getClass();
			while (clazz != null){
				Field[] fields = clazz.getDeclaredFields();
				resultMap.put(clazz,Arrays.asList(fields));
				clazz = clazz.getSuperclass();
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return resultMap;
	}
	
}
