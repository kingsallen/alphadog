package com.moseeker.profile.dao.entity;

import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;

public class ProfileWorkexpEntity extends ProfileWorkexpRecord {

	private static final long serialVersionUID = -184582423356499848L;

	private HrCompanyRecord company;

	public HrCompanyRecord getCompany() {
		return company;
	}

	public void setCompany(HrCompanyRecord company) {
		this.company = company;
	}
}
