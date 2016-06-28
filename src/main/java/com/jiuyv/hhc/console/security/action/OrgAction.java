package com.jiuyv.hhc.console.security.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.security.service.IOrgService;
import com.jiuyv.hhc.console.security.util.TreeUtil;
import com.jiuyv.hhc.model.security.SysOrgVo;
import com.jiuyv.hhc.model.security.TreeOrg;

/**
 * 机构信息的增、删、改、查.
 *

 * @author 
 * @since 2014-1-3 15:59:50
 * @version 1.0.0
 */
public class OrgAction extends DefaultPageSupportAction {

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	/** Constant String FLD_STATUS: String :. */
	private static final String FLD_STATUS = "status";
	
	/** Constant String STATUS_NORMAL: String :. */
	private static final String STATUS_NORMAL = "0";
	
	/** The Service. */
	private IOrgService service;

	/** The OR g_ list. */
	private static List<CellDataType> orgList = new ArrayList<CellDataType>();
	static {
		orgList.add(new CellDataType(FLD_ORG_CODE, "机构代码"));
		orgList.add(new CellDataType("orgName", "机构名称"));
		orgList.add(new CellDataType("fatherOrgName", "上级机构"));
		orgList.add(new CellDataType("orgTypeDesc", "机构类型"));
		orgList.add(new CellDataType("statusDesc", "状态"));
	}
	
	/** The String orgid. */
	private String orgid;

	/**
	 * Gets the orgid.
	 *
	 * @return the orgid
	 */
	public String getOrgid() {
		return orgid;
	}

	/**
	 * Sets the orgid.
	 *
	 * @param orgid the new orgid
	 */
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	/**
	 * 查询机构信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findOrg() throws Exception {
		return resultData(service.findOrg(getOrgFilters(FLD_ORG_CODE), getPageInfo()));
	}

	/**
	 * 机构查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findOrgExcel() throws Exception {
		return defaultExportXLS("机构信息", orgList
				, service.findOrg(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 机构查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findOrgCombo() throws Exception {
		List<Filter> filters = getOrgFilters(FLD_ORG_CODE);
		filters.add(new Filter(FLD_STATUS, Filter.STRING, STATUS_NORMAL, Filter.Comparison.EQ));
		return resultData(service.findOrg(filters, getExcelPageInfo()));
	}

	/**
	 * 查出某机构可选上级机构（不含本身及其子机构...）.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findFatherOrgCombo() throws Exception {
		List<Filter> filters = getOrgFilters(FLD_ORG_CODE);
		filters.add(new Filter(FLD_STATUS, Filter.STRING, STATUS_NORMAL, Filter.Comparison.EQ));
		List<TreeOrg> data = service.findAllOrg(filters, getExcelPageInfo());
		TreeOrg root = new TreeOrg();
		root.setOrgCode(TreeUtil.ROOT_NODE);
		root.setFatherOrgCode(null);
		List<TreeOrg> reslist = TreeUtil.removeNode(data, orgid, root);
		return resultData(ExtDataUtil.genWithData(reslist));
	}

	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertOrg() throws Exception {
		ExtData<SysOrgVo> data = service.doInsertOrg( getValidateBean(SysOrgVo.class), getOrgFilters("fatherOrgCode"), getUserDetailInfo());
		// 需要将用户的orglist修改
		getUserDetailInfo().getChildOrgId().add(data.getRoot().get(0).getOrgCode());
		return resultData(data);
	}

	/**
	 * 修改.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateOrg() throws Exception {
		return resultData(service.doUpdateOrg(getValidateBean(SysOrgVo.class), getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}

	/**
	 * 删除.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDeleteOrg() throws Exception {
		return resultData(service.doDeleteOrg( getValidateBean(SysOrgVo.class), getOrgFilters(FLD_ORG_CODE), getUserDetailInfo()));
	}

	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IOrgService service) {
		this.service = service;
	}

}
