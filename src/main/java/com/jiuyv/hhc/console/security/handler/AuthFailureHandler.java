package com.jiuyv.hhc.console.security.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jiuyv.hhc.console.security.filter.LoginFilter;
import com.jiuyv.hhc.console.security.service.ISecurityService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;

/**
 * 默认的密码错误
 * 
 * 
 * @author
 * 
 */
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	/** 普通登录URL */
	public static final String LOGIN_URL_KEY = "NORMAL_LOGIN_URL";
	/** CAPTCHA 登录 */
	public static final String LOGIN_CD_URL_KEY = "CAPTCHA_LOGIN_URL";
	/** Login */
	public static final String APP_LOGIN_URL = "APP_LOGIN_URL";
	
	public static final String LAST_USERNAME = "SPRING_SECURITY_LAST_USERNAME";
	
	public static final String ERROR_COUNT_KEY = "errorCount";
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/** 表单用户域 */
	private String formUsernameKey = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

	/**  */
	private ISecurityService securityService ; 
	
	/** 路径url Map */
	private Map<String,String> urlMap ;
	
	/** 可容忍的错误次数  */
	private Integer endurance = -1; 
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param exception
	 * @throws IOException
	 * @throws ServletException
	 * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		// We inherited that method:
		saveException(request, exception);
		
		// Prepare URL:
		String username = request.getParameter(formUsernameKey);
		securityService.logRecord(DBLogUtil.generateLog(
				"ROLE_CONNECT", "系统登录", "登录失败：用户名或密码错误",
				DBLogUtil.generateFakeUser(request, username), request));
		HttpSession session = request.getSession();
		// set usename key:  UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY
		session.setAttribute(LAST_USERNAME, username);
		
		Integer errorCount = (Integer) request.getSession().getAttribute(ERROR_COUNT_KEY);
		errorCount = errorCount == null ? 1: errorCount + 1;
		session.setAttribute(ERROR_COUNT_KEY, errorCount);
		// if endurance < 0 then goto login.jsp 
		// else if errorCount >= endurance then goto loginCaptcha.jsp 
		// else then goto login.jsp endif
		String redirectUrl = endurance<0?urlMap.get(LOGIN_URL_KEY):(errorCount >= endurance ? urlMap.get(LOGIN_CD_URL_KEY):urlMap.get(LOGIN_URL_KEY)) ;
		String clientType = (String) session.getAttribute(LoginFilter.S_FLAG);
		if (StringUtils.isNotBlank(clientType) && "mobile".equalsIgnoreCase(clientType)) {
			redirectUrl = urlMap.get(APP_LOGIN_URL);
		}
		// Redirect:
		redirectStrategy.sendRedirect(request, response, redirectUrl);
	}

	/**
	 * @param securityService the securityService to set
	 */
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * @param urlMap the urlMap to set
	 */
	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}

	/**
	 * @param endurace the endurace to set
	 */
	public void setEndurance(Integer endurance) {
		this.endurance = endurance;
	}

}
