package com.moseeker.profile.service.impl.serviceutils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.log.ELKLog;
import com.moseeker.common.log.LogVO;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.profile.constants.StatisticsForChannelmportVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
		profileProfile.put("source", 222);                                      //内推
		profileProfile.put("origin", "100000000000000000000000000000");         //内推
		profileProfile.put("uuid", UUID.randomUUID().toString());               //内推
		profileProfile.put("user_id", 0);
		return profileProfile;
	}
}
