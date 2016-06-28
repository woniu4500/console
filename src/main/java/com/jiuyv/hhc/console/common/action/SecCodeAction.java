package com.jiuyv.hhc.console.common.action;

import java.util.List;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.common.service.ISecService;


/**
 * The Class SecCodeAction.
 *

 * @author 
 * @since 2014-1-2 16:39:27
 * @version 1.0.0
 */
public class SecCodeAction extends DefaultPageSupportAction {
	
	/** The Constant CODE_TYPE. */
	private static final String CODE_TYPE = "codeType";
	
	/** The Constant CODE_SORT. */
	private static final String CODE_SORT = "codeSort";
	/** The Service. */
	private ISecService service;

	/** 参数的过滤参数 *. */
	private String codeType;
	
	/** The combo id. */
	private String comboId;


	/**
	 * Find sec code list.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findSecCodeList() throws Exception {
		List<Filter> tempFilters = getFilters();
		tempFilters.add(new Filter(CODE_TYPE,Filter.STRING, this.getCodeType(), Filter.Comparison.EQ));
		Page page = getExcelPageInfo();
		page.setSort(CODE_SORT);
		page.setDir("DESC");
		resultData(service.findSecCodeList(tempFilters, page));
		return SUCCESS;
	}
	
	/**
	 * Find sec code list fuzzy.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findSecCodeListFuzzy() throws Exception {
		List<Filter> tempFilters = getFilters();
		tempFilters.add(new Filter(CODE_TYPE, Filter.STRING, this.getCodeType(), Filter.Comparison.LIKE));
		Page page = getExcelPageInfo();
		page.setSort(CODE_SORT);
		page.setDir("DESC");
		resultData(service.findSecCodeList(tempFilters, page));
		return SUCCESS;
	}
	
	/**
	 * Find sec code list type name.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findSecCodeListTypeName() throws Exception {
		List<Filter> tempFilters = getFilters();
		tempFilters.add(new Filter(CODE_TYPE, Filter.STRING, this.getCodeType(), Filter.Comparison.EQ));
		tempFilters.add(new Filter(CODE_SORT, Filter.NUMERIC, "0", Filter.Comparison.EQ));
		resultData(service.findSecCodeList(tempFilters));
		return SUCCESS;
	}
	
	/**
	 * 借用sys_code的reserved1字段，标识区分不同机构类型用户所能看到的下拉框，
	 * 适用于reserved1标识为{manager/merchant}的SYS_CODE数据.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findSecCodeListOrgDiff() throws Exception {
		List<Filter> tempFilters = getFilters();
		tempFilters.add(new Filter(CODE_TYPE,Filter.STRING, this.getCodeType(), Filter.Comparison.EQ));
		tempFilters.add(new Filter("reserved1",Filter.STRING,this.getUserInfo().getOrgCode().equals("-1")?"manager":"merchant", Filter.Comparison.EQ));
		Page page = getExcelPageInfo();
		page.setSort(CODE_SORT);
		page.setDir("DESC");
		resultData(service.findSecCodeList(tempFilters, page));
		return SUCCESS;
	}

	/**
	 * 获取 the Service.
	 *
	 * @return the Service
	 */
	public ISecService getService() {
		return service;
	}

	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(ISecService service) {
		this.service = service;
	}

	/**
	 * 获取 参数的过滤参数 *.
	 *
	 * @return the 参数的过滤参数 *
	 */
	public String getCodeType() {
		return codeType;
	}

	/**
	 * 设置 参数的过滤参数 *.
	 *
	 * @param codeType the new 参数的过滤参数 *
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * Gets the combo id.
	 *
	 * @return the combo id
	 */
	public String getComboId() {
		return comboId;
	}

	/**
	 * Sets the combo id.
	 *
	 * @param comboId the new combo id
	 */
	public void setComboId(String comboId) {
		this.comboId = comboId;
	}
}
