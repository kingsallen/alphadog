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
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.constants.DegreeConvertUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.entity.Constant.EmailAccountConsumptionType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.company.bean.*;
import com.moseeker.entity.PcRevisionEntity;
import com.moseeker.entity.TalentPoolEmailEntity;
import com.moseeker.entity.TalentPoolEntity;
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
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    SearchengineServices.Iface searchService = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);

    @Resource(name = "cacheClient")
    private RedisClient client;

    @Autowired
    private PcRevisionEntity pcRevisionEntity;

    @Autowired
    private DictIndustryDao dictIndustryDao;

    @Autowired
    private DictIndustryTypeDao dictIndustryTypeDao;

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
        }else{
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

    /*
    发送部分的职位邀请
     */
    private int sendInviteToDelivyEmail(List<Integer> userIdList,List<Integer> positionIdList,int companyId,int hrId){
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId);
        boolean flag=this.validateSendEmail(hrCompanyEmailInfoRecord,talentpoolEmailRecord);
        if(flag){

        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;
    }
    /*
    发送全部邮件
     */
    private  int sendAllInviteToDelivyEmail(Map<String,String> params,List<Integer> positionIdList,int companyId,int hrId){
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId);
        boolean flag=this.validateSendEmail(hrCompanyEmailInfoRecord,talentpoolEmailRecord);
        if(flag){

        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;
    }

    private List<TalentEmailInviteToDelivyInfo> getInviteToDelivyInfoList(List<Integer> positionIdList,int companyId,String context){
        List<JobPositionRecord> positionList=this.getPositionList(positionIdList);
        HrCompanyRecord record=this.getCompanyInfo(companyId);
        HrWxWechatRecord wechatRecord=getWxInfo(companyId);
        if(!StringUtils.isEmptyList(positionList)){
            List<TalentEmailInviteToDelivyInfo> list=new ArrayList<>();
            List<Integer> publisherList=this.getPublisherList(positionList);
            Map<Integer,HrCompanyRecord> publisherCompany=this.getHrCompanyPublisher(publisherList,record);
            if(StringUtils.isEmptyMap(publisherCompany)){
                return null;
            }
            for(JobPositionRecord jobPositionRecord:positionList){
                TalentEmailInviteToDelivyInfo info=new TalentEmailInviteToDelivyInfo();
                int publisher=jobPositionRecord.getPublisher();
                info.setCompanyAbbr(record.getAbbreviation());
                info.setCompanyLogo(record.getLogo());
                info.setCustomText(context);
                info.setPositionNum(positionIdList.size()+"");
                info.setOfficialAccountName(wechatRecord.getName());
                info.setWeixinQrcode(wechatRecord.getQrcode());
                info.setSeeMorePosition("");
                PositionInfo positionInfo=new  PositionInfo();
                for(Integer key:publisherCompany.keySet()){
                    if(key==publisher){
                        HrCompanyRecord hrCompanyRecord=publisherCompany.get(key);
                        positionInfo.setCompanyAbbr(hrCompanyRecord.getAbbreviation());
                        positionInfo.setCompanyAddr(hrCompanyRecord.getAddress());
                        break;
                    }

                }
                positionInfo.setWorkYear(jobPositionRecord.getExperience());
                positionInfo.setPositionBg("");

            }

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
    private Map<Integer,String> getPositionPicture(List<Integer> teamIdList,List<Integer> positionIdList,HrCompanyRecord record) throws TException {
        if(StringUtils.isEmptyList(teamIdList)){
            return null;
        }
        Map<Integer,String> result=new HashMap<>();
        List<Map<String,Object>> list=pcRevisionEntity.HandleCmsResource(teamIdList,3);
        DictIndustryRecord dictIndustryRecord=this.getIndustryInfo(record.getIndustry());
        DictIndustryTypeRecord dictIndustryTypeRecord=this.getIndustryTypeInfo(dictIndustryRecord.getType());
        if(StringUtils.isEmptyList(list)){
            for(Integer positionId:positionIdList){
                result.put(positionId,dictIndustryTypeRecord.getJobImg());
            }
        }else{
            for(Integer positionId:positionIdList){
                int flag=0;
                for(Map<String,Object> map:list){

                }
                if(flag==0){
                    result.put(positionId,dictIndustryTypeRecord.getJobImg());
                }

            }
        }
        return null;
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
     发送部分转发邮件
     */
    private int sendResumeEmail(List<Integer> idList,List<Integer> userIdList,int companyId,int hrId){
        if(!StringUtils.isEmptyList(idList)) {
            return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
        }
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId);
        boolean flag=this.validateSendEmail(hrCompanyEmailInfoRecord,talentpoolEmailRecord);
        if(flag){
            List<UserEmployeeDO> employeeList=this.getUserEmployeeList(idList);
            if(!StringUtils.isEmptyList(employeeList)){
                int lost=userIdList.size()*employeeList.size();
                if(!this.validateBalance(hrCompanyEmailInfoRecord.getBalance(),lost)){
                   return TalentEmailEnum.NOBALANCE.getValue();
                }
                List<MandrillEmailStruct> emailList=this.convertResumeEmailData(employeeList,userIdList,companyId,talentpoolEmailRecord.getContext(),hrId);
                updateEmailInfoBalance(companyId,lost);
            }else{
                return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
            }
        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;
    }

    /*
     发送全部转发邮件
     */
    private  int sendAllResumeEmail(List<Integer> idList,Map<String,String> params,int companyId,int hrId){
        if(!StringUtils.isEmptyList(idList)) {
            return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
        }
        HrCompanyEmailInfoRecord hrCompanyEmailInfoRecord=this.getHrCompanyEmailInfo(companyId);
        TalentpoolEmailRecord talentpoolEmailRecord=this.getTalentpoolEmail(companyId);
        boolean flag=this.validateSendEmail(hrCompanyEmailInfoRecord,talentpoolEmailRecord);
        if(flag){
            try {
                int talentNum = searchService.talentSearchNum(params);
                if(talentNum>0){
                    List<UserEmployeeDO> employeeList=this.getUserEmployeeList(idList);
                    if(!StringUtils.isEmptyList(employeeList)){
                        int lost=talentNum*employeeList.size();
                        if(!this.validateBalance(hrCompanyEmailInfoRecord.getBalance(),lost)){
                            return TalentEmailEnum.NOBALANCE.getValue();
                        }
                        int totalPage=(int)Math.ceil((double)talentNum/300);
                        for(int i=1;i<=totalPage;i++){
                            params.put("page_size",300+"");
                            params.put("page_number",(i-1)+"");
                            List<MandrillEmailStruct> emailList=this.convertResumeEmailData(employeeList,params,companyId,talentpoolEmailRecord.getContext(),hrId);
                            updateEmailInfoBalance(companyId,lost);
                            //
                        }

                    }else{
                        return TalentEmailEnum.NOUSEREMPLOYEE.getValue();
                    }
                }
            }catch(Exception e){

            }
        }else{
            return TalentEmailEnum.NOCONFIGEMAIL.getValue();
        }
        return 0;
    }
    /*
     全选批量查询简历
     */
    private List<MandrillEmailStruct> convertResumeEmailData(List<UserEmployeeDO> employeeList,Map<String,String> params,int companyId,String context,int hrId){
        List<TalentEmailForwardsResumeInfo> dataInfo=this.handlerData(params,companyId,context,hrId);
        List<MandrillEmailStruct> list=this.convertResumeEmailData(dataInfo,employeeList,context);
        return list;
    }
    /*
    根据user-id查询简历组装发送数据
     */
    private List<MandrillEmailStruct> convertResumeEmailData(List<UserEmployeeDO> employeeList,List<Integer>userIdList,int companyId,String context,int hrId){
        List<TalentEmailForwardsResumeInfo> dataInfo=this.handlerData(userIdList,companyId,context,hrId);
        List<MandrillEmailStruct> list=this.convertResumeEmailData(dataInfo,employeeList,context);
        return list;
    }
    /*
     组装数据为邮件所需数据
     */
    private List<MandrillEmailStruct> convertResumeEmailData(List<TalentEmailForwardsResumeInfo> dataInfo,List<UserEmployeeDO> employeeList,String context){
        List<MandrillEmailStruct> result=new ArrayList<>();
        if(StringUtils.isEmptyList(employeeList)){
            return null;
        }
        if(StringUtils.isEmptyList(dataInfo)){
            return null;
        }
        for(UserEmployeeDO DO:employeeList){
            String email=DO.getEmail();
            String name=DO.getCfname()+DO.getCname();
            for(TalentEmailForwardsResumeInfo info:dataInfo){
                MandrillEmailStruct struct=new MandrillEmailStruct();
                info.setCoworkerName(name);
                struct.setTo_name(name);
                struct.setFrom_email("info@moseeker.net");
                struct.setFrom_name(info.getCompanyAbbr()+"人才招聘团队");
                struct.setTo_email(email);
                String subject=info.getCompanyAbbr()+"请您评审简历"+info.getPositionName()+"-"+info.getUserName();
                if(info.getWorkexps()!=null){
                    subject=subject+"-"+info.getWorkexps().getWorkCompany();
                }
                struct.setSubject(subject);
                struct.setTemplateName("forwards-resume");
                struct.setMergeVars(convertToEmailMergeVars(info));
                result.add(struct);
            }

        }
        return result;
    }
    /*
     转化类做邮件发送参数
     */
    private Map<String,String> convertToEmailMergeVars(TalentEmailForwardsResumeInfo info){
        String res= JSON.toJSONString(info,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
        Map<String,Object> data=JSON.parseObject(res);
        Map<String,String> result=new HashMap<>();
        for(String key:data.keySet()){
            Object value=data.get(key);
            if((value instanceof Map)){
                result.put(key,JSON.toJSONString(value));
            }
            result.put(key,value+"");
        }
        return result;
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
                    info.setCompanyLogo(hrCompanyRecord.getLogo());
                }
                if(hrWxWechatRecord!=null){
                    info.setWeixinQrcode(hrWxWechatRecord.getQrcode());
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
           if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
              Map<String,Object> data= JSON.parseObject(res.getData());
               List<TalentEmailForwardsResumeInfo> result=this.convertData(data,hrId);
               return result;
           }
        }catch(Exception e){

        }
        return null;
    }
    private List<TalentEmailForwardsResumeInfo> talentEmailInfoSearch(List<Integer>UserIdList,int hrId){
        try{
            Response res=searchService.userQueryById(UserIdList);
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData());
                List<TalentEmailForwardsResumeInfo> result=this.convertData(data,hrId);
                return result;
            }
        }catch(Exception e){

        }
        return null;
    }

    /*
     获取所需要收件人的信息的数据
     */
    private List<TalentEmailForwardsResumeInfo> convertData(Map<String,Object> result,int hrId){
        List<TalentEmailForwardsResumeInfo> list=new ArrayList<>();
        if(!StringUtils.isEmptyMap(result)){
            long totalNum=(long)result.get("totalNum");
            if(totalNum>0){
                List<Map<String,Object>> dataList=(List<Map<String,Object>>)result.get("userIdList");
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
                                    String name=(String)profile.get("name");
                                    String email=(String)profile.get("email");
                                    String heading=(String)profile.get("headimg");
                                    String genderName=(String)profile.get("gender_name");
                                    String cityName=(String)profile.get("city_name");
                                    int highestDegree=(int)profile.get("highest_degree");
                                    info.setEmail(email);
                                    info.setUserName(name);
                                    info.setHeading(heading);
                                    info.setCityName(cityName);
                                    info.setDegreeName(DegreeConvertUtil.intToEnum.get(highestDegree));
                                    info.setGenderName(genderName);
                                }
                                Map<String,Object> recentJob=(Map<String,Object>)profiles.get("recent_job");
                                List<Map<String,Object>> educationsList=(List<Map<String,Object>>)profiles.get("educations");
                                List<Map<String,Object>> applist=(List<Map<String,Object>>)userMap.get("applications");
                                TalentEducationInfo talentEducationInfo=this.getTalentEducationInfo(educationsList);
                                TalentWorkExpInfo talentWorkExpInfo=this.getTalentWorkExpInfo(recentJob);
                                info.setEducationList(talentEducationInfo);
                                info.setWorkexps(talentWorkExpInfo);
                                int year=(int)userMap.get("age");
                                info.setBirth(year);
                                info.setPositionName(this.getPositionName(applist,hrId));
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
    private TalentEducationInfo getTalentEducationInfo(List<Map<String,Object>> educationsList){
        if(!StringUtils.isEmptyList(educationsList)) {
            TalentEducationInfo info = new TalentEducationInfo();
            Map<String, Object> education = educationsList.get(0);
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
            return info;
        }
        return null;

    }
    /*
     获取工作经验
     */
    private TalentWorkExpInfo getTalentWorkExpInfo(Map<String,Object> recentJob){
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
            return info;

        }
        return null;
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
    private TalentpoolEmailRecord getTalentpoolEmail(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("disable",1).buildQuery();
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
        Query query=new Query.QueryBuilder().where(new Condition("id",idList.toArray(), ValueOp.IN))
                .buildQuery();
        List<UserEmployeeDO> list=userEmployeeDao.getDatas(query);
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
}
