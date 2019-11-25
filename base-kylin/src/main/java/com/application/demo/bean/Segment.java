package com.application.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : 孤狼 .
 * @NAME: Segment .
 * @DESC: segments 信息 .
 **/
@Data
@AllArgsConstructor
public class Segment implements Serializable {
	
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 条数
	 */
	private Long input_records;
	/**
	 * 条数大小
	 */
	private Long input_records_size;
	/**
	 * 时间戳
	 */
	private Long create_time_utc;
	/**
	 * 字典信息
	 */
	private Map<String,String> dictionaries;
}
