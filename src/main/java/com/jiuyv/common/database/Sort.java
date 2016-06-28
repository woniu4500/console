package com.jiuyv.common.database;


/**
 * 排序字段.
 */
public class Sort {

	/** 排序字段. */
	private String sort ;
	
	/** 升序 降序. */
	private String dir = "ASC";
	
	/**
	 * Instantiates a new sort.
	 */
	public Sort () {}

	/**
	 * Instantiates a new sort.
	 *
	 * @param sort the sort
	 * @param dir the dir
	 */
	public Sort(String sort, String dir) {
		super();
		this.sort = sort;
		this.dir = dir;
	}

	/**
	 * 获取 排序字段.
	 *
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * 设置 排序字段.
	 *
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * 获取 升序 降序.
	 *
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * 设置 升序 降序.
	 *
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
	
}
