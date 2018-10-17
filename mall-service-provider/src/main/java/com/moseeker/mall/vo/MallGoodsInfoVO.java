package com.moseeker.mall.vo;

import java.sql.Timestamp;

/**
 * @author cjm
 * @date 2018-10-14 11:26
 **/
public class MallGoodsInfoVO {

    private Integer id;
    private Integer company_id;
    private String title;
    private String pic_url;
    private String detail;
    private String rule;
    private Integer credit;
    private Integer stock;
    private Integer exchange_num;
    private Integer exchange_order;
    private Timestamp create_time;
    private Timestamp update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getExchange_num() {
        return exchange_num;
    }

    public void setExchange_num(Integer exchange_num) {
        this.exchange_num = exchange_num;
    }

    public Integer getExchange_order() {
        return exchange_order;
    }

    public void setExchange_order(Integer exchange_order) {
        this.exchange_order = exchange_order;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }
}

