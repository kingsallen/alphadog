package com.moseeker.position.service.position.zhilian.pojo;

import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyZhilianPositionAddressDO;

import java.util.List;

public class PositionZhilianForm {
    private int salaryTop;
    private int salaryBottom;
    private int count;
    private List<String> occupation;
    private int channel;
    private int thirdPartyAccountId;
    private int companyId;
    private String companyName;
    private List<ThirdpartyZhilianPositionAddressDO> address;
    private int departmentId;
    private String departmentName;
    private String addressName;     //ATS还是需要传这个字段，然后在职位同步的时候需要

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getSalaryTop() {
        return salaryTop;
    }

    public void setSalaryTop(int salaryTop) {
        this.salaryTop = salaryTop;
    }

    public int getSalaryBottom() {
        return salaryBottom;
    }

    public void setSalaryBottom(int salaryBottom) {
        this.salaryBottom = salaryBottom;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<String> occupation) {
        this.occupation = occupation;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getThirdPartyAccountId() {
        return thirdPartyAccountId;
    }

    public void setThirdPartyAccountId(int thirdPartyAccountId) {
        this.thirdPartyAccountId = thirdPartyAccountId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<ThirdpartyZhilianPositionAddressDO> getAddress() {
        return address;
    }

    public void setAddress(List<ThirdpartyZhilianPositionAddressDO> address) {
        this.address = address;
    }
}
