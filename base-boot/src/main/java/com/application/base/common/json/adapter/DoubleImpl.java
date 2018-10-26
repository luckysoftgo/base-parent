package com.application.base.common.json.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @desc 浮點型類型转换.
 * @author 孤狼
 */
public class DoubleImpl implements JsonSerializer<Double> {
	@Override
	public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
		if (src == src.longValue()) {
			return new JsonPrimitive(src.longValue()+"");
		}
		return new JsonPrimitive(src);
	}
}
