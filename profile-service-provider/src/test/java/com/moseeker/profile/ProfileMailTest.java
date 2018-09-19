package com.moseeker.profile;

import com.moseeker.entity.biz.ProfileMailUtil;
import com.moseeker.entity.pojo.resume.Account;
import com.moseeker.profile.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 简历解析发送邮件test
 *
 * @author cjm
 * @date 2018-06-26 14:46
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileMailTest {
    @Autowired
    ProfileMailUtil profileMailUtil ;
    @Test
    public void testSendProfileParseWarnMail() throws InterruptedException {

        Account account = new Account();
        account.setUsage_remaining(12);
        account.setUsage_limit(12222);
        profileMailUtil.sendProfileParseWarnMail(account);
        Thread.sleep(20000);
    }

//    @Autowired
//    ProfileService profileService;
//    @Test
//    public void testvalidateParseLimit() throws InterruptedException {
//        ResumeObj resumeObj = new ResumeObj();
//        Account account = new Account();
//        account.setUsage_limit(10000);
//        account.setUsage_remaining(1212);
//        resumeObj.setAccount(account);
//        profileService.validateParseLimit(resumeObj);
//        account.setUsage_remaining(999);
//        resumeObj.setAccount(account);
//        profileService.validateParseLimit(resumeObj);
//        Thread.sleep(10000);
//    }
}
