package com.jiuyv.common.database.criteria;

/**
 * The Class BeanColumnMap. 用来对存放vo的属性与数据库字段的映射关系
 * 
 * @author Charley
 */
public class BeanColumnMap {
	
	public static final String TYPE_STRING = "java.lang.String";
	
	public static final String TYPE_NUMBER = "java.lang.Long";

	/** The Bean property name. bean的属性名 */
	private String beanPropertyName = null;

	/** The Column name. 数据库字段名 */
	private String columnName = null;

	/** The mapped. 是否已经映射过 */
	private boolean mapped = false;

	/** bean属性的类型，可以供filter使用 */
	private String propertytype = null;

	public String getPropertytype() {
		return this.propertytype;
	}

	public void setPropertytype(String propertytype) {
		this.propertytype = propertytype;
	}

	public BeanColumnMap(String beanPropertyName, String columnName){
		this(beanPropertyName, columnName, TYPE_STRING);
	}
	
	public BeanColumnMap(String beanPropertyName, String columnName,
			String propertytype) {
		super();
		this.beanPropertyName = beanPropertyName;
		this.columnName = columnName;
		this.propertytype = propertytype;
	}
	
	/**
	 * Gets the bean property name.
	 * 
	 * @return the beanPropertyName
	 */
	public String getBeanPropertyName() {
		if (this.beanPropertyName == null) {
			this.beanPropertyName = columnName.trim();
		}
		return this.beanPropertyName;
	}

	/**
	 * Sets the bean property name.
	 * 
	 * @param beanPropertyName
	 *            the beanPropertyName to set
	 */
	public void setBeanPropertyName(String beanPropertyName) {
		this.beanPropertyName = beanPropertyName;
	}

	/**
	 * Gets the column name.
	 * 
	 * @return the columnName
	 */
	public String getColumnName() {
		if (this.columnName != null) {
			this.columnName = columnName.trim();
		}
		return this.columnName;
	}

	/**
	 * Sets the column name.
	 * 
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * Checks if is mapped.
	 * 
	 * @return the mapped
	 */
	public boolean isMapped() {
		return this.mapped;
	}

	/**
	 * Sets the mapped.
	 * 
	 * @param mapped
	 *            the mapped to set
	 */
	public void setMapped(boolean mapped) {
		this.mapped = mapped;
	}

}
