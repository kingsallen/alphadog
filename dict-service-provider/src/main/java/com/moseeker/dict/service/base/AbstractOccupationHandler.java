package com.moseeker.dict.service.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractOccupationHandler<T> {
    Logger logger= LoggerFactory.getLogger(AbstractOccupationHandler.class);

    private static final String PARENT_ID_CODE="parent_id_code";
    private static final String CHILDREN="children";
    private static final String CODE="code";
    private static final String CODE_OTHER="code_other";

    //获取职位
    protected abstract List<T> getAllOccupation();
    protected abstract List<T> getSingleOccupation(JSONObject obj);
    //把职位转换成JsonObect
    protected abstract JSONObject toJsonObject(T occupation);
    //职位表中作为parentId的参数名称
    protected abstract String parentKeyName();

    //在放入结果集中做一些特殊操作
    protected void doBeforeAddResult(JSONObject son,JSONObject father){
           if(father==null){
               son.put(PARENT_ID_CODE,0);
           }else{
               son.put(PARENT_ID_CODE,father.get(CODE_OTHER));
           }
    }

    //获取单一职位
    public List<JSONObject> getSingle(JSONObject obj){
        List<JSONObject> allData=new ArrayList<>();
        List<T> list = getSingleOccupation(obj);
        if (list != null && list.size() > 0) {
            list.forEach(r -> {
                allData.add(toJsonObject(r));
            });
        }
        return allData;
    }

    public JSONArray getAll() throws BIZException {
        List<T> list=getAllOccupation();
        if(list==null || list.isEmpty()){
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"empty occupation");
        }
        logger.info("get all occupation size :"+list.size());
        //职位list转换成JsonObject类型List
        List<JSONObject> occupations=list.stream().map(o-> toJsonObject(o) ).collect(Collectors.toList());

        //key：职位id, val：父id等于key的职位
        Map<Integer,List<JSONObject>> map=generateMap(occupations);
        if(map==null||map.isEmpty()){
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"empty parentId map");
        }
        logger.info("generate parentId Map :"+map.size());

        JSONArray result=new JSONArray();
        //遍历第一层职位
        occupations.stream().filter(o->o.getIntValue(parentKeyName())==0).forEach(o->{
            doBeforeAddResult(o,null);
            result.add(createChildren(o,map));
        });

        logger.info("handle result {}",result);
        return result;
    }

    //根据职位生成一个特殊的map(key：职位id, val：父id等于key的职位)
    protected Map<Integer,List<JSONObject>> generateMap(List<JSONObject> occupations){
        Map<Integer,List<JSONObject>> map=new HashMap<>();
        for(JSONObject o:occupations){
            int parentId=o.getIntValue(parentKeyName());
            if(parentId!=0){
                List<JSONObject> val=getOrCreateIfNotExist(map,parentId);
                val.add(o);
                map.put(parentId,val);
            }
        }
        return map;
    }

    protected List<JSONObject> getOrCreateIfNotExist(Map<Integer,List<JSONObject>> map,int key){
        if(!map.containsKey(key)){
            return new ArrayList<>();
        }else{
            return map.get(key);
        }
    }

    /**
     * 递归查找子职能
     * @param obj 需要查找的职位
     * @param map key：parentID,val：父id等于key的职位
     * @return
     */
    protected JSONObject createChildren(JSONObject obj, Map<Integer,List<JSONObject>> map){
        Integer fatherCode=obj.getIntValue(CODE);

        if(map.containsKey(fatherCode)){
            List<JSONObject> sonList=map.remove(fatherCode);

            for(JSONObject son:sonList){
                createChildren(son,map);
            }
            sonList.forEach(o->doBeforeAddResult(o,obj));
            obj.put(CHILDREN,sonList);
        }

        return obj;
    }
}
