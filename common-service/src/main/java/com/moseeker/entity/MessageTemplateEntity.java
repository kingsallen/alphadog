package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.profiledb.ProfileBasicDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageLibrary;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public MessageTemplateNoticeStruct handlerTemplate(int userId,int companyId,int templateId,int type){
        ConfigSysTemplateMessageLibraryDO DO=this.getConfigSysTemplateMessageLibraryDOById(templateId);
        String url=DO.getUrl();
        MessageTemplateNoticeStruct messageTemplateNoticeStruct =new MessageTemplateNoticeStruct();
        Map<String,MessageTplDataCol> colMap=this.handleMessageTemplateData(userId,type);
        if(colMap==null||colMap.isEmpty()){
            return null;
        }
        messageTemplateNoticeStruct.setCompany_id(companyId);
        messageTemplateNoticeStruct.setData(colMap);
        messageTemplateNoticeStruct.setUser_id(userId);
        messageTemplateNoticeStruct.setSys_template_id(templateId);
        messageTemplateNoticeStruct.setUrl(url);
        return messageTemplateNoticeStruct;
    }
    /*
        处理发送完善简历消息模板
     */
    private  Map<String,MessageTplDataCol> handleMessageTemplateData(int userId,int type){

        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        if(type==1){
            colMap=this.handleDataForuestion(userId);
        }else if(type==2){

        }else if(type==3){

        }else if(type==4){

        }
        return colMap;
    }
    /*
        组装完善模板简历数据
     */
    private Map<String,MessageTplDataCol> handleDataForuestion(int userId){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        String name=this.handleName(userId);
        UserUserDO DO=this.getUserUserById(userId);
        String time=DO.getRegisterTime();
        MessageTplDataCol keyword1=new MessageTplDataCol();
        keyword1.setColor("#173177");
        keyword1.setValue(name);
        colMap.put("keyword1",keyword1);
        MessageTplDataCol keyword2=new MessageTplDataCol();
        keyword1.setColor("#173177");
        keyword1.setValue(time);
        colMap.put("keyword2",keyword2);
//        MessageTplDataCol remark=new MessageTplDataCol();
//        remark.setColor("#173177");
//        remark.setValue("完善简历信息，让HR更了解你，会得到更多的关注哦");
//        colMap.put("remark",remark);
//        MessageTplDataCol first=new MessageTplDataCol();
//        first.setColor("#173177");
//        first.setValue("您好，请完善简历信息。");
//        colMap.put("first",first);
        return colMap;
    }
    /*
        推荐职位列表消息
     */
    private Map<String,MessageTplDataCol> handleDataRecommendTemplate(int companyId){
        Map<String,MessageTplDataCol> colMap =new HashMap<>();

        return null;
    }

    /*
        职位邀请通知
     */
    private Map<String,MessageTplDataCol> handleDataProfileTemplate(int userId){

        return null;
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
