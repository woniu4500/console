package com.jiuyv.common.web.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * The Class JCaptchaFilter.
 */
public class JCaptchaFilter implements Filter {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(JCaptchaFilter.class);
	
	/** The captcha service. */
	private CaptchaService captchaService; 
	
	/**
	 * Filter回调初始化函数.
	 *
	 * @param filterConfig the filter config
	 * @throws ServletException the servlet exception
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest theRequest, ServletResponse theResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) theRequest;
		HttpServletResponse response = (HttpServletResponse) theResponse;
		genernateCaptchaImage(request, response);
	}

	/**
	 * Filter回调退出函数.
	 */
	public void destroy() {

	}

	
	/**
	 * 生成验证码图片.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void genernateCaptchaImage(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		//Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.setContentType("image/jpeg");

		ServletOutputStream out = response.getOutputStream();
		try {
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId, request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException e) {
			LOG.error("Error generate captcha image. ", e);
		} finally {
			out.close();
		}
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
