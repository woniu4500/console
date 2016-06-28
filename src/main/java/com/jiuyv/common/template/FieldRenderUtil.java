package com.jiuyv.common.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class FieldRenderUtil.
 *

 * @author 
 * @since 2014-5-26 11:25:26
 * @version 1.0.0
 */
public class FieldRenderUtil {
	
	/** Logger for this class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FieldRenderUtil.class);

	private static final String FMT_PATTERN = "$(%s)";
	
	/**
	 * 
	 * 一个公共的用于将简单的bean中的值转化为map的方法。 简单bean说明：bean中只包含基本对象，并且含有get方法。
	 * 不会对bean中的list，Map，这些对象赋值。.
	 *
	 * @param <T> the generic type
	 * @param t the t
	 * @param paraMap the para map
	 * @return the map
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, String> renderTemplateParam(T bean,
			Map<String, ERender> rmap) throws Exception {
		Map<String, String> tplParam = new HashMap<String, String>();
		if ( bean == null ) {
			LOGGER.error("Convert Bean is null");
			return tplParam;
		}
		Map<String, ERender> renderMap = rmap == null ? new HashMap<String, ERender>():rmap ; 
		Map<String,String> propMap = BeanUtils.describe(bean);
		propMap.remove("class");
		for (Entry<String,String> entry: propMap.entrySet()) {
			String propName = entry.getKey();
			String propValue = StringUtils.trimToEmpty(entry.getValue());
			ERender render = renderMap.get(propName);
			String renderValue = render == null ? propValue:render.render(propValue);
			tplParam.put(String.format(FMT_PATTERN, propName), renderValue);
			if ( render != null && render.getType() instanceof RenderAfter ) {
				((RenderAfter)render.getType()).after(propName, propValue, FMT_PATTERN, tplParam);
			}
		}
		return tplParam;
	}

}
