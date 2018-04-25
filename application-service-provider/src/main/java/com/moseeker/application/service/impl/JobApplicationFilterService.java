package com.moseeker.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionProfileFilterDao;
import com.moseeker.baseorm.dao.logdb.LogTalentpoolProfileFilterLogDao;
import com.moseeker.baseorm.dao.profiledb.ProfileBasicDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolEmailDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolExecuteDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolProfileFilterDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolProfileFilterExcuteDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionProfileFilter;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolProfileFilterLogRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.TalentPoolEmailEntity;
import com.moseeker.entity.biz.CompanyFilterTagValidation;
import com.moseeker.entity.biz.CommonUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.profilebs.service.ProfileBS;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolServices;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;
import com.moseeker.thrift.gen.mq.struct.MessageEmailStruct;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Created by moseeker on 2018/4/13.
 */
@Service
public class JobApplicationFilterService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;
    @Autowired
    private JobPositionProfileFilterDao jobPositionProfileFilterDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private TalentpoolProfileFilterDao talentpoolProfileFilterDao;

    @Autowired
    private CompanyFilterTagValidation tagValidation;
    @Autowired
    private LogTalentpoolProfileFilterLogDao logDao;

    @Autowired
    private Environment env;

    @Autowired
    private TalentpoolProfileFilterExcuteDao talentpoolProfileFilterExcuteDao;

    @Autowired
    private TalentpoolExecuteDao talentpoolExecuteDao;
    @Autowired
    private TalentpoolEmailDao talentpoolEmailDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private HrCompanyAccountDao companyAccountDao;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;
    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileBasicDao basicDao;
    @Autowired
    private TalentPoolEmailEntity emailEntity;


    WholeProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER.getService(WholeProfileServices.Iface.class);
    TalentpoolServices.Iface talentpoolService = ServiceManager.SERVICEMANAGER.getService(TalentpoolServices.Iface.class);
    ProfileBS.Iface bsService = ServiceManager.SERVICEMANAGER.getService(ProfileBS.Iface.class);
    MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);

    public void handerApplicationFilter(MessageEmailStruct filterInfoStruct) throws Exception {
        logger.info("handerApplicationFilter filterInfoStruct:{}", filterInfoStruct);
        JobPositionRecord positionDO = jobPositionDao.getPositionById(filterInfoStruct.getPosition_id());
        logger.info("handerApplicationFilter positionDO:{}", positionDO);
        HrCompanyDO companyDO = hrCompanyDao.getCompanyById(positionDO.getCompanyId());
        logger.info("handerApplicationFilter companyDO:{}", companyDO);
        if(companyDO != null && companyDO.getType() == 0){
            HrCompanyConfDO companyConfDO = hrCompanyConfDao.getHrCompanyConfByCompanyId(companyDO.getId());
            if(companyConfDO.getTalentpoolStatus() == 2){
                List<JobPositionProfileFilter> positionProfileFilterList = jobPositionProfileFilterDao.getFilterPositionRecordByPositionId(positionDO.getId());
                if(positionProfileFilterList!= null && positionProfileFilterList.size()>0) {
                    List<Integer> filterIdList = positionProfileFilterList.stream().map(m -> m.getPfid()).collect(Collectors.toList());
                    List<Integer> filterExecute1 = talentpoolProfileFilterExcuteDao.getFilterExcuteByFilterIdListAndExecuterId(filterIdList,1);
                    List<Integer> filterExecute2 = talentpoolProfileFilterExcuteDao.getFilterExcuteByFilterIdListAndExecuterId(filterIdList,2);
                    List<Integer> filterExecute3 = talentpoolProfileFilterExcuteDao.getFilterExcuteByFilterIdListAndExecuterId(filterIdList,3);
                    List<Integer> filterExecute4 = talentpoolProfileFilterExcuteDao.getFilterExcuteByFilterIdListAndExecuterId(filterIdList,4);
                    Map<Integer,TalentpoolProfileFilter>  talentpoolProfileFilterMap = talentpoolProfileFilterDao.getTalentpoolProfileFilterMapByIdListAndCompanyId(companyDO.getId(), 1, filterIdList);
                    Response res=profileService.getResource(filterInfoStruct.getApplier_id(),0,null);
                    Map<Integer, Boolean> filterPassMap = new HashMap<>();
                    boolean passTalentpoolExecute = false;
                    boolean passApplicationExecute = false;
                    Map<String, Object> profiles = JSON.parseObject(res.getData());
                    if(talentpoolProfileFilterMap != null && talentpoolProfileFilterMap.size()>0){
                        passTalentpoolExecute = forTalentpoolProfileFilter(filterExecute2, talentpoolProfileFilterMap, profiles, filterInfoStruct, filterPassMap, positionDO, passTalentpoolExecute,2);
                        passTalentpoolExecute = forTalentpoolProfileFilter(filterExecute1, talentpoolProfileFilterMap, profiles, filterInfoStruct, filterPassMap, positionDO, passTalentpoolExecute,1);
                        passApplicationExecute = forTalentpoolProfileFilter(filterExecute4, talentpoolProfileFilterMap, profiles, filterInfoStruct, filterPassMap, positionDO, passApplicationExecute,4);
                        passApplicationExecute = forTalentpoolProfileFilter(filterExecute3, talentpoolProfileFilterMap, profiles, filterInfoStruct, filterPassMap, positionDO, passApplicationExecute,3);
                    }
                }
            }
        }
    }


    //循环验证各个筛选项
    private boolean forTalentpoolProfileFilter(List<Integer> filterIdList, Map<Integer,TalentpoolProfileFilter>  talentpoolProfileFilterMap, Map<String, Object> profiles,
                                               MessageEmailStruct filterInfoStruct, Map<Integer, Boolean> filterPassMap, JobPositionRecord position, boolean bool, int type) throws Exception {
        logger.info("handerApplicationFilter bool:{}", bool);
        if(filterIdList!=null && filterIdList.size()>0 && !bool) {
            for (Integer filter_id : filterIdList) {
                if (talentpoolProfileFilterMap.get(filter_id) != null) {
                    boolean isflag = false;
                    if(filterPassMap.get(filter_id)== null) {
                        isflag = validateFile(talentpoolProfileFilterMap.get(filter_id), filterInfoStruct, profiles, position.getCompanyId());
                    }else{
                        isflag = filterPassMap.get(filter_id);
                    }
                    filterPassMap.put(filter_id, isflag);
                    bool = isflag;
                    LogTalentpoolProfileFilterLogRecord logRecord = new LogTalentpoolProfileFilterLogRecord();
                    logRecord.setCompanyId(position.getCompanyId());
                    logRecord.setFilterId(filter_id);
                    logRecord.setHrId(position.getPublisher());
                    logRecord.setUserId(filterInfoStruct.getApplier_id());
                    int i = 0;
                    if(bool){
                        i = 1;
                    }
                    logRecord.setResult(i);
                    logDao.addRecord(logRecord);
                    logger.info("handerApplicationFilter isflag:{}", isflag);
                    if (isflag){
                        filterExecuteAction(filterInfoStruct.getApplier_id(), position, filterInfoStruct.getApplication_id(), type);
                        break;
                    }
                }
            }
        }
        return bool;
    }

    //验证筛选项
    private boolean validateFile(TalentpoolProfileFilter filter, MessageEmailStruct filterInfoStruct, Map<String, Object>profiles, int company_id) throws Exception {
        String tagStr = JSON.toJSONString(filter);
        Map<String, Object> filterMap = JSON.parseObject(tagStr);
        filterMap.remove("origins");
        filterMap.remove("submitTime");
        filterMap.remove("isRecommend");
        boolean isflag=tagValidation.validateProfileAndComapnyTag(profiles,filterInfoStruct.getApplier_id(), company_id,filterMap);
        if(isflag){
            isflag = validateProfileAndFilterOther(filter, filterInfoStruct);
        }
        LogTalentpoolProfileFilterLogRecord logRecord = new LogTalentpoolProfileFilterLogRecord();
        return isflag;
    }

    private  boolean validateProfileAndFilterOther(TalentpoolProfileFilter filter, MessageEmailStruct filterInfoStruct){
        if(StringUtils.isNotNullOrEmpty(filter.getOrigins())){
            boolean flag = validateProfileAndFilterOrigins(filter.getOrigins(), filterInfoStruct.getOrigin());
            logger.info("handerApplicationFilter origins isflag:{}", flag);
            if(!flag){
                return  flag;
            }
        }
        if(filter.getIsRecommend()>0 && filterInfoStruct.getRecommender_user_id()<1){
            return false;
        }
        return true;
    }

    private boolean validateProfileAndFilterOrigins(String origins, int origin){
        String[] array = origins.split(",");
        if(array==null || array.length==0){
            return false;
        }
        for(String item : array){
            if(StringUtils.isNotNullOrEmpty(item)&&item.length()<8){
                //当查找来源是99时特殊处理
                if(Integer.parseInt(item)==99||Integer.parseInt(item)==-99){
                    List<Integer> list=new ArrayList<>();
                    list.add(1);
                    list.add(2);
                    list.add(3);
                    list.add(128);
                    list.add(256);
                    list.add(512);
                    list.add(1024);
                    if(list.contains(origin)){
                        return true;
                    }
                }else{
                    if(Integer.parseInt(item)==origin){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void filterExecuteAction(int user_id, JobPositionRecord position, int application_id, int type) throws TException {
        logger.info("handerApplicationFilter filterExecuteAction:{}", type);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(user_id);
        List<Integer> applicaitionIds = new ArrayList<>();
        applicaitionIds.add(application_id);
        Response res = new Response();
        if(type == 1){
            res = talentpoolService.batchAddTalent(position.getPublisher(), userIds, position.getCompanyId());
        }else if(type == 2){
            talentpoolService.batchAddTalent(position.getPublisher(), userIds, position.getCompanyId());
            res = talentpoolService.batchAddPublicTalent(position.getPublisher(), position.getCompanyId(), userIds);
        }else if(type == 3){
            res = bsService.profileProcess(position.getCompanyId(), 7, applicaitionIds, position.getPublisher());
        }else if(type == 4){
            res = bsService.profileProcess(position.getCompanyId(), 13, applicaitionIds, position.getPublisher());
            sendProfileFilterExecuteEmail(user_id, position);
        }
        logger.info("handerApplicationFilter response info :{}", res);
    }

    //发送不匹配邮件
    private void sendProfileFilterExecuteEmail(int user_id, JobPositionRecord position) throws TException {
        MandrillEmailStruct emailStruct = new MandrillEmailStruct();
        List<TalentpoolEmail> emailList = talentpoolEmailDao.getTalentpoolEmailByCompanyIdAndConfigId(position.getCompanyId(), Constant.TALENTPOOL_EMAIL_PROFILE_FILTER_NOT_PASS);
        if(emailList != null && emailList.size()>0 && emailList.get(0).getDisable() == 1){
            UserHrAccount accountDO = userHrAccountDao.getHrAccount(position.getPublisher());
            UserUserRecord userUserRecord = userUserDao.getUserById(user_id);
            HrWxWechatDO wechatDO = hrWxWechatDao.getHrWxWechatByCompanyId(position.getCompanyId());
            if(accountDO != null && userUserRecord!=null && wechatDO != null){
                HrCompanyDO companyDO = companyAccountDao.getHrCompany(accountDO.getId());
                if(companyDO != null) {
                    Map<String, String> params = new HashMap<>();
                    String username = "";
                    if (userUserRecord.getName() != null && !userUserRecord.getName().isEmpty()) {
                        username = userUserRecord.getName();
                    } else {
                        username = userUserRecord.getNickname();
                    }
                    String company_logo = CommonUtils.appendUrl(companyDO.getLogo(), env.getProperty("http.cdn.url"));
                    params.put("company_logo", company_logo);
                    String context = emailList.get(0).getContext();
                    context = CommonUtils.replaceUtil(context, companyDO.getAbbreviation(), position.getTitle(),
                            username, accountDO.getUsername(), wechatDO.getName());
                    String inscribe = emailList.get(0).getInscribe();
                    inscribe = CommonUtils.replaceUtil(inscribe, companyDO.getAbbreviation(), position.getTitle(),
                            username, accountDO.getUsername(), wechatDO.getName());
                    params.put("custom_text", context);
                    params.put("company_sign", inscribe);
                    params.put("employee_name", username);
                    String qrcodeUrl = CommonUtils.appendUrl(wechatDO.getQrcode(), env.getProperty("http.cdn.url"));
                    params.put("weixin_qrcode", qrcodeUrl);
                    params.put("official_account_name", wechatDO.getName());
                    params.put("send_time", DateUtils.dateToNormalDate(new Date()));
                    emailStruct.setMergeVars(params);
                    emailStruct.setTemplateName(Constant.MISMATCH_NOTIFICATION);
                    String subject = "【" + companyDO.getAbbreviation() + "】不合适通知";
                    emailStruct.setSubject(subject);
                    emailStruct.setTo_name(username);
                    emailStruct.setTo_email(userUserRecord.getEmail());
                    emailStruct.setFrom_name(companyDO.getAbbreviation() + "人才招聘团队");
                    int id = emailEntity.handerTalentpoolEmailLogAndBalance(1, 2, position.getCompanyId(), position.getPublisher());
                    if (id > 0) {
                        logger.info("handerApplicationFilter emailStruct:{}", emailStruct);
                        mqService.sendMandrilEmail(emailStruct);
                    }
                }
            }
        }else{
            logger.info("没有查到");
        }
    }

}
