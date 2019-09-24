package com.moseeker.searchengine.domain.vo;

import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;

/**
 * TODO
 *
 * @Author jack
 * @Date 2019/9/5 8:45 PM
 * @Version 1.0
 */
public class CompanyDetail {

    private OtherVO other;
    private HrCompany company;

    public OtherVO getOther() {
        return other;
    }

    public void setOther(OtherVO other) {
        this.other = other;
    }

    public HrCompany getCompany() {
        return company;
    }

    public void setCompany(HrCompany company) {
        this.company = company;
    }
}
