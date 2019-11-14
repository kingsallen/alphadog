package com.moseeker.mall.vo;

import com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress;

import java.sql.Timestamp;

/**
 * @Description TODO
 * @Author Rays
 * @DATE 2019-11-05
 **/
public class MallMailAddressVO {

    private static final long serialVersionUID = 1570146774;

    private Integer   id;
    private Integer   userId;
    private String    addressee;
    private String    mobile;
    private Integer   province;
    private Integer   city;
    private Integer   region;
    private String   provinceName;
    private String   cityName;
    private String   regionName;
    private String    address;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "MallMailAddressVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", addressee='" + addressee + '\'' +
                ", mobile='" + mobile + '\'' +
                ", province=" + province +
                ", city=" + city +
                ", region=" + region +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public MallMailAddressVO() {
    }

    public MallMailAddressVO(Integer id, Integer userId, String addressee, String mobile, Integer province, Integer city,
                             Integer region, String provinceName, String cityName, String regionName, String address,
                             Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.userId = userId;
        this.addressee = addressee;
        this.mobile = mobile;
        this.province = province;
        this.city = city;
        this.region = region;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.regionName = regionName;
        this.address = address;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
