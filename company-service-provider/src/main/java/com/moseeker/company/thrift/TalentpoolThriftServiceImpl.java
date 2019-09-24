package com.moseeker.company.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolPast;
import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.company.bean.TalentTagPOJO;
import com.moseeker.company.bean.email.TalentEmailEnum;
import com.moseeker.company.service.impl.TalentPoolService;
import com.moseeker.company.service.impl.TalentpoolEmailService;
import com.moseeker.entity.Constant.EmailAccountConsumptionType;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolServices;
import com.moseeker.thrift.gen.company.struct.ActionForm;
import com.moseeker.thrift.gen.company.struct.EmailAccountConsumptionForm;
import com.moseeker.thrift.gen.company.struct.EmailAccountForm;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    private TalentpoolEmailService talentpoolEmailService;

    @Override
    public Response upsertTalentPoolApp(int hrId, int companyId,int type) throws BIZException, TException {
        try{
            Response result=talentPoolService.upsertTalentPoolApplication(hrId,companyId,type);
            return result;

        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getTalentAllComment(int hr_id, int company_id, int user_id,int page_number,int page_size) throws BIZException, TException {
        try{
            return talentPoolService.getAllTalentComment(hr_id,company_id,user_id,page_number,page_size);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
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
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response batchAddTalent(int hr_id, List<Integer> user_ids, int company_id,int isGdpr) throws BIZException, TException {
        try{
            return talentPoolService.batchAddTalent(hr_id,ConvertListToSet(user_ids),company_id,isGdpr);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response batchCancelTalent(int hr_id, List<Integer> user_ids, int company_id,int isGdpr) throws BIZException, TException {
        try{
            return talentPoolService.batchCancelTalent(hr_id,ConvertListToSet(user_ids),company_id,isGdpr);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response hrAddTag(int hr_id, int company_id, String name) throws BIZException, TException {
        try{
            return talentPoolService.addHrTag(hr_id,company_id,name);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response hrDelTag(int hr_id, int company_id, int tag_id) throws BIZException, TException {
        try{
            return talentPoolService.deleteHrTag(hr_id,company_id,tag_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response hrUpdateTag(int hr_id, int company_id, int tag_id, String name) throws BIZException, TException {
        try{
            return talentPoolService.updateHrTag(hr_id,company_id,tag_id,name);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response batchCancleTalentTag(int hr_id, List<Integer> user_ids, List<Integer> tag_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.batchCancelTalentTag(hr_id,ConvertListToSet(user_ids),ConvertListToSet(tag_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response batchAddTalentTag(int hr_id, List<Integer> user_ids, List<Integer> tag_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.addBatchTalentTag(hr_id,ConvertListToSet(user_ids),ConvertListToSet(tag_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response hrAddComment(int hr_id, int company_id, int user_id, String content) throws BIZException, TException {
        try{
            return talentPoolService.addTalentComment(hr_id,company_id,user_id,content);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }


    @Override
    public Response hrDelComment(int hr_id, int company_id, int comment_id) throws BIZException, TException {
        try{
            return talentPoolService.delTalentComment(hr_id,company_id,comment_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response batchAddPublicTalent(int hr_id, int company_id, List<Integer> user_ids,int isGdpr) throws BIZException, TException {
        try{
            return talentPoolService.AddbatchPublicTalent(hr_id,company_id,ConvertListToSet(user_ids),isGdpr);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response batchCancelPublicTalent(int hr_id, int company_id, List<Integer> user_ids) throws BIZException, TException {
        try{
            return talentPoolService.cancelBatchPublicTalent(hr_id,company_id,ConvertListToSet(user_ids));
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getCompanyPulicTalent(int hr_id, int company_id, int page_number, int page_size) throws BIZException, TException {
        try{
            return talentPoolService.getCompanyPublic(hr_id,company_id,page_number,page_size);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response batchNewAddTalentTag(int hr_id, List<Integer> user_ids, List<Integer> tag_ids, int company_id) throws BIZException, TException {
        try{
            return talentPoolService.addNewBatchTalentTag(hr_id,ConvertListToSet(user_ids),ConvertListToSet(tag_ids),company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getTalentStat(int hr_id, int company_id, int type) throws BIZException, TException {
        try{
            return talentPoolService.getTalentState(hr_id,company_id,type);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getCompanyUserPublic(int hr_id, int company_id, int user_id) throws BIZException, TException {
        try{
            return talentPoolService.getCompanyUserPublic(hr_id,company_id,user_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getCompanyTalent(int hr_id, int company_id, int user_id) throws BIZException, TException {
        try{
            return talentPoolService.getCompanyTalent(hr_id,company_id,user_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getHrUserTag(int hr_id, int company_id, int user_id) throws BIZException, TException {
        try{
            return talentPoolService.getHrUserTag(hr_id,company_id,user_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getUserOrigin(int hr_id, int company_id, int user_id) throws BIZException, TException {
        try{
            return talentPoolService.getUserOrigin(hr_id,company_id,user_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getTalentAndPublicHr(int hr_id, int company_id, List<Integer> user_ids) throws BIZException, TException {
        try{
            return talentPoolService.getPublicAndHrTalentByUserIdList(hr_id,company_id,this.ConvertListToSet(user_ids));
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
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
            throw ExceptionUtils.convertException(e);
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
            throw ExceptionUtils.convertException(e);
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
            throw ExceptionUtils.convertException(e);
        }

    }

    @Override
    public void handlerCompanyTagAndProfile(Set<Integer> userid_list, int company_id) throws BIZException, TException {
        try{
            talentPoolService.handlerProfileCompanyTag(userid_list,company_id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getTalentCountByPositionFilter(int hr_id, int company_id, int position_id) throws BIZException, TException {
        try{
            return talentPoolService.getTalentCountByPositionFilter(hr_id,company_id,position_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getEmailBalance(int hr_id, int company_id) throws BIZException, TException {
        try{
            return talentpoolEmailService.getCompanyEmailBalance(hr_id, company_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getEmailTemplateList(int hr_id, int company_id) throws BIZException, TException {
        try{
            return talentpoolEmailService.getCompanyEmailTemlateList(hr_id, company_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getEmailInfo(int hr_id, int company_id, int type) throws BIZException, TException {
        try{
            return talentpoolEmailService.getCompanyEmailTemlateInfo(hr_id, company_id, type);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response updateCompanyEmailInfo(int hr_id, int company_id, int type, int disable, String context, String inscribe) throws BIZException, TException {
        try{
            return talentpoolEmailService.updateEmailInfo(hr_id, company_id, type, disable, context, inscribe);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public EmailAccountForm fetchEmailAccounts(int companyId, String companyName, int pageNumber, int pageSize) throws BIZException, TException {
        try {
            if (pageNumber <= 0 || pageSize <= 0) {
                throw ExceptionConvertUtil.convertCommonException(CommonException.validateFailed("参数无效！"));
            }
            if (pageSize > Constant.DATABASE_PAGE_SIZE) {
                throw ExceptionConvertUtil.convertCommonException(CommonException.PROGRAM_FETCH_TOO_MUCH);
            }
            return talentpoolEmailService.fetchEmailAccounts(companyId, companyName, pageNumber, pageSize);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public EmailAccountConsumptionForm fetchEmailAccountConsumption(int companyId, byte type, int pageNumber,
                                                                    int pageSize, String startDate, String endDate) throws BIZException, TException {
        try {
            if (pageNumber <= 0 || pageSize <= 0) {
                throw ExceptionConvertUtil.convertCommonException(CommonException.validateFailed("参数无效！"));
            }
            if (pageSize > Constant.DATABASE_PAGE_SIZE) {
                throw ExceptionConvertUtil.convertCommonException(CommonException.PROGRAM_FETCH_TOO_MUCH);
            }
            EmailAccountConsumptionType emailAccountConsumptionType = EmailAccountConsumptionType.instanceFromValue(type);
            if (emailAccountConsumptionType == null) {
                throw ExceptionConvertUtil.convertCommonException(CommonException.validateFailed("错误的消费类型！"));
            }
            DateTime startDateTime = null;
            if (StringUtils.isNotBlank(startDate)) {
                try {
                    startDateTime = DateTime.parse(startDate);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw ExceptionConvertUtil.convertCommonException(CommonException.validateFailed("开始时间格式不正确！"));
                }
            }
            DateTime endDateTime = null;
            if (StringUtils.isNotBlank(endDate)) {
                try {
                    endDateTime = DateTime.parse(endDate).plusDays(1).withTimeAtStartOfDay();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw ExceptionConvertUtil.convertCommonException(CommonException.validateFailed("结束时间格式不正确！"));
                }
            }
            if (startDateTime != null && endDateTime != null && startDateTime.getMillis() > endDateTime.getMillis()) {
                throw ExceptionConvertUtil.convertCommonException(CommonException.validateFailed("开始时间必不能大于结束时间！"));
            }
            return talentpoolEmailService.fetchEmailAccountConsumption(companyId, emailAccountConsumptionType, pageNumber, pageSize, startDateTime, endDateTime);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int rechargeEmailAccount(int companyId, int lost) throws BIZException, TException {
        try {
            if (companyId <= 0 || lost <= 0) {
                throw ExceptionConvertUtil.convertCommonException(CommonException.validateFailed("参数无效！"));
            }
            return talentpoolEmailService.rechargeEmailAccount(companyId, lost);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void updateEmailAccountRechargeValue(int id, int lost) throws BIZException, TException {
        try {
            if (id <= 0 || lost <= 0) {
                throw ExceptionConvertUtil.convertCommonException(CommonException.validateFailed("参数无效！"));
            }
            talentpoolEmailService.updateEmailAccountRecharge(id, lost);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void addAllTalent(int hrId, Map<String, String> params, int companyId,int isGdpr) throws BIZException, TException {
        try{
             talentPoolService.addAllTalent(hrId,params,companyId,isGdpr);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void addAllTalentTag(Map<String, String> params, List<Integer> tagList, int companyId, int hrId) throws BIZException, TException {
        try{
            talentPoolService.addAllTalentTag(params,tagList,companyId,hrId);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void addAllTalentPublic(int hrId, Map<String, String> params, int companyId,int isGdpr) throws BIZException, TException {
        try{
            talentPoolService.addAllTalentPublic(params,companyId,hrId,isGdpr);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void addAllTalentPrivate(int hrId, Map<String, String> params, int companyId) throws BIZException, TException {
        try{
            talentPoolService.addAllTalentPrivate(params,companyId,hrId);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void cancleAllTalent(int hrId, Map<String, String> params, int companyId,int isGdpr) throws BIZException, TException {
        try{
            talentPoolService.cancleAllTalent(hrId,params,companyId,isGdpr);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response updateCompanyEmailBalance(int company_id, int balance) throws BIZException, TException {
        try{
            return talentpoolEmailService.updateEmailInfoBalance(company_id,balance);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response sendInviteEmail(Map<String, String> params, List<Integer> userIdList, List<Integer> positionIdList, int companyId, int hrId, int flag,int positionFlag) throws BIZException, TException {
        try{
            int result=talentpoolEmailService.talentPoolSendInviteToDelivyEmail(params,userIdList,positionIdList,companyId,hrId,flag,positionFlag);
            if(result<0){
                return  ResponseUtils.fail(TalentEmailEnum.intToEnum.get(result));
            }
            return ResponseUtils.success("success");
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }

    }

    @Override
    public Response sendResumeEmail(Map<String, String> params, List<Integer> userIdList, List<Integer> idList, int companyId, int hrId, int flag,List<String> emailList,List<Integer> appIdList) throws BIZException, TException {
        try{
            int result=talentpoolEmailService.talentPoolSendResumeEmail(idList,params,userIdList,companyId,hrId,flag,emailList,appIdList);
            if(result<0){
                return  ResponseUtils.fail(TalentEmailEnum.intToEnum.get(result));
            }
            return ResponseUtils.success("success");
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }

    @Override
    public Response sendPositionInviteEmail(int hrId, int positionId, int companyId) throws BIZException, TException {
        try{
            int result=talentpoolEmailService.positionInviteDelivyEmail(hrId,positionId,companyId);
            if(result<0){
                return  ResponseUtils.fail(TalentEmailEnum.intToEnum.get(result));
            }
            return ResponseUtils.success("success");
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,e.getMessage());
        }
    }

    @Override
    public Response getProfileFilterList(int hr_id, int company_id, int page_number, int page_size) throws BIZException, TException {
        try{
            return talentPoolService.getProfileFilterList(hr_id,company_id,page_number, page_size);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response handerProfileFilterByIds(int hr_id, int company_id, int disable, List<Integer> filter_ids) throws BIZException, TException {
        try{
            return talentPoolService.handerProfileFilters(hr_id, company_id, disable, filter_ids);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getProfileFilterInfo(int hr_id, int company_id, int filter_id) throws BIZException, TException {
        try{
            return talentPoolService.getProfileFilterInfo(hr_id, company_id, filter_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response addProfileFilter(TalentpoolCompanyTagDO companyTagDO, List<ActionForm> actionForm, List<Integer> positionIdList, int hr_id, int position_total) throws BIZException, TException {
        try{
            return talentPoolService.addProfileFilter(companyTagDO, actionForm, positionIdList, hr_id, position_total);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response updateProfileFilter(TalentpoolCompanyTagDO companyTagDO, List<ActionForm> actionForm, List<Integer> positionIdList, int hr_id, int position_total) throws BIZException, TException {
        try{
            return talentPoolService.updateProfileFilter(companyTagDO, actionForm, positionIdList, hr_id, position_total);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }



    private Set<Integer> ConvertListToSet(List<Integer> list){
        Set<Integer> param=new HashSet<>();
        for(Integer id :list){
            param.add(id);
        }
        return param;
    }

    @Override
    public Response getCompanyCommentByUserIdList( int companyId,List<Integer> userIdList) throws TException {
        try{
            Map<Integer,String> result=talentPoolService.getUserComment(companyId,userIdList);
            return ResponseUtils.success(result);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }
}
