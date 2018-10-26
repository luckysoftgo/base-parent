package com.application.base.common.json.adapter;

import com.application.base.common.json.ComJsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @desc Float 转换为 json
 * @author 孤狼
 */
public class FloatAdapter extends TypeAdapter<Float> {

	/**
	 * json转对象时调用
	 */
	@Override
	public Float read(JsonReader reader) throws IOException {
		return null;
	}
	

	/**
	 * 对象转json时调用
	 */
	@Override
	public void write(JsonWriter out, Float value) throws IOException {
		try {
			if (ComJsonUtil.isBlank(value)) {
				out.value("");
				return;
			}
			out.value(value.floatValue()+"");
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
