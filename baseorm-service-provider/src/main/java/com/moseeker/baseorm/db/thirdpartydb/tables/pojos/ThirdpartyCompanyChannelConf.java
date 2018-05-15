/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.thirdpartydb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 公司可同步渠道配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ThirdpartyCompanyChannelConf implements Serializable {

    private static final long serialVersionUID = 355219278;

    private Integer   id;
    private Integer   companyId;
    private Integer   channel;
    private Timestamp createTime;

    public ThirdpartyCompanyChannelConf() {}

    public ThirdpartyCompanyChannelConf(ThirdpartyCompanyChannelConf value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.channel = value.channel;
        this.createTime = value.createTime;
    }

    public ThirdpartyCompanyChannelConf(
        Integer   id,
        Integer   companyId,
        Integer   channel,
        Timestamp createTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.channel = channel;
        this.createTime = createTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getChannel() {
        return this.channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ThirdpartyCompanyChannelConf (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(channel);
        sb.append(", ").append(createTime);

        sb.append(")");
        return sb.toString();
    }
}
