package com.application.base.utils.json.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @desc 将 Timestamp 类型的转换为 json
 * @author 孤狼
 */
public class SqlTimestampAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {
	
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public JsonElement serialize(Timestamp src, Type type, JsonSerializationContext context) {
		if (null==src){
			src = getInitDate();
		}
		String dateFormatAsString = format.format(new Date(src.getTime()));
		return new JsonPrimitive(dateFormatAsString);
	}
	
	@Override
	public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}
		try {
			Date date = (Date) format.parse(json.getAsString());
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
	}
	
	/**
	 * 初始化时间.
	 * @return
	 */
	private Timestamp getInitDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(1970, 0, 01,0,0,0);
		return (Timestamp) calendar.getTime();
	}
}
