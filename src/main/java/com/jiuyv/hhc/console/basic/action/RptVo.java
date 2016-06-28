package com.jiuyv.hhc.console.basic.action;

import java.util.List;
import java.util.Map;

/**
 * 报表对象.
 *

 * @author 
 * @since 2014-1-9 16:09:39
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
public class RptVo {

	/** report path. */
	private String rptPath;

	/** report parameter. */
	private Map rptParam;

	/** report data. */
	private List rptData;

	/** ActionResult. */
	private String result;
	
	/** 报表HTMLjasper路径 */
	private String rptHtmlPath ; 
	
	/** 报表Wordjasper路径 */
	private String rptWordPath ; 
	
	/** 报表Exceljasper路径 */
	private String rptExcelPath ; 
	
	/** 报表Pdfjasper路径 */
	private String rptPdfPath ; 

	/**
	 * Instantiates a new rpt vo.
	 *
	 * @param rptPath the rpt path
	 * @param rptParam the rpt param
	 * @param rptData the rpt data
	 */
	public RptVo(String rptPath, Map rptParam, List rptData) {
		this(rptPath, rptParam, rptData, "none");
	}

	/**
	 * Instantiates a new rpt vo.
	 *
	 * @param rptPath the rpt path
	 * @param rptParam the rpt param
	 * @param rptData the rpt data
	 * @param result the result
	 */
	public RptVo(String rptPath, Map rptParam, List rptData, String result) {
		super();
		this.rptPath = rptPath;
		this.rptHtmlPath = rptPath ;
		this.rptExcelPath = rptPath ; 
		this.rptPdfPath = rptPath ;
		this.rptWordPath = rptPath ;
		this.rptParam = rptParam;
		this.rptData = rptData;
		this.result = result;
	}

	/**
	 * 获取 report path.
	 *
	 * @return the report path
	 */
	public String getRptPath() {
		return rptPath;
	}

	/**
	 * 设置 report path.
	 *
	 * @param rptPath the new report path
	 */
	public void setRptPath(String rptPath) {
		this.rptPath = rptPath;
	}

	/**
	 * 获取 report parameter.
	 *
	 * @return the report parameter
	 */
	public Map getRptParam() {
		return rptParam;
	}

	/**
	 * 设置 report parameter.
	 *
	 * @param rptParam the new report parameter
	 */
	public void setRptParam(Map rptParam) {
		this.rptParam = rptParam;
	}

	/**
	 * 获取 report data.
	 *
	 * @return the report data
	 */
	public List getRptData() {
		return rptData;
	}

	/**
	 * 设置 report data.
	 *
	 * @param rptData the new report data
	 */
	public void setRptData(List rptData) {
		this.rptData = rptData;
	}

	/**
	 * 获取 actionResult.
	 *
	 * @return the actionResult
	 */
	public String getResult() {
		return result;
	}

	/**
	 * 设置 actionResult.
	 *
	 * @param result the new actionResult
	 */
	public void setResult(String result) {
		this.result = result;
	}

	public String getRptHtmlPath() {
		return rptHtmlPath;
	}

	public void setRptHtmlPath(String rptHtmlPath) {
		this.rptHtmlPath = rptHtmlPath;
	}

	public String getRptWordPath() {
		return rptWordPath;
	}

	public void setRptWordPath(String rptWordPath) {
		this.rptWordPath = rptWordPath;
	}

	public String getRptExcelPath() {
		return rptExcelPath;
	}

	public void setRptExcelPath(String rptExcelPath) {
		this.rptExcelPath = rptExcelPath;
	}

	public String getRptPdfPath() {
		return rptPdfPath;
	}

	public void setRptPdfPath(String rptPdfPath) {
		this.rptPdfPath = rptPdfPath;
	}

}
