package com.jiuyv.hhc.console.loan.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.loan.service.IMhtRiskDataService;
import com.jiuyv.hhc.model.loan.MhtRiskDataVo;

/**
 * 贷款信息.
 *

 * @author 
 * @since 2014-1-3 15:59:50
 * @version 1.0.0
 */
public class MhtRiskDataAction extends DefaultPageSupportAction {

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";

	
	/** The Service. */
	private IMhtRiskDataService service;

	/** The OR g_ list. */
	private static List<CellDataType> RISK_COL = new ArrayList<CellDataType>();
	static {
		RISK_COL.add(new CellDataType("fraudTransNum", "近三个月欺诈交易总笔数"));
		RISK_COL.add(new CellDataType("fraudTransAt", "近三个月欺诈交易总金额", CellDataRender.money));
		RISK_COL.add(new CellDataType("susMchntInDesc", "可疑商户名单是否命中"));
		RISK_COL.add(new CellDataType("negCdhdInDesc", "不良持卡人名单是否命中"));
		RISK_COL.add(new CellDataType("recTime", "记录时间"));
	}

	/**
	 * 查询风险信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtRiskData() throws Exception {
		return resultData(service.findMhtRiskData(getFilters(), getPageInfo()));
	}
	
	/**
	 * 查询风险信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtRiskDatabyMchntCode() throws Exception {
		return resultData(service.findMhtRiskData(getFilters(), getPageInfo()));
	}

	/**
	 * 查询风险信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtRiskDatabyMchntCodeExcel() throws Exception {
		return defaultExportXLS("风险信息", RISK_COL
				,service.findMhtRiskData(getFilters(), getExcelPageInfo()));
	}

	/**
	 * 风险信息查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtRiskDataExcel() throws Exception {
		return defaultExportXLS("风险信息", RISK_COL
				, service.findMhtRiskData(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertMhtRiskData() throws Exception {
		return resultData(service.doInsertMhtRiskData(getValidateBean(MhtRiskDataVo.class),getUserDetailInfo()));
	}

	
	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IMhtRiskDataService service) {
		this.service = service;
	}

}
