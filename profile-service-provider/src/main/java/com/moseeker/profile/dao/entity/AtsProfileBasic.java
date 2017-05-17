package com.moseeker.profile.dao.entity;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class AtsProfileBasic implements Serializable{

  private long profile_id;
  private String name;
  private long gender;
  private long nationality_code;
  private String nationality_name;
  private long city_code;
  private String city_name;
  private java.sql.Date birth;
  private String weixin;
  private String qq;
  private String motto;
  private String self_introduction;
  private java.sql.Timestamp create_time;
  private java.sql.Timestamp update_time;
  private String country_code;

  public long getProfile_id() {
    return profile_id;
  }

  public void setProfile_id(long profile_id) {
    this.profile_id = profile_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getGender() {
    return gender;
  }

  public void setGender(long gender) {
    this.gender = gender;
  }

  public long getNationality_code() {
    return nationality_code;
  }

  public void setNationality_code(long nationality_code) {
    this.nationality_code = nationality_code;
  }

  public String getNationality_name() {
    return nationality_name;
  }

  public void setNationality_name(String nationality_name) {
    this.nationality_name = nationality_name;
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

  public Date getBirth() {
    return birth;
  }

  public void setBirth(Date birth) {
    this.birth = birth;
  }

  public String getWeixin() {
    return weixin;
  }

  public void setWeixin(String weixin) {
    this.weixin = weixin;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
  }

  public String getSelf_introduction() {
    return self_introduction;
  }

  public void setSelf_introduction(String self_introduction) {
    this.self_introduction = self_introduction;
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

  public String getCountry_code() {
    return country_code;
  }

  public void setCountry_code(String country_code) {
    this.country_code = country_code;
  }
}
