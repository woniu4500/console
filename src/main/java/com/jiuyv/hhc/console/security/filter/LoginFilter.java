package com.jiuyv.hhc.console.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * The Class LoginFilter.
 *

 * @author 
 * @since 2014-1-22 17:04:10
 * @version 1.0.0
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	/** Constant String P_FLAG: String :. */
	private static final String P_FLAG = "flag";
	
	/** Constant String S_FLAG: String :. */
	public static final String S_FLAG = "CLIENT_TYPE";
	
	/** Constant String P_CAPTCHA: String :. */
	private static final String P_CAPTCHA = "j_captcha";
	
	/** Constant String S_CODE: String :. */
	private static final String S_CODE = "code";
	

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws AuthenticationException
	 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		HttpSession session = request.getSession();
		
		String flag = request.getParameter(P_FLAG);
		session.setAttribute(S_FLAG, flag);
		
		String code = request.getParameter(P_CAPTCHA);
		String codes = (String) session.getAttribute(S_CODE);
		session.setAttribute(P_CAPTCHA, code);
		session.setAttribute(S_CODE, codes);
		return super.attemptAuthentication(request, response);
	}

}