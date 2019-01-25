package com.application.base.config.demo.lock;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import java.io.UnsupportedEncodingException;

/**
 * @Author: 孤狼
 * @desc:
 */
public class ZKObjSerializer implements ZkSerializer {
	
	@Override
	public byte[] serialize(Object data) throws ZkMarshallingError {
		try {
			return ((String)data).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}
	
	@Override
	public Object deserialize(byte[] bytes) throws ZkMarshallingError {
		try {
			return new String(bytes,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new String();
	}
}
