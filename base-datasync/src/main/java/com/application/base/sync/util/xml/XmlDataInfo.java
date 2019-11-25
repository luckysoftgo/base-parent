package com.application.base.sync.util.xml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 孤狼
 * @NAME: XmlDataInfo
 * @DESC: 存放数据的类.
 **/
@Data
@NoArgsConstructor
public class XmlDataInfo {
	
	/**
	 * 唯一标识数据源的id
	 */
	private String settingId;
	
	/**
	 * 数据请求的地址.
	 */
	private OrginInfo orginInfo;
	
	/**
	 * 数据的目标源
	 */
	private DestDbInfo destDbInfo;
	
	/**
	 * 表的设置.
	 */
	private List<TableInfo> tableInfos;
	
	/**
	 * 外部扩展.
	 */
	private List<ExtendInfo> extendInfos;

}
