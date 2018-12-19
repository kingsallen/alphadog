package com.moseeker.useraccounts.service.impl;

import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.referral.struct.CheckEmployeeInfo;
import com.moseeker.thrift.gen.referral.struct.ConnectRadarInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralCardInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralInviteInfo;
import com.moseeker.useraccounts.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author cjm
 * @date 2018-12-17 13:55
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ReferralServiceTest {

    @Autowired
    private ReferralServiceImpl referralService;

    @Test
    public void testGetRadar(){
        ReferralCardInfo  cardInfo = new ReferralCardInfo();
        cardInfo.setUser_id(5283788);
        cardInfo.setTimestamp(1545034217595L);
        cardInfo.setPage_size(10);
        cardInfo.setPage_number(1);
        cardInfo.setCompany_id(39978);
        referralService.getRadarCards(cardInfo);
    }

    @Test
    public void inviteApplicationTest(){
        ReferralInviteInfo inviteInfo = new ReferralInviteInfo();
        inviteInfo.setCompanyId(39978);
        inviteInfo.setEndUserId(5291537);
        inviteInfo.setPid(19501370);
        inviteInfo.setTimestamp(1545034217595L);
        inviteInfo.setUserId(5283788);
        referralService.inviteApplication(inviteInfo);
    }

    @Test
    public void ignoreCurrentViewerTest(){
        ReferralInviteInfo ignoreInfo = new ReferralInviteInfo();
        ignoreInfo.setCompanyId(39978);
        ignoreInfo.setEndUserId(5291680);
        ignoreInfo.setPid(19501370);
        ignoreInfo.setTimestamp(1545034217595L);
        ignoreInfo.setUserId(5283788);
        referralService.ignoreCurrentViewer(ignoreInfo);
    }

    @Test
    public void connectRadarTest(){
        ConnectRadarInfo radarInfo = new ConnectRadarInfo();
        referralService.connectRadar(radarInfo);
    }

    @Test
    public void checkEmployeeTest() throws BIZException {
        CheckEmployeeInfo checkInfo = new CheckEmployeeInfo();
        checkInfo.setParentChainId(5044);
        checkInfo.setPid(19501370);
        checkInfo.setRecomUserId(5291680);
        referralService.checkEmployee(checkInfo);
    }
}
