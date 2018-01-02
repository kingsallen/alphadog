package com.moseeker.position.service.position.veryeast;

import com.moseeker.position.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class VeryEastTransferTest {
    @Autowired
    VeryEastTransfer veryEastTransfer;

    @Test
    public void test(){
//        System.out.println(veryEastTransfer.transferSalary(-1));
//        System.out.println(veryEastTransfer.transferExpreience("abc"));
    }
}