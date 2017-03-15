/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.candidatedb.tables.records;


import com.moseeker.db.candidatedb.tables.CandidateRecomRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record20;
import org.jooq.Row20;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * 候选人推荐记录表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidateRecomRecordRecord extends UpdatableRecordImpl<CandidateRecomRecordRecord> implements Record20<UInteger, Integer, Integer, Integer, Timestamp, Integer, Integer, Integer, String, String, String, String, Timestamp, Integer, Timestamp, Timestamp, String, UInteger, UInteger, UInteger> {

	private static final long serialVersionUID = -1464806102;

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.id</code>. 主key
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.id</code>. 主key
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.position_id</code>. 推荐的职位ID, hr_position.id
	 */
	public void setPositionId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.position_id</code>. 推荐的职位ID, hr_position.id
	 */
	public Integer getPositionId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.app_id</code>. job_application.id, 被推荐者申请ID
	 */
	public void setAppId(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.app_id</code>. job_application.id, 被推荐者申请ID
	 */
	public Integer getAppId() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.presentee_id</code>. userdb.user_wx_user.id, 被推荐者的微信ID。现在已经废弃，改由被推荐者的C端账号编号表示，请参考presentee_user_id
	 */
	public void setPresenteeId(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.presentee_id</code>. userdb.user_wx_user.id, 被推荐者的微信ID。现在已经废弃，改由被推荐者的C端账号编号表示，请参考presentee_user_id
	 */
	public Integer getPresenteeId() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.click_time</code>. 职位点击时间
	 */
	public void setClickTime(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.click_time</code>. 职位点击时间
	 */
	public Timestamp getClickTime() {
		return (Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.depth</code>. 第几层关系
	 */
	public void setDepth(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.depth</code>. 第几层关系
	 */
	public Integer getDepth() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.recom_id_2</code>. userdb.user_wx_usesr.id, 第2度人脉推荐人微信ID，用来标记谁的朋友。已经废弃，第2度人脉微信ID由第2度人脉C端账号编号替换，请参考repost_user_id
	 */
	public void setRecomId_2(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.recom_id_2</code>. userdb.user_wx_usesr.id, 第2度人脉推荐人微信ID，用来标记谁的朋友。已经废弃，第2度人脉微信ID由第2度人脉C端账号编号替换，请参考repost_user_id
	 */
	public Integer getRecomId_2() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.recom_id</code>. userdb.user_wx_user.id, 推荐者的微信ID。已经废弃，推荐者的微信ID被推荐者的C端账号编号替换，请参考post_user_id
	 */
	public void setRecomId(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.recom_id</code>. userdb.user_wx_user.id, 推荐者的微信ID。已经废弃，推荐者的微信ID被推荐者的C端账号编号替换，请参考post_user_id
	 */
	public Integer getRecomId() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.realname</code>. 被推荐人真实姓名
	 */
	public void setRealname(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.realname</code>. 被推荐人真实姓名
	 */
	public String getRealname() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.company</code>. 被推荐者目前就职公司
	 */
	public void setCompany(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.company</code>. 被推荐者目前就职公司
	 */
	public String getCompany() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.position</code>. 被推荐者的职位
	 */
	public void setPosition(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.position</code>. 被推荐者的职位
	 */
	public String getPosition() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.recom_reason</code>. 推荐理由, 逗号分隔
	 */
	public void setRecomReason(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.recom_reason</code>. 推荐理由, 逗号分隔
	 */
	public String getRecomReason() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.recom_time</code>. 推荐时间
	 */
	public void setRecomTime(Timestamp value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.recom_time</code>. 推荐时间
	 */
	public Timestamp getRecomTime() {
		return (Timestamp) getValue(12);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.is_recom</code>. 推荐状态，0：推荐过，1：未推荐,2:忽略--推荐被动求职者时，可以选中多个求职者挨个填写求职者信息。忽略是指跳过当前求职者，到下一个求职者。3： 选中--推荐被动求职者时，可以选中多个被动求职者。
	 */
	public void setIsRecom(Integer value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.is_recom</code>. 推荐状态，0：推荐过，1：未推荐,2:忽略--推荐被动求职者时，可以选中多个求职者挨个填写求职者信息。忽略是指跳过当前求职者，到下一个求职者。3： 选中--推荐被动求职者时，可以选中多个被动求职者。
	 */
	public Integer getIsRecom() {
		return (Integer) getValue(13);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(14);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.update_time</code>. 更新时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.update_time</code>. 更新时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(15);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.mobile</code>. 被推荐者的手机号
	 */
	public void setMobile(String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.mobile</code>. 被推荐者的手机号
	 */
	public String getMobile() {
		return (String) getValue(16);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.presentee_user_id</code>. userdb.user_user.id 被推荐者的C端账号编号
	 */
	public void setPresenteeUserId(UInteger value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.presentee_user_id</code>. userdb.user_user.id 被推荐者的C端账号编号
	 */
	public UInteger getPresenteeUserId() {
		return (UInteger) getValue(17);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.repost_user_id</code>. userdb.user_user.id 第2度人脉推荐人C 端账号编号，用来标记谁的朋友
	 */
	public void setRepostUserId(UInteger value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.repost_user_id</code>. userdb.user_user.id 第2度人脉推荐人C 端账号编号，用来标记谁的朋友
	 */
	public UInteger getRepostUserId() {
		return (UInteger) getValue(18);
	}

	/**
	 * Setter for <code>candidatedb.candidate_recom_record.post_user_id</code>. userdb.user_user.id 推荐者的C端账号编号
	 */
	public void setPostUserId(UInteger value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>candidatedb.candidate_recom_record.post_user_id</code>. userdb.user_user.id 推荐者的C端账号编号
	 */
	public UInteger getPostUserId() {
		return (UInteger) getValue(19);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<UInteger> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record20 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row20<UInteger, Integer, Integer, Integer, Timestamp, Integer, Integer, Integer, String, String, String, String, Timestamp, Integer, Timestamp, Timestamp, String, UInteger, UInteger, UInteger> fieldsRow() {
		return (Row20) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row20<UInteger, Integer, Integer, Integer, Timestamp, Integer, Integer, Integer, String, String, String, String, Timestamp, Integer, Timestamp, Timestamp, String, UInteger, UInteger, UInteger> valuesRow() {
		return (Row20) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.DEPTH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_ID_2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REALNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.COMPANY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field12() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_REASON;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field13() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field14() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field15() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field16() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field17() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.MOBILE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field18() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field19() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field20() {
		return CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getPositionId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getAppId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getPresenteeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value5() {
		return getClickTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value6() {
		return getDepth();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getRecomId_2();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getRecomId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getRealname();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getCompany();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value12() {
		return getRecomReason();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value13() {
		return getRecomTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value14() {
		return getIsRecom();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value15() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value16() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value17() {
		return getMobile();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value18() {
		return getPresenteeUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value19() {
		return getRepostUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value20() {
		return getPostUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value1(UInteger value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value2(Integer value) {
		setPositionId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value3(Integer value) {
		setAppId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value4(Integer value) {
		setPresenteeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value5(Timestamp value) {
		setClickTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value6(Integer value) {
		setDepth(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value7(Integer value) {
		setRecomId_2(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value8(Integer value) {
		setRecomId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value9(String value) {
		setRealname(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value10(String value) {
		setCompany(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value11(String value) {
		setPosition(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value12(String value) {
		setRecomReason(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value13(Timestamp value) {
		setRecomTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value14(Integer value) {
		setIsRecom(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value15(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value16(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value17(String value) {
		setMobile(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value18(UInteger value) {
		setPresenteeUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value19(UInteger value) {
		setRepostUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord value20(UInteger value) {
		setPostUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateRecomRecordRecord values(UInteger value1, Integer value2, Integer value3, Integer value4, Timestamp value5, Integer value6, Integer value7, Integer value8, String value9, String value10, String value11, String value12, Timestamp value13, Integer value14, Timestamp value15, Timestamp value16, String value17, UInteger value18, UInteger value19, UInteger value20) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached CandidateRecomRecordRecord
	 */
	public CandidateRecomRecordRecord() {
		super(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
	}

	/**
	 * Create a detached, initialised CandidateRecomRecordRecord
	 */
	public CandidateRecomRecordRecord(UInteger id, Integer positionId, Integer appId, Integer presenteeId, Timestamp clickTime, Integer depth, Integer recomId_2, Integer recomId, String realname, String company, String position, String recomReason, Timestamp recomTime, Integer isRecom, Timestamp createTime, Timestamp updateTime, String mobile, UInteger presenteeUserId, UInteger repostUserId, UInteger postUserId) {
		super(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);

		setValue(0, id);
		setValue(1, positionId);
		setValue(2, appId);
		setValue(3, presenteeId);
		setValue(4, clickTime);
		setValue(5, depth);
		setValue(6, recomId_2);
		setValue(7, recomId);
		setValue(8, realname);
		setValue(9, company);
		setValue(10, position);
		setValue(11, recomReason);
		setValue(12, recomTime);
		setValue(13, isRecom);
		setValue(14, createTime);
		setValue(15, updateTime);
		setValue(16, mobile);
		setValue(17, presenteeUserId);
		setValue(18, repostUserId);
		setValue(19, postUserId);
	}
}
