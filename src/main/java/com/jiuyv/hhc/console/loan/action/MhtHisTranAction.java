package com.jiuyv.hhc.console.loan.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.loan.service.IMhtHisTranService;
import com.jiuyv.hhc.model.loan.MhtHisTranVo;

/**
 * 贷款信息.
 *

 * @author 
 * @since 2014-1-3 15:59:50
 * @version 1.0.0
 */
public class MhtHisTranAction extends DefaultPageSupportAction {

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	
	/** The Service. */
	private IMhtHisTranService service;

	/** The OR g_ list. */
	private static List<CellDataType> HIS_COL = new ArrayList<CellDataType>();
	static {
		HIS_COL.add(new CellDataType("hisTransMonth", "统计月份"));
		HIS_COL.add(new CellDataType("monthTransAt", "月交易额", CellDataRender.money));
		HIS_COL.add(new CellDataType("monthTransAtYear", "月交易额同比"));
		HIS_COL.add(new CellDataType("monthTransNum", "月交易笔数"));
		HIS_COL.add(new CellDataType("monthCusNum", "月消费客户数"));
		HIS_COL.add(new CellDataType("monthRepeatCusNum", "月重复消费客户数"));
		HIS_COL.add(new CellDataType("creditDebitRatio", "贷记卡与借记卡交易比"));
	}

	/**
	 * 查询贷前交易信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtHisTran() throws Exception {
		return resultData(service.findMhtHisTran(getFilters(), getPageInfo()));
	}
	
	/**
	 * 查询贷前交易信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtHisTranbyMchntCode() throws Exception {
		return resultData(service.findMhtHisTran(getFilters(), getPageInfo()));
	}

	/**
	 * 查询贷前交易信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtHisTranbyMchntCodeExcel() throws Exception {
		return defaultExportXLS("贷前信息", HIS_COL
				, service.findMhtHisTran(getFilters(), getExcelPageInfo()));
	}

	/**
	 * 贷前交易信息查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMhtHisTranExcel() throws Exception {
		return defaultExportXLS("贷前信息", HIS_COL
				, service.findMhtHisTran(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertMhtHisTran() throws Exception {
		return resultData(service.doInsertMhtHisTran(getValidateBean(MhtHisTranVo.class),getUserDetailInfo()));
	}

	
	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IMhtHisTranService service) {
		this.service = service;
	}

}
