package com.jiuyv.hhc.console.security.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.security.SysOrgVo;
import com.jiuyv.hhc.model.security.SysUserVo;
import com.jiuyv.hhc.model.security.TreeOrg;

/**
 * The Interface IOrgService.
 *

 * @author 
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
public interface IOrgService {
	
	/**
	 * 新增机构.
	 *
	 * @param orgVo the org vo
	 * @param filters the filters
	 * @return the ext data< org vo>
	 * @throws Exception the exception
	 */
	ExtData<SysOrgVo> doInsertOrg(SysOrgVo orgVo, List<Filter> filters, SysUserVo userVo)
			throws Exception;

	/**
	 * 修改机构.
	 *
	 * @param orgVo the org vo
	 * @param filters the filters
	 * @return the ext data< org vo>
	 * @throws Exception the exception
	 */
	ExtData<SysOrgVo> doUpdateOrg(SysOrgVo orgVo, List<Filter> filters, SysUserVo userVo)
			throws Exception;

	/**
	 * 删除机构.
	 *
	 * @param orgVo the org vo
	 * @param filters the filters
	 * @return the ext data< org vo>
	 * @throws Exception the exception
	 */
	ExtData<SysOrgVo> doDeleteOrg(SysOrgVo orgVo, List<Filter> filters, SysUserVo userVo)
			throws Exception;

	/**
	 * 查询机构信息.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< org vo>
	 * @throws Exception the exception
	 */
	ExtData<SysOrgVo> findOrg(List<Filter> filters, Page page) throws Exception;

	/**
	 * Find all org.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the list
	 * @throws Exception the exception
	 */
	List<TreeOrg> findAllOrg(List<Filter> filters, Page page)
			throws Exception;
}
