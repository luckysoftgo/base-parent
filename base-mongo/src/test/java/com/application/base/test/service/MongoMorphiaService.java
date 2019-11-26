package com.application.base.test.service;

import com.application.base.mongo.morphia.MonGoBaseDao;
import com.application.base.utils.page.PageView;
import com.application.base.test.dao.MongoMorphiaDao;
import com.application.base.test.entity.MongoMorphia;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.List;
import java.util.Map;

public class MongoMorphiaService implements MonGoBaseDao<MongoMorphia,ObjectId> {
   
	 public MongoMorphiaDao mongoMorphiaDao = new MongoMorphiaDao();
	 

		public MongoMorphia findObjById(ObjectId pk) {
			return mongoMorphiaDao.findObjById(pk);
		}

		public MongoMorphia findObjByName(String proKey, String proValue) {
			return mongoMorphiaDao.findObjByName(proKey, proValue);
		}

		public MongoMorphia findObjByProps(Map<String, Object> params) {
			return mongoMorphiaDao.findObjByProps(params);
		}

		public List<MongoMorphia> findObjList(PageView pageView, Map<String, Object> params) {
			return mongoMorphiaDao.findObjList(pageView, params);
		}

		public PageView findObjPage(PageView pageView, Map<String, Object> params) {
			return mongoMorphiaDao.findObjPage(pageView, params);
		}

		public List<MongoMorphia> findObjAll() {
			return mongoMorphiaDao.findObjAll();
		}

		public List<MongoMorphia> findObjAllByPros(Map<String, Object> params) {
			return mongoMorphiaDao.findObjAllByPros(params);
		}

		public int addObjOne(MongoMorphia t) {
			return mongoMorphiaDao.addObjOne(t);
		}

		public boolean addObjAll(List<MongoMorphia> ts) {
			return mongoMorphiaDao.addObjAll(ts);
		}

		public int updateObjOne(ObjectId pk, Map<String, Object> params) {
			return mongoMorphiaDao.updateObjOne(pk, params);
		}

		public UpdateResults updateObjOneByResult(Query<MongoMorphia> query, UpdateOperations<MongoMorphia> ops) {
			return mongoMorphiaDao.updateObjOneByResult(query, ops);
		}

		public boolean updateObjAll(List<ObjectId> pks, List<Map<String, Object>> ts) {
			return mongoMorphiaDao.updateObjAll(pks, ts);
		}

		public int deleteByObjId(ObjectId pk) {
			return mongoMorphiaDao.deleteByObjId(pk);
		}

		public boolean deleteObjAll(List<ObjectId> pks) {
			return mongoMorphiaDao.deleteObjAll(pks);
		}

		public long getObjsCount() {
			return mongoMorphiaDao.getObjsCount();
		}

		public long getObjsByProsCount(Map<String, Object> params) {
			return mongoMorphiaDao.getObjsByProsCount(params);
		}
}
