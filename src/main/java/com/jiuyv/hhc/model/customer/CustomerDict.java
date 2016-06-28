package com.jiuyv.hhc.model.customer;

/**
 * <h1>The Interface CustomerDict.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public interface CustomerDict {

	String DEF_RCMD_DESC = "手机号";
	
	
	interface WebCustomerField {
		
		String CUSTOMER_TYPE = "customerType";
		
		String CUSOMTER_CODE = "customerCode";
	}
	
	/**
	 * <h1>The Interface UserState.</h1>
	 * <p>Descriptions:</p>
	 * 0:正常,1:删除
	
	 * @author
	 * @since 2015
	 * @version 1.0.0
	 */
	interface UserState {
		
		/** The String normal. */
		String NORMAL = "0";
		
		/** The String delete. */
		String DELETE = "1";
	}
	
	
	/**
	 * <h1>The Interface MemberType.</h1>
	 * <p>Descriptions:</p>
	 * 0:普通会员,1:代理会员
	
	 * @author
	 * @since 2015
	 * @version 1.0.0
	 */
	interface MemberType {
		
		 /** The String user. */
 		String USER = "0";
		 
		 /** The String agent. */
 		String AGENT = "1";
		
		
	}
	
	
}
