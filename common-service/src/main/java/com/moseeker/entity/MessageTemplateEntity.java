package com.moseeker.entity;

import com.moseeker.baseorm.dao.campaigndb.CampaignPersonaRecomDao;
import com.moseeker.baseorm.dao.campaigndb.CampaignRecomPositionlistDao;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.profiledb.ProfileBasicDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignPersonaRecom;
import com.moseeker.baseorm.db.campaigndb.tables.pojos.CampaignRecomPositionlist;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPersonaRecomRecord;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignRecomPositionlistRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNoticeMessageRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxTemplateMessageRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.pojo.JobPositionPojo;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.Constant.JobStatus;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by zztaiwll on 17/10/20.
 */
@Service
public class MessageTemplateEntity {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(MessageTemplateEntity.class);
    @Autowired
    private ConfigSysTemplateMessageLibraryDao configSysTemplateMessageLibraryDao;
    @Autowired
    private ProfileProfileDao profileProfileDao;
    @Autowired
    private ProfileBasicDao profileBasicDao;
    @Autowired
    private UserWxUserDao userWxUserDao;
    @Autowired
    private UserUserDao userUserDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private UserEmployeeDao userEmployeeDao;
    @Autowired
    private HrEmployeePositionDao hrEmployeePositionDao;
    @Autowired
    private HrEmployeeSectionDao hrEmployeeSectionDao;
    @Autowired
    private HrWxWechatDao hrWxWechatDao;
    @Autowired
    private JobPositionDao positionDao;
    @Autowired
    private CampaignPersonaRecomDao campaignPersonaRecomDao;
    @Autowired
    private HrTeamDao hrTeamDao;
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HrWxTemplateMessageDao hrWxTemplateMessageDao;
    @Autowired
    private HrWxNoticeMessageDao hrWxNoticeMessageDao;

    @Autowired
    private CampaignRecomPositionlistDao campaignRecomPositionlistDao;

    public MessageTemplateNoticeStruct handlerTemplate(int userId,int companyId,int templateId,int type,String url){

        HrWxWechatDO DO= this.getHrWxWechatDOByCompanyId(companyId);
        int weChatId=DO.getId();
        String wxSignture=DO.getSignature();
        List<Integer> pidList=new ArrayList<>();
        if(type==1){
            url=url.replace("{}",wxSignture);
        }else if(type==2){
            //校验推送职位是否下架
            pidList=this.handleEmployeeRecomPosition(userId,companyId,0);
            if(StringUtils.isEmptyList(pidList)){
                return null;
            }
            url=url.replace("{}",wxSignture);
        }else if(type==4){
            url=url.replace("{}",wxSignture);
        }else if(type==3){
            //校验推送职位是否下架,以及将数据加入推送的表中
            pidList=this.handleEmployeeRecomPosition(userId,companyId,1);
           if(StringUtils.isEmptyList(pidList)){
               return null;
           }
           String  positionIds=convertListToString(pidList);
           /*
            这么写感觉特别不好，没水平，添添补补，但是这是特殊的，因为这块的职位推荐要一直能看并且可传播
            */
           int recomId=this.addCampaignRecomPositionlist(companyId,positionIds);
           url=url.replace("{recomPushId}",recomId+"").replace("{}",wxSignture);

        }
        MessageTemplateNoticeStruct messageTemplateNoticeStruct =new MessageTemplateNoticeStruct();
        Map<String,MessageTplDataCol> colMap=this.handleMessageTemplateData(userId,type,companyId,weChatId,pidList);
        if(colMap==null||colMap.isEmpty()){
            return null;
        }
        messageTemplateNoticeStruct.setCompany_id(companyId);
        messageTemplateNoticeStruct.setData(colMap);
        messageTemplateNoticeStruct.setUser_id(userId);
        messageTemplateNoticeStruct.setSys_template_id(templateId);
        messageTemplateNoticeStruct.setUrl(url);
        messageTemplateNoticeStruct.setType((byte)0);
        return messageTemplateNoticeStruct;
    }

    private String getJobName(int userId,int companyId,int type) {
        Query query=new Query.QueryBuilder().where("user_id",userId).and("company_id",companyId).and("type",(byte)type).buildQuery();
        CampaignPersonaRecomRecord record=campaignPersonaRecomDao.getRecord(query);
        if(record==null){
            return null;
        }
        int positionId=record.getPositionId();
        Query query1=new Query.QueryBuilder().where("id",positionId).buildQuery();
        JobPositionDO jobPositionDO=jobPositionDao.getData(query1);
        if(jobPositionDO==null){
            return null;
        }
        String jobName=jobPositionDO.getTitle();
        if(StringUtils.isNotNullOrEmpty(jobName)){
            jobName+="等";
        }
        return jobName;
    }
    public String getJobNameRecom(List<Integer> pid){
        Query query=new Query.QueryBuilder().where(new Condition("id",pid.toArray(),ValueOp.IN)).and("status",0).buildQuery();
        List<JobPositionDO> jobPositionDO=jobPositionDao.getDatas(query);
        if(StringUtils.isEmptyList(jobPositionDO)){
            return null;
        }else{
            String jobName=jobPositionDO.get(0).getTitle();
            return jobName;
        }
    }
    //获取公司的名称
    private String getCompanyName(int companyId) {
        String companyName = null;
        HrCompanyDO companyDO = getCompanyById(companyId);
        if (companyDO != null) {
            companyName = org.apache.commons.lang.StringUtils.isNotBlank(companyDO.getAbbreviation())?companyDO.getAbbreviation():companyDO.getName();
        }
        return companyName;
    }

    /*
        处理发送完善简历消息模板
     */
    private  Map<String,MessageTplDataCol> handleMessageTemplateData(int userId,int type,int companyId,int weChatId,List<Integer> pidList){

        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        if(type==1){
            colMap=this.handleDataForuestion(userId,weChatId);
        }else if(type==2||type==3){

            colMap=this.handleDataRecommendTemplate(userId,companyId,type,weChatId,pidList);
        }else if(type==4){
            colMap=this.handleDataProfileTemplate(userId,companyId,weChatId);
        }
        return colMap;
    }

    /*
        组装完善粉丝模板数据
     */
    private Map<String,MessageTplDataCol> handleDataForuestion(int userId,int weChatId){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        String name=this.handleName(userId);
        UserUserDO DO=this.getUserUserById(userId);
        String time="";
        if(DO!=null){
            time=DO.getRegisterTime();
        }
        MessageTplDataCol first=new MessageTplDataCol();
        first.setColor("#173177");
        HrWxNoticeMessageRecord record=this.getHrWxTemplateMessage(weChatId,Constant.FANS_PROFILE_COMPLETION);
        if(record != null && record.getStatus().byteValue()!=1){
            return null;
        }
        if(record !=null&&StringUtils.isNotNullOrEmpty(record.getFirst())){
            first.setValue(record.getFirst());
        }else{
            first.setValue("您好，请完善您的简历信息。");
        }
        colMap.put("first",first);
        MessageTplDataCol keyword1=new MessageTplDataCol();
        keyword1.setColor("#173177");
        keyword1.setValue(name);
        colMap.put("keyword1",keyword1);
        MessageTplDataCol keyword2=new MessageTplDataCol();
        keyword2.setColor("#173177");
        keyword2.setValue(time);
        colMap.put("keyword2",keyword2);
        MessageTplDataCol remark=new MessageTplDataCol();
        remark.setColor("#173177");
        if(record!=null&&StringUtils.isNotNullOrEmpty(record.getRemark())){
            remark.setValue(record.getRemark());
        }else {
            remark.setValue("完善简历信息，让HR更了解你，会得到更多的关注哦");
        }
        colMap.put("remark",remark);
        return colMap;
    }

    /*
        推荐职位列表消息数据
     */
    private Map<String,MessageTplDataCol> handleDataRecommendTemplate(int userId,int companyId,int type,int weChatId,List<Integer> pidList){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        String jobName="";
        String companyName=this.getCompanyName(companyId);
        if(type==2){
            jobName = this.getJobName(userId,companyId,0);
            MessageTplDataCol first=new MessageTplDataCol();
            first.setColor("#173177");
            HrWxNoticeMessageRecord record=this.getHrWxTemplateMessage(weChatId,Constant.FANS_RECOM_POSITION);
            if(record != null && record.getStatus().byteValue()!=1){
                return null;
            }
            if(record !=null&&StringUtils.isNotNullOrEmpty(record.getFirst())){
                first.setValue(record.getFirst());
            }else {
                first.setValue("根据您的求职意愿，仟寻为您挑选了一些新机会。");
            }
            colMap.put("first",first);
            MessageTplDataCol remark=new MessageTplDataCol();
            remark.setColor("#173177");
            if(record!=null&&StringUtils.isNotNullOrEmpty(record.getRemark())){
                remark.setValue(record.getRemark());
            }else {
                remark.setValue("点击查看推荐职位。");
            }
            colMap.put("remark",remark);
        }
        if(type==3){
            jobName = this.getJobNameRecom(pidList);
            MessageTplDataCol first=new MessageTplDataCol();
            first.setColor("#173177");
            HrWxNoticeMessageRecord record=this.getHrWxTemplateMessage(weChatId,Constant.EMPLOYEE_RECOM_POSITION);
            if(record != null && record.getStatus().byteValue()!=1){
                return null;
            }
            if(record !=null&&StringUtils.isNotNullOrEmpty(record.getFirst())){
                first.setValue(record.getFirst());
            }else {
                first.setValue("以下职位虚位以待，赶快转发起来吧~");
            }
            colMap.put("first",first);
            MessageTplDataCol remark=new MessageTplDataCol();
            remark.setColor("#173177");
            if(record!=null&&StringUtils.isNotNullOrEmpty(record.getRemark())){
                remark.setValue(record.getRemark());
            }else {
                remark.setValue("点击查看推荐职位。");
            }
            colMap.put("remark",remark);
        }
        SimpleDateFormat sf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        MessageTplDataCol keyword1=new MessageTplDataCol();
        keyword1.setColor("#173177");
        keyword1.setValue(jobName);
        colMap.put("keyword1",keyword1);
        MessageTplDataCol keyword2=new MessageTplDataCol();
        keyword2.setColor("#173177");
        keyword2.setValue(companyName);
        colMap.put("keyword2",keyword2);
        String data=sf.format(new Date());
        MessageTplDataCol keyword3=new MessageTplDataCol();
        keyword3.setColor("#173177");
        keyword3.setValue(data);
        colMap.put("keyword3",keyword3);

        return colMap;
    }

    /*
     完善简历信息数据模板
     */
    private Map<String,MessageTplDataCol> handleDataProfileTemplate(int userId,int companyId,int weChatId){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        UserEmployeeRecord DO=this.getUserEmployeeByUserIdAndCompanyId(userId,companyId);
        if(DO==null){
            return null;
        }
        MessageTplDataCol first=new MessageTplDataCol();
        first.setColor("#173177");
        HrWxNoticeMessageRecord record=this.getHrWxTemplateMessage(weChatId,Constant.EMPLOYEE_PROFILE_COMPLETION);
        if(record != null && record.getStatus().byteValue()!=1){
            return null;
        }
        if(record !=null&&StringUtils.isNotNullOrEmpty(record.getFirst())){
            first.setValue(record.getFirst());
        }else {
            first.setValue("您有待完善的员工信息。");
        }
        colMap.put("first",first);
        MessageTplDataCol remark=new MessageTplDataCol();
        remark.setColor("#173177");
        if(record!=null&&StringUtils.isNotNullOrEmpty(record.getRemark())){
            remark.setValue(record.getRemark());
        }else {
            remark.setValue("完善员工信息，参与企业内推，还可以赚积分哦~");
        }
        colMap.put("remark",remark);
        MessageTplDataCol keyword1=new MessageTplDataCol();
        keyword1.setValue(DO.getCname());
        keyword1.setColor("#173177");
        colMap.put("keyword1",keyword1);
        MessageTplDataCol keyword2=new MessageTplDataCol();
        HrTeamDO   hrTeamDO=this.getTeamById(DO.getTeamId());
        keyword2.setValue(hrTeamDO.getName());
        keyword2.setColor("#173177");
        colMap.put("keyword2",keyword2);
        MessageTplDataCol keyword3=new MessageTplDataCol();
        keyword3.setValue(DO.getPosition());
        keyword3.setColor("#173177");
        colMap.put("keyword3",keyword3);
        return colMap;
    }
    /*
     根据team.id获取公司部门信息
     */
    private HrTeamDO getTeamById(int id){
        if(id==0){
            return new HrTeamDO();
        }
        Query query=new Query.QueryBuilder().where("id",id).and("disable",0).buildQuery();
        HrTeamDO DO=hrTeamDao.getData(query);
        if(DO==null){
            DO=new HrTeamDO();
        }
        return DO;
    }
    /*
      根据user_id和company_id查找雇员信息
     */
    private UserEmployeeRecord getUserEmployeeByUserIdAndCompanyId(int userId,int companyId){
        Query query=new Query.QueryBuilder().where("sysuser_id",userId).and("company_id",companyId).and("disable",0).and("activation",0).buildQuery();
        UserEmployeeRecord DO=userEmployeeDao.getRecord(query);
        return DO;
    }
    /*
        根据id获取雇员的职位
     */
    private HrEmployeePositionDO getHrEmployeePositionById(int id){
        Query query=new Query.QueryBuilder().where("id",id).buildQuery();
        HrEmployeePositionDO DO=hrEmployeePositionDao.getData(query);
        return DO;
    }
    /*
       根据id获取雇员的部门
     */
    private HrEmployeeSectionDO getHrEmployeeSectionbyId(int id){
        Query query=new Query.QueryBuilder().where("id",id).buildQuery();
        HrEmployeeSectionDO DO=hrEmployeeSectionDao.getData(query);
        return DO;

    }
    /*
        根据companyId查询公司信息
     */
    private HrCompanyDO getCompanyById(int companyId){
        Query query=new Query.QueryBuilder().where("id",companyId).buildQuery();
        HrCompanyDO DO=hrCompanyDao.getData(query);
        return DO;
    }

    //获取企业微信公众号，根据company_id
    private HrWxWechatDO getHrWxWechatDOByCompanyId(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        HrWxWechatDO hrWxWechatDO=hrWxWechatDao.getData(query);
        return hrWxWechatDO;
    }
    /*
     根据模板id获取模板的内容
     */
    private  ConfigSysTemplateMessageLibraryDO getConfigSysTemplateMessageLibraryDOById(int id){
        Query query=new Query.QueryBuilder().where("id",id).buildQuery();
        ConfigSysTemplateMessageLibraryDO DO=configSysTemplateMessageLibraryDao.getData(query);
        return DO;
    }
    /*
      获取模板中的姓名
     */
    public String handleName(int userId){
        ProfileProfileDO profileProfileDO=this.getProfileProfileByUserId(userId);
        if(profileProfileDO!=null){
            int profileId=profileProfileDO.getId();
            ProfileBasicDO profileBasicDO=this.getProfileBasticByProfileId(profileId);
            if(profileBasicDO!=null){
                String name=profileBasicDO.getName();
                if(!StringUtils.isNullOrEmpty(name)){
                    return name;
                }
            }
        }
        UserWxUserDO userWxUserDO=this.getUserWxUserByUserId(userId);
        if(userWxUserDO!=null){
            String nickName=userWxUserDO.getNickname();
            if(!StringUtils.isNullOrEmpty(nickName)){
                return nickName;
            }
        }
        return null;
    }


    /*
      获取user_id简历profile_profile
     */
    private ProfileProfileDO  getProfileProfileByUserId(int userId){
        Query query=new Query.QueryBuilder().where("user_id",userId).buildQuery();
        ProfileProfileDO DO=profileProfileDao.getData(query);
        return DO;
    }
    /*
      获取profile_basic的信息
     */
    private ProfileBasicDO getProfileBasticByProfileId(int profileId){
        Query query=new Query.QueryBuilder().where("profile_id",profileId).buildQuery();
        ProfileBasicDO DO=profileBasicDao.getData(query);
        return DO;
    }
    /*
     通过user_id获取微信信息
     */
    private UserWxUserDO getUserWxUserByUserId(int userId){
        Query query=new Query.QueryBuilder().where("sysuser_id",userId).buildQuery();
        UserWxUserDO DO=userWxUserDao.getData(query);
        return DO;
    }
    /*
       获取用户信息
     */
    public UserUserDO getUserUserById(int userId){
        Query query=new Query.QueryBuilder().where("id",userId).buildQuery();
        UserUserDO DO=userUserDao.getData(query);
        return DO;
    }
    /*
     获取对userid推送的职位列表
     */
    private List<CampaignPersonaRecomRecord> getCampaignPersonaRecomRecordList(int userId,int companyId,int type,int page,int pageSize){
        Query query=new Query.QueryBuilder().where("user_id",userId).and("company_id",companyId).and("type",type).orderBy("create_time", Order.DESC)
                .setPageNum(page).setPageSize(pageSize).buildQuery();
        List<CampaignPersonaRecomRecord> list=campaignPersonaRecomDao.getRecords(query);
        return list;
    }
    /*
     处理CampaignPersonaRecomRecord 获取pid
     */
    private List<Integer> getPidListByCampaignPersonaRecomRecord(List<CampaignPersonaRecomRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(CampaignPersonaRecomRecord record:list){
            result.add(record.getPositionId());
        }
        return result;
    }
    /*
     获取数据表中是否存在这些职位
     */
    private int getPositionCount(List<Integer> pids){
        if(StringUtils.isEmptyList(pids)){
            return 0;
        }
        Query query=new Query.QueryBuilder().where(new Condition("id",pids.toArray(),ValueOp.IN)).and("status",0).buildQuery();
        int count=positionDao.getCount(query);
        return count;
    }
    /*
      将list转化为string
     */
    private String convertListToString(List<Integer> list){
        if(StringUtils.isEmptyList(list)){
            return "";
        }
        String pids="";
        for(Integer pid:list){
            pids+=pid+",";
        }
        if(StringUtils.isNotNullOrEmpty(pids)){
            pids=pids.substring(0,pids.lastIndexOf(","));
        }
        return pids;
    }
    /*
     处理获取推送的数据
     */
    private List<Integer> handleEmployeeRecomPosition(int userId,int companyId,int type){
        List<CampaignPersonaRecomRecord> list=this.getCampaignPersonaRecomRecordList(userId,companyId,type,0,20);
        List<Integer> pids=this.getPidListByCampaignPersonaRecomRecord(list);
        int count=this.getPositionCount(pids);
        if(count>0){
            return pids;
        }
        return null;
    }

    private int addCampaignRecomPositionlist(int companyId,String positionIds){
        CampaignRecomPositionlistRecord data=new CampaignRecomPositionlistRecord();
        data.setCompanyId(companyId);
        data.setPositionIds(positionIds);
        data.setType((byte)1);
        CampaignRecomPositionlistRecord campaignRecomPositionlist= campaignRecomPositionlistDao.addRecord(data);
        return campaignRecomPositionlist.getId();
    }

    private HrWxNoticeMessageRecord getHrWxTemplateMessage(int wechatId, int tempId){
        Query query=new Query.QueryBuilder().where(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID.getName(),tempId)
                .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID.getName(),wechatId)
                .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE.getName(),"0")
                .buildQuery();
        HrWxNoticeMessageRecord record=hrWxNoticeMessageDao.getRecord(query);
        return record;
    }


}
