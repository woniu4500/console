package com.jiuyv.hhc.console.information.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.information.service.ICmArtCatService;
import com.jiuyv.hhc.console.security.util.TreeUtil;
import com.jiuyv.hhc.model.information.CmArtCatVo;
import com.jiuyv.hhc.model.information.TreeCat;

/**
 * 栏目信息.
 *

 * @author 
 * @since 2014-1-3 15:59:50
 * @version 1.0.0
 */
public class CmArtCatAction extends DefaultPageSupportAction {

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	/** The Service. */
	private ICmArtCatService service;
	
	/** The OR g_ list. */
	private static List<CellDataType> columns = new ArrayList<CellDataType>();
	
	static {
		columns.add(new CellDataType("catSeq", "栏目编号"));
		columns.add(new CellDataType("catParent", "上级栏目"));
		columns.add(new CellDataType("catName", "名称"));
		columns.add(new CellDataType("catTitle", "栏目副标题"));
		columns.add(new CellDataType("catCode", "栏目代码"));
		columns.add(new CellDataType("catLogo", "栏目LOGO"));
		columns.add(new CellDataType("catBanner", "栏目BAN"));
		columns.add(new CellDataType("catSort", "排序"));
		columns.add(new CellDataType("isVisible", "是否显示"));
		columns.add(new CellDataType("catStatus", "栏目状态"));
		columns.add(new CellDataType("recCrtTime", "记录创建时间"));
		columns.add(new CellDataType("recCrtAcc", "记录创建用户帐号"));
		columns.add(new CellDataType("lastUptOrg", "最后更新机构"));
		columns.add(new CellDataType("lastUptTime", "最后修改时间"));
		columns.add(new CellDataType("lastUptAcc", "最后修改用户帐号"));
	}

	/**
	 * 查询栏目信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmArtCat() throws Exception {
		return resultData(service.findCmArtCat(getOrgFilters(FLD_ORG_CODE), getPageInfo()));
	}

	/**
	 * 栏目查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmArtCatExcel() throws Exception {
		return defaultExportXLS("栏目信息", columns
				, service.findCmArtCat(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 查出某栏目可选上级栏目（不含本身及其子栏目...）.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmArtCatCombo() throws Exception {
		List<Filter> filters = new ArrayList<Filter>();
		List<TreeCat> data = service.findAllCat(filters, getExcelPageInfo());
		TreeCat root = new TreeCat();
		List<TreeCat> reslist = TreeUtil.removeNode(data, getParameter("catSeq"),root);
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
	public String doInsertCmArtCat() throws Exception {
		return resultData(service.doInsertCmArtCat(getValidateBean(CmArtCatVo.class),getUserDetailInfo()));
	}

	/**
	 * 修改.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateCmArtCat() throws Exception {
		return resultData(service.doUpdateCmArtCat(getValidateBean(CmArtCatVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 删除.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDeleteCmArtCat() throws Exception {
		return resultData(service.doDeleteCmArtCat(getValidateBean(CmArtCatVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 提交.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doCommitCmArtCat() throws Exception {
		return resultData(service.doCommitCmArtCat(getValidateBean(CmArtCatVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 驳回.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doRejectCmArtCat() throws Exception {
		return resultData(service.doRejectCmArtCat(getValidateBean(CmArtCatVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 发布.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doReleaseCmArtCat() throws Exception {
		return resultData(service.doReleaseCmArtCat(getValidateBean(CmArtCatVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 查询栏目信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmArtCatbyCustomerCode() throws Exception {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("customerCode", Filter.STRING, getStringField("customerCode"), Filter.Comparison.EQ));
		return resultData(service.findCmArtCat(filters, getPageInfo()));
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
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(ICmArtCatService service) {
		this.service = service;
	}

}
