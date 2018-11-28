package com.moseeker.position.service.position.job58.vo;

import java.util.List;

/**
 * @author cjm
 * @date 2018-11-23 17:59
 **/
public class Job58PositionForm {

    private Integer channel;
    private Integer addressId;
    private String addressName;
    private List<String> occupation;
    private List<Integer> features;
    private Integer salaryTop;
    private Integer salaryBottom;
    private Byte salaryDiscuss;
    private Byte freshGraduate;
    private Byte showContact;

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public List<Integer> getFeatures() {
        return features;
    }

    public void setFeatures(List<Integer> features) {
        this.features = features;
    }

    public Integer getSalaryTop() {
        return salaryTop;
    }

    public void setSalaryTop(Integer salaryTop) {
        this.salaryTop = salaryTop;
    }

    public Integer getSalaryBottom() {
        return salaryBottom;
    }

    public void setSalaryBottom(Integer salaryBottom) {
        this.salaryBottom = salaryBottom;
    }

    public Byte getSalaryDiscuss() {
        return salaryDiscuss;
    }

    public void setSalaryDiscuss(Byte salaryDiscuss) {
        this.salaryDiscuss = salaryDiscuss;
    }

    public Byte getFreshGraduate() {
        return freshGraduate;
    }

    public void setFreshGraduate(Byte freshGraduate) {
        this.freshGraduate = freshGraduate;
    }

    public Byte getShowContact() {
        return showContact;
    }

    public void setShowContact(Byte showContact) {
        this.showContact = showContact;
    }

    public List<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<String> occupation) {
        this.occupation = occupation;
    }
}
