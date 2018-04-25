package com.moseeker.company.bean;

import com.moseeker.common.constants.DegreeConvertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zztaiwll on 18/4/25.
 */
public enum  TalentEmailEnum {
    NOBALANCE(-1, "邮件的额度不足"),
    NOUSEREMPLOYEE(-2, "没有选中收件的员工"),
    NOCONFIGEMAIL(-3, "该企业没有配置邮件");
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
