package com.moseeker.company.bean.email;

import com.moseeker.company.bean.email.PositionInfo;

/**
 * Created by zztaiwll on 18/4/25.
 */
public class TalentEmailInviteToDelivyInfo {
    private String companyAbbr;
    private String companyLogo;
    private String employeeName;
    private String customText;
    private PositionInfo positions;
    private String positionNum;
    private String seeMorePosition;
    private String weixinQrcode;
    private String officialAccountName;
    private String rcpt;

    public String getCompanyAbbr() {
        return companyAbbr;
    }

    public void setCompanyAbbr(String companyAbbr) {
        this.companyAbbr = companyAbbr;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCustomText() {
        return customText;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }

    public PositionInfo getPositions() {
        return positions;
    }

    public void setPositions(PositionInfo positions) {
        this.positions = positions;
    }

    public String getPositionNum() {
        return positionNum;
    }

    public void setPositionNum(String positionNum) {
        this.positionNum = positionNum;
    }

    public String getSeeMorePosition() {
        return seeMorePosition;
    }

    public void setSeeMorePosition(String seeMorePosition) {
        this.seeMorePosition = seeMorePosition;
    }

    public String getWeixinQrcode() {
        return weixinQrcode;
    }

    public void setWeixinQrcode(String weixinQrcode) {
        this.weixinQrcode = weixinQrcode;
    }

    public String getOfficialAccountName() {
        return officialAccountName;
    }

    public void setOfficialAccountName(String officialAccountName) {
        this.officialAccountName = officialAccountName;
    }

    public String getRcpt() {
        return rcpt;
    }

    public void setRcpt(String rcpt) {
        this.rcpt = rcpt;
    }
}

