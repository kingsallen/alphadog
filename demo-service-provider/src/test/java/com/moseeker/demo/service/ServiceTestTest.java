package com.moseeker.demo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jack on 23/03/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTestTest {

    @Autowired
    ServiceTest serviceTest;

    @Before
    public void init() {

    }

    @Test
    public void testDeclarativeTransactions() {
        serviceTest.testTransaction();
    }

}