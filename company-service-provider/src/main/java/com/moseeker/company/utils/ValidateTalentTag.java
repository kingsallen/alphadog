package com.moseeker.company.utils;

import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrTalentRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTalentRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolUserTagRecord;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.company.bean.ValidateCommonBean;
import com.moseeker.company.bean.ValidateTagBean;
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
    @Autowired
    private ValidateUtils validateUtils;
    /*
      校验批量添加标签
     */
    public ValidateTagBean validateAddTag(int hrId, Set<Integer> userIdList, Set<Integer> tagIdList, int companyId, int type) {
        ValidateTagBean result = new ValidateTagBean();
        //首先验证该hr是否属于该公司
        int flag = talentPoolEntity.validateHr(hrId, companyId);
        if (flag == 0) {
            result.setStatus(1);
            result.setErrorMessage("该hr不属于该company_id");
            return result;
        }

        ValidateCommonBean validateResult = this.validateUserIdTag(hrId, userIdList, tagIdList, companyId);
        Set<Integer> idList =validateResult.getUseId();
        if (StringUtils.isEmptySet(idList)) {
            result.setStatus(1);
            result.setErrorMessage("该hr无权操作这些人才");
            return result;
        }
        ValidateTagBean validateTag = this.validateTag(idList, hrId);
        int flagTag = validateTag.getStatus();
        if (flagTag==1) {
            result.setStatus(1);
            result.setErrorMessage("不满足操作条件");
            return result;
        }
        List<Map<String, Object>> hrTagList =validateTag.getHrTagList();
        Set<Integer> userTagIdList =validateTag.getUserTagIdList();

        //如果tagIdList是空，那么则代表删除原有的tag
        if (!StringUtils.isEmptySet(tagIdList)) {
            boolean validateOperTag = this.validateAddOperatorTag(this.getIdByTagList(hrTagList), tagIdList, userTagIdList, type);
            if (!validateOperTag) {
                result.setStatus(1);
                result.setErrorMessage("操作的标签不是hr定义的标签");
                return result;
            }
        }
        result.setHrTagList(hrTagList);
        result.setIdList(idList);
        result.setNouseList(validateResult.getUnuseId());
        result.setUserTagIdList(userTagIdList);
        return result;
    }

    public ValidateCommonBean validateUserIdTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId){
        ValidateCommonBean  result=new ValidateCommonBean();
        ValidateCommonBean  validatBean=validateUtils.handlerApplierId(hrId,userIdList,companyId);
        Set<Integer> unUseList=validatBean.getUnuseId();
        Set<Integer> useIdList=validatBean.getUseId();

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
            ValidateCommonBean validateData=this.filterNoPowerUserId(unUseList,companyId);
            unUseList=validateData.getUnuseId();
            pubTalentIdList=validateData.getUseId();
        }
        /*
         添加unuserId是否是该hr收藏的上传简历
         */
        Set<Integer> uploadTalentIdList=new HashSet<>();
        if(!StringUtils.isEmptySet(unUseList)){
            ValidateCommonBean uploadValidate=filterHrUpload(talentPoolEntity.converSetToList(unUseList),hrId,companyId);
            uploadTalentIdList=uploadValidate.getUseId();
            unUseList=uploadValidate.getUnuseId();
        }

        if(!StringUtils.isEmptySet(pubTalentIdList)){
            if(StringUtils.isEmptySet(applierIdList)){
                applierIdList=pubTalentIdList;
            }else{
                applierIdList.addAll(pubTalentIdList);
            }
        }
        if(!StringUtils.isEmptySet(uploadTalentIdList)){
            if(StringUtils.isEmptySet(applierIdList)){
                applierIdList=uploadTalentIdList;
            }else{
                applierIdList.addAll(uploadTalentIdList);
            }
        }
        if(!StringUtils.isEmptySet(unUseList)||StringUtils.isEmptySet(applierIdList)){
            int count=talentPoolEntity.valiadteMainAccount(hrId,companyId);
            if(count>0){
                if(!StringUtils.isEmptySet(unUseList)){
                    List<TalentpoolTalentRecord> talentCompanyList=talentPoolEntity.getTalentByCompanyIdUserSet(companyId,unUseList);
                    Set<Integer> talentUserIdList=talentPoolEntity.getUserIdListByTalentpoolTalent(talentCompanyList);
                    if(!StringUtils.isEmptySet(talentUserIdList)){
                        Set<Integer> finalUnSerIdList=new HashSet<>();
                        for(Integer itemId:unUseList){
                            if(!talentUserIdList.contains(itemId)){
                                finalUnSerIdList.add(itemId);
                            }
                        }
                        unUseList=finalUnSerIdList;
                        if(StringUtils.isEmptySet(applierIdList)){
                            applierIdList=talentUserIdList;
                        }else{
                            applierIdList.addAll(talentUserIdList);
                        }

                    }
                }
                //查询不符合的人是不是公司的收藏的
                if(StringUtils.isEmptySet(applierIdList)){
                    List<TalentpoolTalentRecord> talentCompanyList=talentPoolEntity.getTalentByCompanyIdUserSet(companyId,useIdList);
                    Set<Integer> talentUserIdList=talentPoolEntity.getUserIdListByTalentpoolTalent(talentCompanyList);
                    if(!StringUtils.isEmptySet(talentUserIdList)){
                        Set<Integer> finalUnSerIdList=new HashSet<>();
                        for(Integer itemId:unUseList){
                            if(!talentUserIdList.contains(itemId)){
                                finalUnSerIdList.add(itemId);
                            }
                        }
                        if(StringUtils.isEmptySet(unUseList)){
                            unUseList=finalUnSerIdList;
                        }else{
                            unUseList.addAll(finalUnSerIdList);
                        }
                        applierIdList=talentUserIdList;
                    }
                }
            }
        }
        result.setUnuseId(unUseList);
        result.setUseId(applierIdList);
        return result;
    }
    /*
     添加unuserId是否是该hr收藏的上传简历
     */
    private ValidateCommonBean filterHrUpload(List<Integer> unUseList,int hrId,int companyId){
        ValidateCommonBean bean=new ValidateCommonBean();
        int count=talentPoolEntity.valiadteMainAccount(hrId,companyId);
        Set<Integer> userIdList=new HashSet<>();
        Set<Integer> unUserIdList=new HashSet<>();
        if(count>0){
            List<TalentpoolTalentRecord> list=talentPoolEntity.getTalentByUserIdAndCompanyUpload(unUseList,companyId);
            if(StringUtils.isEmptyList(list)){
                bean.setUnuseId(talentPoolEntity.converListToSet(unUseList));
            }else{
                for(Integer userId:unUseList ){
                    for(TalentpoolTalentRecord record:list){
                        int id=record.getUserId();
                        if(id==userId){
                            userIdList.add(userId);
                            break;
                        }
                    }
                    unUserIdList.add(userId);
                }

            }
            bean.setUnuseId(unUserIdList);
            bean.setUseId(userIdList);
        }else{
            List<TalentpoolHrTalentRecord> list=talentPoolEntity.getTalentByUserIdAndHrId(unUseList,hrId);
            if(StringUtils.isEmptyList(list)){
                bean.setUnuseId(talentPoolEntity.converListToSet(unUseList));
            }else{
                for(Integer userId:unUseList ){
                    for(TalentpoolHrTalentRecord record:list){
                        int id=record.getUserId();
                        if(id==userId){
                            userIdList.add(userId);
                            break;
                        }
                    }
                    unUserIdList.add(userId);
                }
                bean.setUnuseId(unUserIdList);
                bean.setUseId(userIdList);

            }
        }
        return bean;
    }
    /*
    判断是否可以打标签
    */
    public ValidateTagBean validateTag(Set<Integer> userIdList,int hrId){
        ValidateTagBean result=new ValidateTagBean();
        //获取hr下所有的tag
        List<Map<String,Object>> hrTagList=talentPoolEntity.getTagByHr(hrId,0,Integer.MAX_VALUE);
        //获取hr下所有的tagId
        Set<Integer> hrTagIdList=this.getIdByTagList(hrTagList);
        if(StringUtils.isEmptySet(hrTagIdList)){
            result.setStatus(1);
            return result;
        }
        Set<Integer> tagIdList=this.getUserTagIdList(userIdList.iterator().next(),hrTagIdList);
        if(StringUtils.isEmptySet(tagIdList)){
            List<TalentpoolUserTagRecord> allTagList=talentPoolEntity.getUserTagByUserIdListAndTagId(userIdList,hrTagIdList);
            if(StringUtils.isEmptyList(allTagList)){
                result.setStatus(0);
                result.setHrTagList(hrTagList);
                result.setUserTagIdList(null);
                return result;
            }else{
                result.setStatus(1);
                return result;
            }

        }
        List<TalentpoolUserTagRecord> tagList=talentPoolEntity.getUserTagByUserIdListAndTagId(userIdList,tagIdList);
        if(StringUtils.isEmptyList(tagList)){
            result.setStatus(1);
            return result;
        }
        if(userIdList.size()*tagIdList.size()!=tagList.size()){
            result.setStatus(1);
            return result;
        }
        for(TalentpoolUserTagRecord record:tagList){
            int tagId=record.getTagId();
            if(!tagIdList.contains(tagId)){
                result.setStatus(1);
                return result;
            }
        }
        result.setStatus(0);
        result.setHrTagList(hrTagList);
        result.setUserTagIdList(tagIdList);
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

    /*
     处理批量处理标签的公开人才部分的校验
    */
    public ValidateCommonBean filterNoPowerUserId(Set<Integer> userIdList,int companyId){

        ValidateCommonBean bean=new ValidateCommonBean();
        if(StringUtils.isEmptySet(userIdList)){
            return null;
        }
        Map<String,Object> result=new HashMap<>();
        List<TalentpoolTalentRecord> talentList=talentPoolEntity.getTalentpoolTalentByCompanyId(companyId);
        Set<Integer> talentIdList=talentPoolEntity.getUserIdListByTalentpoolTalent(talentList);
        if(StringUtils.isEmptySet(talentIdList)){
            bean.setUnuseId(userIdList);
            bean.setUseId(null);
        }else {
            Set<Integer> noUseIdList = new HashSet<>();
            Set<Integer> publicUserIdList=new HashSet<>();
            for (Integer userId : userIdList) {
                if (!talentIdList.contains(userId)) {
                    noUseIdList.add(userId);
                }else{
                    publicUserIdList.add(userId);
                }
            }

            bean.setUnuseId(noUseIdList);
            bean.setUseId(publicUserIdList);
        }
        return bean;
    }
}
