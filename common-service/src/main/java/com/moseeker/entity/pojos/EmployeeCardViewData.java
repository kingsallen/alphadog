package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionLogRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuxuhui
 * @Date: 2018/9/28
 */
public class EmployeeCardViewData {

    private Map<Integer, UserWxUserRecord> wxUserRecordList = new HashMap<>();
    private Map<Integer, UserUserRecord> userRecordList = new HashMap<>();
    private List<CandidatePositionRecord> candidatePositionRecords = new ArrayList<>();
    private Map<Integer, JobPositionDO> positionMap = new HashMap<>();
    private Map<Integer, UserUserRecord> root2UserMap = new HashMap<>();
    private Map<Integer, Byte> userFromMap = new HashMap<>();
    private List<ReferralConnectionLogRecord> connectionLogList = new ArrayList<>();
    private Map<Integer, List<RadarUserInfo>> connectionMap= new HashMap<>();

    public Map<Integer, UserWxUserRecord> getWxUserRecordList() {
        return wxUserRecordList;
    }

    public void setWxUserRecordList(Map<Integer, UserWxUserRecord> wxUserRecordList) {
        this.wxUserRecordList = wxUserRecordList;
    }

    public Map<Integer, UserUserRecord> getUserRecordList() {
        return userRecordList;
    }

    public void setUserRecordList(Map<Integer, UserUserRecord> userRecordList) {
        this.userRecordList = userRecordList;
    }

    public List<CandidatePositionRecord> getCandidatePositionRecords() {
        return candidatePositionRecords;
    }

    public void setCandidatePositionRecords(List<CandidatePositionRecord> candidatePositionRecords) {
        this.candidatePositionRecords = candidatePositionRecords;
    }

    public Map<Integer, JobPositionDO> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<Integer, JobPositionDO> positionMap) {
        this.positionMap = positionMap;
    }

    public Map<Integer, UserUserRecord> getRoot2UserMap() {
        return root2UserMap;
    }

    public void setRoot2UserMap(Map<Integer, UserUserRecord> root2UserMap) {
        this.root2UserMap = root2UserMap;
    }

    public Map<Integer, Byte> getUserFromMap() {
        return userFromMap;
    }

    public void setUserFromMap(Map<Integer, Byte> userFromMap) {
        this.userFromMap = userFromMap;
    }

    public List<ReferralConnectionLogRecord> getConnectionLogList() {
        return connectionLogList;
    }

    public void setConnectionLogList(List<ReferralConnectionLogRecord> connectionLogList) {
        this.connectionLogList = connectionLogList;
    }

    public Map<Integer, List<RadarUserInfo>> getConnectionMap() {
        return connectionMap;
    }

    public void setConnectionMap(Map<Integer, List<RadarUserInfo>> connectionMap) {
        this.connectionMap = connectionMap;
    }
}
