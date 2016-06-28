package com.jiuyv.hhc.model.information.dao ;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.information.*;

// Generated by AutoCode4J
/**
 * Interface: 友情链接 : TBL_CM_FRD_LINK
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public interface CmFrdLinkDao extends BaseDao<CmFrdLinkVo> {
	
	/** mapped key is : com.jiuyv.hhc.model.information.dao.CmFrdLinkDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.information.dao.CmFrdLinkDao.filterQuery";

	/**
	 * Find CmFrdLinkVo by Primary Key. 
	 * 
	 * @param frdSeq : 内部序号	 
	 * @return CmFrdLinkVo : 友情链接
	 */
	CmFrdLinkVo findByKey(Long frdSeq ) ;
	
	/**
	 * Update  by Primary Key.
	 *
	 * @param vo the vo
	 * @return effect rows.
	 * @author AutoCode4J
	 */
	int updateVerByKey(CmFrdLinkVo vo);
	

	/**
	 * Insert .
	 *
	 * @param vo the vo
	 * @return effect rows.
	 * @author AutoCode4J
	 */
	int insertVer(CmFrdLinkVo vo) ;

	
	/**
	 * Count ver by key.
	 *
	 * @param catSeq the cat seq
	 * @return the int
	 */
	int countVerByKey(Long frdSeq);

	void deleteVer(CmFrdLinkVo dvo);
}