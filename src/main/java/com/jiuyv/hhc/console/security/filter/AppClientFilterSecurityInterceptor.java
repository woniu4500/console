package com.jiuyv.hhc.console.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class AppClientFilterSecurityInterceptor extends
		FilterSecurityInterceptor {
	
	private static final String XFLAG = "app-client-flag";

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request ;
		// 在session中设置application client 标志位
		String xflag = req.getHeader(XFLAG);
		if ( StringUtils.isNotBlank(xflag) ) {
			req.getSession().setAttribute(XFLAG, xflag);
		}
		FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
	}

}
