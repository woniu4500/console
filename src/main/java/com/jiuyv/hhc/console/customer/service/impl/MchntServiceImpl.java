package com.jiuyv.hhc.console.customer.service.impl;

import static com.jiuyv.common.validate.BizCheck.notNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.validate.BizCheck;
import com.jiuyv.hhc.console.customer.service.IMchntService;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.customer.MhtBaseInfoVo;
import com.jiuyv.hhc.model.customer.dao.MhtBaseInfoDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class MemberServiceImpl.
 *

 * @author 
 * @since 2014-1-3 15:45:38
 * @version 1.0.0
 */
public class MchntServiceImpl extends AssistService implements IMchntService {
	
	/** The dao. */
	private MhtBaseInfoDao mhtBaseInfoDao;

	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	public ExtData<MhtBaseInfoVo> findMchnt(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(MhtBaseInfoDao.MAPPED_FIND, filters, page);
	}
	
	/**
	 * 查询代理用户下所有商户信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public ExtData<MhtBaseInfoVo> findMchntbyAgentCustomerCode(Long customerCode, Page page)
			throws Exception {
		return ExtDataUtil.genWithData(mhtBaseInfoDao.filterQuerybyAgentCustomerCode(customerCode));
	}
	
	/**
	 * 修改.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	public ExtData<MhtBaseInfoVo> doUpdateMchnt(MhtBaseInfoVo vo,SysUserVo userVo)
			throws Exception{
		notNull(vo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long mchntCode = vo.getMchntCode();
		MhtBaseInfoVo pvo = mhtBaseInfoDao.findByKey(mchntCode);
		notNull(pvo,ErrorCode.CODE_2021,ErrorCode.CODE_2021_MSG);
		pvo.setVersion(vo.getVersion());
		pvo.setMchntSt(vo.getMchntSt());
		pvo.setAcqInsIdCd(vo.getAcqInsIdCd());
		pvo.setArtifCertifId(vo.getArtifCertifId());
		pvo.setArtifCertifType(vo.getArtifCertifType());
		pvo.setArtifNm(vo.getArtifNm());
		pvo.setLastUptAcc("customer");
		pvo.setLastUptOrg("customer");
		pvo.setLicNo(vo.getLicNo());
		pvo.setMchntCnName(vo.getMchntCnName());
		pvo.setMchntCrtDt(vo.getMchntCrtDt());
		pvo.setMchntGrp(vo.getMchntGrp());
		pvo.setMchntTp(vo.getMchntTp());
		pvo.setPhone(vo.getPhone());
		pvo.setPosNum(vo.getPosNum());
		pvo.setRegAddr(vo.getRegAddr());
		pvo.setReveRegCd(vo.getReveRegCd());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(mhtBaseInfoDao, pvo));
	}
	
	/**
	 * 
	 * @param mhtVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.customer.service.IMchntService#doAddMchnt(com.jiuyv.hhc.model.customer.MhtBaseInfoVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<MhtBaseInfoVo> doAddMchnt(MhtBaseInfoVo mhtVo,
			SysUserVo userVo) throws Exception {
		BizCheck.notBlank(mhtVo.getLicNo(), "", "营业执照号不能为空");
		BizCheck.notBlank(mhtVo.getMchntCd(), "", "商户号不能为空");
		// 检查银联商户号是否已经注册
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("mchntCd", Filter.STRING, mhtVo.getMchntCd(), Filter.Comparison.EQ));
		Long count = getQueryAssist().count(MhtBaseInfoDao.MAPPED_FIND, filters);
		if ( count.compareTo(0l) > 0 ) {
			throw new BaseException("","该银联商户号["+mhtVo.getMchntCd()+"]已注册，请操作原商户记录。");
		}
		if ( mhtVo.getCustomerCode() == null ) {
			mhtVo.setCustomerCode(1001l);
		}
		mhtVo.setRecAcc(userVo.getLoginId());
		mhtVo.setLastUptAcc(userVo.getLoginId());
		mhtVo.setLastUptOrg(userVo.getOrgCode());
		mhtVo.setMchntSt("A0");
		mhtVo.setVersion(0l);
		
		MhtBaseInfoVo dvo = getQueryAssist().insertFetch(mhtBaseInfoDao, mhtVo);
		return ExtDataUtil.genWithSingleData(dvo);
	}
	
	/**
	 * 设置 the dao.
	 *
	 * @param memberInfoDao the new dao
	 */

	public void setMhtBaseInfoDao(MhtBaseInfoDao mhtBaseInfoDao) {
		this.mhtBaseInfoDao = mhtBaseInfoDao;
	}

}
