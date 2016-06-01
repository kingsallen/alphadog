package com.moseeker.common.validation.rules;

import com.moseeker.common.exception.ValidateNotAppointParamException;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.SensitiveWordDB;
import com.moseeker.common.validation.ValidateRule;

/**
 * 
 * @description 敏感词验证器
 * @author wjf
 * @date Jul 12, 2015
 * @company 大岂仟寻
 * @email wjf2255@gmail.com
 */
public class SensitiveWordsValidateRule extends ValidateRule {

	//Logger logger = Logger.getLogger(SensitiveWordsValidateRule.class);

	SensitiveWordDB db = SensitiveWordDB.getSingleton(); // 敏感词过滤

	public SensitiveWordsValidateRule(String paramName, Object beanToValidated,
			String errorMessage) throws ValidateNotAppointParamException {
		super(paramName, beanToValidated, errorMessage);
	}

	public SensitiveWordsValidateRule(String paramName, Object beanToValidated)
			throws ValidateNotAppointParamException {
		super(paramName, beanToValidated);
		this.errorMessage = Constant.DASVALIDATE_SENSITIVEWORDS_ILLEGAL;
	}

	public SensitiveWordsValidateRule(String paramName, String message,
			Object beanToValidated) throws ValidateNotAppointParamException {
		super(paramName, message, beanToValidated);
		this.errorMessage = Constant.DASVALIDATE_SENSITIVEWORDS_ILLEGAL;
	}

	@Override
	public String validate() {
		if (this.beanToValidated == null
				|| StringUtils.isNullOrEmpty((String) this.beanToValidated)) {
			this.legal = true;
			this.message = "";
			return this.message;
		}
		if (db.sensitiveExamin((String) this.beanToValidated)) {
			this.legal = false;
			if (StringUtils.isNullOrEmpty(this.message)
					&& !StringUtils.isNullOrEmpty(this.errorMessage)) {
				this.message = this.paramName + this.errorMessage;
			}
			//log
			/*logger.info("Include sensitive words: " + message + " occur at "
					+ new DateTime().toString("yyyy-MM-dd HH:mm:sss SSS")
					+ "sensitivewords:" + this.beanToValidated);*/
		} else {
			this.legal = true;
			this.message = "";
		}
		return this.message;
	}

}
