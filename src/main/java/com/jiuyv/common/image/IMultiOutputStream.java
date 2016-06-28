package com.jiuyv.common.image;

import java.io.OutputStream;

/**
 * A factory for creating MultiOutputStream objects.
 */
public interface IMultiOutputStream {

	/**
	 * Builds the output stream.
	 *
	 * @param params the params
	 * @return the output stream
	 */
	public OutputStream buildOutputStream(Integer ... params ) ;
	
}
