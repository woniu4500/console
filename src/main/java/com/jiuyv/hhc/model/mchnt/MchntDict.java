package com.jiuyv.hhc.model.mchnt;

/**
 * <h1>The Interface MchntDict.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public interface MchntDict {

	
	/**
	 * <h1>The Interface MchntSt.</h1>
	 * <p>Descriptions:</p>
	 * 01:录入 02:银联已注册 03:资质审核资料补充 04:提交银联资质审核 05:银联审核通过 06:审核驳回 07:已认证 09:无效
	
	 * @author
	 * @since 2015
	 * @version 1.0.0
	 */
	interface MchntSt {
		
		/** 01:录入. */
		String INPUT = "01";
		
		/** 02:银联已注册. */
		String REGIST = "02";
		
		/** 03:资质审核资料补充. */
		String INFOCOMP = "03";
		
		/** 04:提交银联资质审核. */
		String SUBMIT = "04";
		
		/** 05:银联审核通过. */
		String PASS = "05";
		
		/** 06:审核驳回. */
		String DENY = "06";
		
		/** 07:已认证. */
		String CONFID = "07";
		
		/** 09:无效. */
		String INVALID = "09";
	}
	
	
	/**
	 * <h1>The Interface DispMchntSt.</h1>
	 * <p>Descriptions:</p>
	 * 01 录入：初次录入 02 已注册：银联商户信息查询接口返回成功 0000或0022 07 已认证：贷前查询成功
	
	 * @author
	 * @since 2015
	 * @version 1.0.0
	 */
	interface DispMchntSt {
		
		/** 01:录入：初次录入. */
		String INPUT = "01";
		
		/** 02:已注册：银联商户信息查询接口返回成功 0000或0022. */
		String REGIST = "02";
		
		/** 07 已认证：贷前查询成功. */
		String CONFID = "07";
		
		/** 09:无效. */
		String INVALID = "09";
		
		/** 10:删除 */
		String DELETE = "10";
	}
	
	/**
	 * <h1>The Interface CupsOprType.</h1>
	 * <p>Descriptions:</p>
	 *
	
	 * @author
	 * @since 2015
	 * @version 1.0.0
	 */
	interface CupsOprType {
		
		/** The String ge t_ mchn t_ info. */
		String GET_MCHNT_INFO = "getMchntInfo";
		
		/** The String ge t_ mchn t_ ris k_ data. */
		String GET_MCHNT_RISK_DATA = "getMchntRiskData";
		
		/** The String sen d_ mchn t_ loa n_ audit. */
		String SEND_MCHNT_LOAN_AUDIT = "sendMchntLoanAudit";
		
		/** The String ge t_ mchn t_ tran s_ dat a_ befor e_ loan. */
		String GET_MCHNT_TRANS_DATA_BEFORE_LOAN = "getMchntTransDataBeforeLoan";
		
		/** The String uploa d_ mchn t_ loa n_ data. */
		String UPLOAD_MCHNT_LOAN_DATA = "uploadMchntLoanData";
		
		/** The String ge t_ mchn t_ loa n_ dat a_ afte r_ laon. */
		String GET_MCHNT_LOAN_DATA_AFTER_LAON = "getMchntLoanDataAfterLoan";
	}
	
}
