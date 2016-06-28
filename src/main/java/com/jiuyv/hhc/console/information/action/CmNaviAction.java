package com.jiuyv.hhc.console.information.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.information.service.ICmNaviService;
import com.jiuyv.hhc.model.information.CmNaviVo;

public class CmNaviAction extends DefaultPageSupportAction {

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	private ICmNaviService service;

	private static List<CellDataType> columns = new ArrayList<CellDataType>();
	
	static {
		columns.add(new CellDataType("naviSeq", "内部序号"));
		columns.add(new CellDataType("version", "VERSION"));
		columns.add(new CellDataType("naviStatus", "状态"));
		columns.add(new CellDataType("isBlankTarget", "是否在新窗口中打开"));
		columns.add(new CellDataType("isVisible", "是否显示"));
		columns.add(new CellDataType("isHret", "是否为外链"));
		columns.add(new CellDataType("naviName", "名称"));
		columns.add(new CellDataType("naviTitle", "副标题"));
		columns.add(new CellDataType("naviLogo", "导航LOGO"));
		columns.add(new CellDataType("naviBanner", "导航BAN"));
		columns.add(new CellDataType("naviStyle", "样式类别"));
		columns.add(new CellDataType("naviPosition", "导航位置"));
		columns.add(new CellDataType("naviOrderList", "导航顺序"));
		columns.add(new CellDataType("naviUrl", "导航地址"));
		columns.add(new CellDataType("recCrtTime", "记录创建时间"));
		columns.add(new CellDataType("recCrtAcc", "记录创建用户帐号"));
		columns.add(new CellDataType("lastUptOrg", "最后更新机构"));
		columns.add(new CellDataType("lastUptTime", "最后修改时间"));
		columns.add(new CellDataType("lastUptAcc", "最后修改用户帐号"));
	}
	
	/**
	 * 查询导航信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmNavi() throws Exception {
		return resultData(service.find(getOrgFilters(FLD_ORG_CODE), getPageInfo()));
	}

	/**
	 * 导航查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmNaviExcel() throws Exception {
		return defaultExportXLS("导航信息", columns
				, service.find(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}


	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertCmNavi() throws Exception {
		return resultData(service.doInsert(getValidateBean(CmNaviVo.class),getUserDetailInfo()));
	}

	/**
	 * 修改.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateCmNavi() throws Exception {
		return resultData(service.doUpdate(getValidateBean(CmNaviVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 删除.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDeleteCmNavi() throws Exception {
		return resultData(service.doDelete(getValidateBean(CmNaviVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 提交.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doCommitCmNavi() throws Exception {
		return resultData(service.doCommit(getValidateBean(CmNaviVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 驳回.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doRejectCmNavi() throws Exception {
		return resultData(service.doReject(getValidateBean(CmNaviVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 发布.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doReleaseCmNavi() throws Exception {
		return resultData(service.doRelease(getValidateBean(CmNaviVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 查询导航信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmNavibyCustomerCode() throws Exception {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("customerCode", Filter.STRING, getStringField("customerCode"), Filter.Comparison.EQ));
		return resultData(service.find(filters, getPageInfo()));
	}
	
	/**
	 * 审核页面跳转.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doCheckPage() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * @param service the service to set
	 */
	public void setService(ICmNaviService service) {
		this.service = service;
	}
	
	
	
}
