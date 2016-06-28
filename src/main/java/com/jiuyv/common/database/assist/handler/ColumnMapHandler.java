package com.jiuyv.common.database.assist.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.common.database.assist.util.SpecBeanUtils;
import com.jiuyv.common.database.criteria.BeanColumnMap;
import com.jiuyv.common.database.criteria.ConditionMap;


/**
 * The Class ColumnMapHandler.
 *
 * @author 
 * @since 2013-12-19 14:52:14
 * @version 1.0.0
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ColumnMapHandler implements
		AdditionalStatementHandler<MappedStatement> {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ColumnMapHandler.class);

	/** The Constant F_SELECT. */
	public static final String F_SELECT = "select ";
	
	/** The Constant F_FROM. */
	public static final String F_FROM = " from ";
	
	/** The Constant F_AS. */
	public static final String F_AS = " as ";

	/** The statement map. */
	private Map<String, Map<String, BeanColumnMap>> statementMap;

	/**
	 * Instantiates a new column map handler.
	 */
	public ColumnMapHandler() {
		this.statementMap = new HashMap<String, Map<String, BeanColumnMap>>();
	}

	/* (non-Javadoc)
	 * @see com.jiuyv.common.database.assist.handler.AdditionalStatementHandler#onEventLoop(java.lang.Object)
	 */
	
	public MappedStatement onEventLoop(MappedStatement ms) {
		String id = ms.getId();
		String sql = ms.getBoundSql(null).getSql();
		List<ResultMap> rms = ms.getResultMaps();
		if (rms == null || rms.size() != 1) {
			LOG.warn("Statement[{}] do not have beanmap. ", id);
			return null;
		}
		ResultMap rm = rms.get(0);
		if (rm == null || rm.getType() == null) {
			return null;
		}
		Class rc = rm.getType();
		// 替换换行符
		sql = sql.replaceAll("[\\t\\n\\r]", " ");
		String lsql = sql.toLowerCase();
		int si = lsql.indexOf(F_SELECT);
		int fi = lsql.indexOf(F_FROM);
		if (si < 0 || fi < 0) {
			LOG.error("Invalid Select SQL : {} ", sql);
			return null;
		}
		String columns = sql.substring(
				lsql.indexOf(F_SELECT) + F_SELECT.length(),
				lsql.indexOf(F_FROM));

		ColumnReader reader = new ColumnReader(columns, rc,
				rm.getResultMappings());
		Map<String, BeanColumnMap> m = reader.readColumnMap();
		statementMap.put(id, m);
		return ms;
	}

	/**
	 * The Class ColumnReader.
	 *
	 * @author 
	 * @since 2013-12-19 14:52:14
	 * @version 1.0.0
	 */
	public static class ColumnReader {
		
		/** The sql string. */
		private String sqlString;
		
		/** The sql array. */
		private String[] sqlArray;
		
		/** The array index. */
		private int arrayIndex;
		
		/** The columns. */
		private List<String> columns;
		
		/** The column map. */
		private ConditionMap columnMap;
		
		/** The result type. */
		private Class resultType;
		
		/** The result map. */
		private List<ResultMapping> resultMap;
		
		/** The inline map. */
		private boolean inlineMap = true;

		/**
		 * Read column map.
		 *
		 * @return the map
		 */
		public Map<String, BeanColumnMap> readColumnMap() {
			if (inlineMap) {
				// map based on the sql
				this.readAll();
			} else {
				// based on ResultMapping
				for (ResultMapping rm : resultMap) {
					columnMap.add(rm.getProperty(), rm.getColumn(), rm
							.getJavaType().getName());
				}
			}
			return columnMap.getMap();
		}

		/**
		 * split sql result part.
		 *
		 * @return the string
		 */
		private String read() {
			if (arrayIndex >= sqlArray.length) {
				return null;
			}
			String s = sqlArray[arrayIndex++];
			int start = StringUtils.countMatches(s, "(");
			if (start > 0) {
				// process
				int end = StringUtils.countMatches(s, ")");
				int diff = start - end;
				if (diff == 0) {
					return s;
				}
				StringBuffer buf = new StringBuffer(s);
				while (diff > 0) {
					String tmp = sqlArray[arrayIndex++];
					buf.append(",").append(tmp);
					int ts = StringUtils.countMatches(tmp, "(");
					int te = StringUtils.countMatches(tmp, ")");
					diff += (ts - te);
				}
				return buf.toString();
			} else {
				return s;
			}
		}

		/**
		 * read column map based on sql.
		 *
		 * @return the list
		 */
		private List<String> readAll() {
			String column = null;
			while ((column = read()) != null) {
				columns.add(column);
				Pattern pattern = Pattern.compile(F_AS,
						Pattern.CASE_INSENSITIVE);
				String[] cols = pattern.split(column);
				switch (cols.length) {
				case 0:
					break;
				case 1:
					String col1 = cols[0].trim();
					String prn1 = col1.contains(".") ? col1.split("\\.")[1]
							: col1;
					Class rt1 = SpecBeanUtils.getPropertyType(resultType, prn1);
					if (rt1 == null) {
						break;
					}
					columnMap.add(prn1.trim(), col1, rt1.getName());
					break;
				case 2:
					String col2 = cols[0].trim();
					String prn2 = cols[1].trim();
					Class rt2 = SpecBeanUtils.getPropertyType(resultType, prn2);
					if (rt2 == null) {
						break;
					}
					columnMap.add(prn2, col2, rt2.getName());
					break;
				default:
					// do not handle complex column
					break;
				}
			}
			return columns;
		}

		/**
		 * 重置内容.
		 */
		private void reset() {
			this.arrayIndex = 0;
			this.columnMap = new ConditionMap();
			this.columns = new ArrayList();
		}

		/**
		 * 构造函数.
		 *
		 * @param sqlString the sql string
		 * @param resultType the result type
		 * @param resultMap the result map
		 */
		public ColumnReader(String sqlString, Class resultType,
				List<ResultMapping> resultMap) {
			this.sqlString = sqlString;
			this.resultType = resultType;
			// 初步分割，无法识别多级括号
			sqlArray = this.sqlString.split(",+(?=[^\\)]*(\\(|$))");
			this.resultMap = resultMap;
			if (this.resultMap != null && this.resultMap.size() > 0) {
				inlineMap = false;
			}
			this.reset();
		}
	}

	/**
	 * Gets the statement map.
	 *
	 * @return the statementMap
	 */
	public Map<String, Map<String, BeanColumnMap>> getStatementMap() {
		return statementMap;
	}
}
