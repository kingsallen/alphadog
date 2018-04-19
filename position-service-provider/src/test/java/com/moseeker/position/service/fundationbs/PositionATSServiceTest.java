package com.moseeker.position.service.fundationbs;

import com.moseeker.position.config.AppConfig;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import com.moseeker.thrift.gen.position.struct.JobPostrionObj;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PositionATSServiceTest {

    @Autowired
    PositionATSService positionATSService;

    @Test
    public void updatePositionFeature() throws TException {
        BatchHandlerJobPostion batchHandlerJobPostion = new BatchHandlerJobPostion();
        List<JobPostrionObj> list = new ArrayList<>();
        JobPostrionObj obj = new JobPostrionObj();
        obj.setId(1914188);
        obj.setCompany_id(39978);
        obj.setFeature("18#20#30");
        list.add(obj);
        batchHandlerJobPostion.setData(list);
        positionATSService.updatePositionFeature(batchHandlerJobPostion);
    }
}