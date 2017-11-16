package com.moseeker.position.service.position.veryeast;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictVeryEastOccupationDao;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.veryeast.Pojo.VeryEastOccupation;
import com.moseeker.position.utils.PositionParamUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictVeryEastOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OccupationParamHandler extends AbstractVeryEastParamHandler {
    Logger logger= LoggerFactory.getLogger(OccupationParamHandler.class);

    @Autowired
    private DictVeryEastOccupationDao occupationDao;

    /**
     * 处理最佳东方职位信息
     * @param msg
     */
    @Override
    @Transactional
    public void handler(JSONObject msg) {
        if(!msg.containsKey("occupation")){
            logger.info("very east param does not has occupation!");
            return;
        }
        List<VeryEastOccupation> occupationList=msg.getJSONArray("occupation").toJavaList(VeryEastOccupation.class);
        logger.info("occupationList:{}",occupationList);

        //为第三方code生成对应的本地code，作为主键
        List<Integer> cityCodes=occupationList.stream().map(o-> PositionParamUtils.lastCode(o.getCode())).collect(Collectors.toList());
        Map<Integer, Integer> newCode= PositionParamUtils.generateNewKey(cityCodes.iterator());
        newCode.put(0,0);   //查找父城市code时，如果是顶级城市，则父城市code为0

        List<DictVeryEastOccupationDO> forInsert=new ArrayList<>();
        for(VeryEastOccupation o:occupationList){
            DictVeryEastOccupationDO temp=new DictVeryEastOccupationDO();

            List<String> texts=o.getText();
            List<String> codes=o.getCode();

            if(PositionParamUtils.notEmptyAndSizeMatch(texts,codes)){
                logger.info("Invalid VeryEastOccupation: text:{},code:{} ",texts,codes);
                continue;
            }

            temp.setCodeOther(PositionParamUtils.lastCode(codes));
            temp.setCode(newCode.get(temp.getCodeOther()));
            temp.setLevel((short)codes.size());
            temp.setName(PositionParamUtils.lastString(texts));
            temp.setParentId(newCode.get(PositionParamUtils.parentCode(codes)));
            temp.setStatus((short)1);

            forInsert.add(temp);
        }

        logger.info("veryeast occupation for insert : {}",forInsert);

        //全删全插
        Condition condition=new Condition("code",0, ValueOp.NEQ);
        int delCount=occupationDao.delete(condition);
        logger.info("veryeast delete old Occupation "+delCount);
        occupationDao.addAllData(forInsert);
        logger.info("veryeast insert success");
    }
}
