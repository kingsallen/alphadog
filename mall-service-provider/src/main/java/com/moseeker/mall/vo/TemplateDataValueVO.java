package com.moseeker.mall.vo;

/**
 * 模板消息data中有不同的对象，目前观察下来都是以color, value的形式，color都是采用统一颜色，
 * 所以这里将所有的value聚合在一起，如果以后有需求变动，再将这里拆成多个对象
 * @author cjm
 * @date 2018-10-24 14:41
 **/
public class TemplateDataValueVO {

    private String first;
    private String account;
    private String time;
    private String type;
    private String creditChange;
    private String creditName;
    private String remark;
    private String number;
    private String amount;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreditChange() {
        return creditChange;
    }

    public void setCreditChange(String creditChange) {
        this.creditChange = creditChange;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TemplateDataValueVO{" +
                "first='" + first + '\'' +
                ", account='" + account + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", creditChange='" + creditChange + '\'' +
                ", creditName='" + creditName + '\'' +
                ", remark='" + remark + '\'' +
                ", number='" + number + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
