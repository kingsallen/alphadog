package com.moseeker.apps.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.apps.bean.RecruitmentResult;
import com.moseeker.apps.bean.RewardsToBeAddBean;
import com.moseeker.apps.constants.TemplateMs;
import com.moseeker.apps.constants.TemplateMs.MsInfo;
import com.moseeker.apps.service.biztools.ApplicationStateChangeSender;
import com.moseeker.apps.service.vo.StateInfo;
import com.moseeker.apps.utils.BusinessUtil;
import com.moseeker.apps.utils.ProcessUtils;
import com.moseeker.baseorm.dao.configdb.ConfigSysPointsConfTplDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolEmailDao;
import com.moseeker.baseorm.dao.userdb.*;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.annotation.notify.UpdateEs;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.TalentPoolEmailEntity;
import com.moseeker.entity.biz.CommonUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.struct.ApplicationAts;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.GDPRProtectedInfo;
import com.moseeker.thrift.gen.config.ConfigSysPointsConfTpl;
import com.moseeker.thrift.gen.config.HrAwardConfigTemplate;
import com.moseeker.thrift.gen.dao.struct.HistoryOperate;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfileProcessBS {

    private Logger log = LoggerFactory.getLogger(getClass());

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private MqService.Iface mqService = ServiceManager.SERVICEMANAGER
            .getService(MqService.Iface.class);

    CompanyServices.Iface companyService = ServiceManager.SERVICEMANAGER
            .getService(CompanyServices.Iface.class);

    private final String ms = "{'tableName':'job_application'}";

    @Autowired
    private JobApplicationDao jobApplicationDao;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private HrWxNoticeMessageDao noticeMessageDao;

    @Autowired
    private UserHrAccountDao userHraccountDao;

    @Autowired
    private UserWxUserDao wxUserDao;

    @Autowired
    private ConfigSysPointsConfTplDao configSysPointsConfTplDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    protected HrCompanyDao hrCompanyDao;

    @Autowired
    private HrOperationRecordDao hrOperationRecordDao;

    @Autowired
    private TalentpoolEmailDao talentpoolEmailDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private Environment env;

    @Autowired
    private HrCompanyAccountDao companyAccountDao;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private TalentPoolEmailEntity emailEntity;

    @Autowired
    private HrCompanyConfDao companyConfDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private ApplicationStateChangeSender applicaitonStateChangeSender;

    @Resource(name = "cacheClient")
    private RedisClient client;


    public MqService.Iface getMqService() {
		return mqService;
	}

	public void setMqService(MqService.Iface mqService) {
		this.mqService = mqService;
	}

	public CompanyServices.Iface getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyServices.Iface companyService) {
		this.companyService = companyService;
	}

	/**
     * ats简历进度
     *
     * @param progressStatus －－－－下一步的状态，对应config_sys_points_conf_tpl.recruit_order
     * @param params         －－－－l_application_id的字符串，之间用，隔开
     * @return Response(status, message, data)
     * @author zzt
	 * @throws Exception 
     */

    @CounterIface
    public Response processProfileAts(int progressStatus, String params) throws Exception {
        logger.info("ProfileProcessBS processProfileAts progressStatus:{}, params:{}", progressStatus, params);
        int companyId = 0;
        int accountId = 0;
//        try {
            List<ApplicationAts> list = getJobApplication(params);
            if (list == null || list.size() == 0) {
                return ResponseUtils
                        .fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            }
            companyId = list.get(0).getCompany_id();
            accountId = list.get(0).getAccount_id();
            List<Integer> appIds = list.stream().map(jop -> jop.getApplication_id()).collect(Collectors.toList());
            logger.info("ProfileProcessBS processProfileAts appIds:{}", appIds);
            Response result = processProfile(companyId, progressStatus, appIds, accountId);
            logger.info("ProfileProcessBS processProfileAts result:{}", result);
            return result;
//        } catch (Exception e) {
//            return ResponseUtils
//                    .fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
//        }

    }

    // 通过ats的l_application_id得到List<account_id,company_id,application_ia>
    private List<ApplicationAts> getJobApplication(String params)
            throws Exception {
        List<Integer> appIds = this.convertList(params);
        List<ApplicationAts> list=jobApplicationDao.getApplicationByLApId(appIds);
        return list;
    }

    /**
     * 招聘进度
     *
     * @param companyId      企业编号
     * @param progressStatus 下一步的状态，对应config_sys_points_conf_tpl.recruit_order
     * @param appIds         申请编号
     * @param accountId      hr_account.id
     * @throws Exception
     */
    @UpdateEs(tableName = "job_application", argsIndex = 2, argsName = "application_id")
    @CounterIface
    public Response processProfile(int companyId, int progressStatus, List<Integer> appIds, int accountId) throws Exception {
        logger.info("ProfileProcessBS processProfile companyId:{}, progressStatus:{}, appIds:{}, accountId:{}", companyId, progressStatus, appIds, accountId);
        Map<String,String> dataResult=new HashMap<>();
        String message = "";
        try {
            if (appIds == null || appIds.size() == 0) {
                return ResponseUtils
                        .fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
            // 对需要修改的进行权限验证
            List<ProcessValidationStruct> list=jobApplicationDao.getAuth(appIds, companyId, progressStatus);
            logger.info("ProfileProcessBS list:{}", JSON.toJSONString(list));
            if (list!=null&&list.size()>0) {
                List<Integer> applierIdList = list
                        .stream()
                        .map(ProcessValidationStruct::getApplier_id)
                        .collect(Collectors.toList());
                List<GDPRProtectedInfo> gdprProtectedInfos = companyService.validateGDPR(applierIdList, companyId);
                logger.info("ProfileProcessBS gdprProtectedInfos:{}", JSON.toJSONString(gdprProtectedInfos));
                if (gdprProtectedInfos != null && gdprProtectedInfos.size() > 0) {
                    list = list
                            .stream()
                            .filter(processValidationStruct -> {
                                Optional<GDPRProtectedInfo> optional = gdprProtectedInfos
                                        .stream()
                                        .filter(gdprProtectedInfo -> gdprProtectedInfo.getUserId()
                                                == processValidationStruct.getApplier_id()
                                                && !gdprProtectedInfo.isTrigger())
                                        .findAny();
                                return optional.isPresent();
                            })
                            .collect(Collectors.toList());
                }
                if(StringUtils.isEmptyList(list)){
                    return ResponseUtils
                            .fail("{\"status\":2159, \"message\":\"该简历不可操作\"}");
                }
                Set<Integer> userIds = list
                        .stream()
                        .map(ProcessValidationStruct::getApplier_id)
                        .collect(Collectors.toSet());
                if(userIds.size() != new HashSet<>(applierIdList).size()){
                    message = userIds.size()+"人申请状态修改成功！";
                }
                boolean processStatus = true;
                int recruitOrder = 0;
                UserHrAccount account = this.getAccount(accountId);
                //  判断申请状态是否相同
                if (account != null) {
                    for (ProcessValidationStruct record : list) {
                        if (record.getRecruit_order() == 0) {
                            processStatus = false;
                            break;
                        } else if (recruitOrder == 0) {
                            recruitOrder = record.getRecruit_order();
                            break;
                        }
                        if (recruitOrder != record.getRecruit_order()) {
                            processStatus = false;
                            break;
                        }
                    }
                }
                if (recruitOrder == progressStatus) {
                    return ResponseUtils.success(dataResult);
                }
                Map<Integer, StateInfo> preStateMap = new HashMap<>();
                list.forEach(processValidationStruct -> {
                    StateInfo stateInfo = new StateInfo();
                    stateInfo.setId(processValidationStruct.getId());
                    stateInfo.setPreRecruitOrder(processValidationStruct.getRecruit_order());
                    stateInfo.setPreState(processValidationStruct.getTemplate_id());
                    stateInfo.setRecruitOrder(progressStatus);
                    stateInfo.setApplierId(processValidationStruct.getApplier_id());
                    stateInfo.setPositionId(processValidationStruct.getPosition_id());
                    preStateMap.put(stateInfo.getId(), stateInfo);
                });

                logger.info("processProfile stateMap:{}", JSON.toJSONString(preStateMap));

                //  对所有的
                if (processStatus || progressStatus == 13 || progressStatus == 99) {
                	List<HrAwardConfigTemplate> recruitProcesses=configSysPointsConfTplDao.findRecruitProcesses(companyId);
                	logger.info("ProfileProcessBS processProfile excuteRecruitRewardOperation recruitOrder:{}, " +
                            "progressStatus:{}, recruitProcesses:{}", recruitOrder, processStatus, recruitProcesses);
                    RecruitmentResult result = BusinessUtil.excuteRecruitRewardOperation(recruitOrder, progressStatus, recruitProcesses);
                    logger.info("ProfileProcessBS processProfile result:{}", JSON.toJSONString(result));
                    if (result.getStatus() == 0) {
                        List<Integer> recommenderIds = new ArrayList<>();
                        List<RewardsToBeAddBean> rewardsToBeAdd = new ArrayList<>();
                        // 简历还未被浏览就被拒绝，则视为已被浏览，需要在添加角色操作的历史记录之前插入建立被查看的历史记录
                        List<HrOperationRecordDO> turnToCVCheckeds = new ArrayList<>();
                        RewardsToBeAddBean reward = null;
                        HrOperationRecordDO turnToCVChecked = null;
                        /*
                          1,由于后需要发消息模板，所以要过滤出来不和规范的申请内容，所以创建一个list用来存放可以处理的申请的记录
                          2,为了不让调用方产生迷惑，所以记录调用成功的job_application.id和不符合招聘进度流程的的job_application.id，返回给调用端，用来提示为何有一些操作失败

                        */
                        List<ProcessValidationStruct> dataList=new ArrayList<>();
                        List<Integer> errorList=new ArrayList<>();
                        List<Integer> successList=new ArrayList<>();
                        for (ProcessValidationStruct record : list) {
                            RecruitmentResult result1 = BusinessUtil.excuteRecruitRewardOperation(record.getRecruit_order(), progressStatus, recruitProcesses);
                            if(result1.getStatus()==1){
                                //将传递数据状态不合规范的申请id记录下来
                                errorList.add(record.getId());
                                logger.warn("传递数据状态不合规范，具体数据是======appid===="+record.getId()+"====当前状态==="+record.getRecruit_order()+"====操作状态==="+progressStatus);
                                continue;
                            }
                            logger.info("ProfileProcessBS processProfile result1:{}", JSON.toJSONString(result1));
                            reward = new RewardsToBeAddBean();
                            reward.setAccount_id(accountId);
                            reward.setEmployee_id(0);
                            reward.setReason(result1.getReason());
                            reward.setAward(result1.getReward());
                            reward.setApplication_id(record.getId());
                            reward.setCompany_id(record.getCompany_id());
                            reward.setOperate_tpl_id(record.getTemplate_id());
                            reward.setRecommender_id(record.getRecommender_user_id());
                            reward.setPoints_conf_id(result1.getPointsConfId());
                            reward.setPosition_id(record.getPosition_id());
                            reward.setApplier_id(record.getApplier_id());
                            if (progressStatus == 13 &&
                                    (record.getTemplate_id() == ProcessUtils.RECRUIT_STATUS_APPLY_ID
                                            || record.getTemplate_id() == ProcessUtils.RECRUIT_STATUS_EMPLOYEE_REFERRAL)) {
                                turnToCVChecked = new HrOperationRecordDO();
                                turnToCVChecked.setAdminId(accountId);
                                turnToCVChecked.setCompanyId(companyId);
                                turnToCVChecked.setOperateTplId(ProcessUtils.RECRUIT_STATUS_CVCHECKED_ID);
                                turnToCVChecked.setAppId(record.getId());
                                turnToCVCheckeds.add(turnToCVChecked);
                            }
                            rewardsToBeAdd.add(reward);
                            recommenderIds.add(record.getRecommender_user_id());
                            dataList.add(record);
                            successList.add(record.getId());
                        }
                        //由于后面要发消息模板，所以只处理那些被处理的数据
                        list=dataList;

                        //============================================
                        logger.info("ProfileProcessBS processProfile rewardsToBeAdd:{}", rewardsToBeAdd);
                        //注意在获取employyee时，weChatIds已经不用，此处没有修改thrift的代码，所以还在
                        Query.QueryBuilder query = new Query.QueryBuilder();
                        query.where(new Condition("sysuser_id", recommenderIds, ValueOp.IN)).and(new Condition("company_id", employeeEntity.getCompanyIds(companyId), ValueOp.IN))
                        .and("disable", 0).and("activation", 0);
                        List<UserEmployeeStruct> employeesToBeUpdates = userEmployeeDao.getDatas(query.buildQuery(), UserEmployeeStruct.class);
                        Map<Integer, Integer> userIdToEmployeeId = (employeesToBeUpdates == null || employeesToBeUpdates.isEmpty()) ?
                                new HashMap<>() : employeesToBeUpdates.stream().collect(Collectors.toMap(k -> k.getSysuser_id(), v -> v.getId(), (newKey, oldKey) -> newKey));
                        rewardsToBeAdd.stream().forEach(e -> {
                            e.setEmployee_id(userIdToEmployeeId.containsKey((int)e.getRecommender_id()) ? userIdToEmployeeId.get((int)e.getRecommender_id()) : 0);
                        });
                        List<Integer> accountIdList = new ArrayList<>();
                        // 修改招聘进度
                        for (ProcessValidationStruct process : list) {
                            process.setRecruit_order(progressStatus);
                            process.setReward(result.getReward());
                            //发送不匹配邮件
                            if(progressStatus == 13) {
                                sendProfileFilterExecuteEmail(process.getApplier_id(), process.getPosition_id());
                            }
                            accountIdList.add(process.getPublisher());
                        }
                        this.updateRecruitState(progressStatus, list,
                                turnToCVCheckeds, employeesToBeUpdates, result,
                                rewardsToBeAdd, preStateMap);
                        //注意这是结果返回值的插入，提示调用方调用成功的有哪些，调用失败的有哪些，========这部分代码是新增的

                        dataResult.put("success_data",JSON.toJSONString(successList));
                        dataResult.put("error_data",JSON.toJSONString(errorList));
                        //========================================================
                        JSONObject jsb = JSONObject.parseObject(ms);
                        jsb.put("application_id", list
                                .stream()
                                .map(applicationEntity -> applicationEntity.getId())
                                .collect(Collectors.toList()));
                        client.lpush(Constant.APPID_ALPHADOG,
                                "ES_REALTIME_UPDATE_INDEX_USER_IDS", jsb.toJSONString());
                        logger.info("lpush ES_REALTIME_UPDATE_INDEX_USER_IDS:{} success", jsb.toJSONString());

                        List<HrCompanyAccountDO> companyAccountDOList = companyAccountDao.getByHrIdList(accountIdList);
                        List<Integer> companyIdList = new ArrayList<>();
                        if(!StringUtils.isEmptyList(companyAccountDOList)) {
                            companyIdList = companyAccountDOList.stream().map(m -> m.getCompanyId()).collect(Collectors.toList());
                        }
                        List<HrCompanyDO> comapnyList = hrCompanyDao.getHrCompanyByCompanyIds(companyIdList);
                        list.forEach(pvs -> {
                            this.updateApplicationEsIndex(pvs.getApplier_id());
                            HrCompanyDO  company = new HrCompanyDO();
                            Optional<HrCompanyAccountDO>  companyAccount = companyAccountDOList.stream().filter(f -> f.getAccountId() == pvs.getPublisher())
                                    .findAny();
                            if(companyAccount.isPresent()){
                                Optional<HrCompanyDO> companyOptional = comapnyList.stream().filter(f -> f.getId() == companyAccount.get().getCompanyId())
                                        .findAny();
                                if(companyOptional.isPresent()){
                                    company = companyOptional.get();
                                }
                            }
                            //当为这三种状态时说明HR做了重新考虑
                            if(ProcessUtils.LETTERS_RECRUITMENT_REOFFERED.equals(result.getReason())
                                || ProcessUtils.LETTERS_RECRUITMENT_RECVPASSED.equals(result.getReason())
                                    || ProcessUtils.LETTERS_RECRUITMENT_RECVCHECKED.equals(result.getReason())){
                                sendTemplate(pvs.getApplier_id(),
                                        pvs.getApplier_name(), companyId, company,
                                        progressStatus, pvs.getPosition_name(),
                                        pvs.getId(), TemplateMs.RESRT);
                            } else {
                                sendTemplate(pvs.getApplier_id(),
                                        pvs.getApplier_name(), companyId, company,
                                        progressStatus, pvs.getPosition_name(),
                                        pvs.getId(), TemplateMs.TOSEEKER);
                                if(employeeEntity.isEmployee(pvs.getRecommender_user_id(),companyId)) {
                                    sendTemplate(pvs.getRecommender_user_id(),
                                            pvs.getApplier_name(), companyId, company,
                                            progressStatus, pvs.getPosition_name(),
                                            pvs.getId(), TemplateMs.TORECOM);
                                    //因为发给推荐者的消息模板有两种类型，数据不相同，所以不能用同一段代码处理
                                    sendTemplateReferral(pvs.getRecommender_user_id(),
                                            pvs.getApplier_id(),
                                            pvs.getApplier_name(), companyId,
                                            progressStatus, pvs.getPosition_name(),
                                            pvs.getId(), TemplateMs.TORECOMSTATUS);
                                }
                            }

                        });
                    } else {
                        return ResponseUtils
                                .fail("{\"status\":2158, \"message\":\"招聘进度流程异常！\"}");
                    }
                }

            } else {
                return ResponseUtils
                        .fail("{\"status\":2201, \"message\":\"参数错误\"}");
            }

            if(StringUtils.isNotNullOrEmpty(message)){
                return ResponseUtils.fail("{\"status\":2159, \"message\":"+message+"}");
            }
            return ResponseUtils.success(dataResult);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils
                    .fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

    }

    /*
   更新data/application索引
    */
    private void updateApplicationEsIndex(int userId){
        client.lpush(Constant.APPID_ALPHADOG,"ES_CRON_UPDATE_INDEX_APPLICATION_USER_IDS",String.valueOf(userId));
        logger.info("====================redis==============application更新=============");
        logger.info("================userid={}=================",userId);
    }
    /*
     * 将record转化为struct
     */
    private List<UserEmployeeStruct> convertStruct(List<UserEmployeeRecord> list) {
        List<UserEmployeeStruct> datas = new ArrayList<UserEmployeeStruct>();
        if (list != null && list.size() > 0) {
            for (UserEmployeeRecord record : list) {
                UserEmployeeStruct data = BeanUtils.DBToStruct(UserEmployeeStruct.class, record);
                datas.add(data);
            }
        }
        return datas;
    }
    /**
     * 发送消息模板
     * @throws TException
     */
    @CounterIface
    public void sendTemplate(int userId, String userName, int companyId, HrCompanyDO company,
                             int status, String positionName, int applicationId, TemplateMs tm)  {
        if (StringUtils.isNullOrEmpty(positionName)) {
            return;
        }
        if(Constant.USERNAME_IS_NULL.equals(userName)){
            userName = "";
        }
        MsInfo msInfo = tm.processStatus(status, userName);
        logger.info("msInfo: {}", msInfo);
        if(msInfo == null){
            return ;
        }
        String signature = "";
        Response wechat = null;
        try {
            wechat = companyService.getWechat(companyId, 0);
        } catch (TException e) {
            log.error(e.getMessage(), e);
        }
        if (wechat.getStatus() == 0) {
            Map<String, Object> wechatData = JSON.parseObject(wechat
                    .getData());
            signature = String.valueOf(wechatData.get("signature"));
        }
        if (msInfo != null) {
            String companyName = "";
            if(company != null){
                companyName = company.getAbbreviation();
            }
            MessageTemplateNoticeStruct templateNoticeStruct = new MessageTemplateNoticeStruct();
            this.handerTemplate(msInfo, companyName, positionName, msInfo.getStatusDesc(), templateNoticeStruct);
            templateNoticeStruct.setCompany_id(companyId);
            templateNoticeStruct.setUser_id(userId);
            String url = MessageFormat.format(
                    msInfo.getUrl(),
                    ConfigPropertiesUtil.getInstance().get("platform.url",
                            String.class), signature,
                    String.valueOf(applicationId));
            url = url +"&send_time=" + new Date().getTime();
            templateNoticeStruct.setUrl(url);
            try {
                mqService.messageTemplateNotice(templateNoticeStruct);
            } catch (TException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 发送消息模板
     * @throws TException
     */
    @CounterIface
    public void sendTemplateReferral(int recomId, int applierId, String userName, int companyId,
                             int status, String positionName, int applicationId, TemplateMs tm)  {
        if (StringUtils.isNullOrEmpty(positionName)) {
            return;
        }
        MsInfo msInfo = tm.processStatus(status, userName);
        logger.info("msInfo: {}", msInfo);
        if(msInfo == null){
            return ;
        }
        //如果模板消息有跳转链接，屏蔽部分解除
//        String signature = "";
//        Response wechat = null;
//        try {
//            wechat = companyService.getWechat(companyId, 0);
//        } catch (TException e) {
//            log.error(e.getMessage(), e);
//        }
//        if (wechat.getStatus() == 0) {
//            Map<String, Object> wechatData = JSON.parseObject(wechat
//                    .getData());
//            signature = String.valueOf(wechatData.get("signature"));
//        }

        if (msInfo != null) {
            String dateStr = DateUtils.dateToNormalDate(new Date());
            MessageTemplateNoticeStruct templateNoticeStruct = new MessageTemplateNoticeStruct();
            if(StringUtils.isNullOrEmpty(userName)){
                UserWxUserRecord wxUserDO = wxUserDao.getWXUserByUserId(applierId);
                if (wxUserDO != null) {
                    userName = wxUserDO.getNickname();
                }
            }
            this.handerTemplate(msInfo, userName, positionName, dateStr, templateNoticeStruct);
            templateNoticeStruct.setCompany_id(companyId);
            templateNoticeStruct.setUser_id(recomId);
//            String url = MessageFormat.format(
//                    msInfo.getUrl(),
//                    ConfigPropertiesUtil.getInstance().get("platform.url",
//                            String.class), signature,
//                    String.valueOf(applicationId));
//            url = url +"&send_time=" + new Date().getTime();
//            templateNoticeStruct.setUrl(url);
            try {
                mqService.messageTemplateNotice(templateNoticeStruct);
            } catch (TException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /*
    拼装模板消息数据
     */
    private void handerTemplate(MsInfo msInfo, String keyword1, String keyword2, String keyword3,
                                MessageTemplateNoticeStruct templateNoticeStruct){
        Map<String, MessageTplDataCol> data = new HashMap<String, MessageTplDataCol>();
        String dateStr = DateUtils.dateToNormalDate(new Date());
        String color = "#173177";
        MessageTplDataCol firstMs = new MessageTplDataCol();
        firstMs.setColor(color);
        firstMs.setValue(msInfo.getResult());
        data.put("first", firstMs);
        MessageTplDataCol keyOneMs = new MessageTplDataCol();
        keyOneMs.setColor(color);
        keyOneMs.setValue(keyword1);
        data.put("keyword1", keyOneMs);
        MessageTplDataCol keyTwoMs = new MessageTplDataCol();
        keyTwoMs.setColor(color);
        keyTwoMs.setValue(keyword2);
        data.put("keyword2", keyTwoMs);
        MessageTplDataCol keyThreeMs = new MessageTplDataCol();
        keyThreeMs.setColor(color);
        keyThreeMs.setValue(keyword3);
        data.put("keyword3", keyThreeMs);
        MessageTplDataCol remarkMs = new MessageTplDataCol();
        remarkMs.setColor(color);
        remarkMs.setValue(msInfo.getRemark());
        data.put("remark", remarkMs);
        templateNoticeStruct.setData(data);
        templateNoticeStruct.setSys_template_id(msInfo.getConfig_id());
    }

    private List<Integer> convertList(String params) {
        List<Integer> list = new ArrayList<Integer>();
        if (params.contains(",")) {
            String[] array = params.split(",");
            for (String param : array) {
                list.add(Integer.parseInt(param.trim()));
            }
        } else {
            list.add(Integer.parseInt(params.trim()));
        }
        return list;
    }

    // 插入hr操作记录
    private void updateRecruitState(Integer progressStatus,
                                    List<ProcessValidationStruct> applications,
                                    List<HrOperationRecordDO> turnToCVCheckeds,
                                    List<UserEmployeeStruct> employeesToBeUpdates,
                                    RecruitmentResult result, List<RewardsToBeAddBean> rewardsToBeAdd,
                                    Map<Integer, StateInfo> preStateMap)
            throws Exception {
        DateTime dateTime = new DateTime();
        if (progressStatus == 13) {
            rewardsToBeAdd = this.Operation13(applications, rewardsToBeAdd,
                    turnToCVCheckeds, preStateMap, dateTime);
        } else if (progressStatus == 99) {
            rewardsToBeAdd = this.Operation99(applications, rewardsToBeAdd, preStateMap, dateTime);
        } else {
            rewardsToBeAdd = this.OperationOther(applications, rewardsToBeAdd,
                    progressStatus, preStateMap, dateTime);
        }
        logger.info("ProfileProcessBS processProfile rewardsToBeAdd:{}", rewardsToBeAdd);
        List<HrOperationRecordDO> lists = new ArrayList<>();
        HrOperationRecordDO struct = null;
        for (RewardsToBeAddBean beans : rewardsToBeAdd) {
            struct = new HrOperationRecordDO();
            struct.setAdminId(beans.getAccount_id());
            struct.setCompanyId(beans.getCompany_id());
            struct.setOperateTplId(beans.getOperate_tpl_id());
            struct.setAppId(beans.getApplication_id());
            struct.setOptTime(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
            lists.add(struct);
        }
		logger.info("ProfileProcessBS processProfile lists:{}", lists);
        hrOperationRecordDao.addAllData(lists);
        insertRecord(result, rewardsToBeAdd, employeesToBeUpdates);

    }

    // 修改推荐用户积分
    private void insertRecord(RecruitmentResult result,
                              List<RewardsToBeAddBean> rewardsToBeAdd,
                              List<UserEmployeeStruct> employeesToBeUpdates) throws Exception {
        if (result.getReward() != 0) {
//            List<UserEmployeePointsRecordRecord> list = new ArrayList<UserEmployeePointsRecordRecord>();
            UserEmployeePointsRecordRecord record=null;
            for(RewardsToBeAddBean bean : rewardsToBeAdd){
            	 if (bean.getEmployee_id() != 0) {
                     UserEmployeePointsRecordDO userEmployeePointsRecordDO = new UserEmployeePointsRecordDO();
                     userEmployeePointsRecordDO.setAward(bean.getAward());
                     userEmployeePointsRecordDO.setApplicationId(bean.getApplication_id());
                     userEmployeePointsRecordDO.setReason(bean.getReason());
                     userEmployeePointsRecordDO.setEmployeeId(bean.getEmployee_id());
                     userEmployeePointsRecordDO.setAwardConfigId(bean.getPoints_conf_id());
                     userEmployeePointsRecordDO.setRecomUserId((int)bean.getRecommender_id());
                     userEmployeePointsRecordDO.setPositionId(bean.getPosition_id());
                     userEmployeePointsRecordDO.setBerecomUserId(bean.getApplier_id());
            	     employeeEntity.addReward(bean.getEmployee_id(), bean.getCompany_id(), userEmployeePointsRecordDO);
            		 logger.info("ProfileProcessBS processProfile UserEmployeePointStruct:{}", record);
//            		 list.add(record);
            	 }
            }
            logger.info("ProfileProcessBS processProfile employeesToBeUpdates:{}", employeesToBeUpdates);
//            this.updateEmployee(employeesToBeUpdates);
        }
    }

    // 更新雇员信息
//    public void updateEmployee(List<UserEmployeeStruct> employeesToBeUpdates)
//            throws Exception {
//        List<Long> records = new ArrayList<Long>();
//        for (UserEmployeeStruct data : employeesToBeUpdates) {
//            records.add(Long.parseLong(data.getId() + ""));
//        }
//        if(records!=null&&records.size()>0){
//	        List<UserEmployeePointSum> list=userEmployeePointsRecordDao.getSumRecord(records);
//	        List<UserEmployeeRecord> UserEmployeeList = new ArrayList<UserEmployeeRecord>();
//	        if (list!=null&&list.size()>0) {
//	            for (UserEmployeeStruct employee : employeesToBeUpdates) {
//	            	UserEmployeeRecord userEmployeeRecord=BeanUtils.structToDB(employee, UserEmployeeRecord.class);
//	                for (UserEmployeePointSum point : list) {
//	                    if (Long.parseLong(employee.getId() + "") == point
//	                            .getEmployee_id()) {
//	                        employee.setAward(point.getAward());
//	                        userEmployeeRecord.setAward((int)point.getAward());
//	                        break;
//	                    }
//
//	                }
//	                UserEmployeeList.add(userEmployeeRecord);
//	            }
//	            userEmployeeDao.updateRecords(UserEmployeeList);
//	        }
//       }
//    }

    // 当 progress_status！=13&&progress_status！=99时的操作
    public List<RewardsToBeAddBean> OperationOther(
            List<ProcessValidationStruct> applications,
            List<RewardsToBeAddBean> rewardsToBeAdd, int progressStatus, Map<Integer, StateInfo> stateMap, DateTime dateTime)
            throws Exception {
        Query query=new Query.QueryBuilder().buildQuery();
        List<ConfigSysPointsConfTpl> list =configSysPointsConfTplDao.getDatas(query, ConfigSysPointsConfTpl.class);
        if (list!=null&&list.size()>0) {
            List<JobApplication> app = new ArrayList<JobApplication>();
            JobApplication jobApplication = null;
            for (ProcessValidationStruct data : applications) {
                jobApplication = new JobApplication();
                jobApplication.setReward(data.getReward());
                jobApplication.setId(data.getId());
                jobApplication.setNot_suitable(0);
                jobApplication.setIs_viewed(0);
                for (ConfigSysPointsConfTpl config : list) {
                    if (data.getRecruit_order() == config.getRecruit_order()) {
                        jobApplication.setApp_tpl_id(config.getId());
                        break;
                    }
                }
                app.add(jobApplication);
                publishStateChange(jobApplication, stateMap, dateTime);
            }
            jobApplicationDao.updateRecords(convertDB(app));
            int operate_tpl_id = 0;
            for (ConfigSysPointsConfTpl config : list) {
                if (config.getRecruit_order() == progressStatus) {
                    operate_tpl_id = config.getId();
                    break;
                }
            }
            for (RewardsToBeAddBean bean : rewardsToBeAdd) {
                bean.setOperate_tpl_id(operate_tpl_id);
            }
        }
        return rewardsToBeAdd;
    }

    // 当 progress_status=99时的操作
    private List<RewardsToBeAddBean> Operation99(
            List<ProcessValidationStruct> applications,
            List<RewardsToBeAddBean> rewardsToBeAdd, Map<Integer, StateInfo> stateMap, DateTime dateTime) throws Exception {
    	
	    StringBuffer sb = new StringBuffer();
        sb.append("(");
        for (ProcessValidationStruct record : applications) {
          sb.append("" + record.getId() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        String param = sb.toString();
        List<HistoryOperate> list=hrOperationRecordDao.getHistoryOperate(param);
        if (list!=null&&list.size()>0) {
            for (RewardsToBeAddBean reward : rewardsToBeAdd) {
                for (HistoryOperate his : list) {
                    if (his.getOperate_tpl_id() == 0) {
                        his.setOperate_tpl_id(ProcessUtils.RECRUIT_STATUS_APPLY_ID);
                    }
                    if (reward.getApplication_id() == his.getApp_id()) {
                        reward.setOperate_tpl_id(his.getOperate_tpl_id());
                        break;
                    }
                }
            }
            List<JobApplication> app = new ArrayList<>();
            JobApplication jobApplication = null;
            for (HistoryOperate data : list) {
                jobApplication = new JobApplication();
                jobApplication.setId(data.getApp_id());
                jobApplication.setApp_tpl_id(data.getOperate_tpl_id());
                jobApplication.setNot_suitable(0);
                jobApplication.setIs_viewed(0);
                app.add(jobApplication);
                publishStateChange(jobApplication, stateMap, dateTime);
            }
            jobApplicationDao.updateRecords(convertDB(app));
        }
        return rewardsToBeAdd;
    }
    // 当 progress_status=13时的操作
    private List<RewardsToBeAddBean> Operation13(
            List<ProcessValidationStruct> applications,
            List<RewardsToBeAddBean> rewardsToBeAdd,
            List<HrOperationRecordDO> turnToCVCheckeds, Map<Integer, StateInfo> stateMap, DateTime operationTime) throws Exception {
        Query query=new Query.QueryBuilder().where("recruit_order", 13).buildQuery();
        ConfigSysPointsConfTpl config = configSysPointsConfTplDao.getData(query, ConfigSysPointsConfTpl.class);
        if (config!=null) {
            int app_tpl_id = config.getId();
            List<JobApplication> list = new ArrayList<>();
            JobApplication jobApplication = null;
            for (ProcessValidationStruct struct : applications) {
                jobApplication = new JobApplication();
                jobApplication.setId(struct.getId());
                jobApplication.setApp_tpl_id(app_tpl_id);
                jobApplication.setNot_suitable(1);
                jobApplication.setIs_viewed(0);
                jobApplication.setReward(struct.getReward());
                list.add(jobApplication);
                publishStateChange(jobApplication, stateMap, operationTime);
            }
            jobApplicationDao.updateRecords(convertDB(list));
            for (RewardsToBeAddBean reward : rewardsToBeAdd) {
                reward.setOperate_tpl_id(app_tpl_id);
            }
            turnToCVCheckeds.forEach(hrOperationRecordDO -> {
                hrOperationRecordDO.setOptTime(operationTime.toString("yyyy-MM-dd HH:mm:ss"));
            });
            hrOperationRecordDao.addAllData(turnToCVCheckeds);
        }
        return rewardsToBeAdd;
    }

    /**
     * 发布申请状态变更消息
     * @param jobApplication 申请数据
     * @param stateMap 申请状态相关数据
     * @param operationTime 操作时间
     */
    private void publishStateChange(JobApplication jobApplication,
                                    Map<Integer, StateInfo> stateMap, DateTime operationTime) {
        StateInfo stateInfo = stateMap.get((int)jobApplication.getId());
        logger.info("publishStateChange stateMap:{}", JSON.toJSONString(stateMap));
        logger.info("publishStateChange applicationId:{}, stateInfo:{}", jobApplication.getId(), JSON.toJSONString(stateInfo));
        byte move = 0;
        if (stateInfo.getRecruitOrder() != 13 && stateInfo.getPreRecruitOrder() < stateInfo.getRecruitOrder()) {
            move = 1;
        }
        applicaitonStateChangeSender.publishStateChangeEvent((int)jobApplication.getId(), stateInfo.getPreState(),
                jobApplication.getApp_tpl_id(), stateInfo.getApplierId(), stateInfo.getPositionId(), move, operationTime);
    }
    /*
     * struct转化为db
     * 
     */
    private List<JobApplicationRecord>convertDB(List<JobApplication> applications){
		List<JobApplicationRecord> list=new ArrayList<JobApplicationRecord>();
		for(JobApplication application:applications){
			list.add(BeanUtils.structToDB(application, JobApplicationRecord.class));
		}
		return list;
	}


    // 获取账户信息
    private UserHrAccount getAccount(int accountId) throws Exception {
        Query query=new Query.QueryBuilder().where("id",accountId).buildQuery();
        UserHrAccount account=userHraccountDao.getData(query, UserHrAccount.class);
        return account;
    }

    //发送不匹配邮件
    private void sendProfileFilterExecuteEmail(int user_id, int position_id) throws TException {
        logger.info("开始发送不匹配邮件===================");
        JobPositionRecord position = jobPositionDao.getPositionById(position_id);
        HrCompanyConfDO companyConfDO = companyConfDao.getHrCompanyConfByCompanyId(position.getCompanyId());
        if (companyConfDO.getTalentpoolStatus() == 2) {
            MandrillEmailStruct emailStruct = new MandrillEmailStruct();
            List<TalentpoolEmail> emailList = talentpoolEmailDao.getTalentpoolEmailByCompanyIdAndConfigId(position.getCompanyId(), Constant.TALENTPOOL_EMAIL_PROFILE_FILTER_NOT_PASS);
            if (emailList != null && emailList.size() > 0 && emailList.get(0).getDisable() == 1) {
                com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount accountDO = userHrAccountDao.getHrAccount(position.getPublisher());
                UserUserRecord userUserRecord = userUserDao.getUserById(user_id);
                HrWxWechatDO wechatDO = hrWxWechatDao.getHrWxWechatByCompanyId(position.getCompanyId());
                if (accountDO != null && userUserRecord != null && wechatDO != null) {
                    HrCompanyDO companyDO = companyAccountDao.getHrCompany(accountDO.getId());
                    if (companyDO != null) {
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
                        params.put("company_abbr", companyDO.getAbbreviation());
                        emailStruct.setMergeVars(params);
                        emailStruct.setTemplateName(Constant.MISMATCH_NOTIFICATION);
                        emailStruct.setTo_name(username);
                        emailStruct.setTo_email(userUserRecord.getEmail());
                        int id = emailEntity.handerTalentpoolEmailLogAndBalance(1, 2, position.getCompanyId(), position.getPublisher());
                        if (id > 0) {
                            mqService.sendMandrilEmail(emailStruct);
                        }
                    }
                }
            } else {
                logger.info("没有查到邮件信息");
            }
        }else{
            logger.info("没有开启智能人才库");
        }
    }

}
