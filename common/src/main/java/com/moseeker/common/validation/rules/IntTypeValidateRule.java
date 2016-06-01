package com.moseeker.common.validation.rules;

import com.moseeker.common.exception.ValidateIllegalParamException;
import com.moseeker.common.exception.ValidateNotAppointParamException;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateRule;

/**
 * 
 * @description 整形校验器
 * @author wjf
 * @date Jul 9, 2015
 * @company 大岂千寻
 * @email wjf2255@gmail.com
 */
public class IntTypeValidateRule extends ValidateRule {

	private Integer minRange; // 下限，小于该值，提示错误
	private Integer maxRange; // 上线，大于或者等于该值，提示错误

	public IntTypeValidateRule(String paramName, Object beanToValidated,
			String errorMessage) throws ValidateNotAppointParamException {
		super(paramName, beanToValidated, errorMessage);
	}

	public IntTypeValidateRule(String paramName, Object beanToValidated)
			throws ValidateNotAppointParamException {
		super(paramName, beanToValidated);
		this.errorMessage = "必须是整数！";
	}

	public IntTypeValidateRule(String paramName, String message,
			Object beanToValidated) throws ValidateNotAppointParamException {
		super(paramName, message, beanToValidated);
		this.errorMessage = "必须是整数！";
	}

	public IntTypeValidateRule(String paramName, Object beanToValidated,
			Integer minRange, Integer maxRange)
			throws ValidateNotAppointParamException {
		super(paramName, beanToValidated);
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.errorMessage = "必须是整数！";
	}

	@Override
	public String validate() {
		if (minRange != null && maxRange != null && minRange > maxRange) {
			throw new ValidateIllegalParamException();
		}
		if (this.beanToValidated == null
				|| (this.beanToValidated instanceof String && ((String) this.beanToValidated)
						.trim().equals(""))) {
			this.legal = true;
			this.message = "";
			return this.message;
		}
		this.legal = false;
		if (this.beanToValidated != null)
			if (this.beanToValidated instanceof Integer
					|| (this.beanToValidated instanceof String && FormCheck
							.isInteger((String) this.beanToValidated))) {
				long temp = 0;
				if (this.beanToValidated instanceof Integer) {
					temp = ((Integer) this.beanToValidated).intValue();
				} else {
					temp = Long.valueOf((String) this.beanToValidated);
				}
				if (temp >= Integer.MIN_VALUE && temp <= Integer.MAX_VALUE) {
					if (minRange != null && temp < minRange) {
						this.errorMessage = " 必须大于或等于" + minRange;
					} else if (maxRange != null && temp >= maxRange) {
						this.errorMessage = " 必须小于" + maxRange;
					} else {
						this.legal = true;
					}

				} else {
					// do nothing
				}
			}
		if (!this.legal) {
			if (StringUtils.isNullOrEmpty(this.message)
					&& !StringUtils.isNullOrEmpty(this.errorMessage)) {
				this.message = this.paramName + " " + this.errorMessage;
			} else {
				// do nothing
			}
		} else {
			this.message = "";
		}
		return this.message;
	}

	public Integer getMinRange() {
		return minRange;
	}

	public void setMinRange(Integer minRange) {
		this.minRange = minRange;
	}

	public Integer getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(Integer maxRange) {
		this.maxRange = maxRange;
	}

}
