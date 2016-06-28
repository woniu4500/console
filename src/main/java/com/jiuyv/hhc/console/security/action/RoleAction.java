package com.jiuyv.hhc.console.security.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.common.tree.TreeNode;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.security.service.IRoleService;
import com.jiuyv.hhc.console.security.util.ResourceCache;
import com.jiuyv.hhc.console.security.util.TreeUtil;
import com.jiuyv.hhc.model.security.SysRoleResVo;
import com.jiuyv.hhc.model.security.SysRoleVo;

/**
 * 角色信息管理.
 *

 * @author 
 * @since 2014-1-3 16:10:44
 * @version 1.0.0
 */
public class RoleAction extends DefaultPageSupportAction {

	/** The Service. */
	private IRoleService service;

	/** The resoucrecache. */
	private ResourceCache resoucrecache;

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	/** Constant String FLD_RESOURCE: String :. */
	private static final String FLD_RESOURCE = "resource";

	/** The OR g_ list. */
	private static List<CellDataType> orgList = new ArrayList<CellDataType>();
	static {
		orgList.add(new CellDataType("roleId", "角色编号"));
		orgList.add(new CellDataType("roleName", "角色名称"));
		orgList.add(new CellDataType("orgName", "机构名称"));
		orgList.add(new CellDataType("statusDesc", "状态"));
	}

	/**
	 * 查询角色.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findRole() throws Exception {
		return resultData(service.findRole(getOrgFilters(FLD_ORG_CODE), getPageInfo()));
	}

	/**
	 * 角色查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findRoleExcel() throws Exception {
		return defaultExportXLS("角色信息", orgList
				, service.findRole(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 加载选择的树.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String loadTreeWithChecked() throws Exception {
		SysRoleVo rolevo = getValidateBean(SysRoleVo.class);
		List<SysRoleResVo> ress = service.findRecListByRole(rolevo);
		TreeNode node = TreeUtil.buildChkResTree(resoucrecache.getRoleResMap(), ress);
		return resultData(JSONArray.fromObject(node.getChildren()).toString());
	}

	/**
	 * 加载未选中的树.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String loadTreeWithUnChecked() throws Exception {
		TreeNode node = TreeUtil.buildChkResTree(resoucrecache.getRoleResMap(), null);
		return resultData(JSONArray.fromObject(node.getChildren()).toString());
	}

	/**
	 * 新增角色信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertRole() throws Exception {
		SysRoleVo roleVo = getValidateBean(SysRoleVo.class);
		return resultData(service.doInsertRole(roleVo, genRoleResList(roleVo, getStringField(FLD_RESOURCE)), getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}

	/**
	 * 修改角色信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateRole() throws Exception {
		SysRoleVo rolevo = getValidateBean(SysRoleVo.class);
		ExtData<SysRoleVo> data = service.doUpdateRole(rolevo, genRoleResList(rolevo,getStringField(FLD_RESOURCE)), getOrgFilters(FLD_ORG_CODE), getUserDetailInfo());
		return resultData(data);
	}

	/**
	 * 删除角色信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDeleteRole() throws Exception {
		return resultData(service.doDeleteRole(getValidateBean(SysRoleVo.class), getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}

	/**
	 * 转换角色中对应的资源信息.
	 *
	 * @param rolevo the rolevo
	 * @param resource the resource
	 * @return the list< role res vo>
	 */
	public List<SysRoleResVo> genRoleResList(SysRoleVo rolevo, String resource) {
		List<SysRoleResVo> list = new ArrayList<SysRoleResVo>();
		if (StringUtils.isBlank(resource)) {
			return null;
		}
		String[] resourceList = resource.split(",");
		Long roleId = rolevo.getRoleId();
		if (resourceList != null) {
			for (int i = 0; i < resourceList.length; i++) {
				if (!resourceList[i].equals(TreeUtil.ROOT_NODE)) {
					SysRoleResVo vo = new SysRoleResVo();
					vo.setRoleId(roleId);
					vo.setResId(resourceList[i].toUpperCase());
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
	public void setService(IRoleService service) {
		this.service = service;
	}

	/**
	 * Sets the resoucrecache.
	 * 
	 * @param resoucrecache
	 *            the new resoucrecache
	 */
	public void setResoucrecache(ResourceCache resoucrecache) {
		this.resoucrecache = resoucrecache;
	}

}
