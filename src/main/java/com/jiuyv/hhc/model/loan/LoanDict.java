package com.jiuyv.hhc.model.loan;

/**
 * <h1>The Interface LoanDict.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public interface LoanDict {

	/**
	 * <h1>The Interface CreditSt.</h1>
	 * <p>Descriptions:</p>
	 * 00:录入,01:提交,02:商户认证,03:提交银行,04:贷款成功,05:部分还款,06:完全还款,08:贷款驳回,09:贷款失败
	 * 
	
	 * @author
	 * @since 2015
	 * @version 1.0.0
	 */
	interface CreditSt {
		/** 00:录入. */
		String INPUT = "00";
		/** 01:提交. */
		String SUBMIT = "01";
		/** 02:商户认证. */
		String CERTIFY = "02";
		/** 03:提交银行. */
		String TOBANK = "03";
		/** 04:贷款成功. */
		String LOAN_SUCC = "04";
		/** 05:部分还款. */
		String PART_PAY = "05";
		/** 06:完全还款. */
		String FULL_PAY = "06";
		/** 08:贷款驳回. */
		String LOAN_DENY = "08";
		/** 09:贷款失败. */
		String LOAN_FAIL = "09";
	}
	
	/**
	 * <h1>The Interface CancelFlag.</h1>
	 * <p>Descriptions:</p>
	 * 0:正常,1:撤销
	
	 * @author
	 * @since 2015
	 * @version 1.0.0
	 */
	interface CancelFlag {
		/** 0:正常. */
		String NORMAL = "0";
		/** 1:撤销. */
		String CANCEL = "1";
	}
	
}
