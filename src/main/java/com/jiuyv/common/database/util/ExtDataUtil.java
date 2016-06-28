package com.jiuyv.common.database.util;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.exception.BaseException;


/**
 * ExtData 对象工厂. 也存放了一些生成ErrorMap的静态方法。
 *
 * @author 
 * @since 2013-12-19 15:33:16
 * @version 1.0.0
 */
public final class ExtDataUtil {

	/**
	 * Instantiates a new ext data util.
	 */
	private ExtDataUtil() {}

	/**
	 * 建立包含单条数据的对象.
	 *
	 * @param <T> the generic type
	 * @param obj  单个对象
	 * @return the ext data
	 */
	public static <T> ExtData<T> genWithSingleData(T obj) {
		List<T> data = new ArrayList<T>();
		data.add(obj);
		return genWithData(data);
	}

	

	/**
	 * 建立包含系统异常的对象.
	 *
	 * @param <T> the generic type
	 * @param syserr the syserr
	 * @return the ext data
	 */
	public static <T> ExtData<T> genWithExceptions(String syserr) {
		return getExtData(0, null, 0, false, syserr);
	}

	/**
	 * 建立包含系统异常的对象.
	 *
	 * @param <T> the generic type
	 * @param t 系统异常
	 * @return the ext data
	 */
	public static <T> ExtData<T> genWithExceptions(Throwable t) {
		if ((t != null) && t instanceof BaseException) {
			return getExtData(0, null, 0, false, t.getMessage());
		}
		return getExtData(0, null, 0, false, "unknow.system.error");
	}

	/**
	 * 建立包含正常数据的对象.
	 *
	 * @param <T> the generic type
	 * @param root the root
	 * @return the ext data
	 */
	public static <T> ExtData<T> genWithData(List<T> root) {
		return getExtData(0, root, root.size(), true, null);
	}

	/**
	 * 建立ExtData对象.
	 *
	 * @param <T> the generic type
	 * @param status the status
	 * @param root the root
	 * @param totalproperty the totalproperty
	 * @param success the success
	 * @param syserr the syserr
	 * @return the ext data
	 */
	private static <T> ExtData<T> getExtData(int status, List<T> root, int totalproperty,
			boolean success, String syserr) {
		ExtData<T> data = new ExtData<T>();
		data.setRoot(root);
		data.setSuccess(success);
		data.setSyserr(syserr);
		data.setTotalProperty(totalproperty);
		return data;
	}

}
