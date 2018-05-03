package com.moseeker.function.service.chaos.position;

import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.function.service.chaos.base.AbstractPositionEmailBuilder;
import com.moseeker.function.service.chaos.base.PositionEmailBuilder;
import com.moseeker.function.service.chaos.util.PositionSyncMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyZhilianPositionAddressDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ZhilianPositionEmailBuilder extends AbstractPositionEmailBuilder<List<ThirdpartyZhilianPositionAddressDO>> {

    @Autowired
    PositionSyncMailUtil positionSyncMailUtil;

    @Autowired
    DictCityDao cityDao;

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }


    @Override
    public String message(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPosition, List<ThirdpartyZhilianPositionAddressDO> position) throws BIZException {
        EmailBodyBuilder emailMessgeBuilder = new EmailBodyBuilder();

        emailMessgeBuilder.name("【招聘类型】：").value(moseekerPosition.getCandidateSource() == 1 ? "校招" : "社招");
        emailMessgeBuilder.name("【标题】：").value(moseekerPosition.getTitle());
        emailMessgeBuilder.name("【地址】：").value(address(position));


        emailMessgeBuilder.name("【公司】：").value(thirdPartyPosition.getCompanyName());
        emailMessgeBuilder.name("【部门】：").value(thirdPartyPosition.getDepartment());
        emailMessgeBuilder.name("【招聘人数】：").value(String.valueOf(moseekerPosition.getCount()));

        emailMessgeBuilder.name("【学历要求】：").value(positionSyncMailUtil.getDegree(moseekerPosition.getDegree()));
        emailMessgeBuilder.name("【职能】：").value(positionSyncMailUtil.getOccupation(thirdPartyPosition.getChannel(), thirdPartyPosition.getOccupation()));
        emailMessgeBuilder.name("【工作年限】：").value(positionSyncMailUtil.getExperience(moseekerPosition.getExperience()));
        emailMessgeBuilder.name("【月薪】：").value(thirdPartyPosition.getSalaryBottom()+"-"+thirdPartyPosition.getSalaryTop());
        emailMessgeBuilder.name("【学历要求】：").value(positionSyncMailUtil.getDegree(moseekerPosition.getDegree()));
        emailMessgeBuilder.name("【招聘人数】：").value(String.valueOf(thirdPartyPosition.getCount()));
        emailMessgeBuilder.name("【公司】：").value(thirdPartyPosition.getCompanyName());

        emailMessgeBuilder.line(email(moseekerPosition));
        emailMessgeBuilder.line("【职位描述】：");
        emailMessgeBuilder.line(describe(moseekerPosition));

        return emailMessgeBuilder.toString();
    }

    public String address(List<ThirdpartyZhilianPositionAddressDO> position){
        StringBuilder sb = new StringBuilder();
        List<Integer> cityCodes = position.stream().map(p->p.getCityCode()).collect(Collectors.toList());
        List<DictCityRecord> cityRecords = cityDao.getCitiesByCodes(cityCodes);
        for(ThirdpartyZhilianPositionAddressDO a:position){
            Optional<DictCityRecord> optional = cityRecords.stream().filter(c->c.getCode() == a.getCityCode()).findFirst();
            if(optional.isPresent()){
                sb.append("【城市】").append(optional.get().getName()).append(" ").append("【地址】").append(a.getAddress()).append(divider);
            }
        }

        return sb.toString();
    }
}
