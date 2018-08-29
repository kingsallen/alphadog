package com.moseeker.common.validation.rules;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.validation.ValidateRule;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/8/6
 */
public class OverUpperValidateRule extends ValidateRule {

    private List beanToValidated;
    private int upperLimit = Constant.UPPER_LIMIT;

    public OverUpperValidateRule(String paramName,
                                   List beanToValidated, String message, String errorMessage, Integer upperLimit) {
        this.paramName = paramName;
        this.beanToValidated = beanToValidated;
        this.setMessage(message);
        if (!StringUtils.isNotBlank(errorMessage)) {
            this.setErrorMessage(errorMessage);
        } else {
            this.setErrorMessage("数量过多,超过上限");
        }
        if (upperLimit != null && upperLimit > 0) {
            this.upperLimit = upperLimit;
        }
        this.legal = false;
    }

    public OverUpperValidateRule(String paramName,
                                 List beanToValidated) {
        this(paramName, beanToValidated, null, null, null);
    }

    public List getBeanToValidated() {
        return beanToValidated;
    }

    public void setBeanToValidated(List beanToValidated) {
        this.beanToValidated = beanToValidated;
    }

    @Override
    public String validate() {
        this.legal = false;
        if (beanToValidated == null || beanToValidated.size() < upperLimit) {
            this.legal = true;
        }

        if (!legal) {
            if (StringUtils.isBlank(this.message)
                    && !StringUtils.isBlank(this.errorMessage)) {
                this.message = this.paramName + " " + errorMessage;
            }
        } else {
            this.message = "";
        }
        return this.message;
    }
}
