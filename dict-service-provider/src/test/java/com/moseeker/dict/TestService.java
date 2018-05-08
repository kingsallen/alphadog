package com.moseeker.dict;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.config.AppConfig;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.dict.service.impl.DictOccupationService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestService {
    Map<Class,AbstractOccupationHandler> map;

    @Autowired
    DictOccupationService service;


    @Test
    public void test() throws BIZException {
        queryOccupation(ChannelType.JOB51);

        queryOccupation(ChannelType.LIEPIN);

        queryOccupation(ChannelType.ZHILIAN);

        queryOccupation(ChannelType.VERYEAST);

        queryOccupation(ChannelType.JOB1001);

        queryOccupation(ChannelType.JOBSDB);

    }

    void queryOccupation(ChannelType channelType) throws BIZException {
        Param param=new Param();
        param.channel = channelType.getValue();
        print(channelType.getAlias());
        print(service.queryOccupation(JSON.toJSONString(param)));
    }


    public void print(Object str){
        System.out.println(JSON.toJSONString(str));
    }

}

class Param{
    public int channel;
    public int single_layer;
    public int level;
    public int parent_id;
    public int code;
}