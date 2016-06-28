package com.jiuyv.common.database.criteria;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author caoyunke
 *
 */
public class ConditionMap {

	/** The map. */
	private Map<String, BeanColumnMap> map ; 
	
	/**
	 * Instantiates a new condition map.
	 */
	public ConditionMap() {
		this.map = new LinkedHashMap<String, BeanColumnMap>();
	}
	
	/**
	 * Instantiates a new condition map.
	 *
	 * @param map the map
	 */
	public ConditionMap( Map<String, BeanColumnMap> map ) {
		this.map = new LinkedHashMap<String, BeanColumnMap>();
		this.map.putAll(map);
	}
	
	/**
	 * Adds the.
	 *
	 * @param bcm the bcm
	 * @return the condition map
	 */
	public ConditionMap add(BeanColumnMap bcm ) {
		if ( bcm != null ) {
			this.map.put(bcm.getBeanPropertyName().trim().toLowerCase(), bcm);
		}
		return this ; 
	}
	
	public ConditionMap add(String beanPropertyName, String columnName,
			String propertytype) {
		BeanColumnMap bcm = new BeanColumnMap(beanPropertyName, columnName, propertytype);
		this.map.put(beanPropertyName.trim().toLowerCase(), bcm);
		return this; 
	}
	
	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public Map<String, BeanColumnMap> getMap(){
		return this.map ; 
	}
}
