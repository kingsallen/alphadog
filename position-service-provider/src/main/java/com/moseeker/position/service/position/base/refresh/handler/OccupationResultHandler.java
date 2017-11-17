package com.moseeker.position.service.position.base.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.utils.PositionRefreshUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class OccupationResultHandler<T> extends AbstractJsonResultHandler {
    Logger logger= LoggerFactory.getLogger(OccupationResultHandler.class);
    //
    protected abstract T buildOccupation(List<String> texts,List<String> codes,Map<Integer, Integer> newCode);
    //持久化数据
    protected abstract void persistent(List<T> data);
    //职位在json中对应的key
    protected abstract String occupationKey();
    /**
     * 处理最佳东方职位信息
     * @param msg
     */
    @Override
    public void handle(JSONObject msg) {
        if(!msg.containsKey(occupationKey())){
            logger.info("very east param does not has occupation!");
            return;
        }
        List<Occupation> occupationList=msg.getJSONArray(occupationKey()).toJavaList(Occupation.class);
        logger.info("occupationList:{}",occupationList);

        //为第三方code生成对应的本地code，作为主键,同时方便查询 第三方code的父code对应的 本地code
        List<Integer> cityCodes=occupationList.stream().map(o-> PositionRefreshUtils.lastCode(o.getCode())).collect(Collectors.toList());
        Map<Integer, Integer> newCode= PositionRefreshUtils.generateNewKey(cityCodes.iterator());
        newCode.put(0,0);   //是为了在查询到第一层第三方code(即无父code)时，设置父code为0

        List<T> forInsert=new ArrayList<>();
        for(Occupation o:occupationList){
            List<String> texts=o.getText();
            List<String> codes=o.getCode();
            if(PositionRefreshUtils.notEmptyAndSizeMatch(texts,codes)){
                logger.info("Invalid Occupation: text:{},code:{} ",texts,codes);
                continue;
            }

            T temp=buildOccupation(texts,codes,newCode);
            if(temp!=null){
                forInsert.add(temp);
            }
        }

        logger.info("occupation for persistent : {}",forInsert);
        //持久化操作
        persistent(forInsert);
    }



    private static class Occupation {
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
