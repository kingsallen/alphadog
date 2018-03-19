package com.moseeker.function.service.chaos.position;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.WorkType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.function.service.chaos.base.AbstractPositionEmailBuilder;
import com.moseeker.function.service.chaos.base.PositionEmailBuilder;
import com.moseeker.function.service.chaos.util.PositionSyncMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJobsDBPositionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JobsDBPositionEmailBuilder extends AbstractPositionEmailBuilder<ThirdpartyJobsDBPositionDO> {
    @Autowired
    PositionSyncMailUtil positionSyncMailUtil;

    @Override
    public String message(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPosition, ThirdpartyJobsDBPositionDO position) throws BIZException {
        EmailBodyBuilder emailMessgeBuilder = new EmailBodyBuilder();

        emailMessgeBuilder.name("【Job title】").value(moseekerPosition.getTitle());
        emailMessgeBuilder.name("【Job details】").value(getDetail(moseekerPosition));
        emailMessgeBuilder.name("【Summary 1】").value(position.getSummary1());
        emailMessgeBuilder.name("【Summary 2】").value(position.getSummary2());
        emailMessgeBuilder.name("【Summary 3】").value(position.getSummary3());
        emailMessgeBuilder.name("【Job functions 1】").value(positionSyncMailUtil.getOccupation(getChannelType().getValue(),thirdPartyPosition.getOccupation()));
        emailMessgeBuilder.name("【Job functions 2】").value(positionSyncMailUtil.getOccupation(getChannelType().getValue(),position.getOccupationExt1()));
        emailMessgeBuilder.name("【Job functions 3】").value(positionSyncMailUtil.getOccupation(getChannelType().getValue(),position.getOccupationExt2()));


        emailMessgeBuilder.name("【Work location】").value(thirdPartyPosition.getAddressName());
        emailMessgeBuilder.name("【Sub work location】").value(position.getChildAddressName());
        emailMessgeBuilder.name("【Employment type】").value(WorkType.instanceFromInt((int)moseekerPosition.getEmploymentType()).getName());
        emailMessgeBuilder.name("【Salary details (Monthly)】：").value(thirdPartyPosition.getSalaryBottom()+"-"+thirdPartyPosition.getSalaryTop());

        emailMessgeBuilder.line(email(moseekerPosition));

        return emailMessgeBuilder.toString();
    }

    public String getDetail(JobPositionDO moseekerPosition){
        String accounTabilities = moseekerPosition.getAccountabilities();
        String requirement = moseekerPosition.getRequirement();

        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(accounTabilities)) {
            descript.append("Job description and responsibilities:\n")
                    .append(accounTabilities);
        }
        if (StringUtils.isNotNullOrEmpty(requirement)) {
            descript.append("\nJob requirements:\n").append(requirement);
        }
        return descript.toString();
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }
}
