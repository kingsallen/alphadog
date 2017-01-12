package com.moseeker.profile.service.impl.serviceutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;

public class ProfileUtilsTest {
	
	ProfileUtils profileUtils = new ProfileUtils();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMapToWorkexpRecords() {
		@SuppressWarnings("serial")
		List<Map<String, Object>> workexpArray = new ArrayList<Map<String, Object>>(){{
			add(new HashMap<String, Object>() {{
				Map<String, Object> company = new HashMap<String, Object>();
				company.put("company_name", "test");
				put("company", company);
				put("description", "描述");
				put("job", "职位");
				put("start", "2016-07-01");
			}});
			
			add(new HashMap<String, Object>() {{
				put("description", "描述4");
				put("job", "职位4");
				put("start", "2016-07-04");
			}});
			
			add(new HashMap<String, Object>() {{
				Map<String, Object> company = new HashMap<String, Object>();
				company.put("company_name", "test6");
				put("company", company);
				put("description", "描述6");
				put("start", "2016-07-06");
			}});
			
			add(new HashMap<String, Object>() {{
				Map<String, Object> company = new HashMap<String, Object>();
				company.put("company_name", "test7");
				put("company", company);
				put("description", "描述7");
				put("job", "职位7");
			}});
		}};
		
		List<ProfileWorkexpEntity> records = profileUtils.mapToWorkexpRecords(workexpArray, 1);
		if(records != null && records.size() > 0) {
			assertEquals(1, records.size());
			assertEquals("test", records.get(0).getCompany().getName());
			assertEquals("描述", records.get(0).getDescription());
			assertEquals("职位", records.get(0).getJob());
			assertEquals(new DateTime("2016-07-01").getMillis(), records.get(0).getStart().getTime());
		} else {
			fail("should be some result");
		}
	}

	@Test
	public void testMapToSkillRecords() {
		@SuppressWarnings("serial")
		List<Map<String, Object>> skillArray = new ArrayList<Map<String, Object>>(){{
			add(new HashMap<String, Object>(){{
				put("name", "test");
			}});
			add(new HashMap<String, Object>(){{
				put("id", 1);
			}});
		}};
		List<ProfileSkillRecord> records = profileUtils.mapToSkillRecords(skillArray);
		if(records != null && records.size() > 0) {
			assertEquals(1, records.size());
			assertEquals("test", records.get(0).getName());
		} else {
			fail("should be some result");
		}
	}

	@Test
	public void testMapToProjectExpsRecords() {
		@SuppressWarnings("serial")
		List<Map<String, Object>> projectExpArray = new ArrayList<Map<String, Object>>(){{
			add(new HashMap<String, Object>(){{
				put("name","test");
				put("start_date","2016-11-12");
			}});
			
			add(new HashMap<String, Object>(){{
				put("start_date","2016-11-12");
			}});
			
			add(new HashMap<String, Object>(){{
				put("name","test");
			}});
		}};
		List<ProfileProjectexpRecord> records = profileUtils.mapToProjectExpsRecords(projectExpArray);
		if(records != null && records.size() > 0) {
			assertEquals(1, records.size());
			assertEquals("test", records.get(0).getName());
			assertEquals(new DateTime("2016-11-12").getMillis(), records.get(0).getStart().getTime());
		} else {
			fail("Should be some result");
		}
	}

	@Test
	public void testMapToOtherRecord() {
		Map<String, Object> other = new HashMap<>();
		other.put("other", "test");
		ProfileOtherRecord record = profileUtils.mapToOtherRecord(other);
		if(record != null) {
			assertEquals("{\"other\":\"test\"}", record.getOther());
		} else {
			fail("Should be some result");
		}
	}
	
	@Test
	public void testMapToOtherRecord1() {
		Map<String, Object> other = null;
		ProfileOtherRecord record = profileUtils.mapToOtherRecord(other);
		if(record != null) {
			fail("Should be null");
		}
	}

	@Test
	public void testMapToLanguageRecord() {
		@SuppressWarnings("serial")
		List<Map<String, Object>> languageArray = new ArrayList<Map<String, Object>>(){{
			add(new HashMap<String, Object>(){{
				put("name", "test");
			}});
			add(new HashMap<String, Object>(){{
				put("name", "");
			}});
		}};
		List<ProfileLanguageRecord> records = profileUtils.mapToLanguageRecord(languageArray);
		if(records != null && records.size() > 0) {
			assertEquals(1, records.size());
			assertEquals("test", records.get(0).getName());
		} else {
			fail("Should be some result");
		}
	}

	@SuppressWarnings("serial")
	@Test
	public void testMapToEducationRecords() {
		List<Map<String, Object>> educationArray = new ArrayList<Map<String, Object>>(){
		{
			this.add(new HashMap<String, Object>(){
			{
				this.put("college_code", 1);
				this.put("college_name", "test");
				this.put("degree", 1);
				this.put("start_date", "2016-06-07");
				this.put("description", "test");
			}});
			
			this.add(new HashMap<String, Object>(){
			{
				this.put("college_name", "test");
				this.put("degree", 1);
				this.put("start_date", "2016-06-07");
				this.put("description", "test");
			}});
			
			this.add(new HashMap<String, Object>(){
			{
				this.put("college_code", 1);
				this.put("degree", 1);
				this.put("start_date", "2016-06-07");
				this.put("description", "test");
			}});
			
			this.add(new HashMap<String, Object>(){
			{
				this.put("college_code", 1);
				this.put("start_date", "2016-06-07");
				this.put("description", "test");
			}});
			
			this.add(new HashMap<String, Object>(){
			{
				this.put("college_code", 1);
				this.put("degree", 1);
				this.put("description", "test");
			}});
	}};
		
		List<ProfileEducationRecord> records = profileUtils.mapToEducationRecords(educationArray);
		if(records != null && records.size() > 0) {
			assertEquals(3, records.size());
			ProfileEducationRecord record = records.get(0);
			assertEquals(1, record.getCollegeCode().intValue());
			assertEquals("test", record.getCollegeName());
			assertEquals(1, record.getDegree().intValue());
			assertEquals(new DateTime("2016-06-07").getMillis(), record.getStart().getTime());
		} else {
			fail("Not yet implemented");
		}
	}

	@Test
	public void testMapToCredentialsRecords() {
		List<Map<String, Object>> credentials = new ArrayList<>();
		credentials.add(new HashMap<String, Object>(){
			private static final long serialVersionUID = -342877436729947962L;
		{
			this.put("name", "test");
		}});
		credentials.add(new HashMap<String, Object>(){
			private static final long serialVersionUID = -342877436729947962L;
		{
			this.put("name", "");
		}});
		List<ProfileCredentialsRecord> credentialsArray = profileUtils.mapToCredentialsRecords(credentials);
		if(credentialsArray != null && credentialsArray.size() > 0) {
			assertEquals(1, credentialsArray.size());
			ProfileCredentialsRecord record = credentialsArray.get(0);
			assertEquals("test", record.getName());
		} else {
			fail("Should be some result");
		}
	}
}
