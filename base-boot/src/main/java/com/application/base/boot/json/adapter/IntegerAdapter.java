package com.application.base.boot.json.adapter;

import com.application.base.boot.json.ComJsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @desc Integer 转成json
 * @author 孤狼
 */
public class IntegerAdapter extends TypeAdapter<Integer> {
	
	@Override
	public Integer read(JsonReader in) throws IOException {
		return null;
	}
	
	/**
	 * 对象转json时调用
	 */
	@Override
	public void write(JsonWriter out, Integer value) throws IOException {
		try {
			if (ComJsonUtil.isBlank(value)) {
				out.value("");
				return;
			}
			out.value(value.intValue()+"");
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
}
