package com.jiuyv.common.json;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

public class NoProperisObjectMapper  extends ObjectMapper{
	/**
	 * Instantiates a new no properis object mapper.
	 */
	public NoProperisObjectMapper(){
		this.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
