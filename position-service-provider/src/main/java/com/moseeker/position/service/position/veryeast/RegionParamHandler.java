package com.moseeker.position.service.position.veryeast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.veryeast.Pojo.VeryEastRegion;
import com.moseeker.position.utils.PositionParamUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class RegionParamHandler extends AbstractVeryEastParamHandler {
    Logger logger= LoggerFactory.getLogger(RegionParamHandler.class);

    @Autowired
    DictCityMapDao cityMapDao;

    @Override
    public void handler(JSONObject msg) {
        //处理regionMap
        JSONObject regionMap=msg.getJSONObject("regionMap");


        //处理不匹配的数据
        List<VeryEastRegion> moseekerUnmatched=regionMap.getJSONArray("moseekerUnmatched").toJavaList(VeryEastRegion.class);
        logger.info("moseekerUnmatched:{}",moseekerUnmatched);
        List<VeryEastRegion> thirdPartyUnmatched=regionMap.getJSONArray("thirdPartyUnmatched").toJavaList(VeryEastRegion.class);
        logger.info("thirdPartyUnmatched:{}",thirdPartyUnmatched);

        //处理匹配的数据
        JSONArray matchedDatas=regionMap.getJSONArray("map");
        logger.info("matchedDatas:{}",matchedDatas);

        List<DictCityMapDO> cityMapList=new ArrayList<>();
        String nowTime= FastDateFormat.getDateInstance(FastDateFormat.LONG, Locale.CHINA).format(new Date());
        for(int i=0;i<matchedDatas.size();i++){
            JSONObject data=matchedDatas.getJSONObject(i);
            VeryEastRegion moseekerRegion=data.getJSONObject("moseekerRegion").toJavaObject(VeryEastRegion.class);
            VeryEastRegion thirdPartyRegion=data.getJSONObject("thirdPartyRegion").toJavaObject(VeryEastRegion.class);

            int moseekerCode= PositionParamUtils.lastCode(moseekerRegion.getCode());

            DictCityMapDO cityMap=new DictCityMapDO();

            cityMap.setCreateTime(nowTime);
            cityMap.setCodeOther(thirdPartyRegion.codeToString());
            cityMap.setStatus(0);
            cityMap.setCode(moseekerCode);
            cityMap.setChannel(ChannelType.VERYEAST.getValue());

            cityMapList.add(cityMap);
        }

        logger.info("veryeast occupation for insert : {}",cityMapList);

        int delCount=cityMapDao.delete(new Condition("channel",ChannelType.VERYEAST.getValue()));
        logger.info("veryeast delete old Occupation "+delCount);
        cityMapDao.addAllData(cityMapList);
        logger.info("veryeast insert success");
    }
}
