package com.jiuyv.hhc.console.loan.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.loan.LoanInfoVo;
import com.jiuyv.hhc.model.security.SecurityUserDetail;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface ILoanService.
 *

 * @author 
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
@Deprecated
public interface ILoanService {
	
	/**
	 * 新增.
	 *
	 * @param loanVo the loan vo
	 * @param filters the filters
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<LoanInfoVo> doInsertLoan(LoanInfoVo loanVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 修改.
	 *
	 * @param loanVo the loan vo
	 * @param filters the filters
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<LoanInfoVo> doUpdateLoan(LoanInfoVo loanVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<LoanInfoVo> findLoan(List<Filter> filters, Page page) throws Exception;
	
	/**
	 * 查询代理商户下的所有贷款信息
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<LoanInfoVo> findLoanbyAgentCustomerCode(Long customerCode, Page page) throws Exception;

	/**
	 * 贷款申请提交银行.
	 *
	 * @param loanVo the loan vo
	 * @param user the user
	 * @return the ext data
	 * @throws Exception the exception
	 */
	ExtData<LoanInfoVo> doSend2Bank(LoanInfoVo loanVo, SecurityUserDetail user) throws Exception;
	
	/**
	 * 贷款申请成功.
	 *
	 * @param loanVo the loan vo
	 * @param user the user
	 * @return the ext data
	 * @throws Exception the exception
	 */
	ExtData<LoanInfoVo> doLoanSuccess(LoanInfoVo loanVo, SecurityUserDetail user) throws Exception;
	
	/**
	 * 贷款申请失败.
	 *
	 * @param loanVo the loan vo
	 * @param user the user
	 * @return the ext data
	 * @throws Exception the exception
	 */
	ExtData<LoanInfoVo> doLoanFailed(LoanInfoVo loanVo, SecurityUserDetail user) throws Exception;
}
