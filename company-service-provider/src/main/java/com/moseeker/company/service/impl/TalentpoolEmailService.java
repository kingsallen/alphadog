package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.moseeker.baseorm.dao.dictdb.DictIndustryDao;
import com.moseeker.baseorm.dao.dictdb.DictIndustryTypeDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolEmailDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyConf;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyEmailInfo;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyEmailInfoRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolEmailRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.company.bean.email.*;
import com.moseeker.entity.*;
import com.moseeker.entity.Constant.EmailAccountConsumptionType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.company.bean.*;
import com.moseeker.entity.biz.CommonUtils;
import com.moseeker.entity.exception.TalentPoolException;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.EmailAccountConsumptionForm;
import com.moseeker.thrift.gen.company.struct.EmailAccountForm;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailListStruct;
import com.moseeker.thrift.gen.profile.service.ProfileOtherThriftService;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * Created by moseeker on 2018/4/20.
 */
@Service
public class TalentpoolEmailService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public TalentpoolEmailService(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @Autowired
    private TalentPoolEntity talentPoolEntity;

    @Autowired
    private TalentPoolEmailEntity talentPoolEmailEntity;

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Autowired
    private TalentpoolEmailDao talentpoolEmailDao;

    @Autowired
    private HrCompanyEmailInfoDao hrCompanyEmailInfoDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    @Resource(name = "cacheClient")
    private RedisClient client;

    @Autowired
    private PcRevisionEntity pcRevisionEntity;

    @Autowired
    private DictIndustryDao dictIndustryDao;

    @Autowired
    private DictIndustryTypeDao dictIndustryTypeDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;

    @Autowired
    private Environment env;

    SearchengineServices.Iface searchService = ServiceManager.SERVICE_MANAGER.getService(SearchengineServices.Iface.class);

    MqService.Iface mqService = ServiceManager.SERVICE_MANAGER.getService(MqService.Iface.class);

    private ThreadPool tp = ThreadPool.Instance;

    @Autowired
    private UserWxEntity userWxEntity;
    ProfileOtherThriftService.Iface profileOtherService = ServiceManager.SERVICE_MANAGER.getService(ProfileOtherThriftService.Iface.class);

    @Autowired
    MandrillMailListConsumer mandrillMailListConsumer;

    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * 获取公司邮件剩余额度
     * @param hr_id         HR编号
     * @param company_id    公司编号
     * @return
     */
   public Response getCompanyEmailBalance(int hr_id, int company_id){
       HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(company_id);
       if(companyDO == null){
           return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
       }
       int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id, company_id);
       if(flag == -1){
           return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
       }else if(flag == -2){
           return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
       }else if(flag == -3){
           return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
       }
       HrCompanyEmailInfo info = talentPoolEmailEntity.getHrCompanyEmailInfoByCompanyId(company_id);
       Map<String, Object> infoMap = new HashMap<>();
       infoMap.put("number", info.getBalance());
       return ResponseUtils.success(infoMap);
   }

    /**
     * 获取公司的邮件列表
     * @param hr_id         hr编号
     * @param company_id    公司编号
     * @return
     */
    public Response getCompanyEmailTemlateList(int hr_id, int company_id){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(company_id);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id, company_id);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        List<TalentpoolEmail> info = talentPoolEmailEntity.getTalentpoolEmailByCompanyId(company_id);
        List<Map<String, Object>> email_list = new ArrayList<>();
        if(info != null && info.size()>0){
            List<Integer> idList = info.stream().map(m -> m.getConfigId()).collect(Collectors.toList());
            List<ConfigSysTemplateMessageLibraryDO> libraryDOS = talentPoolEmailEntity.getConfigSysTemplateMessageLibraryDOByIdList(idList);
            if(libraryDOS != null && libraryDOS.size()>0){
                for(ConfigSysTemplateMessageLibraryDO tempalte : libraryDOS){
                    for(TalentpoolEmail email : info){
                        if(tempalte.getId() == email.getConfigId()){
                            Map<String, Object> emailInfo = new HashMap<>();
                            emailInfo.put("id",email.getId());
                            emailInfo.put("company_id",email.getCompanyId());
                            emailInfo.put("type",tempalte.getId());
                            emailInfo.put("name",tempalte.getTitle());
                            emailInfo.put("send_condition",tempalte.getSendCondition());
                            emailInfo.put("send_to",tempalte.getSendto());
                            emailInfo.put("send_time",tempalte.getSendtime());
                            emailInfo.put("status",email.getDisable());
                            email_list.add(emailInfo);
                            break;
                        }
                    }
                }
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("email_list",email_list);
        return ResponseUtils.success(data);
    }

    /**
     * 获取邮件的具体信息
     * @param hr_id         HR编号
     * @param company_id    公司编号
     * @param type          邮件类型
     * @return
     */
    public Response getCompanyEmailTemlateInfo(int hr_id, int company_id, int type){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(company_id);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id, company_id);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        List<TalentpoolEmail> info = talentPoolEmailEntity.getTalentpoolEmailByCompanyIdAndConfigId(company_id, type);
        Map<String, Object> data = new HashMap<>();
        if(info != null && info.size()>0){
            ConfigSysTemplateMessageLibraryDO libraryDO = talentPoolEmailEntity.getConfigSysTemplateMessageLibraryDOById(type);
            if(libraryDO != null){
                for(TalentpoolEmail email : info){
                    if(email.getConfigId() == libraryDO.getId()){
                        List<String> symbol = new ArrayList<>();
                        if(type != 71) {
                            symbol.add("#职位名称#");
                            symbol.add("#HR姓名#");
                        }
                        symbol.add("#求职者姓名#");
                        symbol.add("#公司简称#");
                        symbol.add("#公众号名称#");
                        data.put("symbol", symbol);
                        data.put("context", email.getContext());
                        data.put("inscribe", email.getInscribe());
                        data.put("default_text", libraryDO.getFirst());
                        data.put("default_sign", libraryDO.getRemark());
                        data.put("id", email.getId());
                    }
                }
            }
        }
        return ResponseUtils.success(data);
    }


    public Response updateEmailInfo(int hr_id, int company_id, int type, int disable, String context, String inscribe){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(company_id);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id, company_id);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        String result = talentPoolEmailEntity.updateEmailInfo(company_id, type, disable, context, inscribe);
        if("OK".equals(result) ) {
            return ResponseUtils.success("");
        }else{
            return ResponseUtils.fail(1,result);
        }
    }


    public EmailAccountForm fetchEmailAccounts(int companyId, String companyName, int pageNumber, int pageSize) throws CommonException {
        return talentPoolEmailEntity.fetchEmailAccounts(companyId, companyName, pageNumber, pageSize);
    }

    public EmailAccountConsumptionForm fetchEmailAccountConsumption(int companyId, EmailAccountConsumptionType emailAccountConsumptionType, int pageNumber, int pageSize, DateTime startDate, DateTime endDate) {
        return talentPoolEmailEntity.fetchEmailAccountConsumption(companyId, emailAccountConsumptionType, pageNumber, pageSize, startDate, endDate);
    }

    public int rechargeEmailAccount(int companyId, int lost) throws TalentPoolException {
        return talentPoolEmailEntity.handerTalentpoolEmailLogAndBalance(lost, 0, companyId, 0);
    }

    public void updateEmailAccountRecharge(int id, int lost) {
        talentPoolEmailEntity.updateEmailAccountRecharge(id, lost);
    }
    public Response updateEmailInfoBalance(int company_id, int useBalance){
        talentPoolEmailEntity.handerTalentpoolEmailLogAndBalance(useBalance,3,company_id,0);
        return ResponseUtils.success("success");
    }
    public Response updateEmailInfoBalance(int company_id, int useBalance,int type){
        talentPoolEmailEntity.handerTalentpoolEmailLogAndBalance(useBalance,type,company_id,0);
        return ResponseUtils.success("success");
    }
    /*
     转发邀请投递职位邮件
     */
    @CounterIface
    public  int talentPoolSendInviteToDelivyEmail(Map<String,String> params ,List<Integer> userIdList,List<Integer> positionIdList,int companyId,int hrId,int flag,int positionFlag){
        int sflag=validateCompanyAndOther(companyId,hrId);
        logger.info("校验结果为======={}",sflag);
        if(sflag<0){
            return sflag ;
        }
        if(flag==0){
           return this.sendInviteToDelivyEmail(userIdList,positionIdList,companyId, hrId,positionFlag);
        }else{
           return sendAllInviteToDelivyEmail(params, positionIdList,companyId,hrId,positionFlag);
        }
    }
    /*
    发送转发简历邮件
     */
    @CounterIface
    public int talentPoolSendResumeEmail(List<Integer> idList,Map<String,String> params,List<Integer> userIdList,int companyId,int hrId,int flag,List<String>emailList,List<Integer> appIdList){
        int sflag=validateCompanyAndOther(companyId,hrId);
        if(sflag<0){
            return sflag ;
        }
        int result;
        if(flag==0){
            //校验gdpr
            userIdList=StringUtils.convertSetToList(talentPoolEntity.filterGRPD(companyId,StringUtils.convertListToSet(userIdList)));
            if(StringUtils.isEmptyList(userIdList)){
                throw new CommonException(1,"该简历不可操作");
            }
            result=sendResumeEmail(idList, userIdList, companyId, hrId,emailList);
        }else{
            result=sendAllResumeEmail(idList,params,companyId,hrId,emailList);
        }
        if(!CollectionUtils.isEmpty(appIdList)){
            this.addOperationLog(hrId,appIdList);
        }
        return result;
    }

    /**
     * 转发简历时添加操作记录
     * @param hrId
     * @param appIdList
     */
    private void addOperationLog(int hrId, List<Integer> appIdList) {
        Map map=new HashMap();
        map.put("hrId",hrId);
        map.put("logType", "TRANSMIT_CANDIDATE_INFORMATION");
        map.put("candidateNum",appIdList.size());
        map.put("appIds",appIdList);
        amqpTemplate.convertAndSend("operation_log_exchange","operation_log_routekey", JSON.toJSONString(map));
    }

    /*
    职位发布成功之后邀请投递邮件
     */
    @CounterIface
    public int positionInviteDelivyEmail(int hrId,int positionId,int companyId){
        int flag=validateCompanyAndOther(companyId,hrId);
        if(flag<0){
             return flag ;
        }
        int result=this.sendPositionInviteEmail(positionId,hrId,companyId,flag);
        SimpleDateFormat ff=new SimpleDateFormat("yyyy-MM-dd");
        String datetime=ff.format(new Date());
        client.setNoTime(Constant.APPID_ALPHADOG, KeyIdentifier.LAST_SEND_POSITION_INVITE.toString(),hrId+"",positionId+"",datetime);
        return result;

    }

    private int validateCompanyAndOther(int companyId,int hrId){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(companyId);
        if(companyDO == null){
            return TalentEmailEnum.COMPANY_NOT_MU.getValue();
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId, companyId);
        return flag;
    }

    /*
    获取position
     */
    private List<Map<String,String>> getSearchConditions(int hrId,int positionId,int companyId,int flag) {
        List<Map<String, Object>> filterMapList = talentPoolEntity.getProfileFilterByPosition(positionId, companyId);
        Map<String, Object> map = new HashMap<>();
        if (filterMapList != null && filterMapList.size() > 0) {
            List<Map<String, String>> filterList = new ArrayList<>();
            for (Map<String, Object> filterMap : filterMapList) {
                Map<String, String> params = new HashMap<>();
                if (filterMap != null && !filterMap.isEmpty()) {
                    for (String key : filterMap.keySet()) {
                        params.put(key, String.valueOf(filterMap.get(key)));
                    }
                }
                params.put("hr_id", String.valueOf(hrId));
                params.put("account_type", String.valueOf(flag));
                params.put("exists", "user.profiles.profile.user_id,user.profiles.basic.name,user.profiles.basic.email");
                filterList.add(params);
            }
            return filterList;
        }
       return null;
    }
    /*
    发送部分的职位邀请
     */
    private int sendInviteToDelivyEmail(List<Integer> userIdList,List<Integer> positionIdList,int companyId,int hrId,int positionFlag){
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId,72);
        logger.info("=============HrCompanyEmailInfoRecord===========");
        logger.info(hrCompanyEmailInfoRecord.toString());
        logger.info("================================================");
        logger.info("=============TalentpoolEmailRecord===========");
        logger.info(talentpoolEmailRecord.toString());
        logger.info("================================================");
        boolean flag=this.validateSendEmail(hrCompanyEmailInfoRecord,talentpoolEmailRecord);
        logger.info("校验邮件配置的结果========================{}",flag);
        if(flag){
            try {
                HrCompanyRecord record = this.getHrCompanyRecord(hrId,companyId);
                logger.info("=============HrCompanyRecord===========");
                logger.info(record.toString());
                logger.info("================================================");
                boolean balanceFlag = this.validateBalance(hrCompanyEmailInfoRecord.getBalance(), userIdList.size());
                logger.info("校验额度的结果========================{}",balanceFlag);
                if (balanceFlag) {
                    List<InviteToDelivyUserInfo> userInfo = this.talentEmailInviteInfoSearch(userIdList);
                    logger.info("=============List<InviteToDelivyUserInfo>===========");
                    logger.info(JSON.toJSONString(userInfo));
                    logger.info("================================================");
                    EmailInviteBean emailDate = this.handlerData(positionIdList, companyId, talentpoolEmailRecord.getContext(), userInfo, record, hrId,positionFlag);
                    logger.info("=============EmailInviteBean===========");
                    logger.info(JSON.toJSONString(emailDate));
                    logger.info("================================================");
                    if(emailDate!=null) {
                        MandrillEmailListStruct struct = convertToEmailStruct(emailDate);
                        updateEmailInfoBalance(companyId, userInfo.size(),4);
                        logger.info("=============MandrillEmailListStruct===========");
                        logger.info(JSON.toJSONString(struct));
                        logger.info("================================================");
                        mqService.sendMandrilEmailList(struct);
                    }
                }else{
                    return TalentEmailEnum.NOBALANCE.getValue();
                }
            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }
        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;
    }
    /*
    发送全部邮件
     */
    private  int sendAllInviteToDelivyEmail(Map<String,String> params,List<Integer> positionIdList,int companyId,int hrId,int positionFlag){
        params.put("company_id",companyId+"");
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId,72);
        logger.info("=============HrCompanyEmailInfoRecord===========");
        logger.info(hrCompanyEmailInfoRecord.toString());
        logger.info("================================================");
        logger.info("=============TalentpoolEmailRecord===========");
        logger.info(talentpoolEmailRecord.toString());
        logger.info("================================================");
        boolean flag=this.validateSendEmail(hrCompanyEmailInfoRecord,talentpoolEmailRecord);
        logger.info("校验邮件配置的结果========================{}",flag);
        if(flag){
            try {
                int totalNum = searchService.talentSearchNum(params);
                boolean balanceFlag=this.validateBalance(hrCompanyEmailInfoRecord.getBalance(),totalNum);
                logger.info("校验额度的结果========================{}",balanceFlag);
                if(balanceFlag){
                    updateEmailInfoBalance(companyId, totalNum,4);
                    HrCompanyRecord record=this.getHrCompanyRecord(hrId,companyId);
                    logger.info("=============HrCompanyRecord===========");
                    logger.info(record.toString());
                    logger.info("================================================");
                    tp.startTast(() -> {
                        sendInviteEmailCore(params,positionIdList,companyId,talentpoolEmailRecord.getContext(),record,hrId,totalNum,positionFlag);
                        return 0;
                    });

                }else{
                    return TalentEmailEnum.NOBALANCE.getValue();
                }
            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }

        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;
    }

    private int sendPositionInviteEmail(int positionId,int hrId,int companyId,int hrFlag){
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId,72);
        boolean flag=this.validateSendEmail(hrCompanyEmailInfoRecord,talentpoolEmailRecord);
        if(flag) {
            try {
                List<Map<String,String>>params=this.getSearchConditions(hrId,positionId,companyId,hrFlag);
                List<Integer> positionIdList=new ArrayList<>();
                positionIdList.add(positionId);
                int totalNum =getPositionInviteNum(params);
                if(totalNum>0){
                    boolean balanceFlag=this.validateBalance(hrCompanyEmailInfoRecord.getBalance(),totalNum);
                    if(balanceFlag){
                        HrCompanyRecord record=this.getHrCompanyRecord(hrId,companyId);
                        updateEmailInfoBalance(companyId, totalNum,4);
                        tp.startTast(() -> {
                            sendPositionInviteEmailCore(params,positionIdList,companyId,talentpoolEmailRecord.getContext(),record,hrId,totalNum);
                            return 0;
                        });

                    }else{
                        return TalentEmailEnum.NOBALANCE.getValue();
                    }
                }else{
                    return TalentEmailEnum.NOUSERPROFILE.getValue();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;

}
    /*
     发送邀请邮件的核心处理，使用线程启动他
     */
    private void sendInviteEmailCore(Map<String,String> params,List<Integer> positionIdList,int companyId,String context,HrCompanyRecord record,int hrId,int totalNum,int flag){
        int totalPage=(int)Math.ceil((double)totalNum/300);
        for(int i=1;i<=totalPage;i++) {
            params.put("page_size", 300 + "");
            params.put("page_number", i + "");
            try {
                List<InviteToDelivyUserInfo> userInfo = this.talentEmailInviteInfoSearch(params);
                logger.info("=============List<InviteToDelivyUserInfo>===========");
                logger.info(JSON.toJSONString(userInfo));
                logger.info("================================================");
                EmailInviteBean emailDate = this.handlerData(positionIdList, companyId,context , userInfo, record, hrId, flag);
                logger.info("=============EmailInviteBean===========");
                logger.info(JSON.toJSONString(emailDate));
                logger.info("================================================");
                if(emailDate!=null){
                    MandrillEmailListStruct struct = convertToEmailStruct(emailDate);
                    logger.info("=============MandrillEmailListStruct===========");
                    logger.info(JSON.toJSONString(struct));
                    logger.info("================================================");
                    mqService.sendMandrilEmailList(struct);
                }

            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }
        }
    }
    /*
     职位发送邀请邮件的核心处理，使用线程启动他
     */
    private void sendPositionInviteEmailCore(List<Map<String,String>> params,List<Integer> positionIdList,int companyId,String context,HrCompanyRecord record,int hrId,int totalNum){
        int totalPage=(int)Math.ceil((double)totalNum/300);

        for(int i=1;i<=totalPage;i++) {
            try {
                List<InviteToDelivyUserInfo> userInfo = this.talentEmailInviteInfoSearch(params,i,300);
                EmailInviteBean emailDate = this.handlerData(positionIdList, companyId,context , userInfo, record, hrId,0);
                if(emailDate!=null) {
                    MandrillEmailListStruct struct = convertToEmailStruct(emailDate);
                    logger.info(JSON.toJSONString(struct));
                    mqService.sendMandrilEmailList(struct);
                }
            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }
        }
    }
    /*
     发送部分转发邮件
     */
    private int sendResumeEmail(List<Integer> idList,List<Integer> userIdList,int companyId,int hrId,List<String> sendEmailList){
        if(StringUtils.isEmptyList(idList)&&StringUtils.isEmptyList(sendEmailList)) {
            return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
        }
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId,73);
        logger.info(hrCompanyEmailInfoRecord.toString());
        logger.info(talentpoolEmailRecord.toString());
        boolean flag=this.validateSendEmail(hrCompanyEmailInfoRecord,talentpoolEmailRecord);
        if(flag){
            try {
                List<UserEmployeeDO> employeeList = this.getUserEmployeeList(idList);
                logger.info("=============List<UserEmployeeDO>===========");
                logger.info(JSON.toJSONString(employeeList));
                if (!StringUtils.isEmptyList(employeeList)||!StringUtils.isEmptyList(sendEmailList)) {
                    int lost = this.handlerLost(employeeList,userIdList,sendEmailList);
                    if (!this.validateBalance(hrCompanyEmailInfoRecord.getBalance(), lost)) {
                        return TalentEmailEnum.NOBALANCE.getValue();
                    }
                    List<UserEmployeeDO> userEmployeeDOList=this.handlerEmployeeList(employeeList,sendEmailList);
                    if(lost>10){
                        tp.startTast(() -> {
                            sendSingleResumeEmail(userEmployeeDOList,userIdList,companyId,talentpoolEmailRecord.getContext(),hrId,lost);
                            return 0;
                        });
                    }else{
                        int resultSend=sendSingleResumeEmail(userEmployeeDOList,userIdList,companyId,talentpoolEmailRecord.getContext(),hrId,lost);
                         if(resultSend>0){
                             return resultSend;
                         }
                    }


                    List<Map<String,Object>> employeeData=this.handlerEmployeeData(userEmployeeDOList);
                    if(!StringUtils.isEmptyList(employeeData)){
                        logger.info(JSON.toJSONString(employeeData));
                        this.handlerRedisEmployee(employeeData,hrId);
                    }

                } else {
                    return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
                }


            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }
        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;
    }
    //异步发送邮件

    private int sendSingleResumeEmail(List<UserEmployeeDO> employeeList,List<Integer> userIdList,int companyId,String context,int hrId,int lost) throws Exception {
        EmailResumeBean emailList = this.convertResumeEmailData(employeeList, userIdList, companyId, context, hrId);
        logger.info(JSON.toJSONString(emailList));
        updateEmailInfoBalance(companyId, lost,5);
        List<MandrillEmailListStruct> struct = convertToEmailStruct(emailList,companyId);
        logger.info(JSON.toJSONString(struct));
        if(!StringUtils.isEmptyList(struct)){
            for(MandrillEmailListStruct item:struct){
                mqService.sendMandrilEmailList(item);
            }
            return 0;
        }
         return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
    }
    /*
     计算所消耗的积分
     */
    private int handlerLost(List<UserEmployeeDO> employeeList,List<Integer> userIdList,List<String> sendEmailList){
            int sendNum=0;
            if(!StringUtils.isEmptyList(employeeList)){
                sendNum+=employeeList.size();
            }
            if(!StringUtils.isEmptyList(sendEmailList)){
                sendNum+=sendEmailList.size();
            }
            return sendNum*userIdList.size();
    }
    /*
     重载方法，参数不同
     */
    private int handlerLost(List<UserEmployeeDO> employeeList, int userNum,List<String> sendEmailList){
        int sendNum=0;
        if(!StringUtils.isEmptyList(employeeList)){
            sendNum+=employeeList.size();
        }
        if(!StringUtils.isEmptyList(sendEmailList)){
            sendNum+=sendEmailList.size();
        }
        return sendNum*userNum;
    }
    /*
     处理员工的数据，将手动填写的email写入employee
     */
    private List<UserEmployeeDO>  handlerEmployeeList(List<UserEmployeeDO> employeeList,List<String> sendEmailList){
        if(StringUtils.isEmptyList(employeeList)){
            employeeList=new ArrayList<>();
        }
        if(StringUtils.isEmptyList(sendEmailList)){
            return employeeList;
        }
        for(String email:sendEmailList){
            String name=email.substring(0,email.lastIndexOf("@"));
            UserEmployeeDO userEmployeeDO=new UserEmployeeDO();
            userEmployeeDO.setCname(name);
            userEmployeeDO.setEmail(email);
            employeeList.add(userEmployeeDO);
        }
        return employeeList;
    }

    private void handlerRedisEmployee(List<Map<String,Object>> employeeData,int hrId){
        String res=client.get(Constant.APPID_ALPHADOG, KeyIdentifier.PAST_USER_EMPLOYEE_VALIDATE.toString(),hrId+"");
        logger.info("在redis中的转发记录是==========================");
        logger.info(res);
        logger.info("========================");
        if(StringUtils.isNullOrEmpty(res)){
            if(employeeData.size()>10){
                employeeData=employeeData.subList(0,10);
            }
        }else{
            List<Map<String,Object>> resData= (List<Map<String, Object>>) JSON.parse(res);
            if(employeeData.size()>10){
                employeeData=employeeData.subList(0,10);
            }else{
               for(Map<String,Object> map:resData){
                   Integer id=(Integer)map.get("id");
                   if(id==0||id==null){
                       String email=(String)map.get("email");
                       int flag=0;
                       for(Map<String,Object> itemMap:employeeData){
                           String originEmail=(String)itemMap.get("email");
                           if(originEmail.equals(email)){
                               flag=1;
                               break;
                           }
                       }
                       if(flag==0){
                           employeeData.add(map);
                       }
                   }else{
                       int flag=0;
                       for(Map<String,Object> itemMap:employeeData){
                           int itemId=(int)itemMap.get("id");
                           if(itemId==id){
                               flag=1;
                               break;
                           }
                       }
                       if(flag==0){
                           employeeData.add(map);
                       }
                   }
               }
               if(employeeData.size()>10){
                   employeeData=employeeData.subList(0,10);
               }
            }
        }
        client.setNoTime(Constant.APPID_ALPHADOG, KeyIdentifier.PAST_USER_EMPLOYEE_VALIDATE.toString(),hrId+"",JSON.toJSONString(employeeData,serializeConfig, SerializerFeature.DisableCircularReferenceDetect));
    }

    private List<Map<String,Object>> handlerEmployeeData(List<UserEmployeeDO> employeeList) throws TException {
        if(StringUtils.isEmptyList(employeeList)){
            return null;
        }
        List<Map<String,Object>> list=new ArrayList<>();
        for(UserEmployeeDO DO:employeeList){
            Map<String,Object> DOData=new HashMap<>();
            DOData.put("id",DO.getId());
            DOData.put("name",DO.getCname());
            DOData.put("email",DO.getEmail());
            list.add(DOData);
        }
        return list;
    }

    /*
     发送全部转发邮件
     */
    private  int sendAllResumeEmail(List<Integer> idList,Map<String,String> params,int companyId,int hrId,List<String> sendEmailList){
        if(StringUtils.isEmptyList(idList)&&StringUtils.isEmptyList(sendEmailList)) {
            return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
        }
        params.put("company_id",companyId+"");
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId,73);
        logger.info(hrCompanyEmailInfoRecord.toString());
        logger.info(talentpoolEmailRecord.toString());
        boolean flag=this.validateSendEmail(hrCompanyEmailInfoRecord,talentpoolEmailRecord);
        if(flag){
            try {
                if(this.isOpenGdpr(companyId)){
                    params.put("is_gdpr","1");
                }
                int talentNum = searchService.talentSearchNum(params);
                if(talentNum>0){
                    List<UserEmployeeDO> employeeList=this.getUserEmployeeList(idList);
                    logger.info(JSON.toJSONString(employeeList));
                    if(!StringUtils.isEmptyList(employeeList)||!StringUtils.isEmptyList(sendEmailList)){
                        int lost=this.handlerLost(employeeList,talentNum,sendEmailList);
                        if(!this.validateBalance(hrCompanyEmailInfoRecord.getBalance(),lost)){
                            return TalentEmailEnum.NOBALANCE.getValue();
                        }
                        updateEmailInfoBalance(companyId,lost,5);
                        List<UserEmployeeDO> useEmployeeList=this.handlerEmployeeList(employeeList,sendEmailList);
                        tp.startTast(() -> {
                            sendResumeEmailCore(useEmployeeList,params,companyId,hrId,talentpoolEmailRecord.getContext(),talentNum);
                            return 0;
                        });
                    }else{
                        return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
                    }
                    List<Map<String,Object>> employeeData=this.handlerEmployeeData(employeeList);
                    if(!StringUtils.isEmptyList(employeeData)){
                        logger.info("=============employeeData===========");
                        logger.info(JSON.toJSONString(employeeData));
                        logger.info("================================================");
                        this.handlerRedisEmployee(employeeData,hrId);
                    }
                }else{
                    return TalentEmailEnum.NOUSERPROFILE.getValue();
                }

            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }
        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;
    }
    /*
     添加gdpr的规范的校验
     */
    private boolean isOpenGdpr(int companyId){
        HrCompanyConf data=hrCompanyConfDao.getConfbyCompanyId(companyId);
        if(data==null||data.getIsOpenGdpr()==0){
            return false;
        }
        return true;
    }
    /*
     发送转发邮件的核心处理，使用线程启动他
     */
    private void sendResumeEmailCore(List<UserEmployeeDO> employeeList,Map<String,String> params,int companyId,int hrId,String context,int totalNum){
        int totalPage=(int)Math.ceil((double)totalNum/300);
        for(int i=1;i<=totalPage;i++){
            params.put("page_size",300+"");
            params.put("page_number",i+"");

            try{
                EmailResumeBean emailList=this.convertResumeEmailData(employeeList,params,companyId,context,hrId);
                logger.info(JSON.toJSONString(emailList));
                List<MandrillEmailListStruct> structs = convertToEmailStruct(emailList,companyId);
                logger.info(JSON.toJSONString(structs));
                if(!StringUtils.isEmptyList(structs)){
                    for(MandrillEmailListStruct struct:structs){
                        mqService.sendMandrilEmailList(struct);
                    }
                }
                /*
                 * @Author zztaiwll
                 * @Description  ji
                 * @Date 下午3:01 18/12/4
                 * @Param [employeeList, params, companyId, hrId, context, totalNum]
                 * @return void
                 **/

            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }
            //
        }
    }
    public MandrillEmailListStruct convertToEmailStruct(EmailInviteBean emailInviteBean){
        MandrillEmailListStruct result=new MandrillEmailListStruct();
        result.setTemplateName(emailInviteBean.getTemplateName());
        result.setSubject(emailInviteBean.getSubject());
        result.setFrom_name(emailInviteBean.getFromName());
        result.setFrom_email(emailInviteBean.getFromEmail());
        List<TalentEmailInviteToDelivyInfo> merge=emailInviteBean.getMergeVars();
        List<ReceiveInfo> to=emailInviteBean.getTo();
        List<Map<String,String>> toReceive=new ArrayList<>();
        List<Map<String,Object>> mergeData=new ArrayList<>();
        for(ReceiveInfo receiveInfo:to){
            String tores=JSON.toJSONString(receiveInfo,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
            Map<String,Object> map=JSON.parseObject(tores);
            Map<String,String> map1=new HashMap<>();
            for(String key:map.keySet()){
                map1.put(key,String.valueOf(map.get(key)));
            }
            toReceive.add(map1);
        }
        for(TalentEmailInviteToDelivyInfo info:merge){
            String infos=JSON.toJSONString(info,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
            Map<String,Object> infoMap=(Map<String,Object>)JSON.parse(infos);
            Map<String,Object> infoMap1=new HashMap<>();
            for(String key:infoMap.keySet()){
               infoMap1.put(key,infoMap.get(key));

            }
            mergeData.add(infoMap1);
        }
        String merges = JSON.toJSONString(mergeData);
        result.setMergeVars(merges);
        result.setTo(toReceive);
        return result;
    }

    private List<MandrillEmailListStruct> convertToEmailStruct(EmailResumeBean emailInviteBean,int companyId){
        List<MandrillEmailListStruct> dataList=new ArrayList<>();
        List<TalentEmailForwardsResumeInfo> merge=emailInviteBean.getMergeVars();
        List<ReceiveInfo> to=emailInviteBean.getTo();
        for(TalentEmailForwardsResumeInfo info:merge){
            MandrillEmailListStruct result=new MandrillEmailListStruct();
            List<Map<String,Object>> mergeData=new ArrayList<>();
            String infos=JSON.toJSONString(info,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
            List<Map<String,String>> toReceive=new ArrayList<>();
            for(ReceiveInfo receiveInfo:to){
                Map<String,Object> infoMap=(Map<String,Object>)JSON.parse(infos);
                infoMap.put("rcpt",receiveInfo.getToEmail());
                infoMap.put("coworker_name",receiveInfo.getToName());
                mergeData.add(infoMap);
                String tores=JSON.toJSONString(receiveInfo,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);;
                Map<String,Object> map=JSON.parseObject(tores);
                Map<String,String> map1=new HashMap<>();
                for(String key:map.keySet()){
                    map1.put(key,String.valueOf(map.get(key)));
                }
                toReceive.add(map1);
            }
            result.setTemplateName(emailInviteBean.getTemplateName());
            result.setSubject(emailInviteBean.getSubject());
            result.setFrom_name(emailInviteBean.getFromName());
            result.setFrom_email(emailInviteBean.getFromEmail());
            String merges = JSON.toJSONString(mergeData);
            logger.info(merges);
            result.setMergeVars(merges);
            result.setTo(toReceive);
            result.setType(SendEmailTypeEnum.TALENT_INVATE_EMAIL.getValue());
            result.setCompany_id(companyId);
            dataList.add(result);
        }
        return dataList;
    }


    /*
    获取邀请投递邮件的人员的信息
    */
    private List<InviteToDelivyUserInfo> talentEmailInviteInfoSearch(List<Integer>UserIdList){
        try{
            Response res=searchService.userQueryById(UserIdList);
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                List<InviteToDelivyUserInfo> result=this.convertInviteData(data);
                logger.info(JSON.toJSONString(result));
                return result;
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    /*
     根据所查询的参数获取需要接收邮件的人员
     */
    private List<InviteToDelivyUserInfo> talentEmailInviteInfoSearch(Map<String,String> params){
        try{
            params.put("return_params","user.profiles.profile.user_id,user.profiles.basic.name,user.profiles.basic.email");
            Response res=searchService.userQuery(params);
            logger.info(JSON.toJSONString(res));
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                List<InviteToDelivyUserInfo> result=this.convertInviteData(data);
                return result;
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    private List<InviteToDelivyUserInfo>  talentEmailInviteInfoSearch(List<Map<String,String>> params,int page,int pageSize){
        try{
            Response res=searchService.queryProfileFilterUserIdList(params,page,pageSize);
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                List<InviteToDelivyUserInfo> result=this.convertInviteData(data);
                return result;
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    /*
     处理es的数据，整理得到es的链接
     */
    private List<InviteToDelivyUserInfo> convertInviteData(Map<String,Object> result){
        List<InviteToDelivyUserInfo> list=new ArrayList<>();
        if(!StringUtils.isEmptyMap(result)){
            int totalNum=Integer.parseInt(String.valueOf(result.get("totalNum")));
            if(totalNum>0){
                List<Map<String,Object>> dataList=(List<Map<String,Object>>)result.get("users");
                for(Map<String,Object> map:dataList){
                    InviteToDelivyUserInfo info=new InviteToDelivyUserInfo();
                    if(map!=null&&!map.isEmpty()){
                        Map<String,Object> userMap=(Map<String,Object>)map.get("user");
                        if(userMap!=null&&!userMap.isEmpty()){
                            Map<String,Object> profiles=(Map<String,Object>)userMap.get("profiles");
                            logger.info(JSON.toJSONString(profiles));
                            if(profiles!=null&&!profiles.isEmpty()){
                                Map<String,Object> profile=(Map<String,Object>)profiles.get("profile");
                                if(profile!=null&&!profile.isEmpty()){
                                    int userId=Integer.parseInt(String.valueOf(profile.get("user_id")));
                                    info.setUserId(userId);
                                }
                                Map<String,Object> basic=(Map<String,Object>)profiles.get("basic");
                                if(!StringUtils.isEmptyMap(basic)){
                                    String name=(String)basic.get("name");
                                    String email=(String)basic.get("email");
                                    info.setEmail(email);
                                    info.setName(name);
                                }

                                list.add(info);
                            }

                        }
                    }
                }
            }
        }
        return list;
    }
    /*
     获取
     */
    private int getPositionInviteNum(List<Map<String,String>> params) {
        try{
            Response res=searchService.queryProfileFilterUserIdList(params,0,0);
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                int totalNum = Integer.parseInt(String.valueOf(data.get("totalNum"))) ;
                return (int)totalNum;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return 0;
    }
    /*
    处理职位数据，公司数据和邀请人员数据，得到符合邮件格式的数据
     */
    private EmailInviteBean handlerData(List<Integer> positionIdList,int companyId,String context,List<InviteToDelivyUserInfo> userInfoList,HrCompanyRecord record,int hrId,int flag){
        try{
            EmailInviteBean result=new EmailInviteBean();
            int count=talentPoolEntity.valiadteMainAccount(hrId,companyId);
            int positionNum=0;
            if(flag==1){
                positionIdList=this.getPositionIds(companyId,hrId,count);
            }else{
                positionNum=positionIdList.size();
                if(positionNum>10){
                    positionIdList=positionIdList.subList(0,10);
                }
            }
            TalentEmailInviteToDelivyInfo delivyInfo=this.getInviteToDelivyInfoList(positionIdList,companyId,context,record);
            logger.info("=======转换response为List<TalentEmailInviteToDelivyInfo> ===========");
            logger.info(JSON.toJSONString(delivyInfo));
            logger.info("================================================");
            if(delivyInfo==null||StringUtils.isEmptyList(userInfoList)){
                return null;
            }
            List<TalentEmailInviteToDelivyInfo> mergeVars=new ArrayList<>();
            UserHrAccountRecord hrAccountRecord=this.getUserHrInfo(hrId);
            List<ReceiveInfo> receiveInfos=new ArrayList<>();
            for(InviteToDelivyUserInfo userInfo:userInfoList){
                TalentEmailInviteToDelivyInfo delivyInfo1=this.convertDelivyInfo(delivyInfo);
                String name=userInfo.getName();
                String email=userInfo.getEmail();
                int userId=userInfo.getUserId();
                if(StringUtils.isNotNullOrEmpty(email)){
                    ReceiveInfo receiveInfo=new ReceiveInfo();
                    receiveInfo.setToName(name);
                    receiveInfo.setToEmail(email);
                    receiveInfos.add(receiveInfo);
                    delivyInfo1.setRcpt(email);
                    String context1= CommonUtils.replaceUtil(context,delivyInfo1.getCompanyAbbr(),delivyInfo1.getPositionName(),name,hrAccountRecord.getUsername(),delivyInfo1.getOfficialAccountName());
                    delivyInfo1.setCustomText(context1);
                    delivyInfo1.setEmployeeName(name);
                    if(flag==1){
                        delivyInfo1.setPositionNum(this.getPositionIdNum(companyId,hrId,count)+"");
                        String url=env.getProperty("talentpool.allposition")+this.getCompanyIds(count,companyId,hrId);
                        delivyInfo1.setSeeMorePosition(url);
                    }else{
                        if(positionNum>10){
                            delivyInfo1.setPositionNum(positionNum+"");
                            String url=env.getProperty("talentpool.allposition")+this.getCompanyIds(count,companyId,hrId);
                            delivyInfo1.setSeeMorePosition(url);
                        }
                    }
                    mergeVars.add(delivyInfo1);
                }
            }
            if(StringUtils.isEmptyList(mergeVars)||StringUtils.isEmptyList(receiveInfos)){
                return null;
            }
            result.setMergeVars(mergeVars);
            result.setTemplateName("invite-to-delivy");
            result.setTo(receiveInfos);
            result.setFromName(record.getAbbreviation()+"人才招聘团队");
            result.setFromEmail("info@moseeker.net");
            result.setSubject(record.getAbbreviation()+"邀请您投递职位");
            return result;

        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }


    private TalentEmailInviteToDelivyInfo convertDelivyInfo(TalentEmailInviteToDelivyInfo info){
        TalentEmailInviteToDelivyInfo info1=new TalentEmailInviteToDelivyInfo();
        info1.setCompanyAbbr(info.getCompanyAbbr());
        info1.setCompanyLogo(info.getCompanyLogo());
        info1.setEmployeeName(info.getEmployeeName());
        info1.setCustomText(info.getCustomText());
        info1.setPositions(info.getPositions());
        info1.setPositionNum(info.getPositionNum());
        info1.setSeeMorePosition(info.getSeeMorePosition());
        info1.setWeixinQrcode(info.getWeixinQrcode());
        info1.setOfficialAccountName(info.getOfficialAccountName());
        info1.setRcpt(info.getRcpt());
        info1.setPositionName(info.getPositionName());
        return info1;
    }

    /*
     获取hr所属的公司
     */
    private int getCompanyIds(int count,int companyId,int hrId){
        int result=companyId;
        if(count==0){
            List<Integer> accountIdList=new ArrayList<>();
            accountIdList.add(hrId);
            List<HrCompanyAccountRecord> list=getCompanyAccountList(accountIdList);
            if(!StringUtils.isEmptyList(list)){
                result=list.get(0).getCompanyId();
            }

        }
        return result;
    }
    /*
    获取hrId下的10个职位
     */
    private List<Integer> getPositionIds(int companyId,int hrId,int count){

        List<JobPositionRecord> records=new ArrayList<>();
        if(count==0){
            Query query=new Query.QueryBuilder().where("publisher",hrId).and("status",0).setPageNum(1).setPageSize(10).buildQuery();
            records=jobPositionDao.getRecords(query);
        }else{
            Query query=new Query.QueryBuilder().where("company_id",companyId).and("status",0).setPageNum(1).setPageSize(10).buildQuery();
            records=jobPositionDao.getRecords(query);
        }
        if(!StringUtils.isEmptyList(records)){
            List<Integer> positionIdList=new ArrayList<>();
            for(JobPositionRecord jobPositionRecord:records){
                positionIdList.add(jobPositionRecord.getId());
            }
            return positionIdList;
        }
        return null;
    }
    private int getPositionIdNum(int companyId,int hrId,int  count){
        int totalNum=0;
        if(count==0){
            Query query=new Query.QueryBuilder().where("publisher",hrId).and("status",0).buildQuery();
            totalNum =jobPositionDao.getCount(query);
        }else{
            Query query=new Query.QueryBuilder().where("company_id",companyId).and("status",0).buildQuery();
            totalNum =jobPositionDao.getCount(query);
        }

        return totalNum;
    }

    /*
    组装邀请投递的信息
     */
    public TalentEmailInviteToDelivyInfo getInviteToDelivyInfoList(List<Integer> positionIdList,int companyId,String context, HrCompanyRecord record) throws TException {
        List<JobPositionRecord> positionList=this.getPositionList(positionIdList);
        HrWxWechatRecord wechatRecord=getWxInfo(companyId);
        if(!StringUtils.isEmptyList(positionList)){
            TalentEmailInviteToDelivyInfo delivyInfo=new TalentEmailInviteToDelivyInfo();
            List<Integer> publisherList=this.getPublisherList(positionList);
            Map<Integer,HrCompanyRecord> publisherCompany=this.getHrCompanyPublisher(publisherList,record);
            if(StringUtils.isEmptyMap(publisherCompany)){
                return null;
            }
            List<PositionInfo> positionInfoList=new ArrayList<>();
            delivyInfo.setCompanyAbbr(record.getAbbreviation());
            delivyInfo.setCompanyLogo(CommonUtils.appendUrl(record.getLogo(),env.getProperty("http.cdn.url")));
            delivyInfo.setCustomText(context);
            delivyInfo.setPositionNum(positionIdList.size()+"");
            delivyInfo.setOfficialAccountName(wechatRecord.getName());
            delivyInfo.setWeixinQrcode(CommonUtils.appendUrl(wechatRecord.getQrcode(),env.getProperty("http.cdn.url")));
            delivyInfo.setSeeMorePosition(null);
            List<Integer> teamIdList=this.getTeamIdList(positionList);
            Map<Integer,String> positionPic=this.getPositionPicture(teamIdList,positionList,record);
            Map<Integer,String> positionCitys=this.getPositionCity(positionIdList);
            String positionName="";
            int i=1;
            for(JobPositionRecord jobPositionRecord:positionList){
                int publisher=jobPositionRecord.getPublisher();
                PositionInfo positionInfo=new  PositionInfo();
                for(Integer key:publisherCompany.keySet()){
                    if(key==publisher){
                        HrCompanyRecord hrCompanyRecord=publisherCompany.get(key);
                        positionInfo.setCompanyAbbr(hrCompanyRecord.getAbbreviation());

                        break;
                    }
                }
                positionName=positionName+jobPositionRecord.getTitle()+",";
                if(i%2==0){
                    positionInfo.setRow("0");
                }else{
                    positionInfo.setRow("1");
                }
                if(!StringUtils.isEmptyMap(positionCitys)){
                    positionInfo.setCompanyAddr(positionCitys.get(jobPositionRecord.getId()));
                }
                String workYear=jobPositionRecord.getExperience();
                int above=jobPositionRecord.getExperienceAbove();
                if(StringUtils.isNotNullOrEmpty(workYear)){
                    if(above>0){
                        workYear=workYear+"年工作经验及以上";
                    }else{
                        workYear=workYear+"年工作经验";
                    }
                }
                positionInfo.setWorkYear(workYear);
                positionInfo.setPositionUrl(env.getProperty("talentpool.singleposition").replace("{{position_id}}",jobPositionRecord.getId()+""));
                String salary="";
                int salaryBottom=jobPositionRecord.getSalaryBottom();
                int salaryTop=jobPositionRecord.getSalaryTop();
                if(salaryBottom==0&&salaryTop==0){
                    salary="薪资面议";
                }else{
                    salary=salaryBottom+"K - "+salaryTop+"K";
                }
                positionInfo.setSalary(salary);
                if(positionPic!=null&&!positionPic.isEmpty()){
                    positionInfo.setPositionBg(CommonUtils.appendUrl(positionPic.get(jobPositionRecord.getId()),env.getProperty("http.cdn.url")));
                    positionInfo.setPositionName(jobPositionRecord.getTitle());
                }
                i++;
                positionInfoList.add(positionInfo);
            }
            if(StringUtils.isNotNullOrEmpty(positionName)){
                positionName=positionName.substring(0,positionName.lastIndexOf(","));
            }
            delivyInfo.setPositionName(positionName);
            delivyInfo.setPositions(positionInfoList);
            return delivyInfo;
        }
        return null;
    }

    /*
     获取职位的地点
     */
    private Map<Integer,String> getPositionCity(List<Integer> positionIdList){
        Map<Integer,List<String>> result=pcRevisionEntity.handlePositionCity(positionIdList);
        if(!StringUtils.isEmptyMap(result)){
            Map<Integer,String> dataResult=new HashMap<>();
            for(Integer key:result.keySet()){
                List<String> list=result.get(key);
                String positionCitys="";
                if(!StringUtils.isEmptyList(list)){
                    for(String item:list){
                        positionCitys+=item+",";
                    }
                    if(StringUtils.isNotNullOrEmpty(positionCitys)){
                        positionCitys=positionCitys.substring(0,positionCitys.lastIndexOf(","));
                    }
                }
                dataResult.put(key,positionCitys);

            }
            return dataResult;
        }
        return null;
    }
    /*
     获取团队id的列表
     */
    private List<Integer> getTeamIdList(List<JobPositionRecord> positionList){
         if(StringUtils.isEmptyList(positionList)){
             return null;
         }
         List<Integer> teamIdList=new ArrayList<>();
         for(JobPositionRecord record:positionList){
            if(record.getTeamId()!=0){
                teamIdList.add(record.getTeamId());
            }
         }
         return teamIdList;
    }

    //注意验证公司是否开启，一会补上
    public Map<Integer,String> getPositionPicture(List<Integer> teamIdList,List<JobPositionRecord> positionList,HrCompanyRecord record) throws TException {
        Map<Integer,String> result=new HashMap<>();
        DictIndustryRecord dictIndustryRecord=this.getIndustryInfo(record.getIndustry());
        DictIndustryTypeRecord dictIndustryTypeRecord=this.getIndustryTypeInfo(dictIndustryRecord.getType());
        String banner=record.getBanner();
        if(StringUtils.isNotNullOrEmpty(banner)){
            Map<String,String> bannerMap= (Map<String, String>) JSON.parse(banner);
            if(!StringUtils.isEmptyMap(bannerMap)){
                banner=bannerMap.get("banner0");
            }else{
                banner="";
            }

        }else{
            banner="";
        }
        if(!StringUtils.isEmptyList(teamIdList)){
            List<Map<String,Object>> list=pcRevisionEntity.HandleCmsResource(teamIdList,3);

            if(StringUtils.isEmptyList(list)){

                if(StringUtils.isNotNullOrEmpty(banner)){
                    for(JobPositionRecord jobPositionRecord:positionList){
                        result.put(jobPositionRecord.getId(),banner);
                    }
                }else{
                    for(JobPositionRecord jobPositionRecord:positionList){

                        result.put(jobPositionRecord.getId(),dictIndustryTypeRecord.getJobImg());
                    }
                }

            }else {
                for (JobPositionRecord jobPositionRecord : positionList) {
                    logger.info("======================================");
                    logger.info(jobPositionRecord.toString());
                    logger.info("======================================");
                    int teamId = jobPositionRecord.getTeamId();
                    int flag = 0;
                    if (teamId != 0) {
                        for (Map<String, Object> map : list) {
                            Integer configId = (Integer) map.get("configId");
                            if (teamId == configId) {
                                if (map.get("imgUrl") != null) {
                                    result.put(jobPositionRecord.getId(), (String) map.get("imgUrl"));
                                    flag=1;
                                }
                                break;
                            }
                        }
                    }
                    if (flag == 0) {
                        if(StringUtils.isNotNullOrEmpty(banner)){
                            result.put(jobPositionRecord.getId(), banner);
                        }else{
                            result.put(jobPositionRecord.getId(), dictIndustryTypeRecord.getJobImg());
                        }
                    }

                }
            }
        }else{
            if(StringUtils.isNotNullOrEmpty(banner)){
                for(JobPositionRecord jobPositionRecord:positionList){
                    result.put(jobPositionRecord.getId(),banner);
                }
            }else{
                for(JobPositionRecord jobPositionRecord:positionList){
                    result.put(jobPositionRecord.getId(),dictIndustryTypeRecord.getJobImg());
                }
            }
        }

        return result;
    }

    private DictIndustryRecord getIndustryInfo(String name){
        Query query=new Query.QueryBuilder().where("name",name).buildQuery();
        DictIndustryRecord record=dictIndustryDao.getRecord(query);
        return record;
    }

    private DictIndustryTypeRecord  getIndustryTypeInfo(int code){
        Query query=new Query.QueryBuilder().where("code",code).buildQuery();
        DictIndustryTypeRecord record=dictIndustryTypeDao.getRecord(query);
        return record;
    }

    /*
     获取publisher和公司之间的关系
     */
    private Map<Integer,HrCompanyRecord> getHrCompanyPublisher(List<Integer> publisherList,HrCompanyRecord record){
        if(StringUtils.isEmptyList(publisherList)){
            return null;
        }
        List<HrCompanyAccountRecord> companyAccountRecords=this.getCompanyAccountList(publisherList);
        List<Integer> companyIdList=this.getCompanyIdList(companyAccountRecords);
        List<HrCompanyRecord> companyInfoList=this.getCompanyInfo(companyIdList);
        if(!StringUtils.isEmptyList(companyInfoList)){
            Map<Integer,HrCompanyRecord> result=new HashMap<>();
            for(Integer publisher:publisherList){
                int flag=0;
                for(HrCompanyAccountRecord hrCompanyAccountRecord:companyAccountRecords){
                    int accountId=hrCompanyAccountRecord.getAccountId();
                    int companyId=hrCompanyAccountRecord.getCompanyId();
                    if(publisher==accountId){
                        for(HrCompanyRecord hrCompanyRecord:companyInfoList){
                            if(hrCompanyRecord.getId()==companyId){
                                flag=1;
                                result.put(publisher,hrCompanyRecord);
                                break;
                            }
                        }
                        break;
                    }
                }
                if(flag==0){
                    result.put(publisher,record);
                }
            }
            return result;
        }
        return null;
    }
    /*
     获取companyId
     */
    private List<Integer> getCompanyIdList(List<HrCompanyAccountRecord> companyAccountRecords){
        if(StringUtils.isEmptyList(companyAccountRecords)){
            return null;
        }
        List<Integer> publisherList=new ArrayList<>();
        for(HrCompanyAccountRecord record:companyAccountRecords){
            publisherList.add(record.getCompanyId());
        }
        return publisherList;
    }
    /*
     获取publisher
     */
    private List<Integer> getPublisherList(List<JobPositionRecord> positionList){
        if(StringUtils.isEmptyList(positionList)){
            return null;
        }
        List<Integer> publisherList=new ArrayList<>();
        for(JobPositionRecord record:positionList){
            publisherList.add(record.getPublisher());
        }
        return publisherList;
    }


    /*
     全选批量查询简历
     */
    private EmailResumeBean convertResumeEmailData(List<UserEmployeeDO> employeeList,Map<String,String> params,int companyId,String context,int hrId) throws Exception {
        List<TalentEmailForwardsResumeInfo> dataInfo=this.handlerData(params,companyId,context,hrId);
        EmailResumeBean result=this.convertResumeEmailData(dataInfo,employeeList,context,hrId,companyId);
        logger.info("===============EmailResumeBean=======================");
        logger.info(JSON.toJSONString(result));
        logger.info("======================================");
        return result;
    }
    /*
    根据user-id查询简历组装发送数据
     */
    private EmailResumeBean convertResumeEmailData(List<UserEmployeeDO> employeeList,List<Integer>userIdList,int companyId,String context,int hrId) throws Exception {
        List<TalentEmailForwardsResumeInfo> dataInfo=this.handlerDataResume(userIdList,companyId,context,hrId);
        EmailResumeBean result=this.convertResumeEmailData(dataInfo,employeeList,context,hrId,companyId);
        return result;
    }
    /*
     组装数据为邮件所需数据
     */
    private EmailResumeBean convertResumeEmailData(List<TalentEmailForwardsResumeInfo> dataInfo,List<UserEmployeeDO> employeeList,String context,int hrId,int companyId) throws Exception {
        UserHrAccountRecord record=getUserHrInfo(hrId);
        EmailResumeBean result=new EmailResumeBean();
        if(StringUtils.isEmptyList(employeeList)){
            return null;
        }
        if(StringUtils.isEmptyList(dataInfo)){
            return null;
        }
        if(record==null){
            return null;
        }
        String abbr="";
        List<TalentEmailForwardsResumeInfo> resumeInfoList=new ArrayList<>();
        List<ReceiveInfo> receiveInfos=new ArrayList<>();
        for(UserEmployeeDO DO:employeeList) {
            String email = DO.getEmail();
            String name = DO.getCname();
            if (StringUtils.isNotNullOrEmpty(email)) {
                ReceiveInfo receiveInfo = new ReceiveInfo();
                receiveInfo.setToEmail(email);
                receiveInfo.setToName(name);
                receiveInfos.add(receiveInfo);
            }
        }
        for(TalentEmailForwardsResumeInfo info:dataInfo){
            TalentEmailForwardsResumeInfo info1=this.convertInfo1(info);

            String positionName=info1.getPositionName();
            if(StringUtils.isNullOrEmpty(positionName)){
                positionName="";
            }
            String userName=info1.getUserName();
            if(StringUtils.isNullOrEmpty(userName)){
                userName="";
            }
            String companyAbbr=info.getCompanyAbbr();
            String accountName=info.getOfficialAccountName();
            String subject=this.handlerSubject(companyAbbr,userName,positionName,info.getCompanyName());
            info1.setSubject(subject);
            String context1= CommonUtils.replaceUtil(context,companyAbbr,positionName,userName,record.getUsername(),accountName);
            info1.setCustomText(context1);
            String url=env.getProperty("talentpool.wholeProfile");
            String token="user_id="+info1.getUserId()+"&company_id="+companyId+"&hr_id="+hrId+"&timestamp="+new Date().getTime();
            token=CommonUtils.encryptString(token);
            info1.setResumeLink(url+token);
            resumeInfoList.add(info1);

        }
        if(StringUtils.isEmptyList(resumeInfoList)||StringUtils.isEmptyList(receiveInfos)){
            return null;
        }
        result.setTo(receiveInfos);
        result.setMergeVars(resumeInfoList);
        result.setFromName(abbr+"人才招聘团队");
        result.setFromEmail("info@moseeker.net");
        //转发简历模板
        result.setTemplateName("forward-resume-v3");
        return result;
    }
     /*
      处理邮件主题
      */
     private String handlerSubject(String companyAbbr,String userName,String positionName,String  companyName){
        String subject="【"+companyAbbr+"】请您评审简历";
        if(StringUtils.isNotNullOrEmpty(userName)){
            subject+="-"+userName;
        }
        if(StringUtils.isNotNullOrEmpty(positionName)){
            subject+="-"+positionName;
        }
        if(StringUtils.isNotNullOrEmpty(companyName)){
            subject+="-"+companyName;
        }
        if(subject.length()>107){
            subject=subject.substring(0,107);
        }
        return subject;
     }
     private TalentEmailForwardsResumeInfo convertInfo1(TalentEmailForwardsResumeInfo info){
         TalentEmailForwardsResumeInfo info1=JSON.parseObject(JSON.toJSONString(info),TalentEmailForwardsResumeInfo.class);
         return info1;
     }
    /*
     获取应发送邮件的数量
     */
    private boolean validateBalance(int balance,int emailNum){
        if(balance<emailNum){
            return false;
        }
        return true;
    }
    /*
     校验是否可以发送邮件
     */
    private boolean validateSendEmail(HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord, TalentpoolEmailRecord talentpoolEmailRecord){
        if(hrCompanyEmailInfoRecord==null){
            return false;
        }
        if(hrCompanyEmailInfoRecord.getBalance()==0){
            return false;

        }
        if(talentpoolEmailRecord==null){
            return false;
        }

        return true;
    }
    /*
     根据params查询es组织数据，获得需要发送的邮件内容
     */
    private List<TalentEmailForwardsResumeInfo> handlerData(Map<String,String> params,int companyId,String context,int hrId){
        List<TalentEmailForwardsResumeInfo> dataList=this.talentEmailInfoSearch(params,hrId,companyId);
        dataList=this.handlerData(dataList,companyId,context,hrId);
        return dataList;
    }
    /*
     根据user_id查询es组织数据，获得需要发送的邮件内容
     */
    private List<TalentEmailForwardsResumeInfo> handlerDataResume(List<Integer>userIdList,int companyId,String context,int hrId){
        List<TalentEmailForwardsResumeInfo> dataList=this.talentEmailInfoSearch(userIdList,hrId,companyId);
        dataList=this.handlerData(dataList,companyId,context,hrId);
        return dataList;
    }
    /*
     组织数据，获得需要发送的邮件内容
     */
    private List<TalentEmailForwardsResumeInfo> handlerData(List<TalentEmailForwardsResumeInfo> dataList,int companyId,String context,int hrId){
        if(!StringUtils.isEmptyList(dataList)){
            HrCompanyRecord hrCompanyRecord=this.getHrCompanyRecord(hrId,companyId);
            HrWxWechatRecord hrWxWechatRecord=this.getWxInfo(companyId);
            for(TalentEmailForwardsResumeInfo info:dataList){
                if(hrCompanyRecord!=null){
                    if(StringUtils.isNotNullOrEmpty(hrCompanyRecord.getLogo())){
                        info.setCompanyLogo(CommonUtils.appendUrl(hrCompanyRecord.getLogo(),env.getProperty("http.cdn.url")));
                    }else{
                        info.setCompanyLogo("http://cdn.moseeker.com/hr/common/images/default-company-logo.jpg");
                    }
                    info.setCompanyName(hrCompanyRecord.getName());
                    info.setCompanyAbbr(hrCompanyRecord.getAbbreviation());
                }
                if(hrWxWechatRecord!=null){
                    info.setOfficialAccountName(hrWxWechatRecord.getName());
                    String qrCode=hrWxWechatRecord.getQrcode();
                    if(StringUtils.isNotNullOrEmpty(qrCode)){
                        info.setWeixinQrcode(qrCode);
                    }
                }
                info.setCustomText(context);
            }
        }
        return dataList;
    }
    /*
      获取被发送人的信息
     */
    private List<TalentEmailForwardsResumeInfo> talentEmailInfoSearch(Map<String,String> params,int hrId,int companyId){
        try{
           Response res=searchService.userQuery(params);
           logger.info("=========response ================");
           logger.info(JSON.toJSONString(res));
           logger.info("==================================");
           if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
              Map<String,Object> data= JSON.parseObject(res.getData());
               List<TalentEmailForwardsResumeInfo> result=this.convertData(data,hrId,companyId);
               logger.info("=========List<TalentEmailForwardsResumeInfo>  ================");
               logger.info(JSON.toJSONString(result));
               logger.info("==================================");
               return result;
           }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    private List<TalentEmailForwardsResumeInfo> talentEmailInfoSearch(List<Integer>UserIdList,int hrId,int companyId){
        try{
            Response res=searchService.userQueryById(UserIdList);
            logger.info("=========response ================");
            logger.info(JSON.toJSONString(res));
            logger.info("==================================");
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                List<TalentEmailForwardsResumeInfo> result=this.convertData(data,hrId,companyId);
                logger.info("=========List<TalentEmailForwardsResumeInfo>  ================");
                logger.info(JSON.toJSONString(result));
                logger.info("==================================");
                return result;
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }


    /*
     获取所需要收件人的信息的数据
     */
    private List<TalentEmailForwardsResumeInfo> convertData(Map<String,Object> result,int hrId,int companyId){
        List<TalentEmailForwardsResumeInfo> list=new ArrayList<>();
        if(!StringUtils.isEmptyMap(result)){
            int totalNum=Integer.parseInt(String.valueOf(result.get("totalNum")));
            if(totalNum>0){
                List<Map<String,Object>> dataList=(List<Map<String,Object>>)result.get("users");
                for(Map<String,Object> map:dataList){
                    TalentEmailForwardsResumeInfo info=new TalentEmailForwardsResumeInfo();
                    if(map!=null&&!map.isEmpty()){
                        Map<String,Object> userMap=(Map<String,Object>)map.get("user");
                        if(userMap!=null&&!userMap.isEmpty()){
                            Map<String,Object> profiles=(Map<String,Object>)userMap.get("profiles");
                            logger.info(JSON.toJSONString(profiles));
                            if(profiles!=null&&!profiles.isEmpty()){
                                int userId=this.handlerProfileUserId(profiles,info);
                                this.handlerProfileBasicData(profiles,info);
                                List<Map<String,Object>> applist=(List<Map<String,Object>>)userMap.get("applications");
                                this.handlerProfileData(profiles,info);
                                info.setPositionName(this.getPositionName(applist,hrId,companyId));
                                this.handlerProfileOtherData(userId,hrId,info);
                                list.add(info);
                            }

                        }
                    }
                }
            }
        }
        return list;
    }
    /*
     处理简历自定义字段
     */
    private void handlerProfileOtherData(int userId,int hrId,TalentEmailForwardsResumeInfo info){
        try{
            Response res=profileOtherService.getProfileOtherByPosition(userId,hrId,0);
            if(res.getStatus()==0&&StringUtils.isNotNullOrEmpty(res.getData())){
                Map<String,Object> otherData= (Map<String, Object>) JSON.parse(res.getData());
                List<Map<String,Object>>keyvaluesList= (List<Map<String, Object>>) otherData.get("keyvalues");
                List<Map<String,Object>> internshipList= (List<Map<String, Object>>) otherData.get("internship");
                List<Map<String,Object>> schoolWorkList= (List<Map<String, Object>>) otherData.get("schooljob");
                List<TalentOtherInternshipInfo> internList=this.handlerTalentOtherInternShipData(internshipList);
                List<TalentOtherSchoolWorkInfo> schoolList=this.handlerTalentOtherSchoolWorkData(schoolWorkList);
                if(!StringUtils.isEmptyList(internList)){
                    info.setOtherInternship(internList);
                }
                if(!StringUtils.isEmptyList(schoolList)){
                    info.setOtherSchoolWork(schoolList);
                }
                List<Message> schoolData=ProfileOtherSchoolType.getMessageList(keyvaluesList);
                List<Message> identityData=ProfileOtherIdentityType.getMessageList(keyvaluesList);
                List<Message> careerData=ProfileOtherCareerType.getMessageList(keyvaluesList);
                if(!StringUtils.isEmptyList(schoolData)){
                    info.setOtherSchool(schoolData);
                }
                if(!StringUtils.isEmptyList(identityData)){
                    info.setOtherIdentity(identityData);
                }
                if(!StringUtils.isEmptyList(careerData)){
                    info.setOtherCareer(careerData);
                }
                if(!StringUtils.isNullOrEmpty((String)otherData.getOrDefault("photo",""))){
                    info.setOtherIdPhoto(CommonUtils.appendUrl((String)otherData.getOrDefault("photo",""), env.getProperty("http.cdn.url")));
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }

    }
    /*
     处理自定义字段的曾任职位相关的
     */
    private List<TalentOtherInternshipInfo> handlerTalentOtherInternShipData(List<Map<String,Object>> internshipList){
        if(StringUtils.isEmptyList(internshipList)){
            return null;
        }
        List<TalentOtherInternshipInfo> list=new ArrayList<>();
        for(Map<String,Object> data:internshipList){
            TalentOtherInternshipInfo info=new TalentOtherInternshipInfo();
            String start=(String)data.getOrDefault("internshipStart","");
            String end=(String)data.getOrDefault("internshipEnd","");
            int endUntilNow=(int)data.getOrDefault("internshipEndUntilNow",0);
            if(endUntilNow==1){
                end="至今";
            }else{
                end=end.substring(0,7).replace("-",".");
            }
            if(StringUtils.isNotNullOrEmpty(start)){
                start=start.substring(0,7).replace("-",".");

            }
            info.setTime(start+"-"+end);
            info.setCompany((String)data.getOrDefault("internshipCompanyName",""));
            info.setDepartment((String)data.getOrDefault("internshipDepartmentName",""));
            info.setPosition((String)data.getOrDefault("internshipJob",""));
            String description = (String)data.getOrDefault("internshipDescriptionHidden","");
            info.setDescription(mandrillMailListConsumer.replaceHTMLEnterToBr(description));
            list.add(info);
        }
        return list;
    }
    /*
     处理自定义字段的在校工作相关的
     */
    private List<TalentOtherSchoolWorkInfo> handlerTalentOtherSchoolWorkData(List<Map<String,Object>> schoolWorkList){
        if(StringUtils.isEmptyList(schoolWorkList)){
            return null;
        }
        List<TalentOtherSchoolWorkInfo> list=new ArrayList<>();
        for(Map<String,Object> data:schoolWorkList){
            TalentOtherSchoolWorkInfo info=new TalentOtherSchoolWorkInfo();
            String start=(String)data.getOrDefault("schooljobStart","");
            String end=(String)data.getOrDefault("schooljobEnd","");
            int endUntilNow=(int)data.getOrDefault("schooljobEndUntilNow",0);
            if(endUntilNow==1){
                end="至今";
            }else{
                end=end.substring(0,7).replace("-",".");
            }
            if(StringUtils.isNotNullOrEmpty(start)){
                start=start.substring(0,7).replace("-",".");
            }
            info.setTime(start+"-"+end);
            String description = (String)data.getOrDefault("schooljobDescriptionHidden","");
            info.setDescription(mandrillMailListConsumer.replaceHTMLEnterToBr(description));
            info.setName((String)data.getOrDefault("schooljobJob",""));
            list.add(info);
        }
        return list;
    }
    /*
     处理profiles
     */
    private int handlerProfileUserId(Map<String,Object> profiles,TalentEmailForwardsResumeInfo info){
        Map<String,Object> profile=(Map<String,Object>)profiles.get("profile");
        if(profile!=null&&!profile.isEmpty()){
            int userId=Integer.parseInt(String.valueOf(profile.get("user_id")));
            info.setUserId(userId);
            return userId;
        }
        return 0;
    }
    /*
     处理简历通用信息
     */
    private void handlerProfileBasicData(Map<String,Object> profiles,TalentEmailForwardsResumeInfo info){
        Map<String,Object> basic=(Map<String,Object>)profiles.get("basic");
        if(!StringUtils.isEmptyMap(basic)){
            String userName=(String)basic.get("name");
            String heading=(String)basic.get("headimg");
            String genderName=(String)basic.get("gender_name");
            String cityName=(String)basic.get("city_name");
            String nationName=(String)basic.getOrDefault("nationality_name","");
            int age=(int)basic.getOrDefault("age",0);
            info.setUserName(userName);
            if(StringUtils.isNotNullOrEmpty(heading)) {
                info.setHeadimg(CommonUtils.appendUrl(heading, env.getProperty("http.cdn.url")));
            }else{
                info.setHeadimg(env.getProperty("email.user.heading.url"));
            }
            info.setCityName(cityName);
            info.setGenderName(genderName);
            info.setIndustryName(nationName);
            info.setAge(age);

            /***---转发简历显示联系方式 by lcf--- ***/
            //联系方式
            String mobile = basic.get("mobile") == null ? "" : String.valueOf(basic.get("mobile"));
            info.setMobile(mobile);
            //电子邮件
            String email = (String) basic.getOrDefault("email","");
            info.setEmail(email);
        }
    }
    private void handlerProfileData(Map<String,Object> profiles,TalentEmailForwardsResumeInfo info){
        Map<String,Object> basic=(Map<String,Object>)profiles.get("basic");
        Map<String,Object> recentJob=(Map<String,Object>)profiles.get("recent_job");
        List<Map<String,Object>> expJob=(List<Map<String,Object>>)profiles.get("other_workexps");
        List<Map<String,Object>> educationsList=(List<Map<String,Object>>)profiles.get("educations");
        List<Map<String,Object>> otherDataList= (List<Map<String, Object>>) profiles.get("others");
        List<Map<String,Object>> intentionList= (List<Map<String, Object>>) profiles.get("intentions");
        List<Map<String,Object>> skillsList=(List<Map<String,Object>>)profiles.get("skills");
        List<Map<String,Object>> languagesList=(List<Map<String,Object>>)profiles.get("languages");
        List<Map<String,Object>> credentialsList=(List<Map<String,Object>>)profiles.get("credentials");
        List<Map<String,Object>> worksList=(List<Map<String,Object>>)profiles.get("works");
        List<Map<String,Object>> projectExpList=(List<Map<String,Object>>)profiles.get("projectexps");
        info.setEduExps(this.handlerTalentEducationInfoData(educationsList));
        info.setBasicInfo(this.handlerTalentBasicInfoData(basic,educationsList,otherDataList,recentJob));
        info.setIntention(this.handlerTalentIntentionInfoData(intentionList));
        info.setWorkExps(this.handlerTalentWorkExpInfoData(recentJob,expJob));
        info.setLanguages(this.handlerLanguagesInfoData(languagesList));
        info.setProExps(this.handlerTalentProjectExpsInfodata(projectExpList));
        info.setSkills(this.handlerTalentSkillsData(skillsList));
        info.setCredentials(this.handlerTalentCredentialsData(credentialsList));
        info.setWorks(this.handlerTalentWorksInfo(worksList));
    }

    private TalentBasicInfo handlerTalentBasicInfoData(Map<String,Object> basicData,List<Map<String,Object>> eduList,List<Map<String,Object>> otherDataList,Map<String,Object>works){
        if(StringUtils.isEmptyMap(basicData)&&StringUtils.isEmptyList(eduList)){
            return null;
        }
        TalentBasicInfo basicInfo=new TalentBasicInfo();
        if(!StringUtils.isEmptyMap(basicData)){
            basicInfo.setBirth((String)basicData.getOrDefault("birth",""));
        }
        if(!StringUtils.isEmptyList(eduList)){
            Map<String,Object> education=eduList.get(0);
            basicInfo.setDegree((String)education.get("degree_name"));
            basicInfo.setMajor((String)education.getOrDefault("major_name",""));
            basicInfo.setCollege((String)education.getOrDefault("college_name",""));
        }
        if(!StringUtils.isEmptyMap(works)){
            basicInfo.setPosition((String)works.getOrDefault("job_name",""));
        }
        //==========================================
        //==========================================
        return basicInfo;
    }
    /*
     处理求职意向的数据
     */
    private TalentIntentionInfo handlerTalentIntentionInfoData(List<Map<String,Object>> intentionsList){
        if(StringUtils.isEmptyList(intentionsList)){
            return null;
        }
        TalentIntentionInfo info=new TalentIntentionInfo();
        Map<String,Object> intention=intentionsList.get(0);
        List<Map<String,Object>> cityList= (List<Map<String, Object>>) intention.get("cities");
        List<Map<String,Object>> positionList=(List<Map<String,Object>>)intention.get("positions");
        List<Map<String,Object>> industryList=(List<Map<String,Object>>)intention.get("industries");
        info.setCity(this.handlerListdata(cityList,"city_name"));
        info.setJob(this.handlerListdata(positionList,"position_name"));
        info.setIndustry(this.handlerListdata(industryList,"industry_name"));
        info.setWorkStatus((String)intention.get("workstate_name"));
        info.setMonthSalary((String)intention.get("salary_code_name"));
        info.setEmployeeType((String)intention.get("worktype_name"));
        return info;
    }
    /*
    处理List<Map<String,Object>>数据
    */
    private String handlerListdata( List<Map<String,Object>> dataList,String field){
        if(StringUtils.isEmptyList(dataList)){
            return "";
        }
        String datas="";
        for(Map<String,Object> data:dataList){
            datas+=data.get(field)+",";
        }
        if(StringUtils.isNotNullOrEmpty(datas)){
            datas=datas.substring(0,datas.lastIndexOf(","));
        }
        return datas;
    }
    /*
     处理工作经历数据
     */
    private List<TalentWorkExpInfo> handlerTalentWorkExpInfoData(Map<String,Object> recentJob,List<Map<String,Object>>  workExpList){
        if(StringUtils.isEmptyMap(recentJob)&&StringUtils.isEmptyList(workExpList)){
            return null;
        }
        List<TalentWorkExpInfo> list=new ArrayList<>();
        TalentWorkExpInfo info1=this.handlerTalentWorkExpSingleData(recentJob);
        if(info1!=null){
            list.add(info1);
        }
        for(Map<String,Object> workExp:workExpList){
            TalentWorkExpInfo data=this.handlerTalentWorkExpSingleData(workExp);
            if(data!=null){
                list.add(data);
            }
        }
        return list;
    }
    /*
    处理单个TalentWorkExpInfo
     */
    private TalentWorkExpInfo handlerTalentWorkExpSingleData(Map<String,Object> data){
        if(StringUtils.isEmptyMap(data)){
            return null;
        }
        TalentWorkExpInfo info=new TalentWorkExpInfo();
        String start= (String) data.get("start_date");
        String end= (String) data.get("end_date");
        int endUntilNow= (int) data.get("end_until_now");
        if(endUntilNow==1){
            end="至今";
        }else{
            end=end.substring(0,7).replace("-",".");
        }
        if(StringUtils.isNotNullOrEmpty(start)){
            start=start.substring(0,7).replace("-",".");
        }
        info.setTime(start+"-"+end);
        info.setCompany((String)data.getOrDefault("company_name",""));
        info.setDepartment((String)data.getOrDefault("department_name",""));
        info.setPosition((String)data.getOrDefault("job_name",""));
        String description = (String)data.getOrDefault("description","");
        info.setDescription(mandrillMailListConsumer.replaceHTMLEnterToBr(description));
        return info;
    }
    /*
    处理简历教育数据
     */
    private List<TalentEducationInfo> handlerTalentEducationInfoData(List<Map<String,Object>> educationList ){
        if(StringUtils.isEmptyList(educationList)){
            return null;
        }
        List<TalentEducationInfo> list=new ArrayList<>();
        for(Map<String,Object> data:educationList){
            TalentEducationInfo info=new TalentEducationInfo();
            String start= (String) data.get("start_date");
            String end= (String) data.get("end_date");
            int endUntilNow= (int) data.get("end_until_now");
            if(endUntilNow==1){
                end="至今";
            }else{
                end=end.substring(0,7).replace("-",".");
            }
            if(StringUtils.isNotNullOrEmpty(start)){
                start=start.substring(0,7).replace("-",".");
            }
            info.setTime(start+"-"+end);
            info.setCollege((String)data.getOrDefault("college_name",""));
            info.setDegree(DegreeConvertUtil.intToEnum.get(data.get("degree")));
            info.setMajor((String)data.getOrDefault("major_name",""));
            String description = (String)data.getOrDefault("description","");
            info.setDescription(mandrillMailListConsumer.replaceHTMLEnterToBr(description));
            list.add(info);
        }
        return list;
    }
    /*
     处理项目经历
     */
    private List<TalentProjectExpsInfo> handlerTalentProjectExpsInfodata(List<Map<String,Object>> projectExpList){
        if(StringUtils.isEmptyList(projectExpList)){
            return null;
        }
        List<TalentProjectExpsInfo> list=new ArrayList<>();
        for(Map<String,Object> data:projectExpList){
            TalentProjectExpsInfo info=new TalentProjectExpsInfo();
            String start= (String) data.get("start_date");
            String end= (String) data.get("end_date");
            int endUntilNow= (int) data.get("end_until_now");
            if(endUntilNow==1){
                end="至今";
            }else{
                end=end.substring(0,7).replace("-",".");
            }
            if(StringUtils.isNotNullOrEmpty(start)){
                start=start.substring(0,7).replace("-",".");
            }
            info.setTime(start+"-"+end);
            info.setCompany((String)data.getOrDefault("company_name",""));
            info.setName((String)data.getOrDefault("name",""));
            String description = (String)data.getOrDefault("description","");
            info.setDescription(mandrillMailListConsumer.replaceHTMLEnterToBr(description));
            list.add(info);
        }
        return list;
    }
    /*
     处理语言
     */
    private List<TalentLanguagesInfo> handlerLanguagesInfoData(List<Map<String,Object>> languagesList){
        if(StringUtils.isEmptyList(languagesList)){
            return null;
        }
        List<TalentLanguagesInfo> list=new ArrayList<>();
        for(Map<String,Object> data:languagesList){
            TalentLanguagesInfo info=new TalentLanguagesInfo();
            String name=(String)data.getOrDefault("name","");
            String levelName= ProfileLanguagesLevelConvertUtil.intToEnum.get(data.get("level"));
            info.setLanguage(name);
            info.setLevel(levelName);
            list.add(info);
        }
        return list;
    }
    /*
     处理技能
     */
    public List<String> handlerTalentSkillsData(List<Map<String,Object>> skillsList ){
        if(StringUtils.isEmptyList(skillsList)){
            return null;
        }
        List<String> list=new ArrayList<>();
        for(Map<String,Object> data:skillsList){
            String name=(String)data.getOrDefault("name","");
            if(StringUtils.isNotNullOrEmpty(name)){
                list.add(name);
            }
        }
        return list;
    }
    /*
    处理证书
     */
    private List<String> handlerTalentCredentialsData(List<Map<String,Object>> credentialsList){
        if(StringUtils.isEmptyList(credentialsList)){
            return null;
        }
        List<String> list=new ArrayList<>();
        for(Map<String,Object> data:credentialsList){
            String name=(String)data.getOrDefault("name","");
            list.add(name);
        }
        return list;
    }
    /*
     处理Profile的个人作品
     */
    private TalentWorksInfo handlerTalentWorksInfo(List<Map<String,Object>> dataList){
        if(StringUtils.isEmptyList(dataList)){
            return null;
        }
        TalentWorksInfo info=new TalentWorksInfo();
        Map<String,Object> data=dataList.get(0);
        String cover=(String)data.getOrDefault("cover","");
        String url=(String)data.getOrDefault("url","");
        String description=(String)data.getOrDefault("description","");
        info.setCover(cover);
        info.setUrl(url);
        info.setDescription(mandrillMailListConsumer.replaceHTMLEnterToBr(description));
        return info;
    }

    /*
     获取职位信息
     */
    private String getPositionName(List<Map<String,Object>> applications,int hrId,int companyId){
        String positionName="";
        int count=talentPoolEntity.valiadteMainAccount(hrId,companyId);
        if(count>0){
            if(!StringUtils.isEmptyList(applications)){
                for(Map<String,Object> app:applications){
                    int itemId=(int)app.get("company_id");
                    String title=(String)app.get("title");
                    int status=(int)Double.parseDouble(String.valueOf(app.get("status")));
                    if(companyId==itemId&&status==0){
                        positionName=positionName+title+",";
                    }
                }
            }
        }else{
            if(!StringUtils.isEmptyList(applications)){
                for(Map<String,Object> app:applications){
                    int publisher=(int)app.get("publisher");
                    String title=(String)app.get("title");
                    int status=(int)Double.parseDouble(String.valueOf(app.get("status")));
                    if(hrId==publisher&&status==0){
                        positionName=positionName+title+",";
                    }
                }
            }
        }

        if(StringUtils.isNotNullOrEmpty(positionName)){
            positionName=positionName.substring(0,positionName.lastIndexOf(","));
        }
        return positionName;
    }

    /*
     获取hr下的公司
     */
    private HrCompanyRecord getHrCompanyRecord(int hrId,int companyId){
        List<Integer> hrIdList=new ArrayList<>();
        hrIdList.add(hrId);
        List<HrCompanyAccountRecord> recordList=this.getCompanyAccountList(hrIdList);
        if(!StringUtils.isEmptyList(recordList)){
            companyId=recordList.get(0).getCompanyId();
        }
        HrCompanyRecord record=this.getCompanyInfo(companyId);
        return record;
    }

    /*
     获取企业信息
     */
    private HrCompanyRecord getCompanyInfo(int companyId){
        Query query=new Query.QueryBuilder().where("id",companyId).buildQuery();
        HrCompanyRecord record=hrCompanyDao.getRecord(query);
        return record;
    }
    /*
     获取企业二维码信息
     */
    private HrWxWechatRecord getWxInfo(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        HrWxWechatRecord record=hrWxWechatDao.getRecord(query);
        return record;
    }
    /*
     获取公司配置的邮件模板
     */
    private TalentpoolEmailRecord getTalentpoolEmail(int companyId,int type){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("disable",1).and("config_id",type).buildQuery();
        TalentpoolEmailRecord record=talentpoolEmailDao.getRecord(query);
        return record;
    }
    /*
    获取公司配置的邮件模板
    */
    private HrCompanyEmailInfoRecord getHrCompanyEmailInfo(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        HrCompanyEmailInfoRecord record=hrCompanyEmailInfoDao.getRecord(query);
        return record;
    }
    /*
     获取员工信息
     */
    private List<UserEmployeeDO> getUserEmployeeList(List<Integer> idList){
        if(StringUtils.isEmptyList(idList)){
            return null;
        }
        List<UserEmployeeDO> list=userWxEntity.getForWordEmployeeInfo(idList);
        return list;

    }
    /*
     获取职位信息
     */
    private List<JobPositionRecord> getPositionList(List<Integer> idList){
        if(StringUtils.isEmptyList(idList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("id",idList.toArray(), ValueOp.IN))
                .buildQuery();
        List<JobPositionRecord> list=jobPositionDao.getRecords(query);
        return list;

    }
    /*
    获取公司和账号之间的关系
    */
    private List<HrCompanyAccountRecord> getCompanyAccountList(List<Integer> idList){
        if(StringUtils.isEmptyList(idList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("account_id",idList.toArray(), ValueOp.IN))
                .buildQuery();
        List<HrCompanyAccountRecord> list=hrCompanyAccountDao.getRecords(query);
        return list;

    }
    /*
    获取公司信息
     */
    private List<HrCompanyRecord> getCompanyInfo(List<Integer> companyIdList){
        if(StringUtils.isEmptyList(companyIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("id",companyIdList.toArray(),ValueOp.IN)).buildQuery();
        List<HrCompanyRecord> records=hrCompanyDao.getRecords(query);
        return records;
    }
    /*
  获取公司信息
   */
    private UserHrAccountRecord getUserHrInfo(int hrId){
        Query query=new Query.QueryBuilder().where("id",hrId).buildQuery();
        UserHrAccountRecord record=userHrAccountDao.getRecord(query);
        return record;
    }


}
