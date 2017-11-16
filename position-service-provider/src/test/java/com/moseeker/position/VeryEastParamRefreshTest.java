package com.moseeker.position;

import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.position.veryeast.VeryEastParamRefresh;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class VeryEastParamRefreshTest{

    @Autowired
    VeryEastParamRefresh refresh;

    String file="C:\\Users\\xym-moseeker\\Desktop\\environ.response.json";

    @Test
    public void test(){
        refresh.send();

        /*try(BufferedReader br=new BufferedReader(new FileReader(file))){

            StringBuilder sb=new StringBuilder();
            String temp=null;
            while( (temp=br.readLine()) !=null){
                sb.append(temp);
            }

            refresh.receiveAndHandle(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
}
