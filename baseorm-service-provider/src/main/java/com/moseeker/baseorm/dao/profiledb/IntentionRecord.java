package com.moseeker.baseorm.dao.profiledb;


import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionRecord;

import java.util.ArrayList;
import java.util.List;

public class IntentionRecord extends ProfileIntentionRecord {

	private static final long serialVersionUID = 8505792517035776734L;
	
	private List<ProfileIntentionCityRecord> cities = new ArrayList<>();
	private List<ProfileIntentionPositionRecord> positions = new ArrayList<>();
	private List<ProfileIntentionIndustryRecord> industries = new ArrayList<>();
	
	public IntentionRecord(){};
	
	public IntentionRecord(ProfileIntentionRecord pir) {
		super(pir.getId(), pir.getProfileId(), pir.getWorktype(), pir.getWorkstate(), pir.getSalaryCode(), pir.getTag(), pir.getConsiderVentureCompanyOpportunities(), pir.getCreateTime(), pir.getUpdateTime());
	}
	
	
	public List<ProfileIntentionCityRecord> getCities() {
		return cities;
	}
	public void setCities(List<ProfileIntentionCityRecord> cities) {
		this.cities = cities;
	}
	public List<ProfileIntentionPositionRecord> getPositions() {
		return positions;
	}
	public void setPositions(List<ProfileIntentionPositionRecord> positions) {
		this.positions = positions;
	}
	public List<ProfileIntentionIndustryRecord> getIndustries() {
		return industries;
	}
	public void setIndustries(List<ProfileIntentionIndustryRecord> industries) {
		this.industries = industries;
	}
}
