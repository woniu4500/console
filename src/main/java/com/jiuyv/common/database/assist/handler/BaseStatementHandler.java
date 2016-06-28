package com.jiuyv.common.database.assist.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.jiuyv.common.database.assist.util.SpecBeanUtils;

/**
 * The Class BaseStatementHandler.
 *
 * @author 
 * @since 2013-12-19 15:46:43
 * @version 1.0.0
 */
public class BaseStatementHandler implements AddonStatementHandler{

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(BaseStatementHandler.class);
	
	/** config. */
	private Configuration config ;
	
	/** 模板sqlNode. */
	private List<SqlNode> sqlContext ;
	
	/** 新构建的语句列表. */
	private List<MappedStatement> listStatements ; 
	
	/** 模板插入位置. */ 
	private int position; 
	
	/** 命名后缀. */
	private String append;
	
	/** 模板ID. */
	private String statementId;
	
	/** 是否启用. */
	private boolean available = true; 
	
	/** The Constant ROOT_NODE. */
	public static final String ROOT_NODE = "rootSqlNode";
	
	/** The Constant CONTENT_NODE. */
	public static final String CONTENT_NODE = "contents";
	
	/**
	 * Instantiates a new base statement handler.
	 *
	 * @param config the config
	 * @param qtype the qtype
	 */
	public BaseStatementHandler(Configuration config, QueryType qtype ){
		this(config,qtype.getMapperId(),qtype.getAppend(),qtype.getPosition());
	}
	
	/**
	 * Instantiates a new base statement handler.
	 *
	 * @param config the config
	 * @param statementId the statement id
	 * @param append the append
	 * @param position the position
	 */
	public BaseStatementHandler(Configuration config, String statementId, String append, int position) {
		this.config = config ;
		this.listStatements = new ArrayList<MappedStatement>();
		this.position = position ; 
		this.append = append;
		this.statementId = statementId; 
		//
		this.initContext(this.config, this.statementId);
	}
	
	/**
	 * Inits the context.
	 *
	 * @param config the config
	 * @param statementId the statement id
	 */
	private void initContext (Configuration config , String statementId ) {
		MappedStatement ms = config.getMappedStatement(statementId);
		if ( ms == null ) {
			LOG.error("Cannot find statement : " + statementId);
			return ; 
		}
		SqlNode sn = SpecBeanUtils.getProperty(ms.getSqlSource(), ROOT_NODE);
		if ( sn != null && sn instanceof MixedSqlNode) {
			MixedSqlNode msn = (MixedSqlNode) sn ;
			this.sqlContext = SpecBeanUtils.getProperty(msn, CONTENT_NODE);
		}
		if ( this.sqlContext == null ) {
			LOG.error("Cannot init context. ");
			this.available = false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jiuyv.common.database.assist.handler.AdditionalStatementHandler#onEventLoop(java.lang.Object)
	 */
	public MappedStatement onEventLoop(MappedStatement mappedStatement){
		if ( !this.available ) {
			return null ; 
		}
		// Find out select statement
		SqlSource sqlSrc = mappedStatement.getSqlSource();
		// Get root node 
		SqlNode node = SpecBeanUtils.getProperty(sqlSrc, ROOT_NODE);
		List<SqlNode> contents = SpecBeanUtils.duplicate(this.sqlContext); 
		contents.add(this.position, SpecBeanUtils.duplicate(node));
		SqlNode newNode = new MixedSqlNode(contents);
		SqlSource newSqlSrc = new DynamicSqlSource(this.config, newNode);
		String id = mappedStatement.getId() + this.append;
		// Config statement builder. 
		MappedStatement.Builder builder = new MappedStatement.Builder(config, id, newSqlSrc, SqlCommandType.SELECT);
		this.configStatementBuilder(mappedStatement, builder);
	    MappedStatement statement = builder.build();
	    this.listStatements.add(statement);
		return statement;
	}
	
	/**
	 * Config statement builder.
	 *
	 * @param mappedStatement the mapped statement
	 * @param builder the builder
	 */
	protected void configStatementBuilder(MappedStatement mappedStatement, MappedStatement.Builder builder ) {
		builder.resource(mappedStatement.getResource())
				.fetchSize(mappedStatement.getFetchSize())
				.statementType(mappedStatement.getStatementType())
				.keyGenerator(mappedStatement.getKeyGenerator())
				.keyProperty(
						StringUtils.arrayToCommaDelimitedString(mappedStatement
								.getKeyProperties()))
				.keyColumn(
						StringUtils.arrayToCommaDelimitedString(mappedStatement
								.getKeyColumns()))
				.databaseId(mappedStatement.getDatabaseId())
				.timeout(mappedStatement.getTimeout())
				.parameterMap(mappedStatement.getParameterMap())
				.resultMaps(mappedStatement.getResultMaps())
				.resultSetType(mappedStatement.getResultSetType())
				.cache(mappedStatement.getCache())
				.flushCacheRequired(mappedStatement.isFlushCacheRequired())
				.useCache(mappedStatement.isUseCache());
	}
	
	/**
	 * Gets the statements.
	 *
	 * @return the listStatements
	 */
	public List<MappedStatement> getStatements() {
		return listStatements;
	}

	/**
	 * 设置 命名后缀.
	 *
	 * @param append the new 命名后缀
	 */
	public void setAppend(String append) {
		this.append = append;
	}

	/**
	 * 设置 模板ID.
	 *
	 * @param statementId the statementId to set
	 */
	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	/**
	 * 设置 模板插入位置.
	 *
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	
}
