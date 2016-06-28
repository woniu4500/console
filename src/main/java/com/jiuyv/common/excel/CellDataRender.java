package com.jiuyv.common.excel;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;

/**
 * The Enum CellDataRender.
 *

 * @author 
 * @since 2014-5-26 12:20:58
 * @version 1.0.0
 */
public enum CellDataRender {
	
	/** 日期 d/m/y. */
	date,
	/** 时间 d/m/y hh:mm. */
	time,
	/** 金额（分）#,##0.00. */
	money,
	/** 比例（万分之一）. */
	rate,
	/** 百分比 0.00%*/
	percent,
	/** 默认格式 */
	defaultText;
	
	public void render(Object val , HSSFCell cell, Map<String,Object> param) {
		SimpleDateFormat sdf8 = param.get("SDF_DATE") == null ? new SimpleDateFormat("yyyyMMdd"):(SimpleDateFormat)param.get("SDF_DATE");
		param.put("SDF_DATE", sdf8);
		SimpleDateFormat sdf14 = param.get("SDF_DATETIME") == null ? new SimpleDateFormat("yyyyMMddHHmmss"):(SimpleDateFormat)param.get("SDF_DATETIME");
		param.put("SDF_DATETIME", sdf14);
		try {
			switch ( this ){
			case date: 
				String dateVal = String.valueOf(val);
				cell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
				if ( dateVal.length() == 8 ) {
					cell.setCellValue(sdf8.parse(dateVal));
				}
				break;
			case time:
				String timeVal = String.valueOf(val);
				cell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
				if ( timeVal.length() == 14 ) {
					cell.setCellValue(sdf14.parse(timeVal));
				}
				break;
			case money:
				cell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
				cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				if ( NumberUtils.isNumber(String.valueOf(val) ) ) {
					double cellValue = NumberUtils.toLong(String.valueOf(val))/100.0 ;
					cell.setCellValue( cellValue );
				}
				break;
			case rate: 
				cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				if ( NumberUtils.isNumber(String.valueOf(val) ) ) {
					double cellValue = NumberUtils.toLong(String.valueOf(val))/1000000.0 ;
					cell.setCellValue( cellValue );
				}
				break;
			case percent:
				cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				cell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
				if ( NumberUtils.isNumber(String.valueOf(val) ) ) {
					double cellValue = NumberUtils.toLong(String.valueOf(val))/10000.0 ;
					cell.setCellValue( cellValue );
				}
				break;
			case defaultText: 
			default: 
				cell.setCellValue(String.valueOf(val));
			}
		} catch (Exception e) {
			cell.setCellValue(String.valueOf(val));
		}
		
	}
}
