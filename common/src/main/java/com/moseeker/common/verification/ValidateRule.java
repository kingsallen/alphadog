package com.moseeker.common.verification;

import com.moseeker.common.exception.ValidateNotAppointParamException;
import com.moseeker.common.exception.ValidateNotValidateException;

/**
 * 
 * @description 校验规则
 * @author wjf
 * @date Jul 8, 2015
 * @company 大岂千寻
 * @email wjf2255@gmail.com
 */
public abstract class ValidateRule {

	protected String paramName; // 参数名称
	protected String message = ""; // 验证消息
	protected Object beanToValidated; // 被校验的对象
	protected Boolean legal; // 校验是否通过
	protected String errorMessage; // 错误提示消息

	public ValidateRule() {
	}

	/**
	 * 
	 * @param paramName
	 *            被校验的参数的名称
	 * @param beanToValidated
	 *            被校验的参数
	 */
	public ValidateRule(String paramName, Object beanToValidated)
			throws ValidateNotAppointParamException {
		if (paramName == null) {
			throw new ValidateNotAppointParamException();
		}
		this.paramName = paramName;
		this.beanToValidated = beanToValidated;
	}

	/**
	 * 
	 * @param paramName
	 *            被校验的参数
	 * @param message
	 *            校验信息(默认是参数名称+错误消息)
	 * @param beanToValidated
	 *            被校验的参数
	 */
	public ValidateRule(String paramName, String message, Object beanToValidated)
			throws ValidateNotAppointParamException {
		if (paramName == null) {
			throw new ValidateNotAppointParamException();
		}
		this.paramName = paramName;
		this.message = message;
		this.beanToValidated = beanToValidated;
	}

	/**
	 * 
	 * @param paramName
	 *            被校验的参数的名称
	 * @param beanToValidated
	 *            被校验的参数
	 * @param errorMessage
	 *            错误消息
	 */
	public ValidateRule(String paramName, Object beanToValidated,
			String errorMessage) throws ValidateNotAppointParamException {
		if (paramName == null) {
			throw new ValidateNotAppointParamException();
		}
		this.paramName = paramName;
		this.errorMessage = errorMessage;
		this.beanToValidated = beanToValidated;
	}

	/**
	 * 校验规则
	 * 
	 * @return 校验信息
	 */
	public abstract String validate();

	/**
	 * 校验是否合法
	 * 
	 * @return true 合法，false 不合法
	 */
	public boolean isLegal() {
		if (legal == null) {
			throw new ValidateNotValidateException();
		}
		return legal;
	}

	/**
	 * 获取验证消息
	 * 
	 * @return 验证消息
	 */
	public String getMessage() {
		if (legal == null) {
			throw new ValidateNotValidateException();
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
