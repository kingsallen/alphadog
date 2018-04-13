package com.moseeker.company.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolPast;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.Category;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.company.bean.TalentTagPOJO;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.company.service.impl.CompanyTagService;
import com.moseeker.company.service.impl.TalentPoolService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolServices;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/7.
 */
@Service
public class TalentpoolThriftServiceImpl implements TalentpoolServices.Iface {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public TalentpoolThriftServiceImpl(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @Autowired
    private TalentPoolService talentPoolService;

    @Override
    public Response upsertTalentPoolApp(int hrId, int companyId,int type) throws BIZException, TException {
        try{
            Response result=talentPoolService.upsertTalentPoolApplication(hrId,companyId,type);
            return result;
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getTalentAllComment(int hr_id, int company_id, int user_id,int page_number,int page_size) throws BIZException, TException {
        try{
            return talentPoolService.getAllTalentComment(hr_id,company_id,user_id,page_number,page_size);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getHrTag(int hr_id, int company_id, int page_number, int page_size) throws BIZException, TException {
        try{
            Map<String,Object> result=talentPoolService.getAllHrTag(hr_id,company_id,page_number,page_size);
            if(result.get("flag")!=null){
                return ResponseUtils.fail(1,"该hr不属于该company_id");
            }else{
                String res= JSON.toJSONString(result,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
                return ResponseUtils.successWithoutStringify(res);
            }
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchAddTalent(int hr_id, List<Integer> user_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.batchAddTalent(hr_id,ConvertListToSet(user_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response batchCancelTalent(int hr_id, List<Integer> user_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.batchCancelTalent(hr_id,ConvertListToSet(user_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response hrAddTag(int hr_id, int company_id, String name) throws BIZException, TException {
        try{
            return talentPoolService.addHrTag(hr_id,company_id,name);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response hrDelTag(int hr_id, int company_id, int tag_id) throws BIZException, TException {
        try{
            return talentPoolService.deleteHrTag(hr_id,company_id,tag_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response hrUpdateTag(int hr_id, int company_id, int tag_id, String name) throws BIZException, TException {
        try{
            return talentPoolService.updateHrTag(hr_id,company_id,tag_id,name);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchCancleTalentTag(int hr_id, List<Integer> user_ids, List<Integer> tag_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.batchCancelTalentTag(hr_id,ConvertListToSet(user_ids),ConvertListToSet(tag_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchAddTalentTag(int hr_id, List<Integer> user_ids, List<Integer> tag_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.addBatchTalentTag(hr_id,ConvertListToSet(user_ids),ConvertListToSet(tag_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response hrAddComment(int hr_id, int company_id, int user_id, String content) throws BIZException, TException {
        try{
            return talentPoolService.addTalentComment(hr_id,company_id,user_id,content);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }


    @Override
    public Response hrDelComment(int hr_id, int company_id, int comment_id) throws BIZException, TException {
        try{
            return talentPoolService.delTalentComment(hr_id,company_id,comment_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchAddPublicTalent(int hr_id, int company_id, List<Integer> user_ids) throws BIZException, TException {
        try{
            return talentPoolService.AddbatchPublicTalent(hr_id,company_id,ConvertListToSet(user_ids));
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response batchCancelPublicTalent(int hr_id, int company_id, List<Integer> user_ids) throws BIZException, TException {
        try{
            return talentPoolService.cancelBatchPublicTalent(hr_id,company_id,ConvertListToSet(user_ids));
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }    }

    @Override
    public Response getCompanyPulicTalent(int hr_id, int company_id, int page_number, int page_size) throws BIZException, TException {
        try{
            return talentPoolService.getCompanyPublic(hr_id,company_id,page_number,page_size);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response batchNewAddTalentTag(int hr_id, List<Integer> user_ids, List<Integer> tag_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.addNewBatchTalentTag(hr_id,ConvertListToSet(user_ids),ConvertListToSet(tag_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getTalentStat(int hr_id, int company_id, int type) throws BIZException, TException {
        try{
            return talentPoolService.getTalentState(hr_id,company_id,type);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getCompanyUserPublic(int hr_id, int company_id, int user_id) throws BIZException, TException {
        try{
            return talentPoolService.getCompanyUserPublic(hr_id,company_id,user_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getCompanyTalent(int hr_id, int company_id, int user_id) throws BIZException, TException {
        try{
            return talentPoolService.getCompanyTalent(hr_id,company_id,user_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getHrUserTag(int hr_id, int company_id, int user_id) throws BIZException, TException {
        try{
            return talentPoolService.getHrUserTag(hr_id,company_id,user_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getUserOrigin(int hr_id, int company_id, int user_id) throws BIZException, TException {
        try{
            return talentPoolService.getUserOrigin(hr_id,company_id,user_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getTalentAndPublicHr(int hr_id, int company_id, List<Integer> user_ids) throws BIZException, TException {
        try{
            return talentPoolService.getPublicAndHrTalentByUserIdList(hr_id,company_id,this.ConvertListToSet(user_ids));
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getPositionOrCompanyPast(int company_id, int type, int flag) throws BIZException, TException {
        try{
            List<TalentpoolPast> list=talentPoolService.getPastPositionOrCompany(company_id,type,flag);
            String res= JSON.toJSONString(list,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
            return ResponseUtils.successWithoutStringify(res);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }

    }

    @Override
    public Response addPositionOrCompanyPast(int company_id, int type, int flag, String name) throws BIZException, TException {
        try{
            int result=talentPoolService.addPastPositionOrCompany(company_id,type,flag,name);
            if(result==-1){
                return ResponseUtils.fail(1,"职务或公司名称不能为空");
            }
            return ResponseUtils.success(result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getCompanyTagList(int hr_id, int company_id, int page_number, int page_size) throws BIZException, TException {
        try{
            return talentPoolService.getCompanyTagList(hr_id,company_id,page_number, page_size);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getTalentTagList(int hr_id, int company_id, int page_number, int page_size) throws BIZException, TException {
        try{
            TalentTagPOJO pojo=talentPoolService.getTalentTagByPage(hr_id,company_id,page_number,page_size);
            if(pojo.getFlag()==1){
                return ResponseUtils.fail(1,"该hr不属于该company_id");
            }
            String res= JSON.toJSONString(pojo,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
            return ResponseUtils.successWithoutStringify(res);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public void handlerCompanyTagAndProfile(Set<Integer> userid_list, int company_id) throws BIZException, TException {
        try{
            talentPoolService.handlerProfileCompanyTag(userid_list,company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
        public Response deleteCompanyTagByIds(int hr_id, int company_id, List<Integer> company_tag_ids) throws BIZException, TException {
        try{
            return talentPoolService.deleteCompanyTags(hr_id,company_id,company_tag_ids);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response getCompanyIdInfo(int hr_id, int company_id, int company_tag_id) throws BIZException, TException {
        try{
            Map<String, Object> result =  talentPoolService.getCompanyTagInfo(hr_id,company_id, company_tag_id);
            if(result != null && result.get("responseStatus")!=null) {
                int resultStatus = (Integer)result.get("responseStatus");
                if (resultStatus == 0) {
                    if(result.get("data") != null) {
                        Map<String, Object> resultData = (Map<String, Object>)result.get("data");
                        String result1 = JSON.toJSONString(resultData, serializeConfig);
                        return ResponseUtils.successWithoutStringify(result1);
                    }else{
                        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
                    }
                } else if (resultStatus == -1) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
                } else if (resultStatus == -2) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
                } else if (resultStatus == -3) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
                } else {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
                }
            }else{
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response addCompanyTag(TalentpoolCompanyTagDO companyTagDO, int hr_id) throws BIZException, TException {
        try{
            return talentPoolService.addCompanyTag(companyTagDO, hr_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response updateCompanyTag(TalentpoolCompanyTagDO companyTagDO, int hr_id) throws BIZException, TException {
        try{
            return talentPoolService.updateCompanyTag(companyTagDO, hr_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    private Set<Integer> ConvertListToSet(List<Integer> list){
        Set<Integer> param=new HashSet<>();
        for(Integer id :list){
            param.add(id);
        }
        return param;
    }
}
