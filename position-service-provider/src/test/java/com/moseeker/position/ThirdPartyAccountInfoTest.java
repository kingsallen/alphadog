package com.moseeker.position;

import com.alibaba.fastjson.JSON;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.position.liepin.LiepinPositionTransfer;
import com.moseeker.position.service.third.ThirdPartyAccountInfoService;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPartyAccountInfoTest {
    @Autowired
    ThirdPartyAccountInfoService service;

    @Autowired
    LiepinPositionTransfer transfer;

    @Test
    public void test() throws Exception {
        ThirdPartyAccountInfoParam param=new ThirdPartyAccountInfoParam();
        param.setChannel(7);
        param.setHrId(82690);
        service.getAllInfo(param);
        /*ThirdPartyPosition positionForm=new ThirdPartyPosition();
        ThirdPartyPositionForSynchronization position=new ThirdPartyPositionForSynchronization();
        positionForm.setOccupation(new ArrayList<>(Arrays.asList("662","360331")));
        transfer.setOccupation(positionForm,position);*/
    }
}
