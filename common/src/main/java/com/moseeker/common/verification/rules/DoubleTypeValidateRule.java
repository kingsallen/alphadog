package com.moseeker.common.verification.rules;

import com.moseeker.common.exception.ValidateIllegalParamException;
import com.moseeker.common.exception.ValidateNotAppointParamException;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.verification.ValidateRule;
/**
 * 
 * 判断是否是只保留小数点后面2位数的浮点数值 
 * date Jul 17, 2015
 * company 大岂仟寻
 * author wjf
 * email wjf2255@gmail.com
 */
public class DoubleTypeValidateRule extends ValidateRule {
	
	private Double minRange;		//最小值
	private Double maxRange;		//最大值

	public DoubleTypeValidateRule(String paramName, Object beanToValidated,
			String errorMessage) throws ValidateNotAppointParamException {
		super(paramName, beanToValidated, errorMessage);
	}

	public DoubleTypeValidateRule(String paramName, Object beanToValidated) 
			throws ValidateNotAppointParamException {
		super(paramName, beanToValidated);
		this.errorMessage = "必须是数值！";
	}

	public DoubleTypeValidateRule(String paramName, String message,
			Object beanToValidated) throws ValidateNotAppointParamException {
		super(paramName, message, beanToValidated);
		this.errorMessage = "必须是数值！";
	}

	public DoubleTypeValidateRule(String paramName, Object beanToValidated,
			Double minRange, Double maxRange) throws ValidateNotAppointParamException {
		super(paramName, beanToValidated);
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.errorMessage = "必须是数值！";
	}

	@Override
	public String validate() {
		if(minRange != null && maxRange != null && minRange > maxRange) {
			throw new ValidateIllegalParamException();
		}
		if(this.beanToValidated == null || (this.beanToValidated instanceof String 
				&& ((String)this.beanToValidated).trim().equals(""))) {
			this.legal = true;
			this.message = "";
			return this.message;
		}
		this.legal = false;
		if(this.beanToValidated != null) 
		if(this.beanToValidated instanceof Double || this.beanToValidated instanceof Integer 
				|| (this.beanToValidated instanceof String 
				&& FormCheck.isNumber((String)this.beanToValidated))) {
			double temp = 0;
			if(this.beanToValidated instanceof Integer) {
				temp = Double.valueOf((Integer)this.beanToValidated);
			} else if(this.beanToValidated instanceof String) {
				temp = Double.valueOf((String)this.beanToValidated);
			} else {
				temp = (Double)this.beanToValidated;
			}
			if(temp >= Double.MIN_VALUE && temp <= Double.MAX_VALUE) {
				if(minRange != null && temp < minRange) {
					this.errorMessage = " 必须大于或等于"+minRange;
				} else if(maxRange != null && temp >= maxRange) {
					this.errorMessage = " 必须小于"+maxRange;
				} else {
					this.legal = true;
				}
			}
		}
		if(!this.legal) {
			if(StringUtils.isNullOrEmpty(this.message) && !StringUtils.isNullOrEmpty(this.errorMessage)) {
				this.message = this.paramName + " " + this.errorMessage;
			} else {
				//do nothing
			}
		} else {
			this.message = "";
		}
		return this.message;
	}

	public Double getMinRange() {
		return minRange;
	}

	public void setMinRange(Double minRange) {
		this.minRange = minRange;
	}

	public Double getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(Double maxRange) {
		this.maxRange = maxRange;
	}
}
