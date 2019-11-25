package com.application.base.boot.json.adapter;

import com.application.base.boot.json.ComJsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @desc Short 转换为 json
 * @author 孤狼
 */
public class ShortAdapter extends TypeAdapter<Short> {

	/**
	 * json转对象时调用
	 */
	@Override
	public Short read(JsonReader reader) throws IOException {
		return null;
	}
	

	/**
	 * 对象转json时调用
	 */
	@Override
	public void write(JsonWriter out, Short value) throws IOException {
		try {
			if (ComJsonUtil.isBlank(value)) {
				out.value("");
				return;
			}
			out.value(value.shortValue()+"");
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
