package com.moseeker.position.service.position.base.refresh.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.position.utils.PositionParamRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class AbstractRegionResultHandler extends AbstractJsonResultHandler implements IChannelType {
    Logger logger= LoggerFactory.getLogger(AbstractRegionResultHandler.class);

    @Autowired
    DictCityMapDao cityMapDao;

    @Override
    protected void handle(JSONObject msg) {
        //处理regionMap
        JSONObject regionMap=msg.getJSONObject("regionMap");
        int channelValue=getChannelType().getValue();

        //处理不匹配的数据
        List<Region> moseekerUnmatched=regionMap.getJSONArray("moseekerUnmatched").toJavaList(Region.class);
        logger.info("moseekerUnmatched size:{}",moseekerUnmatched==null? "null":moseekerUnmatched.size());
        List<Region> thirdPartyUnmatched=regionMap.getJSONArray("thirdPartyUnmatched").toJavaList(Region.class);
        logger.info("thirdPartyUnmatched size:{}",thirdPartyUnmatched==null? "null":thirdPartyUnmatched.size());

        //处理匹配的数据
        JSONArray matchedDatas=regionMap.getJSONArray("map");
        if(matchedDatas==null) {
            logger.info("matchedDatas:{}", "null");
        }else{
            List<DictCityMapDO> cityMapList=new ArrayList<>();
            String nowTime= FastDateFormat.getDateInstance(FastDateFormat.LONG, Locale.CHINA).format(new Date());
            for(int i=0;i<matchedDatas.size();i++){
                JSONObject data=matchedDatas.getJSONObject(i);
                Region moseekerRegion=data.getJSONObject("moseekerRegion").toJavaObject(Region.class);
                Region thirdPartyRegion=data.getJSONObject("thirdPartyRegion").toJavaObject(Region.class);

                int moseekerCode= PositionParamRefreshUtils.lastCode(moseekerRegion.getCode());

                DictCityMapDO cityMap=new DictCityMapDO();

                cityMap.setCreateTime(nowTime);
                cityMap.setStatus(0);
                cityMap.setCode(moseekerCode);
                cityMap.setChannel(channelValue);
                // 有些渠道cother_other要城市名称，有些要code，所以默认code,要文字可以继承后覆盖
                setCodeOther(cityMap,thirdPartyRegion);

                cityMapList.add(cityMap);
            }

            logger.info("region for insert : {}", JSON.toJSONString(cityMapList));

            int delCount=cityMapDao.delete(new Condition("channel",channelValue));
            logger.info("channel {} delete old region ",channelValue,delCount);
            cityMapDao.addAllData(cityMapList);
            logger.info("channel {} region insert success",channelValue);
        }
    }

    protected void setCodeOther(DictCityMapDO cityMap,Region thirdPartyRegion){
        cityMap.setCodeOther(thirdPartyRegion.codeToString());
    }

    protected static class Region {
        private List<String> text;
        private List<String> code;

        public List<String> getText() {
            return text;
        }

        public void setText(List<String> text) {
            this.text = text;
        }

        public List<String> getCode() {
            return code;
        }

        public void setCode(List<String> code) {
            this.code = code;
        }

        public String codeToString(){
            if(code==null || code.isEmpty()) return "";
            return JSON.toJSONString(code);
        }

        public String textToString(){
            if(text==null || text.isEmpty()) return "";
            return JSON.toJSONString(text);
        }
    }
}
