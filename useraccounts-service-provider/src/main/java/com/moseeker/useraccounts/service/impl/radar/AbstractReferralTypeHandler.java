package com.moseeker.useraccounts.service.impl.radar;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionShareRecordDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.referraldb.ReferralLogDao;
import com.moseeker.baseorm.dao.referraldb.ReferralSeekRecommendDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.useraccounts.pojo.neo4j.UserDepthVO;
import com.moseeker.useraccounts.service.constant.ReferralTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class AbstractReferralTypeHandler {


    @Autowired
    protected UserWxUserDao wxUserDao;
    @Autowired
    protected HrWxWechatDao wxWechatDao;
    @Autowired
    protected ReferralLogDao referralLogDao;
    @Autowired
    protected CandidateShareChainDao shareChainDao;
    @Autowired
    protected ReferralSeekRecommendDao seekRecommendDao;
    @Autowired
    protected CandidatePositionShareRecordDao positionShareRecordDao;

    abstract ReferralTypeEnum getReferralType();

    public JSONObject createApplyCard(JobApplicationDO jobApplicationDO, JobPositionDO jobPosition,
                                      UserUserRecord applier, List<HrOperationRecordRecord> hrOperations,
                                      JSONObject referralTypeSingleMap) {
        JSONObject card = new JSONObject();
        int progress = jobApplicationDO.getAppTplId();
        if(progress == 6 || progress == 15 || progress == 16){
            progress = 1;
        }
        card.put("apply_id", jobApplicationDO.getId());
        card.put("datetime", getLastDateTime(jobApplicationDO.getSubmitTime(), hrOperations));
        card.put("progress", progress);
        card.put("recom", initRecomUserInfo(jobApplicationDO, referralTypeSingleMap));
        card.put("user", doInitUser(jobApplicationDO.getApplierId(), applier.getName()));
        card.put("position", doInitPosition(jobPosition));
        return card;
    }

    /**
     * 组装推荐人数据
     * @return jsonObject
     */
    protected abstract JSONObject initRecomUserInfo(JobApplicationDO jobApplicationDO, JSONObject referralTypeSingleMap);

    protected abstract List<JobApplicationDO> getApplicationsByReferralType(List<JobApplicationDO> jobApplicationDOS);

    protected abstract JSONObject getReferralTypeMap(UserEmployeeRecord employeeRecord, List<JobApplicationDO> jobApplicationDOS, List<UserDepthVO> applierDegrees);

    private String getLastDateTime(String applyTimeStr, List<HrOperationRecordRecord> hrOperations) {
        DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        if(hrOperations == null || hrOperations.size() == 0){
            DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date applyTime;
            try {
                applyTime = sdf1.parse(applyTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
            return sdf.format(applyTime);
        }
        long lastTime = hrOperations.get(0).getOptTime().getTime();
        for(HrOperationRecordRecord hrOperationRecord : hrOperations){
            if(hrOperationRecord.getOptTime().getTime() > lastTime){
                lastTime = hrOperationRecord.getOptTime().getTime();
            }
        }
        return sdf.format(new Date(lastTime));
    }

    private JSONObject doInitPosition(JobPositionDO jobPosition) {
        JSONObject position = new JSONObject();
        position.put("pid", jobPosition.getId());
        position.put("title", jobPosition.getTitle());
        return position;
    }

    private JSONObject doInitUser(int uid, String name) {
        JSONObject user = new JSONObject();
        user.put("uid", uid);
        user.put("name", name);
        return user;
    }

    public void postProcessAfterCreateCard(JSONObject card, JobApplicationDO jobApplicationDO, List<UserDepthVO> applierDegrees) {}
}
