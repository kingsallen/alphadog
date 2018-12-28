package com.moseeker.useraccounts.service.impl.radar;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.useraccounts.service.constant.ReferralTypeEnum;

import java.util.List;

public abstract class AbstractReferralTypeHandler {

    protected IReferralProgressHandler progressHandler;

    public AbstractReferralTypeHandler(IReferralProgressHandler progressHandler){
        this.progressHandler = progressHandler;
    }

    public AbstractReferralTypeHandler() {
    }

    abstract ReferralTypeEnum getReferralType();

    public JSONObject createApplyCard(JobApplicationDO jobApplicationDO, List<JobPositionDO> jobPositions,
                                      List<UserUserRecord> userUsers, List<Object> applierDegrees) {
        JSONObject card = new JSONObject();
        card.put("degree", 0);
        // todo 去hr操作记录里查
        card.put("datetime", );
        card.put("progress", );
        card.put("recom", );
        card.put("user", );
        JSONObject position = new JSONObject();
        position.put("pid", )
        card.put("position", );
        return card;
    }
}
