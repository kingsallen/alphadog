package com.moseeker.profile.service.impl.talentpoolmvhouse.vo;

/**
 * 简历搬家操作信息
 * @author cjm
 * @date 2018-09-07 17:10
 **/
public class ProfileMoveOperationInfoVO {
    private String end_date;
    private String start_date;
    private int crawl_type;
    private String company_name;

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public int getCrawl_type() {
        return crawl_type;
    }

    public void setCrawl_type(int crawl_type) {
        this.crawl_type = crawl_type;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String toString() {
        return "ProfileMoveOperationInfoVO{" +
                "end_date='" + end_date + '\'' +
                ", start_date='" + start_date + '\'' +
                ", crawl_type=" + crawl_type +
                ", company_name='" + company_name + '\'' +
                '}';
    }
}
