package com.jiuyv.hhc.console.security.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.security.service.IRoleService;
import com.jiuyv.hhc.console.security.service.IUserService;
import com.jiuyv.hhc.console.security.util.TreeUtil;
import com.jiuyv.hhc.model.security.SysRoleVo;
import com.jiuyv.hhc.model.security.SysUserRoleVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * 用户信息管理.
 *

 * @author 
 * @since 2014-1-3 16:19:57
 * @version 1.0.0
 */
public class UserAction extends DefaultPageSupportAction {

	/** The Service. */
	private IUserService service;

	/** The resoucrecache. */
	private IRoleService roleService;

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	/** Constant String FLD_ROLES: String :. */
	private static final String FLD_ROLES = "roles";

	/** The OR g_ list. */
	private static List<CellDataType> orgList = new ArrayList<CellDataType>();
	static {
		orgList.add(new CellDataType("userId","操作员编号"));
		orgList.add(new CellDataType("userName","姓名"));
		orgList.add(new CellDataType("loginId", "登录帐号"));
		orgList.add(new CellDataType("orgName", "所属机构"));
		orgList.add(new CellDataType("statusDesc", "状态"));
	}

	/** Constant String STATENAME: String :. */
	private static final String STATENAME = "status";
	
	/** Constant String STATE_VALID: String :. */
	private static final String STATE_VALID = "0";

	/**
	 * 查询用户.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findUser() throws Exception {
		return resultData(service.findUser(getOrgFilters(FLD_ORG_CODE), getPageInfo()));
	}

	/**
	 * 查询用户根据用户Id.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findUserInfoById() throws Exception {
		SysUserVo user = getValidateBean(SysUserVo.class);
		return resultData(service.findUserInfoById(getOrgFilters(FLD_ORG_CODE), user));
	}
	
	/**
	 * 用户查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findUserExcel() throws Exception {
		return defaultExportXLS("用户信息", orgList
				, service.findUser(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 查询用户Combo.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findUserCombo() throws Exception {
		return resultData(service.findUser(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 加载选择的角色列表.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String loadUserTreeWithChecked() throws Exception {
		List<Filter> filters = getOrgFilters(FLD_ORG_CODE);
		filters.add(new Filter(STATENAME, Filter.STRING, STATE_VALID, Filter.Comparison.EQ));
		ExtData<SysRoleVo> data = roleService.findRole(filters, getExcelPageInfo());
		if (data != null && data.isSuccess()) {
			List<SysUserRoleVo> userRole = service.findRoleListByUser(getValidateBean(SysUserVo.class));
			if (data.getRoot() != null) {
				// form role list
				HashMap<Long, SysRoleVo> tempdata = new HashMap<Long, SysRoleVo>();
				for (SysRoleVo m : data.getRoot()) {
					tempdata.put(m.getRoleId(), m);
				}
				for (SysUserRoleVo m : userRole) {
					SysRoleVo temprolevo = tempdata.get(m.getRoleId());
					if (temprolevo != null) {
						temprolevo.setChecked(true);
					}
				}
			}
		}
		return resultData(data);
	}

	/**
	 * 加载未选中的角色列表.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String loadUserTreeWithUnChecked() throws Exception {
		List<Filter> filters = getOrgFilters(FLD_ORG_CODE);
		filters.add(new Filter(STATENAME, Filter.STRING, STATE_VALID, Filter.Comparison.EQ));
		return resultData(roleService.findRole(filters, getExcelPageInfo()));
	}

	/**
	 * 新增用户信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertUser() throws Exception {
		SysUserVo user = getValidateBean(SysUserVo.class);
		return resultData(service.doInsertUser(user, genUserRoleList(user, getStringField(FLD_ROLES)), getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}

	/**
	 * 修改用户信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateUser() throws Exception {
		SysUserVo user = getValidateBean(SysUserVo.class);
		return resultData(service.doUpdateUser(user, genUserRoleList(user, getStringField(FLD_ROLES)), getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}
	
	/**
	 * 修改个人信息.
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateSelfInfo() throws Exception {
		return resultData(service.doUpdateSelfInfo(getValidateBean(SysUserVo.class), getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}

	/**
	 * 重置用户密码信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doResetUserPasswd() throws Exception {
		SysUserVo user = getValidateBean(SysUserVo.class);
		return resultData(service.doResetUserPasswd(user, getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}

	/**
	 * 删除用户信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDeleteUser() throws Exception {
		return resultData(service.doDeleteUser(
				getValidateBean(SysUserVo.class), 
				getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}
	
	/**
	 * 解锁用户.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String doRecoverUser() throws Exception {
		return resultData(service.doRecoverUser(
				getValidateBean(SysUserVo.class),
				getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}

	/**
	 * 转换用户中对应的资源信息.
	 *
	 * @param jsonObject the json object
	 * @param resource the resource
	 * @return the list< role res vo>
	 */
	public List<SysUserRoleVo> genUserRoleList(SysUserVo jsonObject, String resource) {
		List<SysUserRoleVo> list = new ArrayList<SysUserRoleVo>();
		String[] resourceList = resource.split(",");
		Long userId = jsonObject.getUserId();
		if (resourceList != null) {
			for (int i = 0; i < resourceList.length; i++) {
				if (!resourceList[i].equals(TreeUtil.ROOT_NODE)) {
					SysUserRoleVo vo = new SysUserRoleVo();
					vo.setUserId(userId);
					Long roleId = NumberUtils.toLong(resourceList[i]);
					vo.setRoleId(roleId);
					list.add(vo);
				}
			}
		}
		return list;
	}

	/**
	 * Sets the service.
	 * 
	 * @param service
	 *            the new service
	 */
	public void setService(IUserService service) {
		this.service = service;
	}

	/**
	 * Sets the role service.
	 * 
	 * @param roleService
	 *            the new role service
	 */
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}


}
