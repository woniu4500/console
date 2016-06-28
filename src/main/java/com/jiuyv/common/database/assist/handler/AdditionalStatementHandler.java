package com.jiuyv.common.database.assist.handler;

/**
 * The Interface AdditionalStatementHandler.
 *
 * @param <T> the generic type

 * @author 
 * @since 2013-12-19 15:52:34
 * @version 1.0.0
 */
public interface AdditionalStatementHandler<T> {

	 /**
 	 * On event loop.
 	 *
 	 * @param param the param
 	 * @return the t
 	 */
 	T onEventLoop(T param) ;
	
}
