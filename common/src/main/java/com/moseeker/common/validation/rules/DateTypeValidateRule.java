package com.moseeker.common.validation.rules;

import com.moseeker.common.exception.ValidateNotAppointParamException;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateRule;
/**
 * 
 * @description 日期类型判断
 * @author wjf
 * @date Jul 10, 2015
 * @company 大岂千寻
 * @email wjf2255@gmail.com
 */
public class DateTypeValidateRule extends ValidateRule {
	
	private DateType type; 		//日期类型，短类型是yyyy-MM-dd 长类型是yyyy-MM-dd HH:mm:ss

	public DateTypeValidateRule(String paramName, Object beanToValidated,
			String errorMessage) throws ValidateNotAppointParamException {
		super(paramName, beanToValidated, errorMessage);
	}

	public DateTypeValidateRule(String paramName, Object beanToValidated) 
			throws ValidateNotAppointParamException {
		super(paramName, beanToValidated);
		this.errorMessage = "日期格式不合法！";
	}

	public DateTypeValidateRule(String paramName, String message,
			Object beanToValidated) throws ValidateNotAppointParamException {
		super(paramName, message, beanToValidated);
	}

	public DateTypeValidateRule(String paramName, Object beanToValidated,
			DateType type) throws ValidateNotAppointParamException {
		super(paramName, beanToValidated);
		this.type = type;
	}

	@Override
	public String validate() {
		this.legal = false;
		if(this.beanToValidated == null || (this.beanToValidated instanceof String 
				&& ((String)this.beanToValidated).trim().equals(""))) {
			this.legal = true;
			this.message = "";
			return this.message;
		}
		if(this.beanToValidated instanceof String) {
			switch(type) {
				case shortDate:
					if(FormCheck.isDate((String)this.beanToValidated)) {
						this.legal = true;
					} else {
						this.errorMessage = "日期格式不合法,正确地日期格式是'yyyy-MM-dd'";
						if(StringUtils.isNullOrEmpty(message)) {
							this.message = this.paramName + this.errorMessage;
						}
					}
					break;
				case longDate : 
					if(FormCheck.isDateTime((String)this.beanToValidated)) {
						this.legal = true;
					} else {
						this.errorMessage = "日期格式不合法,正确地日期格式是'yyyy-MM-dd HH:mm:ss'";
						if(StringUtils.isNullOrEmpty(message)) {
							this.message = this.paramName + this.errorMessage;
						}
					}
					break;
				default:
			}
		} else {
			this.errorMessage = "必须是字符类型数据";
		}
		if(!this.legal) {
			if(StringUtils.isNullOrEmpty(this.message) && !StringUtils.isNullOrEmpty(this.errorMessage)) {
				this.message = this.paramName + this.errorMessage;
			}
		} else {
			this.message = "";
		}
		return this.message;
	}

	public DateType getType() {
		return type;
	}

	public void setType(DateType type) {
		this.type = type;
	}
}
