package com.application.base.common.json.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @desc 将 Date 类型的转换为 json
 * @author 孤狼
 */
public class SqlDateAdapter implements JsonSerializer<Date> {
	
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 初始化时间.
	 * @return
	 */
	private Date getInitDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(1970, 0, 01,0,0,0);
		return (Date) calendar.getTime();
	}
	
	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		if (null==src){
			src = getInitDate();
		}
		String dateFormatAsString = format.format(new Date(src.getTime()));
		return new JsonPrimitive(dateFormatAsString);
	}
}
