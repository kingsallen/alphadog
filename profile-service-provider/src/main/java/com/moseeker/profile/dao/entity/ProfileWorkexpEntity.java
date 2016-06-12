package com.moseeker.profile.dao.entity;

import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;

public class ProfileWorkexpEntity extends ProfileWorkexpRecord {

	private static final long serialVersionUID = -184582423356499848L;

	private String companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
