package com.jiuyv.hhc.console.loan.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.loan.MhtHisTranVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface ILoanService.
 *

 * @author 
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
public interface IMhtHisTranService {
	
	/**
	 * 新增.
	 *
	 * @param loanVo the loan vo
	 * @param filters the filters
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<MhtHisTranVo> doInsertMhtHisTran(MhtHisTranVo mthHisTranVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<MhtHisTranVo> findMhtHisTran(List<Filter> filters, Page page) throws Exception;
}
