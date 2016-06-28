package com.jiuyv.common.template;

import java.util.Map;

/**
 * The Interface RenderAfter.
 *

 * @author 
 * @since 2014-5-26 12:03:24
 * @version 1.0.0
 */
public interface RenderAfter {

	/**
	 * After.
	 *
	 * @param propName the prop name
	 * @param propValue the prop value
	 * @param namePattern the name pattern
	 * @param storeMap the store map
	 */
	void after(String propName, String propValue, String namePattern, Map<String, String> storeMap);

}
