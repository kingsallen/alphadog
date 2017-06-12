package com.moseeker.baseorm.db.candidatedb.tables.records;

import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.thrift.gen.dao.struct.CandidateCompanyDO;
import com.moseeker.thrift.gen.dao.struct.CandidatePositionDO;
import org.joda.time.DateTime;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by jack on 22/02/2017.
 */
public class CandidateCompanyTest {

    //@Test
    public void convertToDOTest() {
        long time = System.currentTimeMillis();
        CandidateCompanyRecord record = new CandidateCompanyRecord();
        record.setId(1);
        record.setWxuserId(2);
        record.setUpdateTime(new Timestamp(time));
        record.setStatus(3);
        record.setClickFrom(4);
        record.setCompanyId(5);
        record.setEmail("wjf//@Test.com");
        record.setHeadimgurl("headimg");
        record.setIsRecommend((byte)6);
        record.setMobile("1851129553");
        record.setName("wjf");
        record.setNickname("wjfnickname");
        record.setSysUserId((int)(7));
        CandidateCompanyDO candidateCompanyDO = BeanUtils.DBToStruct(CandidateCompanyDO.class, record);
        assertEquals(1, candidateCompanyDO.getId());
        assertEquals(new DateTime(time).toString("yyyy-MM-dd HH:mm:ss"), candidateCompanyDO.getUpdateTime());
        assertEquals(3, candidateCompanyDO.getStatus());
        assertEquals(4, candidateCompanyDO.getClickFrom());
        assertEquals(5, candidateCompanyDO.getCompanyId());
        assertEquals("wjf//@Test.com", candidateCompanyDO.getEmail());
        assertEquals("headimg", candidateCompanyDO.getHeadimgurl());
        assertEquals(true, candidateCompanyDO.isIsRecommend());
        assertEquals("1851129553", candidateCompanyDO.getMobile());
        assertEquals("wjf", candidateCompanyDO.getName());
        assertEquals("wjfnickname", candidateCompanyDO.getNickname());
        assertEquals(7, candidateCompanyDO.getSysUserId());
    }

    //@Test
    public void candidatePositionRecordTest() {
        /*CandidatePositionRecord record = new CandidatePositionRecord();
        record.setIsInterested((byte)2);
        CandidatePositionDO candidatePositionDO = BeanUtils.DBToStruct(CandidatePositionDO.class, record);
        assertEquals(true, candidatePositionDO.isIsInterested());*/

        CandidatePositionDO candidatePositionDO1 = new CandidatePositionDO();
        candidatePositionDO1.setIsInterested(true);

        CandidatePositionRecord record1 = BeanUtils.structToDB(candidatePositionDO1, CandidatePositionRecord.class);
        assertEquals(1, record1.getIsInterested().intValue());
    }
}
