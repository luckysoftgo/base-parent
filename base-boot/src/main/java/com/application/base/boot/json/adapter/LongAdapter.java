package com.application.base.boot.json.adapter;

import com.application.base.boot.json.ComJsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @desc Long 转换为 json
 * @author 孤狼
 */
public class LongAdapter extends TypeAdapter<Long> {

	/**
	 * json转对象时调用
	 */
	@Override
	public Long read(JsonReader reader) throws IOException {
		return null;
	}
	

	/**
	 * 对象转json时调用
	 */
	@Override
	public void write(JsonWriter out, Long value) throws IOException {
		try {
			if (ComJsonUtil.isBlank(value)) {
				out.value("");
				return;
			}
			out.value(value.longValue()+"");
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
