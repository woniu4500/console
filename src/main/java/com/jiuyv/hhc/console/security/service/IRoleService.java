package com.jiuyv.hhc.console.security.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.security.SysRoleResVo;
import com.jiuyv.hhc.model.security.SysRoleVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface IRoleService.
 *

 * @author 
 * @since 2014-1-2 16:36:14
 * @version 1.0.0
 */
public interface IRoleService {
	
	/**
	 * 新增角色.
	 *
	 * @param roleVo the role vo
	 * @param list the list
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	ExtData<SysRoleVo> doInsertRole(SysRoleVo roleVo, List<SysRoleResVo> list,
			List<Filter> filters, SysUserVo userVo) throws Exception;

	/**
	 * 删除角色.
	 *
	 * @param roleVo the role vo
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	ExtData<SysRoleVo> doDeleteRole(SysRoleVo roleVo, List<Filter> filters, SysUserVo userVo)
			throws Exception;

	/**
	 * 修改角色.
	 *
	 * @param roleVo the role vo
	 * @param list the list
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	ExtData<SysRoleVo> doUpdateRole(SysRoleVo roleVo, List<SysRoleResVo> list,
			List<Filter> filters, SysUserVo userVo) throws Exception;

	/**
	 * 查询角色.
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
	ExtData<SysRoleVo> findRole(List<Filter> filters, Page page)
			throws Exception;

	/**
	 * 查询角色对应的资源信息.
	 * 
	 * @param roleVo
	 *            the role vo
	 * 
	 * @return the ext data< role vo>
	 * 
	 * @throws Exception
	 *             the exception
	 */
	List<SysRoleResVo> findRecListByRole(SysRoleVo roleVo) throws Exception;
}
