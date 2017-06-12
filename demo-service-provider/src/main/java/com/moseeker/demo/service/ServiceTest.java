package com.moseeker.demo.service;

import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionDao;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateCompanyRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jack on 22/03/2017.
 */
@Service
public class ServiceTest {

    @Autowired
    CandidateCompanyDao candidateCompanyDao;

    @Autowired
    CandidatePositionDao candidatePositionDao;

    @Transactional
    public void testTransaction() {

        CandidateCompanyRecord candidateCompanyRecord = new CandidateCompanyRecord();
        candidateCompanyRecord.setCompanyId(1);
        candidateCompanyRecord.setEmail("wjf@email.com");
        candidateCompanyRecord.setHeadimgurl("headimg");
        candidateCompanyRecord.setName("name");
        candidateCompanyRecord.setMobile("mobile");
        candidateCompanyRecord.setNickname("nickname");
        candidateCompanyRecord.setSysUserId((int)(1));
        candidateCompanyRecord = candidateCompanyDao.addRecord(candidateCompanyRecord);

        CandidatePositionRecord candidatePositionRecord = new CandidatePositionRecord();
        candidatePositionRecord.setCandidateCompanyId(candidateCompanyRecord.getId());
        candidatePositionRecord.setIsInterested((byte)0);
        candidatePositionRecord.setPositionId(1);
        candidatePositionRecord.setUserId((int)(1));
        candidatePositionRecord.setSharedFromEmployee((byte)0);
        candidatePositionRecord.setViewNumber(3);
        candidatePositionRecord.setWxuserId(3);
        candidatePositionRecord = candidatePositionDao.addRecord(candidatePositionRecord);
    }

    @Transactional
    public void testTransactionRolback() {

        CandidateCompanyRecord candidateCompanyRecord = new CandidateCompanyRecord();
        candidateCompanyRecord.setCompanyId(1);
        candidateCompanyRecord.setEmail("wjf1@email.com");
        candidateCompanyRecord.setHeadimgurl("headimg1");
        candidateCompanyRecord.setName("name1");
        candidateCompanyRecord.setMobile("mobile1");
        candidateCompanyRecord.setNickname("nickname1");
        candidateCompanyRecord.setSysUserId((int)(3));
        candidateCompanyRecord = candidateCompanyDao.addRecord(candidateCompanyRecord);

        int i=1, j=0;
        int k = i/j;

        CandidatePositionRecord candidatePositionRecord = new CandidatePositionRecord();
        candidatePositionRecord.setCandidateCompanyId(candidateCompanyRecord.getId());
        candidatePositionRecord.setIsInterested((byte)0);
        candidatePositionRecord.setPositionId(1);
        candidatePositionRecord.setUserId((int)(3));
        candidatePositionRecord.setSharedFromEmployee((byte)0);
        candidatePositionRecord.setViewNumber(3);
        candidatePositionRecord.setWxuserId(3);
        candidatePositionRecord = candidatePositionDao.addRecord(candidatePositionRecord);
    }
}
