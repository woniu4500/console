package com.jiuyv.hhc.console.loan.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.loan.service.ITxnService;

/**
 * <h1>The Class TransTxnLogAction.</h1>
 * <p>Descriptions:</p>
 * 根据条件查询交易日志信息

 * @author
 * @since 2014
 * @version 1.0.0
 */
public class TransTxnLogAction extends DefaultPageSupportAction {

	private ITxnService txnService;
	
	private static List<CellDataType> TXN_COL = new ArrayList<CellDataType>();
	private static List<CellDataType> TXNAMT_COL = new ArrayList<CellDataType>();
	
	static {
		TXN_COL.add(new CellDataType("logSeq", "序列号"));
		TXN_COL.add(new CellDataType("transTypeDesc", "交易类型"));
//		TXN_COL.add(new CellDataType("transDate", "交易日期",CellDataRender.date));
		TXN_COL.add(new CellDataType("transTime", "交易时间",CellDataRender.time));
		TXN_COL.add(new CellDataType("mchntCode", "内部商户号"));
		TXN_COL.add(new CellDataType("mchntCd", "银联商户号"));
		TXN_COL.add(new CellDataType("licNo", "营业执照号码"));
		TXN_COL.add(new CellDataType("mchntName", "商户名称"));
//		TXN_COL.add(new CellDataType("artifCertifId", "法人代表证件号"));
//		TXN_COL.add(new CellDataType("filePath", "附件路径"));
		TXN_COL.add(new CellDataType("oprAcct", "操作帐号"));
		TXN_COL.add(new CellDataType("resultCode", "应答码"));
		TXN_COL.add(new CellDataType("resultDesc", "应答信息"));
		
		TXNAMT_COL.add(new CellDataType("logSeq", "序列号"));
		TXNAMT_COL.add(new CellDataType("transTime", "交易时间", CellDataRender.time));
		TXNAMT_COL.add(new CellDataType("oprAcct", "操作帐号"));
		TXNAMT_COL.add(new CellDataType("mchntCode", "内部商户号"));
		TXNAMT_COL.add(new CellDataType("mchntCd", "银联商户号"));
		TXNAMT_COL.add(new CellDataType("licNo", "营业执照号码"));
		TXNAMT_COL.add(new CellDataType("mchntName", "商户名称"));
		TXNAMT_COL.add(new CellDataType("transTypeDesc", "交易类型"));
		TXNAMT_COL.add(new CellDataType("amount", "费用", CellDataRender.money));
	}

	
	/**
	 * Find trans log.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findTransLog() throws Exception {
		return resultData(txnService.findPage(getFilters(), getPageInfo()));
	}
	
	/**
	 * Find trans log excel.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findTransLogExcel() throws Exception {
		return defaultExportXLS("交易日志信息", TXN_COL
				, txnService.findPage(getFilters(), getExcelPageInfo()));
	}
	
	/**
	 * Find trans log.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findTransLogAmt() throws Exception {
		Long registAmt = NumberUtils.toLong(getParameter("registAmt"),2000);
		Long certAmt = NumberUtils.toLong(getParameter("certAmt"),1000);
		return resultData(txnService.findAmtPage(getFilters(), getPageInfo(), registAmt, certAmt));
	}
	
	/**
	 * Find trans log excel.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findTransLogAmtExcel() throws Exception {
		Long registAmt = NumberUtils.toLong(getParameter("registAmt"),2000);
		Long certAmt = NumberUtils.toLong(getParameter("certAmt"),1000);
		return defaultExportXLS("商户注册认证费用信息", TXNAMT_COL
				, txnService.findAmtPage(getFilters(), getExcelPageInfo(), registAmt, certAmt));
	}
	
	/**
	 * @param txnService the txnService to set
	 */
	public void setTxnService(ITxnService txnService) {
		this.txnService = txnService;
	}
	
}
