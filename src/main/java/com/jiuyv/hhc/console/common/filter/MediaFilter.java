package com.jiuyv.hhc.console.common.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.hhc.console.common.service.IMediaSupportService;
import com.jiuyv.hhc.model.common.IMedia;


/**
 * 
 * 
 * 
 * @see ResponseHeaderFilter
 * @author 
 *
 */
public class MediaFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaFilter.class);

	
	/** 过滤器参数配置 */
	private Map<String, String> responseConfig;
	
	/** 默认的MIME类型 */
	private Map<String, String> mimeType ;

	/** 排除路径  */
	private String excludeUrl = "/r/";
	
	/** 资源获取类型 */
	private IMediaSupportService mediaService ; 

	/**
	 * 
	 * @param servletRequest
	 * @param servletResponse
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// set response cache head info
		if ( responseConfig != null ) {
			for ( Entry<String, String> entry : responseConfig.entrySet() ){
				response.addHeader(entry.getKey(), entry.getValue());
			}
		}
		// exclude console
		String filePath = request.getRequestURI().substring(request.getContextPath().length() + 1);
		if ( !filePath.startsWith("/")) {
			filePath = "/" + filePath ; 
		}
		if ( filePath.startsWith(excludeUrl) || mediaService == null) {
			// load from file.
			chain.doFilter(request, response);
			return ; 
		}
		// load with media service
		// if url = xxxx?show=thumbnail
		String fileType = request.getParameter("show"); 
		
		IMedia media = mediaService.findMedia(filePath, fileType);
		// get content type for file. 
		String contentType = media.getContentType();
		if ( contentType == null ) {
			contentType = mimeType.get(fileType.toLowerCase());
		}
		response.setContentType(contentType);
		String encode = media.getEncode();
		if ( encode != null ) {
			response.setCharacterEncoding(encode);
		}
		OutputStream os = response.getOutputStream();
		byte[] content = media.getContent();
		
		try{
			os.write(content);
			os.flush();
		} catch ( IOException e ) {
			LOGGER.info("Client Abort. ");
		} catch ( Exception e ) {
			LOGGER.error("Error occur in write media files. ",e);
		} finally {
			os.close();
		}
		
	}

	/**
	 * 初始化
	 * 
	 * @param filterConfig
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	/**
	 * 
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @param responseConfig the responseConfig to set
	 */
	public void setResponseConfig(Map<String, String> responseConfig) {
		this.responseConfig = responseConfig;
	}

	/**
	 * @param excludeUrl the excludeUrl to set
	 */
	public void setExcludeUrl(String excludeUrl) {
		this.excludeUrl = excludeUrl;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(Map<String, String> mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @param mediaService the mediaService to set
	 */
	public void setMediaService(IMediaSupportService mediaService) {
		this.mediaService = mediaService;
	}
}
