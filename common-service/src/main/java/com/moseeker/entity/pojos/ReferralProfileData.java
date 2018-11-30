package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuxuhui
 * @Date: 2018/9/28
 */
public class ReferralProfileData {

    private List<ReferralLog> logList;
    private Map<Integer, String> positionTitleMap = new HashMap<>();
    private Map<Integer, ProfileAttachmentDO> attchmentMap = new HashMap<>();
    private Map<Integer, String> employeeNameMap = new HashMap<>();

    public List<ReferralLog> getLogList() {
        return logList;
    }

    public void setLogList(List<ReferralLog> logList) {
        this.logList = logList;
    }

    public Map<Integer, String> getPositionTitleMap() {
        return positionTitleMap;
    }

    public void setPositionTitleMap(Map<Integer, String> positionTitleMap) {
        this.positionTitleMap = positionTitleMap;
    }

    public Map<Integer, ProfileAttachmentDO> getAttchmentMap() {
        return attchmentMap;
    }

    public void setAttchmentMap(Map<Integer, ProfileAttachmentDO> attchmentMap) {
        this.attchmentMap = attchmentMap;
    }

    public Map<Integer, String> getEmployeeNameMap() {
        return employeeNameMap;
    }

    public void setEmployeeNameMap(Map<Integer, String> employeeNameMap) {
        this.employeeNameMap = employeeNameMap;
    }
}
