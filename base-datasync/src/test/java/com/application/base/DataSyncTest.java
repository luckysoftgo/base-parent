package com.application.base;


import com.application.base.sync.DataSyncApplication;
import com.application.base.sync.core.DataParser;
import com.application.base.sync.util.sql.PkProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author : 孤狼
 * @NAME: DataSyncTest
 * @DESC: 测试.
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataSyncApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataSyncTest {
	
	@Autowired
	private DataParser dataParser;
	
	@Test
	public void datasign(){
		String json= "";
		System.out.println("json 是:\n\t"+json);
		try {
			dataParser.getInsertSql("develop", json, "CQ_tminfo", "f2fc060c26c645c2a9498509ecccd9a1", new PkProvider() {
				@Override
				public String getStrPrimKey() {
					return null;
				}
				@Override
				public Integer getIntPrimKey() {
					return null;
				}
			});
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
