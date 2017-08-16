package com.moseeker.apps.bean;

public class RewardsToBeAddBean {
	private int account_id;
	private int employee_id;
	private String reason;
	private int award;
	private int application_id;
	private int company_id;
	private int operate_tpl_id;
	private long recommender_id;
	private int points_conf_id;
    private int position_id;
    private int applier_id ;

    public int getApplier_id() {
        return applier_id;
    }

    public void setApplier_id(int applier_id) {
        this.applier_id = applier_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public int getPoints_conf_id() {
        return points_conf_id;
    }

    public void setPoints_conf_id(int points_conf_id) {
        this.points_conf_id = points_conf_id;
    }

    public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getAward() {
		return award;
	}
	public void setAward(int award) {
		this.award = award;
	}
	public int getApplication_id() {
		return application_id;
	}
	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public int getOperate_tpl_id() {
		return operate_tpl_id;
	}
	public void setOperate_tpl_id(int operate_tpl_id) {
		this.operate_tpl_id = operate_tpl_id;
	}
	public long getRecommender_id() {
		return recommender_id;
	}
	public void setRecommender_id(long recommender_id) {
		this.recommender_id = recommender_id;
	}
	
}
