package com.moseeker.position.service.position.base.refresh.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.utils.PositionRefreshUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractOccupationResultHandler<T> extends AbstractJsonResultHandler {
    Logger logger= LoggerFactory.getLogger(AbstractOccupationResultHandler.class);

    //
    protected abstract T buildOccupation(List<String> texts,List<String> codes,Map<String, Integer> newCode,JSONObject msg);
    //持久化数据,需要加上@Transactional注解
    protected abstract void persistent(List<T> data);
    //职位在json中对应的key
    protected abstract String occupationKey();

    protected abstract List<Occupation> toList(JSONObject msg);
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
        List<Occupation> occupationList=splitOccupation(toList(msg));
        logger.info("occupationList:{}",JSON.toJSONString(occupationList));

        //为第三方code生成对应的本地code，作为主键,同时方便查询 第三方code的父code对应的 本地code
        List<String> otherCodes=occupationList.stream().map(o-> o.getCode().get(o.getCode().size()-1)).collect(Collectors.toList());
        Map<String, Integer> newCode=generateNewKey(otherCodes,msg);
        newCode.put("0",0);   //是为了在查询到第一层第三方code(即无父code)时，设置父code为0

        List<T> forInsert=new ArrayList<>();
        for(Occupation o:occupationList){
            List<String> texts=o.getText();
            List<String> codes=o.getCode();
            if(PositionRefreshUtils.notEmptyAndSizeMatch(texts,codes)){
                logger.info("Invalid Occupation: text:{},code:{} ",texts,codes);
                continue;
            }

            T temp=buildOccupation(texts,codes,newCode,msg);
            if(temp!=null){
                forInsert.add(temp);
            }
        }

        logger.info("occupation for persistent : {}", JSON.toJSONString(forInsert));
        //持久化操作
        persistent(forInsert);
    }

    protected Map<String,Integer> generateNewKey(List<String> otherCodes,JSONObject msg) {
        return PositionRefreshUtils.generateNewKey(otherCodes.iterator());
    }


    public List<Occupation> splitOccupation(List<Occupation> occupationList){
        //Map<第几层，Map<第几层职的某个位职能文字,对应生成的code>>

        Set<Occupation> result=new HashSet<>();
        for(int i=0,size=occupationList.size();i<size;i++){
            Occupation o=occupationList.get(i);

            List<String> texts=o.getText();
            List<String> codes=o.getCode();

            if(PositionRefreshUtils.notEmptyAndSizeMatch(texts,codes)){
                logger.info("Invalid Occupation: text:{},code:{} ",texts,codes);
                continue;
            }

            for(int j=codes.size();j>0;j--){
                List<String> splitTexts=texts.subList(0,j);
                List<String> splitCodes=codes.subList(0,j);

                Occupation occupation=new Occupation();
                occupation.setCode(splitCodes);
                occupation.setText(splitTexts);
                result.add(occupation);
            }
        }

        return new ArrayList<>(result);
    }

    public Map<List<String>,String> getOrInitIfNotExist(Map<Integer,Map<List<String>,String>> allLevelOccupation,Integer j){
        if(!allLevelOccupation.containsKey(j)){
            allLevelOccupation.put(j,new HashMap<>());
        }
        return allLevelOccupation.get(j);
    }

    public void generateNewCode(List<Occupation> occupationList){
        generateNewCode(occupationList,1000);
    }

    public void generateNewCode(List<Occupation> occupationList,int seed){
        Map<Integer,Map<List<String>,String>> allLevelOccupation=new HashMap<>();
        for(int i=0;i<occupationList.size();i++) {
            Occupation o=occupationList.get(i);

            List<String> texts=o.getText();
            List<String> codes=o.getCode();

            for (int j = 1; j <= texts.size(); j++) {
                List<String> occupationText = texts.subList(0,j);
                Map<List<String>,String> oneLevelOccupation = getOrInitIfNotExist(allLevelOccupation, j);
                if (!oneLevelOccupation.containsKey(occupationText)) {
                    oneLevelOccupation.put(occupationText, seed + "");
                }
                codes.add(oneLevelOccupation.get(occupationText).toString());
                seed++;
            }
        }
    }

    public static class Occupation {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Occupation that = (Occupation) o;

            if (!text.equals(that.text)) return false;
            return code.equals(that.code);
        }

        @Override
        public int hashCode() {
            int result = text.hashCode();
            result = 31 * result + code.hashCode();
            return result;
        }
    }
}
