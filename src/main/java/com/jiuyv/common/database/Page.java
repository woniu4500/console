package com.jiuyv.common.database;


/**
 * 查询所带的分页信息。 The Class Page.
 *

 * @author 
 * @since 2013-12-20 16:27:37
 * @version 1.0.0
 */
public class Page {

	/** The Constant FOR_EXPORT. */
	public static final String FOR_EXPORT = "excel";
	
	/** 开始. */
	private String start;

	/** 每页记录. */
	private String limit;

	/** 升序 降序. */
	private String dir;

	/** 排序域指定. */
	private String sort;

	/**
	 * Instantiates a new page.
	 */
	public Page() {
		super();
	}

	/**
	 * Instantiates a new page.
	 *
	 * @param sort: 排序字段 
	 */
	public Page( String sort ) {
		this("ASC", sort);
	}
	
	/**
	 * Instantiates a new page.
	 *
	 * @param dir: ASC or DESC
	 * @param sort: 排序字段
	 */
	public Page(String dir, String sort) {
		super();
		this.dir = dir;
		this.sort = sort;
	}

	/**
	 * Instantiates a new page.
	 * 
	 * @param start: 开始记录数
	 * @param limit: 每页记录数
	 * @param sort: 排序字段
	 */
	public Page(String start, String limit, String sort) {
		super();
		this.start = start;
		this.limit = limit;
		this.sort = sort;
	}

	/**
	 * Instantiates a new page.
	 * 
	 * @param start: 开始记录数
	 * @param limit: 每页记录数
	 * @param sort: 排序字段
	 * @param dir: ASC or DESC
	 */
	public Page(String start, String limit, String sort, String dir) {
		super();
		this.start = start;
		this.limit = limit;
		this.sort = sort;
		this.dir = dir;
	}

	/**
	 * 获取 开始.
	 *
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * 设置 开始.
	 *
	 * @param start the new start
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * 获取 每页记录.
	 *
	 * @return the limit
	 */
	public String getLimit() {
		return limit;
	}

	/**
	 * 设置 每页记录.
	 *
	 * @param limit the new limit
	 */
	public void setLimit(String limit) {
		this.limit = limit;
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
	 * @param dir the new dir
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * 获取 排序域指定.
	 *
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * 设置 排序域指定.
	 *
	 * @param sort the new sort
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
}
