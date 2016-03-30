package com.moseeker.common.verification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.moseeker.common.exception.ValidateNotAppointParamException;
import com.moseeker.common.exception.ValidateNotAppointValidateException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.verification.rules.DateType;
import com.moseeker.common.verification.rules.DateTypeValidateRule;
import com.moseeker.common.verification.rules.DoubleTypeValidateRule;
import com.moseeker.common.verification.rules.IntTypeValidateRule;
import com.moseeker.common.verification.rules.RegExpressValidateRule;
import com.moseeker.common.verification.rules.RequiredOneValidateRule;
import com.moseeker.common.verification.rules.RequiredStringValidateRule;
import com.moseeker.common.verification.rules.RequiredValidateRule;
import com.moseeker.common.verification.rules.SensitiveWordsValidateRule;
import com.moseeker.common.verification.rules.StringLengthValidateRule;

/**
 * 
 * @description 校验器
 * @author wjf
 * @date Jul 10, 2015
 * @company 大岂仟寻
 * @email wjf2255@gmail.com
 */
public class ValidateUtil {
	
	Logger logger = Logger.getLogger(ValidateUtil.class);
	
	private Map<String, List<ValidateRule>> rules = new HashMap<String, List<ValidateRule>>();
	
	/**
	 * 往验证器中添加一个requiredValidateRule(必要验证器)
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @return DasValidateRule
	 */
	public ValidateRule addRequiredValidate(String paramName, Object beanToBeValidated, 
			String errorMessage, String message)  {
		try {
			RequiredValidateRule rv = new RequiredValidateRule(paramName, beanToBeValidated, errorMessage, message);
			if(!StringUtils.isNullOrEmpty(message)) {
				rv.setMessage(message);
			}
			addToRules(rv);
			return rv;
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			return null;
		}
	}
	/**
	 * 
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @return
	 */
	public ValidateRule addRequiredOneValidate(String paramName, List<Object> beanToBeValidated, 
			String errorMessage, String message)  {
		try {
			RequiredOneValidateRule rv = new RequiredOneValidateRule(paramName, beanToBeValidated, message, errorMessage);
			if(!StringUtils.isNullOrEmpty(message)) {
				rv.setMessage(message);
			}
			addToRules(rv);
			return rv;
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			return null;
		}
	}
	/**
	 * 往验证器添加一个RequiredStringValidateRule
	 * @param paramName 参数名称
	 * @param beanToBeValidated 被校验的对象
	 * @param errorMessage 错误消息主题
	 * @param message 校验结果（如果不给定message的值，message = paramName + errorMessage）
	 * @return DasValidateRule 校验规则
	 */
	public ValidateRule addRequiredStringValidate(String paramName, Object beanToBeValidated, 
			String errorMessage, String message) {
		try {
			RequiredStringValidateRule rsv = new RequiredStringValidateRule(
					paramName, beanToBeValidated);
			if (!StringUtils.isNullOrEmpty(errorMessage)) {
				rsv.setErrorMessage(errorMessage);
			}
			if (!StringUtils.isNullOrEmpty(message)) {
				rsv.setMessage(message);
			}
			addToRules(rsv);
			return rsv;
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			return null;
		}
	}
	/**
	 * 往验证器中添加一个StringLengthValidateRule
	 * @param param
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @param minRange 
	 * @param maxRange
	 * @return DasValidateRule
	 */
	public ValidateRule addStringLengthValidate(String param, Object beanToBeValidated, 
			String errorMessage, String message, Integer minRange, Integer maxRange)  {
		try {
			StringLengthValidateRule slvr = new StringLengthValidateRule(param, beanToBeValidated, minRange, maxRange);
			if(!StringUtils.isNullOrEmpty(errorMessage)) {
				slvr.setErrorMessage(errorMessage);
			}
			if(!StringUtils.isNullOrEmpty(message)) {
				slvr.setMessage(message);
			}
			
			addToRules(slvr);
			
			return slvr;
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			return null;
		}
		
	}
	/**
	 * 往验证器中添加一个IntTypeValidateRule
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @param minRange
	 * @param maxRange
	 * @return DasValidateRule
	 */
	public ValidateRule addIntTypeValidate(String paramName, Object beanToBeValidated, 
			String errorMessage, String message, Integer minRange, Integer maxRange) {
		try {
			IntTypeValidateRule itvr = new IntTypeValidateRule(paramName, beanToBeValidated, minRange, maxRange);
			
			if(!StringUtils.isNullOrEmpty(errorMessage)) {
				itvr.setErrorMessage(errorMessage);
			}
			if(!StringUtils.isNullOrEmpty(message)) {
				itvr.setMessage(message);
			}
			
			addToRules(itvr);
			return itvr;
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			return null;
		}
	}
	/**
	 * 往验证器中添加一个IntTypeValidateRule
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @param minRange
	 * @param maxRange
	 * @return DasValidateRule
	 */
	public ValidateRule addFloatTypeValidate(String paramName, Object beanToBeValidated, 
			String errorMessage, String message, Double minRange, Double maxRange) {
		try {
			DoubleTypeValidateRule itvr = new DoubleTypeValidateRule(paramName, beanToBeValidated, minRange, maxRange);
			
			if(!StringUtils.isNullOrEmpty(errorMessage)) {
				itvr.setErrorMessage(errorMessage);
			}
			if(!StringUtils.isNullOrEmpty(message)) {
				itvr.setMessage(message);
			}
			
			addToRules(itvr);
			return itvr;
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			return null;
		}
	}
	/**
	 * 往验证器中添加一个DateTypeValidateRule
	 * @param paramName
	 * @param beanToBeValidated
	 * @param type
	 * @param errorMessage
	 * @param message
	 * @return DasValidateRule
	 */
	public ValidateRule addDateValidate(String paramName, Object beanToBeValidated, 
			DateType type, String errorMessage, String message) {
		try {
			DateTypeValidateRule dtvr = new DateTypeValidateRule(paramName, beanToBeValidated, type);
			if(!StringUtils.isNullOrEmpty(errorMessage)) {
				dtvr.setErrorMessage(errorMessage);
			}
			if(!StringUtils.isNullOrEmpty(message)) {
				dtvr.setMessage(message);
			}
			addToRules(dtvr);
			return dtvr;
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			return null;
		}
	}
	/**
	 * 往验证器中添加RegExpressValidateRule
	 * @param paramName
	 * @param beanToBeValidated
	 * @param regExpress
	 * @param errorMessage
	 * @param message
	 * @return DasValidateRule
	 */
	public ValidateRule addRegExpressValidate(String paramName, Object beanToBeValidated, 
			String regExpress, String errorMessage, String message) {
		try {
			RegExpressValidateRule revr = 
					new RegExpressValidateRule(beanToBeValidated, paramName, regExpress);
			if(!StringUtils.isNullOrEmpty(errorMessage)) {
				revr.setErrorMessage(errorMessage);
			}
			if(!StringUtils.isNullOrEmpty(message)) {
				revr.setMessage(message);
			}
			addToRules(revr);
			return revr;
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			return null;
		}
	}
	/**
	 * 敏感词校验器
	 * @param paramName
	 * @param beanToBeValidated
	 * @param errorMessage
	 * @param message
	 * @return DasValidateRule
	 */
	public ValidateRule addSensitiveValidate(String paramName, String beanToBeValidated, 
			String errorMessage, String message) {
		try {
			SensitiveWordsValidateRule swvr = new SensitiveWordsValidateRule(paramName, beanToBeValidated);
			if(!StringUtils.isNullOrEmpty(errorMessage)) {
				swvr.setErrorMessage(errorMessage);
			}
			if(!StringUtils.isNullOrEmpty(message)) {
				swvr.setMessage(message);
			}
			addToRules(swvr);
			return swvr;
		} catch (ValidateNotAppointParamException e) {
			logger.error("faild!", e);
			return null;
		}
	}
	/**
	 * 将创建好的rule 添加到验证库中
	 * @param rule
	 */
	private void addToRules(ValidateRule rule) {
		if(rules != null && rules.size() > 0) {
			boolean flag = false;   		//表示paramName是否已经存在
			for(Map.Entry<String, List<ValidateRule>> entry : rules.entrySet()) {
				if(entry.getKey().equals(rule.getParamName())) {
					flag = true;
					entry.getValue().add(rule);
				}
			}
			if(!flag) {
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
	 * @param type
	 * @param param
	 * @return 当前校验工具类
	 */
	public ValidateUtil createChain(ValidateType type, Map<String, Object> param) {
		switch(type) {
			case required: 
				addRequiredValidate((String)param.get("paramName"), param.get("beanToBeValidated"),
						(String)param.get("errorMessage"), (String)param.get("message"));
				break;
			case requiredstring:
				addRequiredStringValidate((String)param.get("paramName"), param.get("beanToBeValidated"),
						(String)param.get("errorMessage"), (String)param.get("message"));
				break;
			case intType: 
				addIntTypeValidate((String)param.get("paramName"), param.get("beanToBeValidated"),
						(String)param.get("errorMessage"), (String)param.get("message"), 
						(Integer)param.get("minRange"), (Integer)param.get("maxRange"));
				break;
			case stringLength:
				addStringLengthValidate((String)param.get("paramName"), param.get("beanToBeValidated"),
						(String)param.get("errorMessage"), (String)param.get("message"), 
						(Integer)param.get("minRange"), (Integer)param.get("maxRange"));
				break;
			case dateType: 
				addDateValidate((String)param.get("paramName"), param.get("beanToBeValidated"), 
						(DateType)param.get("dateType"),(String)param.get("errorMessage"), (String)param.get("message"));
				break;
			case regex : 
				addRegExpressValidate((String)param.get("paramName"), param.get("beanToBeValidated"), 
						(String)param.get("regExpress"), (String)param.get("errorMessage"), 
						(String)param.get("message"));
				break;
			case sensitivewords :
				addSensitiveValidate((String)param.get("paramName"), (String)param.get("beanToBeValiated"), 
						(String)param.get("errorMessage"), (String)param.get("message"));
				break;
			default :
		}
		return this;
	}
	/**
	 * 添加一组校验器
	 * @param params
	 * @return 返回当前校验工具类
	 */
	public ValidateUtil createChains(List<Map<ValidateType, Map<String, Object>>> params) {
		if(params != null) {
			for(Map<ValidateType, Map<String, Object>> param : params) {
				
				if(param != null && param.size() > 0) {
					for(Map.Entry<ValidateType, Map<String, Object>> entry : param.entrySet()) {
						createChain(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		return this;
	}
	/**
	 * 检验是否符合指定的校验信息
	 * @return 校验信息
	 */
	public String validate() {
		
		StringBuffer sb = new StringBuffer("");
		
		if(rules == null || rules.size() == 0) {
			throw new ValidateNotAppointValidateException();
		}
		
		for(Map.Entry<String, List<ValidateRule>> entry : rules.entrySet()) {
			if(entry.getValue() != null && entry.getValue().size() > 0) {
				for(ValidateRule rule : entry.getValue()) {
					rule.validate();
					if(!StringUtils.isNullOrEmpty(rule.getMessage())) {
						sb.append(rule.getMessage()+";");
					}
				}
			}
		}
		if(sb.length() > 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		
		return sb.toString();
	}
}
