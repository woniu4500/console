package com.jiuyv.common.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * The Class XLSTemplate.
 */
public class XLSSheetTemplate {

	/** 页名. */
	private String sheetName;

	/** 标题. */
	private String titleText;

	/** 操作人. */
	private String operator;

	/** 操作时间. */
	private String operTime;

	/** 操作数据. */
	private List<? extends Object> data;

	/** The props. */
	private List<CellDataType> props;

	/** The col count. */
	private short colCount;

	/** The row count. */
	private short rowCount;

	/** The head style. */
	private HSSFCellStyle titleStyle;

	/** The head style. */
	private HSSFCellStyle headStyle;

	/** The body style. */
	private HSSFCellStyle bodyStyle;

	/** The user style. */
	private HSSFCellStyle userStyle;

	/** The time style. */
	private HSSFCellStyle timeStyle;

	/**
	 * Instantiates a new xLS sheet template.
	 * 
	 * @param sheetName
	 *            the sheet name
	 * @param titleText
	 *            the title text
	 * @param operator
	 *            the operator
	 * @param operTime
	 *            the oper time
	 * @param data
	 *            the data
	 * @param props
	 *            the props
	 */
	public XLSSheetTemplate(String sheetName, String titleText,
			String operator, String operTime, List<? extends Object> data,
			List<CellDataType> props) {
		this.sheetName = sheetName;
		this.titleText = titleText;
		this.operator = operator;
		this.operTime = operTime;
		setData(data);
		setProps(props);
	}

	/**
	 * Gets the sheet name.
	 * 
	 * @return the sheet name
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * Sets the sheet name.
	 * 
	 * @param sheetName
	 *            the new sheet name
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * Gets the title text.
	 * 
	 * @return the title text
	 */
	public String getTitleText() {
		return titleText;
	}

	/**
	 * Sets the title text.
	 * 
	 * @param titleText
	 *            the new title text
	 */
	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	/**
	 * Gets the operator.
	 * 
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 * 
	 * @param operator
	 *            the new operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Gets the oper time.
	 * 
	 * @return the oper time
	 */
	public String getOperTime() {
		return operTime;
	}

	/**
	 * Sets the oper time.
	 * 
	 * @param operTime
	 *            the new oper time
	 */
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	public List<? extends Object> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 * 
	 * @param data
	 *            the new data
	 */
	public final void setData(List<? extends Object> data) {
		this.data = data;
		if (data != null) {
			this.rowCount = (short) data.size();
		}
	}

	/**
	 * Gets the props.
	 * 
	 * @return the props
	 */
	public List<CellDataType> getProps() {
		return props;
	}

	/**
	 * Sets the props.
	 * 
	 * @param props
	 *            the new props
	 */
	public final void setProps(List<CellDataType> props) {
		this.props = props;
		if (props != null) {
			this.colCount = (short) props.size();
		}
	}

	/**
	 * Gets the col count.
	 * 
	 * @return the col count
	 */
	public short getColCount() {
		return colCount;
	}

	/**
	 * Sets the col count.
	 * 
	 * @param colCount
	 *            the new col count
	 */
	public void setColCount(short colCount) {
		this.colCount = colCount;
	}

	/**
	 * Gets the row count.
	 * 
	 * @return the row count
	 */
	public short getRowCount() {
		return rowCount;
	}

	/**
	 * Sets the row count.
	 * 
	 * @param rowCount
	 *            the new row count
	 */
	public void setRowCount(short rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * Gets the title style.
	 * 
	 * @return the title style
	 */
	public HSSFCellStyle getTitleStyle() {
		return titleStyle;
	}

	/**
	 * Sets the title style.
	 * 
	 * @param titleStyle
	 *            the new title style
	 */
	public void setTitleStyle(HSSFCellStyle titleStyle) {
		this.titleStyle = titleStyle;
	}

	/**
	 * Gets the head style.
	 * 
	 * @return the head style
	 */
	public HSSFCellStyle getHeadStyle() {
		return headStyle;
	}

	/**
	 * Sets the head style.
	 * 
	 * @param headStyle
	 *            the new head style
	 */
	public void setHeadStyle(HSSFCellStyle headStyle) {
		this.headStyle = headStyle;
	}

	/**
	 * Gets the body style.
	 * 
	 * @return the body style
	 */
	public HSSFCellStyle getBodyStyle() {
		return bodyStyle;
	}

	/**
	 * Sets the body style.
	 * 
	 * @param bodyStyle
	 *            the new body style
	 */
	public void setBodyStyle(HSSFCellStyle bodyStyle) {
		this.bodyStyle = bodyStyle;
	}

	/**
	 * Gets the user style.
	 * 
	 * @return the user style
	 */
	public HSSFCellStyle getUserStyle() {
		return userStyle;
	}

	/**
	 * Sets the user style.
	 * 
	 * @param userStyle
	 *            the new user style
	 */
	public void setUserStyle(HSSFCellStyle userStyle) {
		this.userStyle = userStyle;
	}

	/**
	 * Gets the time style.
	 * 
	 * @return the time style
	 */
	public HSSFCellStyle getTimeStyle() {
		return timeStyle;
	}

	/**
	 * Sets the time style.
	 * 
	 * @param timeStyle
	 *            the new time style
	 */
	public void setTimeStyle(HSSFCellStyle timeStyle) {
		this.timeStyle = timeStyle;
	}

}
