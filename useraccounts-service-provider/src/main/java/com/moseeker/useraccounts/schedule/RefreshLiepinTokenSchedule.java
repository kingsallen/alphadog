package com.moseeker.useraccounts.schedule;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.email.Email;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.service.impl.LiePinUserAccountBindHandler;
import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;
import com.moseeker.useraccounts.utils.AESUtils;
import com.rabbitmq.client.AMQP;
import com.taobao.api.domain.BizResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时刷新猎聘hr账号token
 *
 * @author cjm
 * @date 2018-06-15 9:10
 **/

@Component
@EnableScheduling
public class RefreshLiepinTokenSchedule {

    static Logger logger = LoggerFactory.getLogger(RefreshLiepinTokenSchedule.class);

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    private LiePinUserAccountBindHandler bindHandler;

    @Autowired
    private EmailNotification emailNotification;

    static List<String> emailList = new ArrayList<>();

    // {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
//    @Scheduled(fixedDelay = 120000)
//    @Scheduled(cron="0 0 0 1,15 * ?")
    public void refreshLiepinToken() {
        try {
            List<HrThirdPartyAccountDO> successRequest = new ArrayList<>();
            List<Integer> failRequest = new ArrayList<>();
            List<HrThirdPartyAccountDO> failUpdate = new ArrayList<>();
            // 猎聘返回用户名或密码错误时，需要重新绑定

            emailList = emailNotification.getRefreshMails();
            int channel = ChannelType.LIEPIN.getValue();
            int bindState = BindingStatus.BOUND.getValue();
            StringBuilder content = new StringBuilder();
            StringBuilder subject = new StringBuilder();
            List<HrThirdPartyAccountDO> accountDOS = thirdPartyAccountDao.getRefreshAccounts(channel, bindState);
            if(accountDOS == null || accountDOS.size() < 1){
                subject.append("猎聘第三方hr账号token刷新失败");
                content.append("token方刷新时，查不到任何第三账号信息，本次刷新失败，" +
                        "刷新日期为:" + new SimpleDateFormat("yyyyMMdd").format(new Date()));
                emailNotification.sendCustomEmail(emailList, content.toString(), subject.toString());
                return;
            }

            for(HrThirdPartyAccountDO accountDO : accountDOS){
                try{
                    accountDO = bindHandler.bind(accountDO, null);
                    logger.info("========userId:{}, token:{}==============", accountDO.getExt(), accountDO.getExt2());
                    successRequest.add(accountDO);
                }catch (BIZException e){
                    e.printStackTrace();
                    failRequest.add(accountDO.getId());
                }
            }
            // todo 密码错误时，绑定状态置为0
            if(successRequest.size() > 0){
                for(HrThirdPartyAccountDO accountDO : successRequest){
                    if(StringUtils.isNotNullOrEmpty(accountDO.getExt()) && StringUtils.isNotNullOrEmpty(accountDO.getExt2())){
                        int row = thirdPartyAccountDao.updateBindToken(accountDO.getExt2(), Integer.parseInt(accountDO.getExt()), accountDO.getId());
                        if(row < 1){
                            failUpdate.add(accountDO);
                        }
                    }
                }
            }

            if(failRequest.size() > 0){
                StringBuilder faileRequestContent = new StringBuilder("请求第三方token失败，失败列表:</br>");
                String faileRequestSubject = "猎聘token刷新存在请求失败项";

                for(Integer id : failRequest){
                    faileRequestContent.append(id).append("</br>");
                }
                emailNotification.sendCustomEmail(emailList, faileRequestContent.toString(), faileRequestSubject);
            }

            if(failUpdate.size() > 0){
                StringBuilder faileRequestContent = new StringBuilder("更新token到数据库失败，详细信息如下:</br>");
                String faileRequestSubject = "定时任务更新猎聘token到数据库失败";
                for(HrThirdPartyAccountDO accountDO : failUpdate){
                    faileRequestContent.append(JSONObject.toJSONString(accountDO)).append("</br>");
                }
                emailNotification.sendCustomEmail(emailList, faileRequestContent.toString(), faileRequestSubject);
            }
        }catch (Exception e){
            e.printStackTrace();
            emailNotification.sendCustomEmail(emailList, "token刷新失败", "token刷新失败");
        }

    }

}
