package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import java.util.*;

/**
 * @Author: liuxuhui
 * @Date: 2018/9/28
 */
public class EmployeeRadarData {

    private Map<Integer, UserWxUserRecord> wxUserRecordList = new HashMap<>();
    private List<UserUserRecord> userRecordList = new ArrayList<>();
    private Map<Integer, Integer> positionView = new HashMap<>();
    private Map<Integer, JobPositionDO> positionMap = new HashMap<>();
    private Map<Integer, UserUserRecord> root2UserMap = new HashMap<>();
    private Set<Integer> recommendUserSet = new HashSet<>();
    private Map<Integer, Byte> userFromMap = new HashMap<>();

    public List<UserUserRecord> getUserRecordList() {
        return userRecordList;
    }

    public void setUserRecordList(List<UserUserRecord> userRecordList) {
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
}
