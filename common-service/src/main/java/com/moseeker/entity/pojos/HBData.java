package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbScratchCardRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * 红包金额列表所需的前期数据。
 * @Author: jack
 * @Date: 2018/9/27
 */
public class HBData {

    private Map<Integer, String> titleMap = new HashMap<>();
    private Map<Integer, String> candidateNameMap = new HashMap<>();
    private Map<Integer, HrHbScratchCardRecord> cardNoMap = new HashMap<>();
    private Map<Integer, HrHbConfigRecord> configMap = new HashMap<>();

    public Map<Integer, String> getTitleMap() {
        return titleMap;
    }

    public void setTitleMap(Map<Integer, String> titleMap) {
        this.titleMap = titleMap;
    }

    public Map<Integer, String> getCandidateNameMap() {
        return candidateNameMap;
    }

    public void setCandidateNameMap(Map<Integer, String> candidateNameMap) {
        this.candidateNameMap = candidateNameMap;
    }

    public Map<Integer, HrHbScratchCardRecord> getCardNoMap() {
        return cardNoMap;
    }

    public void setCardNoMap(Map<Integer, HrHbScratchCardRecord> cardNoMap) {
        this.cardNoMap = cardNoMap;
    }

    public Map<Integer, HrHbConfigRecord> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<Integer, HrHbConfigRecord> configMap) {
        this.configMap = configMap;
    }
}
