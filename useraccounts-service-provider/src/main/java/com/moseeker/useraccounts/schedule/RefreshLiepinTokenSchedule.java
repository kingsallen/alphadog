package com.moseeker.useraccounts.schedule;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.email.Email;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;
import com.rabbitmq.client.AMQP;
import com.taobao.api.domain.BizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时刷新猎聘hr账号token
 *
 * @author cjm
 * @date 2018-06-15 9:10
 **/

@Component
//@EnableScheduling
public class RefreshLiepinTokenSchedule {

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    private EmailNotification emailNotification;

    static List<String> emailList = new ArrayList<>();

    // {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
//    @Scheduled(cron="0 * * 1/15 * ?")
    public void refreshLiepinToken() {
        try {
            emailList = emailNotification.getRefreshMails();
            int channel = ChannelType.LIEPIN.getValue();
            int bindState = BindingStatus.BOUND.getValue();
            List<HrThirdPartyAccountDO> accountDOS = thirdPartyAccountDao.getRefreshAccounts(channel, bindState);
            if(accountDOS == null || accountDOS.size() < 1){
                emailNotification.sendWebBindFailureMail(emailList, null);
            }
//        }catch (BIZException e){

        }catch (Exception e){

        }


    }
}
