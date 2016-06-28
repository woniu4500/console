package com.jiuyv.common.database.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Filter.Comparison;
import com.jiuyv.common.database.Filter.Data;
import com.jiuyv.common.web.util.WebDateUtil;

/**
 * criteria查询工具.
 *
 * @author 
 * @since 2013-12-19 15:40:23
 * @version 1.0.0
 */
public final class CriteriaUtil {
	/** 每页记录数excel. */
	public static final String EXCEL = "excel";
	/** 百分号. */
	public static final String PERCENT_SIGN = "%";

	/** 日志记录. */
	private static final Logger LOG = LoggerFactory.getLogger(CriteriaUtil.class);
	/** 常量 OPER_MAP. */
	private static final Map<String, String> OPER_MAP = new HashMap<String, String>();
	static {
		OPER_MAP.put("gt", " > ");
		OPER_MAP.put("lt", " < ");
		OPER_MAP.put("ge", " >= ");
		OPER_MAP.put("le", " <= ");
		OPER_MAP.put("eq", " = ");
		OPER_MAP.put("ne", " <> ");
		OPER_MAP.put("ulike", " NOT LIKE ");
		OPER_MAP.put("like", " LIKE ");
		OPER_MAP.put("in", " IN ");

	}

	/**
	 * Instantiates a new criteria util.
	 */
	private CriteriaUtil() {
	}

	/**
	 * 
	 * 获得操作符 "gt",">" "lt","<" "eq","=" "ge",">=" "le","<=".
	 * 
	 * @param comp :
	 *            "gt", "lt", "eq", "ge", "le"
	 * 
	 * @return '>', '<', '=', '>=', '<='
	 */
	public static String getComp(String comp) {
		if (comp == null) {
			return null;
		}

		String tcomp = comp.trim();

		String oper = null;
		oper = OPER_MAP.get(tcomp);

		return oper;
	}

	/**
	 * 对单个对象进行类型转换.
	 *
	 * @param orgvalue the orgvalue
	 * @param type the type
	 * @param compar the compar
	 * @return the object
	 */
	public static Object convertonevalue(Object orgvalue, String type,
			String compar) {
		if (type.equals(Filter.NUMERIC) || type.equals(Filter.MONEY)) {
			// 数字
			return Long.valueOf(orgvalue.toString());
		}
		// condition date 8
		if (Filter.DATE.equalsIgnoreCase(type) && orgvalue instanceof String) {
			return WebDateUtil.parseDateFromVo2Db((String) orgvalue);
		}
		// condition time 14
		// ADDED FOR TIME
		if (Filter.TIME.equalsIgnoreCase(type) && orgvalue instanceof String ) {
			return WebDateUtil.parseDateFromVo2Db14((String) orgvalue);
		}
		String newvalue = null;
		newvalue = String.valueOf(orgvalue);
		newvalue = newvalue.trim();
		// 字符判断是否有like的问题
		if (compar.equals(Comparison.LIKE) || compar.equals(Comparison.ULIKE)) {
			newvalue = PERCENT_SIGN + newvalue + PERCENT_SIGN;
		}
		// 字符串类型LIST判断
		if ( Filter.NUMBERLIST.equals(type) && orgvalue instanceof String ) {
			String strVal = (String) orgvalue;
			// 默认使用,分隔
			String[] valArr = strVal.split(",");
			List<Long> valList = new ArrayList<Long>() ;
			for (String val: valArr) {
				if ( NumberUtils.isNumber(val) ) {
					valList.add(Long.valueOf(val));
				}
			}
			return valList ; 
		}
		if ( (Filter.STRINGLIST.equals(type)||Filter.LIST.equals(type)) && orgvalue instanceof String ) {
			String strVal = (String) orgvalue;
			// 默认使用,分隔
			String[] valArr = strVal.split(",");
			return Arrays.asList(valArr);
		}
		return newvalue;

	}

	/**
	 * 对列表和单个的进行类型转换.
	 *
	 * @param tempdatax the tempdatax
	 * @param listtype the listtype
	 * @return the object
	 */
	public static Object convertvalue(Data tempdatax, String listtype) {
		// 对列表和单个的进行类型转换
		Data tempdata = null;
		try {
			tempdata = (Data) BeanUtils.cloneBean(tempdatax);
		} catch (Exception e) {
			LOG.error("Clone bean exception", e);
		}
		if (tempdata == null) {
			return null;
		}
		Object tempvalue = tempdata.getValue();

		if (!(tempvalue instanceof java.util.List)) {
			tempdata.setValue(convertonevalue(tempdata.getValue(), tempdata
					.getType(), tempdata.getComparison()));
		}
		return tempdata.getValue();
	}

	/**
	 * Gen criteriaby filter. 根据filter条件生成Criteria对象
	 * 
	 * @param filters
	 *            the filters
	 * @param beanColumnMappings
	 *            the bean column mappings
	 * 
	 * @return the criteria
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Criteria genCriteriabyFilter(List<Filter> filters,
			Map<String, BeanColumnMap> beanColumnMappings) {
		Criteria newCriteria = new Criteria();
		if (filters != null && filters.size() > 0) {
			for (Filter oneFilter : filters) {
				// 先根据filter里的属性名得到数据库列名
				String filterpropretyname = oneFilter.getField();
				BeanColumnMap beanColumn = beanColumnMappings
						.get(filterpropretyname.trim().toLowerCase());
				if (beanColumn != null) {
					String columnname = beanColumn.getColumnName();
					// 然后根据判断条件添加Criteria
					Data data = oneFilter.getData();
					// 先取出colume的类型，如果没有data类型定义的话，则根据columne的类型添加
					String columtype = beanColumn.getPropertytype();

					if (data.getType() == null) {
						if (columtype.toLowerCase().indexOf("string") > -1) {
							data.setType(Filter.STRING);
						} else {
							data.setType(Filter.NUMERIC);
						}
					}
					if (data.getType() != null
							&& data.getType().equals(Filter.LIST)) {
						if (columtype.toLowerCase().indexOf("string") > -1) {
							data.setType(Filter.STRINGLIST);
						} else {
							data.setType(Filter.NUMBERLIST);
						}
					}

					if (data.getType().equals(Filter.NUMBERLIST)) {
						String condition = columnname + getComp("in");
						newCriteria.addCriterion(condition,
								(List) convertvalue(data, Filter.NUMERIC),
								filterpropretyname);
					} else if (data.getType().equals(Filter.STRINGLIST)) {
						String condition = columnname + getComp("in");
						newCriteria.addCriterion(condition,
								(List) convertvalue(data, Filter.STRING),
								filterpropretyname);
					} else {
						String condition = columnname
								+ getComp(data.getComparison());
						newCriteria.addCriterion(condition, convertvalue(data,
								null), filterpropretyname);
					}
				}
			}
		}
		return newCriteria;
	}

}
