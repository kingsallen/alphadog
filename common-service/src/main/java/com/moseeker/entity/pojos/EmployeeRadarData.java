package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Author: liuxuhui
 * @Date: 2018/9/28
 */
public class EmployeeRadarData {

    private Map<Integer, UserWxUserRecord> wxUserRecordList = new HashMap<>();
    private Map<Integer, UserUserRecord> userRecordList = new HashMap<>();
    private Map<Integer, Integer> positionView = new HashMap<>();
    private Map<Integer, JobPositionDO> positionMap = new HashMap<>();
    private Map<Integer, UserUserRecord> root2UserMap = new HashMap<>();
    private Set<Integer> recommendUserSet = new HashSet<>();
    private Map<Integer, Integer> recommendMap = new HashMap<>();
    private Map<Integer, Byte> userFromMap = new HashMap<>();
    private Map<Integer, Timestamp> timeMap = new HashMap<>();

    public Map<Integer, UserUserRecord> getUserRecordList() {
        return userRecordList;
    }

    public void setUserRecordList(Map<Integer, UserUserRecord> userRecordList) {
        this.userRecordList = userRecordList;
    }

    public Map<Integer, UserWxUserRecord> getWxUserRecordList() {
        return wxUserRecordList;
    }

    public void setWxUserRecordList(Map<Integer, UserWxUserRecord> wxUserRecordList) {
        this.wxUserRecordList = wxUserRecordList;
    }

    public Map<Integer, Integer> getPositionView() {
        return positionView;
    }

    public void setPositionView(Map<Integer, Integer> positionView) {
        this.positionView = positionView;
    }

    public Map<Integer, JobPositionDO> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<Integer, JobPositionDO> positionMap) {
        this.positionMap = positionMap;
    }

    public Set<Integer> getRecommendUserSet() {
        return recommendUserSet;
    }

    public void setRecommendUserSet(Set<Integer> recommendUserSet) {
        this.recommendUserSet = recommendUserSet;
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

    public Map<Integer, Timestamp> getTimeMap() {
        return timeMap;
    }

    public void setTimeMap(Map<Integer, Timestamp> timeMap) {
        this.timeMap = timeMap;
    }

    public Map<Integer, Integer> getRecommendMap() {
        return recommendMap;
    }

    public void setRecommendMap(Map<Integer, Integer> recommendMap) {
        this.recommendMap = recommendMap;
    }
}
