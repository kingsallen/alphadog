package com.moseeker.baseorm.dao.profiledb.entity;


import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorkexpRecord;

import java.util.Map;

public class ProfileWorkexpEntity extends ProfileWorkexpRecord {

	private static final long serialVersionUID = -184582423356499848L;

	private HrCompanyRecord company;
	
	public ProfileWorkexpEntity() {};
	
	public ProfileWorkexpEntity(ProfileWorkexpRecord pfw) {
		super(pfw.getId(), pfw.getProfileId(), pfw.getStart(), pfw.getEnd(), pfw.getEndUntilNow(), pfw.getSalaryCode(), pfw.getIndustryCode(), pfw.getIndustryName(), pfw.getCompanyId(), pfw.getDepartmentName(), pfw.getPositionCode(), pfw.getPositionName(), pfw.getDescription(), pfw.getType(), pfw.getCityCode(), pfw.getCityName(), pfw.getReportTo(), pfw.getUnderlings(), pfw.getReference(), pfw.getResignReason(), pfw.getAchievement(), pfw.getCreateTime(), pfw.getUpdateTime(), pfw.getJob());
	}

	public final Map<String, Object> intoMapNew() {
		Map<String, Object> map = super.intoMap();

		if (company != null) {
			map.put("company", company.intoMap());
		}
		return map;
	}

	public HrCompanyRecord getCompany() {
		return company;
	}

	public void setCompany(HrCompanyRecord company) {
		this.company = company;
	}

}
