package com.jiuyv.hhc.console.loan.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.loan.service.IMhtTransAfterLoanService;
import com.jiuyv.hhc.model.loan.MhtTransAfterLoanVo;

/**
 * 贷款信息.
 *

 * @author 
 * @since 2014-1-3 15:59:50
 * @version 1.0.0
 */
public class MhtTransAfterLoanAction extends DefaultPageSupportAction {

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	/** The Service. */
	private IMhtTransAfterLoanService service;

	/** The OR g_ list. */
	private static List<CellDataType> AFT_COL = new ArrayList<CellDataType>();
	static {
		AFT_COL.add(new CellDataType("transDt", "交易日期", CellDataRender.date));
		AFT_COL.add(new CellDataType("daliyTransAt", "当日交易额", CellDataRender.money));
		AFT_COL.add(new CellDataType("daliyTransNum", "当日交易笔数"));
		AFT_COL.add(new CellDataType("daliyCusNum", "当日消费客户数"));
		AFT_COL.add(new CellDataType("daliyRepeatCusNum", "当日重复消费客户数"));
		AFT_COL.add(new CellDataType("creditDebitRatio", "贷记卡与借记卡交易比"));
	}

	/**
	 * 查询贷后交易信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtTransAfterLoan() throws Exception {
		return resultData(service.findMhtTransAfterLoan(getFilters(), getPageInfo()));
	}
	/**
	 * 查询贷后交易信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtTransAfterLoanbyMchntCode() throws Exception {
		return resultData(service.findMhtTransAfterLoan(getFilters(), getPageInfo()));
	}
	/**
	 * 查询贷后交易信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtTransAfterLoanbyMchntCodeExcel() throws Exception {
		return defaultExportXLS("贷后交易信息", AFT_COL
				, service.findMhtTransAfterLoan(getFilters(), getPageInfo()));
	}

	/**
	 * 贷后交易查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtTransAfterLoanExcel() throws Exception {
		return defaultExportXLS("贷后交易信息", AFT_COL
				, service.findMhtTransAfterLoan(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertMhtTransAfterLoan() throws Exception {
		return resultData(service.doInsertMhtTransAfterLoan(getValidateBean(MhtTransAfterLoanVo.class),getUserDetailInfo()));
	}

	
	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IMhtTransAfterLoanService service) {
		this.service = service;
	}

}
