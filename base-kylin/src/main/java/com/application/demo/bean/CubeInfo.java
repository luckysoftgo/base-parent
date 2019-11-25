package com.application.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: CubeInfo
 * @DESC: cube 的主要信息.
 **/
@Data
@AllArgsConstructor
public class CubeInfo implements Serializable {
	/**
	 * 项目名称
	 */
	private String project;
	/**
	 * cube的名称
	 */
	private String name;
	/**
	 * 介绍
	 */
	private String descriptor;
	/**
	 * 模型名称
	 */
	private String model;
	/**
	 * 创建时间
	 */
	private Long create_time_utc;
	/**
	 * 总条数.
	 */
	private Integer input_records_count;
	/**
	 * 总大小.
	 */
	private Integer input_records_size;
	/**
	 * segments信息
	 */
	private Segment[] segments;
	/**
	 * 数据库名称.
	 */
	private String table_SCHEM;
	/**
	 * 表名称.
	 */
	private String table_Name;
}
