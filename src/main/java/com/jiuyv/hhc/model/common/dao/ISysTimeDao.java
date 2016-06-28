package com.jiuyv.hhc.model.common.dao;

import com.jiuyv.hhc.model.common.SysTimeBean;

/**
 * The Interface ISysTimeDao.
 *

 * @author cowyk
 * @since 2014-1-15 15:12:43
 * @version 1.0.0
 */
public interface ISysTimeDao {
	
	/**
	 * Find database time.
	 *
	 * @return the sys time bean
	 */
	SysTimeBean findDatabaseTime();
}
