package com.moseeker.function.service.chaos.position;

import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.function.service.chaos.base.AbstractPositionEmailBuilder;
import com.moseeker.function.service.chaos.util.PositionSyncMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarnocPositionEmailBuilder extends AbstractPositionEmailBuilder<EmptyExtThirdPartyPosition> {
    @Autowired
    PositionSyncMailUtil positionSyncMailUtil;

    @Override
    public String message(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPosition, EmptyExtThirdPartyPosition position) throws BIZException {
        EmailBodyBuilder emailMessgeBuilder = new EmailBodyBuilder();

        emailMessgeBuilder.name("【招聘类型】：").value(moseekerPosition.getCandidateSource() == 1 ? "校招" : "社招");
        emailMessgeBuilder.name("【标题】：").value(moseekerPosition.getTitle());
        emailMessgeBuilder.name("【城市】：").value(positionSyncMailUtil.getCitys(moseekerPosition.getId()));
        emailMessgeBuilder.name("【地址】：").value("");
        emailMessgeBuilder.name("【职能】：").value(positionSyncMailUtil.getOccupation(thirdPartyPosition.getChannel(), thirdPartyPosition.getOccupation()));
        emailMessgeBuilder.name("【工作年限】：").value(positionSyncMailUtil.getExperience(moseekerPosition.getExperience()));
        emailMessgeBuilder.name("【学历要求】：").value(positionSyncMailUtil.getDegree(moseekerPosition.getDegree()));
        emailMessgeBuilder.line(email(moseekerPosition));
        emailMessgeBuilder.line("【职位描述】：");
        emailMessgeBuilder.line(describe(moseekerPosition));

        return emailMessgeBuilder.toString();
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.CARNOC;
    }
}
