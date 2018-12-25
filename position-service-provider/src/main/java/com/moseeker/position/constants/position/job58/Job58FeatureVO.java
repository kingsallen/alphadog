package com.moseeker.position.constants.position.job58;

/**
 * 58福利特色，由58官方
 * @author cjm
 * @date 2018-11-23 14:06
 **/
public class Job58FeatureVO {

    private Integer code;
    private String text;

    public Job58FeatureVO() {
    }

    public Job58FeatureVO(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
