package com.jiuyv.hhc.console.security.action;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.jiuyv.common.web.util.WebRequestUtil;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;

/**
 * Exception process action.
 *

 * @author 
 * @since 2014-1-3 16:28:14
 * @version 1.0.0
 */
public class ExceptionHandleAction extends DefaultPageSupportAction {

	/** 目标结果 ajax请求. */
	private static final String ERR_AJAX = "errAjax";

	/** 目标结果 页面请求. */
	private static final String ERR_PAGE = "errPage";

	/**
	 * 对异常出错信息进行转发.
	 *
	 * @return the string
	 */
	public String exceptionForward() {
		if (WebRequestUtil.isAjax(null) || WebRequestUtil.isAppClient(null)) {
			return ERR_AJAX;
		}
		// reset the system error message
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonStr = (String) request.getAttribute("exception");
		if (jsonStr != null) {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			request.setAttribute("exceptionMsg", jsonObj.get("syserr"));
		}
		return ERR_PAGE;
	}

}
