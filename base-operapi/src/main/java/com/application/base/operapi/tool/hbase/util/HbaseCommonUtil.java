package com.application.base.operapi.tool.hbase.util;

import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author : 孤狼
 * @NAME: HbaseCommonUtil
 * @DESC: 公共工具.
 **/
public class HbaseCommonUtil {
	
	public static final String COLLECTION_SPLIT_CH = "\u0001";
	
	public static final String MAP_KV_CH = "\u0002";
	
    /**
     * 过滤特殊用于分隔作用的特殊字符
     * @param value
     * @return
     */
    public static String filterSpecChar(String value){
        return value.replaceAll(COLLECTION_SPLIT_CH,"").replaceAll(MAP_KV_CH,"");
    }
	
	/**
	 * 转换.
	 * @param type
	 * @param value
	 * @return
	 */
	public static Object convert(Class type, String value) {
		if (type.equals(String.class)) {
			return value;
		} else if (!type.equals(Integer.TYPE) && !type.equals(Integer.class)) {
			if (!type.equals(Short.class) && !type.equals(Short.TYPE)) {
				if (!type.equals(Integer.class) && !type.equals(Integer.TYPE)) {
					if (!type.equals(Float.class) && !type.equals(Float.TYPE)) {
						if (!type.equals(Double.class) && !type.equals(Double.TYPE)) {
							if (!type.equals(Long.class) && !type.equals(Long.TYPE)) {
								if (!type.equals(Boolean.class) && !type.equals(Boolean.TYPE)) {
									return null;
								} else {
									return value == null ? false : Boolean.parseBoolean(value);
								}
							} else {
								return value == null ? 0L : Long.parseLong(value);
							}
						} else {
							return value == null ? 0.0D : Double.parseDouble(value);
						}
					} else {
						return value == null ? 0.0F : Float.parseFloat(value);
					}
				} else {
					return value == null ? 0 : Integer.parseInt(value);
				}
			} else {
				return value == null ? 0 : Short.parseShort(value);
			}
		} else {
			return Integer.parseInt(value);
		}
	}
	
	/**
	 * 转换
	 * @param type
	 * @param value
	 * @return
	 */
	public static Object convertWithNull(Class type, String value) {
		if (type.equals(String.class)) {
			return value;
		} else if (!type.equals(Integer.TYPE) && !type.equals(Integer.class)) {
			if (!type.equals(Short.class) && !type.equals(Short.TYPE)) {
				if (!type.equals(Integer.class) && !type.equals(Integer.TYPE)) {
					if (!type.equals(Float.class) && !type.equals(Float.TYPE)) {
						if (!type.equals(Double.class) && !type.equals(Double.TYPE)) {
							if (!type.equals(Long.class) && !type.equals(Long.TYPE)) {
								if (!type.equals(Boolean.class) && !type.equals(Boolean.TYPE)) {
									if (type.equals(Date.class)) {
										return value == null ? null : value.toString();
									} else if (type.equals(java.util.Date.class)) {
										return value == null ? null : java.util.Date.parse(value);
									} else if (type.equals(Timestamp.class)) {
										return value == null ? null : Timestamp.valueOf(value) + "";
									} else {
										return null;
									}
								} else {
									return value == null ? null : Boolean.parseBoolean(value);
								}
							} else {
								return value == null ? null : Long.parseLong(value);
							}
						} else {
							return value == null ? null : Double.parseDouble(value);
						}
					} else {
						return value == null ? null : Float.parseFloat(value);
					}
				} else {
					return value == null ? null : Integer.parseInt(value);
				}
			} else {
				return value == null ? null : Short.parseShort(value);
			}
		} else {
			return Integer.parseInt(value);
		}
	}
	
	/**
	 * 类型转换
	 * @param type
	 * @param bts
	 * @return
	 */
	public static Object bytesToValue(Type type, byte[] bts) {
		if (bts == null && type.equals(String.class)) {
			return null;
		} else if (bts == null && !type.equals(String.class)) {
			return null;
		} else if (type.equals(String.class)) {
			return Bytes.toString(bts);
		} else if (!type.equals(Integer.TYPE) && !type.equals(Integer.class)) {
			if (!type.equals(Short.class) && !type.equals(Short.TYPE)) {
				if (!type.equals(Float.class) && !type.equals(Float.TYPE)) {
					if (!type.equals(Double.class) && !type.equals(Double.TYPE)) {
						if (!type.equals(Long.class) && !type.equals(Long.TYPE)) {
							return !type.equals(Boolean.class) && !type.equals(Boolean.TYPE) ? null : Bytes.toBoolean(bts);
						} else {
							return Bytes.toLong(bts);
						}
					} else {
						return Bytes.toDouble(bts);
					}
				} else {
					return Bytes.toFloat(bts);
				}
			} else {
				return Bytes.toShort(bts);
			}
		} else {
			return Bytes.toInt(bts);
		}
	}
	
	/**
	 *类型转换
	 * @param obj
	 * @return
	 */
	public static byte[] convertToBytes(Object obj) {
		if (obj.getClass().equals(String.class)) {
			return Bytes.toBytes(obj.toString());
		} else if (!obj.getClass().equals(Integer.TYPE) && !obj.getClass().equals(Integer.class)) {
			if (!obj.getClass().equals(Short.class) && !obj.getClass().equals(Short.TYPE)) {
				if (!obj.getClass().equals(Integer.class) && !obj.getClass().equals(Integer.TYPE)) {
					if (!obj.getClass().equals(Float.class) && !obj.getClass().equals(Float.TYPE)) {
						if (!obj.getClass().equals(Double.class) && !obj.getClass().equals(Double.TYPE)) {
							if (!obj.getClass().equals(Long.class) && !obj.getClass().equals(Long.TYPE)) {
								if (!obj.getClass().equals(Boolean.class) && !obj.getClass().equals(Boolean.TYPE)) {
									return null;
								} else {
									return obj == null ? Bytes.toBytes(false) : Bytes.toBytes(Boolean.parseBoolean(obj.toString()));
								}
							} else {
								return obj.toString() == null ? Bytes.toBytes(0L) : Bytes.toBytes(Long.parseLong(obj.toString()));
							}
						} else {
							return obj == null ? Bytes.toBytes(0.0D) : Bytes.toBytes(Double.parseDouble(obj.toString()));
						}
					} else {
						return obj == null ? Bytes.toBytes(0.0F) : Bytes.toBytes(Float.parseFloat(obj.toString()));
					}
				} else {
					return obj == null ? Bytes.toBytes(0) : Bytes.toBytes(Integer.parseInt(obj.toString()));
				}
			} else {
				return obj == null ? Bytes.toBytes((short)0) : Bytes.toBytes(Short.parseShort(obj.toString()));
			}
		} else {
			return Bytes.toBytes(Integer.parseInt(obj.toString()));
		}
	}
	
	/**
	 * 得到实例
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getInstanceByClass(Class<T> clazz) {
		T model = null;
		try {
			model = clazz.newInstance();
		} catch (InstantiationException var3) {
			var3.printStackTrace();
		} catch (IllegalAccessException var4) {
			var4.printStackTrace();
		}
		return model;
	}
	
	
	/**
	 * 获得对象的值
	 * @param bean
	 * @param name
	 * @return
	 */
	public static Object getProperty(Object bean, String name) {
		Object value = null;
		String firstLetter = name.substring(0, 1).toUpperCase();
		String methodName = "get" + firstLetter + name.substring(1);
		Method method = null;
		try {
			method = bean.getClass().getMethod(methodName);
			value = method.invoke(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 设置属性
	 * @param bean
	 * @param name
	 * @param value
	 */
	public static void setProperty(Object bean, String name, Object value) {
		String firstLetter = name.substring(0, 1).toUpperCase();
		String setMethodName = "set" + firstLetter + name.substring(1);
		Method method = null;
		try {
			Method[] methods = bean.getClass().getDeclaredMethods();
			Method[] array = methods;
			int count = methods.length;
			for(int i = 0; i < count; ++i) {
				Method method1 = array[i];
				if (setMethodName.equals(method1.getName())) {
					method = method1;
					break;
				}
			}
			method.invoke(bean, value);
		} catch (Exception var11) {
			var11.printStackTrace();
		}
	}
}
