package com.jiuyv.common.web.qrcode;

import javax.servlet.http.HttpServletRequest;


/**
 * The Interface CodeContent.
 *

 * @author 
 * @since 2014-5-26 10:06:18
 * @version 1.0.0
 */
public interface CodeContent {

	/**
	 * 根据请求获取编码内容.
	 *
	 * @param request the request
	 * @return the string
	 */
	String content(HttpServletRequest request);
	
}
