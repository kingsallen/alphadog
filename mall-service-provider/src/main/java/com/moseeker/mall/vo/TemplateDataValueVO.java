package com.moseeker.mall.vo;

/**
 * 模板消息data中有不同的对象，目前观察下来都是以color, value的形式，color都是采用统一颜色，
 * 所以这里将所有的value聚合在一起，如果以后有需求变动，再将这里拆成多个对象
 * @author cjm
 * @date 2018-10-24 14:41
 **/
public class TemplateDataValueVO {

    private String first;
    private String keyWord1;
    private String keyWord2;
    private String keyWord3;
    private String keyWord4;
    private String remark;
    private Integer companyId;
    private Integer templateId;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getKeyWord1() {
        return keyWord1;
    }

    public void setKeyWord1(String keyWord1) {
        this.keyWord1 = keyWord1;
    }

    public String getKeyWord2() {
        return keyWord2;
    }

    public void setKeyWord2(String keyWord2) {
        this.keyWord2 = keyWord2;
    }

    public String getKeyWord3() {
        return keyWord3;
    }

    public void setKeyWord3(String keyWord3) {
        this.keyWord3 = keyWord3;
    }

    public String getKeyWord4() {
        return keyWord4;
    }

    public void setKeyWord4(String keyWord4) {
        this.keyWord4 = keyWord4;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "TemplateDataValueVO{" +
                "first='" + first + '\'' +
                ", keyWord1='" + keyWord1 + '\'' +
                ", keyWord2='" + keyWord2 + '\'' +
                ", keyWord3='" + keyWord3 + '\'' +
                ", keyWord4='" + keyWord4 + '\'' +
                ", remark='" + remark + '\'' +
                ", companyId=" + companyId +
                ", templateId=" + templateId +
                '}';
    }
}

