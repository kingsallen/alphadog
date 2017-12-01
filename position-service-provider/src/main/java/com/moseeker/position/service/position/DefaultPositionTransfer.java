package com.moseeker.position.service.position;

import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.PositionTransfer;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultPositionTransfer{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    
    protected void setDepartment(ThirdPartyPosition form, JobPositionDO positionDO, ThirdPartyPositionForSynchronization position) {
        //do nothing
    }

    
    protected void setOccupation(ThirdPartyPosition positionForm, ThirdPartyPositionForSynchronization position) {
        //do nothing
    }

    
    protected void setEmployeeType(byte employment_type, ThirdPartyPositionForSynchronization position) {
        WorkType workType = WorkType.instanceFromInt(employment_type);
        position.setWork_type(String.valueOf(WorkTypeChangeUtil.getLiepinWorkType(workType).getValue()));
    }

    
    protected void setDegree(int degreeInt, ThirdPartyPositionForSynchronization position) {
        //do nothing
    }

    
    protected void setExperience(Integer experience, ThirdPartyPositionForSynchronization position) {
        //do nothing
    }

    
    public void setCities(JobPositionDO positionDB, ThirdPartyPositionForSynchronization syncPosition) {
        syncPosition.setPub_place_code("");
        syncPosition.setPub_place_name("");
    }

    
    public ChannelType getChannel() {
        return null;
    }
}
