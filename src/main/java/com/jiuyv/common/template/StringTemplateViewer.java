package com.jiuyv.common.template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * The Class StringTemplateViewer.
 *

 * @author 
 * @since 2014-2-27 9:40:16
 * @version 1.0.0
 */
public final class StringTemplateViewer {
	
	/** Logger for this class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(StringTemplateViewer.class);

	/** 默认编码 UTF-8 */
	private static final String DEF_ENCODE = "UTF-8";
	
	/**
	 * Instantiates a new string template viewer.
	 */
	private StringTemplateViewer () { }
	
	/**
	 * Inits the template.
	 *
	 * @param tplId the tpl id
	 * @param tplContent the tpl content
	 * @return the template
	 */
	public static Template initTemplate(String tplId, String tplContent) {
		Configuration cfg = new Configuration();  
		StringTemplateLoader stl = new StringTemplateLoader();
		stl.putTemplate(tplId, tplContent);
		cfg.setTemplateLoader(stl);  
		cfg.setDefaultEncoding(DEF_ENCODE);  
		Template template;
		try {
			template = cfg.getTemplate(tplId);
			return template;
		} catch (IOException e) {
			LOGGER.error("Error in get template. ", e);
		}  
		return null;
	}
	
	/**
	 * Prints the with string template.
	 * 根据
	 * @param template the template
	 * @param root the root
	 * @param out the out
	 */
	public static void printWithStringTemplate (Template template, Map<String,Object> root, Writer out) {
		try {
			template.process(root, out);
		} catch (Exception e) {
			LOGGER.error("Error in print template. ", e);
		}  
	}
	
	/**
	 * Prints the with string template.
	 * 根据字符串模板生成文本
	 * @param tplId the tpl id
	 * @param tplContent the tpl content
	 * @param root the root
	 * @return the string
	 */
	public static String printWithStringTemplate (String tplId, String tplContent, Map<String,Object> root ) {
		StringWriter out = new StringWriter();
		printWithStringTemplate(initTemplate(tplId, tplContent), root, out);
		return out.toString();
	}
	
}
