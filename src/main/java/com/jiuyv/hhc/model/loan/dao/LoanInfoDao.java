package com.jiuyv.hhc.model.loan.dao ;

import java.util.List;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.loan.LoanInfoVo;

// Generated by AutoCode4J
/**
 * Interface: 商户贷款信息表 : TBL_LOAN_INFO
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
@Deprecated
public interface LoanInfoDao extends BaseDao<LoanInfoVo>{
	
	/** mapped key is : com.jiuyv.hhc.model.loan.dao.LoanInfoDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.loan.dao.LoanInfoDao.filterQuery";

	/**
	 * Find LoanInfoVo by Primary Key. 
	 * 
	 * @param loanId : 贷款编号	 
	 * @return LoanInfoVo : 商户贷款信息表
	 */
	LoanInfoVo findByKey(String loanId ) ;
	
	/**
	 * Update LoanInfoVo by Primary Key.
	 * 
	 * @return effect rows.
	 * @author AutoCode4J
	 */
	int updateByKey(LoanInfoVo vo);
	

	/**
	 * Insert LoanInfoVo.
	 * 
	 * @return effect rows.
	 * @author AutoCode4J
	 */
	int insert(LoanInfoVo vo) ;

	List<LoanInfoVo> findByCustomerCode(Long customerCode ) ;

	List<LoanInfoVo> filterQuerybyAgentCustomerCode(Long customerCode ) ;
}