package com.jiuyv.hhc.model.loan.dao ;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.loan.MhtHisTranVo;

// Generated by AutoCode4J
/**
 * Interface: 商户历史交易记录 : TBL_MHT_HIS_TRAN
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public interface MhtHisTranDao extends BaseDao<MhtHisTranVo>{
	
	/** mapped key is : com.jiuyv.hhc.model.loan.dao.MhtHisTranDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.loan.dao.MhtHisTranDao.filterQuery";

	/**
	 * Find MhtHisTranVo by Primary Key. 
	 * 
	 * @param mchntCode : 内部商户号
	 * @param hisTransMonth : 统计月份	 
	 * @return MhtHisTranVo : 商户历史交易记录
	 */
	MhtHisTranVo findByKey(Long mchntCode , String hisTransMonth ) ;
	
	/**
	 * Update MhtHisTranVo by Primary Key.
	 * 
	 * @return effect rows.
	 * @author AutoCode4J
	 */
	int updateByKey(MhtHisTranVo vo);
	

	/**
	 * Insert MhtHisTranVo.
	 * 
	 * @return effect rows.
	 * @author AutoCode4J
	 */
	int insert(MhtHisTranVo vo) ;

	int deleteByMhtCode(MhtHisTranVo vo);


}