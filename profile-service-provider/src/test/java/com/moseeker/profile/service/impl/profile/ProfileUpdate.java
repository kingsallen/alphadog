package com.moseeker.profile.service.impl.profile;

import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveRecordDao;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecordRecord;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileBasicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zztaiwll on 18/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileUpdate {

    @Autowired
    private ProfileBasicService profileBasicService;
    @Test
    public  void basicTest(){
//        profileBasicService.delBasic(251314);
    }

    @Autowired
    TalentPoolProfileMoveRecordDao  dao;

    @Test
    public void test1(){
        TalentpoolProfileMoveRecordRecord recordRecord = dao.getOneProfileMoveRecordById(186);
    }
}
