package com.jiuyv.common.web.qrcode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.image.ImageType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class QRCode.
 *

 * @author 
 * @since 2014-5-26 9:58:40
 * @version 1.0.0
 */
public class QRCodeFilter implements javax.servlet.Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QRCodeFilter.class);
	
	/** 编码内容获取. */
	private CodeContent codeContent; 
	
	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param theRequest the the request
	 * @param theResponse the the response
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	public void doFilter(ServletRequest theRequest, ServletResponse theResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) theRequest;
		HttpServletResponse response = (HttpServletResponse) theResponse;
		String encodeContent = codeContent.content(request);
		if ( LOGGER.isDebugEnabled() ) {
			LOGGER.debug("QRCode Content: {}", encodeContent);
		}
		ByteArrayOutputStream out = net.glxn.qrgen.QRCode.from(encodeContent).to(ImageType.PNG).stream();
		try {
			OutputStream output = response.getOutputStream();
			output.write(out.toByteArray()); 
		} catch (Exception e) {
			LOGGER.error("Output QRCode Error. ", e);
		}
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
	 * Filter回调退出函数.
	 */
	public void destroy() {
	}

	/**
	 * 设置 编码内容获取.
	 *
	 * @param codeContent the codeContent to set
	 */
	public void setCodeContent(CodeContent codeContent) {
		this.codeContent = codeContent;
	}
}
