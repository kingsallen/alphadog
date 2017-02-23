package com.moseeker.baseorm.db.candidatedb.tables.records;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.dao.struct.CandidateCompanyDO;
import org.joda.time.DateTime;
import org.jooq.types.UInteger;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

/**
 * Created by jack on 22/02/2017.
 */
public class CandidateCompanyTest {

    @Test
    public void convertToDOTest() {
        long time = System.currentTimeMillis();
        CandidateCompanyRecord record = new CandidateCompanyRecord();
        record.setId(1);
        record.setWxuserId(2);
        record.setUpdateTime(new Timestamp(time));
        record.setStatus(3);
        record.setClickFrom(4);
        record.setCompanyId(5);
        record.setEmail("wjf@test.com");
        record.setHeadimgurl("headimg");
        record.setIsRecommend((byte)6);
        record.setMobile("1851129553");
        record.setName("wjf");
        record.setNickname("wjfnickname");
        record.setSysUserId(UInteger.valueOf(7));
        CandidateCompanyDO candidateCompanyDO = BeanUtils.DBToStruct(CandidateCompanyDO.class, record);
        assertEquals(1, candidateCompanyDO.getId());
        assertEquals(new DateTime(time).toString("yyyy-MM-dd HH:mm:ss"), candidateCompanyDO.getUpdateTime());
        assertEquals(3, candidateCompanyDO.getStatus());
        assertEquals(4, candidateCompanyDO.getClickFrom());
        assertEquals(5, candidateCompanyDO.getCompanyId());
        assertEquals("wjf@test.com", candidateCompanyDO.getEmail());
        assertEquals("headimg", candidateCompanyDO.getHeadimgurl());
        assertEquals(true, candidateCompanyDO.isIsRecommend());
        assertEquals("1851129553", candidateCompanyDO.getMobile());
        assertEquals("wjf", candidateCompanyDO.getName());
        assertEquals("wjfnickname", candidateCompanyDO.getNickname());
        assertEquals(7, candidateCompanyDO.getSysUserId());
    }
}
