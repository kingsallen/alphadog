package com.moseeker.position.constants;

import com.moseeker.common.util.StringUtils;

public enum CheckStrategy {
    REQUIRED("%s是必填字段") {
        public boolean check(String checkValue,String value) {
            if(StringUtils.isNullOrEmpty(value)){
                return true;
            }
            return false;
        }
    };

    CheckStrategy(String msg) {
        this.msg = msg;
    }

    private String msg;

    public abstract boolean check(String checkValue,String value);


    public String errorMsg(String value){
        return String.format(msg,value);
    }
}
