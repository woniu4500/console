package com.jiuyv.hhc.console.loan.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.loan.BizLoanDataVo;
import com.jiuyv.hhc.model.mchnt.BizMchntAttachVo;
import com.jiuyv.hhc.model.security.SecurityUserDetail;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * <h1>The Interface IBizLoanDataService.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public interface IBizLoanDataService {

	/**
	 * 新增.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> doInsertLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 修改.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> doUpdateLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception;
	
	/**
	 * 贷后信息维护.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> doMaintainLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 提交.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> doSubmitLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception;
	
	/**
	 * 手工商户认证.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> doCertifyLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception;
	
	/**
	 * 手工贷款申请驳回.
	 * 填写驳回原因
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> doDenyLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 手工贷款通过.
	 *
	 * @param loanVo the loan vo
	 * @param userVo the user vo
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	public ExtData<BizLoanDataVo> doAuditLoan(BizLoanDataVo loanVo,SysUserVo userVo)
			throws Exception; 
	
	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> findLoan(List<Filter> filters, Page page) throws Exception;
	
	/**
	 * 查询代理商户下的所有贷款信息.
	 *
	 * @param customerCode the customer code
	 * @param page the page
	 * @return the ext data< loan vo>
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> findLoanbyAgentCustomerCode(Long customerCode, Page page) throws Exception;
	
	/**
	 * 查询会员的贷款信息
	 * @param customerCode
	 * @param page
	 * @return
	 * @throws Exception
	 */
	ExtData<BizLoanDataVo> findBizLoanDataCustomerCode(Long customerCode, Page page) throws Exception;

	/**
	 * 贷款申请提交银行.
	 *
	 * @param loanVo the loan vo
	 * @param user the user
	 * @return the ext data
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> doSend2Bank(BizLoanDataVo loanVo, SecurityUserDetail user) throws Exception;
	
	/**
	 * 贷款申请成功.
	 *
	 * @param loanVo the loan vo
	 * @param user the user
	 * @return the ext data
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> doLoanSuccess(BizLoanDataVo loanVo, SecurityUserDetail user) throws Exception;
	
	/**
	 * 贷款申请失败.
	 *
	 * @param loanVo the loan vo
	 * @param user the user
	 * @return the ext data
	 * @throws Exception the exception
	 */
	ExtData<BizLoanDataVo> doLoanFailed(BizLoanDataVo loanVo, SecurityUserDetail user) throws Exception;
	
	/**
	 * 通过loanId查找贷款信息
	 * @param loanId
	 * @return
	 * @throws Exception
	 */
	public BizLoanDataVo findBizLoanDataByLoanId(String loanId) throws Exception;
	
	/**
	 * 发送邮件到银行
	 * @param loanVo
	 * @return
	 * @throws Exception
	 */
	public ExtData<CmMediaResVo> zipFileAndSendMaill(File dir, Map<String,String> map,String datestr)throws Exception; 
	
	/**
	 * 查询用户下的附件访问路径
	 * @param customerCode
	 * @return
	 * @throws BaseException
	 */
	public List<BizMchntAttachVo> findWebMemberInfoAttach(Long customerCode) throws BaseException; 
	
}
