package com.moseeker.useraccounts.schedule;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.constant.Job58Constant;
import com.moseeker.useraccounts.constant.UserAccountConstant;
import com.moseeker.useraccounts.service.impl.Job58BindProcessor;
import com.moseeker.useraccounts.service.impl.pojos.Job58BindUserInfo;
import com.moseeker.useraccounts.service.impl.vo.Job58TokenRefreshVO;
import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cjm
 * @date 2018-11-23 15:10
 **/
@Component
@EnableScheduling
public class RefreshJob58TokenSchedule {

    private static Logger logger = LoggerFactory.getLogger(RefreshJob58TokenSchedule.class);

    private final HRThirdPartyAccountDao thirdPartyAccountDao;

    private final EmailNotification emailNotification;

    private final Job58BindProcessor bindProcessor;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static List<String> emailList = new ArrayList<>();

    private static final Long ONE_YEAR = 1000L * 60 * 60 * 24 * 365;

    @Autowired
    public RefreshJob58TokenSchedule(Job58BindProcessor bindProcessor, HRThirdPartyAccountDao thirdPartyAccountDao,
                                     EmailNotification emailNotification) {
        this.bindProcessor = bindProcessor;
        this.thirdPartyAccountDao = thirdPartyAccountDao;
        this.emailNotification = emailNotification;
    }

    /**
     * 每3个月的1日刷新58token
     * {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
     * @author  cjm
     * @date  2018/7/9
     */
    @Scheduled(cron="0 0 0 1 3,6,9,12 ? ")
    public void refresh58Token() {
        // 避免不同服务器一起刷新
        long check= redisClient.incrIfNotExist(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.JOB58_TOKEN_REFRESH.toString(), "");
        if (check>1) {
            //绑定中
            logger.info("已经开始刷新");
            return;
        }
        redisClient.expire(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.JOB58_TOKEN_REFRESH.toString(), "", RefreshConstant.REFRESH_THIRD_PARTY_TOKEN_TIMEOUT);
        try {
            // 刷新成功的账号
            List<HrThirdPartyAccountDO> successRequest = new ArrayList<>();
            // 用户信息过期的账号，需要重新绑定
            List<HrThirdPartyAccountDO> expireAccount = new ArrayList<>();
            // 业务异常刷新失败的账号
            Map<Integer, String> failRequest = new HashMap<>(1 >> 4);
            emailList = emailNotification.getRefreshMails();
            int channel = ChannelType.JOB58.getValue();
            int bindState = BindingStatus.BOUND.getValue();
            // 获取所有的绑定状态为1并且渠道为58的第三方账号信息
            List<HrThirdPartyAccountDO> accountDOS = thirdPartyAccountDao.getRefreshAccounts(channel, bindState);

            if(accountDOS == null || accountDOS.size() < 1){
                logger.warn("=================无需要刷新的58token");
                return;
            }
            String appKey = Job58Constant.APP_KEY;
            // 将所有查出得第三方账号刷新token，如果抛出bizException则是猎聘返回的业务异常，将第三方账号id和异常信息放入failRequest的map中用于发邮件
            for(HrThirdPartyAccountDO accountDO : accountDOS){
                if(!checkNeedRefresh(accountDO)){
                    expireAccount.add(accountDO);
                    continue;
                }
                try{
                    // 开始刷新token
                    Job58BindUserInfo bindUserInfo = JSONObject.parseObject(accountDO.getExt(), Job58BindUserInfo.class);
                    Job58TokenRefreshVO refreshVO = new Job58TokenRefreshVO(bindUserInfo.getOpenId(), bindUserInfo.getAccessToken(),System.currentTimeMillis(),
                            appKey, bindUserInfo.getRefreshToken());
                    accountDO = bindProcessor.handleBind(accountDO, refreshVO, UserAccountConstant.job58UserRefreshUrl);
                    accountDO.setUpdateTime(null);
                    successRequest.add(accountDO);
                }catch (BIZException e){
                    logger.warn("=================errormsg:{},username:{}===================", e.getMessage(), accountDO.getUsername());
                    // 如果返回值是109表示token过期无法刷新
                    if(e.getCode() == 109 || e.getCode() == 108){
                        expireAccount.add(accountDO);
                    }else{
                        failRequest.put(accountDO.getId(), e.getMessage());
                    }
                }catch (Exception e){
                    logger.error(e.getMessage(), e);
                    failRequest.put(accountDO.getId(), e.getMessage());
                }
            }
            // 将成功刷新token的账号信息更新
            if(successRequest.size() > 0){
                thirdPartyAccountDao.updateDatas(successRequest);
            }
            handleFailRequest(failRequest);
            handleExpireAccount(expireAccount);
            redisClient.del(AppId.APPID_ALPHADOG.getValue(),KeyIdentifier.JOB58_TOKEN_REFRESH.toString(),"");
        }catch (Exception e){
            e.printStackTrace();
            emailNotification.sendCustomEmail(emailList, "token刷新失败", "token刷新失败");
        }

    }

    private void handleExpireAccount(List<HrThirdPartyAccountDO> expireAccount) {
        for(HrThirdPartyAccountDO accountDO : expireAccount){
            accountDO.setErrorMessage("用户信息过期，请重新绑定");
            accountDO.setBinding((short)10);
            accountDO.setUpdateTime(null);
        }
        thirdPartyAccountDao.updateDatas(expireAccount);
    }

    private void handleFailRequest(Map<Integer, String> failRequest) {
        // 刷新token失败的信息发邮件
        if(failRequest.size() > 0){
            StringBuilder failRequestContent = new StringBuilder("请求刷新第三方token失败，第三方账号HrThirdPartyAccountDO.id失败列表:");
            String faileRequestSubject = "58token刷新失败项";
            Set<Integer> idSet = failRequest.keySet();
            for(Integer id : idSet){
                failRequestContent.append(id).append(":").append(failRequest.get(id)).append("</br>");
            }
            emailNotification.sendCustomEmail(emailList, failRequestContent.toString(), faileRequestSubject);
        }
    }

    /**
     * 绑定时间距离刷新时间满一年则不刷新
     * @param   accountDO 第三方账号信息
     * @author  cjm
     * @date  2018/11/23
     * @return   是否需要刷新，true为需要
     */
    private boolean checkNeedRefresh(HrThirdPartyAccountDO accountDO) {
        String syncTimeStr = accountDO.getSyncTime();
        Date date;
        try {
            date = sdf.parse(syncTimeStr);
        } catch (ParseException e) {
            logger.info("58刷新token绑定时间解析异常，msg:{}", e.getMessage());
            return false;
        }
        long currentTime = System.currentTimeMillis();
        long syncTime = date.getTime();
        return currentTime - syncTime < ONE_YEAR;
    }
}
