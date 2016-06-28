package com.jiuyv.hhc.console.common.service;

import java.util.List;
import java.util.Map;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.common.SysParamVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface ISysParamService.
 * 系统参数设置
 * 

 * @author 
 * @since 2014-2-20 16:59:04
 * @version 1.0.0
 */
public interface ISysParamService {

	/**
	 * Find sys param list.
	 * 查询参数信息
	 * @param filters the filters
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<SysParamVo> findSysParamList(List<Filter> filters, SysUserVo userInfo) throws BaseException;
	/**
	 * 获取生成二维码字符串
	 * */
	ExtData<SysParamVo> selectQrcodeText(SysParamVo paramVo)throws BaseException;
	
	/**
	 * Do update sys param.
	 * 批量更新系统参数
	 * @param filters the filters
	 * @param userInfo the user info
	 * @param paramList the param list
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<SysParamVo>  doUpdateSysParam(List<Filter> filters, SysUserVo userInfo,
			List<SysParamVo> paramList) throws BaseException;
	/**
	 * 加载主题
	 * */
	ExtData<SysParamVo> loadTheme()throws BaseException;
	/**
	 * 更新主题
	 * */
	ExtData<SysParamVo> updateTheme(SysParamVo paramVo,SysUserVo userInfo)throws BaseException;
	
	/**
	 * 根据paramCode查询参数
	 * @param string
	 * @return
	 */
	String findParam(String string);
	
	/**
	 * 根据前缀查询参数信息
	 * @param prefix
	 * @return
	 */
	Map<String,String> findParamMap(String prefix);

}
