package com.jiuyv.common.database.assist;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.criteria.BeanColumnMap;
import com.jiuyv.common.database.exception.BaseException;

/**
 * Query Assit.
 *

 * @author 
 * @since 2013-12-20 15:35:01
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
public interface QueryAssist {

	/**
	 * Count
	 *
	 * @param <E> the element type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @return 返回结果列表
	 */
	Long count(String mappedId, List<Filter> filters);
	
	/**
	 * Count.
	 *
	 * @param <E> the element type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @param params Map参数
	 * @return 返回结果列表
	 */
	Long count(String mappedId, List<Filter> filters, Map params);
	
	/**
	 * List. 列表查询
	 *
	 * @param <E> the element type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @return 返回结果列表
	 */
	<E> List<E> list(String mappedId, List<Filter> filters);
	
	/**
	 * List.
	 *
	 * @param <E> the element type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @param page 分页信息
	 * @return 返回结果列表
	 */
	<E> List<E> list(String mappedId, List<Filter> filters, Page page);

	/**
	 * List.
	 *
	 * @param <E> the element type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @param page 分页信息
	 * @param params Map参数
	 * @return 返回结果列表
	 */
	<E> List<E> list(String mappedId, List<Filter> filters, Page page, Map params);

	/**
	 * Page.
	 *
	 * @param <E> the element type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @param page 分页信息
	 * @return 返回分页结果
	 */
	<E> ExtData<E> page(String mappedId, List<Filter> filters, Page page);

	/**
	 * Page.
	 *
	 * @param <E> the element type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @param page 分页信息
	 * @param params Map参数
	 * @return 返回分页结果
	 */
	<E> ExtData<E> page(String mappedId, List<Filter> filters, Page page, Map params);
	
	/**
	 * Map.
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @param page 分页信息
	 * @param params Map参数
	 * @param mapKey Map中key对应字段
	 * @return the map
	 */
	<K,V> Map<K,V> map(String mappedId, List<Filter> filters, Page page, Map params, String mapKey);
			
	/**
	 * Column map. 列映射
	 *
	 * @param mappedId mapId
	 * @return the map
	 */
	Map<String, BeanColumnMap> columnMap(String mappedId) ;
	
	
	/**
	 * 调findByKey无返回结果的情况下调用insert插入，若有记录则调用update
	 * @param dao
	 * @param obj
	 * @return
	 * @throws BaseException
	 */
	<T> T merge(BaseDao<T> dao, T obj) throws BaseException ;
	
	/**
	 * 插入后查询出对象
	 * @param obj
	 * @return 
	 * @throws BaseException 
	 */
	<T> T insertFetch(BaseDao<T> dao, T obj) throws BaseException ;
	
	/**
	 * 更新后查询对象
	 * @param obj
	 * @return
	 */
	<T> T updateFetch(BaseDao<T> dao, T obj) throws BaseException ;
	
	/**
	 * 批量插入对象
	 * @param dao
	 * @param list
	 * @return 
	 */
	<T> int insertBatch(BaseDao<T> dao, Collection<T> list) ;
	
	/**
	 * 非批量处理，逐条插入数据
	 * @param dao
	 * @param list
	 * @return
	 */
	<T> int insertList(BaseDao<T> dao, Collection<T> list);

	/**
	 * 非批量处理，逐条插入数据
	 * @param dao
	 * @param list
	 * @return
	 */
	<T> int updateList(BaseDao<T> dao, Collection<T> list);
	
	
	/**
	 * Map查询
	 * @param mappedId
	 * @param parameter
	 * @param mapKey
	 * @return
	 */
	Map selectMap(String mappedId, Map parameter, String mapKey);
	
	/**
	 * 列表查询
	 * @param mappedId
	 * @param parameter
	 * @return
	 */
	<E> List<E> selectList(String mappedId, Map parameter);
	
	/**
	 * 单条记录查询
	 * @param mappedId
	 * @param parameter
	 * @return
	 */
	<E> E selectOne(String mappedId, Map parameter);
	
	/**
	 * 使用结果处理来获取自定义的结果集
	 * @param statementId 语句ID
	 * @param parameter 参数
	 * @param handler ResultHandler
	 */
	void selectWithHandler(String statementId, Map parameter, ResultHandler handler);
	
}
