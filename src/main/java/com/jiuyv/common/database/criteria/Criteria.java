package com.jiuyv.common.database.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class Criteria.
 *
 * @author
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Criteria {
	
	/** 独立条件 is null , is not null. */
	private List<String> criteriaWithoutValue;
	
	/** 单值条件 = > >= < <= !=. */
	private List<Map<String, Object>> criteriaWithSingleValue;

	/** 多值条件 in() not in. */
	private List<ListCondition> criteriaWithListValue;

	/** 双值条件 between, not between. */
	private List<Map<String, Object>> criteriaWithBetweenValue;

	/**
	 * The Constructor.
	 */
	public Criteria() {
		super();
		criteriaWithoutValue = new ArrayList<String>();
		criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
		criteriaWithListValue = new ArrayList<ListCondition>();
		criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
	}

	/**
	 * Checks if is valid.
	 *
	 * @return true, if checks if is valid
	 */
	public boolean isValid() {
		return criteriaWithoutValue.size() > 0
				|| criteriaWithSingleValue.size() > 0
				|| criteriaWithListValue.size() > 0
				|| criteriaWithBetweenValue.size() > 0;
	}

	/**
	 * 获取 独立条件 is null , is not null.
	 *
	 * @return the 独立条件 is null , is not null
	 */
	public List<String> getCriteriaWithoutValue() {
		return criteriaWithoutValue;
	}

	/**
	 * 获取 单值条件 = > >= < <= !=.
	 *
	 * @return the 单值条件 = > >= < <= !=
	 */
	public List<Map<String, Object>> getCriteriaWithSingleValue() {
		return criteriaWithSingleValue;
	}

	/**
	 * 获取 多值条件 in() not in.
	 *
	 * @return the 多值条件 in() not in
	 */
	public List<ListCondition> getCriteriaWithListValue() {
		return criteriaWithListValue;
	}

	/**
	 * 获取 双值条件 between, not between.
	 *
	 * @return the 双值条件 between, not between
	 */
	public List<Map<String, Object>> getCriteriaWithBetweenValue() {
		return criteriaWithBetweenValue;
	}

	/**
	 * Adds the criterion.
	 *
	 * @param condition the condition
	 * @param value the value
	 * @param property the property
	 */
	public void addCriterion(String condition, Object value, String property) {
		if (value == null) {
			throw new RuntimeException("Value for " + property
					+ " cannot be null");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("value", value);
		criteriaWithSingleValue.add(map);
	}

	/**
	 * Adds the criterion.
	 *
	 * @param condition the condition
	 * @param values the values
	 * @param property the property
	 */
	public void addCriterion(String condition, List<? extends Object> values,
			String property) {
		if (values == null || values.size() == 0) {
			throw new RuntimeException("Value list for " + property
					+ " cannot be null or empty");
		}
		ListCondition lc = new ListCondition();
		lc.setCondition(condition);
		lc.setValues(values);
		criteriaWithListValue.add(lc);
	}

	/**
	 * Adds the criterion.
	 *
	 * @param condition the condition
	 * @param value1 the value1
	 * @param value2 the value2
	 * @param property the property
	 */
	public void addCriterion(String condition, Object value1, Object value2,
			String property) {
		if (value1 == null || value2 == null) {
			throw new RuntimeException("Between values for " + property
					+ " cannot be null");
		}
		List<Object> list = new ArrayList<Object>();
		list.add(value1);
		list.add(value2);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("values", list);
		criteriaWithBetweenValue.add(map);
	}

}
