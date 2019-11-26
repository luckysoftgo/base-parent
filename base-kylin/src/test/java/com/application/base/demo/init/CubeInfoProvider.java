package com.application.demo.init;

import com.application.demo.bean.CubeDescInfo;
import com.application.demo.bean.CubeInfo;
import com.application.demo.bean.Measures;
import com.application.demo.cont.KylinConstant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 孤狼.
 * @NAME: CubeInfoProvider.
 * @DESC: cube 信息的提供.
 **/
@Component
public class CubeInfoProvider {
	
	/**
	 * cube 的信息.
	 */
	public ConcurrentHashMap<String,Object> cubesMap = new ConcurrentHashMap(32);
	
	/**
	 * 放入
	 */
	public void put(String key,Object value){
		cubesMap.put(key,value);
	}
	/**
	 * 取出
	 */
	public Object get(String key){
		return cubesMap.get(key);
	}
	
	/**
	 * json 信息
	 * @param json
	 * @return
	 */
	public ArrayList<CubeInfo> getCubesInfo(String json){
		ArrayList<CubeInfo> cubesList = new ArrayList<>();
		try {
			if (StringUtils.isBlank(json)){
				return cubesList;
			}
			//Json的解析类对象
			JsonParser parser = new JsonParser();
			//将JSON的String 转成一个JsonArray对象
			JsonArray jsonArray = parser.parse(json).getAsJsonArray();
			Gson gson = new Gson();
			//加强for循环遍历JsonArray
			for (JsonElement element : jsonArray) {
				//使用GSON，直接转成Bean对象
				CubeInfo info = gson.fromJson(element, CubeInfo.class);
				if (info!=null && info.getInput_records_count()>0 && info.getInput_records_size()>0){
					setSchem(info);
					cubesList.add(info);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return cubesList;
	}
	
	/**
	 * 获得schem信息
	 * @param info
	 * @return
	 */
	private void setSchem(CubeInfo info){
		Map<String,String> schemMap = info.getSegments()[0].getDictionaries();
		for (Map.Entry<String,String> entry : schemMap.entrySet()) {
			// result="/dict/USE_AND_DROP.CREDIT_RISK_DATA/TERM/108d18c5-ed99-10e5-16d5-572827fc3e4d.dict"
			String tmpStr = entry.getValue();
			String[] values = tmpStr.split("/");
			String schem = values[2];
			String[] finals = schem.split("\\.");
			info.setTable_SCHEM(finals[0]);
			info.setTable_Name(finals[1]);
			break;
		}
	}

	/**
	 * json 信息
	 * @param json
	 * @return
	 */
	public List<CubeDescInfo> getCubeDescInfo(String json, CubeInfo info){
		ArrayList<CubeDescInfo> cubeDescList = new ArrayList<>();
		try {
			if (StringUtils.isBlank(json)){
				return cubeDescList;
			}
			//Json的解析类对象
			JsonParser parser = new JsonParser();
			//将JSON的String 转成一个JsonArray对象
			JsonArray jsonArray = parser.parse(json).getAsJsonArray();
			Gson gson = new Gson();
			//加强for循环遍历JsonArray
			for (JsonElement element : jsonArray) {
				//使用GSON，直接转成Bean对象
				CubeDescInfo descInfo = gson.fromJson(element, CubeDescInfo.class);
				descInfo.setProject(info.getProject());
				descInfo.setSchem_name(info.getTable_SCHEM());
				descInfo.setTable_name(info.getTable_Name());
				getExecuteSql(descInfo);
				cubeDescList.add(descInfo);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return cubeDescList;
	}
	
	/**
	 * json 信息
	 * @param cubeDescInfo
	 * @return
	 */
	public void getExecuteSql(CubeDescInfo cubeDescInfo){
		String table_schem = cubeDescInfo.getSchem_name();
		StringBuffer buffer = new StringBuffer("SELECT "+cubeDescInfo.toColumns()+" , ");
		List<Measures> measuress = cubeDescInfo.getMeasures();
		if (measuress!=null && measuress.size()>0){
			String tableName = cubeDescInfo.getDimensions().get(0).getTable();
			for (int i = 0; i <measuress.size() ; i++) {
				Measures measures = measuress.get(i);
				Measures.Function instance = measures.getFunction();
				Measures.Parameter parameter = instance.getParameter();
				if (parameter.getType().equalsIgnoreCase(KylinConstant.MEASURES_CONST_TYPE)){
					buffer.append(instance.getExpression()+"("+parameter.getValue()+") AS "+ measures.getName());
				}else {
					if (instance.getExpression().equalsIgnoreCase(KylinConstant.PERCENTILE_APPROX)){
						buffer.append(instance.getExpression()+"("+parameter.getValue().replace(tableName,"").replace(".","")+","+KylinConstant.PERCENTILE_APPROX_VALUE+") AS "+ measures.getName());
					}else{
						buffer.append(instance.getExpression()+"("+parameter.getValue().replace(tableName,"").replace(".","")+") AS "+ measures.getName());
					}
				}
				if (i!=measuress.size()-1){
					buffer.append(",");
				}
			}
			if (table_schem==null){
				buffer.append(" FROM "+tableName);
			}else{
				buffer.append(" FROM "+table_schem+"."+tableName);
			}
			cubeDescInfo.setFromsql(buffer.toString());
			String groupBy = " GROUP BY "+cubeDescInfo.toColumns();
			cubeDescInfo.setGroupby(groupBy);
			buffer.append(groupBy);
			cubeDescInfo.setSql(buffer.toString());
			whereIs(cubeDescInfo,groupBy);
		}
	}
	
	/**
	 *
	 * @param cubeDescInfo
	 */
	private void whereIs(CubeDescInfo cubeDescInfo,String groupBy) {
		String sql = "SELECT PROCESSING_DTTM FROM "+cubeDescInfo.getSchem_name()+"."+cubeDescInfo.getTable_name()+groupBy+" ORDER BY PROCESSING_DTTM DESC LIMIT 1 ";
		cubeDescInfo.setWhereis(sql);
	}
}
