package com.jiuyv.hhc.console.common.service.impl;

import static com.jiuyv.common.validate.BizCheck.isTrue;
import static com.jiuyv.common.validate.BizCheck.notBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.common.SysParamVo;
import com.jiuyv.hhc.model.common.dao.SysParamDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class SysParamServiceImpl.
 * 系统参数Service

 * @author 
 * @since 2014-2-21 10:23:16
 * @version 1.0.0
 */
public class SysParamServiceImpl extends AssistService implements ISysParamService{

	/** 系统参数Dao */
	private SysParamDao sysParamDao ; 
	/** paramCode*/
	private static final String FLD_PARAM_CODE = "paramCode";
	/** paramModifyFlag */
	private static final String FLD_PARAM_MODIFY_FLAG = "paramModifyFlag";
	/**theme*/
	private static final String THEME_PARAM_CODE = "sys.theme.%";
	
	/**
	 * Find sys param list.
	 * 查询参数信息
	 * @param filters the filters
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<SysParamVo> findSysParamList(List<Filter> filters, SysUserVo userInfo) throws BaseException {
		List<SysParamVo> reslist = getQueryAssist().list(SysParamDao.MAPPED_FIND, filters, new Page(FLD_PARAM_CODE));
		return ExtDataUtil.genWithData(reslist);
	}
	/**
	 * 获取生成二维码字符串
	 * */
	public ExtData<SysParamVo> selectQrcodeText(SysParamVo paramVo)throws BaseException{
		notBlank(paramVo.getParamCode(),ErrorCode.CODE_9901,ErrorCode.CODE_9901_MSG);
		SysParamVo paramObj = sysParamDao.findByKey(paramVo);
		return ExtDataUtil.genWithSingleData(paramObj);
	}
	
	/**
	 * Do update sys param.
	 * 批量更新系统参数
	 * @param filters the filters
	 * @param userInfo the user info
	 * @param paramList the param list
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<SysParamVo> doUpdateSysParam(List<Filter> filters, SysUserVo userInfo,
			List<SysParamVo> paramList) throws BaseException {
		filters.add(new Filter(FLD_PARAM_MODIFY_FLAG, Filter.STRING, "1", Filter.Comparison.EQ));
		Map<String,SysParamVo> paramMap = getQueryAssist().map(SysParamDao.MAPPED_FIND, filters, null, null, FLD_PARAM_CODE);
		List<SysParamVo> toUpdList = new ArrayList<SysParamVo>();
		for ( SysParamVo param : paramList ) {
			String paramCode = param.getParamCode();
			SysParamVo dbVo = paramMap.get(paramCode);
			if ( dbVo == null ) {
				continue;
			}
			notBlank(param.getParamValue(), ErrorCode.CODE_4020, String.format(ErrorCode.CODE_4020_MSG_P, paramCode));
			if ( StringUtils.equals(param.getParamValue(), dbVo.getParamValue())) {
				continue;
			}
			// regex check
			if ( StringUtils.equals(dbVo.getParamCheck(), "2") && StringUtils.isNotBlank(dbVo.getParamRule())) { 
				isTrue(Pattern.matches(dbVo.getParamRule(), param.getParamValue()), ErrorCode.CODE_4021, String.format(ErrorCode.CODE_4021_MSG_P, paramCode));
			}
			dbVo.setParamValue( param.getParamValue() ) ;
			dbVo.setVersion( param.getVersion() ) ;
			dbVo.setLastUpdateAcc(userInfo.getLoginId());
			dbVo.setLastUpdateOrg(userInfo.getOrgCode());
			toUpdList.add(dbVo);
		}
		int updrow = getQueryAssist().updateList(sysParamDao, toUpdList);
		isTrue(updrow==toUpdList.size(), ErrorCode.CODE_9902,  ErrorCode.CODE_9902_MSG_2);
		return ExtDataUtil.genWithData(toUpdList);
	}
	/**
	 * 加载主题
	 * */
	public ExtData<SysParamVo> loadTheme()
			throws BaseException{
		List<SysParamVo> paramList =sysParamDao.loadTheme(THEME_PARAM_CODE);
		return ExtDataUtil.genWithData(paramList);
	}
	/**
	 * 更新主题
	 * */
	public ExtData<SysParamVo> updateTheme(SysParamVo paramVo,SysUserVo userInfo)
			throws BaseException{
		notBlank(paramVo.getParamCode(),ErrorCode.CODE_9901,ErrorCode.CODE_9901_MSG);
		SysParamVo paramObj = sysParamDao.findByKey(paramVo);
		paramObj.setParamValue(paramVo.getParamValue());
		paramObj.setLastUpdateAcc(userInfo.getLoginId());
		paramObj.setLastUpdateOrg(userInfo.getOrgCode());
		getQueryAssist().updateFetch(sysParamDao, paramObj);
		return ExtDataUtil.genWithSingleData(paramObj);
	}

	/**
	 * 根据paramCode查询参数
	 * @param string
	 * @return
	 */
	public String findParam(String paramCode) {
		SysParamVo paramObj = sysParamDao.findByKey(paramCode);
		return paramObj.getParamValue();
	}
	
	/**
	 * 根据前缀查询参数
	 * @param prefix
	 * @return
	 */
	public Map<String,String> findParamMap(String prefix){
		List<SysParamVo> plist = sysParamDao.findByPrefix("sys.mes");
		Map<String,String> paramMap = new HashMap<String, String>();
		for (SysParamVo sysParamVo : plist) {
			paramMap.put(sysParamVo.getParamCode(), sysParamVo.getParamValue());
		}
		return paramMap;
	}

	
	/**
	 * 设置 系统参数Dao.
	 *
	 * @param sysParamDao the new 系统参数Dao
	 */
	public void setSysParamDao(SysParamDao sysParamDao) {
		this.sysParamDao = sysParamDao;
	}
}
