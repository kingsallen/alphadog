package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeePositionDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeSectionDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.profiledb.ProfileBasicDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageLibrary;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.pojos.Data;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeePositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeSectionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.SimpleFormatter;

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

    public MessageTemplateNoticeStruct handlerTemplate(int userId,int companyId,int templateId,int type,String url){
       //https://platform.moseeker.com/m/user/survey?wechat_siganture=xxx
        if(StringUtils.isNullOrEmpty(url)){
            if(type==1){
                HrWxWechatDO DO= this.getHrWxWechatDOByCompanyId(companyId);
                String wxSignture=DO.getSignature();
                url=url.replace("{}",wxSignture);
            }
        }
        MessageTemplateNoticeStruct messageTemplateNoticeStruct =new MessageTemplateNoticeStruct();
        Map<String,MessageTplDataCol> colMap=this.handleMessageTemplateData(userId,type,companyId);
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
    /*
        处理发送完善简历消息模板
     */
    private  Map<String,MessageTplDataCol> handleMessageTemplateData(int userId,int type,int companyId){

        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        if(type==1){
            colMap=this.handleDataForuestion(userId);
        }else if(type==2||type==3){
            colMap=this.handleDataRecommendTemplate(companyId);
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
        MessageTplDataCol keyword1=new MessageTplDataCol();
        keyword1.setColor("#173177");
        keyword1.setValue(name);
        colMap.put("keyword1",keyword1);
        MessageTplDataCol keyword2=new MessageTplDataCol();
        keyword2.setColor("#173177");
        keyword2.setValue(time);
        colMap.put("keyword2",keyword2);
        return colMap;
    }

    /*
        推荐职位列表消息数据
     */
    private Map<String,MessageTplDataCol> handleDataRecommendTemplate(int companyId){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        SimpleDateFormat sf=new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
        String data=sf.format(new Date());
        MessageTplDataCol keyword1=new MessageTplDataCol();
        keyword1.setColor("#173177");
        HrCompanyDO DO=this.getCompanyById(companyId);
        if(DO==null){
            return null;
        }
        keyword1.setValue(DO.getAbbreviation());
        colMap.put("keyword1",keyword1);
        MessageTplDataCol keyword2=new MessageTplDataCol();
        keyword2.setColor("#173177");
        keyword2.setValue(data);
        colMap.put("keyword2",keyword2);
        return colMap;
    }

    /*
     完善简历信息数据模板
     */
    private Map<String,MessageTplDataCol> handleDataProfileTemplate(int userId,int companyId){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        UserEmployeeDO DO=this.getUserEmployeeByUserIdAndCompanyId(userId,companyId);
        if(DO==null){
            return null;
        }
        HrEmployeePositionDO hrEmployeePositionDO=this.getHrEmployeePositionById(DO.getPositionId());
        if(hrEmployeePositionDO==null){
            hrEmployeePositionDO=new HrEmployeePositionDO();
        }
        HrEmployeeSectionDO hrEmployeeSectionDO=this.getHrEmployeeSectionbyId(DO.getSectionId());
        if(hrEmployeeSectionDO==null){
            hrEmployeeSectionDO=new HrEmployeeSectionDO();
        }
        MessageTplDataCol keyword1=new MessageTplDataCol();
        keyword1.setValue(DO.getCname());
        keyword1.setColor("#173177");
        colMap.put("keyword1",keyword1);
        MessageTplDataCol keyword2=new MessageTplDataCol();
        keyword2.setValue(hrEmployeePositionDO.getName());
        keyword2.setColor("#173177");
        colMap.put("keyword2",keyword2);
        MessageTplDataCol keyword3=new MessageTplDataCol();
        keyword3.setValue(hrEmployeeSectionDO.getName());
        keyword3.setColor("#173177");
        colMap.put("keyword3",keyword3);
        return colMap;
    }
    /*
      根据user_id和company_id查找雇员信息
     */
    private UserEmployeeDO getUserEmployeeByUserIdAndCompanyId(int userId,int companyId){
        Query query=new Query.QueryBuilder().where("sysuser_id",userId).and("company_id",companyId).buildQuery();
        UserEmployeeDO DO=userEmployeeDao.getData(query);
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
