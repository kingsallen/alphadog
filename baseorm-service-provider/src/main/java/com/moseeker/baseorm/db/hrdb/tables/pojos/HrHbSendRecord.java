/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 红包发送记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHbSendRecord implements Serializable {

    private static final long serialVersionUID = -170384711;

    private Integer   id;
    private String    returnCode;
    private String    returnMsg;
    private String    sign;
    private String    resultCode;
    private String    errCode;
    private String    errCodeDes;
    private String    mchBillno;
    private String    mchId;
    private String    wxappid;
    private String    reOpenid;
    private Integer   totalAmount;
    private String    sendTime;
    private String    sendListid;
    private Timestamp createTime;
    private Integer   hbItemId;

    public HrHbSendRecord() {}

    public HrHbSendRecord(HrHbSendRecord value) {
        this.id = value.id;
        this.returnCode = value.returnCode;
        this.returnMsg = value.returnMsg;
        this.sign = value.sign;
        this.resultCode = value.resultCode;
        this.errCode = value.errCode;
        this.errCodeDes = value.errCodeDes;
        this.mchBillno = value.mchBillno;
        this.mchId = value.mchId;
        this.wxappid = value.wxappid;
        this.reOpenid = value.reOpenid;
        this.totalAmount = value.totalAmount;
        this.sendTime = value.sendTime;
        this.sendListid = value.sendListid;
        this.createTime = value.createTime;
        this.hbItemId = value.hbItemId;
    }

    public HrHbSendRecord(
        Integer   id,
        String    returnCode,
        String    returnMsg,
        String    sign,
        String    resultCode,
        String    errCode,
        String    errCodeDes,
        String    mchBillno,
        String    mchId,
        String    wxappid,
        String    reOpenid,
        Integer   totalAmount,
        String    sendTime,
        String    sendListid,
        Timestamp createTime,
        Integer   hbItemId
    ) {
        this.id = id;
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.sign = sign;
        this.resultCode = resultCode;
        this.errCode = errCode;
        this.errCodeDes = errCodeDes;
        this.mchBillno = mchBillno;
        this.mchId = mchId;
        this.wxappid = wxappid;
        this.reOpenid = reOpenid;
        this.totalAmount = totalAmount;
        this.sendTime = sendTime;
        this.sendListid = sendListid;
        this.createTime = createTime;
        this.hbItemId = hbItemId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReturnCode() {
        return this.returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return this.returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return this.errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getMchBillno() {
        return this.mchBillno;
    }

    public void setMchBillno(String mchBillno) {
        this.mchBillno = mchBillno;
    }

    public String getMchId() {
        return this.mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getWxappid() {
        return this.wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getReOpenid() {
        return this.reOpenid;
    }

    public void setReOpenid(String reOpenid) {
        this.reOpenid = reOpenid;
    }

    public Integer getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendListid() {
        return this.sendListid;
    }

    public void setSendListid(String sendListid) {
        this.sendListid = sendListid;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getHbItemId() {
        return this.hbItemId;
    }

    public void setHbItemId(Integer hbItemId) {
        this.hbItemId = hbItemId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrHbSendRecord (");

        sb.append(id);
        sb.append(", ").append(returnCode);
        sb.append(", ").append(returnMsg);
        sb.append(", ").append(sign);
        sb.append(", ").append(resultCode);
        sb.append(", ").append(errCode);
        sb.append(", ").append(errCodeDes);
        sb.append(", ").append(mchBillno);
        sb.append(", ").append(mchId);
        sb.append(", ").append(wxappid);
        sb.append(", ").append(reOpenid);
        sb.append(", ").append(totalAmount);
        sb.append(", ").append(sendTime);
        sb.append(", ").append(sendListid);
        sb.append(", ").append(createTime);
        sb.append(", ").append(hbItemId);

        sb.append(")");
        return sb.toString();
    }
}