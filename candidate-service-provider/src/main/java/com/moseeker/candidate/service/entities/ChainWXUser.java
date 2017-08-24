package com.moseeker.candidate.service.entities;

import java.util.Date;

public class ChainWXUser {

	private int id;
    private int positionId;
	private int wxuserId;
	private Date date;
	private int companyId;
	private int sysUserId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public int getWxuserId() {
		return wxuserId;
	}
	public void setWxuserId(int wxuserId) {
		this.wxuserId = wxuserId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getDepartmentId() {
		return companyId;
	}
	public void setDepartmentId(int companyId) {
		this.companyId = companyId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(int sysUserId) {
		this.sysUserId = sysUserId;
	}
}