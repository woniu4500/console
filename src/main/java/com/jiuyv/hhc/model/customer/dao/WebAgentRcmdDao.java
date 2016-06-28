package com.jiuyv.hhc.model.customer.dao ;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.customer.*;

// Generated by AutoCode4J
/**
 * Interface: 代理商推荐号信息表 : TBL_WEB_AGENT_RCMD
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public interface WebAgentRcmdDao extends BaseDao<WebAgentRcmdVo> {
	
	/** mapped key is : com.jiuyv.hhc.model.customer.dao.WebAgentRcmdDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.customer.dao.WebAgentRcmdDao.filterQuery";

	/**
	 * Find WebAgentRcmdVo by Primary Key. 
	 * 
	 * @param rcmdCode : 推荐号	 
	 * @return WebAgentRcmdVo : 代理商推荐号信息表
	 */
	WebAgentRcmdVo findByKey(String rcmdCode ) ;

	/**
	 * Delete.
	 * 删除推荐号
	 * @param rcmdCode the rcmd code
	 * @return the int
	 */
	int deleteByKey(String rcmdCode);

	/**
	 * Count refered.
	 * 推荐号引用次数
	 * @param rcmdCode the rcmd code
	 * @return the long
	 */
	Long countRefered(String rcmdCode);
}