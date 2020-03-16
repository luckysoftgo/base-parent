package com.application.test;

import com.application.base.elastic.elastic.restclient.config.EsRestClientPoolConfig;
import com.application.base.elastic.elastic.restclient.factory.EsRestClientSessionPoolFactory;
import com.application.base.elastic.elastic.restclient.pool.ElasticRestClientPool;
import com.application.base.elastic.entity.ElasticData;
import com.application.base.elastic.entity.NodeInfo;
import com.application.base.utils.common.BeanMapConvertUtils;
import com.application.base.utils.execl.ExcelUtil;
import com.application.test.excel.data.DbCommandUtil;
import com.application.test.excel.data.EsData1;
import com.application.test.excel.data.EsData10;
import com.application.test.excel.data.EsData11;
import com.application.test.excel.data.EsData12;
import com.application.test.excel.data.EsData13;
import com.application.test.excel.data.EsData14;
import com.application.test.excel.data.EsData2;
import com.application.test.excel.data.EsData3;
import com.application.test.excel.data.EsData4;
import com.application.test.excel.data.EsData5;
import com.application.test.excel.data.EsData6;
import com.application.test.excel.data.EsData7;
import com.application.test.excel.data.EsData8;
import com.application.test.excel.data.EsData9;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: FactoryTest
 * @DESC:
 **/
public class TransTest {
	
	static final String PREX_TAG="logistic_";
	
	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		EsRestClientSessionPoolFactory poolFactory = getFactory();
		data(poolFactory);
		//test1(poolFactory,importEs1());
		//test2(poolFactory,importEs2());
		//test3(poolFactory,importEs3());
		//test4(poolFactory,importEs4());
		//test5(poolFactory,importEs5());
		//test6(poolFactory,importEs6());
		//test7(poolFactory,importEs7());
		//test8(poolFactory,importEs8());
		//test9(poolFactory);
		//test10(poolFactory,importEs10());
		System.exit(0);
	}
	
	private static void data(EsRestClientSessionPoolFactory transportSessionFactory){
		DbCommandUtil manager = new DbCommandUtil();
		String sql="select a.*,c.zhaddress,c.sourcecode,b.shaddress,b.destcode,\n" +
				"d.goodsname,d.goodscode,d.goodsweight,d.goodsvolume,d.goodscount from data_process" +
				".logistics_order_info a \n" +
				"inner join data_process.logistics_receive_info b on a.ordertag = b.dataid\n" +
				"inner join data_process.logistics_locad_info c on a.ordertag = c.dataid\n" +
				"inner join data_process.logistics_goods_info d on a.ordertag = d.dataid \n" +
				"\n";
		try {
			ResultSet rest = manager.selectSQL(sql);
			List<Map<String,Object>> datas = manager.resultSetToList(rest);
			List<ElasticData> list = new ArrayList<>();
			int index=1;
			for (Map<String,Object> map : datas ) {
				ElasticData data = new ElasticData();
				data.setIndex("aaaaaaaaaaaaaaaaa");
				data.setType("all");
				data.setId(index+"");
				data.setMapData(convertMap(map));
				data.setMapFlag(true);
				list.add(data);
				index++;
			}
			boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
			System.out.println("执行 EsData1 完成!flag="+flag);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 类型转换
	 * @param sourceMap
	 * @return
	 */
	public static Map<String,Object> convertMap(Map<String,Object> sourceMap){
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.putAll(sourceMap);
		for (Map.Entry<String,Object> entry : sourceMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof java.util.Date || value instanceof java.sql.Date) {
				value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
				returnMap.put(key, value);
			}
		}
		return returnMap;
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 * @param datas
	 */
	public static void test1(EsRestClientSessionPoolFactory transportSessionFactory,List<EsData1> datas){
		int index=1;
		List<ElasticData> list = new ArrayList<>();
		for (EsData1 data1 : datas) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"manager_info");
			data.setType("manager");
			data.setId(index+"");
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data1);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData1 完成!flag="+flag);
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 * @param datas
	 */
	public static void test2(EsRestClientSessionPoolFactory transportSessionFactory,List<EsData2> datas){
		int index=1;
		List<ElasticData> list = new ArrayList<>();
		for (EsData2 data2 : datas) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"vehicle_platform");
			data.setType("vehicle");
			data.setId(index+"");
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data2);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData2 完成!flag="+flag);
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 * @param datas
	 */
	public static void test3(EsRestClientSessionPoolFactory transportSessionFactory,List<EsData3> datas){
		int index=1;
		List<ElasticData> list = new ArrayList<>();
		for (EsData3 data3 : datas) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"personnel_info");
			data.setType("personnel");
			data.setId(index+"");
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data3);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData3 完成!flag="+flag);
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 * @param datas
	 */
	public static void test4(EsRestClientSessionPoolFactory transportSessionFactory,List<EsData4> datas){
		int index=1;
		List<ElasticData> list = new ArrayList<>();
		for (EsData4 data4 : datas) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"case_info");
			data.setType("case");
			data.setId(index+"");
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data4);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData4 完成!flag="+flag);
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 * @param datas
	 */
	public static void test5(EsRestClientSessionPoolFactory transportSessionFactory,List<EsData5> datas){
		int index=1;
		List<ElasticData> list = new ArrayList<>();
		for (EsData5 data5 : datas) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"copy_info");
			data.setType("copy");
			data.setId(index+"");
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data5);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData5 完成!flag="+flag);
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 * @param datas
	 */
	public static void test6(EsRestClientSessionPoolFactory transportSessionFactory,List<EsData6> datas){
		int index=1;
		List<ElasticData> list = new ArrayList<>();
		for (EsData6 data6 : datas) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"black_info");
			data.setType("black");
			data.setId(index+"");
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data6);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData5 完成!flag="+flag);
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 * @param datas
	 */
	public static void test7(EsRestClientSessionPoolFactory transportSessionFactory,List<EsData7> datas){
		int index=1;
		List<ElasticData> list = new ArrayList<>();
		for (EsData7 data7 : datas) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"electronic_waybill");
			data.setType("electronic");
			data.setId(index+"");
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data7);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData5 完成!flag="+flag);
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 * @param datas
	 */
	public static void test8(EsRestClientSessionPoolFactory transportSessionFactory,List<EsData8> datas){
		int index=1;
		List<ElasticData> list = new ArrayList<>();
		for (EsData8 data8 : datas) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"track_vehicle");
			data.setType("track");
			data.setId(index+"");
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data8);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData5 完成!flag="+flag);
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 */
	public static void test9(EsRestClientSessionPoolFactory transportSessionFactory){
		int index=1;
		List<EsData11> es11s = importEs11();
		List<EsData12> es12s = importEs12();
		List<EsData13> es13s = importEs13();
		List<EsData14> es14s = importEs14();
		List<ElasticData> list = new ArrayList<>();
		
		for (EsData14 data14 : es14s) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"network_freight");
			data.setType("network");
			data.setId(index+"");
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data14);
			dealWith13(mapInfo,data14,es13s);
			dealWith12(mapInfo,data14,es12s);
			dealWith11(mapInfo,data14,es11s);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		System.out.println("得到的结果是:"+list.size());
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData5 完成!flag="+flag);
	}
	
	private static void dealWith11(Map<String, Object> mapInfo, EsData14 data14, List<EsData11> es11s) {
		for (EsData11 data11 : es11s) {
			if (data11.getDataId().equalsIgnoreCase(data14.getDataId())){
				mapInfo.putAll(BeanMapConvertUtils.convertBean2Map(data11));
			}
		}
	}
	
	private static void dealWith12(Map<String, Object> mapInfo, EsData14 data14, List<EsData12> es12s) {
		for (EsData12 data12 : es12s) {
			if (data12.getDataId().equalsIgnoreCase(data14.getDataId())){
				mapInfo.putAll(BeanMapConvertUtils.convertBean2Map(data12));
			}
		}
	}
	
	private static void dealWith13(Map<String, Object> mapInfo, EsData14 data14, List<EsData13> es13s) {
		for (EsData13 data13 : es13s) {
			if (data13.getDataId().equalsIgnoreCase(data14.getDataId())){
				mapInfo.putAll(BeanMapConvertUtils.convertBean2Map(data13));
			}
		}
	}
	
	/**
	 * test1 数据录入.
	 * @param transportSessionFactory
	 * @param datas
	 */
	public static void test10(EsRestClientSessionPoolFactory transportSessionFactory,List<EsData10> datas){
		int index=1;
		List<EsData9> es9s = importEs9();
		List<ElasticData> list = new ArrayList<>();
		for (EsData10 data10 : datas) {
			ElasticData data = new ElasticData();
			data.setIndex(PREX_TAG+"order_info");
			data.setType("order");
			data.setId(index+"");
			setValue9(es9s,data10);
			Map<String, Object> mapInfo = BeanMapConvertUtils.convertBean2Map(data10);
			data.setMapData(mapInfo);
			data.setMapFlag(true);
			list.add(data);
			index++;
		}
		boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
		System.out.println("执行 EsData5 完成!flag="+flag);
	}
	
	/**
	 * @param data10
	 */
	private static void setValue9(List<EsData9> es9s,EsData10 data10){
		for (EsData9 data9 : es9s) {
			if (data9.getCompanyTag().equalsIgnoreCase(data10.getCompanyTag())){
				data10.setCompanyTag(data9.getCompanyName());
			}
		}
	}
	
	/**
	 * 获得结果.
	 * @return
	 */
	public static EsRestClientSessionPoolFactory getFactory(){
		List<NodeInfo> esNodes = getTransportNodeInfos();
		EsRestClientPoolConfig poolConfig = new EsRestClientPoolConfig();
		poolConfig.setClusterName("elasticsearch");
		poolConfig.setServerNodes(esNodes);
		ElasticRestClientPool transportPool = new ElasticRestClientPool(poolConfig);
		EsRestClientSessionPoolFactory transportSessionFactory = new EsRestClientSessionPoolFactory(transportPool);
		return transportSessionFactory;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData1> importEs1(){
		ExcelUtil<EsData1> util = new ExcelUtil<EsData1>(EsData1.class);
		File file = new File("E:\\data\\test1.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData1> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData2> importEs2(){
		ExcelUtil<EsData2> util = new ExcelUtil<EsData2>(EsData2.class);
		File file = new File("E:\\data\\test2.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData2> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData3> importEs3(){
		ExcelUtil<EsData3> util = new ExcelUtil<EsData3>(EsData3.class);
		File file = new File("E:\\data\\test3.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData3> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData4> importEs4(){
		ExcelUtil<EsData4> util = new ExcelUtil<EsData4>(EsData4.class);
		File file = new File("E:\\data\\test4.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData4> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData5> importEs5(){
		ExcelUtil<EsData5> util = new ExcelUtil<EsData5>(EsData5.class);
		File file = new File("E:\\data\\test5.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData5> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData6> importEs6(){
		ExcelUtil<EsData6> util = new ExcelUtil<EsData6>(EsData6.class);
		File file = new File("E:\\data\\test6.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData6> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData7> importEs7(){
		ExcelUtil<EsData7> util = new ExcelUtil<EsData7>(EsData7.class);
		File file = new File("E:\\data\\test7.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData7> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData8> importEs8(){
		ExcelUtil<EsData8> util = new ExcelUtil<EsData8>(EsData8.class);
		File file = new File("E:\\data\\test8.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData8> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData9> importEs9(){
		ExcelUtil<EsData9> util = new ExcelUtil<EsData9>(EsData9.class);
		File file = new File("E:\\data\\test9.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData9> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData10>  importEs10(){
		ExcelUtil<EsData10> util = new ExcelUtil<EsData10>(EsData10.class);
		File file = new File("E:\\data\\test10.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData10> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData11>  importEs11(){
		ExcelUtil<EsData11> util = new ExcelUtil<EsData11>(EsData11.class);
		File file = new File("E:\\data\\test11.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData11> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData12>  importEs12(){
		ExcelUtil<EsData12> util = new ExcelUtil<EsData12>(EsData12.class);
		File file = new File("E:\\data\\test12.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData12> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData13>  importEs13(){
		ExcelUtil<EsData13> util = new ExcelUtil<EsData13>(EsData13.class);
		File file = new File("E:\\data\\test13.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData13> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static List<EsData14> importEs14(){
		ExcelUtil<EsData14> util = new ExcelUtil<EsData14>(EsData14.class);
		File file = new File("E:\\data\\test14.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<EsData14> alterList = util.importExcel(input);
			return alterList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得 node 信息
	 * @return
	 */
	private static List<NodeInfo> getTransportNodeInfos(){
		List<NodeInfo> nodeConfigs = new ArrayList<>();
		NodeInfo nodeConfig = new NodeInfo();
		nodeConfig.setNodeName("es0");
		nodeConfig.setNodeHost("192.168.10.216");
		nodeConfig.setNodePort(9200);
		nodeConfig.setNodeSchema("http");
		nodeConfigs.add(nodeConfig);
		return nodeConfigs;
	}
	
}
