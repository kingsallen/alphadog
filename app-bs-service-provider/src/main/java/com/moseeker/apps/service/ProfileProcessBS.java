package com.moseeker.apps.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.apps.bean.RecruitmentResult;
import com.moseeker.apps.bean.RewardsToBeAddBean;
import com.moseeker.apps.utils.BusinessUtil;
import com.moseeker.apps.utils.ProcessUtils;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.config.ConfigSysPointsConfTpl;
import com.moseeker.thrift.gen.config.HrAwardConfigTemplate;
import com.moseeker.thrift.gen.dao.service.ApplicationDao;
import com.moseeker.thrift.gen.dao.service.ConfigDao;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.service.UserDao;
import com.moseeker.thrift.gen.dao.service.UserHrAccountDao;
import com.moseeker.thrift.gen.dao.struct.HistoryOperate;
import com.moseeker.thrift.gen.hr.struct.HrOperationrecordStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointSum;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import com.alibaba.fastjson.JSON;
import com.moseeker.apps.constants.TemplateMs;
import com.moseeker.apps.constants.TemplateMs.MsInfo;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;

@Service
public class ProfileProcessBS {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private  MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);
	
	CompanyServices.Iface companyService = ServiceManager.SERVICEMANAGER.getService(CompanyServices.Iface.class);
	
	ApplicationDao.Iface applicationDao = ServiceManager.SERVICEMANAGER
			.getService(ApplicationDao.Iface.class);
    UserHrAccountDao.Iface useraccountDao = ServiceManager.SERVICEMANAGER
			.getService(UserHrAccountDao.Iface.class);
    ConfigDao.Iface configDao = ServiceManager.SERVICEMANAGER
			.getService(ConfigDao.Iface.class);
    UserDao.Iface userDao = ServiceManager.SERVICEMANAGER
			.getService(UserDao.Iface.class);
    HrDBDao.Iface hrDao = ServiceManager.SERVICEMANAGER
			.getService(HrDBDao.Iface.class);
    Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 发送消息模板
	 * @param userId
	 * @param userName
	 * @param companyId
	 * @param status
	 * @param companyName
	 * @param positionName
	 * @param applicationId
	 * @param tm
	 */
	public void sendTemplate(int userId, String userName, int companyId, int status, String positionName, int applicationId, TemplateMs tm) {
		if (StringUtils.isNullOrEmpty(positionName)) {
			return;
		}
		Map<String,MessageTplDataCol> data = new HashMap<String, MessageTplDataCol>();
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
					JSONObject companyJson = JSON.parseObject(company.getData());
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
		        		Map<String, Object> wechatData = JSON.parseObject(wechat.getData());
		        		signature = String.valueOf(wechatData.get("signature"));
				}
			} catch (TException e1) {
				log.error(e1.getMessage(), e1);
			}
			templateNoticeStruct.setUrl(MessageFormat.format(tm.getUrl(), ConfigPropertiesUtil.getInstance().get("platform.url", String.class), signature, "0"));
	        try {
				mqService.messageTemplateNotice(templateNoticeStruct);
			} catch (TException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
     private List<Integer> convertList(String params){
    	 List<Integer> list=new ArrayList<Integer>();
    	 if(params.contains(",")){
    		 String [] array=params.split(",");
    		 for(String param:array){
    			 list.add(Integer.parseInt(param.trim()));
    		 }
    	 }else{
    		 list.add(Integer.parseInt(params.trim()));
    	 }
		return list;
     }
	 public Response processProfile(int companyId,int progressStatus,String params,int accountId ){
		 try{
			List<Integer> appIds=this.convertList(params);
			if(appIds==null||appIds.size()==0){
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
	    	Response application=applicationDao.getProcessAuth(appIds.toString(), companyId, progressStatus);
	    	if(application.getStatus()==0){
	    		String data=application.getData();
	    		if(StringUtils.isNullOrEmpty(data)||"[]".equals(data)){
	    			return ResponseUtils.success("");
	    		}
	    		boolean processStatus = true;
	    		int recruitOrder = 0;
	    		List<ProcessValidationStruct> list=this.ConvertList(data);
	    		UserHrAccount account=this.getAccount(accountId);
	    		// 判断申请状态是否相同
	    		if(account!=null){
	    			for(ProcessValidationStruct record:list){
	    				if(record.getRecruit_order()==0){
	    					processStatus = false;
							break;
	    				}else if(recruitOrder == 0){
	    					recruitOrder = record.getRecruit_order();
	    					break;
	    				}
	    				if(recruitOrder !=record.getRecruit_order() ) {
							processStatus = false;
							break;
						}
	    			}
	    			
	    		}
	    		//  对所有的
	    		if(processStatus||progressStatus==13||progressStatus==99){
	    			Response recruit=configDao.getRecruitProcesses(companyId);
	    			
	    			List<HrAwardConfigTemplate> recruitProcesses=null;
	    			if(recruit.getStatus()==0&&StringUtils.isNotNullOrEmpty(recruit.getData())&&!"[]".equals(recruit.getData())){
	    				recruitProcesses=this.convertRecruitProcessesList(recruit.getData());
	    			}
	    			RecruitmentResult result=BusinessUtil.excuteRecruitRewardOperation(recruitOrder, progressStatus, recruitProcesses);
	    			if(result.getStatus() == 0){
	    				List<Integer> weChatIds=new ArrayList<Integer>();
	    				List<RewardsToBeAddBean>rewardsToBeAdd=new ArrayList<RewardsToBeAddBean>();
	    				// 简历还未被浏览就被拒绝，则视为已被浏览，需要在添加角色操作的历史记录之前插入建立被查看的历史记录
	    				List<HrOperationrecordStruct> turnToCVCheckeds=new ArrayList<HrOperationrecordStruct>();
	    				RewardsToBeAddBean reward=null;
	    				HrOperationrecordStruct turnToCVChecked=null;
	    				for(ProcessValidationStruct record:list){
	    					RecruitmentResult result1 = BusinessUtil.excuteRecruitRewardOperation(record.getRecruit_order(), progressStatus, recruitProcesses);
	    					reward=new RewardsToBeAddBean();
	    					reward.setAccount_id(accountId);
	    					reward.setEmployee_id(0);
	    					reward.setReason(result1.getReason());
	    					reward.setAward(result1.getReward());
	    					reward.setApplication_id(record.getId());
	    					reward.setCompany_id(record.getCompany_id());
	    					reward.setOperate_tpl_id(record.getTemplate_id());
	    					reward.setRecommender_id(record.getRecommender_id());
	    					if(progressStatus == 13&&record.getTemplate_id()==ProcessUtils.RECRUIT_STATUS_APPLY_ID){
	    						turnToCVChecked=new HrOperationrecordStruct();
	    						turnToCVChecked.setAdmin_id(accountId);
	    						turnToCVChecked.setCompany_id(companyId);
	    						turnToCVChecked.setOperate_tpl_id(ProcessUtils.RECRUIT_STATUS_CVCHECKED_ID);
	    						turnToCVChecked.setApp_id(record.getId());
								turnToCVCheckeds.add(turnToCVChecked);
	    					}
	    					rewardsToBeAdd.add(reward);
	    					weChatIds.add(record.getRecommender_id());
	    				}
	    				Response employeeResult=userDao.getUserEmployee(companyId, weChatIds);
	    				List<UserEmployeeStruct> employeesToBeUpdates=new ArrayList<UserEmployeeStruct>();
	    				if(employeeResult.getStatus()==0&&StringUtils.isNotNullOrEmpty(employeeResult.getData())&&!"[]".equals(employeeResult.getData())){
	    					employeesToBeUpdates=ConvertUserEmployeeList(employeeResult.getData());
	    				}
	    				if(employeesToBeUpdates!=null&&employeesToBeUpdates.size()>0){
	    					for(RewardsToBeAddBean bean:rewardsToBeAdd){
	    						for(UserEmployeeStruct user:employeesToBeUpdates){
	    							if(bean.getRecommender_id()==user.getWxuser_id()){
	    								bean.setEmployee_id(user.getId());
	    								break;
	    							}
	    						}
	    					}
	    				}
	    				//修改招聘进度
	    				for(ProcessValidationStruct process : list) {
	    					process.setRecruit_order(progressStatus);
	    					process.setReward(result.getReward());
						}
	    				this.updateRecruitState(progressStatus, list, turnToCVCheckeds, employeesToBeUpdates, result, rewardsToBeAdd);
	    				list.forEach(pvs -> {
	    					sendTemplate(pvs.getApplier_id(), pvs.getApplier_name(), companyId, progressStatus, pvs.getPosition_name(), Constant.APPID_ALPHADOG, TemplateMs.TOSEEKER);
	    					sendTemplate(pvs.getRecommender_user_id(), pvs.getApplier_name(), companyId, progressStatus, pvs.getPosition_name(), Constant.APPID_ALPHADOG, TemplateMs.TORECOM);
	    				});
	    			}
				}
	    		
	    	}
	    	return ResponseUtils.success("操作成功");
		 }catch(Exception e){
			 logger.error(e.getMessage());
			 return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	    	}
	    	
	    }
	    //插入hr操作记录
	    private void  updateRecruitState(Integer progressStatus,
	    		List<ProcessValidationStruct> applications,
	    		List<HrOperationrecordStruct> turnToCVCheckeds,
	    		List<UserEmployeeStruct> employeesToBeUpdates, 
				RecruitmentResult result,
				List<RewardsToBeAddBean> rewardsToBeAdd) throws Exception{
	    	if(progressStatus==13){
	    		rewardsToBeAdd=this.Operation13(applications,rewardsToBeAdd,turnToCVCheckeds);
	    	}else if(progressStatus==99){
	    		rewardsToBeAdd=this.Operation99(applications, rewardsToBeAdd);
	    	}else{
	    		rewardsToBeAdd=this.OperationOther(applications, rewardsToBeAdd, progressStatus);
	    	}
	    	hrDao.postHrOperationrecords(turnToCVCheckeds);
	    	insertRecord(result,rewardsToBeAdd,employeesToBeUpdates);
	    	
	    }
	    //修改推荐用户积分
	    private void insertRecord(RecruitmentResult result,List<RewardsToBeAddBean> rewardsToBeAdd,
	    		List<UserEmployeeStruct> employeesToBeUpdates) throws Exception{
	    	if(result.getReward()!=0){
	    		List<UserEmployeePointStruct> list=new ArrayList<UserEmployeePointStruct>();
	    		UserEmployeePointStruct point=null;
	    		for(RewardsToBeAddBean bean:rewardsToBeAdd){
	    			if(bean.getEmployee_id()!=0){
	    				point=new UserEmployeePointStruct();
	    				point.setEmployee_id(bean.getEmployee_id());
	    				point.setAward(bean.getAward());
	    				point.setApplication_id(bean.getAccount_id());
	    				point.setReason(bean.getReason());
	    				list.add(point);
	    			}
	    		}
	    		//插入积分操作日志
	    		userDao.postUserEmployeePoints(list);
	    		this.updateEmployee(employeesToBeUpdates);
	    		
	    	}
	    }
	    //更新雇员信息
	    public void updateEmployee(List<UserEmployeeStruct> employeesToBeUpdates) throws Exception{
	    	List<Long> records=new ArrayList<Long>();
    		for(UserEmployeeStruct data:employeesToBeUpdates){
    			records.add(Long.parseLong(data.getId()+""));
    		}
    		Response result=userDao.getPointSum(records);
    		if(result.getStatus()==0&&StringUtils.isNotNullOrEmpty(result.getData())&&!"[]".equals(result.getData())){
    			List<UserEmployeePointSum> list=this.ConvertpointSumList(result.getData());
    			for(UserEmployeeStruct employee:employeesToBeUpdates){
    				for(UserEmployeePointSum point:list){
    					if(Long.parseLong(employee.getId()+"")==point.getEmployee_id()){
    						employee.setAward(point.getAward());
    						break;
    					}
    					
    				}
    			}
    			userDao.putUserEmployees(employeesToBeUpdates);
    		}
	    }
	    private List<UserEmployeePointSum> ConvertpointSumList(String data){
	    	List<UserEmployeePointSum> list=new ArrayList<UserEmployeePointSum>();
	    	JSONArray jsay=JSONObject.parseArray(data);
	    	for(int i=0;i<jsay.size();i++){
	    		UserEmployeePointSum record=JSONObject.toJavaObject(jsay.getJSONObject(i), UserEmployeePointSum.class);
	    		list.add(record);
	    	}
	    	return list;
	    }
	    public List<RewardsToBeAddBean> OperationOther(List<ProcessValidationStruct> applications,
				 List<RewardsToBeAddBean> rewardsToBeAdd,int progressStatus) throws Exception{
	    	CommonQuery query=new CommonQuery();
	    	query.setPer_page(Integer.MAX_VALUE);
	    	Response result=configDao.getConfigSysPointsConfTpls(query);
	    	if(result.getStatus()==0&&StringUtils.isNotNullOrEmpty(result.getData())&&!"[]".equals(result.getData())){
	    		List<ConfigSysPointsConfTpl> list=this.ConvertConfigList(result.getData());
	    		List<JobApplication> app=new ArrayList<JobApplication>();
	    		JobApplication jobApplication=null;
	    		for(ProcessValidationStruct data:applications){
	    			jobApplication=new JobApplication();
	    			jobApplication.setReward(data.getReward());
	    			jobApplication.setId(data.getId());
	    			for(ConfigSysPointsConfTpl config:list){
	    				if(data.getRecruit_order()==config.getRecruit_order()){
	    					jobApplication.setApp_tpl_id(config.getId());
	    					break;
	    				}
	    			}
	    			app.add(jobApplication);
	    		}
	    		applicationDao.putApplications(app);
	    		int operate_tpl_id=0;
	    		for(ConfigSysPointsConfTpl config:list){
	    			if(config.getRecruit_order()==progressStatus){
	    				operate_tpl_id=config.getId();
	    			}
	    		}
	    		for(RewardsToBeAddBean bean:rewardsToBeAdd){
	    			bean.setOperate_tpl_id(operate_tpl_id);
	    		}
	    	}
	    	return rewardsToBeAdd;
	    }
	    private List<ConfigSysPointsConfTpl> ConvertConfigList(String data){
	    	List<ConfigSysPointsConfTpl> list=new ArrayList<ConfigSysPointsConfTpl>();
	    	JSONArray jsay=JSONObject.parseArray(data);
	    	for(int i=0;i<jsay.size();i++){
	    		ConfigSysPointsConfTpl record=JSONObject.toJavaObject(jsay.getJSONObject(i), ConfigSysPointsConfTpl.class);
	    		list.add(record);
	    	}
	    	return list;
	    }
	    private List<RewardsToBeAddBean> Operation99(List<ProcessValidationStruct> applications,
	    											 List<RewardsToBeAddBean> rewardsToBeAdd) throws Exception{
	    	Response result=hrDao.getHrHistoryOperations(applications);
	    	if(result.getStatus()==0&&StringUtils.isNotNullOrEmpty(result.getData())&&!"[]".equals(result.getData())){
	    		List<HistoryOperate> list=ConvertHistoryList(result.getData());
	    		for(RewardsToBeAddBean reward : rewardsToBeAdd) {
	    			for(HistoryOperate his:list){
	    				if(his.getOperate_tpl_id()==0){
	    					his.setOperate_tpl_id(ProcessUtils.RECRUIT_STATUS_APPLY_ID);
	    				}
	    				if(reward.getApplication_id()==his.getApp_id()){
	    					reward.setOperate_tpl_id(his.getOperate_tpl_id());
	    					break;
	    				}
	    			}
				}
	    		List<JobApplication> app=new ArrayList<JobApplication>();
	    		JobApplication jobApplication=null;
	    		for(HistoryOperate data:list){
	    			jobApplication=new JobApplication();
	    			jobApplication.setId(data.getApp_id());
	    			jobApplication.setApp_tpl_id(data.getOperate_tpl_id());
	    			app.add(jobApplication);
	    		}
	    		applicationDao.putApplications(app);
	    	}
	    	return rewardsToBeAdd;
	    }
	    private List<HistoryOperate> ConvertHistoryList(String data){
	    	List<HistoryOperate> list=new ArrayList<HistoryOperate>();
	    	JSONArray jsay=JSONObject.parseArray(data);
	    	for(int i=0;i<jsay.size();i++){
	    		HistoryOperate record=JSONObject.toJavaObject(jsay.getJSONObject(i), HistoryOperate.class);
	    		list.add(record);
	    	}
	    	return list;
	    }
	    private List<RewardsToBeAddBean> Operation13(List<ProcessValidationStruct> applications,List<RewardsToBeAddBean> rewardsToBeAdd,
	    		List<HrOperationrecordStruct> turnToCVCheckeds) throws Exception{
	    	CommonQuery query=new CommonQuery();
	    	Map<String,String> map=new HashMap<String,String>();
	    	map.put("recruit_order", 13+"");
	    	query.setEqualFilter(map);
	    	Response result=configDao.getConfigSysPointsConfTpl(query);
	    	if(result.getStatus()==0&&StringUtils.isNotNullOrEmpty(result.getData())&&!"[]".equals(result.getData())){
	    		ConfigSysPointsConfTpl config=JSONObject.toJavaObject(JSONObject.parseObject(result.getData()), ConfigSysPointsConfTpl.class);
	    		int app_tpl_id=config.getId();
	    		List<JobApplication> list=new ArrayList<JobApplication>();
	    		JobApplication jobApplication=null;
	    		for(ProcessValidationStruct struct:applications){
	    			jobApplication=new JobApplication();
	    			jobApplication.setId(struct.getId());
	    			jobApplication.setApp_tpl_id(app_tpl_id);
	    			jobApplication.setNot_suitable(1);
	    			jobApplication.setIs_viewed(0);
	    			jobApplication.setReward(struct.getReward());
	    			list.add(jobApplication);
	    		}
	    		applicationDao.putApplications(list);
	    		for(RewardsToBeAddBean reward : rewardsToBeAdd) {
					reward.setOperate_tpl_id(app_tpl_id);
				}
	    		hrDao.postHrOperationrecords(turnToCVCheckeds);
	    	}
	    	return rewardsToBeAdd;
	    }
	    //将信息验证转换为list集合
	    private List<ProcessValidationStruct> ConvertList(String data){
	    	List<ProcessValidationStruct> list=new ArrayList<ProcessValidationStruct>();
	    	JSONArray jsay=JSONObject.parseArray(data);
	    	for(int i=0;i<jsay.size();i++){
	    		ProcessValidationStruct record=JSONObject.toJavaObject(jsay.getJSONObject(i), ProcessValidationStruct.class);
	    		list.add(record);
	    	}
	    	return list;
	    }
	    //将返回的雇员信息转换成List
	    private List<UserEmployeeStruct> ConvertUserEmployeeList(String data){
	    	List<UserEmployeeStruct> list=new ArrayList<UserEmployeeStruct>();
	    	JSONArray jsay=JSONObject.parseArray(data);
	    	for(int i=0;i<jsay.size();i++){
	    		UserEmployeeStruct record=JSONObject.toJavaObject(jsay.getJSONObject(i), UserEmployeeStruct.class);
	    		list.add(record);
	    	}
	    	return list;
	    }
	    //将企业积分列表转换成list 集合
	    private List<HrAwardConfigTemplate> convertRecruitProcessesList(String data){
	    	List<HrAwardConfigTemplate> list=new ArrayList<HrAwardConfigTemplate>();
	    	JSONArray jsay=JSONObject.parseArray(data);
	    	for(int i=0;i<jsay.size();i++){
	    		HrAwardConfigTemplate record=JSONObject.toJavaObject(jsay.getJSONObject(i), HrAwardConfigTemplate.class);
	    		list.add(record);
	    	}
	    	return list;
	    }
	    //获取账户信息
	    private UserHrAccount getAccount(int accountId) throws Exception{
	    	UserHrAccount account=null;
	    	CommonQuery query=new CommonQuery();
	    	Map<String,String> map=new HashMap<String,String>();
	    	map.put("id", accountId+"");
	    	query.setEqualFilter(map);
	    	Response user=useraccountDao.getAccount(query);
	    	if(user.getStatus()==0&&StringUtils.isNotNullOrEmpty(user.getData())&&!"[]".equals(user.getData())){
	    		account=JSONObject.toJavaObject(JSONObject.parseObject(user.getData()), UserHrAccount.class);
	    	}
	    	return account;
	    }
}
