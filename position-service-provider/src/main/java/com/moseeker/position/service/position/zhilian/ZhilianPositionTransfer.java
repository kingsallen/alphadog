package com.moseeker.position.service.position.zhilian;

import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.ExperienceChangeUtil;
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

import java.text.DecimalFormat;
import java.util.*;

@Service
public class ZhilianPositionTransfer extends PositionTransfer {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void setDepartment(ThirdPartyPosition form, JobPositionDO positionDO, ThirdPartyPositionForSynchronization position) {
        //do noting
    }

    @Override
    protected void setOccupation(ThirdPartyPosition positionForm, ThirdPartyPositionForSynchronization position) {
        DecimalFormat df = new DecimalFormat("000");
        List<String> list=new ArrayList<>();
        if (positionForm.getOccupation() != null) {
            positionForm.getOccupation().forEach(o -> list.add(df.format(Integer.valueOf(o))));
        }
        position.setOccupation(list);
    }

    @Override
    protected void setEmployeeType(byte employment_type, ThirdPartyPositionForSynchronization position) {
        WorkType workType = WorkType.instanceFromInt(employment_type);
        position.setWork_type(String.valueOf(WorkTypeChangeUtil.getZhilianEmployeeType(workType).getValue()));
    }

    @Override
    protected void setDegree(int degreeInt, ThirdPartyPositionForSynchronization position) {
        Degree degree = Degree.instanceFromCode(String.valueOf(degreeInt));
        position.setDegree_code(DegreeChangeUtil.getZhilianDegree(degree).getValue());
        position.setDegree(DegreeChangeUtil.getZhilianDegree(degree).getName());
    }

    @Override
    protected void setExperience(Integer experience, ThirdPartyPositionForSynchronization position) {
        position.setExperience_code(ExperienceChangeUtil.getZhilianExperience(experience).getValue());
        position.setExperience(ExperienceChangeUtil.getZhilianExperience(experience).getName());
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.ZHILIAN;
    }

}
