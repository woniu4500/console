package com.jiuyv.hhc.model.security.rpt;

import java.util.List;

/**
 * The Class LoginCompStatisticBean.
 *

 * @author 
 * @since 2014-1-21 10:41:51
 * @version 1.0.0
 */
public class LoginCompStatisticBean {
	
	/** 开始日期. */
	private String bgnDate;
	
	/** 结束日期. */
	private String endDate;
	
	/** 用户. */
	private String operUser;
	
	/** 登录统计信息. */
	private List<RptFieldBean> loginStatistic ; 
	
	/** 操作信息. */
	private List<RptFieldBean> operateStatistic ;

	/**
	 * Instantiates a new login comp statistic bean.
	 */
	public LoginCompStatisticBean() {}
	
	
	/**
	 * 获取 开始日期.
	 *
	 * @return the 开始日期
	 */
	public String getBgnDate() {
		return bgnDate;
	}


	/**
	 * 设置 开始日期.
	 *
	 * @param bgnDate the new 开始日期
	 */
	public void setBgnDate(String bgnDate) {
		this.bgnDate = bgnDate;
	}


	/**
	 * 获取 结束日期.
	 *
	 * @return the 结束日期
	 */
	public String getEndDate() {
		return endDate;
	}


	/**
	 * 设置 结束日期.
	 *
	 * @param endDate the new 结束日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	/**
	 * 获取 用户.
	 *
	 * @return the 用户
	 */
	public String getOperUser() {
		return operUser;
	}


	/**
	 * 设置 用户.
	 *
	 * @param operUser the new 用户
	 */
	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}


	/**
	 * 获取 登录统计信息.
	 *
	 * @return the 登录统计信息
	 */
	public List<RptFieldBean> getLoginStatistic() {
		return loginStatistic;
	}

	/**
	 * 设置 登录统计信息.
	 *
	 * @param loginStatistic the new 登录统计信息
	 */
	public void setLoginStatistic(List<RptFieldBean> loginStatistic) {
		this.loginStatistic = loginStatistic;
	}

	/**
	 * 获取 操作信息.
	 *
	 * @return the 操作信息
	 */
	public List<RptFieldBean> getOperateStatistic() {
		return operateStatistic;
	}

	/**
	 * 设置 操作信息.
	 *
	 * @param operateStatistic the new 操作信息
	 */
	public void setOperateStatistic(List<RptFieldBean> operateStatistic) {
		this.operateStatistic = operateStatistic;
	}

}
