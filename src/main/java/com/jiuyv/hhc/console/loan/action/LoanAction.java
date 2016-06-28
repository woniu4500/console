package com.jiuyv.hhc.console.loan.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.common.httpclient.HttpClientUtil;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.console.loan.service.ILoanService;
import com.jiuyv.hhc.model.loan.LoanInfoVo;

/**
 * 贷款信息.
 *

 * @author 
 * @since 2014-1-3 15:59:50
 * @version 1.0.0
 */
@Deprecated
public class LoanAction extends DefaultPageSupportAction {

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	/** Constant String FLD_STATUS: String :. */
	private static final String FLD_STATUS = "status";
	
	/** Constant String STATUS_NORMAL: String :. */
	private static final String STATUS_NORMAL = "0";
	
	/** The Service. */
	private ILoanService service;
	
	private ISysParamService sysParamService ;
	

	/** The OR g_ list. */
	private static List<CellDataType> loanList = new ArrayList<CellDataType>();
	static {
		loanList.add(new CellDataType("loanId", "贷款编号"));
		loanList.add(new CellDataType("loanAt", "贷款金额"));
		loanList.add(new CellDataType("loanCycle", "贷款周期"));
		loanList.add(new CellDataType("loanrt", "贷款利率"));
		loanList.add(new CellDataType("loanSuccTm", "放款成功时间"));
		loanList.add(new CellDataType("loanBalance", "贷款余额"));
		loanList.add(new CellDataType("loanStDesc", "贷款状态"));
	}

	/**
	 * 查询贷款信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findLoan() throws Exception {
		return resultData(service.findLoan(getOrgFilters(FLD_ORG_CODE), getPageInfo()));
	}

	/**
	 * 贷款查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findLoanExcel() throws Exception {
		return defaultExportXLS("贷款信息", loanList
				, service.findLoan(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 贷款查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findLoanCombo() throws Exception {
		List<Filter> filters = getOrgFilters(FLD_ORG_CODE);
		filters.add(new Filter(FLD_STATUS, Filter.STRING, STATUS_NORMAL, Filter.Comparison.EQ));
		return resultData(service.findLoan(filters, getExcelPageInfo()));
	}

	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertLoan() throws Exception {
		return resultData(service.doInsertLoan(getValidateBean(LoanInfoVo.class),getUserDetailInfo()));
	}

	/**
	 * 修改.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateLoan() throws Exception {
		return resultData(service.doUpdateLoan(getValidateBean(LoanInfoVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 贷款申请审核驳回
	 * @return
	 * @throws Exception
	 */
	public String doAuditDenyLoan() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 贷款提交银行
	 * @return
	 * @throws Exception
	 */
	public String doSend2Bank() throws Exception {
		return resultData(service.doSend2Bank(getValidateBean(LoanInfoVo.class),getUserDetailInfo()));
	}
	
	
	/**
	 * 贷款申请成功
	 * @return
	 * @throws Exception
	 */
	public String doLoanSuccess() throws Exception {
		return resultData(service.doLoanSuccess(getValidateBean(LoanInfoVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 贷款申请失败
	 * @return
	 * @throws Exception
	 */
	public String doLoanFailed() throws Exception {
		return resultData(service.doLoanFailed(getValidateBean(LoanInfoVo.class),getUserDetailInfo()));
	}
	
	/**
	 * Send audit.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String doSendLoanInfo() throws Exception {
		LoanInfoVo vo = getValidateBean(LoanInfoVo.class);
		String url = sysParamService.findParam("cups.sendloan");
		Map<String, String> params = new HashMap<String, String>();
		params.put("loanId", vo.getLoanId());
		params.put("remark", "");
		params.put("oprAcct", getUserInfo().getLoginId());
		String result = HttpClientUtil.post(url, params );
		return resultData(result);
	}
	
	
	/**
	 * 查询贷款信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findLoanbyCustomerCode() throws Exception {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("customerCode", Filter.STRING, getStringField("customerCode"), Filter.Comparison.EQ));
		return resultData(service.findLoan(filters, getPageInfo()));
	}
	
	/**
	 * 查询代理商户下的所有贷款信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findLoanbyAgentCustomerCode() throws Exception {
		return resultData(service.findLoanbyAgentCustomerCode(Long.valueOf(getStringField("customerCode")), getPageInfo()));
	}
	
	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(ILoanService service) {
		this.service = service;
	}

	/**
	 * @param sysParamService the sysParamService to set
	 */
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

}
