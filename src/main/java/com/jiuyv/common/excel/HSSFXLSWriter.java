package com.jiuyv.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HSSFXLSWriter.
 */
public final class HSSFXLSWriter {

	/** Logger for this class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HSSFXLSWriter.class);

	/** The Constant DEFAULT_TTL. */
	private static final String DEFAULT_TTL = "sheet_1";

	private HSSFXLSWriter() {
	}

	/**
	 * Output xls.
	 * 
	 * @param sheetTemplate
	 *            the sheet template
	 * @param os
	 *            the os
	 * @throws Exception
	 */
	public static void outputXLS(XLSSheetTemplate sheetTemplate, OutputStream os)
			throws Exception {
		if (sheetTemplate == null || os == null) {
			LOGGER.debug("Unexpected arguments null! ");
			return;
		}
		// create work book
		HSSFWorkbook work = new HSSFWorkbook();
		// create sheet
		String sheetName = sheetTemplate.getSheetName() == null ? DEFAULT_TTL : sheetTemplate.getSheetName();
		if ( sheetName.length() > 31 ) { 
			sheetName = sheetName.substring(31);
		}
		HSSFSheet sheet = work.createSheet(sheetName);
		// create title
		createTitle(
				(short) 0,
				sheet,
				sheetTemplate.getTitleText(),
				sheetTemplate.getColCount(),
				sheetTemplate.getTitleStyle() == null ? DefaultCellStyleFactory
						.getTitleStyle(work) : sheetTemplate.getTitleStyle());
		// create sub title
		createSubTitle(work, (short) 1, sheet, sheetTemplate.getOperator(),
				sheetTemplate.getOperTime(), sheetTemplate.getColCount());
		// create head
		createHead(
				(short) 2,
				sheet,
				sheetTemplate.getProps(),
				sheetTemplate.getHeadStyle() == null ? DefaultCellStyleFactory
						.getHeadStyle(work) : sheetTemplate.getHeadStyle());
		// create data
		createData(
				(short) 3,
				sheet,
				sheetTemplate.getProps(),
				sheetTemplate.getData(),
				sheetTemplate.getBodyStyle() == null ? DefaultCellStyleFactory
						.getBodyStyle(work) : sheetTemplate.getBodyStyle(),
				work);
		// write to file
		write2File(work, os);
	}

	/**
	 * Creates the title.
	 * 
	 * @param rowNum
	 *            the row num
	 * @param sheet
	 *            the sheet
	 * @param title
	 *            the title
	 * @param cols
	 *            the cols
	 * @param style
	 *            the style
	 */
	private static void createTitle(short rowNum, HSSFSheet sheet,
			String title, short cols, HSSFCellStyle style) {
		HSSFRow row = sheet.createRow(rowNum);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(title));
		if (style != null) {
			cell.setCellStyle(style);
		}
		sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,0, cols <= 0 ?0:(cols - 1)));
	}

	/**
	 * Creates the createSubTitle.
	 * 
	 * @param rowNum
	 *            the row num
	 * @param sheet
	 *            the sheet
	 * @param user
	 *            the user
	 * @param time
	 *            the time
	 * @param cols
	 *            the cols
	 * @param style
	 *            the style
	 */
	private static void createSubTitle(HSSFWorkbook work, short rowNum, HSSFSheet sheet,
			String user, String time, short cols) {
		HSSFRow row = sheet.createRow(rowNum);
		HSSFCell lCell = row.createCell(0);
		HSSFCell rCell = row.createCell(cols <= 1 ? 1:(cols - 1));

		lCell.setCellValue(new HSSFRichTextString(user));
		lCell.setCellStyle(DefaultCellStyleFactory.getSubTitleStyle(work));
		lCell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_LEFT);

		rCell.setCellValue(new HSSFRichTextString(time));
		rCell.setCellStyle(DefaultCellStyleFactory.getSubTitleStyle(work));
		rCell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_RIGHT);

	}

	/**
	 * Creates the head.
	 * 
	 * @param headRow
	 *            the head row
	 * @param sheet
	 *            the sheet
	 * @param headStyle
	 *            the head style
	 * @param colName
	 *            the col name
	 */
	private static void createHead(short headRow, HSSFSheet sheet,
			List<CellDataType> colName, HSSFCellStyle headStyle) {
		if (colName == null) {
			return;
		}
		HSSFRow row = sheet.createRow(headRow);
		int col = 0;
		for (CellDataType colt : colName) {
			HSSFCell cell = row.createCell(col++, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(colt.getShowName()));
			if (headStyle != null) {
				cell.setCellStyle(headStyle);
			}
		}
	}

	/**
	 * Creates the data.
	 * 
	 * @param startRow
	 *            the start row
	 * @param sheet
	 *            the sheet
	 * @param propNames
	 *            the prop names
	 * @param data
	 *            the data
	 * @param style
	 *            the style
	 * @param wb
	 *            the wb
	 * @throws Exception
	 */
	private static <T> void createData(short startRow, HSSFSheet sheet,
			List<CellDataType> propNames, List<T> data, HSSFCellStyle style,
			HSSFWorkbook wb) throws Exception {
		if (data == null) {
			return;
		}
		short rowNum = startRow;
		for (T tData : data) {
			HSSFRow row = sheet.createRow(rowNum++);
			createRow(row, propNames, tData, style, wb);
		}
	}

	/**
	 * Creates the row.
	 * 
	 * @param row
	 *            the row
	 * @param propNames
	 *            the prop names
	 * @param data
	 *            the data
	 * @param style
	 *            the style
	 * @param wb
	 *            the wb
	 * @throws Exception
	 */
	private static void createRow(HSSFRow row, List<CellDataType> propNames,
			Object data, HSSFCellStyle style, HSSFWorkbook wb) throws Exception {
		if (row == null) {
			return;
		}
		int colNum = 0;
		Map<CellDataRender, HSSFCellStyle> styleMap = new HashMap<CellDataRender, HSSFCellStyle>();
		
		for (CellDataType prop : propNames) {
			HSSFCell cell = row.createCell(colNum++);
			try {
				Object value = PropertyUtils.getProperty(data, prop.getPropName()); 

				HSSFCellStyle cellStyle = styleMap.get(prop.getRender());
				if ( cellStyle == null ) {
					cellStyle = wb.createCellStyle();
					styleMap.put(prop.getRender(), cellStyle);
				}
				cell.setCellStyle(cellStyle);
				if (value == null) {
					continue;
				}
				Map<String, Object> param = new HashMap<String, Object>();
				prop.getRender().render(value, cell, param);
			} catch (Exception e) {
				LOGGER.error("Invalid Access to Object! " + e);
				throw e;
			}
		}
	}

	/**
	 * Write2 file.
	 * 
	 * @param work
	 *            the work
	 * @param os
	 *            the os
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void write2File(HSSFWorkbook work, OutputStream os)
			throws IOException {
		if (os != null && work != null) {
			try {
				work.write(os);
				os.flush();
			} catch (IOException e) {
				LOGGER.error("Invalid output write! ");
				throw e;
			} finally {
				os.close();
			}
		}
	}
	
}
