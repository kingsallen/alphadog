package com.moseeker.position.service.fundationbs;

import com.moseeker.position.config.AppConfig;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 17/10/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PositionThridServiceTest {
    @Autowired
    private PositionThridService positionThridService;

    @Test
    public void putAlipayTest(){
        int result=positionThridService.putAlipayPositionResult(1,200,30);
        System.out.println(result+"======================");
    }
    @Test
    public void getThridPosition() throws TException {
        Map<String,Object> list=positionThridService.getThridPositionAlipay(0,39978,1,1,10);
        System.out.println("==========================================");
        System.out.println(list);
        System.out.println("===========================================");
    }
}
