package com.moseeker.servicemanager.web.controller.position.bean;

import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;

import java.io.Serializable;

/**
 * Created by zhangdi on 2017/6/23.
 */
public class ThirdPartyPositionVO implements Serializable {

    private int id;
    private int position_id;
    private String third_part_position_id;
    private int is_synchronization;
    private int is_refresh;
    private String sync_time;
    private String refresh_time;
    private String update_time;
    private String address;
    private String occupation;
    private String sync_fail_reason;
    private int use_company_address;
    private int third_party_account_id;
    private int channel;
    private String department;
    private int salary_month;
    private int feedback_period;
    private int salary_discuss;
    private int salary_bottom;
    private int salary_top;
    public String account_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public String getThird_part_position_id() {
        return third_part_position_id;
    }

    public void setThird_part_position_id(String third_part_position_id) {
        this.third_part_position_id = third_part_position_id;
    }

    public int getIs_synchronization() {
        return is_synchronization;
    }

    public void setIs_synchronization(int is_synchronization) {
        this.is_synchronization = is_synchronization;
    }

    public int getIs_refresh() {
        return is_refresh;
    }

    public void setIs_refresh(int is_refresh) {
        this.is_refresh = is_refresh;
    }

    public String getSync_time() {
        return sync_time;
    }

    public void setSync_time(String sync_time) {
        this.sync_time = sync_time;
    }

    public String getRefresh_time() {
        return refresh_time;
    }

    public void setRefresh_time(String refresh_time) {
        this.refresh_time = refresh_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSync_fail_reason() {
        return sync_fail_reason;
    }

    public void setSync_fail_reason(String sync_fail_reason) {
        this.sync_fail_reason = sync_fail_reason;
    }

    public int getUse_company_address() {
        return use_company_address;
    }

    public void setUse_company_address(int use_company_address) {
        this.use_company_address = use_company_address;
    }

    public int getThird_party_account_id() {
        return third_party_account_id;
    }

    public void setThird_party_account_id(int third_party_account_id) {
        this.third_party_account_id = third_party_account_id;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary_month() {
        return salary_month;
    }

    public void setSalary_month(int salary_month) {
        this.salary_month = salary_month;
    }

    public int getFeedback_period() {
        return feedback_period;
    }

    public void setFeedback_period(int feedback_period) {
        this.feedback_period = feedback_period;
    }

    public int getSalary_discuss() {
        return salary_discuss;
    }

    public void setSalary_discuss(int salary_discuss) {
        this.salary_discuss = salary_discuss;
    }

    public int getSalary_bottom() {
        return salary_bottom;
    }

    public void setSalary_bottom(int salary_bottom) {
        this.salary_bottom = salary_bottom;
    }

    public int getSalary_top() {
        return salary_top;
    }

    public void setSalary_top(int salary_top) {
        this.salary_top = salary_top;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public ThirdPartyPositionVO copyDO(HrThirdPartyPositionDO positionDO) {
        id = positionDO.id;
        position_id = positionDO.positionId;
        third_part_position_id = positionDO.thirdPartPositionId;
        is_synchronization = (byte) positionDO.isSynchronization;
        is_refresh = (byte) positionDO.isRefresh;
        sync_time = positionDO.syncTime;
        refresh_time = positionDO.refreshTime;
        update_time = positionDO.updateTime;
        address = positionDO.address;
        occupation = positionDO.occupation;
        sync_fail_reason = positionDO.syncFailReason;
        use_company_address = positionDO.useCompanyAddress;
        third_party_account_id = positionDO.thirdPartyAccountId;
        channel = positionDO.channel;
        department = positionDO.department;
        salary_month = positionDO.salaryMonth;
        feedback_period = positionDO.feedbackPeriod;
        salary_discuss = positionDO.salaryDiscuss;
        salary_bottom = positionDO.salaryBottom;
        salary_top = positionDO.salaryTop;
        account_id = String.valueOf(positionDO.thirdPartyAccountId);
        return this;
    }
}
