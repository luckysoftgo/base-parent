package com.application.base.test;

import com.application.base.mongo.morphia.MonGoBaseDaoImpl;
import com.application.base.mongo.morphia.api.DataStore;
import com.application.base.test.entity.MongoMorphia;
import org.bson.types.ObjectId;

@DataStore(tagValue = "mongo_morphia", mongoDBName = "mongoTest")
public class BaseMorphiaDAO<T, K> extends MonGoBaseDaoImpl<MongoMorphia,ObjectId> {
	
	public static final int key_cardinal_number = 1000;

	@Override
	public long getNextIdValue() {
		long now = System.currentTimeMillis();
		long r = Double.valueOf(Math.random() * key_cardinal_number).longValue();
		long k = now * key_cardinal_number + r;
		return k;
	}
	
}