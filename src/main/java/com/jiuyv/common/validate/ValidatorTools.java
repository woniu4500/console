package com.jiuyv.common.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.common.database.exception.BaseException;

/**
 * The Class Validator Tools.
 *

 * @author 
 * @since 2014-1-17 9:39:54
 * @version 1.0.0
 */
public final class ValidatorTools {

	/** Constant Logger LOGGER: Logger :. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValidatorTools.class);

	/** The Constant REQUEST_PARA_ERROR_DESC. */
	public static final String REQUEST_PARA_ERROR_DESC = "请求参数错误";
	
	/** The Constant VALIDATOR_LOAD_ERROR. */
	public static final String VALIDATOR_LOAD_ERROR = "9001";
	
	/** The Constant VALIDATOR_LOAD_ERROR_DESC. */
	public static final String VALIDATOR_LOAD_ERROR_DESC = "validator加载失败";
	
	/**
	 * Hide Utility Class Constructor.
	 */
	private ValidatorTools() {
	}

	/** The Validator validator. */
	private static Validator validator = null;
	static {
		loadValidatorInstance();
	}

	/**
	 * Validator bean.
	 *
	 * @param validatorobj the validatorobj
	 * @param groups the groups
	 * @throws BaseException the base exception
	 */
	public static void validatorBean(Object validatorobj, Class<?>... groups)
			throws BaseException {
		Set<ConstraintViolation<Object>> constraintViolations = validator
				.validate(validatorobj, groups);
		if (!constraintViolations.isEmpty()) {
			String err = constraintViolations.iterator().next().getMessage();
			// 包含{}说明未读取到properties文件，则直接扔验证失败错误
			if (err.contains("{")) {
				throw new BaseException(VALIDATOR_LOAD_ERROR, VALIDATOR_LOAD_ERROR_DESC);
			}
			throw new BaseException(err, REQUEST_PARA_ERROR_DESC);
		}

	}

	/**
	 * Load validator instance.
	 */
	private synchronized static void loadValidatorInstance() {
		if (validator == null) {
			LOGGER.info("validator begin to load instance");
			validator = Validation.buildDefaultValidatorFactory().getValidator();
			LOGGER.info("validator load instance over");
		}
	}
}
