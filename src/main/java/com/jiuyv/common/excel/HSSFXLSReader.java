package com.jiuyv.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HSSFXLSReader {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HSSFXLSReader.class);

	private HSSFXLSReader(){
		
	}
	
	public static Xls inputXls(File file) {
		Xls x = new Xls();
		int totalNum = 0;
		if (file == null) {
			return null;
		}
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(file));
			if (wb instanceof HSSFWorkbook) {
				x = readHSSFSheet(wb.getSheetAt(0));
			} else {
				if (wb instanceof XSSFWorkbook) {
					x = readXSSFSheet(wb.getSheetAt(0));
				}
			}
			totalNum = wb.getSheetAt(0).getPhysicalNumberOfRows();
		} catch (IllegalArgumentException e) {
			LOGGER.error("inputXls(File)", e); 
			return null;
		} catch (IOException e) {
			LOGGER.error("inputXls(File)", e); 
			return null;
		} catch (InvalidFormatException e) {
			LOGGER.error("inputXls(File)", e); 
			return null;
		}
		x.setTotalRows(totalNum);
		return x;
	}

	public static Xls readHSSFSheet(Sheet s) {
		Xls x = new Xls();
		List<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		int rowId = 0;
		for (int i = 0; i < s.getPhysicalNumberOfRows(); i++) {
			Row r = null;
			do {
				r = s.getRow(rowId);
				rowId++;
			} while (r == null);
			ArrayList<String> cells = new ArrayList<String>();
			int cellId = 0;
			for (int j = 0; j < r.getPhysicalNumberOfCells(); j++) {
				Cell cell = null;
				do {
					cell = r.getCell(cellId);
					cellId++;
				} while (cell == null);
				String cellValue = "";
				if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
					/** */
					/** 在excel里,日期也是数字,在此要进行判断 */
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = DateUtil.getJavaDate(DateUtil
								.getExcelDate(cell.getDateCellValue()));
						SimpleDateFormat f = new SimpleDateFormat(
								"YYYYMMDDHHMISS");
						cellValue = f.format(date);
					} else {
						cellValue = getRightStr(cell.getNumericCellValue() + "");
					}
				}
				/** */
				/** 处理字符串型 */
				else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
					cellValue = cell.getStringCellValue();
				}
				/** */
				/** 处理布尔型 */
				else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
					cellValue = cell.getBooleanCellValue() + "";
				}
				/** */
				/** 其它的,非以上几种数据类型 */
				else {
					cellValue = cell.toString() + "";
				}
				cells.add(cellValue);
			}
			rows.add(cells);
		}
		x.setList(rows);
		return x;
	}

	public static Xls readXSSFSheet(Sheet s) {
		Xls x = new Xls();
		List<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		int rowId = 0;
		for (int i = 0; i < s.getPhysicalNumberOfRows(); i++) {
			Row r = null;
			do {
				r = s.getRow(rowId);
				rowId++;
			} while (r == null);
			ArrayList<String> cells = new ArrayList<String>();
			int cellId = 0;
			for (int j = 0; j < r.getPhysicalNumberOfCells(); j++) {
				Cell cell = null;
				do {
					cell = r.getCell(cellId);
					cellId++;
				} while (cell == null);
				String cellValue = "";
				if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
					/** */
					/** 在excel里,日期也是数字,在此要进行判断 */
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = DateUtil.getJavaDate(DateUtil
								.getExcelDate(cell.getDateCellValue()));
						SimpleDateFormat f = new SimpleDateFormat(
								"YYYYMMDDHHMISS");
						cellValue = f.format(date);
					} else {
						cellValue = getRightStr(cell.getNumericCellValue() + "");
					}
				}
				/** */
				/** 处理字符串型 */
				else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
					cellValue = cell.getStringCellValue();
				}
				/** */
				/** 处理布尔型 */
				else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
					cellValue = cell.getBooleanCellValue() + "";
				}
				/** */
				/** 其它的,非以上几种数据类型 */
				else {
					cellValue = cell.toString() + "";
				}
				cells.add(cellValue);
			}
			rows.add(cells);
		}
		x.setList(rows);
		return x;
	}

	public static String getRightStr(String sNum) {
		DecimalFormat decimalFormat = new DecimalFormat("#.000000");
		String resultStr = decimalFormat.format(new Double(sNum));
		if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
			resultStr = resultStr.substring(0, resultStr.indexOf('.'));
		}
		return resultStr;
	}
}
