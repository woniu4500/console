package com.jiuyv.hhc.console.mchnt.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.mchnt.BizAttachDefVo;
import com.jiuyv.hhc.model.mchnt.BizMchntAttachVo;
import com.jiuyv.hhc.model.mchnt.BizMerchantVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * <h1>The Interface IMchntService.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public interface IMchntService {

	/**
	 * Find mchnt.
	 * 分页查询商户信息
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMerchantVo> findMchnt(List<Filter> filters, Page pageInfo) throws BaseException;
	
	/**
	 * Find mchnt.
	 * 分页查询商户详细信息
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMerchantVo> findMchntDetail(List<Filter> filters, Page pageInfo) throws BaseException;
	
	/**
	 * Find mchntby agent customer code.
	 * 分页查询代理会员下所属商户信息
	 * @param customerCode the customer code
	 * @param pageInfo the page info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMerchantVo> findMchntbyAgentCustomerCode(Long customerCode, Page pageInfo) throws BaseException;

	/**
	 * Do add mchnt.
	 * 添加商户
	 * @param merchant the merchant
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMerchantVo> doAddMchnt(BizMerchantVo merchant, SysUserVo userInfo) throws BaseException;

	/**
	 * Do upd mchnt.
	 * 更新商户
	 * @param merchant the merchant
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMerchantVo> doUpdMchnt(BizMerchantVo merchant, SysUserVo userInfo) throws BaseException;

	/**
	 * Do audit mchnt.
	 * 审核通过，认证
	 * @param merchant the merchant
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMerchantVo> doAuditMchnt(BizMerchantVo merchant, SysUserVo userInfo) throws BaseException;
	
	/**
	 * Do deny mchnt.
	 * 审核驳回
	 * @param merchant the merchant
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMerchantVo> doDenyMchnt(BizMerchantVo merchant, SysUserVo userInfo) throws BaseException;

	/**
	 * Find mchnt attach.
	 * 查询商户附件信息
	 * @param merchant the merchant
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMchntAttachVo> findMchntAttach(BizMerchantVo merchant) throws BaseException;
	
	/**
	 * Find attach def.
	 * 查询附件定义
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizAttachDefVo> findAttachDef() throws BaseException;

	/**
	 * 记录资质申请中提交的压缩文件.
	 *
	 * @param vo the vo
	 * @param medUrl 图片路径
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMchntAttachVo> doMergeZipAttach(BizMerchantVo vo, String medUrl, SysUserVo userInfo)  throws BaseException;

	
	/**
	 * 验证商户号是否可以进行资质审核及认证.
	 *
	 * @param vo the vo
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<BizMerchantVo> findValidMchntForAudit(BizMerchantVo vo) throws BaseException;

	/**
	 * 插入商户附件图片
	 * @param mediaVo
	 * @param mchntCode
	 * @param attachType
	 * @param userInfo
	 * @return
	 */
	ExtData<BizMchntAttachVo> doInsertAttachFile(CmMediaResVo mediaVo, String mchntCode,
			String attachType, SysUserVo userInfo);

	/**
	 * 删除商户附件
	 * @param attachSeqs
	 * @param userInfo
	 * @return
	 */
	ExtData<BizMchntAttachVo> doDeleteAttachFile(String attachSeqs, SysUserVo userInfo);
	
}
