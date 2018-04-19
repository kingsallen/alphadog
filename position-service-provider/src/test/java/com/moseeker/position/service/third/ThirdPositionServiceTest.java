package com.moseeker.position.service.third;

import com.moseeker.position.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPositionServiceTest {

    @Autowired
    ThirdPositionService thirdPositionService;

    @Test
    public void updateThirdPartyPositionWithAccount() throws BIZException {
        HrThirdPartyPositionDO thirdPartyPosition = new HrThirdPartyPositionDO();
        HrThirdPartyAccountDO thirdPartyAccount = new HrThirdPartyAccountDO();
        Map<String,String> extData = new HashMap<>();

        thirdPartyPosition.setId(3126);
        thirdPartyPosition.setIsSynchronization(3);

        thirdPositionService.updateThirdPartyPositionWithAccount(thirdPartyPosition,thirdPartyAccount,extData);

    }

}