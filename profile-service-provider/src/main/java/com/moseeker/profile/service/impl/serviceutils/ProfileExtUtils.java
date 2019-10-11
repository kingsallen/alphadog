package com.moseeker.profile.service.impl.serviceutils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.log.ELKLog;
import com.moseeker.common.log.LogVO;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.Constant.GenderType;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.profile.constants.EmailVerifyState;
import com.moseeker.profile.constants.StatisticsForChannelmportVO;
import com.moseeker.profile.service.impl.vo.FileNameData;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class ProfileExtUtils extends com.moseeker.entity.biz.ProfileUtils {

	/**
	 * 统计添加日志
	 * @param method
	 * @param params
	 * @param statisticsForChannelmportVO
	 */
    public void logForStatistics(String method, String params, StatisticsForChannelmportVO statisticsForChannelmportVO) {
		try {
			LogVO logVO = new LogVO();
			logVO.setAppid(Constant.APPID_ALPHADOG);
			logVO.setEvent("WholeProfileService_"+method);
			logVO.setStatus_code(0);
			logVO.setReq_params(params);
			logVO.setCustoms(statisticsForChannelmportVO);
			ELKLog.ELK_LOG.log(logVO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 将简历解析的结果转成员工内推的简历json格式数据
	 * 在转换过程中，会添加profile_profile信息。profile.source 和profile.origin都是指向内推
	 * @param profileObj 简历
	 * @return json格式的简历数据
	 */
	public static JSONObject convertToReferralProfileJson(ProfileObj profileObj) {
		JSONObject jsonObject = (JSONObject) JSON.toJSON(profileObj);
		JSONObject profileProfile = createReferralProfileData();
		jsonObject.put("profile", profileProfile);
		return jsonObject;
	}

	/**
	 * 生成内推来源的profile_profile数据
	 * @return profile_profile的json格式数据
	 */
	public static JSONObject createReferralProfileData() {
		JSONObject profileProfile = new JSONObject();
		profileProfile.put("source", com.moseeker.profile.constants.ProfileSource.EmployeeReferral.getValue()); //内推
		profileProfile.put("origin", "100000000000000000000000000000");         //内推
		profileProfile.put("uuid", UUID.randomUUID().toString());               //内推
		profileProfile.put("user_id", 0);
		profileProfile.put("disable", 1);
		return profileProfile;
	}

	/**
	 * 生成内推来源的profile_profile数据
	 * @return profile_profile的json格式数据
	 */
	public static void createReferralProfileData(ProfilePojo profilePojo) {
		ProfileProfileRecord profileProfileRecord = new ProfileProfileRecord();
		profileProfileRecord.setSource(com.moseeker.profile.constants.ProfileSource.EmployeeReferral.getValue());
		profileProfileRecord.setOrigin("100000000000000000000000000000");
		profileProfileRecord.setUuid(UUID.randomUUID().toString());
		profileProfileRecord.setUserId(0);
		profileProfileRecord.setDisable((byte)1);
		profilePojo.setProfileRecord(profileProfileRecord);
	}

    /**
     * 生成内推来源的profile_profile数据
     * @return profile_profile的json格式数据
     */
    public static void createOrMergeReferralProfileOtherData(ProfilePojo profilePojo, Map<String, ?> otherMap) {
    	if(otherMap != null && !otherMap.isEmpty()){
			ProfileOtherRecord otherRecord = profilePojo.getOtherRecord();
			JSONObject map = otherRecord != null ? JSONObject.parseObject(otherRecord.getOther()) : new JSONObject() ;
			if(otherRecord != null){
				map.putAll(otherMap);
				profilePojo.getOtherRecord().setOther(JSONObject.toJSONString(map));
			}else{
				otherRecord = new ProfileOtherRecord();
				otherRecord.setProfileId(0);
				profilePojo.setOtherRecord(otherRecord);
			}

			otherMap.forEach((k,v)->{
				if(v != null && !"".equals(v)){
					map.put(k,v);
				}
			});
			otherRecord.setOther(JSON.toJSONString(map));
		}
    }


    /**
	 * 添加附加信息
	 * @param jsonObject 简历数据
	 * @param fileNameData 附件信息
	 * @param description 描述
	 */
	public static void createAttachment(JSONObject jsonObject, FileNameData fileNameData, String description) {
		JSONObject attachment = new JSONObject();
		attachment.put("name", fileNameData.getOriginName());
		attachment.put("path", fileNameData.getFileAbsoluteName());
		attachment.put("description", description);
		if (jsonObject.get("attachments") != null && jsonObject.get("attachments") instanceof JSONArray) {
			JSONArray attachments = jsonObject.getJSONArray("attachments");
			attachments.add(attachment);
		} else {
			JSONArray attachments = new JSONArray();
			attachments.add(attachment);
			jsonObject.put("attachments", attachments);
		}
	}

	/**
	 * 设置用户信息
	 * @param jsonObject 简历数据
	 * @param name 姓名
	 * @param mobile 手机号码
	 */
	public static void createReferralUser(JSONObject jsonObject, String name, String mobile,String email) {
		JSONObject user = new JSONObject();
		user.put("name", name);
		user.put("mobile", mobile);
		user.put("email", email);
		user.put("source", UserSource.EMPLOYEE_REFERRAL.getValue());
		jsonObject.put("user", user);
	}

	/**
	 * 设置用户信息
	 * @param profilePojo 简历数据
	 * @param name 姓名
	 * @param mobile 手机号码
	 * @param email 邮箱
	 */
	public static void createReferralUser(ProfilePojo profilePojo, String name, String mobile, String email) {
		UserUserRecord userUserRecord = new UserUserRecord();
		userUserRecord.setName(name);
		userUserRecord.setMobile(Long.valueOf(mobile));
		if(StringUtils.isNotNullOrEmpty(email)) {
            userUserRecord.setEmail(email);
        }
		userUserRecord.setEmailVerified(EmailVerifyState.UnVerified.getValue());
		profilePojo.setUserRecord(userUserRecord);
	}

	/**
     * 添加profile_basic性别的设置
     * @param profilePojo 简历数据
     * @param genderType 性别
     */
    public static void createProfileBasic(ProfilePojo profilePojo, GenderType genderType) {
        if (profilePojo != null && genderType != null) {
            if (profilePojo.getBasicRecord() != null) {
                if (profilePojo.getBasicRecord().getGender() == null || profilePojo.getBasicRecord().getGender() == 0) {
                    profilePojo.getBasicRecord().setGender((byte) genderType.getValue());
                }
            } else {
                ProfileBasicRecord basicRecord = new ProfileBasicRecord();
                basicRecord.setGender((byte) genderType.getValue());
                profilePojo.setBasicRecord(basicRecord);
            }
        }
    }
    /**
     * 添加profile_basic现居住地的设置
     * @param profilePojo 简历数据
     * @param cityCode 城市code
     * @param cityName 城市name
     */
    public static void createProfileBasicCity(ProfilePojo profilePojo, int cityCode, String cityName) {
        if (profilePojo != null) {
            if (profilePojo.getBasicRecord() != null) {
                profilePojo.getBasicRecord().setCityCode(cityCode);
                profilePojo.getBasicRecord().setCityName(cityName);
            } else {
                ProfileBasicRecord basicRecord = new ProfileBasicRecord();
                basicRecord.setCityCode(cityCode);
                basicRecord.setCityName(cityName);
                profilePojo.setBasicRecord(basicRecord);
            }
        }
    }

	/**
	 * 设置用户信息
	 * @param jsonObject 简历数据
	 * @param name 姓名
	 * @param mobile 手机号码
	 */
	public static void createUserProfile(JSONObject jsonObject, String name, String mobile) {
		JSONObject user = new JSONObject();
		user.put("name", name);
		user.put("mobile", mobile);
		user.put("source", UserSource.TALENT_UPLOAD.getValue());
		jsonObject.put("user", user);
	}

	/**
	 * 将简历解析的结果转成用户简历json格式数据
	 * 在转换过程中，会添加profile_profile信息
	 * @param profileObj 简历
	 * @return json格式的简历数据
	 */
	public static JSONObject convertToUserProfileJson(ProfileObj profileObj) {
		JSONObject jsonObject = (JSONObject) JSON.toJSON(profileObj);
		JSONObject profileProfile = createUserProfileData();
		profileProfile.put("user_id", profileObj.getUser().getUid());
		jsonObject.put("profile", profileProfile);
		return jsonObject;
	}

	/**
	 * 生成手机上创来源的profile_profile数据
	 * @return profile_profile的json格式数据
	 */
	public static JSONObject createUserProfileData() {
		JSONObject profileProfile = new JSONObject();
		profileProfile.put("source", com.moseeker.profile.constants.ProfileSource.MOBILEReferral.getValue()); //手机上传
		profileProfile.put("uuid", UUID.randomUUID().toString());
		profileProfile.put("disable", 1);
		return profileProfile;
	}

}
