package com.application.base.utils.json.adapter;

import com.application.base.utils.json.ComJsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @desc object转json时，String类型字段：null转"" json转object时，String类型字段：""转null
 * @author 孤狼
 */
public class StringAdapter extends TypeAdapter<String> {

	/**
	 * json转对象时调用
	 */
	@Override
	public String read(JsonReader reader) throws IOException {
		try {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				//原先是返回null，这里改为返回空字符串
				return "";
			}
			String value = reader.nextString();
			if (ComJsonUtil.isBlank(value)) {
				//原先是返回null，这里改为返回空字符串
				return "";
			}
			return value;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return reader.nextString();
	}
	

	/**
	 * 对象转json时调用
	 */
	@Override
	public void write(JsonWriter out, String value) throws IOException {
		try {
			if (ComJsonUtil.isBlank(value)) {
				out.value("");
				return;
			}
			out.value(value.toString());
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
