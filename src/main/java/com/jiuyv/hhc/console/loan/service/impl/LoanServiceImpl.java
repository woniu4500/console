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
import com.jiuyv.common.validate.BizCheck;
import com.jiuyv.hhc.console.loan.service.ILoanService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.loan.LoanInfoVo;
import com.jiuyv.hhc.model.loan.dao.LoanInfoDao;
import com.jiuyv.hhc.model.security.SecurityUserDetail;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class LoanServiceImpl.
 *

 * @author 
 * @since 2014-1-3 15:45:38
 * @version 1.0.0
 */
@Deprecated
public class LoanServiceImpl extends AssistService implements ILoanService {

	/** The dao. */
	private LoanInfoDao loanInfoDao;
	/** 00:内部初审. */
	final static String STATUS_00="00" ;
	
	/**
	 * 新增.
	 *
	 * @param loanVo the loan vo
	 * @param filters the filters
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<LoanInfoVo> doInsertLoan(LoanInfoVo loanVo,SysUserVo userVo)
			throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		try {
			loanVo.setLoanSt("S0");
			loanVo.setLastUptAcc(userVo.getLoginId());
			loanVo.setLastUptOrg(userVo.getOrgCode());
			DBLogUtil.addLogInfo(loanVo);
			return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(loanInfoDao, loanVo));
		} catch (DuplicateKeyException e) {
			throw new BaseException(ErrorCode.CODE_2012, ErrorCode.CODE_2012_MSG, e);
		}
	}

	/**
	 * 修改.
	 *
	 * @param loanVo the loan vo
	 * @param filters the filters
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<LoanInfoVo> doUpdateLoan(LoanInfoVo loanVo,SysUserVo userVo)
			throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		LoanInfoVo pvo = loanInfoDao.findByKey(loanId);
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);	
		pvo.setCupLoanId(loanVo.getCupLoanId());
		
		pvo.setMchntCode      (loanVo.getMchntCode      ());
		pvo.setLicNo          (loanVo.getLicNo          ());
		pvo.setReveRegCd      (loanVo.getReveRegCd      ());
		pvo.setArtifNm        (loanVo.getArtifNm        ());
		pvo.setArtifCertifId  (loanVo.getArtifCertifId  ());
		pvo.setArtifCertifType(loanVo.getArtifCertifType());
		pvo.setCreditSt       (loanVo.getCreditSt       ());
		pvo.setMchntSt        (loanVo.getMchntSt        ());
		pvo.setLenderId       (loanVo.getLenderId       ());
		pvo.setLoanAt         (loanVo.getLoanAt         ());
		pvo.setLoanCycle      (loanVo.getLoanCycle      ());
		pvo.setLoanrt         (loanVo.getLoanrt         ());
		pvo.setCardCreditLimit(loanVo.getCardCreditLimit());
		pvo.setCardUseLimit   (loanVo.getCardUseLimit   ());
		pvo.setCardExpire     (loanVo.getCardExpire     ());
		pvo.setLoanSuccTm     (loanVo.getLoanSuccTm     ());
		pvo.setLoanBalance    (loanVo.getLoanBalance    ());
		pvo.setLoanProd       (loanVo.getLoanProd       ());
		pvo.setSvcFeeBenefit  (loanVo.getSvcFeeBenefit  ());
		pvo.setAllotTp        (loanVo.getAllotTp        ());
		pvo.setExceedAt       (loanVo.getExceedAt       ());
		pvo.setExceedNum      (loanVo.getExceedNum      ());
		pvo.setLoanExpire     (loanVo.getLoanExpire     ());
		pvo.setRemark         (loanVo.getRemark         ());
		pvo.setVersion(loanVo.getVersion());
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(loanInfoDao, pvo));
	}

	/**
	 * 贷款申请提交银行.
	 *
	 * @param loanVo the loan vo
	 * @param user the user
	 * @return the ext data
	 * @throws Exception the exception
	 */
	public ExtData<LoanInfoVo> doSend2Bank(LoanInfoVo loanVo, SecurityUserDetail userVo) throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		LoanInfoVo pvo = loanInfoDao.findByKey(loanId);
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);
		BizCheck.isTrue(pvo.getLoanSt().equals("S0"), "", "仅贷款录入状态的申请可提交银行");
		BizCheck.isTrue(pvo.getLoanAt().compareTo(0l)>0, "", "贷款金额必须大于0");
		BizCheck.notNull(pvo.getMchntCd(), "", "商户号必须填写");
		BizCheck.notNull(pvo.getLoanProd(), "", "贷款产品不能为空");
		BizCheck.notNull(pvo.getLenderId(), "", "资金方不能为空");
		
		pvo.setLoanSt("S1");
		pvo.setVersion(loanVo.getVersion());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(loanInfoDao, pvo));
	}
	
	/**
	 * 贷款申请成功.
	 *
	 * @param loanVo the loan vo
	 * @param user the user
	 * @return the ext data
	 * @throws Exception the exception
	 */
	public ExtData<LoanInfoVo> doLoanSuccess(LoanInfoVo loanVo, SecurityUserDetail userVo) throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		LoanInfoVo pvo = loanInfoDao.findByKey(loanId);
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);
		BizCheck.isTrue(pvo.getLoanSt().equals("S1"), "", "提交银行状态可进行贷款成功操作");
		BizCheck.isTrue(pvo.getLoanAt().compareTo(0l)>0, "", "贷款金额必须大于0");
		BizCheck.notNull(pvo.getMchntCd(), "", "商户号必须填写");
		BizCheck.notNull(pvo.getLoanProd(), "", "贷款产品不能为空");
		BizCheck.notNull(pvo.getLenderId(), "", "资金方不能为空");
		
		pvo.setLoanSt("00");
		pvo.setVersion(loanVo.getVersion());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(loanInfoDao, pvo));
	}
	
	/**
	 * 贷款申请失败.
	 *
	 * @param loanVo the loan vo
	 * @param user the user
	 * @return the ext data
	 * @throws Exception the exception
	 */
	public ExtData<LoanInfoVo> doLoanFailed(LoanInfoVo loanVo, SecurityUserDetail userVo) throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		LoanInfoVo pvo = loanInfoDao.findByKey(loanId);
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);
		BizCheck.isTrue(pvo.getLoanSt().equals("S1"), "", "提交银行状态可进行贷款失败操作");
		BizCheck.isTrue(loanVo.getLoanAt().compareTo(0l)>0, "", "贷款金额必须大于0");
		BizCheck.notNull(loanVo.getMchntCd(), "", "商户号必须填写");
		BizCheck.notNull(loanVo.getLoanProd(), "", "贷款产品不能为空");
		BizCheck.notNull(loanVo.getLenderId(), "", "资金方不能为空");
		
		pvo.setLoanSt("01");
		
		pvo.setVersion(loanVo.getVersion());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(loanInfoDao, pvo));
	}
	
	
	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<LoanInfoVo> findLoan(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(LoanInfoDao.MAPPED_FIND, filters, page);
	}		
	
	/**
	 * 查询代理商户下的所有贷款信息
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<LoanInfoVo> findLoanbyAgentCustomerCode(Long customerCode, Page page)
			throws Exception {
		return ExtDataUtil.genWithData(loanInfoDao.filterQuerybyAgentCustomerCode(customerCode));
	}
	/**
	 * 设置 the dao.
	 *
	 * @param loanInfoDao the new dao
	 */
	public void setLoanInfoDao(LoanInfoDao loanInfoDao) {
		this.loanInfoDao = loanInfoDao;
	}

}
