package com.application.base.common.json.adapter;

import com.application.base.common.json.ComJsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desc object转json时，String类型字段：null转"" json转object时，String类型字段：""转null
 * @author 孤狼
 */
public class DateAdapter extends TypeAdapter<Date> {
	
	/**
	 * 对象转json时调用
	 */
	@Override
	public void write(JsonWriter out, Date value) throws IOException {
		if (ComJsonUtil.isBlank(value)) {
			out.value("");
			return;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		out.value(format.format(value));
	}
	
	@Override
	public Date read(JsonReader in) throws IOException {
		return null;
	}
}
