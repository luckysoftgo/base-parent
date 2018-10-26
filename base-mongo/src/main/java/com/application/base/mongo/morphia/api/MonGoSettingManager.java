package com.application.base.mongo.morphia.api;

import com.application.base.mongo.morphia.MonGoBaseDaoImpl;

/**
 * 
 * @author admin
 * @param <T>
 * @param <K>
 */
@DataStore(tagValue = "mongo_morphia", mongoDBName = "mongoTest")
public class MonGoSettingManager<T,K> extends MonGoBaseDaoImpl<T,K> {
	
	public static final int KEY_AUTO_NUMBER = 10000;
	
	@Override
	public long getNextIdValue() {
		long now = System.currentTimeMillis();
		long r = Double.valueOf(Math.random() * KEY_AUTO_NUMBER).longValue();
		long k = now * KEY_AUTO_NUMBER + r;
		return k;
	}
	
}
