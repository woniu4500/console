package com.jiuyv.hhc.console.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.web.util.WebDateUtil;
import com.jiuyv.hhc.console.security.service.ISecurityStatisticService;
import com.jiuyv.hhc.model.security.SysUserVo;
import com.jiuyv.hhc.model.security.dao.SecurityStatisticDao;
import com.jiuyv.hhc.model.security.rpt.LoginCompStatisticBean;

/**
 * The Class SecurityStatisticServiceImpl.
 *

 * @author 
 * @since 2014-1-21 14:06:46
 * @version 1.0.0
 */
public class SecurityStatisticServiceImpl implements ISecurityStatisticService {
	
	/** The Security statistic dao security statistic dao. */
	private SecurityStatisticDao securityStatisticDao;
	
	private static final String FLD_BGN_DATE = "bgnDate";
	private static final String FLD_END_DATE = "endDate";
	private static final String FLD_OPER_USER = "operUser";
	
	
	
	/**
	 * Find login comp statistic data.
	 *
	 * @param filters the filters
	 * @param userInfo the user info
	 * @return the list
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.security.service.ISecurityStatisticService#findLoginCompStatisticData(java.util.List, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public List<LoginCompStatisticBean> findLoginCompStatisticData(
			List<Filter> filters, SysUserVo userInfo) throws BaseException {
		List<LoginCompStatisticBean> slist = new ArrayList<LoginCompStatisticBean>();
		LoginCompStatisticBean sbean = new LoginCompStatisticBean();
		slist.add(sbean);
		String bgnDate = null;
		String endDate = null;
		String operUser = null;
		Map<String, String> params = new HashMap<String, String>();
		for (Filter filter : filters) {
			String field = filter.getField();
			String value = (String) filter.getData().getValue();
			if ( FLD_BGN_DATE.equals(field)) {
				bgnDate = value; 
				params.put(FLD_BGN_DATE, value);
			}
			if ( FLD_END_DATE.equals(field)) {
				endDate = value;
				params.put(FLD_END_DATE, value);
			}
			if ( FLD_OPER_USER.equals(field) ) {
				operUser = value;
				params.put(FLD_OPER_USER, value);
			}
		} 
		sbean.setBgnDate(WebDateUtil.string2date(bgnDate));
		sbean.setEndDate(WebDateUtil.string2date(endDate));
		if ( StringUtils.isNotBlank(operUser)) {
			sbean.setOperUser(securityStatisticDao.findOperUserName(operUser));
		}
		sbean.setLoginStatistic(securityStatisticDao.findLoginStatistic(params));
		sbean.setOperateStatistic(securityStatisticDao.findOperateStatistic(params));
		return slist;
	}

	/**
	 * 设置 the Security statistic dao security statistic dao.
	 *
	 * @param securityStatisticDao the new Security statistic dao security statistic dao
	 */
	public void setSecurityStatisticDao(SecurityStatisticDao securityStatisticDao) {
		this.securityStatisticDao = securityStatisticDao;
	}

}
