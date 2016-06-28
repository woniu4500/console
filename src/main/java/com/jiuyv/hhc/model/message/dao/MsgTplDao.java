package com.jiuyv.hhc.model.message.dao ;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.message.MsgTplVo;

// Generated by AutoCode4J
/**
 * Interface: 信息模板表 : TBL_MSG_TPL
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public interface MsgTplDao extends BaseDao<MsgTplVo> {
	
	/** mapped key is : com.jiuyv.hhc.model.message.dao.MsgTplDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.message.dao.MsgTplDao.filterQuery";
	String MAPPED_FIND_NC = "com.jiuyv.hhc.model.message.dao.MsgTplDao.filterNoContentQuery";

	/**
	 * Find MsgTplVo by Primary Key. 
	 * 
	 * @param tplId : 模版ID	 
	 * @return MsgTplVo : 信息模板表
	 */
	MsgTplVo findByKey(Long tplId ) ;
}
