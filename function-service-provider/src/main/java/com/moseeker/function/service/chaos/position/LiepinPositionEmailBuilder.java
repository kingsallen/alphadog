package com.moseeker.function.service.chaos.position;

import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.WorkType;
import com.moseeker.function.service.chaos.base.AbstractPositionEmailBuilder;
import com.moseeker.function.service.chaos.base.PositionEmailBuilder;
import com.moseeker.function.service.chaos.util.PositionSyncMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LiepinPositionEmailBuilder extends AbstractPositionEmailBuilder<EmptyExtThirdPartyPosition> {

    @Autowired
    PositionSyncMailUtil positionSyncMailUtil;

    @Override
    public ChannelType getChannelType() {
        return ChannelType.LIEPIN;
    }

    @Override
    public String message(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPosition, EmptyExtThirdPartyPosition position) throws BIZException {
        EmailBodyBuilder emailMessgeBuilder = new EmailBodyBuilder();

        emailMessgeBuilder.name("【招聘类型】：").value(moseekerPosition.getCandidateSource() == 1 ? "校招" : "社招");
        emailMessgeBuilder.name("【标题】：").value(moseekerPosition.getTitle());
        emailMessgeBuilder.name("【城市】：").value(positionSyncMailUtil.getCitys(moseekerPosition.getId()));
        emailMessgeBuilder.name("【地址】：").value("");
        emailMessgeBuilder.name("【职能】：").value(positionSyncMailUtil.getOccupation(thirdPartyPosition.getChannel(), thirdPartyPosition.getOccupation()));
        emailMessgeBuilder.name("【部门】：").value(thirdPartyPosition.getDepartmentName());
        emailMessgeBuilder.name("【月薪】：").value(thirdPartyPosition.getSalaryBottom()+"-"+thirdPartyPosition.getSalaryTop());
        emailMessgeBuilder.name("【面议】：").value(thirdPartyPosition.getSalaryDiscuss() == 0 ? "否" : "是");
        emailMessgeBuilder.name("【工作年限】：").value(positionSyncMailUtil.getExperience(moseekerPosition.getExperience()));
        emailMessgeBuilder.name("【学历要求】：").value(positionSyncMailUtil.getDegree(moseekerPosition.getDegree()));
        emailMessgeBuilder.name("【工作性质】：").value(WorkType.instanceFromInt((int)moseekerPosition.getEmploymentType()).getName());
        emailMessgeBuilder.name("【公司】：").value(thirdPartyPosition.getCompanyName());
        emailMessgeBuilder.name("【招聘人数】：").value(String.valueOf(moseekerPosition.getCount()));
        if (moseekerPosition.getCandidateSource() == 1) {
            emailMessgeBuilder.name("【实习薪资】：").value(thirdPartyPosition.getPracticeSalary()).value(thirdPartyPosition.getPracticeSalaryUnit() == 1 ? "元/天" : "元/月");
            emailMessgeBuilder.name("【每周实习天数】：").value(thirdPartyPosition.getPracticePerWeek());
        } else {
            emailMessgeBuilder.name("【发放月数】：").value(thirdPartyPosition.getSalaryMonth());
            emailMessgeBuilder.name("【反馈时长】：").value(thirdPartyPosition.getFeedbackPeriod());
        }
        emailMessgeBuilder.line(email(moseekerPosition));
        emailMessgeBuilder.line("【职位描述】：");
        emailMessgeBuilder.line(describe(moseekerPosition));

        return emailMessgeBuilder.toString();
    }
}
