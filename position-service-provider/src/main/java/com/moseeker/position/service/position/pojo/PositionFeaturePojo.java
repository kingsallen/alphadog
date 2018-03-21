package com.moseeker.position.service.position.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;

import java.util.List;

/**
 * Created by zztaiwll on 18/3/21.
 */
public class PositionFeaturePojo {
    private int pid;
    private List<HrCompanyFeature> featureList;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<HrCompanyFeature> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<HrCompanyFeature> featureList) {
        this.featureList = featureList;
    }
}
