package com.moseeker.company.bean.email;

import com.moseeker.common.constants.ConstantErrorCodeMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zztaiwll on 18/4/25.
 */
public enum  TalentEmailEnum {
    NOBALANCE(-8, ConstantErrorCodeMessage.TALENTPOOL_EMAIL_BALANCE),
    NOUSEREMPLOYEE(-7, ConstantErrorCodeMessage.TALENTPOOL_EMAIL_NOUSEREMPLOYEE),
    NOCONFIGEMAIL(-6, ConstantErrorCodeMessage.TALENTPOOL_EMAIL_NOCONFIGEMAIL),
    NOUSERPROFILE(-5, ConstantErrorCodeMessage.TALENTPOOL_EMAIL_NOUSERPROFILE),
    COMPANY_NOT_MU(-4,ConstantErrorCodeMessage.COMPANY_NOT_MU),
    COMPANY_STATUS_NOT_AUTHORITY(-1,ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY),
    HR_NOT_IN_COMPANY(-2,ConstantErrorCodeMessage.HR_NOT_IN_COMPANY),
    COMPANY_CONF_TALENTPOOL_NOT(-3,ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);

    private int value;
    private String info;
    TalentEmailEnum(int value, String info) {
        this.value = value;
        this.info = info;
    }
    public static final Map<Integer,String > intToEnum = new HashMap();
    static {
        for (TalentEmailEnum op : values())
            intToEnum.put(op.getValue(),op.getInfo());
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
