package com.application.base.boot.json;

import com.application.base.boot.json.adapter.*;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * @desc 空字符串转为null，null转为空字符串适配器工厂类
 * @author 孤狼
 */
public class JsonAdapterFactory<T> implements TypeAdapterFactory {
	/**
	 * 所有的类型处理.
	 * @param gson
	 * @param type
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		try {
			Class<T> rawType = (Class<T>) type.getRawType();
			String dataType = rawType.getTypeName();
			String str1= "String",str2 ="java.lang.String";
			if (str1.equals(dataType) || str2.equals(dataType)) {
				return (TypeAdapter<T>) new StringAdapter();
			}
			String str3= "int",str4 ="java.lang.Integer";
			if (str3.equals(dataType) || str4.equals(dataType)){
				return (TypeAdapter<T>) new IntegerAdapter();
			}
			String str5= "double",str6 ="java.lang.Double";
			if (str5.equals(dataType) || str6.equals(dataType)){
				return (TypeAdapter<T>) new DoubleAdapter();
			}
			String str7= "java.util.Date";
			if (str7.equals(dataType)){
				return (TypeAdapter<T>) new DateAdapter();
			}
			String str8= "float",str9 ="java.lang.Float";
			if (str8.equals(dataType) || str9.equals(dataType)){
				return (TypeAdapter<T>) new FloatAdapter();
			}
			String str10= "short",str11 ="java.lang.Short";
			if (str10.equals(dataType) || str11.equals(dataType)){
				return (TypeAdapter<T>) new ShortAdapter();
			}
			String str12= "long",str13 ="java.lang.Long";
			if (str12.equals(dataType) || str13.equals(dataType)){
				return (TypeAdapter<T>) new LongAdapter();
			}
			String str14= "java.math.BigDecimal";
			if (str14.equals(dataType)){
				return (TypeAdapter<T>) new BigDecimalAdapter();
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}