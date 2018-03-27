package com.moseeker.function.service.chaos.position;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.WorkType;
import com.moseeker.function.service.chaos.base.AbstractPositionEmailBuilder;
import com.moseeker.function.service.chaos.base.PositionEmailBuilder;
import com.moseeker.function.service.chaos.util.PositionSyncMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJob1001PositionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class VLPositionEmailBuilder extends AbstractPositionEmailBuilder<ThirdpartyJob1001PositionDO> {

    @Autowired
    PositionSyncMailUtil positionSyncMailUtil;

    @Override
    public String message(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPosition, ThirdpartyJob1001PositionDO position) throws BIZException {
        EmailBodyBuilder emailMessgeBuilder = new EmailBodyBuilder();

        emailMessgeBuilder.name("【发布网站】").value(position.getSubsite());
        emailMessgeBuilder.name("【标题】").value(moseekerPosition.getTitle());
        emailMessgeBuilder.name("【部门】").value(moseekerPosition.getDepartment());
        emailMessgeBuilder.name("【语言】").value(moseekerPosition.getLanguage());

        emailMessgeBuilder.name("【招聘人数】").value((int)moseekerPosition.getCount());
        emailMessgeBuilder.name("【工作经验】").value(moseekerPosition.getExperience());
        emailMessgeBuilder.name("【地区】").value(positionSyncMailUtil.getCitys(moseekerPosition.getId()));

        emailMessgeBuilder.name("【学历】").value(positionSyncMailUtil.getDegree(moseekerPosition.getDegree()));
        emailMessgeBuilder.name("【招聘类型】").value(moseekerPosition.getCandidateSource() == 1 ? "校招" : "社招");
        emailMessgeBuilder.name("【工作性质】").value(WorkType.instanceFromInt((int)moseekerPosition.getEmploymentType()).getName());
        emailMessgeBuilder.name("【性别】").value(getGender((int)moseekerPosition.getGender()));

        emailMessgeBuilder.name("【公司】").value(thirdPartyPosition.getCompanyName());
        emailMessgeBuilder.name("【职能】").value(positionSyncMailUtil.getOccupation(thirdPartyPosition.getChannel(), thirdPartyPosition.getOccupation()));
        emailMessgeBuilder.name("【薪资】").value(thirdPartyPosition.getSalaryBottom()+"-"+thirdPartyPosition.getSalaryTop());

        emailMessgeBuilder.line(email(moseekerPosition));
        emailMessgeBuilder.line("【职位描述】：");
        emailMessgeBuilder.line(describe(moseekerPosition));
        return emailMessgeBuilder.toString();
    }

    private String getGender(int gender){
        //0：未知，1：男，2：女
        switch (gender){
            case 1:
                return "男";
            case 2:
                return "女";
            default:
                return "未知";
        }
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB1001;
    }
}
