package com.jiuyv.hhc.console.loan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.console.loan.service.ITxnService;
import com.jiuyv.hhc.model.loan.TxnTransLogVo;
import com.jiuyv.hhc.model.loan.dao.TxnTransLogDao;

/**
 * <h1>The Class TxnServiceImpl.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2014
 * @version 1.0.0
 */
public class TxnServiceImpl extends AssistService implements ITxnService {

	/** The Txn trans log dao txn trans log dao. */
	private TxnTransLogDao txnTransLogDao;
	
	/**
	 * @param filters
	 * @param pageInfo
	 * @return
	 * @throws BaseException
	 * @see com.jiuyv.hhc.console.loan.service.ITxnService#findPage(java.util.List, com.jiuyv.common.database.Page)
	 */
	public ExtData<TxnTransLogVo> findPage(List<Filter> filters, Page pageInfo)
			throws BaseException {
		return getQueryAssist().page(TxnTransLogDao.MAPPED_FIND, filters, pageInfo);
	}

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
	public ExtData<TxnTransLogVo> findAmtPage(List<Filter> filters, Page pageInfo, Long registAmt,
			Long certAmt) throws BaseException {
		Map params = new HashMap();
		params.put("registAmt", registAmt);
		params.put("certAmt", certAmt);
		return getQueryAssist().page(TxnTransLogDao.MAPPED_AMT_FIND, filters, pageInfo, params);
	}
	
	/**
	 * @param txnTransLogDao the txnTransLogDao to set
	 */
	public void setTxnTransLogDao(TxnTransLogDao txnTransLogDao) {
		this.txnTransLogDao = txnTransLogDao;
	}

}
