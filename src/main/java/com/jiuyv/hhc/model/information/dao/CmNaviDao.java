package com.jiuyv.hhc.model.information.dao ;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.information.*;

// Generated by AutoCode4J
/**
 * Interface: 网站导航 : TBL_CM_NAVI
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public interface CmNaviDao extends BaseDao<CmNaviVo> {
	
	/** mapped key is : com.jiuyv.hhc.model.information.dao.CmNaviDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.information.dao.CmNaviDao.filterQuery";

	/**
	 * Find CmNaviVo by Primary Key. 
	 * 
	 * @param naviSeq : 内部序号	 
	 * @return CmNaviVo : 网站导航
	 */
	CmNaviVo findByKey(Long naviSeq ) ;
	
	/**
	 * Update  by Primary Key.
	 *
	 * @param vo the vo
	 * @return effect rows.
	 * @author AutoCode4J
	 */
	int updateVerByKey(CmNaviVo vo);
	

	/**
	 * Insert .
	 *
	 * @param vo the vo
	 * @return effect rows.
	 * @author AutoCode4J
	 */
	int insertVer(CmNaviVo vo) ;

	
	/**
	 * Count ver by key.
	 *
	 * @param catSeq the cat seq
	 * @return the int
	 */
	int countVerByKey(Long naviSeq);
}
