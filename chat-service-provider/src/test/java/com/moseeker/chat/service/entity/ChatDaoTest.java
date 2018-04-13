package com.moseeker.chat.service.entity;

import com.moseeker.chat.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ChatDaoTest {

    @Autowired
    ChatDao chatDao;

    @Test
    public void listHr(){
//        int hrIds[] = {94000, 92763, 85692, 86253, 89704, 85893, 93000, 85244, 90226, 84312};
        int hrIds[] = {88961, 85474, 91135, 94868, 87289, 61425, 60264, 16989, 94663, 93681};
        chatDao.listHr(hrIds);
    }
}