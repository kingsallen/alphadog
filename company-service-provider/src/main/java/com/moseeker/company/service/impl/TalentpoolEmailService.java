package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.moseeker.baseorm.dao.dictdb.DictIndustryDao;
import com.moseeker.baseorm.dao.dictdb.DictIndustryTypeDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyEmailInfoDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolEmailDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryTypeRecord;
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
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.constants.DegreeConvertUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.company.bean.email.*;
import com.moseeker.entity.Constant.EmailAccountConsumptionType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.company.bean.*;
import com.moseeker.entity.PcRevisionEntity;
import com.moseeker.entity.TalentPoolEmailEntity;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.entity.UserWxEntity;
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
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailListStruct;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import com.moseeker.thrift.gen.searchengine.struct.FilterResp;
import org.apache.thrift.TException;

import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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
    private Environment env;

    SearchengineServices.Iface searchService = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);

    MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);

    private ThreadPool tp = ThreadPool.Instance;

    @Autowired
    private UserWxEntity userWxEntity;

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
                        symbol.add("#职位名称#");
                        symbol.add("#求职者姓名#");
                        symbol.add("#HR姓名#");
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
        int result = talentPoolEmailEntity.updateEmailInfo(company_id, type, disable, context, inscribe);
        if(result >0 ) {
            return ResponseUtils.success("");
        }else if(result == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.EMAIL_SWITCH_FAILED);
        }else if(result == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.EMAIL_CONTEXT_FAILED);
        }else if(result == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.EMAIL_INSCRIBE_FAILED);
        }else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
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
    public int talentPoolSendResumeEmail(List<Integer> idList,Map<String,String> params,List<Integer> userIdList,int companyId,int hrId,int flag){
        int sflag=validateCompanyAndOther(companyId,hrId);
        if(sflag<0){
            return sflag ;
        }
        if(flag==0){
            return sendResumeEmail(idList, userIdList, companyId, hrId);
        }else{
            return sendAllResumeEmail(idList,params,companyId,hrId);
        }
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
                HrCompanyRecord record = this.getCompanyInfo(companyId);
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
                    HrCompanyRecord record=this.getCompanyInfo(companyId);
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
                        HrCompanyRecord record=this.getCompanyInfo(companyId);
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
            params.put("page_number", (i - 1) + "");
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
                logger.info("=============EmailInviteBean===========");
                logger.info(JSON.toJSONString(emailDate));
                logger.info("================================================");
                if(emailDate!=null) {
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
     发送部分转发邮件
     */
    private int sendResumeEmail(List<Integer> idList,List<Integer> userIdList,int companyId,int hrId){
        if(StringUtils.isEmptyList(idList)) {
            return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
        }
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId,73);
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
                List<UserEmployeeDO> employeeList = this.getUserEmployeeList(idList);
                logger.info("=============List<UserEmployeeDO>===========");
                logger.info(JSON.toJSONString(employeeList));
                logger.info("================================================");
                if (!StringUtils.isEmptyList(employeeList)) {
                    int lost = userIdList.size() * employeeList.size();
                    logger.info("lost==================================={}=============",lost);
                    if (!this.validateBalance(hrCompanyEmailInfoRecord.getBalance(), lost)) {
                        return TalentEmailEnum.NOBALANCE.getValue();
                    }
                    EmailResumeBean emailList = this.convertResumeEmailData(employeeList, userIdList, companyId, talentpoolEmailRecord.getContext(), hrId);
                    logger.info("=============EmailResumeBean===========");
                    logger.info(JSON.toJSONString(emailList));
                    logger.info("================================================");
                    updateEmailInfoBalance(companyId, lost,5);
                    MandrillEmailListStruct struct = convertToEmailStruct(emailList);
                    logger.info("=============MandrillEmailListStruct===========");
                    logger.info(JSON.toJSONString(struct));
                    logger.info("================================================");
                    mqService.sendMandrilEmailList(struct);
                } else {
                    return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
                }
                List<Map<String,Object>> employeeData=this.handlerEmployeeData(employeeList);
                if(!StringUtils.isEmptyList(employeeData)){
                    client.setNoTime(Constant.APPID_ALPHADOG, KeyIdentifier.PAST_USER_EMPLOYEE_VALIDATE.toString(),hrId+"",JSON.toJSONString(employeeData,serializeConfig, SerializerFeature.DisableCircularReferenceDetect));
                }

            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }
        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;
    }

    private List<Map<String,Object>> handlerEmployeeData(List<UserEmployeeDO> employeeList) throws TException {
        if(StringUtils.isEmptyList(employeeList)){
            return null;
        }
        List<Map<String,Object>> list=new ArrayList<>();
        for(UserEmployeeDO DO:employeeList){
            String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
            Map<String,Object> DOData=JSON.parseObject(DOs, Map.class);
            list.add(DOData);
        }
        return list;
    }

    /*
     发送全部转发邮件
     */
    private  int sendAllResumeEmail(List<Integer> idList,Map<String,String> params,int companyId,int hrId){
        if(StringUtils.isEmptyList(idList)) {
            return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
        }
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId,73);
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
                int talentNum = searchService.talentSearchNum(params);
                logger.info("符合转发的人数========================{}",talentNum);
                if(talentNum>0){
                    List<UserEmployeeDO> employeeList=this.getUserEmployeeList(idList);
                    logger.info("=============List<UserEmployeeDO>===========");
                    logger.info(JSON.toJSONString(employeeList));
                    logger.info("================================================");
                    if(!StringUtils.isEmptyList(employeeList)){
                        int lost=talentNum*employeeList.size();
                        if(!this.validateBalance(hrCompanyEmailInfoRecord.getBalance(),lost)){
                            return TalentEmailEnum.NOBALANCE.getValue();
                        }
                        updateEmailInfoBalance(companyId,lost,5);
                        tp.startTast(() -> {
                            sendResumeEmailCore(employeeList,params,companyId,hrId,talentpoolEmailRecord.getContext(),talentNum);
                            return 0;
                        });
                    }else{
                        return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
                    }
                    client.setNoTime(Constant.APPID_ALPHADOG, KeyIdentifier.PAST_USER_EMPLOYEE_VALIDATE.toString(),hrId+"",JSON.toJSONString(employeeList));
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
     发送转发邮件的核心处理，使用线程启动他
     */
    private void sendResumeEmailCore(List<UserEmployeeDO> employeeList,Map<String,String> params,int companyId,int hrId,String context,int totalNum){
        int totalPage=(int)Math.ceil((double)totalNum/300);
        for(int i=1;i<=totalPage;i++){
            params.put("page_size",300+"");
            params.put("page_number",(i-1)+"");

            try{
                EmailResumeBean emailList=this.convertResumeEmailData(employeeList,params,companyId,context,hrId);
                logger.info("=============EmailResumeBean===========");
                logger.info(JSON.toJSONString(emailList));
                logger.info("================================================");
                MandrillEmailListStruct struct = convertToEmailStruct(emailList);
                logger.info("=============MandrillEmailListStruct===========");
                logger.info(JSON.toJSONString(struct));
                logger.info("================================================");
                mqService.sendMandrilEmailList(struct);
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
            Map<String,Object> infoMap=JSON.parseObject(infos);
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

    private MandrillEmailListStruct convertToEmailStruct(EmailResumeBean emailInviteBean){
        MandrillEmailListStruct result=new MandrillEmailListStruct();
        result.setTemplateName(emailInviteBean.getTemplateName());
        result.setSubject(emailInviteBean.getSubject());
        result.setFrom_name(emailInviteBean.getFromName());
        result.setFrom_email(emailInviteBean.getFromEmail());
        List<TalentEmailForwardsResumeInfo> merge=emailInviteBean.getMergeVars();
        List<ReceiveInfo> to=emailInviteBean.getTo();
        List<Map<String,String>> toReceive=new ArrayList<>();
        List<Map<String,Object>> mergeData=new ArrayList<>();
        for(ReceiveInfo receiveInfo:to){
            String tores=JSON.toJSONString(receiveInfo,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);;
            Map<String,Object> map=JSON.parseObject(tores);
            Map<String,String> map1=new HashMap<>();
            for(String key:map.keySet()){
                map1.put(key,String.valueOf(map.get(key)));
            }
            toReceive.add(map1);
        }
        for(TalentEmailForwardsResumeInfo info:merge){
            String infos=JSON.toJSONString(info,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);;
            Map<String,Object> infoMap=JSON.parseObject(infos);
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


    /*
    获取邀请投递邮件的人员的信息
    */
    private List<InviteToDelivyUserInfo> talentEmailInviteInfoSearch(List<Integer>UserIdList){
        try{
            Response res=searchService.userQueryById(UserIdList);
            logger.info("=======获取邀请投递邮件的人员的信息======查询es获得结果的Response===========");
            logger.info(JSON.toJSONString(res));
            logger.info("================================================");
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                List<InviteToDelivyUserInfo> result=this.convertInviteData(data);
                logger.info("=======转换response为List<InviteToDelivyUserInfo> ===========");
                logger.info(JSON.toJSONString(result));
                logger.info("================================================");
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
            logger.info("=======获取邀请投递邮件的人员的信息======查询es获得结果的Response===========");
            logger.info(JSON.toJSONString(res));
            logger.info("================================================");
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                List<InviteToDelivyUserInfo> result=this.convertInviteData(data);
                logger.info("=======转换response为List<InviteToDelivyUserInfo> ===========");
                logger.info(JSON.toJSONString(result));
                logger.info("================================================");
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
            logger.info("=======获取邀请投递邮件的人员的信息======查询es获得结果的Response===========");
            logger.info(JSON.toJSONString(res));
            logger.info("================================================");
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                List<InviteToDelivyUserInfo> result=this.convertInviteData(data);
                logger.info("=======转换response为List<InviteToDelivyUserInfo> ===========");
                logger.info(JSON.toJSONString(result));
                logger.info("================================================");
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
            if(flag==1){
                positionIdList=getPositionIds(companyId,hrId,count);
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
                    context= CommonUtils.replaceUtil(context,delivyInfo1.getCompanyAbbr(),delivyInfo1.getPositionName(),name,hrAccountRecord.getUsername(),delivyInfo1.getOfficialAccountName());
                    delivyInfo1.setCustomText(context);
                    delivyInfo1.setEmployeeName(name);
                    if(flag==1){
                        delivyInfo1.setPositionNum(this.getPositionIdNum(companyId,hrId,count)+"");
                        String url=env.getProperty("talentpool.allposition")+this.getCompanyIds(count,companyId,hrId);
                        delivyInfo1.setSeeMorePosition(url);
                    }else{
                        if(positionIdList.size()>10){
                            delivyInfo1.setPositionNum(this.getPositionIdNum(companyId,hrId,count)+"");
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
            Query query=new Query.QueryBuilder().where("publisher",hrId).and("status",0).setPageNum(1).setPageSize(10).buildQuery();
            totalNum =jobPositionDao.getCount(query);
        }else{
            Query query=new Query.QueryBuilder().where("company_id",companyId).and("status",0).setPageNum(1).setPageSize(10).buildQuery();
            totalNum =jobPositionDao.getCount(query);
        }

        return totalNum;
    }


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
            String positionName="";
            for(JobPositionRecord jobPositionRecord:positionList){
                int i=1;
                int publisher=jobPositionRecord.getPublisher();
                PositionInfo positionInfo=new  PositionInfo();
                for(Integer key:publisherCompany.keySet()){
                    if(key==publisher){
                        HrCompanyRecord hrCompanyRecord=publisherCompany.get(key);
                        positionInfo.setCompanyAbbr(hrCompanyRecord.getAbbreviation());
                        positionInfo.setCompanyAddr(hrCompanyRecord.getAddress());
                        break;
                    }
                }
                positionName=positionName+jobPositionRecord.getTitle()+",";
                positionInfo.setRow(i+"");
                positionInfo.setWorkYear(jobPositionRecord.getExperience());
                positionInfo.setSalary(jobPositionRecord.getSalary());
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
    private Map<Integer,String> getPositionPicture(List<Integer> teamIdList,List<JobPositionRecord> positionList,HrCompanyRecord record) throws TException {
        Map<Integer,String> result=new HashMap<>();
        DictIndustryRecord dictIndustryRecord=this.getIndustryInfo(record.getIndustry());
        DictIndustryTypeRecord dictIndustryTypeRecord=this.getIndustryTypeInfo(dictIndustryRecord.getType());
        if(!StringUtils.isEmptyList(teamIdList)){
            List<Map<String,Object>> list=pcRevisionEntity.HandleCmsResource(teamIdList,3);

            if(StringUtils.isEmptyList(list)){
                for(JobPositionRecord jobPositionRecord:positionList){
                    result.put(jobPositionRecord.getId(),dictIndustryTypeRecord.getJobImg());
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
                                }
                                break;
                            }
                        }
                    }

                    if (flag == 0) {
                        result.put(jobPositionRecord.getId(), dictIndustryTypeRecord.getJobImg());
                    }

                }
            }
        }else{
            for(JobPositionRecord jobPositionRecord:positionList){
                result.put(jobPositionRecord.getId(),dictIndustryTypeRecord.getJobImg());
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
        List<TalentEmailForwardsResumeInfo> dataInfo=this.handlerData(userIdList,companyId,context,hrId);
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
        for(UserEmployeeDO DO:employeeList){
            String email=DO.getEmail();
            String name=DO.getCname();
            if(StringUtils.isNotNullOrEmpty(email)){
                ReceiveInfo receiveInfo=new ReceiveInfo();
                receiveInfo.setToEmail(email);
                receiveInfo.setToName(name);
                receiveInfos.add(receiveInfo);
                for(TalentEmailForwardsResumeInfo info:dataInfo){
                    TalentEmailForwardsResumeInfo info1=this.convertInfo1(info);
                    info1.setCoworkerName(name);
                    String companyAbbr=info1.getCompanyAbbr();
                    if(StringUtils.isNullOrEmpty(companyAbbr)){
                        companyAbbr="";
                    }
                    String positionName=info1.getPositionName();
                    if(StringUtils.isNullOrEmpty(positionName)){
                        positionName="";
                    }
                    String userName=info1.getUserName();
                    if(StringUtils.isNullOrEmpty(userName)){
                        userName="";
                    }
                    String accountName=info1.getOfficialAccountName();
                    if(StringUtils.isNullOrEmpty(accountName)){
                        accountName="";
                    }
                    context= CommonUtils.replaceUtil(context,companyAbbr,positionName,userName,record.getUsername(),accountName);
                    info1.setCustomText(context);
                    info1.setRcpt(email);
                    info1.setHrName(record.getUsername());
                    String url=env.getProperty("talentpool.wholeProfile");
                    String token="user_id="+info1.getUserId()+"&company_id="+companyId+"&hr_id="+hrId+"&timestamp="+new Date().getTime();
                    token=CommonUtils.encryptString(token);
                    info1.setProfileFullUrl(url+token);
                    if(StringUtils.isNullOrEmpty(abbr)){
                        abbr=info1.getCompanyAbbr();
                    }
                    resumeInfoList.add(info1);
                }
            }
        }
        if(StringUtils.isEmptyList(resumeInfoList)||StringUtils.isEmptyList(receiveInfos)){
            return null;
        }
        result.setTo(receiveInfos);
        result.setMergeVars(resumeInfoList);
        result.setFromName(abbr+"才招聘团队");
        result.setFromEmail("info@moseeker.net");
        result.setTemplateName("forwards-resume");
        return result;
    }
     private TalentEmailForwardsResumeInfo convertInfo1(TalentEmailForwardsResumeInfo info){
         TalentEmailForwardsResumeInfo info1=new TalentEmailForwardsResumeInfo();
         info1.setCompanyAbbr(info.getCompanyAbbr());
         info1.setCompanyLogo(info.getCompanyLogo());
         info1.setCoworkerName(info.getCoworkerName());
         info1.setCustomText(info.getCustomText());
         info1.setHeading(info.getHeading());
         info1.setUserName(info.getUserName());
         info1.setGenderName(info.getGenderName());
         info1.setCityName(info.getCityName());
         info1.setDegreeName(info.getDegreeName());
         info1.setEducationList(info.getEducationList());
         info1.setWorkexps(info.getWorkexps());
         info1.setWeixinQrcode(info.getWeixinQrcode());
         info1.setOfficialAccountName(info.getOfficialAccountName());
         info1.setEmail(info.getEmail());
         info1.setUserId(info.getUserId());
         info1.setBirth(info.getBirth());
         info1.setPositionName(info.getPositionName());
         info1.setCompanyName(info.getCompanyName());
         info1.setProfileFullUrl(info.getProfileFullUrl());
         info1.setHrName(info.getHrName());
         info1.setRcpt(info.getRcpt());
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
        List<TalentEmailForwardsResumeInfo> dataList=this.talentEmailInfoSearch(params,hrId);
        dataList=this.handlerData(dataList,companyId,context);
        return dataList;
    }
    /*
     根据user_id查询es组织数据，获得需要发送的邮件内容
     */
    private List<TalentEmailForwardsResumeInfo> handlerData(List<Integer>userIdList,int companyId,String context,int hrId){
        List<TalentEmailForwardsResumeInfo> dataList=this.talentEmailInfoSearch(userIdList,hrId);
        dataList=this.handlerData(dataList,companyId,context);
        return dataList;
    }
    /*
     组织数据，获得需要发送的邮件内容
     */
    private List<TalentEmailForwardsResumeInfo> handlerData(List<TalentEmailForwardsResumeInfo> dataList,int companyId,String context){
        if(!StringUtils.isEmptyList(dataList)){
            HrCompanyRecord hrCompanyRecord=this.getCompanyInfo(companyId);
            HrWxWechatRecord hrWxWechatRecord=this.getWxInfo(companyId);
            for(TalentEmailForwardsResumeInfo info:dataList){
                if(hrCompanyRecord!=null){
                    info.setCompanyAbbr(hrCompanyRecord.getAbbreviation());
                    info.setCompanyLogo(CommonUtils.appendUrl(hrCompanyRecord.getLogo(),env.getProperty("http.cdn.url")));
                }
                if(hrWxWechatRecord!=null){
                    info.setWeixinQrcode(CommonUtils.appendUrl(hrWxWechatRecord.getQrcode(),env.getProperty("http.cdn.url")));
                    info.setOfficialAccountName(hrWxWechatRecord.getName());
                }
                info.setCustomText(context);
            }
        }
        return dataList;
    }
    /*
      获取被发送人的信息
     */
    private List<TalentEmailForwardsResumeInfo> talentEmailInfoSearch(Map<String,String> params,int hrId){
        try{
           Response res=searchService.userQuery(params);
           logger.info("=========response ================");
           logger.info(JSON.toJSONString(res));
           logger.info("==================================");
           if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
              Map<String,Object> data= JSON.parseObject(res.getData());
               List<TalentEmailForwardsResumeInfo> result=this.convertData(data,hrId);
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
    private List<TalentEmailForwardsResumeInfo> talentEmailInfoSearch(List<Integer>UserIdList,int hrId){
        try{
            Response res=searchService.userQueryById(UserIdList);
            logger.info("=========response ================");
            logger.info(JSON.toJSONString(res));
            logger.info("==================================");
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                List<TalentEmailForwardsResumeInfo> result=this.convertData(data,hrId);
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
    private List<TalentEmailForwardsResumeInfo> convertData(Map<String,Object> result,int hrId){
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
                                Map<String,Object> profile=(Map<String,Object>)profiles.get("profile");
                                if(profile!=null&&!profile.isEmpty()){
                                    int userId=Integer.parseInt(String.valueOf(profile.get("user_id")));
                                    info.setUserId(userId);
                                }
                                Map<String,Object> basic=(Map<String,Object>)profiles.get("basic");
                                if(!StringUtils.isEmptyMap(basic)){
                                    String name=(String)basic.get("name");
                                    String email=(String)basic.get("email");
                                    String heading=(String)basic.get("headimg");
                                    String genderName=(String)basic.get("gender_name");
                                    String cityName=(String)basic.get("city_name");
                                    if(basic.get("highest_degree")!=null){
                                        int highestDegree=(int)basic.get("highest_degree");
                                        info.setDegreeName(DegreeConvertUtil.intToEnum.get(highestDegree));
                                    }

                                    info.setEmail(email);
                                    info.setUserName(name);
                                    info.setHeading(CommonUtils.appendUrl(heading,env.getProperty("http.cdn.url")));
                                    info.setCityName(cityName);
                                    info.setGenderName(genderName);
                                }
                                Map<String,Object> recentJob=(Map<String,Object>)profiles.get("recent_job");
                                List<Map<String,Object>> expJob=(List<Map<String,Object>>)profiles.get("other_workexps");
                                List<Map<String,Object>> educationsList=(List<Map<String,Object>>)profiles.get("educations");
                                List<Map<String,Object>> applist=(List<Map<String,Object>>)userMap.get("applications");
                                List<TalentEducationInfo> talentEducationInfo=this.getTalentEducationInfo(educationsList);
                                List<TalentWorkExpInfo> talentWorkExpInfo=this.getTalentWorkExpInfo(recentJob,expJob);
                                info.setEducationList(talentEducationInfo);
                                info.setWorkexps(talentWorkExpInfo);
                                int year=(int)userMap.get("age");
                                info.setBirth(year);
                                info.setPositionName(this.getPositionName(applist,hrId));
                                info.setCompanyName("");
                                if(!StringUtils.isEmptyList(talentEducationInfo)){
                                    info.setCompanyName(talentWorkExpInfo.get(0).getWorkCompany());
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
     获取职位信息
     */
    private String getPositionName(List<Map<String,Object>> applications,int hrId){
        String positionName="";
        if(!StringUtils.isEmptyList(applications)){
            for(Map<String,Object> app:applications){
                int publisher=(int)app.get("publisher");
                String title=(String)app.get("title");
                if(hrId==publisher){
                    positionName=positionName+title+",";
                }
            }
        }
        if(StringUtils.isNotNullOrEmpty(positionName)){
            positionName=positionName.substring(0,positionName.lastIndexOf(","));
        }
        return positionName;
    }
    /*
     获取学历信息
     */
    private List<TalentEducationInfo> getTalentEducationInfo(List<Map<String,Object>> educationsList){
        List<TalentEducationInfo> list=new ArrayList<>();
        if(!StringUtils.isEmptyList(educationsList)) {
            for(Map<String,Object> education:educationsList){
                TalentEducationInfo info = new TalentEducationInfo();
                String startTime = (String) education.get("start_date");
                int endUntilNow = (int) education.get("end_until_now");
                String endTime = (String) education.get("end_date");
                String collegeName = (String) education.get("college_name");
                int degree = (int) education.get("degree");
                String majorName = (String) education.get("major_name");
                if (endUntilNow == 1) {
                    SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-DD");
                    endTime = ff.format(new Date());
                }
                info.setStartTime(startTime);
                info.setCollegeName(collegeName);
                info.setDegree(DegreeConvertUtil.intToEnum.get(degree));
                info.setEndTime(endTime);
                info.setMajorName(majorName);
                list.add(info);
            }

            return list;
        }
        return null;

    }
    /*
     获取工作经验
     */
    private List<TalentWorkExpInfo> getTalentWorkExpInfo(Map<String,Object> recentJob,List<Map<String,Object>> expList){
        List<TalentWorkExpInfo> list=new ArrayList<>();
        if(!StringUtils.isEmptyMap(recentJob)){
            TalentWorkExpInfo info=new TalentWorkExpInfo();
            String workStartTime= (String) recentJob.get("start_date");
            String workEndTime= (String) recentJob.get("end_date");
            String companyName= (String) recentJob.get("end_date");
            String job= (String) recentJob.get("job");
            int endUntilNow=(int)recentJob.get("end_until_now");
            if(endUntilNow==1){
                SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-DD");
                workEndTime = ff.format(new Date());
            }
            info.setWorkCompany(companyName);
            info.setWorkEndTime(workEndTime);
            info.setWorkJob(job);
            info.setWorkStartTime(workStartTime);
            list.add(info);

        }
        if(!StringUtils.isEmptyList(expList)){
            for(Map<String,Object> map:expList){
                TalentWorkExpInfo info=new TalentWorkExpInfo();
                String workStartTime= (String) map.get("start_date");
                String workEndTime= (String) map.get("end_date");
                String companyName= (String) map.get("end_date");
                String job= (String) map.get("job");
                int endUntilNow=(int)map.get("end_until_now");
                if(endUntilNow==1){
                    SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-DD");
                    workEndTime = ff.format(new Date());
                }
                info.setWorkCompany(companyName);
                info.setWorkEndTime(workEndTime);
                info.setWorkJob(job);
                info.setWorkStartTime(workStartTime);
                list.add(info);
            }
        }
        return list;
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
