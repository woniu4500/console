package com.jiuyv.hhc.console.security.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jiuyv.common.json.IConverter;
import com.jiuyv.hhc.console.basic.interceptor.AbstractConvertInterceptor;

/**
 * @version 0.1
 * @author  <h2>The Class DefaultErrorConvertInterceptor.</h2>
 *         <p>
 *         Default error converter interceptor.<br />
 *         This class is used to test in the Struts2 independence.
 *         </p>
 */
@SuppressWarnings("rawtypes")
public class DefaultErrorConvertInterceptor extends AbstractConvertInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2461492026009847791L;

	/**
	 * Initialize the interceptor and check the fields.
	 * 
	 * @return false : if any of the required fields is emtpy.
	 * 
	 */
	protected boolean initInterceptor() {
		setSourceAttrName("errors");
		setConverter(new IConverter() {
			@SuppressWarnings("unchecked")
			public Object convertFromAttrObject(Object attrObj) {
				if (attrObj instanceof Map) {
					Map errors = (Map) attrObj;
					Map retErr = new HashMap();
					for (Iterator iterator = errors.keySet().iterator(); iterator
							.hasNext();) {
						// get the key such as className.fieldName
						String key = String.valueOf(iterator.next());
						Object value = String.valueOf(errors.get(key));
						int tmp = key.indexOf('.');
						if (tmp == -1) {
							tmp = 0;
						} else if (tmp < key.length()) {
							tmp += 1;
						}
						// change the key to fieldName
						retErr.put(key.substring(tmp), value);
					}
					return JSONObject.fromObject(retErr).toString();
				}
				return null;
			}
		});
		return super.initInterceptor();
	}

}
