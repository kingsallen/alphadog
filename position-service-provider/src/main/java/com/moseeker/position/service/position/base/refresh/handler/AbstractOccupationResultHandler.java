package com.moseeker.position.service.position.base.refresh.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.position.utils.PositionEmailNotification;
import com.moseeker.position.utils.PositionParamRefreshUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractOccupationResultHandler<T> extends AbstractJsonResultHandler implements IChannelType {
    Logger logger= LoggerFactory.getLogger(AbstractOccupationResultHandler.class);

    @Autowired
    private PositionEmailNotification emailNotification;

    /**
     * 创建occupation,每个渠道根据texts,codes,额外信息msg,来构建自己的occupation实体
     * @param texts 职能文字链
     * @param codes 职能code链
     * @param newCode   用来查找parent_id
     * @param msg   一些额外信息，一览需要subsite
     * @return
     */
    protected abstract T buildOccupation(List<String> texts,List<String> codes,Map<String, Integer> newCode,JSONObject msg);

    /**
     * 持久化数据,需要加上@Transactional注解!!!
     * @param data  持久化的list
     */
    protected abstract void persistent(List<T> data);

    /**
     * 获取所有职能，为了和新插入职能做对比
     * @return
     */
    protected abstract List<T> getAll();

    /**
     * 新旧职能对比，某几个字段对比相同
     * @param oldData   数据库职能
     * @param newData   新推送职能
     * @return
     */
    protected abstract boolean equals(T oldData, T newData);

    /**
     * 处理职位信息
     * @param msg
     */
    @Override
    public void handle(JSONObject msg) {
        if(!msg.containsKey(occupationKey())){
            logger.info("{} param does not has occupation!",getChannelType().getAlias());
            return;
        }
        //把json中的职能转换成List<Occupation>
        List<Occupation> occupationsToBeSplit=toList(msg);
        //分割list，即分出1层有哪些，2层有哪些，3层有哪些。。。
        //比如传来的 IT,程序员，Java程序员-->IT/IT,程序员/IT,程序员，Java程序员
        List<Occupation> occupationList=splitOccupation(occupationsToBeSplit);
        logger.info("occupationList:{}",JSON.toJSONString(occupationList));

        //为第三方code生成对应的本地code，作为主键,同时方便查询 第三方code的父code对应的 本地code
        List<String> otherCodes=occupationList.stream().map(o-> o.getCode().get(o.getCode().size()-1)).collect(Collectors.toList());
        Map<String, Integer> newCode=generateNewKey(otherCodes,msg);
        newCode.put("0",0);   //是为了在查询到第一层第三方code(即无父code)时，设置父code为0

        List<T> forInsert=new ArrayList<>();
        for(Occupation o:occupationList){
            List<String> texts=o.getText();
            List<String> codes=o.getCode();
            if(PositionParamRefreshUtils.notEmptyAndSizeMatch(texts,codes)){
                logger.info("Invalid Occupation: text:{},code:{} ",texts,codes);
                continue;
            }

            T temp=buildOccupation(texts,codes,newCode,msg);
            if(temp!=null){
                forInsert.add(temp);
            }
        }

        //检测是否有变化
        changeCheck(forInsert);

        logger.info("occupation for persistent : {}", JSON.toJSONString(forInsert));
        //持久化操作
        persistent(forInsert);
    }


    /**
     * 检测职位是否有变化
     * @param newDatas  要入库的数据
     */
    protected void changeCheck( List<T> newDatas) {
        List<T> oldDatas=getAll();  //数据库数据

        List<T> notInOldDatas=new ArrayList<>();
        List<T> notInNewDatas=new ArrayList<>();

        //查找出数据库存在，但本次刷新不存在，即本次删除的数据
        out:for(T oldData:oldDatas){

            for(T newData:newDatas){
                if(equals(oldData,newData)){
                    continue out;
                }
            }
            notInNewDatas.add(oldData);
        }

        //查找出数据库不存在，但本次刷新存在，即本次新增的数据
        out:for(T newData:newDatas){

            for(T oldData:oldDatas){
                if(equals(oldData,newData)){
                    continue out;
                }
            }
            notInOldDatas.add(newData);
        }

        if(!notInNewDatas.isEmpty()|| !notInOldDatas.isEmpty()){
            emailNotification.sendUnMatchOccupationMail(notInOldDatas,notInNewDatas,getChannelType());
        }
    }

    //职位在json中对应的key

    /**
     * 获取environ中职能的key名称
     * @return environ中职能的key名称
     */
    protected String occupationKey(){
        return "occupation";
    }

    /**
     * 将msg中的occupation转成List<Occupation>类
     * @param msg   完整的environ的json数据
     * @return
     */
    protected List<Occupation> toList(JSONObject msg){
        return msg.getJSONArray(occupationKey()).toJavaList(Occupation.class);
    }

    /**
     * 为第三方code生成对应的本地code，作为主键,同时方便查询 第三方code的父code对应的 本地code
     * @param otherCodes
     * @param msg
     * @return
     */
    protected Map<String,Integer> generateNewKey(List<String> otherCodes,JSONObject msg) {
        return PositionParamRefreshUtils.generateNewKey(otherCodes.iterator());
    }


    /**
     * 分割list，即分出1层有哪些，2层有哪些，3层有哪些。。。
     * 比如传来的 IT,程序员，Java程序员-->IT/IT,程序员/IT,程序员，Java程序员
     * @param occupationList 职能列表
     * @return
     */
    protected List<Occupation> splitOccupation(List<Occupation> occupationList){
        //Map<第几层，Map<第几层职的某个位职能文字,对应生成的code>>

        Set<Occupation> result=new HashSet<>();
        for(int i=0,size=occupationList.size();i<size;i++){
            Occupation o=occupationList.get(i);

            List<String> texts=o.getText();
            List<String> codes=o.getCode();

            if(PositionParamRefreshUtils.notEmptyAndSizeMatch(texts,codes)){
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

    private Map<List<String>,String> getOrInitIfNotExist(Map<Integer,Map<List<String>,String>> allLevelOccupation,Integer j){
        if(!allLevelOccupation.containsKey(j)){
            allLevelOccupation.put(j,new HashMap<>());
        }
        return allLevelOccupation.get(j);
    }

    protected void generateNewCode(List<Occupation> occupationList,String... seeds){

        try {
            String code="";
            MessageDigest digest = MessageDigest.getInstance("MD5");
            Map<Integer,Map<List<String>,String>> allLevelOccupation=new HashMap<>();
            for(int i=0;i<occupationList.size();i++) {
                Occupation o=occupationList.get(i);

                List<String> texts=o.getText();
                List<String> codes=o.getCode();

                for (int j = 1; j <= texts.size(); j++) {
                    List<String> occupationText = texts.subList(0,j);
                    Map<List<String>,String> oneLevelOccupation = getOrInitIfNotExist(allLevelOccupation, j);
                    if (!oneLevelOccupation.containsKey(occupationText)) {
                        code=md5(digest,concatKey(occupationText,seeds));
                        oneLevelOccupation.put(occupationText, code);
                    }
                    codes.add(oneLevelOccupation.get(occupationText).toString());

                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * 拼接需要加密的key
     * @param texts
     * @param seeds
     * @return
     */
    private String concatKey(List<String> texts,String...seeds){
        StringBuilder key=new StringBuilder();
        for (String text:texts){
            key.append("_"+text);
        }
        for(String seed:seeds){
            key.append("_"+seed);
        }
        key.delete(0,1);
        return key.toString();
    }

    private String md5(MessageDigest digest,String str) throws NoSuchAlgorithmException {
        digest.reset();
        StringBuilder md5=new StringBuilder();
        digest.update(str.getBytes());
        byte strDigest[] = digest.digest();
        for (int i = 0; i < strDigest.length; i++) {
            String param = Integer.toString((strDigest[i] & 0xff) + 0x100, 16);
            md5.append(param.substring(1));
        }
        return md5.toString();
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
