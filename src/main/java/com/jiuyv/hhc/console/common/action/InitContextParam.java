package com.jiuyv.hhc.console.common.action;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.model.common.SysParamVo;

/**
 * The Class InitContextParam.
 * 上下文参数初始化
 * 

 * @author 
 * @since 2014-5-27 10:40:47
 * @version 1.0.0
 */
public class InitContextParam implements ServletContextAware  {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(InitContextParam.class);

	/** 系统参数Service. */
	private ISysParamService sysParamService ; 
	
	/**
	 * 
	 * @param servletContext
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext servletContext) {
		ExtData<SysParamVo> data;
		try {
			data = sysParamService.loadTheme();
			for ( SysParamVo p : data.getRoot() ) {
				servletContext.setAttribute(p.getParamCode(), p.getParamValue());
				LOGGER.info("Context Parameter: {} = {}", new Object[]{p.getParamCode(), p.getParamValue()});
			}
		} catch (Exception e) {
			LOGGER.error("Context Parameter init error. ", e);
		}
	}
	
	/**
	 * 设置 系统参数Service.
	 *
	 * @param sysParamService the sysParamService to set
	 */
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}
}
