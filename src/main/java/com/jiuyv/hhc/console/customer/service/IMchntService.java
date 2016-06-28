package com.jiuyv.hhc.console.customer.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.customer.MemberInfoVo;
import com.jiuyv.hhc.model.customer.MhtBaseInfoVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface IMchntService.
 * 

 * @author
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
@Deprecated
public interface IMchntService {

	/**
	 * 查询.
	 * 
	 * @param filters
	 *            the filters
	 * @param page
	 *            the page
	 * @return the ext data< member vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<MhtBaseInfoVo> findMchnt(List<Filter> filters, Page page)
			throws Exception;

	/**
	 * 查询代理用户下所有商户信息.
	 *
	 * @param customerCode the customer code
	 * @param page the page
	 * @return the string
	 * @throws Exception the exception
	 */
	ExtData<MhtBaseInfoVo> findMchntbyAgentCustomerCode(Long customerCode,
			Page page) throws Exception;

	/**
	 * 修改.
	 *
	 * @param memberVo the member vo
	 * @param userVo the user vo
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	ExtData<MhtBaseInfoVo> doUpdateMchnt(MhtBaseInfoVo memberVo,
			SysUserVo userVo) throws Exception;

	
	/**
	 * 新增商户(后台测试功能).
	 *
	 * @param mhtVo the mht vo
	 * @param userVo the user vo
	 * @return the ext data
	 * @throws Exception the exception
	 */
	ExtData<MhtBaseInfoVo> doAddMchnt(MhtBaseInfoVo mhtVo, SysUserVo userVo)
			throws Exception;

}
