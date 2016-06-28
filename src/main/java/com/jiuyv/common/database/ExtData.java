package com.jiuyv.common.database;

import java.util.List;


/**
 * 
 * 分页结果信息
 * 
 * @param <T> 记录类型
 */
public class ExtData<T> {

	/** 操作是否成功. */
	private boolean success;

	/** 错误信息. */
	private String syserr;
	
	/** 总记录数. */
	private long totalProperty;
	
	/** 结果列表 */
	private List<T> root;

	/** 记录内容. */

	/**
	 * Instantiates a new ext data.
	 */
	public ExtData() {
		
	}

	/**
	 * Instantiates a new ext data.
	 *
	 * @param status the status
	 * @param success the success
	 * @param totalProperty the total property
	 * @param syserr the syserr
	 * @param root the root
	 */
	public ExtData(boolean success, int totalProperty,
			String syserr, List<T> root) {
		super();
		this.success = success;
		this.totalProperty = totalProperty;
		this.syserr = syserr;
		this.root = root;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getSyserr() {
		return syserr;
	}

	public void setSyserr(String syserr) {
		this.syserr = syserr;
	}

	public long getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(long totalProperty) {
		this.totalProperty = totalProperty;
	}

	public List<T> getRoot() {
		return root;
	}

	public void setRoot(List<T> root) {
		this.root = root;
	}


}
