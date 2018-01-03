package com.moseeker.company.utils;

import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolUserTagRecord;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.TalentPoolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zztaiwll on 17/12/29.
 */
@Service
public class ValidateTalentTag {
    @Autowired
    private TalentPoolEntity talentPoolEntity;
    /*
      校验批量添加标签
     */
    public Map<String,Object> validateAddTag(int hrId, Set<Integer> userIdList, Set<Integer> tagIdList, int companyId, int type) {
        Map<String, Object> result = new HashMap<>();
        int flag = talentPoolEntity.validateHr(hrId, companyId);
        if (flag == 0) {
            result.put("result", ResponseUtils.fail(1, "该hr不属于该company_id"));
            return result;
        }
        Map<String, Object> validateResult = this.validateUserIdTag(hrId, userIdList, tagIdList, companyId);
        Set<Integer> idList = (Set<Integer>) validateResult.get("use");
        if (StringUtils.isEmptySet(idList)) {
            result.put("result", ResponseUtils.fail(1, "该hr无权操作这些人才"));
            return result;
        }
        Map<String, Object> validateTag = this.validateTag(idList, hrId);
        boolean flagTag = (boolean) validateTag.get("result");
        if (!flagTag) {
            result.put("result", ResponseUtils.fail(1, "不满足操作条件"));
            return result;
        }

        List<Map<String, Object>> hrTagList = (List<Map<String, Object>>) validateTag.get("hrTagList");
        Set<Integer> userTagIdList = (Set<Integer>) validateTag.get("userTagIdList");

        //如果tagIdList是空，那么则代表删除原有的tag
        if (!StringUtils.isEmptySet(tagIdList)) {
            boolean validateOperTag = this.validateAddOperatorTag(this.getIdByTagList(hrTagList), tagIdList, userTagIdList, type);
            if (!validateOperTag) {
                result.put("result", ResponseUtils.fail(1, "操作的标签不是hr定义的标签"));
                return result;
            }
        }
        result.put("hrTagList", hrTagList);
        result.put("userIdList", idList);
        result.put("userTagIdList", userTagIdList);
        result.put("nouseList", (Set<Integer>) validateResult.get("nouse"));
        return result;
    }

    public Map<String,Object> validateUserIdTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId){
        Map<String,Object> validateMap=talentPoolEntity.handlerApplierId(hrId,userIdList,companyId);
        Set<Integer> unUseList= (Set<Integer>) validateMap.get("unuse");
        Set<Integer> useIdList= (Set<Integer>) validateMap.get("use");
        //获取已经收藏的简历
        List<Map<String,Object>> talentList=talentPoolEntity.getTalentpoolHrTalentByIdList(hrId,useIdList);
        //获取被收藏的user_id
        Set<Integer> applierIdList=talentPoolEntity.getIdListByTalentpoolHrTalentList(talentList);
        //获取投递了该hr但是未被收藏的人的id
        Set<Integer> idList=talentPoolEntity.filterIdList(useIdList,applierIdList);
        if(!StringUtils.isEmptySet(idList)){
            unUseList.addAll(idList);
        }
        Set<Integer> pubTalentIdList=new HashSet<>();
        if(!StringUtils.isEmptySet(unUseList)){
            Map<String,Object> validateData=talentPoolEntity.filterNoPowerUserId(unUseList,companyId);
            unUseList=(Set<Integer>) validateData.get("unuse");
            pubTalentIdList= (Set<Integer>) validateData.get("use");
        }
        if(!StringUtils.isEmptySet(pubTalentIdList)){
            if(StringUtils.isEmptySet(applierIdList)){
                applierIdList=pubTalentIdList;
            }else{
                applierIdList.addAll(pubTalentIdList);
            }
        }
        Map<String,Object> result=new HashMap<>();
        result.put("nouse",unUseList);
        result.put("use",applierIdList);
        return result;
    }
    /*
    判断是否可以打标签
    */
    public Map<String,Object> validateTag(Set<Integer> userIdList,int hrId){
        Map<String,Object> result=new HashMap<>();
        //获取hr下所有的tag
        List<Map<String,Object>> hrTagList=talentPoolEntity.getTagByHr(hrId,0,Integer.MAX_VALUE);
        //获取hr下所有的tagId
        Set<Integer> hrTagIdList=this.getIdByTagList(hrTagList);
        if(StringUtils.isEmptySet(hrTagIdList)){
            result.put("result",false);
            return result;
        }
        Set<Integer> tagIdList=this.getUserTagIdList(userIdList.iterator().next(),hrTagIdList);
        if(StringUtils.isEmptySet(tagIdList)){
            List<TalentpoolUserTagRecord> allTagList=talentPoolEntity.getUserTagByUserIdListAndTagId(userIdList,hrTagIdList);
            if(StringUtils.isEmptyList(allTagList)){
                result.put("result",true);
                result.put("hrTagList",hrTagList);
                result.put("userTagIdList",null);
                return result;
            }else{
                result.put("result",false);
                return result;
            }

        }
        List<TalentpoolUserTagRecord> tagList=talentPoolEntity.getUserTagByUserIdListAndTagId(userIdList,tagIdList);
        if(StringUtils.isEmptyList(tagList)){
            result.put("result",false);
            return result;
        }
        if(userIdList.size()*tagIdList.size()!=tagList.size()){
            result.put("result",false);
            return result;
        }
        for(TalentpoolUserTagRecord record:tagList){
            int tagId=record.getTagId();
            if(!tagIdList.contains(tagId)){
                result.put("result",false);
                return result;
            }
        }
        result.put("result",true);
        result.put("hrTagList",hrTagList);
        result.put("userTagIdList",tagIdList);
        return result;
    }

    /*
    验证添加标签是否符合操作条件
    */
    private boolean validateAddOperatorTag(Set<Integer> hrTagIdList,Set<Integer> tagIdList, Set<Integer> userTagIdList,int type){
        if(StringUtils.isEmptySet(hrTagIdList)){
            return false;
        }
        for(Integer id:tagIdList){
            if(!hrTagIdList.contains(id)) {
                return false;
            }
        }
        if(type==0){
            if(!StringUtils.isEmptySet(userTagIdList)){
                for(Integer id:tagIdList){
                    if(userTagIdList.contains(id)){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /*
    获取TalentpoolTagRecord 集合的id
    */
    public Set<Integer> getIdByTagList(List<Map<String,Object>> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        Set<Integer> result=new HashSet<>();
        for(Map<String,Object> record:list){
            result.add((int)record.get("id"));
        }
        return result;
    }
    /*
   获取任意一个人才在这家公司下的所有的标签的id
   */
    public Set<Integer> getUserTagIdList(int userId,Set<Integer> tagIdList){
        List<TalentpoolUserTagRecord> userTagList=talentPoolEntity.getUserTagByUserIdAndTagId(userId,tagIdList);
        Set<Integer> idList=this.getTagIdByUserTagList(userTagList);
        return idList;
    }

    /*
    获取TalentpoolTagRecord 集合的id
    */
    private Set<Integer> getTagIdByUserTagList(List<TalentpoolUserTagRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        Set<Integer> result=new HashSet<>();
        for(TalentpoolUserTagRecord record:list){
            result.add(record.getTagId());
        }
        return result;
    }
}
