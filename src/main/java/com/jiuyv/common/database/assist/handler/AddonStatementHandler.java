package com.jiuyv.common.database.assist.handler;

import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * The Interface AddonStatementHandler.
 *
 * @author 
 * @since 2013-12-19 15:46:32
 * @version 1.0.0
 */
public interface AddonStatementHandler extends AdditionalStatementHandler<MappedStatement> {

	/**
	 * Gets the statements.
	 *
	 * @return the statements
	 */
	List<MappedStatement> getStatements();
	
	
}
