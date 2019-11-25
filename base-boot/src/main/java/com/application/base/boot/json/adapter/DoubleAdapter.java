package com.application.base.boot.json.adapter;

import com.application.base.boot.json.ComJsonUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @desc Double 转换为 json.
 * @author 孤狼
 */
public class DoubleAdapter extends TypeAdapter<Double> {

	/**
	 * json转对象时调用
	 */
	@Override
	public Double read(JsonReader reader) throws IOException {
		return null;
	}
	
	/**
	 * 对象转json时调用
	 */
	@Override
	public void write(JsonWriter out, Double value) throws IOException {
		try {
			if (ComJsonUtil.isBlank(value)) {
				out.value("");
				return;
			}
			out.value(value.doubleValue()+"");
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
