package com.moseeker.position.service.position.job51.refresh.handler;

import com.moseeker.position.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class Job51OccupationResultHandlerTest {

    /*@Autowired
    Job51OccupationResultHandler occupationResultHandler;

    @Test
    public void test() throws IOException {
        List<String> jsonLine=Files.readAllLines(Paths.get("/Users/pyb/Desktop/Job51Occupation.txt"));

        StringBuilder sb=new StringBuilder();
        jsonLine.forEach(str->sb.append(str));

        occupationResultHandler.handle(sb.toString());
    }*/
}