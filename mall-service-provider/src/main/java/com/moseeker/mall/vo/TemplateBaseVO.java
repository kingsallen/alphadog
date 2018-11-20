package com.moseeker.mall.vo;

/**
 * @author cjm
 * @date 2018-10-24 14:25
 **/
public class TemplateBaseVO {

    private String color;
    private String value;

    @Override
    public String toString() {
        return "TemplateBaseVO{" +
                "color='" + color + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {

        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
