package com.moseeker.useraccounts.schedule;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.*;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.service.impl.LiePinUserAccountBindHandler;
import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    static List<String> emailList = new ArrayList<>();

    /**
     * {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
     * @author  cjm
     * @date  2018/7/9
     */
//    @Scheduled(cron="0 0 0 1,15 * ?")
//    @Scheduled(cron="0 0,5,10,15,20,25,30,35,40,45,50,55 * * * ? ")
    public void refreshLiepinToken() {
        long check= redisClient.incrIfNotExist(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.LIEPIN_TOKEN_REFRESH.toString(), "");
        if (check>1) {
            //绑定中
            logger.info("已经开始刷新");
            return;
        }
        redisClient.expire(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.LIEPIN_TOKEN_REFRESH.toString(), "", RefreshConstant.REFRESH_THIRD_PARTY_TOKEN_TIMEOUT);
        try {
            List<HrThirdPartyAccountDO> successRequest = new ArrayList<>();
            Map<Integer, String> failRequest = new HashMap<>();
            List<HrThirdPartyAccountDO> failUpdate = new ArrayList<>();
            emailList = emailNotification.getRefreshMails();
            int channel = ChannelType.LIEPIN.getValue();
            int bindState = BindingStatus.BOUND.getValue();
            StringBuilder content = new StringBuilder();
            StringBuilder subject = new StringBuilder();
            // 获取所有的绑定状态为1并且渠道为猎聘的第三方账号信息
            List<HrThirdPartyAccountDO> accountDOS = thirdPartyAccountDao.getRefreshAccounts(channel, bindState);

            if(accountDOS == null || accountDOS.size() < 1){
                subject.append("猎聘第三方hr账号token刷新失败");
                content.append("token方刷新时，查不到任何第三账号信息，本次刷新失败，" +
                        "刷新日期为:" + new SimpleDateFormat("yyyyMMdd").format(new Date()));
                emailNotification.sendCustomEmail(emailList, content.toString(), subject.toString());
                return;
            }
            // 将所有查出得第三方账号刷新token，如果抛出bizException则是猎聘返回的业务异常，将第三方账号id和异常信息放入failRequest的map中用于发邮件
            for(HrThirdPartyAccountDO accountDO : accountDOS){
                try{
                    accountDO = bindHandler.bindAdaptor(accountDO, null);
                    logger.info("========userId:{}, token:{}==============", accountDO.getExt(), accountDO.getExt2());
                    successRequest.add(accountDO);
                }catch (BIZException e){
                    logger.warn("=================errormsg:{},username:{}===================", e.getMessage(), accountDO.getUsername());
                    failRequest.put(accountDO.getId(), e.getMessage());
                }catch (Exception e){
                    logger.error(e.getMessage(), e);
                    failRequest.put(accountDO.getId(), e.getMessage());
                }
            }
            // 将成功刷新token的账号信息更新
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
            // 刷新token失败的信息发邮件
            if(failRequest.size() > 0){
                StringBuilder faileRequestContent = new StringBuilder("请求刷新第三方token失败，第三方账号HrThirdPartyAccountDO.id失败列表:");
                String faileRequestSubject = "猎聘token刷新失败项";
                Set<Integer> idSet = failRequest.keySet();
                for(Integer id : idSet){
                    faileRequestContent.append(id).append(":").append(failRequest.get(id)).append("</br>");
                }
                emailNotification.sendCustomEmail(emailList, faileRequestContent.toString(), faileRequestSubject);
            }
            // 数据库更新失败的信息发邮件
            if(failUpdate.size() > 0){
                StringBuilder faileRequestContent = new StringBuilder("更新token到数据库失败，详细信息如下:</br>");
                String faileRequestSubject = "定时任务更新猎聘token到数据库失败";
                for(HrThirdPartyAccountDO accountDO : failUpdate){
                    faileRequestContent.append(JSONObject.toJSONString(accountDO)).append("</br>");
                }
                emailNotification.sendCustomEmail(emailList, faileRequestContent.toString(), faileRequestSubject);
            }
            redisClient.del(AppId.APPID_ALPHADOG.getValue(),KeyIdentifier.LIEPIN_TOKEN_REFRESH.toString(),"");
        }catch (Exception e){
            e.printStackTrace();
            emailNotification.sendCustomEmail(emailList, "token刷新失败", "token刷新失败");
        }

    }

}
