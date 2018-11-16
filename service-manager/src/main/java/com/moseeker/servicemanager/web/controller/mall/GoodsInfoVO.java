package com.moseeker.servicemanager.web.controller.mall;

/**
 * @author cjm
 * @date 2018-11-16 21:59
 **/
public class GoodsInfoVO {
    private Integer id;
    private Integer company_id;
    private Integer hr_id;
    private String title;
    private String pic_url;
    private Integer stock;
    private Integer credit;
    private String detail;
    private String rule;

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

    public Integer getHr_id() {
        return hr_id;
    }

    public void setHr_id(Integer hr_id) {
        this.hr_id = hr_id;
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

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "GoodsInfoVO{" +
                "id=" + id +
                ", company_id=" + company_id +
                ", hr_id=" + hr_id +
                ", title='" + title + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", stock=" + stock +
                ", credit=" + credit +
                ", detail='" + detail + '\'' +
                ", rule='" + rule + '\'' +
                '}';
    }
}
