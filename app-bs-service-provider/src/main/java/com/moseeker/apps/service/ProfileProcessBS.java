package com.moseeker.apps.service;


import com.alibaba.fastjson.JSON;
import com.moseeker.apps.bean.RecruitmentResult;
import com.moseeker.apps.bean.RewardsToBeAddBean;
import com.moseeker.apps.constants.TemplateMs;
import com.moseeker.apps.constants.TemplateMs.MsInfo;
import com.moseeker.apps.utils.BusinessUtil;
import com.moseeker.apps.utils.ProcessUtils;
import com.moseeker.baseorm.dao.configdb.ConfigSysPointsConfTplDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsRecordDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.annotation.notify.UpdateEs;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.struct.ApplicationAts;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.config.ConfigSysPointsConfTpl;
import com.moseeker.thrift.gen.config.HrAwardConfigTemplate;
import com.moseeker.thrift.gen.dao.struct.HistoryOperate;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrOperationRecordDO;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointSum;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileProcessBS {
    private Logger log = LoggerFactory.getLogger(getClass());
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private MqService.Iface mqService = ServiceManager.SERVICEMANAGER
            .getService(MqService.Iface.class);
    CompanyServices.Iface companyService = ServiceManager.SERVICEMANAGER
            .getService(CompanyServices.Iface.class);
    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private UserHrAccountDao userHraccountDao;
    @Autowired
    private ConfigSysPointsConfTplDao configSysPointsConfTplDao;
    @Autowired
    private UserEmployeeDao userEmployeeDao;
    @Autowired
    protected HrCompanyDao hrCompanyDao;
    @Autowired
    private HrOperationRecordDao hrOperationRecordDao;
    @Autowired
    private UserEmployeePointsRecordDao userEmployeePointsRecordDao;

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
//        try {
            if (appIds == null || appIds.size() == 0) {
                return ResponseUtils
                        .fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
            // 对需要修改的进行权限验证
            List<ProcessValidationStruct> list=jobApplicationDao.getAuth(appIds, companyId, progressStatus);
            if (list!=null&&list.size()>0) {
               
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
                    return ResponseUtils.success("操作成功");
                }
                //  对所有的
                if (processStatus || progressStatus == 13 || progressStatus == 99) {
                	List<HrAwardConfigTemplate> recruitProcesses=configSysPointsConfTplDao.findRecruitProcesses(companyId);
                    RecruitmentResult result = BusinessUtil.excuteRecruitRewardOperation(recruitOrder, progressStatus, recruitProcesses);
                    logger.info("ProfileProcessBS processProfile result:{}", result);
                    if (result.getStatus() == 0) {
                        List<Integer> weChatIds = new ArrayList<Integer>();
                        List<RewardsToBeAddBean> rewardsToBeAdd = new ArrayList<RewardsToBeAddBean>();
                        // 简历还未被浏览就被拒绝，则视为已被浏览，需要在添加角色操作的历史记录之前插入建立被查看的历史记录
                        List<HrOperationRecordDO> turnToCVCheckeds = new ArrayList<HrOperationRecordDO>();
                        RewardsToBeAddBean reward = null;
                        HrOperationRecordDO turnToCVChecked = null;
                        for (ProcessValidationStruct record : list) {
                            RecruitmentResult result1 = BusinessUtil.excuteRecruitRewardOperation(record.getRecruit_order(), progressStatus, recruitProcesses);
                            logger.info("ProfileProcessBS processProfile result1:{}", result1);
                            reward = new RewardsToBeAddBean();
                            reward.setAccount_id(accountId);
                            reward.setEmployee_id(0);
                            reward.setReason(result1.getReason());
                            reward.setAward(result1.getReward());
                            reward.setApplication_id(record.getId());
                            reward.setCompany_id(record.getCompany_id());
                            reward.setOperate_tpl_id(record.getTemplate_id());
                            reward.setRecommender_id(record.getRecommender_user_id());
                            if (progressStatus == 13 && record.getTemplate_id() == ProcessUtils.RECRUIT_STATUS_APPLY_ID) {
                                turnToCVChecked = new HrOperationRecordDO();
                                turnToCVChecked.setAdminId(accountId);
                                turnToCVChecked.setCompanyId(companyId);
                                turnToCVChecked.setOperateTplId(ProcessUtils.RECRUIT_STATUS_CVCHECKED_ID);
                                turnToCVChecked.setAppId(record.getId());
                                turnToCVCheckeds.add(turnToCVChecked);
                            }
                            rewardsToBeAdd.add(reward);
                            weChatIds.add(record.getRecommender_user_id());
                        }
                        logger.info("ProfileProcessBS processProfile rewardsToBeAdd:{}", rewardsToBeAdd);
                        //注意在获取employyee时，weChatIds已经不用，此处没有修改thrift的代码，所以还在
                        List<UserEmployeeStruct> employeesToBeUpdates = new ArrayList<UserEmployeeStruct>();
                        List<UserEmployeeRecord> UserEmployeeRecords =userEmployeeDao.getEmployeeByWeChat(companyId, weChatIds);
                        employeesToBeUpdates=convertStruct(UserEmployeeRecords);
                        if (employeesToBeUpdates != null
                                && employeesToBeUpdates.size() > 0) {
                            for (RewardsToBeAddBean bean : rewardsToBeAdd) {
                                for (UserEmployeeStruct user : employeesToBeUpdates) {
                                    if (bean.getRecommender_id() == user.getSysuser_id()) {
                                        bean.setEmployee_id(user.getId());
                                        break;
                                    }
                                }
                            }
                            logger.info("ProfileProcessBS processProfile rewardsToBeAdd:{}", rewardsToBeAdd);
                        }
                        // 修改招聘进度
                        for (ProcessValidationStruct process : list) {
                            process.setRecruit_order(progressStatus);
                            process.setReward(result.getReward());
                        }
                        this.updateRecruitState(progressStatus, list,
                                turnToCVCheckeds, employeesToBeUpdates, result,
                                rewardsToBeAdd);
                        list.forEach(pvs -> {
                            sendTemplate(pvs.getApplier_id(),
                                    pvs.getApplier_name(), companyId,
                                    progressStatus, pvs.getPosition_name(),
                                    pvs.getId(), TemplateMs.TOSEEKER);
                            sendTemplate(pvs.getRecommender_user_id(),
                                    pvs.getApplier_name(), companyId,
                                    progressStatus, pvs.getPosition_name(),
                                    pvs.getId(), TemplateMs.TORECOM);
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
            return ResponseUtils.success("操作成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage(), e);
//            return ResponseUtils
//                    .fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
//        }

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
    public void sendTemplate(int userId, String userName, int companyId,
                             int status, String positionName, int applicationId, TemplateMs tm)  {
        if (StringUtils.isNullOrEmpty(positionName)) {
            return;
        }
        Map<String, MessageTplDataCol> data = new HashMap<String, MessageTplDataCol>();
        MsInfo msInfo = tm.processStatus(status, userName);
        if (msInfo != null) {
            String color = "#173177";
            String companyName = "";
            try {
            	Query query = new Query.QueryBuilder().where("id", companyId).buildQuery();
                HrCompanyDO company = hrCompanyDao.getData(query);
                if(company != null){
                	companyName = company.getName();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage(), e2);
            }
            MessageTplDataCol firstMs = new MessageTplDataCol();
            firstMs.setColor(color);
            firstMs.setValue(msInfo.getResult());
            data.put("first", firstMs);
            MessageTplDataCol keyOneMs = new MessageTplDataCol();
            keyOneMs.setColor(color);
            keyOneMs.setValue(companyName);
            data.put("keyword1", keyOneMs);
            MessageTplDataCol keyTwoMs = new MessageTplDataCol();
            keyTwoMs.setColor(color);
            keyTwoMs.setValue(positionName);
            data.put("keyword2", keyTwoMs);
            MessageTplDataCol keyThreeMs = new MessageTplDataCol();
            keyThreeMs.setColor(color);
            keyThreeMs.setValue(msInfo.getResult());
            data.put("keyword3", keyThreeMs);
            MessageTplDataCol remarkMs = new MessageTplDataCol();
            remarkMs.setColor(color);
            remarkMs.setValue(msInfo.getRemark());
            data.put("remark", remarkMs);
            MessageTemplateNoticeStruct templateNoticeStruct = new MessageTemplateNoticeStruct();
            templateNoticeStruct.setCompany_id(companyId);
            templateNoticeStruct.setData(data);
            templateNoticeStruct.setUser_id(userId);
            templateNoticeStruct.setSys_template_id(tm.getSystemlateId());
            String signature = "";
            try {
                Response wechat = companyService.getWechat(companyId, 0);
                if (wechat.getStatus() == 0) {
                    Map<String, Object> wechatData = JSON.parseObject(wechat
                            .getData());
                    signature = String.valueOf(wechatData.get("signature"));
                }
            } catch (TException e1) {
                log.error(e1.getMessage(), e1);
            }
            templateNoticeStruct.setUrl(MessageFormat.format(
                    tm.getUrl(),
                    ConfigPropertiesUtil.getInstance().get("platform.url",
                            String.class), signature,
                    String.valueOf(applicationId)));
            try {
                mqService.messageTemplateNotice(templateNoticeStruct);
            } catch (TException e) {
                log.error(e.getMessage(), e);
            }
        }
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
                                    RecruitmentResult result, List<RewardsToBeAddBean> rewardsToBeAdd)
            throws Exception {
        if (progressStatus == 13) {
            rewardsToBeAdd = this.Operation13(applications, rewardsToBeAdd,
                    turnToCVCheckeds);
        } else if (progressStatus == 99) {
            rewardsToBeAdd = this.Operation99(applications, rewardsToBeAdd);
        } else {
            rewardsToBeAdd = this.OperationOther(applications, rewardsToBeAdd,
                    progressStatus);
        }
        logger.info("ProfileProcessBS processProfile rewardsToBeAdd:{}", rewardsToBeAdd);
        List<HrOperationRecordDO> lists = new ArrayList<HrOperationRecordDO>();
        HrOperationRecordDO struct = null;
        for (RewardsToBeAddBean beans : rewardsToBeAdd) {
            struct = new HrOperationRecordDO();
            struct.setAdminId(beans.getAccount_id());
            struct.setCompanyId(beans.getCompany_id());
            struct.setOperateTplId(beans.getOperate_tpl_id());
            struct.setAppId(beans.getApplication_id());
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
            List<UserEmployeePointsRecordRecord> list = new ArrayList<UserEmployeePointsRecordRecord>();
            UserEmployeePointsRecordRecord record=null;
            for(RewardsToBeAddBean bean : rewardsToBeAdd){
            	 if (bean.getEmployee_id() != 0) {
            		 record=new UserEmployeePointsRecordRecord();
            		 record.setEmployeeId((long)bean.getEmployee_id());
            		 record.setAward(bean.getAward());
            		 record.setApplicationId((long)bean.getApplication_id());
            		 record.setReason(bean.getReason());
            		 logger.info("ProfileProcessBS processProfile UserEmployeePointStruct:{}", record);
            		 list.add(record);
            	 }
            }
            // 插入积分操作日志
            logger.info("ProfileProcessBS processProfile lists:{}", list);
            userEmployeePointsRecordDao.addAllRecord(list);
            logger.info("ProfileProcessBS processProfile employeesToBeUpdates:{}", employeesToBeUpdates);
            this.updateEmployee(employeesToBeUpdates);

        }
    }

    // 更新雇员信息
    public void updateEmployee(List<UserEmployeeStruct> employeesToBeUpdates)
            throws Exception {
        List<Long> records = new ArrayList<Long>();
        for (UserEmployeeStruct data : employeesToBeUpdates) {
            records.add(Long.parseLong(data.getId() + ""));
        }
        if(records!=null&&records.size()>0){
	        List<UserEmployeePointSum> list=userEmployeePointsRecordDao.getSumRecord(records);
	        List<UserEmployeeRecord> UserEmployeeList = new ArrayList<UserEmployeeRecord>();
	        if (list!=null&&list.size()>0) {
	            for (UserEmployeeStruct employee : employeesToBeUpdates) {
	            	UserEmployeeRecord userEmployeeRecord=BeanUtils.structToDB(employee, UserEmployeeRecord.class);
	                for (UserEmployeePointSum point : list) {
	                    if (Long.parseLong(employee.getId() + "") == point
	                            .getEmployee_id()) {
	                        employee.setAward(point.getAward());
	                        userEmployeeRecord.setAward((int)point.getAward());
	                        break;
	                    }
	
	                }
	                UserEmployeeList.add(userEmployeeRecord);
	            }
	            userEmployeeDao.updateRecords(UserEmployeeList);
	        }
       }
    }

    // 当 progress_status！=13&&progress_status！=99时的操作
    public List<RewardsToBeAddBean> OperationOther(
            List<ProcessValidationStruct> applications,
            List<RewardsToBeAddBean> rewardsToBeAdd, int progressStatus)
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
            }
            jobApplicationDao.updateRecords(convertDB(app));
            int operate_tpl_id = 0;
            for (ConfigSysPointsConfTpl config : list) {
                if (config.getRecruit_order() == progressStatus) {
                    operate_tpl_id = config.getId();
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
            List<RewardsToBeAddBean> rewardsToBeAdd) throws Exception {
    	
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
            List<JobApplication> app = new ArrayList<JobApplication>();
            JobApplication jobApplication = null;
            for (HistoryOperate data : list) {
                jobApplication = new JobApplication();
                jobApplication.setId(data.getApp_id());
                jobApplication.setApp_tpl_id(data.getOperate_tpl_id());
                jobApplication.setNot_suitable(0);
                jobApplication.setIs_viewed(0);
                app.add(jobApplication);
            }
            jobApplicationDao.updateRecords(convertDB(app));
        }
        return rewardsToBeAdd;
    }
    // 当 progress_status=13时的操作
    private List<RewardsToBeAddBean> Operation13(
            List<ProcessValidationStruct> applications,
            List<RewardsToBeAddBean> rewardsToBeAdd,
            List<HrOperationRecordDO> turnToCVCheckeds) throws Exception {     
        Query query=new Query.QueryBuilder().where("recruit_order", 13).buildQuery();
        ConfigSysPointsConfTpl config = configSysPointsConfTplDao.getData(query, ConfigSysPointsConfTpl.class);
        if (config!=null) {
            int app_tpl_id = config.getId();
            List<JobApplication> list = new ArrayList<JobApplication>();
            JobApplication jobApplication = null;
            for (ProcessValidationStruct struct : applications) {
                jobApplication = new JobApplication();
                jobApplication.setId(struct.getId());
                jobApplication.setApp_tpl_id(app_tpl_id);
                jobApplication.setNot_suitable(1);
                jobApplication.setIs_viewed(0);
                jobApplication.setReward(struct.getReward());
                list.add(jobApplication);
            }
            jobApplicationDao.updateRecords(convertDB(list));
            for (RewardsToBeAddBean reward : rewardsToBeAdd) {
                reward.setOperate_tpl_id(app_tpl_id);
            }
            hrOperationRecordDao.addAllData(turnToCVCheckeds);
        }
        return rewardsToBeAdd;
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

}
