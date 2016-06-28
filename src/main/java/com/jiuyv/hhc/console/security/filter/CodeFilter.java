package com.jiuyv.hhc.console.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * The Class CodeFilter.
 *

 * @author 
 * @since 2014-1-2 16:38:53
 * @version 1.0.0
 */
public class CodeFilter implements Filter {

	/** The Constant URL. */
	private static final String URL_CODE_ERR = "logCodeFail.action?errorCode=1";
	private static final String URL_CODE_NVL = "logCodeFail.action?errorCode=2";

	/** The Constant ERROR_COUNT. */
	private static final String ERROR_COUNT = "errorCount";
	/**  */
	private Integer endurance = -1;

	/**
	 * @param servletRequest
	 * @param servletResponse
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Integer errorCount = (Integer) request.getSession().getAttribute(
				ERROR_COUNT);
		
		String flag = request.getParameter("flag");
		
		// 登入错误小于3次，不进行验证码效验
		if (endurance < 0 || errorCount == null || errorCount < endurance || (StringUtils.equals(flag, "mobile"))) {
			filterChain.doFilter(request, response);
		} else {
			String code = request.getParameter("j_captcha");
			String codes = (String) request.getSession().getAttribute("code");
			if (!"".equals(code) && code != null && !"".equals(codes)) {
				if (code.equalsIgnoreCase(codes)) {
					filterChain.doFilter(request, response);
				} else {
					response.sendRedirect(URL_CODE_ERR);
				}
			} else {
				response.sendRedirect(URL_CODE_NVL);
			}
		}
	}

	/**
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @param arg0
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	/**
	 * @param endurance the endurance to set
	 */
	public void setEndurance(Integer endurance) {
		this.endurance = endurance;
	}

}
