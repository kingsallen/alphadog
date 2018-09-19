package com.moseeker.baseorm.dao.profiledb;


import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IntentionRecord extends ProfileIntentionRecord {

	private static final long serialVersionUID = 8505792517035776734L;
	
	private List<ProfileIntentionCityRecord> cities = new ArrayList<>();
	private List<ProfileIntentionPositionRecord> positions = new ArrayList<>();
	private List<ProfileIntentionIndustryRecord> industries = new ArrayList<>();
	
	public IntentionRecord(){};
	
	public IntentionRecord(ProfileIntentionRecord pir) {
		super(pir.getId(), pir.getProfileId(), pir.getWorktype(), pir.getWorkstate(), pir.getSalaryCode(), pir.getTag(), pir.getConsiderVentureCompanyOpportunities(), pir.getCreateTime(), pir.getUpdateTime());
	}

	public final Map<String, Object> intoMapNew() {
		Map<String, Object> map = super.intoMap();
		if (map == null) {
			map = new HashMap<>();
		}
		if (cities != null && cities.size() > 0) {
			List<Map<String, Object>> cityList = new ArrayList<>(cities.size());
			for (ProfileIntentionCityRecord cityRecord : cities) {
				cityList.add(cityRecord.intoMap());

			}
			map.put("cities", cityList);
		}
		if (positions != null && positions.size() > 0) {
			List<Map<String, Object>> positionList = new ArrayList<>(positions.size());
			for (ProfileIntentionPositionRecord profileIntentionPositionRecord : positions) {
				positionList.add(profileIntentionPositionRecord.intoMap());
			}
			map.put("positions", positionList);
		}
		if (industries != null && industries.size() > 0) {
			List<Map<String, Object>> industryList = new ArrayList<>(industries.size());
			for (ProfileIntentionIndustryRecord profileIntentionIndustryRecord : industries) {
				industryList.add(profileIntentionIndustryRecord.intoMap());
			}
			map.put("industries", industryList);
		}
		return map;
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
