package com.jiuyv.hhc.model.common.dao ;

import java.util.List;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.common.SysParamVo;

// Generated by AutoCode4J
/**
 * Interface: 系统参数表 : TBL_SYS_PARAM
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public interface SysParamDao extends BaseDao<SysParamVo> {
	
	/** mapped key is : com.jiuyv.hhc.model.common.dao.SysParamDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.common.dao.SysParamDao.filterQuery";

	/**
	 * Find SysParamVo by Primary Key. 
	 * 
	 * @param paramCode : 参数名称	 
	 * @return SysParamVo : 系统参数表
	 */
	SysParamVo findByKey(String paramCode ) ;
	
	List<SysParamVo> loadTheme(String paramCode);
	
	/**
	 * Find by prefix.
	 *
	 * @param prefix the prefix
	 * @return the list
	 */
	List<SysParamVo> findByPrefix(String prefix);
}