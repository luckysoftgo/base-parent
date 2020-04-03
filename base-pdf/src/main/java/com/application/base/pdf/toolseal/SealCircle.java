package com.application.base.pdf.toolseal;

import lombok.Builder;
import lombok.Data;

/**
 * @author : 孤狼
 * @NAME: SealCircle
 * @DESC: SealCircle类设计
 **/
@Data
@Builder
public class SealCircle {
	/**
	 * 线
	 */
	private Integer line;
	/**
	 * 宽
	 */
	private Integer width;
	/**
	 * 高
	 */
	private Integer height;
	
}
