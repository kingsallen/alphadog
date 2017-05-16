package com.moseeker.profile.dao.entity;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class AtsProfileWorkexp implements Serializable {

  private long id;
  private long profile_d;
  private java.sql.Date start;
  private java.sql.Date end;
  private long end_until_now;
  private long salary_code;
  private long industry_code;
  private String industry_name;
  private long company_d;
  private String company_name;
  private String department_name;
  private long position_code;
  private String position_name;
  private String description;
  private long type;
  private long city_code;
  private String city_name;
  private String report_to;
  private long underlings;
  private String reference;
  private String resign_reason;
  private String achievement;
  private java.sql.Timestamp create_time;
  private java.sql.Timestamp update_time;
  private String job;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getProfile_d() {
    return profile_d;
  }

  public void setProfile_d(long profile_d) {
    this.profile_d = profile_d;
  }

  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public long getEnd_until_now() {
    return end_until_now;
  }

  public void setEnd_until_now(long end_until_now) {
    this.end_until_now = end_until_now;
  }

  public long getSalary_code() {
    return salary_code;
  }

  public void setSalary_code(long salary_code) {
    this.salary_code = salary_code;
  }

  public long getIndustry_code() {
    return industry_code;
  }

  public void setIndustry_code(long industry_code) {
    this.industry_code = industry_code;
  }

  public String getIndustry_name() {
    return industry_name;
  }

  public void setIndustry_name(String industry_name) {
    this.industry_name = industry_name;
  }

  public long getCompany_d() {
    return company_d;
  }

  public void setCompany_d(long company_d) {
    this.company_d = company_d;
  }

  public String getCompany_name() {
    return company_name;
  }

  public void setCompany_name(String company_name) {
    this.company_name = company_name;
  }

  public String getDepartment_name() {
    return department_name;
  }

  public void setDepartment_name(String department_name) {
    this.department_name = department_name;
  }

  public long getPosition_code() {
    return position_code;
  }

  public void setPosition_code(long position_code) {
    this.position_code = position_code;
  }

  public String getPosition_name() {
    return position_name;
  }

  public void setPosition_name(String position_name) {
    this.position_name = position_name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getType() {
    return type;
  }

  public void setType(long type) {
    this.type = type;
  }

  public long getCity_code() {
    return city_code;
  }

  public void setCity_code(long city_code) {
    this.city_code = city_code;
  }

  public String getCity_name() {
    return city_name;
  }

  public void setCity_name(String city_name) {
    this.city_name = city_name;
  }

  public String getReport_to() {
    return report_to;
  }

  public void setReport_to(String report_to) {
    this.report_to = report_to;
  }

  public long getUnderlings() {
    return underlings;
  }

  public void setUnderlings(long underlings) {
    this.underlings = underlings;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getResign_reason() {
    return resign_reason;
  }

  public void setResign_reason(String resign_reason) {
    this.resign_reason = resign_reason;
  }

  public String getAchievement() {
    return achievement;
  }

  public void setAchievement(String achievement) {
    this.achievement = achievement;
  }

  public Timestamp getCreate_time() {
    return create_time;
  }

  public void setCreate_time(Timestamp create_time) {
    this.create_time = create_time;
  }

  public Timestamp getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(Timestamp update_time) {
    this.update_time = update_time;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }
}
