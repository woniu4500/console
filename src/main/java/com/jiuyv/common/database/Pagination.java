package com.jiuyv.common.database;

import java.io.Serializable;

/**
 * 查询分页信息.
 *

 * @author 
 * @since 2013-12-20 16:44:38
 * @version 1.0.0
 */
public class Pagination implements Serializable {

	/**  */
	private static final long serialVersionUID = -5241676915542436164L;

	/** 每页记录数. */
	private Long limit ; 
	
	/** 偏移量 起始记录. */
	private Long offset ;
	
	/** 结束记录. */
	private Long endline ; 

	/**
	 * 获取 结束记录.
	 *
	 * @return the 结束记录
	 */
	public Long getEndline() {
		return endline;
	}

	/**
	 * 设置 结束记录.
	 *
	 * @param endline the new 结束记录
	 */
	public void setEndline(Long endline) {
		this.endline = endline;
	}

	/**
	 * 获取 每页记录数.
	 *
	 * @return the limit
	 */
	public Long getLimit() {
		return limit;
	}

	/**
	 * Instantiates a new pagination.
	 *
	 * @param limit the limit
	 * @param offset the offset
	 */
	public Pagination(Long limit, Long offset) {
		super();
		this.limit = limit;
		this.offset = offset;
		calc();
	}

	/**
	 * Calc.
	 * 
	 * Add at {diff}, for Oracle: this.offset = this.offset + 1;
	 */
	private void calc(){
		if ( limit != null && offset != null ) {
			if(offset==0){
				this.endline = this.offset + this.limit;
			}else{
				// diff
				offset += 1;
				this.endline = this.offset + this.limit - 1 ;
			}
		}
	}
	
	/**
	 * Instantiates a new pagination.
	 */
	public Pagination() {
		super();
	}

	/**
	 * 设置 每页记录数.
	 *
	 * @param limit the limit to set
	 */
	public void setLimit(Long limit) {
		this.limit = limit;
		calc();
	}

	/**
	 * 获取 偏移量 起始记录.
	 *
	 * @return the offset
	 */
	public Long getOffset() {
		return offset;
	}

	/**
	 * 设置 偏移量 起始记录.
	 *
	 * @param offset the offset to set
	 */
	public void setOffset(Long offset) {
		this.offset = offset;
		calc();
	} 
	
}
