package com.application.base.utils.json.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : 孤狼
 * @NAME: DateSerializer
 * @DESC: date类型的序列化
 **/
public class DateSerializer implements JsonSerializer<Date> {
	
	/**
	 * 默认日期的序列化方式.
	 */
	SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(dateTime.format(src));
	}
}
