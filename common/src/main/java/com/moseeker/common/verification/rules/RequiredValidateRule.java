package com.moseeker.common.verification.rules;

import com.moseeker.common.exception.ValidateNotAppointParamException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.verification.ValidateRule;

/**
 * 
 * @description 必填项验证规则
 * @author wjf
 * @date Jul 8, 2015
 * @company 大岂千寻
 * @email wjf2255@gmail.com
 */
public class RequiredValidateRule extends ValidateRule {

	public RequiredValidateRule(String paramName, Object beanToValidated)
			throws ValidateNotAppointParamException {
		super(paramName, beanToValidated);
		this.errorMessage = "是必填项！";
	}

	public RequiredValidateRule(String paramName, Object beanToValidated,
			String errorMessage) throws ValidateNotAppointParamException {
		super(paramName, beanToValidated, errorMessage);
	}

	public RequiredValidateRule(String paramName, String message,
			Object beanToValidated) throws ValidateNotAppointParamException {
		super(paramName, message, beanToValidated);
		this.errorMessage = "是必填项！";
	}

	public RequiredValidateRule(String paramName, Object beanToValidated,
			String errorMessage, String message)
			throws ValidateNotAppointParamException {
		super(paramName, message, beanToValidated);
		if (StringUtils.isNullOrEmpty(errorMessage)) {
			this.errorMessage = "是必填项！";
		}
		if (!StringUtils.isNullOrEmpty(message)) {
			this.message = message;
		}
	}

	@Override
	public String validate() {
		if (this.beanToValidated == null) {
			this.legal = false;
			if (StringUtils.isNullOrEmpty(this.message)
					&& !StringUtils.isNullOrEmpty(this.errorMessage)) {
				this.message = this.paramName + " " + errorMessage;
			}
		} else {
			this.legal = true;
			this.message = "";
		}
		return this.message;
	}
}
