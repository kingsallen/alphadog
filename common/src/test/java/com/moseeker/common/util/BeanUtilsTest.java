package com.moseeker.common.util;

import org.joda.time.DateTime;
import org.junit.Test;

import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.thrift.gen.profile.struct.Profile;

public class BeanUtilsTest {
	
	public static void main(String[] args) {
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
		System.out.println("id:"+record.getId().intValue());
		System.out.println("uuid:"+record.getUuid());
		System.out.println("lang:"+record.getLang());
		System.out.println("completeness:"+record.getCompleteness());
		System.out.println("user_id:"+record.getUserId());
		System.out.println("create_time:"+(new DateTime(record.getCreateTime().getTime())).toString("yyyy-MM-dd"));
		System.out.println("update_time:"+(new DateTime(record.getUpdateTime().getTime())).toString("yyyy-MM-dd"));
	}

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
		System.out.println("id:"+record.getId().intValue());
		System.out.println("uuid:"+record.getUuid());
		System.out.println("lang:"+record.getLang());
		System.out.println("completeness:"+record.getCompleteness());
		System.out.println("user_id:"+record.getUserId());
		System.out.println("create_time:"+(new DateTime(record.getCreateTime().getTime())).toString("yyyy-MM-dd"));
		System.out.println("update_time:"+(new DateTime(record.getUpdateTime().getTime())).toString("yyyy-MM-dd"));
	}
}
