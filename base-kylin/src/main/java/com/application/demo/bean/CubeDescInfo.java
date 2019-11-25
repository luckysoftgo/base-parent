package com.application.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: CubeInfo
 * @DESC: cube 的详细信息.
 **/
@Data
@AllArgsConstructor
public class CubeDescInfo implements Serializable {
	/**
	 * 项目名称
	 */
	private String project;
	/**
	 * cube的名称
	 */
	private String name;

	/**
	 * 模型名称
	 */
	private String model_name;

	/**
	 * 表对象信息
	 */
	private List<Dimensions> dimensions;

	/**
	 * 函数集合
	 */
	private List<Measures> measures;
	/**
	 * 表名
	 */
	private String table_name;
	/**
	 * schem名
	 */
	private String schem_name;
	
	/**
	 * 执行的sql语句.
	 */
	private String sql;
	
	/**
	 * 有条件的from
	 */
	private String fromsql;
	
	/**
	 * group by
	 */
	private String groupby;
	
	/**
	 * processing_dttm
	 */
	private String whereis;
	
	/**
	 * 获得列信息
	 * @return
	 */
	public String toColumns(){
		StringBuffer buffer = new StringBuffer();
		int count = dimensions.size();
		for (int i = 0; i <count ; i++) {
			Dimensions info = dimensions.get(i);
			buffer.append(info.getName());
			if (i!=count-1){
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
}
