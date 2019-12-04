package com.moseeker.common.validation;

import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.ValidateNotAppointParamException;
import com.moseeker.common.exception.ValidateNotAppointValidateException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.rules.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 
 * @description 校验器
 * @author wjf
 * @date Jul 10, 2015
 * @company 大岂仟寻
 * @email wjf2255@gmail.com
 */
public class ValidateUtil {

	Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

	private final Map<String, List<ValidateRule>> rules = new HashMap<>();

	private Optional<Boolean> verified = Optional.empty();

	private StringBuffer sb = new StringBuffer();

	/**
	 * 往验证器中添加一个requiredValidateRule(必要验证器)
	 * 
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @return DasValidateRule
	 */
	public ValidateRule addRequiredValidate(String paramName,
			Object beanToBeValidated, String errorMessage, String message)
			throws CommonException {
		RequiredValidateRule rv = null;
		try {
			rv = new RequiredValidateRule(paramName, beanToBeValidated,
					errorMessage, message);
			if (!StringUtils.isNullOrEmpty(message)) {
				rv.setMessage(message);
			}
			addToRules(rv);
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		}
		return rv;
	}
	
	/**
	 * 往验证器中添加一个requiredValidateRule(必要验证器)
	 * 
	 * @param paramName
	 * @param beanToBeValidated
	 * @param paramName
	 * @param beanToBeValidated
	 * @return CommonException
	 */
	public ValidateRule addRequiredValidate(String paramName,
			Object beanToBeValidated)
			throws CommonException {
		RequiredValidateRule rv = null;
		try {
			rv = new RequiredValidateRule(paramName, beanToBeValidated);
			addToRules(rv);
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		}
		return rv;
	}

	/**
	 * 
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @return
	 */
	public ValidateRule addRequiredOneValidate(String paramName,
			List beanToBeValidated, String errorMessage, String message)
			throws ValidateNotAppointParamException {
		RequiredOneValidateRule rv = null;
		try {
			rv = new RequiredOneValidateRule(paramName, beanToBeValidated,
					message, errorMessage);
			if (!StringUtils.isNullOrEmpty(message)) {
				rv.setMessage(message);
			}
			addToRules(rv);
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		} finally {
			// do nothing
		}
		return rv;
	}

	public ValidateRule addRequiredOneValidate(String paramName,
											   List beanToBeValidated)
			throws ValidateNotAppointParamException {
		return addRequiredOneValidate(paramName, beanToBeValidated, null, null);
	}

	/**
	 * 添加数量限制校验逻辑
	 * @param paramName 属性名称
	 * @param beanToBeValidated 被校验对象
	 * @param errorMessage 错误信息
	 * @param message 结果信息
	 * @param upperLimit 限制条件
	 * @return 校验规则
	 * @throws ValidateNotAppointParamException
	 */
	public ValidateRule addUpperLimitValidate(String paramName,
											  List beanToBeValidated, String errorMessage, String message, Integer upperLimit)
			throws ValidateNotAppointParamException {
		OverUpperValidateRule rv = null;
		try {
			rv = new OverUpperValidateRule(paramName, beanToBeValidated,
					message, errorMessage, upperLimit);
			if (!StringUtils.isNullOrEmpty(message)) {
				rv.setMessage(message);
			}
			addToRules(rv);
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		} finally {
			// do nothing
		}
		return rv;
	}

	/**
	 * 添加数量限制校验逻辑
	 * @param paramName 属性名称
	 * @param beanToBeValidated 被校验对象
	 * @return 校验规则
	 * @throws ValidateNotAppointParamException
	 */
	public ValidateRule addUpperLimitValidate(String paramName,
											  List beanToBeValidated)
			throws ValidateNotAppointParamException {
		return addUpperLimitValidate(paramName, beanToBeValidated, null, null, null);
	}

	/**
	 * 往验证器添加一个RequiredStringValidateRule
	 * 
	 * @param paramName
	 *            参数名称
	 * @param beanToBeValidated
	 *            被校验的对象
	 * @param errorMessage
	 *            错误消息主题
	 * @param message
	 *            校验结果（如果不给定message的值，message = paramName + errorMessage）
	 * @return DasValidateRule 校验规则
	 */
	public ValidateRule addRequiredStringValidate(String paramName,
			Object beanToBeValidated, String errorMessage, String message)
			throws ValidateNotAppointParamException {
		RequiredStringValidateRule rsv = null;
		try {
			rsv = new RequiredStringValidateRule(paramName, beanToBeValidated);
			if (!StringUtils.isNullOrEmpty(errorMessage)) {
				rsv.setErrorMessage(errorMessage);
			}
			if (!StringUtils.isNullOrEmpty(message)) {
				rsv.setMessage(message);
			}
			addToRules(rsv);
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		}
		return rsv;
	}

	/**
	 * 往验证器添加一个RequiredStringValidateRule
	 *
	 * @param paramName
	 *            参数名称
	 * @param beanToBeValidated
	 *            被校验的对象
	 * @return DasValidateRule 校验规则
	 */
	public ValidateRule addRequiredStringValidate(String paramName,
												  Object beanToBeValidated)
			throws ValidateNotAppointParamException {
		return addRequiredStringValidate(paramName, beanToBeValidated, null, null);
	}

	/**
	 * 往验证器中添加一个StringLengthValidateRule
	 *
	 * @param param
	 * @param beanToBeValidated
	 * @param minRange
	 * @param maxRange
	 * @return DasValidateRule
	 */
	public ValidateRule addStringLengthValidate(String param,
												Object beanToBeValidated,
												Integer minRange, Integer maxRange)
			throws ValidateNotAppointParamException {
		return addStringLengthValidate(param, beanToBeValidated, null, null, minRange, maxRange);
	}

	/**
	 * 往验证器中添加一个StringLengthValidateRule
	 * 
	 * @param param
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @param minRange
	 * @param maxRange
	 * @return DasValidateRule
	 */
	public ValidateRule addStringLengthValidate(String param,
			Object beanToBeValidated, String errorMessage, String message,
			Integer minRange, Integer maxRange)
			throws ValidateNotAppointParamException {
		StringLengthValidateRule slvr = null;
		try {
			slvr = new StringLengthValidateRule(param, beanToBeValidated,
					minRange, maxRange);
			if (!StringUtils.isNullOrEmpty(errorMessage)) {
				slvr.setErrorMessage(errorMessage);
			}
			if (!StringUtils.isNullOrEmpty(message)) {
				slvr.setMessage(message);
			}

			addToRules(slvr);

		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		} finally {
			// do nothing
		}
		return slvr;
	}

    /**
     * 往验证器中添加一个StringLengthValidateRule
     *
     * @param param
     * @param beanToBeValidated
     * @param errorMessage
     * @param message
     * @param minRange
     * @param maxRange
     * @return DasValidateRule
     */
    public ValidateRule addStringSplitLengthValidate(String param,
                                                Object beanToBeValidated, String errorMessage, String message,
                                                Integer minRange, Integer maxRange, String splitString)
            throws ValidateNotAppointParamException {
        StringSplitLengthValidateRule slvr = null;
        try {
            slvr = new StringSplitLengthValidateRule(param, beanToBeValidated,
                    minRange, maxRange, splitString, errorMessage);
            if (!StringUtils.isNullOrEmpty(message)) {
                slvr.setMessage(message);
            }

            addToRules(slvr);

        } catch (ValidateNotAppointParamException e) {
            logger.error("faild!", e);
            throw e;
        } finally {
            // do nothing
        }
        return slvr;
    }

	/**
	 * 往验证器中添加一个IntTypeValidateRule
	 * 
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @param minRange
	 * @param maxRange
	 * @return DasValidateRule
	 */
	public ValidateRule addIntTypeValidate(String paramName,
			Object beanToBeValidated, String errorMessage, String message,
			Integer minRange, Integer maxRange)
			throws ValidateNotAppointParamException {
		IntTypeValidateRule itvr = null;
		try {
			itvr = new IntTypeValidateRule(paramName, beanToBeValidated,
					minRange, maxRange);

			if (!StringUtils.isNullOrEmpty(errorMessage)) {
				itvr.setErrorMessage(errorMessage);
			}
			if (!StringUtils.isNullOrEmpty(message)) {
				itvr.setMessage(message);
			}

			addToRules(itvr);
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		} finally {
			// do nothing
		}
		return itvr;
	}

	public ValidateRule addIntTypeValidate(String paramName,
										   Object beanToBeValidated,
										   Integer minRange, Integer maxRange)
			throws ValidateNotAppointParamException {
		return addIntTypeValidate(paramName, beanToBeValidated, null, null, minRange, maxRange);
	}

	/**
	 * 往验证器中添加一个IntTypeValidateRule
	 * 
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @param minRange
	 * @param maxRange
	 * @return DasValidateRule
	 */
	public ValidateRule addFloatTypeValidate(String paramName,
			Object beanToBeValidated, String errorMessage, String message,
			Double minRange, Double maxRange)
			throws ValidateNotAppointParamException {
		DoubleTypeValidateRule itvr = null;
		try {
			itvr = new DoubleTypeValidateRule(paramName, beanToBeValidated,
					minRange, maxRange);

			if (!StringUtils.isNullOrEmpty(errorMessage)) {
				itvr.setErrorMessage(errorMessage);
			}
			if (!StringUtils.isNullOrEmpty(message)) {
				itvr.setMessage(message);
			}

			addToRules(itvr);

		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		}
		return itvr;
	}

	/**
	 * 往验证器中添加一个IntTypeValidateRule
	 *
	 * @param paramName
	 * @param beanToBeValidated
	 * @param minRange
	 * @param maxRange
	 * @return DasValidateRule
	 */
	public ValidateRule addDoubleTypeValidate(String paramName,
											  Object beanToBeValidated, Double minRange, Double maxRange)
			throws ValidateNotAppointParamException {
		return addDoubleTypeValidate(paramName, beanToBeValidated, null, null, minRange, maxRange);
	}

	/**
	 * 往验证器中添加一个IntTypeValidateRule
	 *
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @param minRange
	 * @param maxRange
	 * @return DasValidateRule
	 */
	public ValidateRule addDoubleTypeValidate(String paramName,
											 Object beanToBeValidated, String errorMessage, String message,
											 Double minRange, Double maxRange)
			throws ValidateNotAppointParamException {
		DoubleTypeValidateRule itvr = null;
		try {
			itvr = new DoubleTypeValidateRule(paramName, beanToBeValidated,
					minRange, maxRange);

			if (!StringUtils.isNullOrEmpty(errorMessage)) {
				itvr.setErrorMessage(errorMessage);
			}
			if (!StringUtils.isNullOrEmpty(message)) {
				itvr.setMessage(message);
			}

			addToRules(itvr);

		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		}
		return itvr;
	}

	/**
	 * 往验证器中添加一个DateTypeValidateRule
	 * 
	 * @param paramName
	 * @param beanToBeValidated
	 * @param type
	 * @param errorMessage
	 * @param message
	 * @return DasValidateRule
	 */
	public ValidateRule addDateValidate(String paramName,
			Object beanToBeValidated, DateType type, String errorMessage,
			String message) throws ValidateNotAppointParamException {
		DateTypeValidateRule dtvr = null;
		try {
			dtvr = new DateTypeValidateRule(paramName, beanToBeValidated, type);
			if (!StringUtils.isNullOrEmpty(errorMessage)) {
				dtvr.setErrorMessage(errorMessage);
			}
			if (!StringUtils.isNullOrEmpty(message)) {
				dtvr.setMessage(message);
			}
			addToRules(dtvr);
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		} finally {
			// do nothing
		}
		return dtvr;
	}

	/**
	 * 往验证器中添加一个DateTypeValidateRule
	 *
	 * @param paramName
	 * @param beanToBeValidated
	 * @param type
	 * @return DasValidateRule
	 */
	public ValidateRule addDateValidate(String paramName,
										Object beanToBeValidated, DateType type) throws ValidateNotAppointParamException {

		return addDateValidate(paramName, beanToBeValidated, type, null, null);
	}

	/**
	 * 往验证器中添加RegExpressValidateRule
	 * 
	 * @param paramName
	 * @param beanToBeValidated
	 * @param regExpress
	 * @param errorMessage
	 * @param message
	 * @return DasValidateRule
	 */
	public ValidateRule addRegExpressValidate(String paramName,
			Object beanToBeValidated, String regExpress, String errorMessage,
			String message) throws ValidateNotAppointParamException {
		RegExpressValidateRule revr = null;
		try {
			revr = new RegExpressValidateRule(beanToBeValidated, paramName,
					regExpress);
			if (!StringUtils.isNullOrEmpty(errorMessage)) {
				revr.setErrorMessage(errorMessage);
			}
			if (!StringUtils.isNullOrEmpty(message)) {
				revr.setMessage(message);
			}
			addToRules(revr);
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		}
		return revr;
	}

	/**
	 * 往验证器中添加RegExpressValidateRule
	 * @param paramName 属性名称
	 * @param beanToBeValidated 被检验的字符串
	 * @param regExpress 正则表达式
	 * @return
	 * @throws ValidateNotAppointParamException
	 */
	public ValidateRule addRegExpressValidate(String paramName,
											  String beanToBeValidated, String regExpress) throws ValidateNotAppointParamException {
		return addRegExpressValidate(paramName, beanToBeValidated, regExpress, null, null);
	}

	/**
	 * 敏感词校验器
	 * 
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @return DasValidateRule
	 */
	public ValidateRule addSensitiveValidate(String paramName,
			String beanToBeValidated, String errorMessage, String message)
			throws ValidateNotAppointParamException {
		SensitiveWordsValidateRule swvr = null;
		try {
			swvr = new SensitiveWordsValidateRule(paramName, beanToBeValidated);
			if (!StringUtils.isNullOrEmpty(errorMessage)) {
				swvr.setErrorMessage(errorMessage);
			}
			if (!StringUtils.isNullOrEmpty(message)) {
				swvr.setMessage(message);
			}
			addToRules(swvr);
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			throw e;
		} finally {
			// do nothing
		}
		return swvr;
	}

	/**
	 * 将创建好的rule 添加到验证库中
	 * 
	 * @param rule
	 */
	private void addToRules(ValidateRule rule) {
		if (rules != null && rules.size() > 0) {
			boolean flag = false; // 表示paramName是否已经存在
			for (Map.Entry<String, List<ValidateRule>> entry : rules.entrySet()) {
				if (entry.getKey().equals(rule.getParamName())) {
					flag = true;
					entry.getValue().add(rule);
				}
			}
			if (!flag) {
				List<ValidateRule> validateRules = new ArrayList<ValidateRule>();
				validateRules.add(rule);
				rules.put(rule.getParamName(), validateRules);
			}
		} else {
			List<ValidateRule> validateRules = new ArrayList<ValidateRule>();
			validateRules.add(rule);
			rules.put(rule.getParamName(), validateRules);
		}
	}

	/**
	 * 添加一个验证器
	 * 
	 * @param type
	 * @param param
	 * @return 当前校验工具类
	 */
	public ValidateUtil createChain(ValidateType type, Map<String, Object> param) {
		switch (type) {
		case required:
			addRequiredValidate((String) param.get("paramName"),
					param.get("beanToBeValidated"),
					(String) param.get("errorMessage"),
					(String) param.get("message"));
			break;
		case requiredstring:
			addRequiredStringValidate((String) param.get("paramName"),
					param.get("beanToBeValidated"),
					(String) param.get("errorMessage"),
					(String) param.get("message"));
			break;
		case intType:
			addIntTypeValidate((String) param.get("paramName"),
					param.get("beanToBeValidated"),
					(String) param.get("errorMessage"),
					(String) param.get("message"),
					(Integer) param.get("minRange"),
					(Integer) param.get("maxRange"));
			break;
		case stringLength:
			addStringLengthValidate((String) param.get("paramName"),
					param.get("beanToBeValidated"),
					(String) param.get("errorMessage"),
					(String) param.get("message"),
					(Integer) param.get("minRange"),
					(Integer) param.get("maxRange"));
			break;
		case dateType:
			addDateValidate((String) param.get("paramName"),
					param.get("beanToBeValidated"),
					(DateType) param.get("dateType"),
					(String) param.get("errorMessage"),
					(String) param.get("message"));
			break;
		case regex:
			addRegExpressValidate((String) param.get("paramName"),
					param.get("beanToBeValidated"),
					(String) param.get("regExpress"),
					(String) param.get("errorMessage"),
					(String) param.get("message"));
			break;
		case sensitivewords:
			addSensitiveValidate((String) param.get("paramName"),
					(String) param.get("beanToBeValiated"),
					(String) param.get("errorMessage"),
					(String) param.get("message"));
			break;
		default:
		}
		return this;
	}

	/**
	 * 添加一组校验器
	 * 
	 * @param params
	 * @return 返回当前校验工具类
	 */
	public ValidateUtil createChains(
			List<Map<ValidateType, Map<String, Object>>> params) {
		if (params != null) {
			for (Map<ValidateType, Map<String, Object>> param : params) {

				if (param != null && param.size() > 0) {
					for (Map.Entry<ValidateType, Map<String, Object>> entry : param
							.entrySet()) {
						createChain(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		return this;
	}

	/**
	 * 检验是否符合指定的校验信息
	 * 
	 * @return 校验信息
	 */
	public String validate() {

		boolean flag = true;

		if (rules == null || rules.size() == 0) {
			ValidateNotAppointValidateException e = new ValidateNotAppointValidateException();
			logger.error("failed", e);
			throw e;
		}

		for (Map.Entry<String, List<ValidateRule>> entry : rules.entrySet()) {
			if (entry.getValue() != null && entry.getValue().size() > 0) {
				for (ValidateRule rule : entry.getValue()) {
					rule.validate();
					if(flag) {
						flag = rule.isLegal();
					}
					if (!StringUtils.isNullOrEmpty(rule.getMessage())) {
						sb.append(rule.getMessage() + ";");
					}
				}
			}
		}
		this.verified = Optional.of(flag);
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	/**
	 * 获取认证结果
	 * @return 认证结果
	 */
	public Optional<Boolean> getVerified() {
		return this.verified;
	}

	/**
	 * 获取校验信息
	 * @return 校验信息
	 */
	public String getResult() {
		return sb.toString();
	}

	public int countRules() {
		return rules.size();
	}
}
