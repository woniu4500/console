package com.jiuyv.hhc.console.security.service.impl;

import static com.jiuyv.common.validate.BizCheck.notEmpty;
import static com.jiuyv.common.validate.BizCheck.notNull;
import static com.jiuyv.common.validate.BizCheck.same;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.security.service.IRoleService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.security.SysRoleResVo;
import com.jiuyv.hhc.model.security.SysRoleVo;
import com.jiuyv.hhc.model.security.SysUserVo;
import com.jiuyv.hhc.model.security.dao.SysRoleDao;
import com.jiuyv.hhc.model.security.dao.SysRoleResDao;


/**
 * The Class OrgServiceImpl.
 *

 * @author 
 * @since 2014-1-3 15:33:22
 * @version 1.0.0
 */
public class RoleServiceImpl extends AssistService implements IRoleService {

	/** Constant String STATUS_NORMAL: String :. */
	private static final String STATUS_NORMAL = "0";
	
	/** Constant String STATUS_DEL: String :. */
	private static final String STATUS_DEL = "1";
	
	/** 角色ID. */
	private static final String ROLE_ID = "roleId";
	
	/** Role dao. */
	private SysRoleDao sysRoleDao;

	/** Role Res Dao. */
	private SysRoleResDao sysRoleResDao ; 
	
	/**
	 * 新增角色.
	 *
	 * @param roleVo the role vo
	 * @param list the list
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysRoleVo> doInsertRole(SysRoleVo roleVo, List<SysRoleResVo> list,
			List<Filter> filters, SysUserVo userVo) throws Exception {
		notNull(roleVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		notEmpty(list, ErrorCode.CODE_1001, ErrorCode.CODE_1001_MSG );
		roleVo.setStatus(STATUS_NORMAL);
		roleVo.setLastUptAcc(userVo.getLoginId());
		roleVo.setLastUptOrg(userVo.getOrgCode());
		SysRoleVo dbRole = getQueryAssist().insertFetch(sysRoleDao, roleVo);
		Long roleId = dbRole.getRoleId();
		sysRoleResDao.deleteByRole(roleId);
		for ( SysRoleResVo rr: list ) {
			rr.setRoleId(roleId);
		}
		getQueryAssist().insertList(sysRoleResDao, list);
		DBLogUtil.addLogInfo(dbRole, list);
		return ExtDataUtil.genWithSingleData(dbRole);
	}

	/**
	 * 删除角色.
	 *
	 * @param roleVo the role vo
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysRoleVo> doDeleteRole(SysRoleVo roleVo, List<Filter> filters, SysUserVo userVo)
			throws Exception {
		notNull(roleVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long roleId = roleVo.getRoleId();
		SysRoleVo dbrole = sysRoleDao.findByKey(roleId);
		notNull(dbrole,ErrorCode.CODE_1001, ErrorCode.CODE_1001_MSG_1);
		same(dbrole.getStatus(),STATUS_NORMAL,ErrorCode.CODE_1003, ErrorCode.CODE_1003_MSG);
		dbrole.setVersion(roleVo.getVersion());
		dbrole.setStatus(STATUS_DEL);
		dbrole.setLastUptAcc(userVo.getLoginId());
		dbrole.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(dbrole);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(sysRoleDao, dbrole)) ;
	}

	/**
	 * 修改角色.
	 *
	 * @param roleVo the role vo
	 * @param list the list
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysRoleVo> doUpdateRole(SysRoleVo roleVo, List<SysRoleResVo> list,
			List<Filter> filters, SysUserVo userVo) throws Exception {
		notNull(roleVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		notEmpty(list, ErrorCode.CODE_1001, ErrorCode.CODE_1001_MSG );
		Long roleId = roleVo.getRoleId();
		SysRoleVo dbrole = sysRoleDao.findByKey(roleId);
		notNull(dbrole,ErrorCode.CODE_1001, ErrorCode.CODE_1001_MSG_1);
		dbrole.setOrgCode(roleVo.getOrgCode());
		dbrole.setVersion(roleVo.getVersion());
		dbrole.setRoleName(roleVo.getRoleName());
		dbrole.setLastUptAcc(userVo.getLoginId());
		dbrole.setLastUptOrg(userVo.getOrgCode());
		SysRoleVo pvo = getQueryAssist().updateFetch(sysRoleDao, dbrole); 
		sysRoleResDao.deleteByRole(roleId);
		for ( SysRoleResVo rr: list ) {
			rr.setRoleId(roleId);
		}
		getQueryAssist().insertList(sysRoleResDao, list);
		DBLogUtil.addLogInfo(pvo, list);
		return ExtDataUtil.genWithSingleData(pvo);
	}

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
	public ExtData<SysRoleVo> findRole(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(SysRoleDao.MAPPED_FIND, filters, page);
	}

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
	public List<SysRoleResVo> findRecListByRole(SysRoleVo roleVo) throws Exception {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter(ROLE_ID, Filter.STRING, roleVo.getRoleId(), Filter.Comparison.EQ));
		return getQueryAssist().list(SysRoleResDao.MAPPED_FIND, filters, new Page("orderNum"));
	}

	/**
	 * 设置 role dao.
	 *
	 * @param sysRoleDao the new role dao
	 */
	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	/**
	 * 设置 role Res Dao.
	 *
	 * @param sysRoleResDao the new role Res Dao
	 */
	public void setSysRoleResDao(SysRoleResDao sysRoleResDao) {
		this.sysRoleResDao = sysRoleResDao;
	}

}
