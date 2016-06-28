package com.jiuyv.common.database;

import java.io.Serializable;

/**
 * Filter对象，用于对数据库进行条件查询。 The Class Filter.
 */
public class Filter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1203115751716593992L;

	/** 类型 字符串 string */
	public static final String STRING = "string";

	/** 类型 数字 numeric */
	public static final String NUMERIC = "numeric";

	/** 类型 金额 moneyNumeric */
	public static final String MONEY = "moneyNumeric";

	/** 类型 布尔 boolean */
	public static final String BOOLEAN = "boolean";

	/** 类型 集合 List */
	public static final String NUMBERLIST = "numberlist";
	/** 类型 集合 List */
	public static final String STRINGLIST = "stringlist";
	/** 类型 集合 List */
	public static final String LIST = "list";

	/** 类型 日期 date (yyyyMMdd) */
	public static final String DATE = "date";

	/** 类型 时间 time (yyyyMMddHHmmss) */
	public static final String TIME = "time";

	/** The data. */
	private Data data;

	/** The field. */
	private String field;

	/**
	 * Instantiates a new filter.
	 * 
	 * @param field
	 *            the field
	 * @param type
	 *            the type
	 * @param value
	 *            the value
	 * @param comparison
	 *            the comparison
	 */
	public Filter(String field, String type, Object value, String comparison) {
		this.field = field;
		this.data = new Data();
		this.data.comparison = comparison;
		this.data.type = type;
		this.data.value = value;
	}

	/**
	 * Instantiates a new filter.
	 */
	public Filter() {
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * Sets the data.
	 * 
	 * @param data
	 *            the new data
	 */
	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * Gets the field.
	 * 
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the field.
	 * 
	 * @param field
	 *            the new field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * The Class Comparison.
	 */
	public static final class Comparison {

		private Comparison() {
		}

		/** 操作符 like */
		public static final String LIKE = "like";

		/** 操作符 not like */
		public static final String ULIKE = "ulike";

		/** 操作符 等于 = */
		public static final String EQ = "eq";

		/** 操作符 不等于 <> */
		public static final String NE = "ne";

		/** 操作符 大于 > */
		public static final String GT = "gt";

		/** 操作符 小于 < */
		public static final String LT = "lt";

		/** 操作符 大于等于 >= */
		public static final String GE = "ge";

		/** 操作符 小于等于 <= */
		public static final String LE = "le";
	}

	/**
	 * The Class Data.
	 */
	public static class Data implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2387035084306283420L;

		/** The type. */
		private String type;

		/** The comparison. */
		private String comparison;

		/** The value. */
		private Object value;

		/**
		 * Gets the type.
		 * 
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * Sets the type.
		 * 
		 * @param type
		 *            the new type
		 */
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * Gets the comparison.
		 * 
		 * @return the comparison
		 */
		public String getComparison() {
			return comparison;
		}

		/**
		 * Sets the comparison.
		 * 
		 * @param comparison
		 *            the new comparison
		 */
		public void setComparison(String comparison) {
			this.comparison = comparison;
		}

		/**
		 * Gets the value.
		 * 
		 * @return the value
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Sets the value.
		 * 
		 * @param value
		 *            the new value
		 */
		public void setValue(Object value) {
			this.value = value;
		}
	}
}
