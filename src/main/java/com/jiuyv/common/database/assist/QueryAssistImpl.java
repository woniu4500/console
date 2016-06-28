package com.jiuyv.common.database.assist;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.common.SystemErrorCode;
import com.jiuyv.common.database.BaseDao;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.Pagination;
import com.jiuyv.common.database.Sort;
import com.jiuyv.common.database.assist.handler.BaseStatementHandler;
import com.jiuyv.common.database.assist.handler.ColumnMapHandler;
import com.jiuyv.common.database.assist.handler.CountStatementHandler;
import com.jiuyv.common.database.assist.handler.HandlerCollection;
import com.jiuyv.common.database.assist.handler.QueryType;
import com.jiuyv.common.database.criteria.BeanColumnMap;
import com.jiuyv.common.database.criteria.CriteriaUtil;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.exception.UpdateException;
import com.jiuyv.common.database.util.PageUtil;


/**
 * The Class QueryAssistImpl.
 * 保证此对象为单例运行
 * 
 * @author 
 * @since 2013-12-19 15:06:57
 * @version 1.0.0
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class QueryAssistImpl extends SqlSessionDaoSupport implements QueryAssist {
	
	/** logger. */
	private Logger logger = LoggerFactory.getLogger(QueryAssistImpl.class);

	/** SqlSessionFactory would normally be set by SqlSessionDaoSupport 
	 * */
	private SqlSessionFactory extendSqlSessionFactory;
	
	/** 允许使用批量模式(独立事务无法和AOP事务一起回滚) */
	private Boolean allowBatchMode = false ; 
	
	/** 忽略处理的语句名称. */
	private Collection<String> excludeStatement ; 
	
	/** handle集合. */
	private HandlerCollection handler ; 
	
	/** The bean column map. */
	private Map<String, Map<String, BeanColumnMap>> beanColumnMap ;  
	
	/** The Constant F_CRITERIA. */
	private static final String F_CRITERIA = "criteria";
	
	/** The Constant F_SORTS. */
	private static final String F_SORTS = "sorts";
	
	/** The Constant F_PAGE. */
	private static final String F_PAGE = "page";
	
	/** 默认提交批数量 1000 */
	private static final int DEF_BATCH_SIZE = 1000;
	
	/**
	 * Initialize Query Assist
	 * 1. Find out select statement
	 * 2. Add _list, _count, _page statement
	 * 3. Map query columns
	 *
	 * @throws Exception the exception
	 */
	public void initAssist() throws Exception {
		logger.debug("<no args> - start"); 

		Configuration config = getSqlSession().getConfiguration();
		
		Collection<String> msNmC = config.getMappedStatementNames();
		Set<String> excludeSet = this.excludeStatement == null ? new HashSet<String>():new HashSet<String>(this.excludeStatement);
		// preset excludeStatement 
		excludeSet.add(QueryType.COUNT.getMapperId());
		excludeSet.add(QueryType.PAGE.getMapperId());
		excludeSet.add(QueryType.LIST.getMapperId());
		// Column Map Handler: use to map result type 
		ColumnMapHandler colMapHandler = new ColumnMapHandler();
		// add base handler
		handler = handler == null ? new HandlerCollection(): handler ;
		handler.addHandlers(
			 new CountStatementHandler(config, QueryType.COUNT)
			,new BaseStatementHandler(config, QueryType.PAGE)
			,new BaseStatementHandler(config, QueryType.LIST)
			,colMapHandler);
		//
		for (String nm : msNmC) {
			if ( !excludeSet.contains(nm) && nm.contains(".") 
					&& nm.substring(nm.lastIndexOf(".")).startsWith(".filter") ){
				MappedStatement ms = config.getMappedStatement(nm);
				// check select name 
				if (ms.getSqlCommandType() == SqlCommandType.SELECT) {
					logger.info("Proccessing Statement {} ", nm);
					handler.onEventLoop(ms);
				}
			}
		}
		// set beanColumnMap 
		this.beanColumnMap = colMapHandler.getStatementMap();
		// Add new Mapped Statement into Config
		this.handler.configNewStatements(config);

		logger.debug("<no args> - end"); 
	}
	
	
	/**
	 * Count
	 *
	 * @param <E> the element type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @return 返回结果列表
	 */
	public Long count(String mappedId, List<Filter> filters) {
		return this.count(mappedId, filters, null);
	}
	
	/**
	 * Count.
	 *
	 * @param <E> the element type
	 * @param mappedId mapId
	 * @param filters 查询条件集合
	 * @param page 分页信息
	 * @param params Map参数
	 * @return 返回结果列表
	 */
	public Long count(String mappedId, List<Filter> filters, Map params) {
		Map parameter = params == null ? new HashMap(): new HashMap(params);
		if (this.beanColumnMap.containsKey(mappedId)){
			Map columnMap = this.beanColumnMap.get(mappedId);
			parameter.put(F_CRITERIA, CriteriaUtil.genCriteriabyFilter(filters, columnMap));
		}
		return (Long)getSqlSession().selectOne(QueryType.COUNT.statementId(mappedId), parameter);
	}

	/**
	 * @param mappedId
	 * @param filters
	 * @return
	 * @see com.jiuyv.common.database.assist.QueryAssist#list(java.lang.String, java.util.List)
	 */
	public <E> List<E> list(String mappedId, List<Filter> filters) {
		return this.list(mappedId, filters, null, null);
	}


	/**
	 * @param mappedId
	 * @param filters
	 * @param page
	 * @return
	 * @see com.jiuyv.common.database.assist.QueryAssist#list(java.lang.String, java.util.List, com.jiuyv.model.common.Page)
	 */
	public <E> List<E> list(String mappedId,
			List<Filter> filters, Page page) {
		return this.list(mappedId, filters, page, null);
	}
	
	/**
	 * @param mappedId
	 * @param filters
	 * @param page
	 * @param params
	 * @return
	 * @see com.jiuyv.common.database.assist.QueryAssist#list(java.lang.String, java.util.List, com.jiuyv.model.common.Page, java.util.Map)
	 */
	public <E> List<E> list(String mappedId, List<Filter> filters, Page page,
			Map params) {
		Map parameter = params == null ? new HashMap(): new HashMap(params);
		if (this.beanColumnMap.containsKey(mappedId)){
			Map columnMap = this.beanColumnMap.get(mappedId);
			parameter.put(F_CRITERIA, CriteriaUtil.genCriteriabyFilter(filters, columnMap));
			parameter.put(F_SORTS, PageUtil.genSorts(page, columnMap));
		}
		return getSqlSession().selectList(QueryType.LIST.statementId(mappedId), parameter);
	}
	
	/**
	 * @param mappedId
	 * @param filters
	 * @param page
	 * @param params
	 * @param mapKey
	 * @return
	 * @see com.jiuyv.common.database.assist.QueryAssist#map(java.lang.String, java.util.List, com.jiuyv.model.common.Page, java.util.Map, java.lang.String)
	 */
	public <K,V> Map<K,V> map(String mappedId, List<Filter> filters, Page page,
			Map params, String mapKey) {
		Map parameter = params == null ? new HashMap(): new HashMap(params);
		if (this.beanColumnMap.containsKey(mappedId)){
			Map columnMap = this.beanColumnMap.get(mappedId);
			parameter.put(F_CRITERIA, CriteriaUtil.genCriteriabyFilter(filters, columnMap));
			parameter.put(F_SORTS, PageUtil.genSorts(page, columnMap));
		}
		return getSqlSession().selectMap(QueryType.LIST.statementId(mappedId), parameter, mapKey);
	}

	/**
	 * @param mappedId
	 * @param filters
	 * @param page
	 * @return
	 * @see com.jiuyv.common.database.assist.QueryAssist#page(java.lang.String, java.util.List, com.jiuyv.model.common.Page)
	 */
	public <E> ExtData<E> page(String mappedId,
			List<Filter> filters, Page page) {
		return this.page(mappedId, filters, page, null);
	}

	/**
	 * @param mappedId
	 * @param filters
	 * @param page
	 * @param params
	 * @return
	 * @see com.jiuyv.common.database.assist.QueryAssist#page(java.lang.String, java.util.List, com.jiuyv.model.common.Page, java.util.Map)
	 */
	public <E> ExtData<E> page(String mappedId, List<Filter> filters, Page page,
			Map params) {
		// Build parameter for query. 
		Map parameter = params == null ? new HashMap(): new HashMap(params);
		ExtData<E> data = new ExtData<E>();
		Pagination pagination = null ;
		List<Sort> sorts = null ; 
		if (this.beanColumnMap.containsKey(mappedId)){
			Map columnMap = this.beanColumnMap.get(mappedId);
			parameter.put(F_CRITERIA, CriteriaUtil.genCriteriabyFilter(filters, columnMap));
			sorts = PageUtil.genSorts(page, columnMap);
			parameter.put(F_SORTS, sorts);
		}
		pagination=PageUtil.genPagination(page);
		parameter.put(F_PAGE, pagination);
		data.setSuccess(true);
		// When limit == -1 return all data without pagination .
		// Mainly used in the data export module. 
		if ( pagination.getLimit() == -1) {
			List<E> result = getSqlSession().selectList(QueryType.LIST.statementId(mappedId), parameter);
			data.setRoot(result);
			data.setTotalProperty(result.size());
			return data ; 
		}
		// Get the total count 
		List cnt = getSqlSession().selectList(QueryType.COUNT.statementId(mappedId), parameter);
		if ( cnt == null || cnt.size() != 1 ) {
			data.setTotalProperty(0);
			return data ; 
		}
		Long totalCnt = Long.valueOf(cnt.get(0).toString());
		data.setTotalProperty(totalCnt);
		// Skip query when count equals zero. 
		if ( totalCnt == 0 ) {
			return data ; 
		}
		List<E> result = getSqlSession().selectList(QueryType.PAGE.statementId(mappedId), parameter);
		data.setRoot(result);
		return data;
	}

	/**
	 * 调findByKey无返回结果的情况下调用insert插入，若有记录则调用update
	 * @param dao
	 * @param obj
	 * @return
	 * @throws BaseException
	 */
	public <T> T merge(BaseDao<T> dao, T obj) throws BaseException {
		T t = dao.findByKey(obj);
		int r = 0;
		if ( t == null ) {
			r = dao.insert(obj);
		} else {
			r = dao.updateByKey(obj);
		}
		if ( r == 0 ) {
			throw new UpdateException(SystemErrorCode.CODE_9902, SystemErrorCode.CODE_9902_MSG);
		}
		return dao.findByKey(obj);
	}
	
	/**
	 * 插入后查询出对象
	 * @param obj
	 * @return 
	 */
	public <T> T insertFetch(BaseDao<T> dao, T obj) throws BaseException {
		if ( dao != null && obj != null ) {
			int r = dao.insert(obj);
			if ( r == 0 ) {
				throw new UpdateException(SystemErrorCode.CODE_9902, SystemErrorCode.CODE_9902_MSG);
			}
			return dao.findByKey(obj);
		}
		logger.error("Dao {} or Object {} is invalid. ", new Object[]{dao, obj});
		return obj ; 
	}
	
	/**
	 * 更新后查询对象
	 * @param obj
	 * @return
	 */
	public <T> T updateFetch(BaseDao<T> dao, T obj) throws BaseException {
		if ( dao != null && obj != null ) {
			int r = dao.updateByKey(obj);
			if ( r == 0 ) {
				throw new UpdateException(SystemErrorCode.CODE_9902, SystemErrorCode.CODE_9902_MSG_1);
			}
			return dao.findByKey(obj);
		}
		logger.error("Dao {} or Object {} is invalid. ", new Object[]{dao, obj});
		return obj ; 
	}
	
	/**
	 * 批量插入对象
	 * 
	 * 使用ExecutorType.BATCH需要新建SqlSession，插入的数据无法和AOP事务一起回滚
	 * 
	 * @param dao
	 * @param list
	 * @return 
	 */
	public <T> int insertBatch(BaseDao<T> dao, Collection<T> list) {
		if ( dao == null || list == null ) {
			logger.error("Dao {} or Collection {} is invalid. ", new Object[]{dao, list});
			return 0;
		}
		// 根据extendSqlSessionFactory 
		if ( allowBatchMode ) {
			SqlSession session = this.extendSqlSessionFactory.openSession(ExecutorType.BATCH, false);
			int er = 0 ; 
			try {
				int count = 0;
				for (T obj : list) {
					int effect = dao.insert(obj);
					er += effect ; 
					if (count % DEF_BATCH_SIZE == 0 || count == list.size() - 1) {
						session.commit();
						session.clearCache();
					}
					count++;
				}
			} catch (Exception e) {
				session.rollback();
			} finally {
				session.close(); 
			}
			return er ; 
		} else {
			// 非批量模式处理
			return insertList(dao, list);
		}
	}
	
	/**
	 * 非批量处理，逐条插入数据
	 * @param dao
	 * @param list
	 * @return
	 */
	public <T> int insertList(BaseDao<T> dao, Collection<T> list) {
		if ( dao == null || list == null ) {
			logger.error("Dao {} or Collection {} is invalid. ", new Object[]{dao, list});
			return 0;
		}
		int er = 0 ;
		for (T obj : list) {
			int effect = dao.insert(obj);
			er += effect ; 
		}
		return er ; 
	}
	
	/**
	 * 非批量处理，逐条更新数据
	 * @param dao
	 * @param list
	 * @return
	 */
	public <T> int updateList(BaseDao<T> dao, Collection<T> list) {
		if ( dao == null || list == null ) {
			logger.error("Dao {} or Collection {} is invalid. ", new Object[]{dao, list});
			return 0;
		}
		int er = 0 ;
		for (T obj : list) {
			int effect = dao.updateByKey(obj);
			er += effect ; 
		}
		return er ; 
	}
	
	/**
	 * @param mappedId
	 * @return
	 * @see com.jiuyv.common.database.assist.QueryAssist#columnMap(java.lang.String)
	 */
	public Map<String, BeanColumnMap> columnMap(String mappedId) {
		return this.beanColumnMap.get(mappedId); 
	}
	
	/**
	 * 单条记录查询
	 * @param mappedId
	 * @param parameter
	 * @return
	 */
	public <E> E selectOne(String mappedId, Map parameter) {
		return getSqlSession().selectOne(mappedId, parameter);
	}
	
	/**
	 * 列表查询
	 * @param mappedId
	 * @param parameter
	 * @return
	 */
	public <E> List<E> selectList(String mappedId, Map parameter){
		return getSqlSession().selectList(mappedId, parameter);
	}
	
	/**
	 * Map查询
	 * @param mappedId
	 * @param parameter
	 * @param mapKey
	 * @return
	 */
	public Map selectMap(String mappedId, Map parameter, String mapKey) {
		return getSqlSession().selectMap(mappedId, parameter, mapKey);
	}
	
	/**
	 * 通过结果handler执行操作
	 * @param statementId
	 * @param parameter
	 * @param handler
	 */
	public void selectWithHandler(String statementId, Map parameter, ResultHandler handler) {
		getSqlSession().select(statementId, parameter, handler);
	}
	
	/**
	 * 设置 忽略处理的语句名称.
	 *
	 * @param excludeStatement the excludeStatement to set
	 */
	public void setExcludeStatement(Collection<String> excludeStatement) {
		this.excludeStatement = excludeStatement;
	}

	/**
	 * 设置 handle集合.
	 *
	 * @param handler the handler to set
	 */
	public void setHandler(HandlerCollection handler) {
		this.handler = handler;
	}


	/**
	 * @param extendSqlSessionFactory the extendSqlSessionFactory to set
	 */
	public void setExtendSqlSessionFactory(SqlSessionFactory extendSqlSessionFactory) {
		this.extendSqlSessionFactory = extendSqlSessionFactory;
	}


	/**
	 * @param allowBatchMode the allowBatchMode to set
	 */
	public void setAllowBatchMode(Boolean allowBatchMode) {
		this.allowBatchMode = allowBatchMode;
	}

}
