package com.moseeker.company.bean;

import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolPast;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTag;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zztaiwll on 18/4/3.
 */
public class TalentTagPOJO implements Serializable{
    private static final long serialVersionUID = 1193221362;
    private List<TalentpoolTag> tags;
    private Integer page_number;
    private Integer page_size;
    private Integer total;
    private Integer flag;

    public List<TalentpoolTag> getTags() {
        return tags;
    }

    public void setTags(List<TalentpoolTag> tags) {
        this.tags = tags;
    }

    public Integer getPage_number() {
        return page_number;
    }

    public void setPage_number(Integer page_number) {
        this.page_number = page_number;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
