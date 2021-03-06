package com.jiuyv.hhc.model.loan.dao ;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.loan.*;

// Generated by AutoCode4J
/**
 * Interface: [New]贷款记录信息表 : CZD_BIZ_LOAN_DATA
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public interface BizLoanDataDao extends BaseDao<BizLoanDataVo> {
	
	/** mapped key is : com.jiuyv.hhc.model.loan.dao.BizLoanDataDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.loan.dao.BizLoanDataDao.filterQuery";

	/**
	 * Find BizLoanDataVo by Primary Key. 
	 * 
	 * @param loanId : 贷款编号	 
	 * @return BizLoanDataVo : [New]贷款记录信息表
	 */
	BizLoanDataVo findByKey(String loanId ) ;

	/**
	 * Count by mchnt cd.
	 *
	 * @param mchntCd the mchnt cd
	 * @return the long
	 */
	Long countByMchntCd(String mchntCd);

	/**
	 * Update loan to pass.
	 *
	 * @param mchntCode the mchnt code
	 * @param loginId the login id
	 * @param orgCode the org code
	 * @return the int
	 */
	int updateLoanToPass(@Param("mchntCode")Long mchntCode, @Param("loginId") String loginId, @Param("orgCode") String orgCode);
	
	/**
	 * Update loan to deny.
	 *
	 * @param mchntCode the mchnt code
	 * @param procResp the proc resp
	 * @param loginId the login id
	 * @param orgCode the org code
	 * @return the int
	 */
	int updateLoanToDeny(@Param("mchntCode") Long mchntCode, @Param("procResp")String procResp, @Param("loginId") String loginId, @Param("orgCode") String orgCode);

	/**
	 * Filter queryby agent customer code.
	 *
	 * @param customerCode the customer code
	 * @return the list
	 */
	List<BizLoanDataVo> filterQuerybyAgentCustomerCode(Long customerCode);
	
	List<BizLoanDataVo> findBizLoanDataCustomerCode(Long customerCode);
	
}
