package com.moseeker.function.service.chaos.position;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.common.constants.WorkType;
import com.moseeker.function.service.chaos.base.AbstractPositionEmailBuilder;
import com.moseeker.function.service.chaos.base.PositionEmailBuilder;
import com.moseeker.function.service.chaos.util.PositionSyncMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyVeryEastPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class VEPositionEmailBuilder extends AbstractPositionEmailBuilder<ThirdpartyVeryEastPositionDO> {
    Logger logger= LoggerFactory.getLogger(VEPositionEmailBuilder.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    PositionSyncMailUtil positionSyncMailUtil;

    @Override
    public ChannelType getChannelType() {
        return ChannelType.VERYEAST;
    }

    @Override
    public String message(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPosition, ThirdpartyVeryEastPositionDO position) throws BIZException {
        EmailBodyBuilder emailMessgeBuilder = new EmailBodyBuilder();

        emailMessgeBuilder.name("【公司】").value(thirdPartyPosition.getCompanyName());
        emailMessgeBuilder.name("【标题】").value(moseekerPosition.getTitle());
        emailMessgeBuilder.name("【地址】").value(positionSyncMailUtil.getCitys(moseekerPosition.getId()));
        emailMessgeBuilder.name("【招聘人数】").value(String.valueOf(moseekerPosition.getCount()));
        emailMessgeBuilder.name("【薪资】").value(thirdPartyPosition.getSalaryBottom()+"-"+thirdPartyPosition.getSalaryTop());
        emailMessgeBuilder.name("【职能】").value(positionSyncMailUtil.getOccupation(thirdPartyPosition.getChannel(), thirdPartyPosition.getOccupation()));
        emailMessgeBuilder.name("【学历】").value(positionSyncMailUtil.getDegree(moseekerPosition.getDegree()));
        emailMessgeBuilder.name("【工作经验】").value(positionSyncMailUtil.getExperience(moseekerPosition.getExperience()));
        emailMessgeBuilder.name("【工作性质】").value(WorkType.instanceFromInt((int)moseekerPosition.getEmploymentType()).getName());

        String json=redisClient.get(RefreshConstant.APP_ID, KeyIdentifier.THIRD_PARTY_ENVIRON_PARAM.toString(),String.valueOf(getChannelType().getValue()));
        JSONObject obj=JSONObject.parseObject(json);
        JSONArray accommodations=obj.getJSONArray("accommodation");
        JSONArray computerLevels=obj.getJSONArray("computerLevel");
        JSONArray languageTypes=obj.getJSONArray("languageType");
        JSONArray languageLevels=obj.getJSONArray("languageLevel");
        emailMessgeBuilder.name("").name("【提供食宿】").value(getText(accommodations,position.getAccommodation()+""));
        emailMessgeBuilder.name("").name("【年龄要求】").value(position.getAge_bottom()+"至"+position.getAge_top()+"岁");
        emailMessgeBuilder.name("").name("【语言能力1】").value(getText(languageTypes,position.getLanguageType1()+"")+"，掌握程度："+getText(languageLevels,position.getLanguageLevel1()+""));
        emailMessgeBuilder.name("").name("【语言能力2】").value(getText(languageTypes,position.getLanguageType2()+"")+"，掌握程度："+getText(languageLevels,position.getLanguageLevel2()+""));
        emailMessgeBuilder.name("").name("【语言能力3】").value(getText(languageTypes,position.getLanguageType3()+"")+"，掌握程度："+getText(languageLevels,position.getLanguageLevel3()+""));
        emailMessgeBuilder.name("").name("【计算机能力】").value(getText(computerLevels,position.getComputerLevel()+""));
        emailMessgeBuilder.name("").name("【有效期】").value(position.getIndate()+"天");

        emailMessgeBuilder.name(email(moseekerPosition));
        emailMessgeBuilder.name("【职位描述】：").value("");
        emailMessgeBuilder.name(describe(moseekerPosition));

        return emailMessgeBuilder.name("").toString();
    }

    public String getText(JSONArray array,String code){
        if(array==null) return "";
        for(int i=0;i<array.size();i++){
            JSONObject obj=array.getJSONObject(i);

            if(obj.getString("code").equals(code)){
                return obj.getString("text");
            }
        }
        return "";
    }
}
