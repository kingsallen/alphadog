package com.moseeker.position.service.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.db.dictdb.tables.daos.DictVeryeastOccupationDao;
import com.moseeker.baseorm.db.dictdb.tables.pojos.DictVeryeastOccupation;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VeryEastParamRefresh extends RabbitMQParamRefresh {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    DictCityDao cityDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private DictVeryeastOccupationDao occupationDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Override
    public void send() {
        JSONObject jsonSend=new JSONObject();
        jsonSend.put("channel",getChannel().getValue());

        JSONObject moseekerRegin=new JSONObject();
        for(DictCityDO c:cityDao.getFullCity()){
            moseekerRegin.put("code", Arrays.asList(c.getCode()));

            List<String> chain=cityDao.getMoseekerLevels(c).stream().map(d->d.getName()).collect(Collectors.toList());
            moseekerRegin.put("text",chain);
        }
        jsonSend.put("moseeker_region",moseekerRegin);

        amqpTemplate.send(exchange(),rountineKey(), createMsg(jsonSend.toJSONString()));
    }



    @Override
    public void receiveAndHandle(String json) {
        logger.info("receive json:{}" ,json);

        JSONObject msg= JSON.parseObject(json);




        //处理regionMap
        JSONObject regionMap=msg.getJSONObject("regionMap");



        String appid="";
        String key_identifier="";
//        redisClient.set(appid, key_identifier,msg.getJSONObject("data").getString(.CHAOS_ACCOUNTID),json);
    }

    private void handleOccupation(JSONObject msg){
        //处理occuptation
        List<VeryEastOccupation> occupationList=msg.getJSONArray("occupation").toJavaList(VeryEastOccupation.class);
        logger.info("occupationList:{}",occupationList);




        HashMap<List<String>, Integer> codeToNewCode=new HashMap<>(occupationList.size());

        int newCode=100000;
        for(VeryEastOccupation o:occupationList){
            codeToNewCode.put(o.getCode(),newCode++);
        }

        List<DictVeryeastOccupation> forInsert=new ArrayList<>();
        for(VeryEastOccupation o:occupationList){
            DictVeryeastOccupation temp=new DictVeryeastOccupation();

            List<String> texts=o.getText();
            List<String> codes=o.getCode();

            if(textAndCodeInvalid(texts,codes)){
                logger.info("Invalid VeryEastOccupation: text:{},code:{} ",texts,codes);
                continue;
            }


            temp.setCodeOther(lastCode(codes));
            temp.setCode(codeToNewCode.get(temp.getCodeOther()));
            temp.setLevel((short)codes.size());
            temp.setName(lastName(texts));
            temp.setParentId(codeToNewCode.get(parentCode(codes)));
            temp.setStatus((short)1);

            forInsert.add(temp);
        }


        //事务，删除
        occupationDao.delete();
        occupationDao.insert(forInsert);
    }

    private String lastName(List<String> texts){
        return texts.get(texts.size()-1);
    }

    private int parentCode(List<String> codes){
        if(codes==null || codes.size()<2){
            return 0;
        }
        try {
            return Integer.valueOf(codes.get(codes.size()-2));
        }catch (NumberFormatException e){
            logger.info("parentCode NumberFormatException:{}",codes);
            throw e;
        }

    }
    private int lastCode(List<String> codes){
        try {
            return Integer.valueOf(codes.get(codes.size()-1));
        }catch (NumberFormatException e){
            logger.info("lastCode NumberFormatException:{}",codes);
            throw e;
        }
    }

    private boolean textAndCodeInvalid(List<String > texts,List<String> codes){
        return texts==null || codes==null || texts.isEmpty() || codes.isEmpty() || texts.size() != codes.size();
    }

    @Override
    public String exchange() {
        return "chaos";
    }

    @Override
    public String rountineKey() {
        return "environ.request";
    }

    @Override
    public String receiveQueue() {
        return "environ.response";
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.VERYEAST;
    }

    private class VeryEastOccupation{
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
    }
}
