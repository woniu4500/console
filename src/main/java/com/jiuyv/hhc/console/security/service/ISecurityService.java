package com.jiuyv.hhc.console.security.service;

import java.util.Map;

import com.jiuyv.hhc.model.security.SysOprlogVo;
import com.jiuyv.hhc.model.security.SysResourceVo;


/**
 * 提供安全模块所需要的服务.
 *

 * @author 
 * @since 2014-1-2 16:36:25
 * @version 1.0.0
 */
public interface ISecurityService {

	/**
	 * 查询以URL为key的map，value存放ROLES（RES_ID）,用逗号分隔.
	 *
	 * @return the map
	 */
	Map<String, String> findFullResMapWithUrlKey();

	/**
	 * 查询以RES_ID为key的map.
	 *
	 * @return the map
	 */
	Map<String, SysResourceVo> findFullResMapWithResId();

	/**
	 * 获取操作流水ID.
	 *
	 * @return the long
	 */
	Long findOprNo();

	/**
	 * 记录操作流水.
	 *
	 * @param logVo the log vo
	 */
	void logRecord(SysOprlogVo logVo);

	/**
	 * Find sys time.
	 *
	 * @return the string
	 */
	String findSysTime();
}
