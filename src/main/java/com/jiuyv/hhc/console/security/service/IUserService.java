package com.jiuyv.hhc.console.security.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.security.PassWordVo;
import com.jiuyv.hhc.model.security.SysUserRoleVo;
import com.jiuyv.hhc.model.security.SysUserVo;


/**
 * 用户管理.
 *

 * @author 
 * @since 2014-1-2 16:36:32
 * @version 1.0.0
 */
public interface IUserService {

	/**
	 * 新增用户.
	 *
	 * @param userVo the user vo
	 * @param list the list
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	ExtData<SysUserVo> doInsertUser(SysUserVo userVo, List<SysUserRoleVo> list,
			List<Filter> filters, SysUserVo oprUser) throws Exception;

	/**
	 * 删除用户.
	 *
	 * @param userVo the user vo
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	ExtData<SysUserVo> doDeleteUser(SysUserVo userVo, List<Filter> filters, SysUserVo oprUser) throws Exception;
	
	/**
	 * 解冻用户.
	 *
	 * @param userVo the user vo
	 * @param filters the filters
	 * @return the ext data
	 * @throws Exception the exception
	 */
	ExtData<SysUserVo> doRecoverUser(SysUserVo userVo, List<Filter> filters, SysUserVo oprUser) throws Exception;

	/**
	 * 修改用户.
	 *
	 * @param userVo the user vo
	 * @param list the list
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	ExtData<SysUserVo> doUpdateUser(SysUserVo userVo, List<SysUserRoleVo> list,
			List<Filter> filters, SysUserVo oprUser) throws Exception;

	/**
	 * 更新个人信息
	 * @param validateBean
	 * @param filters
	 * @param oprUser
	 * @return
	 */
	ExtData<SysUserVo> doUpdateSelfInfo(SysUserVo validateBean, List<Filter> filters, SysUserVo oprUser) throws Exception;
	
	/**
	 * 修改用户密码.
	 *
	 * @param passWordVo the pass word vo
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	ExtData<SysUserVo> doUpdateUserPasswd(PassWordVo passWordVo,
			List<Filter> filters, SysUserVo oprUser) throws Exception;

	/**
	 * 重置用户密码.
	 *
	 * @param userVo the user vo
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	ExtData<SysUserVo> doResetUserPasswd(SysUserVo userVo, List<Filter> filters, SysUserVo oprUser)
			throws Exception;

	/**
	 * 查询用户.
	 * 
	 * @param filters
	 *            the filters
	 * @param page
	 *            the page
	 * 
	 * @return the ext data< role vo>
	 * 
	 * @throws Exception
	 *             the exception
	 */
	ExtData<SysUserVo> findUser(List<Filter> filters, Page page)
			throws Exception;
	
	
	/**
	 * 查询用户根据用户Id.
	 * 
	 * @param SysUserVo userVo
	 * @return the ext data< role vo>
	 * 
	 * @throws Exception
	 *             the exception
	 */
	ExtData<SysUserVo> findUserInfoById(List<Filter> filters,SysUserVo userVo)
			throws Exception;

	/**
	 * 查询用户对应的角色信息.
	 * 
	 * @param userVo
	 *            the user vo
	 * 
	 * @return the List< role vo>
	 * 
	 * @throws Exception
	 *             the exception
	 */
	List<SysUserRoleVo> findRoleListByUser(SysUserVo userVo) throws Exception;

	/**
	 * 查询用户-角色信息.
	 * 
	 * @param filters
	 *            the filters
	 * @param page
	 *            the page
	 * @return the ext data< UserRoleVo>
	 * 
	 * @throws Exception
	 *             the exception
	 */
	ExtData<SysUserRoleVo> findUserRole(List<Filter> filters, Page page)
			throws Exception;

	
}
