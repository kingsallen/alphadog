/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrHbConfig;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record22;
import org.jooq.Row22;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 红包配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHbConfigRecord extends UpdatableRecordImpl<HrHbConfigRecord> implements Record22<Integer, Byte, Byte, Integer, Timestamp, Timestamp, Integer, Double, Double, Double, Byte, String, String, String, String, String, Byte, Byte, Integer, Timestamp, Timestamp, Integer> {

    private static final long serialVersionUID = -44205012;

    /**
     * Setter for <code>hrdb.hr_hb_config.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.type</code>. 0:员工认证红包，1:推荐评价红包，2:转发被点击红包，3:转发被申请红包
     */
    public void setType(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.type</code>. 0:员工认证红包，1:推荐评价红包，2:转发被点击红包，3:转发被申请红包
     */
    public Byte getType() {
        return (Byte) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.target</code>. 0:员工，1:员工及员工二度，2:粉丝
     */
    public void setTarget(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.target</code>. 0:员工，1:员工及员工二度，2:粉丝
     */
    public Byte getTarget() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.company_id</code>. company.id
     */
    public void setCompanyId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.company_id</code>. company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.start_time</code>. 红包活动开始时间
     */
    public void setStartTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.start_time</code>. 红包活动开始时间
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.end_time</code>. 红包活动结束时间，直到红包用尽：2037-12-31 23:59:59）
     */
    public void setEndTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.end_time</code>. 红包活动结束时间，直到红包用尽：2037-12-31 23:59:59）
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.total_amount</code>. 总预算
     */
    public void setTotalAmount(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.total_amount</code>. 总预算
     */
    public Integer getTotalAmount() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.range_min</code>. 红包最小金额
     */
    public void setRangeMin(Double value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.range_min</code>. 红包最小金额
     */
    public Double getRangeMin() {
        return (Double) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.range_max</code>. 红包最大金额
     */
    public void setRangeMax(Double value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.range_max</code>. 红包最大金额
     */
    public Double getRangeMax() {
        return (Double) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.probability</code>. 中奖几率: 0 < x <= 100
     */
    public void setProbability(Double value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.probability</code>. 中奖几率: 0 < x <= 100
     */
    public Double getProbability() {
        return (Double) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.d_type</code>. 分布类型 0:平均分布，1:指数分布
     */
    public void setDType(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.d_type</code>. 分布类型 0:平均分布，1:指数分布
     */
    public Byte getDType() {
        return (Byte) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.headline</code>. 抽奖页面标题
     */
    public void setHeadline(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.headline</code>. 抽奖页面标题
     */
    public String getHeadline() {
        return (String) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.headline_failure</code>. 抽奖失败页面标题
     */
    public void setHeadlineFailure(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.headline_failure</code>. 抽奖失败页面标题
     */
    public String getHeadlineFailure() {
        return (String) get(12);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.share_title</code>. 转发消息标题
     */
    public void setShareTitle(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.share_title</code>. 转发消息标题
     */
    public String getShareTitle() {
        return (String) get(13);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.share_desc</code>. 转发消息摘要
     */
    public void setShareDesc(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.share_desc</code>. 转发消息摘要
     */
    public String getShareDesc() {
        return (String) get(14);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.share_img</code>. 转发消息背景图地址
     */
    public void setShareImg(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.share_img</code>. 转发消息背景图地址
     */
    public String getShareImg() {
        return (String) get(15);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.status</code>. 0:待审核，1:已审核，2:未开始，3:进行中：4:暂停中，5：已完成， －1: 已删除
     */
    public void setStatus(Byte value) {
        set(16, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.status</code>. 0:待审核，1:已审核，2:未开始，3:进行中：4:暂停中，5：已完成， －1: 已删除
     */
    public Byte getStatus() {
        return (Byte) get(16);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.checked</code>. 0:未审核，1:审核通过，2:审核不通过
     */
    public void setChecked(Byte value) {
        set(17, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.checked</code>. 0:未审核，1:审核通过，2:审核不通过
     */
    public Byte getChecked() {
        return (Byte) get(17);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.estimated_total</code>. 预估红包总数
     */
    public void setEstimatedTotal(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.estimated_total</code>. 预估红包总数
     */
    public Integer getEstimatedTotal() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(19, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(19);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(20);
    }

    /**
     * Setter for <code>hrdb.hr_hb_config.actual_total</code>. 实际红包总数
     */
    public void setActualTotal(Integer value) {
        set(21, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_config.actual_total</code>. 实际红包总数
     */
    public Integer getActualTotal() {
        return (Integer) get(21);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record22 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<Integer, Byte, Byte, Integer, Timestamp, Timestamp, Integer, Double, Double, Double, Byte, String, String, String, String, String, Byte, Byte, Integer, Timestamp, Timestamp, Integer> fieldsRow() {
        return (Row22) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<Integer, Byte, Byte, Integer, Timestamp, Timestamp, Integer, Double, Double, Double, Byte, String, String, String, String, String, Byte, Byte, Integer, Timestamp, Timestamp, Integer> valuesRow() {
        return (Row22) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrHbConfig.HR_HB_CONFIG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field2() {
        return HrHbConfig.HR_HB_CONFIG.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return HrHbConfig.HR_HB_CONFIG.TARGET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrHbConfig.HR_HB_CONFIG.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HrHbConfig.HR_HB_CONFIG.START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrHbConfig.HR_HB_CONFIG.END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrHbConfig.HR_HB_CONFIG.TOTAL_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field8() {
        return HrHbConfig.HR_HB_CONFIG.RANGE_MIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field9() {
        return HrHbConfig.HR_HB_CONFIG.RANGE_MAX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field10() {
        return HrHbConfig.HR_HB_CONFIG.PROBABILITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return HrHbConfig.HR_HB_CONFIG.D_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return HrHbConfig.HR_HB_CONFIG.HEADLINE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return HrHbConfig.HR_HB_CONFIG.HEADLINE_FAILURE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return HrHbConfig.HR_HB_CONFIG.SHARE_TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return HrHbConfig.HR_HB_CONFIG.SHARE_DESC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field16() {
        return HrHbConfig.HR_HB_CONFIG.SHARE_IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field17() {
        return HrHbConfig.HR_HB_CONFIG.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field18() {
        return HrHbConfig.HR_HB_CONFIG.CHECKED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field19() {
        return HrHbConfig.HR_HB_CONFIG.ESTIMATED_TOTAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field20() {
        return HrHbConfig.HR_HB_CONFIG.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field21() {
        return HrHbConfig.HR_HB_CONFIG.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field22() {
        return HrHbConfig.HR_HB_CONFIG.ACTUAL_TOTAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value2() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getTarget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getTotalAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value8() {
        return getRangeMin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value9() {
        return getRangeMax();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value10() {
        return getProbability();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value11() {
        return getDType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getHeadline();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getHeadlineFailure();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getShareTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getShareDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value16() {
        return getShareImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value17() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value18() {
        return getChecked();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value19() {
        return getEstimatedTotal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value20() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value21() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value22() {
        return getActualTotal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value2(Byte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value3(Byte value) {
        setTarget(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value4(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value5(Timestamp value) {
        setStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value6(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value7(Integer value) {
        setTotalAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value8(Double value) {
        setRangeMin(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value9(Double value) {
        setRangeMax(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value10(Double value) {
        setProbability(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value11(Byte value) {
        setDType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value12(String value) {
        setHeadline(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value13(String value) {
        setHeadlineFailure(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value14(String value) {
        setShareTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value15(String value) {
        setShareDesc(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value16(String value) {
        setShareImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value17(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value18(Byte value) {
        setChecked(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value19(Integer value) {
        setEstimatedTotal(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value20(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value21(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord value22(Integer value) {
        setActualTotal(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbConfigRecord values(Integer value1, Byte value2, Byte value3, Integer value4, Timestamp value5, Timestamp value6, Integer value7, Double value8, Double value9, Double value10, Byte value11, String value12, String value13, String value14, String value15, String value16, Byte value17, Byte value18, Integer value19, Timestamp value20, Timestamp value21, Integer value22) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        value20(value20);
        value21(value21);
        value22(value22);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrHbConfigRecord
     */
    public HrHbConfigRecord() {
        super(HrHbConfig.HR_HB_CONFIG);
    }

    /**
     * Create a detached, initialised HrHbConfigRecord
     */
    public HrHbConfigRecord(Integer id, Byte type, Byte target, Integer companyId, Timestamp startTime, Timestamp endTime, Integer totalAmount, Double rangeMin, Double rangeMax, Double probability, Byte dType, String headline, String headlineFailure, String shareTitle, String shareDesc, String shareImg, Byte status, Byte checked, Integer estimatedTotal, Timestamp createTime, Timestamp updateTime, Integer actualTotal) {
        super(HrHbConfig.HR_HB_CONFIG);

        set(0, id);
        set(1, type);
        set(2, target);
        set(3, companyId);
        set(4, startTime);
        set(5, endTime);
        set(6, totalAmount);
        set(7, rangeMin);
        set(8, rangeMax);
        set(9, probability);
        set(10, dType);
        set(11, headline);
        set(12, headlineFailure);
        set(13, shareTitle);
        set(14, shareDesc);
        set(15, shareImg);
        set(16, status);
        set(17, checked);
        set(18, estimatedTotal);
        set(19, createTime);
        set(20, updateTime);
        set(21, actualTotal);
    }
}
