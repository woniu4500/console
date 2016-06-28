package com.jiuyv.common.excel;

import java.util.ArrayList;
import java.util.List;

public class Xls {
	private int totalRows;
	private int totalCell;
	private List<ArrayList<String>> list;

	/**
	 * @return the totalRows
	 */
	public int getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows
	 *            the totalRows to set
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * @return the totalCell
	 */
	public int getTotalCell() {
		return totalCell;
	}

	/**
	 * @param totalCell
	 *            the totalCell to set
	 */
	public void setTotalCell(int totalCell) {
		this.totalCell = totalCell;
	}

	/**
	 * @return the list
	 */
	public List<ArrayList<String>> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<ArrayList<String>> list) {
		this.list = list;
	}

}
