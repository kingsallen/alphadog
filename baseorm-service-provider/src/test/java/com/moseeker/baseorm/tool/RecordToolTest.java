package com.moseeker.baseorm.tool;

import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCredentialsRecord;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Created by jack on 02/01/2018.
 */
public class RecordToolTest {
    @Test
    public void recordToRecord() throws Exception {

        ProfileCredentialsRecord profileCredentialsRecord = new ProfileCredentialsRecord();
        profileCredentialsRecord.setId(1);
        profileCredentialsRecord.setName("test");

        ProfileCredentialsRecord profileCredentialsRecord1 = new ProfileCredentialsRecord();
        profileCredentialsRecord1.setName("test");
        profileCredentialsRecord1.setCode("1111");
        profileCredentialsRecord1.setOrganization("test");
        profileCredentialsRecord1.set(profileCredentialsRecord1.field1(), 1);
        long time = System.currentTimeMillis();
        profileCredentialsRecord1.setCreateTime(new Timestamp(time));
        RecordTool.recordToRecord(profileCredentialsRecord, profileCredentialsRecord1);

        assertEquals(profileCredentialsRecord.getCode(), "1111");
        assertEquals(profileCredentialsRecord.getOrganization(), "test");
        assertEquals(profileCredentialsRecord.getId().intValue(), 1);
        assertEquals(profileCredentialsRecord.getName(), "test");
        assertEquals(profileCredentialsRecord.getCreateTime().getTime(), time);
    }

}