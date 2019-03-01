package com.moseeker.useraccounts.service.impl.vo;

/**
 * @author cjm
 * @date 2018-12-11 16:06
 **/
public class InviteTemplateVO {

    private String first;
    private String keyWord1;
    private String keyWord2;
    private String keyWord3;
    private String remark;
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
}
