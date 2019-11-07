package com.application.base;


import com.application.base.core.DataParser;
import com.application.base.util.sql.PkProvider;
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
		String json= "{\"code\":\"200\",\"data\":{\"TMINFO\":[{\"TMPROJECT\":[{\"similar_group\":\"4501\"," +
				"\"commodity_services\":\"夜间护卫服务\"},{\"similar_group\":\"4501\"," +
				"\"commodity_services\":\"安全及防盗警报系统的监控\"},{\"similar_group\":\"4502\"," +
				"\"commodity_services\":\"家务服务\"},{\"similar_group\":\"4503\",\"commodity_services\":\"服装出租\"}," +
				"{\"similar_group\":\"4504\",\"commodity_services\":\"殡仪\"},{\"similar_group\":\"4505\"," +
				"\"commodity_services\":\"开保险锁\"},{\"similar_group\":\"4505\",\"commodity_services\":\"婚姻介绍\"}," +
				"{\"similar_group\":\"4505\",\"commodity_services\":\"消防\"},{\"similar_group\":\"4505\"," +
				"\"commodity_services\":\"失物招领\"},{\"similar_group\":\"4506\",\"commodity_services\":\"法律研究\"}]," +
				"\"TMADDRESS\":\"陕西省西安市高新区唐延路旺座现代城H座1505室\",\"REGDATE\":\"2016-03-07\"," +
				"\"TMFLOW\":[{\"status\":\"商标注册申请中\",\"tdate\":\"2015-01-04\"},{\"status\":\"商标注册申请受理通知书发文\"," +
				"\"tdate\":\"2015-04-10\"},{\"status\":\"等待受理通知书发文\",\"tdate\":\"2015-04-10\"}," +
				"{\"status\":\"等待注册证发文\",\"tdate\":\"2016-05-04\"},{\"status\":\"申请收文\",\"tdate\":\"2016-05-23\"}," +
				"{\"status\":\"商标注销申请完成\",\"tdate\":\"2016-11-10\"},{\"status\":\"核准通知打印发送\"," +
				"\"tdate\":\"2016-11-10\"}],\"TNAME\":\"乐乐趣\",\"TMAPPLICANT\":\"西安荣信文化产业发展有限公司\",\"TMTYPE\":45," +
				"\"APPDATE\":\"2015-01-04\",\"PIC\":\"https://feifeidata.oss-cn-beijing.aliyuncs" +
				".com/tm\\\\160\\\\p42CQIbYwu.png/\",\"REGNO\":\"16079857\",\"TMAGENT\":\"集慧知识产权代理（北京）有限公司\"," +
				"\"TMDETAIL\":\"普通商标\",\"TMGG\":[{\"tdate\":\"2015-12-06\",\"period\":\"1482\"," +
				"\"title\":\"商标初步审定公告\"},{\"tdate\":\"2016-10-20\",\"period\":\"1524\",\"title\":\"注册商标注销公告\"}," +
				"{\"tdate\":\"2016-03-06\",\"period\":\"1494\",\"title\":\"商标注册公告（一）\"}]}," +
				"{\"TMPROJECT\":[{\"similar_group\":\"4501\",\"commodity_services\":\"安全及防盗警报系统的监控\"}," +
				"{\"similar_group\":\"4502\",\"commodity_services\":\"家务服务\"},{\"similar_group\":\"4503\"," +
				"\"commodity_services\":\"服装出租\"},{\"similar_group\":\"4504\",\"commodity_services\":\"殡仪\"}," +
				"{\"similar_group\":\"4505\",\"commodity_services\":\"开保险锁\"},{\"similar_group\":\"4505\"," +
				"\"commodity_services\":\"婚姻介绍\"},{\"similar_group\":\"4505\",\"commodity_services\":\"消防\"}," +
				"{\"similar_group\":\"4505\",\"commodity_services\":\"失物招领\"},{\"similar_group\":\"4506\"," +
				"\"commodity_services\":\"法律研究\"},{\"similar_group\":null,\"commodity_services\":\"夜间护卫\"}]," +
				"\"TMADDRESS\":\"陕西省西安市高新区唐延路旺座现代城H座1505室\",\"REGDATE\":\"2016-03-07\"," +
				"\"TMFLOW\":[{\"status\":\"商标注册申请中\",\"tdate\":\"2014-12-30\"},{\"status\":\"商标注册申请受理通知书发文\"," +
				"\"tdate\":\"2015-04-10\"},{\"status\":\"商标注册申请注册公告排版完成\",\"tdate\":\"2016-03-06\"}," +
				"{\"status\":\"商标注册申请完成\",\"tdate\":\"2016-05-04\"},{\"status\":\"变更商标申请人/注册人名义/地址完成\"," +
				"\"tdate\":\"2016-12-01\"}],\"TNAME\":\"乐乐趣及图\",\"TMAPPLICANT\":\"荣信教育文化产业发展股份有限公司\",\"TMTYPE\":45," +
				"\"APPDATE\":\"2014-12-30\",\"PIC\":\"https://feifeidata.oss-cn-beijing.aliyuncs" +
				".com/tm\\\\160\\\\p4o0WiUKpb.png/\",\"REGNO\":\"16050943\",\"TMAGENT\":\"集慧知识产权代理（北京）有限公司\"," +
				"\"TMDETAIL\":\"普通商标\",\"TMGG\":[{\"tdate\":\"2015-12-06\",\"period\":\"1482\"," +
				"\"title\":\"商标初步审定公告\"},{\"tdate\":\"2016-12-06\",\"period\":\"1530\"," +
				"\"title\":\"商标注册人/申请人名义及地址变更公告\"},{\"tdate\":\"2016-03-06\",\"period\":\"1494\"," +
				"\"title\":\"商标注册公告（一）\"}]}],\"TMINFOCOUNT\":146}}";
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
