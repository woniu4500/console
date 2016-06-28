package com.jiuyv.hhc.console.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.jiuyv.common.web.util.WebRequestUtil;

public class MngrLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if ( WebRequestUtil.isAppClient(request) ) {
			response.sendRedirect("page/common/clientLogout.jsp");
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
