package com.moseeker.position.service.position.liepin;

import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.WorkTypeChangeUtil;
import com.moseeker.position.service.position.base.PositionTransfer;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LiepinPositionTransfer extends PositionTransfer {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    protected void setDepartment(ThirdPartyPosition form, JobPositionDO positionDO, ThirdPartyPositionForSynchronization position) {
        position.setDepartment(form.getDepartmentName());
    }

    @Override
    protected void setOccupation(ThirdPartyPosition positionForm, ThirdPartyPositionForSynchronization position) {
        List<String> list=new ArrayList<>();
        if (positionForm.getOccupation() != null) {
            positionForm.getOccupation().forEach(o -> list.add(o));
        }
        position.setOccupation(list);
    }

    @Override
    protected void setEmployeeType(byte employment_type, ThirdPartyPositionForSynchronization position) {
        WorkType workType = WorkType.instanceFromInt(employment_type);
        position.setWork_type(String.valueOf(WorkTypeChangeUtil.getLiepinWorkType(workType).getValue()));
    }

    @Override
    protected void setDegree(int degreeInt, ThirdPartyPositionForSynchronization position) {
        Degree degree = Degree.instanceFromCode(String.valueOf(degreeInt));
        position.setDegree_code(DegreeChangeUtil.getLiepinDegree(degree).getValue());
        position.setDegree(DegreeChangeUtil.getLiepinDegree(degree).getName());
    }

    @Override
    protected void setExperience(Integer experience, ThirdPartyPositionForSynchronization position) {
        position.setExperience_code((experience == null || experience == 0) ? "不限" : String.valueOf(experience));
        position.setExperience((experience == null || experience == 0) ? "不限" : String.valueOf(experience));
    }


    @Override
    //做一些额外操作
    public void setMore(ThirdPartyPositionForSynchronization position,ThirdPartyPosition form, JobPositionDO positionDB){
        setWelfare(position,positionDB);
    }

    public void setWelfare(ThirdPartyPositionForSynchronization position, JobPositionDO positionDB){
        if(positionDB.getFeature() == null || positionDB.getFeature().isEmpty()){
            //爬虫需要即使数据库这个字段为空，也需要要一个空列表
            position.setWelfare(new ArrayList<>());
        }else {
            position.setWelfare(Arrays.asList(positionDB.getFeature().split("#")));
        }
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.LIEPIN;
    }
}
