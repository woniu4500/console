package com.jiuyv.common.database;


public interface BaseDao<T> {

	/**
	 * 根据主键查找对象
	 * @param vo
	 * @return
	 */
	T findByKey(T vo);
	
	/**
	 * Update Object by Primary Key.
	 * 
	 * @return effect rows.
	 */
	int updateByKey(T vo);
	

	/**
	 * Insert Object.
	 * 
	 * @return effect rows.
	 */
	int insert(T vo) ;
	
}
