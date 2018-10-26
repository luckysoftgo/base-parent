package com.application.base.common.page;

import java.util.List;

/**
 * @desc 分页的信息展示
 * @author 孤狼
 * @param <T>
 */
public class Pagination<T> {
	
	/**
	 * 当前页码
	 */
	private Integer pageNo = 1;
	/**
	 * 每页条数
	 */
	private Integer pageSize = 10;
	/**
	 * 总页数
	 */
	private Integer pageCount = 1;
	/**
	 * 总条数据.
	 */
	private Integer rowCount = 0;
	/**
	 * 数据体
	 */
	private List<T> data;

	public Pagination() {
		
	}

	/**
	 * 使用构造函数,强制必需输入
	 * 当前页
	 * @param pageNo　当前页
	 */
	public Pagination(Integer pageNo){
		this.pageNo = pageNo;
	}
	
	public Pagination(Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Pagination(List<T> data, Integer pageNo, Integer pageSize) {
		this.data = data;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Pagination(List<T> data, Integer pageNo) {
		this.data = data;
		this.pageNo = pageNo;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getRowCount() {
		return rowCount;
	}
	
	/**
	 * 设置总共有多少页数据信息.
	 * @param rowCount
	 */
	public void setRowCount(Integer rowCount) {
		setPageCount(rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize+1 );
		this.rowCount = rowCount;
	}
	
}