package com.jiuyv.common.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * A factory for creating DefaultCellStyle objects.
 */
public final class DefaultCellStyleFactory {

	/** 常量: FONT_BODY. */
	private static final short FONT_BODY = 10;

	/** 常量: FONT_TITLE. */
	private static final short FONT_TITLE = 14;

	/** 常量: FONT_SUB. */
	private static final short FONT_SUB = 10;

	/** 常量: FONT_COURIER. */
	private static final String FONT_COURIER = "Arial Unicode MS";

	private DefaultCellStyleFactory() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the head style.
	 * 
	 * @param work
	 *            the work
	 * 
	 * @return the head style
	 */
	public static HSSFCellStyle getHeadStyle(HSSFWorkbook work) {
		if (work == null) {
			return null;
		}
		HSSFCellStyle style = work.createCellStyle();
		HSSFFont font = work.createFont();
		style.setFont(font);
		// align center
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		font.setFontHeightInPoints(FONT_BODY);
		font.setFontName(FONT_COURIER);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		addBorder(style, HSSFCellStyle.BORDER_THIN, HSSFColor.BLACK.index);

		return style;
	}

	/**
	 * Gets the title style.
	 * 
	 * @param work
	 *            the work
	 * 
	 * @return the title style
	 */
	public static HSSFCellStyle getTitleStyle(HSSFWorkbook work) {
		if (work == null) {
			return null;
		}
		HSSFCellStyle style = work.createCellStyle();
		HSSFFont font = work.createFont();
		style.setFont(font);
		// align center
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		font.setFontHeightInPoints(FONT_TITLE);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName(FONT_COURIER);

		return style;
	}

	/**
	 * Gets the body style.
	 * 
	 * @param work
	 *            the work
	 * 
	 * @return the body style
	 */
	public static HSSFCellStyle getBodyStyle(HSSFWorkbook work) {
		if (work == null) {
			return null;
		}
		HSSFCellStyle style = work.createCellStyle();
		HSSFFont font = work.createFont();
		style.setFont(font);
		// align center
		font.setFontHeightInPoints(FONT_BODY);
		font.setFontName(FONT_COURIER);
		addBorder(style, HSSFCellStyle.BORDER_THIN, HSSFColor.BLACK.index);
		return style;
	}

	/**
	 * Gets the sub title style.
	 * 
	 * @param work
	 *            the work
	 * 
	 * @return the sub title style
	 */
	public static HSSFCellStyle getSubTitleStyle(HSSFWorkbook work) {
		if (work == null) {
			return null;
		}
		HSSFCellStyle style = work.createCellStyle();
		HSSFFont font = work.createFont();
		style.setFont(font);
		// align center
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		font.setFontHeightInPoints(FONT_SUB);
		font.setFontName(FONT_COURIER);

		return style;
	}

	/**
	 * Adds the border.
	 * 
	 * @param style
	 *            the style
	 * @param border
	 *            the border
	 * @param color
	 *            the color
	 */
	public static void addBorder(HSSFCellStyle style, short border, short color) {
		if (style != null) {
			style.setBorderBottom(border);
			style.setBottomBorderColor(color);
			style.setBorderLeft(border);
			style.setLeftBorderColor(color);
			style.setBorderRight(border);
			style.setRightBorderColor(color);
			style.setBorderTop(border);
			style.setTopBorderColor(color);
		}
	}

}
