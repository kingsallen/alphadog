package com.moseeker.common.validation.rules;

import java.util.List;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateRule;

public class RequiredOneValidateRule extends ValidateRule {

	private List beanToValidated;

	public RequiredOneValidateRule(String paramName,
			List beanToValidated, String message, String errorMessage) {
		this.paramName = paramName;
		this.beanToValidated = beanToValidated;
		this.setMessage(message);
		this.setErrorMessage("至少有一项为必填项");
		if (!StringUtils.isNullOrEmpty(errorMessage)) {
			this.setErrorMessage(errorMessage);
		}
	}

	@Override
	public String validate() {
		this.legal = false;
		if (beanToValidated != null && beanToValidated.size() > 0) {
			for (Object obj : beanToValidated) {
				if (obj != null) {
					this.legal = true;
					break;
				}
			}
		}

		if (!legal) {
			if (StringUtils.isNullOrEmpty(this.message)
					&& !StringUtils.isNullOrEmpty(this.errorMessage)) {
				this.message = this.paramName + " " + errorMessage;
			}
		} else {
			this.message = "";
		}
		return this.message;
	}

	public List getBeanToValidated() {
		return beanToValidated;
	}

	public void setBeanToValidated(List beanToValidated) {
		this.beanToValidated = beanToValidated;
	}

}
