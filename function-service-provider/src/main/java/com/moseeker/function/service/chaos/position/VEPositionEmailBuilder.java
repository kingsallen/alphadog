package com.moseeker.function.service.chaos.position;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.function.service.PositionEmailBuilder;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyVeryEastPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class VEPositionEmailBuilder implements PositionEmailBuilder<ThirdpartyVeryEastPositionDO> {
    Logger logger= LoggerFactory.getLogger(VEPositionEmailBuilder.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Override
    public ChannelType getChannelType() {
        return ChannelType.VERYEAST;
    }

    @Override
    public Map<String, String> message(ThirdpartyVeryEastPositionDO position) {
        Map<String, String> map=new HashMap<>();

        String json=redisClient.get(RefreshConstant.APP_ID,RefreshConstant.VERY_EAST_REDIS_PARAM_KEY,"");

        JSONObject obj=JSONObject.parseObject(json);

        JSONArray accommodations=obj.getJSONArray("accommodation");
        JSONArray computerLevels=obj.getJSONArray("computerLevel");
        JSONArray languageTypes=obj.getJSONArray("languageType");
        JSONArray languageLevels=obj.getJSONArray("languageLevel");


        map.put("【提供食宿】",getText(accommodations,position.getAccommodation()+""));
        map.put("【年龄要求】",position.getAge_bottom()+"至"+position.getAge_top()+"岁");
        map.put("【语言能力1】",getText(languageTypes,position.getLanguageType1()+"")+"，掌握程度："+getText(languageLevels,position.getLanguageLevel1()+"")+divider);
        map.put("【语言能力2】",getText(languageTypes,position.getLanguageType2()+"")+"，掌握程度："+getText(languageLevels,position.getLanguageLevel2()+"")+divider);
        map.put("【语言能力3】",getText(languageTypes,position.getLanguageType3()+"")+"，掌握程度："+getText(languageLevels,position.getLanguageLevel3()+"")+divider);
        map.put("【计算机能力】",getText(computerLevels,position.getComputerLevel()+""));
        map.put("【有效期】",position.getIndate()+"天");

        return map;
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
