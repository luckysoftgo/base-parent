package com.application.base.test;

import com.application.base.utils.json.JsonConvertUtils;
import com.application.base.utils.page.Common;
import com.application.base.utils.page.PageView;
import com.application.base.test.entity.MongoMorphia;
import com.application.base.test.service.MongoMorphiaService;
import org.bson.types.ObjectId;

import java.util.*;

public class MongoMorphiaTest {

	static MongoMorphiaService service = new MongoMorphiaService();

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// add
		//save();
		
		//findById();

		// update
		//update();

		// delete
		//delete();

		// findAll
		//findAll();

		// findLikeAll like 查询
		// findLikeAll();
		
		//findContainAll contain 查询.
		//findContainAll();
		
		//findInAll in 查询.
		//findInAll();
		
		// findPage
		findPage();
	}

	
	private static void findById() {
		MongoMorphia mongoMorphia = service.findObjById(new ObjectId("585bf03d6feb21ab336c2dce"));
		System.out.println(JsonConvertUtils.toJson(mongoMorphia));
	}
	
	private static void findPage() {
		Map<String, Object> params = new HashMap<>();
		PageView pageView = findPage("1","10");
		pageView = service.findObjPage(pageView, params);
		System.out.println("分页获取的信息是:");
		System.out.println(JsonConvertUtils.toJson(pageView));
	}

	private static void findContainAll() {
		Map<String, Object> params = new HashMap<>();
		String[] tags = new String[] { "Google"};
		params.put("tags_@contain", tags); // contain 包涵操作
		List<MongoMorphia> allList = service.findObjAllByPros(params);
		System.out.println("查询所有的记录");
		for (MongoMorphia mongoMorphia : allList) {
			System.out.println(JsonConvertUtils.toJson(mongoMorphia));
		}
	}
	
	private static void findInAll() {
		Map<String, Object> params = new HashMap<>();
		Set<String> set = new HashSet();
		set.add("Google");
		set.add("Baidu");
		set.add("Sougo");
		params.put("tags_@in", set); // in 包涵操作
		List<MongoMorphia> allList = service.findObjAllByPros(params);
		System.out.println("查询所有的记录");
		for (MongoMorphia mongoMorphia : allList) {
			System.out.println(JsonConvertUtils.toJson(mongoMorphia));
		}
	}
	
	private static void findLikeAll() {
		Map<String, Object> params = new HashMap<>();
		params.put("title_@like", "教程"); //
		List<MongoMorphia> allList = service.findObjAllByPros(params);
		System.out.println("查询所有的记录");
		for (MongoMorphia mongoMorphia : allList) {
			System.out.println(JsonConvertUtils.toJson(mongoMorphia));
		}
	}
	
	private static void findAll() {
		List<MongoMorphia> allList = service.findObjAll();
		System.out.println("查询所有的记录");
		for (MongoMorphia mongoMorphia : allList) {
			System.out.println(JsonConvertUtils.toJson(mongoMorphia));
		}
	}

	private static void delete() {
		service.deleteByObjId(new ObjectId("585ba4236feb21a9822ed46e"));
		System.out.println("删除一条 mongo 记录完成...");
	}

	private static void update() {
		Map<String, Object> params = new HashMap<>();
		params.put("by", "Mongo 教程");
		params.put("url", "www.mongo.com");
		service.updateObjOne(new ObjectId("585b41b36feb21a773a208b8"), params);
		System.out.println("修改一条 mongo 记录完成...");
	}

	private static void save() {
		MongoMorphia obj = new MongoMorphia();
		obj.setTitle("Sougo  教程");
		obj.setDescription("Sougo 是一个全新的数据库");
		obj.setBy("Sougo 教程");
		obj.setUrl("www.Sougo.com");
		String[] tags = new String[] { "Sougo"};
		obj.setTags(tags);
		obj.setLikes(200);
		service.addObjOne(obj);
		System.out.println("添加一条 mongo 记录完成...");
	}

	public static PageView findPage(String pageNow, String pageSize) {
		PageView pageView = null;
		if ((Common.isEmpty(pageNow)) && (Common.isEmpty(pageSize))) {
			pageView = new PageView(1);
		}
		else if ((!Common.isEmpty(pageNow)) && (Common.isEmpty(pageSize))) {
			pageView = new PageView(Integer.parseInt(pageNow));
		}
		else if ((Common.isEmpty(pageNow)) && (!Common.isEmpty(pageSize))) {
			pageView = new PageView(1, Integer.parseInt(pageSize));
		}
		else {
			pageView = new PageView(Integer.parseInt(pageNow), Integer.parseInt(pageSize));
		}
		return pageView;
	}
}
