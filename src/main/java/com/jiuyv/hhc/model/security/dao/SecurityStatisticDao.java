package com.jiuyv.hhc.model.security.dao;

import java.util.List;
import java.util.Map;

import com.jiuyv.hhc.model.security.rpt.LoginStatisticBean;
import com.jiuyv.hhc.model.security.rpt.RptFieldBean;

public interface SecurityStatisticDao {

	List<LoginStatisticBean> loginDayStatistic();

	String findOperUserName(String operUser);

	List<RptFieldBean> findLoginStatistic(Map<String, String> params);

	List<RptFieldBean> findOperateStatistic(Map<String, String> params);
}
