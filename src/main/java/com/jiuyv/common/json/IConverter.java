/*
 * 
 */
package com.jiuyv.common.json;

/**
 * @version 0.1
 * @author  The Interface IConverter. 转换接口，用于拦截其中进行对象转换。
 */
public interface IConverter {

	/**
	 * Convert from attr object.
	 * 
	 * @param attrObj
	 *            the attr obj 源物件
	 * 
	 * @return the object 目标物件
	 */
	Object convertFromAttrObject(Object attrObj);

}
