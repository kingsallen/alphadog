package com.moseeker.entity.pojos;

/**
 * 城市
 * Created by jack on 28/09/2017.
 */
public class City {

    private int amout;
    private String area;
    private String expiryDate;
    private String jobType;

    public int getAmout() {
        return amout;
    }

    public void setAmout(int amout) {
        this.amout = amout;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getJobType() {
        return jobType;
    }

    public int getJobTypeInt() {
        if (jobType != null && jobType.equals("社会招聘")) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}
