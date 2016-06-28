package com.jiuyv.hhc.console.security.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.jiuyv.common.web.util.WebRequestUtil;

/**
 * The Class ClientProxyServlet.
 *

 * @author 
 * @since 2014-3-16 9:13:30
 * @version 1.0.0
 */
public class ClientProxyFilter implements Filter {

	/** Constant String DEFAULT_EMPTY_PATH: String :. */
	private static final String DEFAULT_EMPTY_PATH = "/page/common/cmdError.jsp";

	/** The Map url map. */
	private Map<String, String> urlMap = new HashMap<String, String>();

	/**
	 * 准备请求.
	 *
	 * @param req the req
	 */
	private void prepareRequest(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.setAttribute("CLIENT_TYPE", "mobile");
		session.setAttribute(WebRequestUtil.XFLAG, "mobile");
		
	}
	
	/**
	 * 准备data.
	 *
	 * @param req the req
	 * @return the jSON object
	 */
	private JSONObject prepareData(HttpServletRequest req) {
		String data = req.getParameter("data");
		if ( StringUtils.isBlank(data) ) {
			return null; 
		}
		return JSONObject.fromObject(data);
	}
	
	
	/**
	 * Dispatch path.
	 * 获取转发路径
	 * 
	 * 对特殊交易进行处理
	 * 
	 * @param url the url
	 * @param jsonObject the json object
	 * @return the string
	 */
	@SuppressWarnings("unchecked")
	private String dispatchPath(String url, JSONObject jsonObject) {
		StringBuffer path = new StringBuffer();
		path.append(url).append("?flag=mobile");
		if ( jsonObject == null ) {
			return path.toString();
		}
		Set<String> keys = jsonObject.keySet();
		for(String key : keys) {
			if ( !( "jsonObject".equals(key) || "jsonfilter".equals(key) || "jsonArray".equals(key) ) ) {
				try {
					path.append("&").append(key).append("=").append(URLEncoder.encode(jsonObject.getString(key),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return path.toString();
	}

	
	/**
	 * Inits the.
	 *
	 * @param filterConfig the filter config
	 * @throws ServletException the servlet exception
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	
	/**
	 * Do filter.
	 *
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String content_type = request.getContentType();
		String cmd = req.getParameter("cmd");
		// 对上传文件的请求单独做处理，如果是多个类型需要在struts2内进行二次分发。
		if (content_type != null && content_type.indexOf("multipart/form-data") != -1) {
			cmd = cmd == null ? "3013":cmd; 
		}
		String url = StringUtils.defaultIfBlank(urlMap.get(cmd), DEFAULT_EMPTY_PATH);
		prepareRequest(req);
		req.getRequestDispatcher(dispatchPath(url,prepareData(req))).forward(req, resp);
	}

	
	/**
	 * Destroy.
	 *
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}


	/**
	 * 设置 the Map url map.
	 *
	 * @param urlMap the new Map url map
	 */
	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}

	
	
}
