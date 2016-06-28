package com.jiuyv.common.database.assist.handler;

/**
 * 
 * @author caoyunke
 *
 */
public enum QueryType {

	 COUNT("criteria.__count","_count", 2)
	,PAGE("criteria.__page","_page", 2)
	,LIST("criteria.__list","_list", 0);
	
	private String mapperId ; 
	
	private String append ; 
	
	private int position ; 
	
	private QueryType(String mapperId, String append, int position){
		this.mapperId = mapperId;
		this.append = append ; 
		this.position = position;
	}

	/**
	 * @return the mapperId
	 */
	public String getMapperId() {
		return mapperId;
	}

	/**
	 * @return the append
	 */
	public String getAppend() {
		return append;
	}
	
	/**
	 * mappedId + append
	 * @param mappedId
	 * @return mappedId + append
	 */
	public String statementId (String mappedId) {
		return mappedId + append;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
	
}
