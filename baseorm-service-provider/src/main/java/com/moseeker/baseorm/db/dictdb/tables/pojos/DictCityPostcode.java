/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictCityPostcode implements Serializable {

    private static final long serialVersionUID = -486562872;

    private Integer id;
    private String  postcode;
    private String  province;
    private String  city;
    private String  district;
    private String  code;

    public DictCityPostcode() {}

    public DictCityPostcode(DictCityPostcode value) {
        this.id = value.id;
        this.postcode = value.postcode;
        this.province = value.province;
        this.city = value.city;
        this.district = value.district;
        this.code = value.code;
    }

    public DictCityPostcode(
        Integer id,
        String  postcode,
        String  province,
        String  city,
        String  district,
        String  code
    ) {
        this.id = id;
        this.postcode = postcode;
        this.province = province;
        this.city = city;
        this.district = district;
        this.code = code;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DictCityPostcode (");

        sb.append(id);
        sb.append(", ").append(postcode);
        sb.append(", ").append(province);
        sb.append(", ").append(city);
        sb.append(", ").append(district);
        sb.append(", ").append(code);

        sb.append(")");
        return sb.toString();
    }
}
