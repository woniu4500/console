package com.jiuyv.common.database.assist.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

/**
 * The Class HandlerCollection.
 *
 * @author 
 * @since 2013-12-19 15:48:16
 * @version 1.0.0
 */
public class HandlerCollection implements AdditionalStatementHandler<MappedStatement> {

	/** The handlers. */
	private List<AdditionalStatementHandler<MappedStatement>> handlers ; 
	
	/**
	 * Instantiates a new handler collection.
	 */
	public HandlerCollection () {
		handlers = new ArrayList<AdditionalStatementHandler<MappedStatement>>();
	}
	
	/**
	 * Instantiates a new handler collection.
	 *
	 * @param handlerArray the handler array
	 */
	public HandlerCollection(AdditionalStatementHandler<MappedStatement>... handlerArray) {
		handlers = new ArrayList<AdditionalStatementHandler<MappedStatement>>();
		addHandlers(handlerArray);
	}
	
	/**
	 * Adds the handlers.
	 *
	 * @param handlerArray the handler array
	 */
	public void addHandlers(AdditionalStatementHandler<MappedStatement>... handlerArray){
		if ( handlerArray != null ) {
			for (AdditionalStatementHandler<MappedStatement> handler : handlerArray) {
				if ( handler != null ) {
					handlers.add(handler);
				}
			}
		}
	}
	
	/**
	 * @param ms
	 * @return
	 * @see com.jiuyv.common.database.assist.handler.AdditionalStatementHandler#onEventLoop(java.lang.Object)
	 */
	public MappedStatement onEventLoop(MappedStatement ms) {
		for ( AdditionalStatementHandler<MappedStatement> handler : handlers ) {
			handler.onEventLoop(ms);
		}
		return ms;
	}
	
	/**
	 * Config new statements.
	 *
	 * @param config the config
	 */
	public void configNewStatements(Configuration config) {
		for (AdditionalStatementHandler<MappedStatement> handler : handlers) {
			if ( handler instanceof AddonStatementHandler ) {
				List<MappedStatement> list = ((AddonStatementHandler)handler).getStatements();
				for ( MappedStatement sta:list ) {
					config.addMappedStatement(sta);
				}
			}
		}
	}

}
