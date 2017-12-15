package com.moseeker.useraccounts.service.thirdpartyaccount.operation;

import com.moseeker.useraccounts.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DispatchOperationTest {
    /*@Autowired
    DispatchOperation operation;

    @Test
    public void test() throws Exception {
        List<Integer> hrIds= Arrays.asList(82690, 83323, 83324);
        int accountId=538;

        operation.dispatch(accountId,hrIds);
    }*/
}