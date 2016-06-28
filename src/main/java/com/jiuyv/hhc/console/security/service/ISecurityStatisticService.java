package com.jiuyv.hhc.console.security.service;

import java.util.List;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.security.SysUserVo;
import com.jiuyv.hhc.model.security.rpt.LoginCompStatisticBean;

/**
 * The Interface ISecurityStatisticService.
 *

 * @author 
 * @since 2014-1-21 12:23:31
 * @version 1.0.0
 */
public interface ISecurityStatisticService {

	/**
	 * Find login comp statistic data.
	 *
	 * @param filters the filters
	 * @param userInfo the user info
	 * @return the list
	 */
	List<LoginCompStatisticBean> findLoginCompStatisticData(
			List<Filter> filters, SysUserVo userInfo) throws BaseException;

}
