package com.moseeker.apps.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.moseeker.thrift.gen.dao.service.*;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrOperationRecordDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.apps.bean.RecruitmentResult;
import com.moseeker.apps.bean.RewardsToBeAddBean;
import com.moseeker.apps.constants.TemplateMs;
import com.moseeker.apps.constants.TemplateMs.MsInfo;
import com.moseeker.apps.utils.BusinessUtil;
import com.moseeker.apps.utils.ProcessUtils;
import com.moseeker.common.annotation.notify.UpdateEs;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.struct.ApplicationAts;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.config.ConfigSysPointsConfTpl;
import com.moseeker.thrift.gen.config.HrAwardConfigTemplate;
import com.moseeker.thrift.gen.dao.struct.HistoryOperate;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointSum;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;

@Service
public class ProfileProcessBS {
    private Logger log = LoggerFactory.getLogger(getClass());

    private MqService.Iface mqService = ServiceManager.SERVICEMANAGER
            .getService(MqService.Iface.class);

    CompanyServices.Iface companyService = ServiceManager.SERVICEMANAGER
            .getService(CompanyServices.Iface.class);

    ApplicationDao.Iface applicationDao = ServiceManager.SERVICEMANAGER
            .getService(ApplicationDao.Iface.class);
    UserHrAccountDao.Iface useraccountDao = ServiceManager.SERVICEMANAGER
            .getService(UserHrAccountDao.Iface.class);
    ConfigDBDao.Iface configDao = ServiceManager.SERVICEMANAGER
            .getService(ConfigDBDao.Iface.class);
    UserDBDao.Iface userDao = ServiceManager.SERVICEMANAGER
            .getService(UserDBDao.Iface.class);
    HrDBDao.Iface hrDao = ServiceManager.SERVICEMANAGER
            .getService(HrDBDao.Iface.class);
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ats简历进度
     *
     * @param progressStatus －－－－下一步的状态，对应config_sys_points_conf_tpl.recruit_order
     * @param params         －－－－l_application_id的字符串，之间用，隔开
     * @return Response(status, message, data)
     * @author zzt
     */
    public Response processProfileAts(int progressStatus, String params) {
        int companyId = 0;
        int accountId = 0;
        try {
            List<ApplicationAts> list = getJobApplication(params);
            if (list == null || list.size() == 0) {
                return ResponseUtils
                        .fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            }
            companyId = list.get(0).getCompany_id();
            accountId = list.get(0).getAccount_id();
            List<Integer> appIds = list.stream().map(jop -> jop.getApplication_id()).collect(Collectors.toList());
            Response result = processProfile(companyId, progressStatus, appIds, accountId);
            return result;
        } catch (Exception e) {
            return ResponseUtils
                    .fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

    }

    // 通过ats的l_application_id得到List<account_id,company_id,application_ia>
    private List<ApplicationAts> getJobApplication(String params)
            throws Exception {
        List<Integer> appIds = this.convertList(params);
        Response result = applicationDao.getApplicationsByList(appIds);
        if (result.getStatus() == 0
                && StringUtils.isNotNullOrEmpty(result.getData())
                && !"[]".equals(result.getData())) {
            return this.convertApplicationAtsList(result.getData());
        }
        return null;
    }

    // 将ApplicationAts（account_id,company_id,application_ia）转化为list
    private List<ApplicationAts> convertApplicationAtsList(String params) {
        List<ApplicationAts> list = new ArrayList<ApplicationAts>();
        JSONArray jsay = JSON.parseArray(params);
        for (int i = 0; i < jsay.size(); i++) {
            JSONObject obj = jsay.getJSONObject(i);
            list.add(JSONObject.toJavaObject(obj, ApplicationAts.class));
        }
        return list;
    }

    /**
     * 招聘进度
     *
     * @param companyId      企业编号
     * @param progressStatus 下一步的状态，对应config_sys_points_conf_tpl.recruit_order
     * @param appIds         申请编号
     * @param accountId      hr_account.id
     */
    @UpdateEs(tableName = "job_application", argsIndex = 2, argsName = "application_id")
    public Response processProfile(int companyId, int progressStatus, List<Integer> appIds, int accountId) {
        try {
            if (appIds == null || appIds.size() == 0) {
                return ResponseUtils
                        .fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
            // 对需要修改的进行权限验证
            Response application = applicationDao.getProcessAuth(appIds,
                    companyId, progressStatus);
            if (application.getStatus() == 0) {
                String data = application.getData();
                if (StringUtils.isNullOrEmpty(data) || "[]".equals(data)) {
                    return ResponseUtils.success("");
                }
                boolean processStatus = true;
                int recruitOrder = 0;
                List<ProcessValidationStruct> list = this.ConvertList(data);
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
                    Response recruit = configDao.getRecruitProcesses(companyId);

                    List<HrAwardConfigTemplate> recruitProcesses = null;
                    if (recruit.getStatus() == 0 && StringUtils.isNotNullOrEmpty(recruit.getData()) && !"[]".equals(recruit.getData())) {
                        recruitProcesses = this.convertRecruitProcessesList(recruit.getData());
                    }

                    RecruitmentResult result = BusinessUtil.excuteRecruitRewardOperation(recruitOrder, progressStatus, recruitProcesses);
                    if (result.getStatus() == 0) {
                        List<Integer> weChatIds = new ArrayList<Integer>();
                        List<RewardsToBeAddBean> rewardsToBeAdd = new ArrayList<RewardsToBeAddBean>();
                        // 简历还未被浏览就被拒绝，则视为已被浏览，需要在添加角色操作的历史记录之前插入建立被查看的历史记录
                        List<HrOperationRecordDO> turnToCVCheckeds = new ArrayList<HrOperationRecordDO>();
                        RewardsToBeAddBean reward = null;
                        HrOperationRecordDO turnToCVChecked = null;
                        for (ProcessValidationStruct record : list) {
                            RecruitmentResult result1 = BusinessUtil.excuteRecruitRewardOperation(record.getRecruit_order(), progressStatus, recruitProcesses);
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
                            weChatIds.add(record.getRecommender_id());
                        }
                        //注意在获取employyee时，weChatIds已经不用，此处没有修改thrift的代码，所以还在
                        Response employeeResult = userDao.getUserEmployee(
                                companyId, weChatIds);
                        List<UserEmployeeStruct> employeesToBeUpdates = new ArrayList<UserEmployeeStruct>();
                        if (employeeResult.getStatus() == 0
                                && StringUtils.isNotNullOrEmpty(employeeResult
                                .getData())
                                && !"[]".equals(employeeResult.getData())) {
                            employeesToBeUpdates = ConvertUserEmployeeList(employeeResult
                                    .getData());
                        }
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
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils
                    .fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

    }

    /**
     * 发送消息模板
     */
    public void sendTemplate(int userId, String userName, int companyId,
                             int status, String positionName, int applicationId, TemplateMs tm) {
        if (StringUtils.isNullOrEmpty(positionName)) {
            return;
        }
        Map<String, MessageTplDataCol> data = new HashMap<String, MessageTplDataCol>();
        MsInfo msInfo = tm.processStatus(status, userName);
        if (msInfo != null) {
            String color = "#173177";
            String companyName = "";
            CommonQuery query = new CommonQuery();
            Map<String, String> paramMap = new HashMap<String, String>();
            query.setEqualFilter(paramMap);
            paramMap.put("id", String.valueOf(companyId));
            try {
                Response company = companyService.getResource(query);
                if (company.status == 0) {
                    JSONObject companyJson = JSON
                            .parseObject(company.getData());
                    companyName = companyJson.getString("name");
                }
            } catch (TException e2) {
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
        hrDao.postHrOperationrecords(lists);
        insertRecord(result, rewardsToBeAdd, employeesToBeUpdates);

    }

    // 修改推荐用户积分
    private void insertRecord(RecruitmentResult result,
                              List<RewardsToBeAddBean> rewardsToBeAdd,
                              List<UserEmployeeStruct> employeesToBeUpdates) throws Exception {
        if (result.getReward() != 0) {
            List<UserEmployeePointStruct> list = new ArrayList<UserEmployeePointStruct>();
            UserEmployeePointStruct point = null;
            for (RewardsToBeAddBean bean : rewardsToBeAdd) {
                if (bean.getEmployee_id() != 0) {
                    point = new UserEmployeePointStruct();
                    point.setEmployee_id(bean.getEmployee_id());
                    point.setAward(bean.getAward());
                    point.setApplication_id(bean.getApplication_id());
                    point.setReason(bean.getReason());
                    list.add(point);
                }
            }
            // 插入积分操作日志
            userDao.postUserEmployeePoints(list);
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
        Response result = userDao.getPointSum(records);
        if (result.getStatus() == 0
                && StringUtils.isNotNullOrEmpty(result.getData())
                && !"[]".equals(result.getData())) {
            List<UserEmployeePointSum> list = this.ConvertpointSumList(result
                    .getData());
            for (UserEmployeeStruct employee : employeesToBeUpdates) {
                for (UserEmployeePointSum point : list) {
                    if (Long.parseLong(employee.getId() + "") == point
                            .getEmployee_id()) {
                        employee.setAward(point.getAward());
                        break;
                    }

                }
            }
            userDao.putUserEmployees(employeesToBeUpdates);
        }
    }

    // 将总积分列表的string转化为list
    private List<UserEmployeePointSum> ConvertpointSumList(String data) {
        List<UserEmployeePointSum> list = new ArrayList<UserEmployeePointSum>();
        JSONArray jsay = JSONObject.parseArray(data);
        for (int i = 0; i < jsay.size(); i++) {
            UserEmployeePointSum record = JSONObject.toJavaObject(
                    jsay.getJSONObject(i), UserEmployeePointSum.class);
            list.add(record);
        }
        return list;
    }

    // 当 progress_status！=13&&progress_status！=99时的操作
    public List<RewardsToBeAddBean> OperationOther(
            List<ProcessValidationStruct> applications,
            List<RewardsToBeAddBean> rewardsToBeAdd, int progressStatus)
            throws Exception {
        CommonQuery query = new CommonQuery();
        query.setPer_page(Integer.MAX_VALUE);
        Response result = configDao.getConfigSysPointsConfTpls(query);
        if (result.getStatus() == 0
                && StringUtils.isNotNullOrEmpty(result.getData())
                && !"[]".equals(result.getData())) {
            List<ConfigSysPointsConfTpl> list = this.ConvertConfigList(result
                    .getData());
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
            applicationDao.putApplications(app);
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

    // 将积分配置表的string转化为list
    private List<ConfigSysPointsConfTpl> ConvertConfigList(String data) {
        List<ConfigSysPointsConfTpl> list = new ArrayList<ConfigSysPointsConfTpl>();
        JSONArray jsay = JSONObject.parseArray(data);
        for (int i = 0; i < jsay.size(); i++) {
            ConfigSysPointsConfTpl record = JSONObject.toJavaObject(
                    jsay.getJSONObject(i), ConfigSysPointsConfTpl.class);
            list.add(record);
        }
        return list;
    }

    // 当 progress_status=99时的操作
    private List<RewardsToBeAddBean> Operation99(
            List<ProcessValidationStruct> applications,
            List<RewardsToBeAddBean> rewardsToBeAdd) throws Exception {
        Response result = hrDao.getHrHistoryOperations(applications);
        if (result.getStatus() == 0
                && StringUtils.isNotNullOrEmpty(result.getData())
                && !"[]".equals(result.getData())) {
            List<HistoryOperate> list = ConvertHistoryList(result.getData());
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
            applicationDao.putApplications(app);
        }
        return rewardsToBeAdd;
    }

    // 将操作记录的string转化为list
    private List<HistoryOperate> ConvertHistoryList(String data) {
        List<HistoryOperate> list = new ArrayList<HistoryOperate>();
        JSONArray jsay = JSONObject.parseArray(data);
        for (int i = 0; i < jsay.size(); i++) {
            HistoryOperate record = JSONObject.toJavaObject(
                    jsay.getJSONObject(i), HistoryOperate.class);
            list.add(record);
        }
        return list;
    }

    // 当 progress_status=13时的操作
    private List<RewardsToBeAddBean> Operation13(
            List<ProcessValidationStruct> applications,
            List<RewardsToBeAddBean> rewardsToBeAdd,
            List<HrOperationRecordDO> turnToCVCheckeds) throws Exception {
        CommonQuery query = new CommonQuery();
        Map<String, String> map = new HashMap<String, String>();
        map.put("recruit_order", 13 + "");
        query.setEqualFilter(map);
        Response result = configDao.getConfigSysPointsConfTpl(query);
        if (result.getStatus() == 0
                && StringUtils.isNotNullOrEmpty(result.getData())
                && !"[]".equals(result.getData())) {
            ConfigSysPointsConfTpl config = JSONObject.toJavaObject(
                    JSONObject.parseObject(result.getData()),
                    ConfigSysPointsConfTpl.class);
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
            applicationDao.putApplications(list);
            for (RewardsToBeAddBean reward : rewardsToBeAdd) {
                reward.setOperate_tpl_id(app_tpl_id);
            }
            hrDao.postHrOperationrecords(turnToCVCheckeds);
        }
        return rewardsToBeAdd;
    }

    // 将信息验证转换为list集合
    private List<ProcessValidationStruct> ConvertList(String data) {
        List<ProcessValidationStruct> list = new ArrayList<ProcessValidationStruct>();
        JSONArray jsay = JSONObject.parseArray(data);
        for (int i = 0; i < jsay.size(); i++) {
            ProcessValidationStruct record = JSONObject.toJavaObject(
                    jsay.getJSONObject(i), ProcessValidationStruct.class);
            Integer applier_id=record.getApplier_id();
            if(applier_id!=null&&applier_id!=0){
            	try{
	            	CommonQuery query=new CommonQuery();
	            	HashMap<String,String> map=new HashMap<String,String>();
	            	map.put("id", applier_id+"");
	            	query.setEqualFilter(map);
	            	UserUserDO userRecord=userDao.getUser(query);
	            	String applier_name=userRecord.getName();
	            	record.setApplier_name(applier_name);
            	}catch(Exception e){
            		logger.info(e.getMessage(),e);
            	}
            }
            list.add(record);
        }
        return list;
    }

    // 将返回的雇员信息转换成List
    private List<UserEmployeeStruct> ConvertUserEmployeeList(String data) {
        List<UserEmployeeStruct> list = new ArrayList<UserEmployeeStruct>();
        JSONArray jsay = JSONObject.parseArray(data);
        for (int i = 0; i < jsay.size(); i++) {
            UserEmployeeStruct record = JSONObject.toJavaObject(
                    jsay.getJSONObject(i), UserEmployeeStruct.class);
            list.add(record);
        }
        return list;
    }

    // 将企业积分列表转换成list 集合
    private List<HrAwardConfigTemplate> convertRecruitProcessesList(String data) {
        List<HrAwardConfigTemplate> list = new ArrayList<HrAwardConfigTemplate>();
        JSONArray jsay = JSONObject.parseArray(data);
        for (int i = 0; i < jsay.size(); i++) {
            HrAwardConfigTemplate record = JSONObject.toJavaObject(
                    jsay.getJSONObject(i), HrAwardConfigTemplate.class);
            list.add(record);
        }
        return list;
    }

    // 获取账户信息
    private UserHrAccount getAccount(int accountId) throws Exception {
        UserHrAccount account = null;
        CommonQuery query = new CommonQuery();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", accountId + "");
        query.setEqualFilter(map);
        Response user = useraccountDao.getAccount(query);
        if (user.getStatus() == 0
                && StringUtils.isNotNullOrEmpty(user.getData())
                && !"[]".equals(user.getData())) {
            account = JSONObject
                    .toJavaObject(JSONObject.parseObject(user.getData()),
                            UserHrAccount.class);
        }
        return account;
    }

}
