package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralPositionBonusStageDetailRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/9/28
 */
public class BonusData {

    private Map<Integer, ReferralPositionBonusStageDetailRecord> stageDetailMap = new HashMap<>();
    private Map<Integer, String> candidateMap = new HashMap<>();
    private Map<Integer, String> positionTitleMap = new HashMap<>();
    private Map<Integer, Long> employmentDateMap = new HashMap<>();

    public Map<Integer, ReferralPositionBonusStageDetailRecord> getStageDetailMap() {
        return stageDetailMap;
    }

    public void setStageDetailMap(Map<Integer, ReferralPositionBonusStageDetailRecord> stageDetailMap) {
        this.stageDetailMap = stageDetailMap;
    }

    public Map<Integer, String> getCandidateMap() {
        return candidateMap;
    }

    public void setCandidateMap(Map<Integer, String> candidateMap) {
        this.candidateMap = candidateMap;
    }

    public Map<Integer, String> getPositionTitleMap() {
        return positionTitleMap;
    }

    public void setPositionTitleMap(Map<Integer, String> positionTitleMap) {
        this.positionTitleMap = positionTitleMap;
    }

    public Map<Integer, Long> getEmploymentDateMap() {
        return employmentDateMap;
    }

    public void setEmploymentDateMap(Map<Integer, Long> employmentDateMap) {
        this.employmentDateMap = employmentDateMap;
    }
}
