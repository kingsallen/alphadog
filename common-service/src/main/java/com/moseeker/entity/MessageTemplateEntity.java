package com.moseeker.entity;

import com.moseeker.baseorm.dao.campaigndb.CampaignPersonaRecomDao;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.profiledb.ProfileBasicDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignPersonaRecom;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPersonaRecomRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.pojo.JobPositionPojo;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.Constant.JobStatus;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zztaiwll on 17/10/20.
 */
@Service
public class MessageTemplateEntity {
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


    public MessageTemplateNoticeStruct handlerTemplate(int userId,int companyId,int templateId,int type,String url,String jobName,String companyName){

        HrWxWechatDO DO= this.getHrWxWechatDOByCompanyId(companyId);
        if(type==1){
            //https://platform.moseeker.com/m/user/survey?wechat_siganture=xxx
            String wxSignture=DO.getSignature();
            url=url.replace("{}",wxSignture);
        }else if(type==2){
            //https://platform-t.dqprism.com/m/user/ai-recom?wechat_signature=xxx
            String wxSignture=DO.getSignature();
            url=url.replace("{}",wxSignture);
        }
        MessageTemplateNoticeStruct messageTemplateNoticeStruct =new MessageTemplateNoticeStruct();

        companyName = getCompanyName(companyId);
        jobName = getJobName(companyId);
        Map<String,MessageTplDataCol> colMap=this.handleMessageTemplateData(userId,type,companyId,jobName,companyName);
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

    private String getJobName(int companyId) {

        List<Integer> positionIdList = positionDao.getPositionIds(new ArrayList<Integer>(){{add(companyId);}});
        if (positionIdList != null && positionIdList.size() > 0) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.select(CampaignPersonaRecom.CAMPAIGN_PERSONA_RECOM.POSITION_ID.getName())
                    .where(new Condition(CampaignPersonaRecom.CAMPAIGN_PERSONA_RECOM.POSITION_ID.getName(), positionIdList, ValueOp.IN))
                    .setPageNum(1);

            CampaignPersonaRecomRecord campaignPersonaRecom = campaignPersonaRecomDao.getRecord(queryBuilder.buildQuery());
            if (campaignPersonaRecom != null) {
                JobPositionPojo positionPojo = positionDao.getPosition(campaignPersonaRecom.getPositionId());
                if (positionPojo != null) {
                    return positionPojo.title+"等";
                }
            }
        }
        return null;
    }

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
    private  Map<String,MessageTplDataCol> handleMessageTemplateData(int userId,int type,int companyId,String jobName,String companyName){

        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        if(type==1){
            colMap=this.handleDataForuestion(userId);
        }else if(type==2||type==3){
            colMap=this.handleDataRecommendTemplate(companyId,userId,type,jobName,companyName);
        }else if(type==4){
             colMap=this.handleDataProfileTemplate(userId,companyId);
        }
        return colMap;
    }
    /*
        组装完善粉丝模板数据
     */
    private Map<String,MessageTplDataCol> handleDataForuestion(int userId){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        String name=this.handleName(userId);
        UserUserDO DO=this.getUserUserById(userId);
        String time="";
        if(DO!=null){
            time=DO.getRegisterTime();
        }
        MessageTplDataCol first=new MessageTplDataCol();
        first.setColor("#173177");
        first.setValue("您好，请完善简历信息。");
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
        remark.setValue("完善简历信息，让HR更了解你，会得到更多的关注哦");
        colMap.put("remark",remark);
        return colMap;
    }

    /*
        推荐职位列表消息数据
     */
    private Map<String,MessageTplDataCol> handleDataRecommendTemplate(int companyId,int userId,int type,String jobName,String companyName){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        UserUserDO userDO=this.getUserUserById(userId);
        String name="";
        if(userDO!=null){
            name=userDO.getName();
        }
        if(type==2){
            MessageTplDataCol first=new MessageTplDataCol();
            first.setColor("#173177");
            first.setValue("根据您的求职意愿，仟寻为您挑选了一些新机会。");
            colMap.put("first",first);
            MessageTplDataCol remark=new MessageTplDataCol();
            remark.setColor("#173177");
            remark.setValue("点击查看推荐职位。");
            colMap.put("remark",remark);
        }
        if(type==3){
            MessageTplDataCol first=new MessageTplDataCol();
            first.setColor("#173177");
            first.setValue("以下职位虚位以待，赶快转发起来吧~");
            colMap.put("first",first);
            MessageTplDataCol remark=new MessageTplDataCol();
            remark.setColor("#173177");
            remark.setValue("点击查看推荐职位。");
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
    private Map<String,MessageTplDataCol> handleDataProfileTemplate(int userId,int companyId){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        UserEmployeeRecord DO=this.getUserEmployeeByUserIdAndCompanyId(userId,companyId);
        if(DO==null){
            return null;
        }
        MessageTplDataCol first=new MessageTplDataCol();
        first.setColor("#173177");
        first.setValue("您有待完善的员工信息。");
        colMap.put("first",first);
        MessageTplDataCol remark=new MessageTplDataCol();
        remark.setColor("#173177");
        remark.setValue("完善员工信息，参与企业内推，还可以赚积分哦~");
        colMap.put("remark",remark);
        MessageTplDataCol keyword1=new MessageTplDataCol();
        keyword1.setValue(DO.getCname());
        keyword1.setColor("#173177");
        colMap.put("keyword1",keyword1);
        MessageTplDataCol keyword2=new MessageTplDataCol();
        keyword2.setValue(DO.getPosition());
        keyword2.setColor("#173177");
        colMap.put("keyword2",keyword2);
        HrTeamDO   hrTeamDO=this.getTeamById(DO.getTeamId());
        MessageTplDataCol keyword3=new MessageTplDataCol();
        keyword3.setValue(hrTeamDO.getName());
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
        return DO;
    }
    /*
      根据user_id和company_id查找雇员信息
     */
    private UserEmployeeRecord getUserEmployeeByUserIdAndCompanyId(int userId,int companyId){
        Query query=new Query.QueryBuilder().where("sysuser_id",userId).and("company_id",companyId).buildQuery();
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
}
