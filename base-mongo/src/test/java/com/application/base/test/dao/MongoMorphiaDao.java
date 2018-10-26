package com.application.base.test.dao;

import com.application.base.mongo.morphia.MonGoBaseDao;
import com.application.base.test.BaseMorphiaDAO;
import com.application.base.test.entity.MongoMorphia;
import org.bson.types.ObjectId;

public class MongoMorphiaDao extends BaseMorphiaDAO<MongoMorphia, ObjectId> implements MonGoBaseDao<MongoMorphia,ObjectId>{
	
}
