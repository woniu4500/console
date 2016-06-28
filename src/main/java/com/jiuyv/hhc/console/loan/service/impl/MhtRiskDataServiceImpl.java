package com.jiuyv.hhc.console.loan.service.impl;

import static com.jiuyv.common.validate.BizCheck.notNull;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.loan.service.IMhtRiskDataService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.loan.MhtRiskDataVo;
import com.jiuyv.hhc.model.loan.dao.MhtRiskDataDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class LoanServiceImpl.
 *

 * @author 
 * @since 2014-1-3 15:45:38
 * @version 1.0.0
 */
public class MhtRiskDataServiceImpl extends AssistService implements IMhtRiskDataService {

	/** The dao. */
	private MhtRiskDataDao mhtRiskDataDao;
	
	/**
	 * 新增.
	 *
	 * @param loanVo the loan vo
	 * @param filters the filters
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<MhtRiskDataVo> doInsertMhtRiskData(MhtRiskDataVo mthHisTranVo,SysUserVo userVo)
			throws Exception {
		notNull(mthHisTranVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		try {
			DBLogUtil.addLogInfo(mthHisTranVo);
			return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(mhtRiskDataDao, mthHisTranVo));
		} catch (DuplicateKeyException e) {
			throw new BaseException(ErrorCode.CODE_2012, ErrorCode.CODE_2012_MSG, e);
		}
	}

	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<MhtRiskDataVo> findMhtRiskData(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(MhtRiskDataDao.MAPPED_FIND, filters, page);
	}
	
	/**
	 * 设置 the dao.
	 *
	 * @param loanInfoDao the new dao
	 */
	public void setMhtRiskDataDao(MhtRiskDataDao mhtRiskDataDao) {
		this.mhtRiskDataDao = mhtRiskDataDao;
	}	
}
