package com.jiuyv.hhc.model.security;

import java.io.Serializable;

/**
 * The Class LogInfo.
 *

 * @author 
 * @since 2014-1-2 16:46:28
 * @version 1.0.0
 */
public class LogInfo implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -571831255833287347L;

	/** The opr no. */
	private Long oprNo;

	/** The result info. */
	private String resultInfo;

	/**
	 * Gets the opr no.
	 * 
	 * @return the opr no
	 */
	public Long getOprNo() {
		return oprNo;
	}

	/**
	 * Sets the opr no.
	 * 
	 * @param oprNo
	 *            the new opr no
	 */
	public void setOprNo(Long oprNo) {
		this.oprNo = oprNo;
	}

	/**
	 * Gets the result info.
	 * 
	 * @return the result info
	 */
	public String getResultInfo() {
		return resultInfo;
	}

	/**
	 * Sets the result info.
	 * 
	 * @param resultInfo
	 *            the new result info
	 */
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	/**
	 * 追加结果信息.
	 *
	 * @param resultInfo the result info
	 */
	public void appendResultInfo(String resultInfo) {
		this.resultInfo = this.resultInfo == null ? resultInfo
				: this.resultInfo + resultInfo;
	}

}
