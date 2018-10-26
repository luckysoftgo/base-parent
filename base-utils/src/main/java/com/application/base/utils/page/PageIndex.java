
package com.application.base.utils.page;

import java.io.Serializable;

/**
 * @desc 用来显示分页的下标的
 * @author 孤狼
 */
public class PageIndex  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 *开始索引
	 */
	private long startIndex;
	/**
	 * 结束索引
	 */
	private long endIndex;
	
	
	public PageIndex(long startIndex, long endIndex) {
		super();
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public long getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}
	
	public long getEndIndex() {
		return endIndex;
	}
	
	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}
	
}