package com.jiuyv.common.web.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;


/**
 * JSON转换工具.
 *

 * @author caoyunke
 * @version 1.0
 */
public abstract class JsonUtil {

	/** Json转换过滤配置. */
	private static final JsonConfig CONFIG = new JsonConfig();

	static {
		// 过滤 空属性
		CONFIG.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (value instanceof String
						&& "".equals(String.valueOf(value).trim())) {
					return true;
				}
				return false;
			}
		});
	}
	
	/**
	 * 从字符串中获取对象
	 * request.getParameter(paramName)
	 *
	 * @param <T> the generic type
	 * @param jsonObjectStr the json object str
	 * @param targetClass 目标类型
	 * @return the T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T objectFromString(String jsonObjectStr, Class<T> targetClass ) {
		// Empty check  
		if ( StringUtils.isNotBlank(jsonObjectStr) ) {
			JSONObject obj = JSONObject.fromObject(jsonObjectStr, CONFIG);
			if ( obj == null ) {
				// Return null 
				return null ; 
			} 
			// Convert to target class
			return (T) JSONObject.toBean(obj, targetClass);
		}
		// Return null when input string is blank. 
		return null ; 
	}
	
	/**
	 * 从字符串转换为目标列表.
	 *
	 * @param <T> the generic type
	 * @param jsonArrayStr  待转换字符串
	 * @param targetClass 目标类型
	 * @return the list< t>
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> listFromString(String jsonArrayStr, Class<T> targetClass) {
		// Empty check  
		if ( StringUtils.isNotBlank(jsonArrayStr) ) {
			JSONArray arr = JSONArray.fromObject(jsonArrayStr, CONFIG);
			if (arr == null) {
				// If no elements is define in list, return null. 
				return null;
			}
			// Convert to target class list. 
			return new ArrayList<T>(JSONArray.toCollection(arr, targetClass));
		}
		// If jsonArrayStr is empty, return null. 
		return null ; 
	}

}
