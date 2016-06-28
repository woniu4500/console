package com.jiuyv.hhc.console.security.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.console.security.service.ISecurityService;
import com.jiuyv.hhc.model.security.SysOprlogVo;
import com.jiuyv.hhc.model.security.SysResourceVo;
import com.jiuyv.hhc.model.security.dao.ISecurityDao;
import com.jiuyv.hhc.model.security.dao.SysOprlogDao;
import com.jiuyv.hhc.model.security.dao.SysResourceDao;

/**
 * The Class SecurityServiceImpl.
 *

 * @author 
 * @since 2014-1-2 16:35:04
 * @version 1.0.0
 */
public class SecurityServiceImpl extends AssistService implements ISecurityService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);
	/** 斜线 */
	private static final String SLASH = "/";
	/** 逗号 */
	private static final String COMMA = ",";
	
	/** The security dao. */
	private ISecurityDao securityDao ; 
	
	/** The oprlog dao. */
	private SysOprlogDao oprlogDao ;
	
	/**
	 * Sets the security dao.
	 *
	 * @param securityDao the new security dao
	 */
	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}
	
	/**
	 * Sets the oprlog dao.
	 *
	 * @param oprlogDao the new oprlog dao
	 */
	public void setOprlogDao(SysOprlogDao oprlogDao) {
		this.oprlogDao = oprlogDao;
	}

	/**
	 * @return
	 * @see com.jiuyv.hhc.console.security.service.ISecurityService#findFullResMapWithUrlKey()
	 */
	public Map<String, String> findFullResMapWithUrlKey() {
		List<SysResourceVo> reslist = securityDao.findFullRes();
		Map<String, String> resMap = new LinkedHashMap<String, String>();
		if ( reslist == null) {
			return new LinkedHashMap<String, String>();
		}
		for (SysResourceVo res : reslist) {
			String urls = res.getResUrl();
			if ( StringUtils.isNotBlank(urls) ) {
				String[] urlArr = urls.split(COMMA);
				for( String url: urlArr ) {
					url = StringUtils.trim(url);
					if ( StringUtils.isNotBlank(url) && url.charAt(0) != '/' ) {
						url = SLASH + url;
					}
					String role = res.getResId().toUpperCase();
					if ( resMap.containsKey(url) ) {
						resMap.put(url, resMap.get(url) + COMMA + role);
					} else {
						resMap.put(url, role);
					}
				}
			}
		}
		return resMap;
	}

	/**
	 * @return
	 * @see com.jiuyv.hhc.console.security.service.ISecurityService#findFullResMapWithResId()
	 */
	public Map<String, SysResourceVo> findFullResMapWithResId() {
		List<SysResourceVo> reslist = getQueryAssist().list(SysResourceDao.MAPPED_FIND, null, new Page("orderNum"));
		Map<String, SysResourceVo> resmap = new LinkedHashMap<String, SysResourceVo>();
		for(SysResourceVo res: reslist) {
			resmap.put(res.getResId(), res);
		}
		return resmap;
//		return getQueryAssist().map(SysResourceDao.MAPPED_FIND, null, new Page("orderNum"), null, "resId");
	}
	
	/**
	 * @return
	 * @see com.jiuyv.hhc.console.security.service.ISecurityService#findOprNo()
	 */
	public Long findOprNo() {
		return null;
	}

	/**
	 * @param logVo
	 * @see com.jiuyv.hhc.console.security.service.ISecurityService#logRecord(com.jiuyv.hhc.model.security.SysOprlogVo)
	 */
	public void logRecord(SysOprlogVo logVo) {
		if (logVo == null) {
			LOGGER.error("OprlogVo is null. ");
			return;
		}
		try {
			if (StringUtils.isEmpty(logVo.getStlmDate())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				logVo.setStlmDate(sdf.format(new Date()));
			}
			oprlogDao.insert(logVo);
		} catch (Exception e) {
			LOGGER.error("Error record log. ", e);
		}
	}

	/**
	 * @return
	 * @see com.jiuyv.hhc.console.security.service.ISecurityService#findSysTime()
	 */
	public String findSysTime() {
		return securityDao.findSysTime();
	}



}
