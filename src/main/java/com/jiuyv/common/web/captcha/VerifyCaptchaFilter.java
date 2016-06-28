package com.jiuyv.common.web.captcha;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * The Class VerifyCaptchaFilter.
 *
 * @author 
 * @since 2013-12-19 15:50:11
 * @version 1.0.0
 */
public class VerifyCaptchaFilter implements Filter {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(VerifyCaptchaFilter.class);
	
	/** The captcha field. */
	private String captchaField = "j_captcha";
	
	/** The FL d_ logi n_ error. */
	public static final String FLD_LOGIN_ERROR = "ERROR_COUNT" ;
	
	/** The REDIREC t_ url. */
	public static final String REDIRECT_URL = "logfail.action?errorCode=3";
	
	/** The login endurance. */
	private Integer loginEndurance = 3; 
	
	/** The captcha service. */
	private CaptchaService captchaService ; 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() { }

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response ,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		Integer errCnt = (Integer) session.getAttribute(FLD_LOGIN_ERROR);
		if ( errCnt == null || errCnt < this.loginEndurance ) {
			chain.doFilter(request, response);
		} else {
			String captcha = req.getParameter(captchaField);
			if ( validate(session.getId(), captcha) ) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse)response).sendRedirect(REDIRECT_URL);
			}
		}
	}

	/**
	 * Validate.
	 *
	 * @param sessionId the session id
	 * @param captcha the captcha
	 * @return true, if successful
	 */
	private boolean validate(String sessionId, String captcha){
		try {
			return captchaService.validateResponseForID(sessionId, captcha);
		} catch(CaptchaServiceException e) {
			return false; 
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		LOG.debug("Init VerifyCaptchaFilter ...");
	}

	/**
	 * Sets the login endurance.
	 *
	 * @param loginEndurance the new login endurance
	 */
	public void setLoginEndurance(Integer loginEndurance) {
		this.loginEndurance = loginEndurance;
	}

	/**
	 * Sets the captcha field.
	 *
	 * @param captchaField the new captcha field
	 */
	public void setCaptchaField(String captchaField) {
		this.captchaField = captchaField;
	}

	/**
	 * Sets the captcha service.
	 *
	 * @param captchaService the new captcha service
	 */
	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

}
