package com.jiuyv.hhc.console.mchnt.service.impl;

import static com.jiuyv.common.validate.BizCheck.notNull;
import static com.jiuyv.common.validate.BizCheck.notBlank;
import static com.jiuyv.common.validate.BizCheck.isTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.mchnt.service.IMchntService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.loan.dao.BizLoanDataDao;
import com.jiuyv.hhc.model.mchnt.BizAttachDefVo;
import com.jiuyv.hhc.model.mchnt.BizMchntAttachVo;
import com.jiuyv.hhc.model.mchnt.BizMchntPropVo;
import com.jiuyv.hhc.model.mchnt.BizMerchantVo;
import com.jiuyv.hhc.model.mchnt.dao.BizAttachDefDao;
import com.jiuyv.hhc.model.mchnt.dao.BizMchntAttachDao;
import com.jiuyv.hhc.model.mchnt.dao.BizMchntPropDao;
import com.jiuyv.hhc.model.mchnt.dao.BizMerchantDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * <h1>The Class MchntServiceImpl.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public class MchntServiceImpl extends AssistService implements IMchntService{

	/** The Biz merchant dao biz merchant dao. */
	private BizMerchantDao bizMerchantDao ; 
	/**   */
	private BizMchntAttachDao bizMchntAttachDao;
	
	private BizMchntPropDao bizMchntPropDao ;
	
	private BizLoanDataDao bizLoanDataDao ;
	
	/**
	 * Find mchnt.
	 * 分页查询商户信息
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizMerchantVo> findMchnt(List<Filter> filters, Page page) throws BaseException {
		return getQueryAssist().page(BizMerchantDao.MAPPED_FIND_BRIEF, filters, page);
	}
	
	/**
	 * Find mchnt.
	 * 分页查询商户详细信息
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizMerchantVo> findMchntDetail(List<Filter> filters, Page page) throws BaseException {
		return getQueryAssist().page(BizMerchantDao.MAPPED_FIND, filters, page);
	}
	
	/**
	 * Find mchntby agent customer code.
	 * 分页查询代理会员下所属商户信息
	 *
	 * @param customerCode the customer code
	 * @param page the page
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizMerchantVo> findMchntbyAgentCustomerCode(Long customerCode, Page page) throws BaseException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("customerCode", customerCode);
		return getQueryAssist().page(BizMerchantDao.MAPPED_FIND_AGENT_CODE, null, page, params );
	}

	/**
	 * Do add mchnt.
	 * 添加商户
	 * @param merchant the merchant
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizMerchantVo> doAddMchnt(BizMerchantVo merchant, SysUserVo userInfo) throws BaseException {
		notBlank(merchant.getLicNo(), "", "营业执照号不能为空");
		notBlank(merchant.getMchntCd(), "", "商户号不能为空");
		if ( merchant.getCustomerCode() == null ) {
			merchant.setCustomerCode(1001l);
		}
		merchant.setRecAcc(userInfo.getLoginId());
		merchant.setLastUptAcc(userInfo.getLoginId());
		merchant.setLastUptOrg(userInfo.getOrgCode());
		merchant.setMchntSt("01");
		merchant.setDispMchntSt("01");
		merchant.setVersion(0l);
		// 
		BizMerchantVo dvo = getQueryAssist().insertFetch(bizMerchantDao, merchant);
		BizMchntPropVo prop = new BizMchntPropVo(dvo.getMchntCode());
		bizMchntPropDao.insert(prop);
		return ExtDataUtil.genWithSingleData(dvo);
	}

	/**
	 * Do upd mchnt.
	 * 更新商户
	 *
	 * @param vo the vo
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizMerchantVo> doUpdMchnt(BizMerchantVo vo, SysUserVo userInfo) throws BaseException {
		notNull(vo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long mchntCode = vo.getMchntCode();
		BizMerchantVo pvo = bizMerchantDao.findByKey(mchntCode);
		notNull(pvo, ErrorCode.CODE_2021, ErrorCode.CODE_2021_MSG);
		BizMchntPropVo prop = bizMchntPropDao.findByKey(mchntCode);
		// 商户基本信息
		pvo.setVersion(vo.getVersion());
		pvo.setAcqInsIdCd(vo.getAcqInsIdCd());
		pvo.setArtifCertifId(vo.getArtifCertifId());
		pvo.setArtifCertifType(vo.getArtifCertifType());
		pvo.setArtifNm(vo.getArtifNm());
		pvo.setLicNo(vo.getLicNo());
		pvo.setMchntCnName(vo.getMchntCnName());
		pvo.setMchntCrtDt(vo.getMchntCrtDt());
		pvo.setMchntGrp(vo.getMchntGrp());
		pvo.setMchntTp(vo.getMchntTp());
		pvo.setPhone(vo.getPhone());
		pvo.setPosNum(vo.getPosNum());
		pvo.setRegAddr(vo.getRegAddr());
		pvo.setReveRegCd(vo.getReveRegCd());
		pvo.setLastUptAcc(userInfo.getLoginId());
		pvo.setLastUptOrg(userInfo.getOrgCode());
		// 商户扩展信息
		prop.setMchntEnType(vo.getMchntEnType());
		prop.setMchntDispName(vo.getMchntDispName());
		prop.setMchntContact(vo.getMchntContact());
		prop.setMchntContactMobile(vo.getMchntContactMobile());
		prop.setMchntArtifMobile(vo.getMchntArtifMobile());
		prop.setTelephone(vo.getTelephone());
		prop.setMchntStartDate(vo.getMchntStartDate());
		prop.setRegCapital(vo.getRegCapital());
		prop.setOrgCode(vo.getOrgCode());
		prop.setOthBankName(vo.getOthBankName());
		prop.setOthCardNo(vo.getOthCardNo());
		
		// 更新商户信息
		DBLogUtil.addLogInfo(pvo,prop);
		bizMchntPropDao.updateByKey(prop);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizMerchantDao, pvo));
	}

	/**
	 * Do audit mchnt.
	 * 审核通过，认证
	 * @param merchant the merchant
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizMerchantVo> doAuditMchnt(BizMerchantVo merchant, SysUserVo userInfo) throws BaseException {
		notNull(merchant, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long mchntCode = merchant.getMchntCode();
		BizMerchantVo pvo = bizMerchantDao.findByKey(mchntCode);
		notNull(pvo, ErrorCode.CODE_2021, ErrorCode.CODE_2021_MSG);
		isTrue(!StringUtils.equals(pvo.getMchntSt(),"01"), "", "录入状态不能进行此操作");

		ExtData<BizMerchantVo> checked = findValidMchntForAudit(pvo);
		if ( !checked.isSuccess() ) {
			return checked;
		}
		
		pvo.setLastUptAcc(userInfo.getLoginId());
		pvo.setLastUptOrg(userInfo.getOrgCode());
		pvo.setVersion(merchant.getVersion());
		pvo.setMchntSt("07");
		if ( StringUtils.equals(pvo.getDispMchntSt(), "02") || StringUtils.equals(pvo.getDispMchntSt(), "01") ) {
			pvo.setDispMchntSt("07");
		}
		// 更新贷款记录状态，从提交到商户认证
		bizLoanDataDao.updateLoanToPass(mchntCode, userInfo.getLoginId(), userInfo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizMerchantDao, pvo));
	}
	
	/**
	 * Do deny mchnt.
	 * 审核驳回
	 * @param merchant the merchant
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizMerchantVo> doDenyMchnt(BizMerchantVo merchant, SysUserVo userInfo) throws BaseException {
		notNull(merchant, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long mchntCode = merchant.getMchntCode();
		BizMerchantVo pvo = bizMerchantDao.findByKey(mchntCode);
		notNull(pvo, ErrorCode.CODE_2021, ErrorCode.CODE_2021_MSG);
		isTrue(!StringUtils.equals(pvo.getMchntSt(),"01"), "", "录入状态不能进行此操作");
		
		pvo.setLastUptAcc(userInfo.getLoginId());
		pvo.setLastUptOrg(userInfo.getOrgCode());
		pvo.setVersion(merchant.getVersion());
		pvo.setMchntSt("06");
		// 更新贷款记录状态，从提交到审核驳回
		bizLoanDataDao.updateLoanToDeny(mchntCode, StringUtils.defaultString(merchant.getRemark(),"商户认证被驳回"), userInfo.getLoginId(), userInfo.getOrgCode() );
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(bizMerchantDao, pvo));
	}

	/**
	 * 插入商户附件图片
	 * @param mediaVo
	 * @param mchntCode
	 * @param attachType
	 * @param userInfo
	 * @return
	 */
	public ExtData<BizMchntAttachVo> doInsertAttachFile(CmMediaResVo mediaVo, String mchntCode,
			String attachType, SysUserVo userInfo) {
		BizMchntAttachVo attachVo = new BizMchntAttachVo();
		attachVo.setAttachType(attachType);
		attachVo.setMchntCode(NumberUtils.toLong(mchntCode));
		attachVo.setAttachUrl(mediaVo.getMedPath());
		bizMchntAttachDao.insert(attachVo);
		return ExtDataUtil.genWithSingleData(attachVo);
	}
	
	/**
	 * 删除商户附件
	 * @param attachSeqs
	 * @param userInfo
	 * @return
	 */
	public ExtData<BizMchntAttachVo> doDeleteAttachFile(String attachSeqs, SysUserVo userInfo) {
		String[] attachSeqArr = attachSeqs.split(",");
		List<Long> attachSeqList = new ArrayList<Long>();
		for (String attachSeq : attachSeqArr) {
			Long attachSeqL = NumberUtils.toLong(attachSeq);
			if ( attachSeqL.compareTo(0l)>0) {
				attachSeqList.add(attachSeqL);
			}
		}
		if ( attachSeqList.size() > 0 ) {
			bizMchntAttachDao.deleteBySeqList(attachSeqList);
		}
		return ExtDataUtil.genWithData(new ArrayList<BizMchntAttachVo>());
	}
	
	/**
	 * Find mchnt attach.
	 * 查询商户附件信息
	 * @param merchant the merchant
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizMchntAttachVo> findMchntAttach(BizMerchantVo merchant) throws BaseException {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("mchntCode",Filter.NUMERIC, merchant.getMchntCode(),Filter.Comparison.EQ));
		List<BizMchntAttachVo> list = getQueryAssist().list(BizMchntAttachDao.MAPPED_FIND, filters , new Page("DESC", "attachTime") );
		return ExtDataUtil.genWithData(list);
	}
	
	/**
	 * Find attach def.
	 * 查询附件定义
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizAttachDefVo> findAttachDef() throws BaseException {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("attachGroup", Filter.STRING, "01", Filter.Comparison.EQ));
		List<BizAttachDefVo> list = getQueryAssist().list(BizAttachDefDao.MAPPED_FIND, filters , new Page("ASC","attachSort") );
		return ExtDataUtil.genWithData(list);
	}

	/**
	 * 记录资质申请中提交的压缩文件
	 * @param vo
	 * @param medUrl
	 * @param userInfo
	 * @return
	 * @throws BaseException 
	 */
	public ExtData<BizMchntAttachVo> doMergeZipAttach(BizMerchantVo vo, String medUrl, SysUserVo userInfo) throws BaseException {
		BizMchntAttachVo attachVo = new BizMchntAttachVo();
		attachVo.setAttachUrl(medUrl);
		attachVo.setAttachType("zip");
		attachVo.setMchntCode(vo.getMchntCode());
		// 查询是否已经保存过同一个附件
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("mchntCode",Filter.NUMERIC, vo.getMchntCode(), Filter.Comparison.EQ));
		filters.add(new Filter("attachType",Filter.STRING, "zip", Filter.Comparison.EQ));
		filters.add(new Filter("attachUrl", Filter.STRING, medUrl, Filter.Comparison.EQ));
		Long count = getQueryAssist().count(BizMchntAttachDao.MAPPED_FIND, filters);
		if ( count.compareTo(0l) > 0 ) {
			return ExtDataUtil.genWithSingleData(attachVo);
		}
		// 新增附件
		BizMchntAttachVo res = getQueryAssist().insertFetch(bizMchntAttachDao, attachVo);
		return ExtDataUtil.genWithSingleData(res);
	}
	
	/**
	 * 验证商户号是否可以进行资质审核及认证.
	 *
	 * @param vo the vo
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<BizMerchantVo> findValidMchntForAudit(BizMerchantVo vo) throws BaseException {
		String mchntCd = vo.getMchntCd();
		Long mchntCode = vo.getMchntCode();
		
		Long count = bizMerchantDao.countAuditMchnt(mchntCd, mchntCode);
		if ( count.compareTo(0l) > 0 ) {
			return ExtDataUtil.genWithExceptions("此银联商户号已由其他商户进行认证申请。");
		}
		Long creditCount = bizLoanDataDao.countByMchntCd(mchntCd);
		if ( creditCount.compareTo(0l) > 0 ) {
			return ExtDataUtil.genWithExceptions("此银联商户号尚有贷款未处理或还清。");
		}
		
		return ExtDataUtil.genWithSingleData(vo);
	}
	
	/**
	 * @param bizMerchantDao the bizMerchantDao to set
	 */
	public void setBizMerchantDao(BizMerchantDao bizMerchantDao) {
		this.bizMerchantDao = bizMerchantDao;
	}

	/**
	 * @param bizMchntPropDao the bizMchntPropDao to set
	 */
	public void setBizMchntPropDao(BizMchntPropDao bizMchntPropDao) {
		this.bizMchntPropDao = bizMchntPropDao;
	}

	/**
	 * @param bizMchntAttachDao the bizMchntAttachDao to set
	 */
	public void setBizMchntAttachDao(BizMchntAttachDao bizMchntAttachDao) {
		this.bizMchntAttachDao = bizMchntAttachDao;
	}

	/**
	 * @param bizLoanDataDao the bizLoanDataDao to set
	 */
	public void setBizLoanDataDao(BizLoanDataDao bizLoanDataDao) {
		this.bizLoanDataDao = bizLoanDataDao;
	}
	
}
