package com.jiuyv.hhc.console.security.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.hhc.console.security.service.ISecurityService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.console.security.util.ResourceCache;
import com.jiuyv.hhc.model.security.LogInfo;
import com.jiuyv.hhc.model.security.SysResourceVo;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;

/**
 * 用于往数据库记录系统日志。
 * 
 * 
 */
public class DBLogInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6376477163174079911L;

	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DBLogInterceptor.class);

	private transient ISecurityService securityService ; 
	
	/** The resource cache. */
	private transient ResourceCache resourceCache;

	/**
	 * Sets the resource cache.
	 * 
	 * @param resourceCache
	 *            the new resource cache
	 */
	public void setResourceCache(ResourceCache resourceCache) {
		this.resourceCache = resourceCache;
	}

	/**
	 * 
	 * @param securityService
	 */
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com
	 * .opensymphony.xwork2.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// 判断是否需要进行日志记录 这个值在此次请求中不会改变
		final boolean needLog = DBLogUtil.needLogInDB(resourceCache);

		if (needLog) {
			DBLogUtil.setLogInfo(new LogInfo());
		}

		// 添加记录日志的监听器
		invocation.addPreResultListener(new PreResultListener() {
			public void beforeResult(ActionInvocation invocation, String action) {
				// return when not need to log
				if (!needLog) {
					LOGGER.debug("Not record any log in database.");
					return;
				}
				// the last action in the chain
				if (!DBLogUtil.isForward2Page(action)) {
					LOGGER.debug("Not record current action in the whole action chain.");
					return;
				}
				HttpServletRequest request = ServletActionContext.getRequest();
				// get resource
				String actionUrl = request.getServletPath().replaceFirst("/","");
				Map<String, SysResourceVo> resMap = resourceCache.getUrlResMap();
				SysResourceVo resource = resMap.get(actionUrl);
				// get exception message
				String exception = (String) request.getAttribute("exception");
				// record log in database
				securityService.logRecord(DBLogUtil.generateLog(resource.getResId(),resource.getResZh(), exception, null)) ;
			}
		});
		// invoke action chain
		return invocation.invoke();
	}






}
