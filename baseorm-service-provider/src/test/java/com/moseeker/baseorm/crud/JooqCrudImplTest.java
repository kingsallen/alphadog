package com.moseeker.baseorm.crud;

import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateCompanyRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jack on 21/06/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class JooqCrudImplTest {

    @Autowired
    private CandidateCompanyDao candidateCompanyDao;

    @Test
    public void batchInsertTest() {
        List<CandidateCompanyRecord> list = new ArrayList<>();

        CandidateCompanyRecord referralCompanyConfRecord1 = new CandidateCompanyRecord();
        referralCompanyConfRecord1.setCompanyId(100);
        referralCompanyConfRecord1.setSysUserId(1);
        list.add(referralCompanyConfRecord1);

        CandidateCompanyRecord referralCompanyConfRecord2 = new CandidateCompanyRecord();
        referralCompanyConfRecord2.setCompanyId(101);
        referralCompanyConfRecord2.setSysUserId(2);
        list.add(referralCompanyConfRecord2);

        CandidateCompanyRecord referralCompanyConfRecord3 = new CandidateCompanyRecord();
        referralCompanyConfRecord3.setCompanyId(103);
        referralCompanyConfRecord3.setSysUserId(3);
        list.add(referralCompanyConfRecord3);

        list = candidateCompanyDao.addAllRecord(list);
        
        list.forEach(candidateCompanyRecord -> {
            assertEquals(true, candidateCompanyRecord.getId() != null && candidateCompanyRecord.getId() > 0);
        });

        list.forEach(System.out::println);


        CandidateCompanyRecord referralCompanyConfRecord4 = new CandidateCompanyRecord();
        referralCompanyConfRecord4.setCompanyId(104);
        referralCompanyConfRecord4.setSysUserId(4);
        List<CandidateCompanyRecord> list1  = candidateCompanyDao.addAllRecord(new ArrayList<CandidateCompanyRecord>(){{add(referralCompanyConfRecord4);}});
        list1.forEach(candidateCompanyRecord -> {
            assertEquals(true, candidateCompanyRecord.getId() != null && candidateCompanyRecord.getId() > 0);
        });

        list1.forEach(System.out::println);
    }
}