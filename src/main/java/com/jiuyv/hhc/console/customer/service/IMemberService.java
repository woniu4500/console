package com.jiuyv.hhc.console.customer.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.customer.MemberInfoVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface IMemberService.
 *

 * @author 
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
@Deprecated
public interface IMemberService {
	
	/**
	 * 新增.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	ExtData<MemberInfoVo> doInsertMember(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 修改.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	ExtData<MemberInfoVo> doUpdateMember(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 删除.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	ExtData<MemberInfoVo> doDeleteMember(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 解锁.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	ExtData<MemberInfoVo> doRecoverMember(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception;
	/**
	 * 重置密码.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	ExtData<MemberInfoVo> doResetMemberPasswd(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception;
	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	ExtData<MemberInfoVo> findMember(List<Filter> filters, Page page) throws Exception;

}
