package com.jiuyv.common.database.criteria;

import java.util.List;

/**
 * The Class ListCondition.
 *
 * @param <T> the generic type

 * @author 
 * @since 2013-12-25 9:41:10
 * @version 1.0.0
 */
public class ListCondition<T> {

	/** 条件. */
	private String condition ; 
	
	/** 参数. */
	private List<T> values;
	
	/** 固定字符串参数. */
	private String strValue ;

	/**
	 * 获取 条件.
	 *
	 * @return the 条件
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * 设置 条件.
	 *
	 * @param condition the new 条件
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 获取 参数.
	 *
	 * @return the 参数
	 */
	public List<T> getValues() {
		return values;
	}

	/**
	 * 设置 参数.
	 *
	 * @param values the new 参数
	 */
	public void setValues(List<T> values) {
		this.values = values;
	}

	/**
	 * 获取 固定字符串参数.
	 *
	 * @return the 固定字符串参数
	 */
	public String getStrValue() {
		return strValue;
	}

	/**
	 * 设置 固定字符串参数.
	 *
	 * @param strValue the new 固定字符串参数
	 */
	public void setStrValue(String strValue) {
		this.strValue = strValue;
	} 
	
}
