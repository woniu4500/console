package com.jiuyv.common.database.assist.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Special Bean Utils For Query Assist.
 *
 * @author 
 * @since 2013-12-19 15:44:37
 * @version 1.0.0
 */
@SuppressWarnings({"rawtypes","unchecked"})
public final class SpecBeanUtils {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SpecBeanUtils.class);
	
	private SpecBeanUtils () {
	}
	
	/**
	 * Make a copy of input object.
	 *
	 * @param <T> the generic type
	 * @param src the src
	 * @return the t
	 */
	public static <T> T duplicate(T src) {
		if ( src == null ) {
			return null ; 
		}
		Class clz = src.getClass();
		if ( clz.isPrimitive() ) {
			return src ;
		}
		// skip deep copy of Configuration
		if ( src instanceof Configuration ) {
			return src ; 
		}
		if ( src instanceof String ) {
			return src ; 
		}
		if ( src instanceof Boolean ) {
			return (T) Boolean.valueOf(String.valueOf(src));
		}
		if ( src instanceof Short ) {
			return (T) Short.valueOf(String.valueOf(src));
		}
		if ( src instanceof Integer ) {
			return (T) Integer.valueOf(String.valueOf(src));
		}
		if ( src instanceof Long ) {
			return (T) Long.valueOf(String.valueOf(src));
		}
		if ( src instanceof Float ) {
			return (T) Float.valueOf(String.valueOf(src));
		}
		if ( src instanceof Double ) {
			return (T) Double.valueOf(String.valueOf(src));
		}
		if ( src instanceof Byte ) {
			return (T) Byte.valueOf(String.valueOf(src));
		}
		if ( src instanceof Character ) {
			return (T) src;
		}
		if (clz.isArray()){
			Object[] a = (Object[]) src ;
			Object[] t = new Object[a.length];
			for ( int i = 0 ; i < a.length ; i++ ) {
				t[i] = duplicate(a[i]);
			}
			return (T) t ; 
		}
		// 
		T tar = null;
		try {
			tar = (T) newInstance(clz);
		} catch (Exception e) {
			LOG.error("Error in build new instance " + clz.getName() + ". ", e);
		}
		if ( tar instanceof Collection ) {
			Collection srcCol = (Collection) src ; 
			Collection tarCol = (Collection) tar ;
			for(Object o : srcCol) {
				tarCol.add(duplicate(o));
			}
			return tar ; 
		}
		// copy properties
		for ( Field f : clz.getDeclaredFields() ) {
			fieldCopy(src, tar, f);
		}
		return tar; 
	}
	
	/**
	 * Field copy.
	 *
	 * @param src the src
	 * @param tar the tar
	 * @param f the f
	 */
	private static void fieldCopy(Object src, Object tar, Field f) {
		try {
			int m = f.getModifiers();
			if ( Modifier.isFinal(m) || Modifier.isStatic(m) ){
				return ;
			}
			f.setAccessible(true);
			Object val = duplicate(f.get(src));
			if ( val != null && tar != null ) {
				f.set(tar, val);
			}
		} catch (Exception e){
			LOG.error("Error copy field["+ f.getName()  + "] ", e);
		}
	}
	
	/**
	 * Build new instance of giving class.
	 *
	 * @param <T> the generic type
	 * @param clz the clz
	 * @return the t
	 * @throws Exception the exception
	 */
	private static <T> T newInstance(Class<T> clz) throws Exception{
		if ( clz == null ) {
			return null;
		}
		Constructor[] constructors = clz.getConstructors();
		int min = 99 ; 
		Constructor c = null ; 
		for (Constructor constructor : constructors) {
			int l = constructor.getParameterTypes().length;
			if ( l < min ) {
				min = l ; 
				c = constructor;
			}
		}
		// 
		if ( c == null ) {
			return null ;
		}
		Object[] initparams = new Object[min];
		return (T) c.newInstance(initparams);
	}
	
	/**
	 * Get field without setter,getter method.
	 *
	 * @param <T> the generic type
	 * @param obj the obj
	 * @param field the field
	 * @return property value.
	 */
	public static <T> T getProperty(Object obj, String field){
		if ( obj == null || field == null ) {
			return null ; 
		}
		for(Class c = obj.getClass();c!=Object.class;c=c.getSuperclass()) {
			try {
				Field f = c.getDeclaredField(field);
				f.setAccessible(true);
				return (T) f.get(obj);
			} catch (Exception e) {
				// Do nothing
			} 
		}
		LOG.error("Error getting property value: " + field + " of " + obj.getClass().getName());
		return null ; 
	}
	
	/**
	 * Gets the property type.
	 *
	 * @param c the c
	 * @param fn the fn
	 * @return the property type
	 */
	public static Class getPropertyType(Class c , String fn) {
		if ( c == null || fn == null ) {
			return null ; 
		}
		String cname = c.getName() ; 
		for (Class clazz=c; clazz != Object.class; clazz = clazz.getSuperclass()){
			try {
				Field fld = clazz.getDeclaredField(fn);
				fld.setAccessible(true);
				return fld.getType();
			} catch (Exception e) {
				// Do nothing. 
			} 
		}
		LOG.error("Error getting property type: " + fn + " of " + cname);
		return null ; 
	}
}
