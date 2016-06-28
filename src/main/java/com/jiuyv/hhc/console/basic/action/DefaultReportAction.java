package com.jiuyv.hhc.console.basic.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jiuyv.common.report.ReportUtil;


/**
 * 默认的报表Action
 * 
 * 提供 已有的 printHtml、printExcel、printWord、printPdf 方法 需要实现 processData 方法.
 *

 * @author 
 * @since 2014-1-9 16:17:33
 * @version 1.0.0
 */
public abstract class DefaultReportAction extends DefaultPageSupportAction {

	/** The Constant ERROR_PAGE:errRpt. */
	public static final String ERROR_PAGE = "errRpt";
	/** 报表基本路径 */
	public static final String RPT_BASE = "/com/jiuyv/report";
	/** 制表时间  */
	public static final String PARAM_RPT_TIME = "RPT_TIME";
	/** 制表人 */
	public static final String PARAM_RPT_USER = "RPT_USER";

	/**
	 * 生成报表需要的数据.
	 * 
	 * @return the rpt vo
	 * @throws Exception
	 *             the exception
	 */
	protected abstract RptVo processData() throws Exception;

	/**
	 * 预设制表参数
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map presetParams( Map params ) throws Exception {
		Map rptParams = params == null?new HashMap():params;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ( !rptParams.containsKey(PARAM_RPT_TIME) ) {
			rptParams.put(PARAM_RPT_TIME, sdf.format(new Date()));
		}
		if ( !rptParams.containsKey(PARAM_RPT_USER)) {
			rptParams.put(PARAM_RPT_USER, getUserInfo().getUserName());
		}
		return params;
	}
	/**
	 * 报表预览.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String printHtml() throws Exception {
		RptVo rptVo = processData();
		String value = null;
		if (rptVo == null) {
			value = ERROR_PAGE;
		} else {
			value = rptVo.getResult();
		}
		if (ERROR_PAGE.equals(value)) {
			return value;
		}
		ReportUtil.reportNewHtml(rptVo.getRptHtmlPath(), presetParams(rptVo.getRptParam()),
				rptVo.getRptData());
		return value;
	}

	/**
	 * 报表excel导出.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String printExcel() throws Exception {
		RptVo rptVo = processData();
		if (rptVo != null) {
			ReportUtil.reportExcel(rptVo.getRptExcelPath(), presetParams(rptVo.getRptParam()),
					rptVo.getRptData());
		}
		return NONE;
	}

	/**
	 * 报表word rtf导出.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String printWord() throws Exception {
		RptVo rptVo = processData();
		if (rptVo != null) {
			ReportUtil.reportWord(rptVo.getRptWordPath(), presetParams(rptVo.getRptParam()),
					rptVo.getRptData());
		}
		return NONE;
	}

	/**
	 * 报表word docx导出.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String printDocx() throws Exception {
		RptVo rptVo = processData();
		if (rptVo != null) {
			ReportUtil.reportDocx(rptVo.getRptWordPath(), presetParams(rptVo.getRptParam()),
					rptVo.getRptData());
		}
		return NONE;
	}
	
	/**
	 * 导出pdf导出.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String printPdf() throws Exception {
		RptVo rptVo = processData();
		if (rptVo != null) {
			ReportUtil.reportPdf(rptVo.getRptPdfPath(), presetParams(rptVo.getRptParam()),
					rptVo.getRptData());
		}
		return NONE;
	}

}
