package com.jiuyv.hhc.console.loan.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.loan.TxnTransLogVo;

/**
 * <h1>The Interface ITxnService.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2014
 * @version 1.0.0
 */
public interface ITxnService {

	/**
	 * Find page.
	 *
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<TxnTransLogVo> findPage(List<Filter> filters, Page pageInfo) throws BaseException;

	/**
	 * Find amt page.
	 *
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @param registAmt the regist amt
	 * @param certAmt the cert amt
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<TxnTransLogVo> findAmtPage(List<Filter> filters, Page pageInfo, Long registAmt,
			Long certAmt) throws BaseException;

}
