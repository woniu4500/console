/*
 * 
 */
package com.jiuyv.hhc.console.basic.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.common.json.IConverter;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;

/**
 * @version 0.1
 * @author  <h2>AbstractConvertInterceptor.</h2>
 *         <p>
 *         This class convert the attribute in request scope and store the
 *         result after convertion into request.<br />
 *         It needs at least 2 parameter which called 'sourceAttrName' and
 *         'converter' to complete the process of convert. It also provide the
 *         target attribute name .<br />
 *         After you called this interceptor, find the result with:<br />
 *         &nbsp;&nbsp;request.getAttibute(&lt;targetAttrName&gt;);<br />
 *         or use EL to get it.
 *         </p>
 */
public class AbstractConvertInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1930877566194337899L;

	/**
	 * Logger for this class.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractConvertInterceptor.class);

	/** The source attr name. */
	private String sourceAttrName;

	/** The target attr name. */
	private String targetAttrName;

	/** The converter. */
	private IConverter converter;

	/**
	 * Gets the source attr name.
	 * 
	 * @return the source attr name
	 */
	public String getSourceAttrName() {
		return sourceAttrName;
	}

	/**
	 * Sets the source attr name.
	 * 
	 * @param sourceAttrName
	 *            the new source attr name
	 */
	public void setSourceAttrName(String sourceAttrName) {
		this.sourceAttrName = sourceAttrName;
	}

	/**
	 * Gets the target attr name.
	 * 
	 * @return the target attr name
	 */
	public String getTargetAttrName() {
		return targetAttrName;
	}

	/**
	 * Sets the target attr name.
	 * 
	 * @param targetAttrName
	 *            the new target attr name
	 */
	public void setTargetAttrName(String targetAttrName) {
		this.targetAttrName = targetAttrName;
	}

	/**
	 * Gets the converter.
	 * 
	 * @return the converter
	 */
	public IConverter getConverter() {
		return converter;
	}

	/**
	 * Sets the converter.
	 * 
	 * @param converter
	 *            the new converter
	 */
	public void setConverter(IConverter converter) {
		this.converter = converter;
	}

	/**
	 * Called in intercept stack or reference by single action.
	 * 
	 * @param invocation
	 *            the invocation
	 * @return result string
	 * @throws Exception
	 *             the exception
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("intercept - start");
		}

		if (!initInterceptor()) {
			String returnString = invocation.invoke();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("intercept - end");
			}
			return returnString;
		}
		invocation.addPreResultListener(new PreResultListener() {
			public void beforeResult(ActionInvocation invocation, String action) {
				LOGGER.debug("beforeResult(ActionInvocation, String) - start");
				// get http request. 得到Http request对象
				HttpServletRequest request = ServletActionContext.getRequest();
				// get object in request 从源物件域获得对象
				Object attrObj = request.getAttribute(sourceAttrName);
				// convert it and put it into request 转换对象
				Object tarObj = converter.convertFromAttrObject(attrObj);
				// 将对象放入目标域中
				request.setAttribute(targetAttrName, tarObj);
				LOGGER.debug("beforeResult(ActionInvocation, String) - end");
			}
		});
		String returnString = invocation.invoke();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("intercept(ActionInvocation) - end");
		}
		return returnString;
	}

	/**
	 * Inits the interceptor.<br />
	 * 初始化拦截器，检测域的赋值，决定是否作转换操作。 返回为false时不进行转换操作。<br/>
	 * 转换规则：<br />
	 * <ol>
	 * <li>converter 对象为空时 返回false 不需要加监听器</li>
	 * <li>源物件名称 为空时，返回false 不需要加监听器</li>
	 * <li>物件名称都指定时 返回true</li>
	 * <li>目标物件未指定时 默认目标物件名和源物件名相同</li>
	 * </ol>
	 * 
	 * @return true, if successful
	 */
	protected boolean initInterceptor() {
		// converter 对象为空时 返回false 不需要加监听器
		if (converter == null) {
			return false;
		}
		// 源物件名称 为空时，返回false 不需要加监听器
		if (sourceAttrName == null) {
			return false;
		}
		// 物件名称都指定时 返回true
		if (targetAttrName != null) {
			return true;
		} else {
			// 目标物件未指定时 默认目标物件名和源物件名相同
			targetAttrName = sourceAttrName;
			return true;
		}
	}

}
