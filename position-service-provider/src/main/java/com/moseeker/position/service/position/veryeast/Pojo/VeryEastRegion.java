package com.moseeker.position.service.position.veryeast.Pojo;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class VeryEastRegion {
    private List<String> text;
    private List<String> code;

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public String codeToString(){
        if(code==null || code.isEmpty()) return "";
        return JSON.toJSONString(code);
    }
}
