package com.jiuyv.common.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.util.UrlPathHelper;


/**
 * 
 * @author gemini
 * 
 */
public final class WebRequestUtil {
	/**
	 * ajax http request head token
	 */
	private static final String XMLHTTP_REQUEST = "XMLHttpRequest";
	
	public static final String XFLAG = "app-client-flag";

	private WebRequestUtil() {
	}
	
	/**
	 * 判断是否为ajax请求 如参数传递null，则对当前请求进行判断
	 * 
	 * @param httpRequest
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest httpRequest) {
		HttpServletRequest request = httpRequest == null ? ServletActionContext
				.getRequest() : httpRequest;
		// 指定递交参数 ajaxUpload 时判断为ajax请求
		if ( StringUtils.equals("ajaxUpload", request.getParameter("ajaxUpload"))){
			return true;
		}
		// get requestHead
		String requestedWith = request.getHeader("x-requested-with");
		// 根据是否有jsonObject 和Filters 或者header头部信息来判别是否ajax请求
		return XMLHTTP_REQUEST.equals(requestedWith);
	}
	
	/**
	 * 判断是否来自客户端的请求
	 * @param httpRequest
	 * @return
	 */
	public static boolean isAppClient(HttpServletRequest httpRequest) {
		HttpServletRequest request = httpRequest == null ? ServletActionContext
				.getRequest() : httpRequest;
		String sessionFlag = (String) request.getSession().getAttribute(XFLAG);
		if (  StringUtils.isNotBlank(sessionFlag) ) {
			return true;
		}
		if ( StringUtils.equals(request.getParameter("flag"),"mobile")) {
			return true;
		}
		String xflag = request.getHeader(XFLAG);		
		return StringUtils.isNotBlank(xflag);
	}

	/** 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	
	/**
	 * 获得当的访问路径
	 * 
	 * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
	 * 
	 * @param request
	 * @return
	 */
	public static String getLocation(HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		StringBuffer buff = request.getRequestURL();
		String uri = request.getRequestURI();
		String origUri = helper.getOriginatingRequestUri(request);
		buff.replace(buff.length() - uri.length(), buff.length(), origUri);
		String queryString = helper.getOriginatingQueryString(request);
		if (queryString != null) {
			buff.append("?").append(queryString);
		}
		return buff.toString();
	}

	/**
	 * 获得请求的session id，但是HttpServletRequest#getRequestedSessionId()方法有一些问题。
	 * 当存在部署路径的时候，会获取到根路径下的jsessionid。
	 * 
	 * @see HttpServletRequest#getRequestedSessionId()
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestedSessionId(HttpServletRequest request) {
		String sid = request.getRequestedSessionId();
		String ctx = request.getContextPath();
		// 如果session id是从url中获取，或者部署路径为空，那么是在正确的。
		if (request.isRequestedSessionIdFromURL() || StringUtils.isBlank(ctx)) {
			return sid;
		} else {
			// 手动从cookie获取
			Cookie cookie = CookieUtil.getCookie(request,
					Constants.JSESSION_COOKIE);
			if (cookie != null) {
				return cookie.getValue();
			} else {
				return null;
			}
		}

	}
	/**
	 * 验证是否需要跳转回到原请求。
	 * 
	 * 判断POST请求中是否包含请求参数
	 * @param request
	 * @return
	 */
	public static boolean redirectable(HttpServletRequest request) {
		if ( request == null ) {
			return false;
		}
		String method = request.getMethod();
		if ( method!= null && "POST".equals(method.toUpperCase())) {
			if (request.getParameterMap().size() > 0 ) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	/**
	 * 获取浏览器类型
	 * @param request
	 * @return IE|FF|SF|Other
	 */
	public static String getBrowser(HttpServletRequest httpRequest) {
		HttpServletRequest request = httpRequest == null ? ServletActionContext
				.getRequest() : httpRequest;
		String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
		if (UserAgent != null) {
			if (UserAgent.indexOf("msie") >= 0){
				return "IE";
			}
			if (UserAgent.indexOf("firefox") >= 0){
				return "FF";
			}
			if (UserAgent.indexOf("safari") >= 0){
				return "SF";
			}
		}
		return "Other";
	}
	
	/**
	 * 对下载文件名进行编码
	 * @param httpRequest
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeFileName (HttpServletRequest httpRequest, String fileName) throws UnsupportedEncodingException {
		HttpServletRequest request = httpRequest == null ? ServletActionContext
				.getRequest() : httpRequest;
		String fnm = fileName;
		if("FF".equals(getBrowser(request))){
			fnm = new String(fnm.getBytes("utf-8"),"iso-8859-1");
		} else {
			fnm = URLEncoder.encode(fnm,"utf-8"); 
		}
		return fnm;
	}
}
