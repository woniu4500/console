package com.jiuyv.hhc.model.security.rpt;

/**
 * The Class RptFieldBean.
 * 报表bean

 * @author 
 * @since 2014-1-21 10:46:04
 * @version 1.0.0
 */
public class RptFieldBean {
	
	/** 名称. */
	private String fldName;
	
	/** 值. */
	private Long fldValue;
	
	/**
	 * Instantiates a new rpt field bean.
	 */
	public RptFieldBean(){}
	
	/**
	 * Instantiates a new rpt field bean.
	 *
	 * @param fldName the fld name
	 * @param fldValue the fld value
	 */
	public RptFieldBean(String fldName, Long fldValue) {
		super();
		this.fldName = fldName;
		this.fldValue = fldValue;
	}

	/**
	 * 获取 名称.
	 *
	 * @return the 名称
	 */
	public String getFldName() {
		return fldName;
	}

	/**
	 * 设置 名称.
	 *
	 * @param fldName the new 名称
	 */
	public void setFldName(String fldName) {
		this.fldName = fldName;
	}

	/**
	 * 获取 值.
	 *
	 * @return the 值
	 */
	public Long getFldValue() {
		return fldValue;
	}

	/**
	 * 设置 值.
	 *
	 * @param fldValue the new 值
	 */
	public void setFldValue(Long fldValue) {
		this.fldValue = fldValue;
	}

}
