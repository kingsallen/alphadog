package com.moseeker.position;

import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.position.liepin.LiepinPositionTransfer;
import com.moseeker.position.service.position.liepin.pojo.PositionLiepin;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PositionTest {

    @Autowired
    LiepinPositionTransfer transfer;

    @Test
    public void test(){
        ThirdPartyPosition positionForm =new ThirdPartyPosition();
        PositionLiepin position=new PositionLiepin();
        positionForm.setOccupation(new ArrayList<>(Arrays.asList("cate-01","cate-01-05","662","360331")));
        transfer.setOccupation(positionForm,position);
    }

}
