package com.jiuyv.hhc.console.security.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.web.util.WebRequestUtil;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.security.service.ISecurityService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 根据判断是否ajax请求返回错误页面或错误的json串.
 *

 * @author 
 * @since 2014-1-3 16:27:14
 * @version 1.0.0
 */
public class ErrorAction extends DefaultPageSupportAction {
	
	/** Constant String XMLHTTP_REQUEST: String :. */
	private static final String XMLHTTP_REQUEST = "XMLHttpRequest";
	
	private static final String AJAX = "ajaxSuccess";

	/** Constant String ERROR_COUNT: String :. */
	private static final String ERROR_COUNT = "errorCount";
	
	private static final Integer DEF_ENDURANCE = 3;

	/** The I security service security service. */
	private ISecurityService securityService ; 
	
	/** The String login message. */
	private String loginMessage ; 
	
	/** The Integer endurance. */
	private Integer endurance = DEF_ENDURANCE;
	

	/**
	 * Checks if is ajax.
	 *
	 * @return true, if is ajax
	 */
	private boolean isajax() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestedWith = request.getHeader("x-requested-with");
		// 根据是否有jsonObject 和Filters 或者header头部信息来判别是否ajax请求
		if ((request.getParameter("jsonObject") != null && getFilters() != null)
				|| XMLHTTP_REQUEST.equals(requestedWith)) {
			return true;
		}
		return false;
	}

	/**
	 * Sessionkickoff.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	@SuppressWarnings("rawtypes")
	public String sessionkickoff() throws Exception {
		// 判断不是ajax请求，也不是App请求，返回页面
		if (!isajax() && !WebRequestUtil.isAppClient(null)) {
			return ERROR;
		}
		// 返回json错误信息
		ExtData tempextdata = new ExtData();
		tempextdata.setSuccess(false);
		tempextdata.setSyserr(getText("prompt.sessionExpired"));
		resultData(tempextdata);

		return SUCCESS;
	}

	/**
	 * Logfail.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	@SuppressWarnings("rawtypes")
	public String logfail() throws Exception {
		if (!isajax()) {
			securityService.logRecord(DBLogUtil.generateLog(
					"ROLE_CONNECT", "系统登录", "登录失败：用户名或密码错误",
					DBLogUtil.generateFakeUser(ServletActionContext.getRequest())));

			HttpServletRequest request = (HttpServletRequest) ActionContext
					.getContext().get(ServletActionContext.HTTP_REQUEST);
			HttpSession session = request.getSession();
			Integer errorCount = (Integer) session.getAttribute(ERROR_COUNT);
			// 第一次登入失败，则错误次数赋1
			if (errorCount == null) {
				errorCount = 1;
			} else {
				errorCount++;
			}
			session.setAttribute(ERROR_COUNT, errorCount);

			// 大于3次错误进入随机码登入页面
			if (errorCount >= endurance) {
				return LOGIN;
			}
			return ERROR;
		}
		ExtData tempextdata = new ExtData();
		tempextdata.setSuccess(false);
		tempextdata.setSyserr(getText("prompt.sessionfail"));
		resultData(tempextdata);

		return SUCCESS;
	}

	/**
	 * Log code fail.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String logCodeFail() throws Exception {

		this.loginMessage = "验证码错误";
		return ERROR;
	}
	
	/**
	 * 应用登录失败
	 * @return
	 * @throws Exception
	 */
	public String applogfail() throws Exception {
		securityService.logRecord(DBLogUtil.generateLog("ROLE_CONNECT", "系统登录", "登录失败：用户名或密码错误",
				DBLogUtil.generateFakeUser(ServletActionContext.getRequest())));
		resultData(ExtDataUtil.genWithExceptions("登录失败：用户名或密码错误"));
		return ERROR;
	}
	
	/**
	 * Deal exception.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String dealException() throws Exception {
		if (!isajax()) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * session过期时前往的参数页面
	 * @return
	 * @throws Exception
	 */
	public String invalidSession() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		resultData(ExtDataUtil.genWithExceptions(getText("prompt.sessionfail")));
		if ( WebRequestUtil.isAjax(request)) {
			return AJAX;
		}
		if ( WebRequestUtil.isAppClient(request)) {
			return APP_SUCC;
		}
		return SUCCESS;
	}
	
	/**
	 * Sets the security service.
	 *
	 * @param securityService the new security service
	 */
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * Gets the login message.
	 *
	 * @return the loginMessage
	 */
	public String getLoginMessage() {
		return loginMessage;
	}

	/**
	 * Sets the login message.
	 *
	 * @param loginMessage the loginMessage to set
	 */
	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	/**
	 * Sets the endurance.
	 *
	 * @param endurance the new endurance
	 */
	public void setEndurance(Integer endurance) {
		this.endurance = endurance;
	}
}
