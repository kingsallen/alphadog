package com.moseeker.application.service.impl;

import com.moseeker.application.config.AppConfig;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author cjm
 * @date 2018-09-26 12:00
 **/
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class JobApplicationDaoTest {
    Logger logger = LoggerFactory.getLogger(JobApplicationDaoTest.class);

    @Autowired
    private JobApplicataionService jobApplicationService;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private JobApplicationDao jobApplicationDao;

    ThreadPool pool = ThreadPool.Instance;

    private CountDownLatch batchHandlerCountDown = new CountDownLatch(1);

    private CyclicBarrier barrier = new CyclicBarrier(10);

//    @Test
    public void testAddIfNotExists(){
//        JobApplicationRecord record = new JobApplicationRecord();
//        record.setApplierId(5278111);
//        record.setApplierName("崔佳明");
//        record.setPositionId(134305);
//        record.setRoutine(10);
//        record.setOrigin(1);
        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplier_id(5278111);
        jobApplication.setApplier_name("崔佳明");
        jobApplication.setPosition_id(134305);
        jobApplication.setRoutine(10);
        jobApplication.setOrigin(1);
//        record.setCompanyId(39978);
        int i = 0;
        while(i<1000){
            i++;
            testAddIfNotExists(jobApplication);
        }
//        batchHandlerCountDown.countDown();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testAddIfNotExists(JobApplication record) {

        pool.startTast(() -> {
//            batchHandlerCountDown.await(60, TimeUnit.SECONDS);
            barrier.await();
            jobApplicationService.postApplication(record);
            return 1;
        });
    }

    private void testAddIfNotExists(JobApplicationRecord record) {

        pool.startTast(() -> {
//            batchHandlerCountDown.await(60, TimeUnit.SECONDS);
            barrier.await();
            jobApplicationDao.addIfNotExists(record);
            return 1;
        });
    }
//    @Test
    public void testAddIfNotExist1s() {
        JobApplicationRecord record = new JobApplicationRecord();
        record.setApplierId(5278111);
        record.setApplierName("崔佳明");
        record.setPositionId(134305);
        record.setRoutine(10);
        record.setOrigin(1);
        int i = 0;
        while(i<10){
            i++;
            testAddIfNotExists(record);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
