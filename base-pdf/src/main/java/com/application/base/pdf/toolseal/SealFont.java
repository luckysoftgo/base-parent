package com.application.base.pdf.toolseal;

import lombok.Builder;
import lombok.Data;

/**
 * @author : 孤狼
 * @NAME: SealFont
 * @DESC: SealFont类设计
 **/
@Data
@Builder
public class SealFont {
	/**
	 * 文本
	 */
	private String text;
	/**
	 * 字体
	 */
	private String family;
	/**
	 * 大小
	 */
	private Integer size;
	/**
	 * bold
	 */
	private Boolean bold;
	/**
	 * space
	 */
	private Double space;
	/**
	 * margin
	 */
	private Integer margin;
	
	public String getFamily() {
		return family == null ? "宋体" : family;
	}
	
	public boolean getBold() {
		return bold == null ? true : bold;
	}
	
	public SealFont append(String text) {
		this.text += text;
		return this;
	}

}
