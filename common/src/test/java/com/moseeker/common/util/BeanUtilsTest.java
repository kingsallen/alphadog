package com.moseeker.common.util;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.thrift.gen.profile.struct.Profile;

public class BeanUtilsTest {

	@Test
	public void structToDBTest() {
		//ProfileProfileRecord record = new ProfileProfileRecord();
		Profile profile = new Profile();
		profile.setId(1);
		profile.setUuid("uuid");
		profile.setLang(1);
		profile.setCompleteness(1);
		profile.setUser_id(1);
		profile.setCreate_time("2016-04-02");
		profile.setUpdate_time("2016-05-02");
		ProfileProfileRecord record = (ProfileProfileRecord)BeanUtils.structToDB(profile, ProfileProfileRecord.class);
		Assert.assertEquals(1, record.getId().intValue());
		Assert.assertEquals("uuid", record.getUuid());
		Assert.assertEquals(1, record.getLang().intValue());
		Assert.assertEquals(1, record.getCompleteness().intValue());
		Assert.assertEquals(1, record.getUserId().intValue());
		Assert.assertEquals(new DateTime("2016-04-02").getMillis(), record.getCreateTime().getTime());
		Assert.assertEquals(new DateTime("2016-05-02").getMillis(), record.getUpdateTime().getTime());
	}
}
