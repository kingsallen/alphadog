package com.moseeker.position.service.position.job1001.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.position.job1001.YLParamRefresher;
import com.moseeker.position.utils.PositionEmailNotification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileReader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class YLOccupationResultHandlerTest {

    @Autowired
    YLParamRefresher refresh;

    @Autowired
    PositionEmailNotification emailNotification;

    String file="C:\\Users\\xym-moseeker\\Desktop\\备份\\YLoccupation.txt";

    @Test
    public void test(){
//        refresh.send();
        String temp=null;
        try(BufferedReader br=new BufferedReader(new FileReader(file))){

            StringBuilder sb=new StringBuilder();

            while( (temp=br.readLine()) !=null){
                sb.append(temp);
            }

            JSONObject obj=JSONObject.parseObject(sb.toString());

            refresh.receiveAndHandle(sb.toString());
        } catch (Exception e){
            emailNotification.sendRefreshFailureMail(temp,refresh,e);
        }


    }

}