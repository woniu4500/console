package com.jiuyv.hhc.console.loan.service.impl;

import static com.jiuyv.common.validate.BizCheck.notNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.validate.BizCheck;
import com.jiuyv.hhc.console.common.service.IMediaImageService;
import com.jiuyv.hhc.console.loan.service.IBizLoanDataService;
import com.jiuyv.hhc.console.message.service.IMailService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.common.SysTimeBean;
import com.jiuyv.hhc.model.common.dao.ISysTimeDao;
import com.jiuyv.hhc.model.loan.BizLoanDataVo;
import com.jiuyv.hhc.model.loan.LoanDict.CancelFlag;
import com.jiuyv.hhc.model.loan.LoanDict.CreditSt;
import com.jiuyv.hhc.model.loan.dao.BizLoanDataDao;
import com.jiuyv.hhc.model.mchnt.BizMchntAttachVo;
import com.jiuyv.hhc.model.mchnt.BizMerchantVo;
import com.jiuyv.hhc.model.mchnt.MchntDict;
import com.jiuyv.hhc.model.mchnt.dao.BizMchntAttachDao;
import com.jiuyv.hhc.model.mchnt.dao.BizMerchantDao;
import com.jiuyv.hhc.model.security.SecurityUserDetail;
import com.jiuyv.hhc.model.security.SysUserVo;


/**
 * <h1>The Class BizLoanDataServiceImpl.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public class BizLoanDataServiceImpl extends AssistService implements IBizLoanDataService{

	/** The dao. */
	private BizLoanDataDao bizLoanDataDao ;
	
	/** 商户Dao. */
	private BizMerchantDao bizMerchantDao ;
	
	/** . */
	private ISysTimeDao sysTimeDao ;
	
	/**   */
	private BizMchntAttachDao bizMchntAttachDao;
	
	/**   */
	/** 图片处理Service. */
	private IMediaImageService mediaImageService ; 
	
	/** 邮件Service. */ 
	private IMailService mailService ; 


	/**
	 * 新增.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doInsertLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		try {
			loanVo.setCreditSt(CreditSt.INPUT);
			loanVo.setCancelFlag(CancelFlag.NORMAL);
			loanVo.setLastUptAcc(userVo.getLoginId());
			loanVo.setLastUptOrg(userVo.getOrgCode());
			loanVo.setRecAcc(userVo.getLoginId());
			DBLogUtil.addLogInfo(loanVo);
			return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(bizLoanDataDao, loanVo));
		} catch (DuplicateKeyException e) {
			throw new BaseException(ErrorCode.CODE_2012, ErrorCode.CODE_2012_MSG, e);
		}
	}

	/**
	 * 修改.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doUpdateLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		BizLoanDataVo pvo = bizLoanDataDao.findByKey(loanId);
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);	
		
		BizCheck.isTrue(pvo.getCreditSt().equals(CreditSt.INPUT)||pvo.getCreditSt().equals(CreditSt.SUBMIT), "", "仅录入或提交状态的申请可做修改操作");
		
		pvo.setVersion(loanVo.getVersion());
		pvo.setMchntCode(loanVo.getMchntCode());
//		pvo.setCupLoanId(loanVo.getCupLoanId());
		pvo.setLicNo(loanVo.getLicNo());
		pvo.setArtifCertifId(loanVo.getArtifCertifId());
		pvo.setArtifNm(loanVo.getArtifNm());
		pvo.setCreditSt(loanVo.getCreditSt());
//		pvo.setMchntSt(loanVo.getMchntSt());
		pvo.setCancelFlag(loanVo.getCancelFlag());
//		pvo.setLenderId(loanVo.getLenderId());
		pvo.setReqLoanAt(loanVo.getReqLoanAt());
//		pvo.setLoanAt(loanVo.getLoanAt());
		pvo.setLoanCycle(loanVo.getLoanCycle());
		pvo.setLoanRt(loanVo.getLoanRt());
		pvo.setCardCreditLimit(loanVo.getCardCreditLimit());
		pvo.setCardUseLimit(loanVo.getCardUseLimit());
		pvo.setCardExpire(loanVo.getCardExpire());
		pvo.setCardNo(loanVo.getCardNo());
//		pvo.setLoanSuccTm(loanVo.getLoanSuccTm());
//		pvo.setLoanBalance(loanVo.getLoanBalance());
//		pvo.setLoanProd(loanVo.getLoanProd());
		pvo.setSvcFeeBenefit(loanVo.getSvcFeeBenefit());
//		pvo.setAllotTp(loanVo.getAllotTp());
//		pvo.setExceedAt(loanVo.getExceedAt());
//		pvo.setExceedNum(loanVo.getExceedNum());
//		pvo.setLoanExpire(loanVo.getLoanExpire());
		pvo.setRemark(loanVo.getRemark());
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizLoanDataDao, pvo));
	}

	/**
	 * 提交.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doSubmitLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		BizLoanDataVo pvo = bizLoanDataDao.findByKey(loanId);
		BizCheck.isTrue(pvo.getCreditSt().equals(CreditSt.INPUT), "", "仅录入状态的申请可做提交操作");
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);	
		
		pvo.setVersion(loanVo.getVersion());
		pvo.setMchntCode(loanVo.getMchntCode());
//		pvo.setCupLoanId(loanVo.getCupLoanId());
		pvo.setLicNo(loanVo.getLicNo());
		pvo.setArtifCertifId(loanVo.getArtifCertifId());
		pvo.setArtifNm(loanVo.getArtifNm());
//		pvo.setCreditSt(loanVo.getCreditSt());
//		pvo.setMchntSt(loanVo.getMchntSt());
//		pvo.setCancelFlag(loanVo.getCancelFlag());
//		pvo.setLenderId(loanVo.getLenderId());
		pvo.setReqLoanAt(loanVo.getReqLoanAt());
//		pvo.setLoanAt(loanVo.getLoanAt());
		pvo.setLoanCycle(loanVo.getLoanCycle());
		pvo.setLoanRt(loanVo.getLoanRt());
		pvo.setCardCreditLimit(loanVo.getCardCreditLimit());
		pvo.setCardUseLimit(loanVo.getCardUseLimit());
		pvo.setCardExpire(loanVo.getCardExpire());
		pvo.setCardNo(loanVo.getCardNo());
//		pvo.setLoanSuccTm(loanVo.getLoanSuccTm());
//		pvo.setLoanBalance(loanVo.getLoanBalance());
//		pvo.setLoanProd(loanVo.getLoanProd());
//		pvo.setSvcFeeBenefit(loanVo.getSvcFeeBenefit());
//		pvo.setAllotTp(loanVo.getAllotTp());
//		pvo.setExceedAt(loanVo.getExceedAt());
//		pvo.setExceedNum(loanVo.getExceedNum());
//		pvo.setLoanExpire(loanVo.getLoanExpire());
		pvo.setRemark(loanVo.getRemark());
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		
		// 查询商户是否已认证，是的话自动跳转到商户认证状态
		BizMerchantVo mchnt = bizMerchantDao.findByKey(loanVo.getMchntCode());
		notNull(mchnt,ErrorCode.CODE_2021,ErrorCode.CODE_2021_MSG);	
		if ( StringUtils.equals(mchnt.getMchntSt(), MchntDict.MchntSt.CONFID) ) {
			pvo.setCreditSt(CreditSt.CERTIFY);
		} else {
			pvo.setCreditSt(CreditSt.SUBMIT);
		}
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizLoanDataDao, pvo));
	}
	
	/**
	 * 贷后信息维护.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doMaintainLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		BizLoanDataVo pvo = bizLoanDataDao.findByKey(loanId);
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);	
		SysTimeBean timeBean = sysTimeDao.findDatabaseTime();
		
		pvo.setVersion(loanVo.getVersion());
		pvo.setMchntCode(loanVo.getMchntCode());
		pvo.setCupLoanId(loanVo.getCupLoanId());
		pvo.setLicNo(loanVo.getLicNo());
		pvo.setArtifCertifId(loanVo.getArtifCertifId());
		pvo.setArtifNm(loanVo.getArtifNm());
		pvo.setCreditSt(loanVo.getCreditSt());
		pvo.setMchntSt(loanVo.getMchntSt());
		pvo.setCancelFlag(loanVo.getCancelFlag());
		pvo.setLenderId(loanVo.getLenderId());
		pvo.setReqLoanAt(loanVo.getReqLoanAt());
		pvo.setLoanAt(loanVo.getLoanAt());
		pvo.setLoanCycle(loanVo.getLoanCycle());
		pvo.setLoanRt(loanVo.getLoanRt());
		pvo.setCardCreditLimit(loanVo.getCardCreditLimit());
		pvo.setCardUseLimit(loanVo.getCardUseLimit());
		pvo.setCardExpire(loanVo.getCardExpire());
		pvo.setCardNo(loanVo.getCardNo());
		pvo.setLoanSuccTm(loanVo.getLoanSuccTm());
		pvo.setLoanBalance(loanVo.getLoanBalance());
		pvo.setLoanProd(loanVo.getLoanProd());
		pvo.setSvcFeeBenefit(loanVo.getSvcFeeBenefit());
		pvo.setAllotTp(loanVo.getAllotTp());
		pvo.setExceedAt(loanVo.getExceedAt());
		pvo.setExceedNum(loanVo.getExceedNum());
		pvo.setLoanExpire(loanVo.getLoanExpire());
		pvo.setRemark(loanVo.getRemark());
		
		pvo.setLoanProcResp(loanVo.getLoanProcResp());
		pvo.setLoanProcAcc(userVo.getLoginId());
		pvo.setLoanProcTime(timeBean.getDataBaseTime());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizLoanDataDao, pvo));
	}
	
	
	/**
	 * 手工商户认证.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doCertifyLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		BizLoanDataVo pvo = bizLoanDataDao.findByKey(loanId);
		BizCheck.isTrue(pvo.getCreditSt().equals(CreditSt.INPUT)||pvo.getCreditSt().equals(CreditSt.SUBMIT), "", "仅录入或提交状态的申请可做提交操作");
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);	
		SysTimeBean timeBean = sysTimeDao.findDatabaseTime();
		
		pvo.setCreditSt(CreditSt.CERTIFY);
		pvo.setVersion(loanVo.getVersion());

		pvo.setLoanProcAcc(userVo.getLoginId());
		pvo.setLoanProcTime(timeBean.getDataBaseTime());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizLoanDataDao, pvo));
	}
	
	/**
	 * 手工贷款驳回.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doDenyLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		BizLoanDataVo pvo = bizLoanDataDao.findByKey(loanId);
		BizCheck.isTrue(pvo.getCreditSt().equals(CreditSt.INPUT)||pvo.getCreditSt().equals(CreditSt.SUBMIT), "", "仅录入或提交状态的申请可做提交操作");
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);
		SysTimeBean timeBean = sysTimeDao.findDatabaseTime();
		 
		pvo.setCreditSt(CreditSt.LOAN_DENY);
		pvo.setVersion(loanVo.getVersion());
		
		pvo.setLoanProcResp(loanVo.getLoanProcResp());
		pvo.setLoanProcAcc(userVo.getLoginId());
		pvo.setLoanProcTime(timeBean.getDataBaseTime());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		// 更新数据
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizLoanDataDao, pvo));
	}
	
	
	/**
	 * 手工贷款驳回.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doAuditLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		BizLoanDataVo pvo = bizLoanDataDao.findByKey(loanId);
		BizCheck.isTrue(pvo.getCreditSt().equals(CreditSt.INPUT)||pvo.getCreditSt().equals(CreditSt.SUBMIT), "", "仅录入或提交状态的申请可做提交操作");
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);
		SysTimeBean timeBean = sysTimeDao.findDatabaseTime();
		 
		pvo.setCreditSt(CreditSt.LOAN_SUCC);
		pvo.setVersion(loanVo.getVersion());
		
		pvo.setLoanProcResp(loanVo.getLoanProcResp());
		pvo.setLoanProcAcc(userVo.getLoginId());
		pvo.setLoanProcTime(timeBean.getDataBaseTime());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		// 更新数据
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizLoanDataDao, pvo));
	}
	
	/**
	 * 贷款申请提交银行.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doSend2Bank(BizLoanDataVo loanVo, SecurityUserDetail userVo) throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		BizLoanDataVo pvo = bizLoanDataDao.findByKey(loanId);
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);
		BizCheck.isTrue(pvo.getCreditSt().equals(CreditSt.CERTIFY)||pvo.getCreditSt().equals(CreditSt.SUBMIT), "", "仅提交或商户认证状态的申请可提交银行");
		
		pvo.setCreditSt(CreditSt.TOBANK);
		
		pvo.setMchntCode(loanVo.getMchntCode());
//		pvo.setCupLoanId(loanVo.getCupLoanId());
		pvo.setLicNo(loanVo.getLicNo());
		pvo.setArtifCertifId(loanVo.getArtifCertifId());
		pvo.setArtifNm(loanVo.getArtifNm());
//		pvo.setCreditSt(loanVo.getCreditSt());
//		pvo.setMchntSt(loanVo.getMchntSt());
//		pvo.setCancelFlag(loanVo.getCancelFlag());
//		pvo.setLenderId(loanVo.getLenderId());
		pvo.setReqLoanAt(loanVo.getReqLoanAt());
//		pvo.setLoanAt(loanVo.getLoanAt());
		pvo.setLoanCycle(loanVo.getLoanCycle());
		pvo.setLoanRt(loanVo.getLoanRt());
		pvo.setCardCreditLimit(loanVo.getCardCreditLimit());
		pvo.setCardUseLimit(loanVo.getCardUseLimit());
		pvo.setCardExpire(loanVo.getCardExpire());
		pvo.setCardNo(loanVo.getCardNo());
//		pvo.setLoanSuccTm(loanVo.getLoanSuccTm());
//		pvo.setLoanBalance(loanVo.getLoanBalance());
//		pvo.setLoanProd(loanVo.getLoanProd());
//		pvo.setSvcFeeBenefit(loanVo.getSvcFeeBenefit());
//		pvo.setAllotTp(loanVo.getAllotTp());
//		pvo.setExceedAt(loanVo.getExceedAt());
//		pvo.setExceedNum(loanVo.getExceedNum());
//		pvo.setLoanExpire(loanVo.getLoanExpire());
		pvo.setRemark(loanVo.getRemark());
		// 设置操作信息
		SysTimeBean timeBean = sysTimeDao.findDatabaseTime();
		pvo.setLoanProcAcc(userVo.getLoginId());
		pvo.setLoanProcTime(timeBean.getDataBaseTime());
		pvo.setVersion(loanVo.getVersion());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizLoanDataDao, pvo));
	}
	
	/**
	 * 贷款申请成功.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doLoanSuccess(BizLoanDataVo loanVo, SecurityUserDetail userVo) throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		BizLoanDataVo pvo = bizLoanDataDao.findByKey(loanId);
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);
		BizCheck.isTrue(pvo.getCreditSt().equals(CreditSt.TOBANK), "", "提交银行状态可进行贷款成功操作");
		
//		pvo.setMchntCode(loanVo.getMchntCode());
//		pvo.setCupLoanId(loanVo.getCupLoanId());
//		pvo.setLicNo(loanVo.getLicNo());
//		pvo.setArtifCertifId(loanVo.getArtifCertifId());
//		pvo.setArtifNm(loanVo.getArtifNm());
//		pvo.setCreditSt(loanVo.getCreditSt());
//		pvo.setMchntSt(loanVo.getMchntSt());
//		pvo.setCancelFlag(loanVo.getCancelFlag());
//		pvo.setLenderId(loanVo.getLenderId());
//		pvo.setReqLoanAt(loanVo.getReqLoanAt());
		pvo.setLoanAt(loanVo.getLoanAt());
		pvo.setLoanCycle(loanVo.getLoanCycle());
		pvo.setLoanRt(loanVo.getLoanRt());
		pvo.setCardCreditLimit(loanVo.getCardCreditLimit());
		pvo.setCardUseLimit(loanVo.getCardUseLimit());
		pvo.setCardExpire(loanVo.getCardExpire());
		pvo.setCardNo(loanVo.getCardNo());
		
		pvo.setLoanSuccTm(loanVo.getLoanSuccTm());
		pvo.setLoanBalance(loanVo.getLoanBalance());
//		pvo.setLoanProd(loanVo.getLoanProd());
//		pvo.setSvcFeeBenefit(loanVo.getSvcFeeBenefit());
//		pvo.setAllotTp(loanVo.getAllotTp());
//		pvo.setExceedAt(loanVo.getExceedAt());
//		pvo.setExceedNum(loanVo.getExceedNum());
		pvo.setLoanExpire(loanVo.getLoanExpire());
		pvo.setRemark(loanVo.getRemark());
		
		pvo.setCreditSt(CreditSt.LOAN_SUCC);
		pvo.setVersion(loanVo.getVersion());
		
		// 设置操作信息
		SysTimeBean timeBean = sysTimeDao.findDatabaseTime();
		pvo.setLoanProcAcc(userVo.getLoginId());
		pvo.setLoanProcTime(timeBean.getDataBaseTime());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizLoanDataDao, pvo));
	}
	
	/**
	 * 贷款申请失败.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doLoanFailed(BizLoanDataVo loanVo, SecurityUserDetail userVo) throws Exception {
		notNull(loanVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String loanId = loanVo.getLoanId();
		BizLoanDataVo pvo = bizLoanDataDao.findByKey(loanId);
		notNull(pvo,ErrorCode.CODE_2011,ErrorCode.CODE_2011_MSG);
		BizCheck.isTrue(pvo.getCreditSt().equals(CreditSt.TOBANK), "", "提交银行状态可进行贷款失败操作");
		BizCheck.isTrue(loanVo.getLoanAt().compareTo(0l)>0, "", "贷款金额必须大于0");

//		pvo.setMchntCode(loanVo.getMchntCode());
//		pvo.setCupLoanId(loanVo.getCupLoanId());
//		pvo.setLicNo(loanVo.getLicNo());
//		pvo.setArtifCertifId(loanVo.getArtifCertifId());
//		pvo.setArtifNm(loanVo.getArtifNm());
//		pvo.setCreditSt(loanVo.getCreditSt());
//		pvo.setMchntSt(loanVo.getMchntSt());
//		pvo.setCancelFlag(loanVo.getCancelFlag());
//		pvo.setLenderId(loanVo.getLenderId());
//		pvo.setReqLoanAt(loanVo.getReqLoanAt());
		pvo.setLoanAt(loanVo.getLoanAt());
		pvo.setLoanCycle(loanVo.getLoanCycle());
		pvo.setLoanRt(loanVo.getLoanRt());
		pvo.setCardCreditLimit(loanVo.getCardCreditLimit());
		pvo.setCardUseLimit(loanVo.getCardUseLimit());
		pvo.setCardExpire(loanVo.getCardExpire());
		pvo.setCardNo(loanVo.getCardNo());
		
		pvo.setLoanSuccTm(loanVo.getLoanSuccTm());
		pvo.setLoanBalance(loanVo.getLoanBalance());
//		pvo.setLoanProd(loanVo.getLoanProd());
//		pvo.setSvcFeeBenefit(loanVo.getSvcFeeBenefit());
//		pvo.setAllotTp(loanVo.getAllotTp());
//		pvo.setExceedAt(loanVo.getExceedAt());
//		pvo.setExceedNum(loanVo.getExceedNum());
		pvo.setLoanExpire(loanVo.getLoanExpire());
		pvo.setRemark(loanVo.getRemark());
		pvo.setCreditSt(CreditSt.LOAN_FAIL);
		
		pvo.setVersion(loanVo.getVersion());
		// 设置操作信息
		SysTimeBean timeBean = sysTimeDao.findDatabaseTime();
		pvo.setLoanProcResp(loanVo.getLoanProcResp());
		pvo.setLoanProcAcc(userVo.getLoginId());
		pvo.setLoanProcTime(timeBean.getDataBaseTime());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizLoanDataDao, pvo));
	}
	
	public ExtData<CmMediaResVo> zipFileAndSendMaill(File dir, Map<String,String> map,String datestr)throws Exception {
//		String medUrls = "";
//		String fileName = datestr+".zip";
//		CmMediaResVo mediaVo = mediaImageService.doGenerateLoanZipFile(dir, map, fileName, datestr);
//		String medPath = mediaVo.getMedPath();
//		if(medPath.startsWith("/")){
//			mediaVo.setMedPath(medPath.replaceFirst("\\/", ""));
//		}
//		ExtData<CmMediaResVo> data = ExtDataUtil.genWithSingleData(mediaVo);		
		return null;
	}
	
	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> findLoan(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(BizLoanDataDao.MAPPED_FIND, filters, page);
	}		
	
	/**
	 * 查询代理商户下的所有贷款信息.
	 *
	 * @param customerCode the customer code
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> findLoanbyAgentCustomerCode(Long customerCode, Page page)
			throws Exception {
		return ExtDataUtil.genWithData(bizLoanDataDao.filterQuerybyAgentCustomerCode(customerCode));
	}
	
	public ExtData<BizLoanDataVo> findBizLoanDataCustomerCode(Long customerCode, Page page)
			throws Exception {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("mchntCode",Filter.NUMERIC, customerCode,Filter.Comparison.EQ));
//		List<BizLoanDataVo> list = (List<BizLoanDataVo>) getQueryAssist().page(bizLoanDataDao.MAPPED_FIND, filters , page);
		return getQueryAssist().page(BizLoanDataDao.MAPPED_FIND, filters, page);
//		return ExtDataUtil.genWithData(bizLoanDataDao.findBizLoanDataCustomerCode(customerCode));
	}

	public List<BizMchntAttachVo> findWebMemberInfoAttach(Long customerCode) throws BaseException {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("mchntCode",Filter.NUMERIC, customerCode,Filter.Comparison.EQ));
		List<BizMchntAttachVo> list = getQueryAssist().list(BizMchntAttachDao.MAPPED_FIND, filters , new Page("DESC", "attachTime") );
		return list;
	}
	
	/**
	 * Find BizLoanDataVo by Primary Key. 
	 * 
	 * @param loanId : 贷款编号	 
	 * @return BizLoanDataVo : [New]贷款记录信息表
	 */
	public BizLoanDataVo findBizLoanDataByLoanId(String loanId)
			throws Exception {
		return bizLoanDataDao.findByKey(loanId);
	}
	
	
	/**
	 * 设置 the dao.
	 *
	 * @param bizLoanDataDao the bizLoanDataDao to set
	 */
	public void setBizLoanDataDao(BizLoanDataDao bizLoanDataDao) {
		this.bizLoanDataDao = bizLoanDataDao;
	}

	/**
	 * 设置 商户Dao.
	 *
	 * @param bizMerchantDao the bizMerchantDao to set
	 */
	public void setBizMerchantDao(BizMerchantDao bizMerchantDao) {
		this.bizMerchantDao = bizMerchantDao;
	}

	/**
	 * Sets the sys time dao.
	 *
	 * @param sysTimeDao the sysTimeDao to set
	 */
	public void setSysTimeDao(ISysTimeDao sysTimeDao) {
		this.sysTimeDao = sysTimeDao;
	}

	public void setBizMchntAttachDao(BizMchntAttachDao bizMchntAttachDao) {
		this.bizMchntAttachDao = bizMchntAttachDao;
	}

	public void setMediaImageService(IMediaImageService mediaImageService) {
		this.mediaImageService = mediaImageService;
	}
	
	public void setMailService(IMailService mailService) {
		this.mailService = mailService;
	}
	
}
