package com.moseeker.profile.service.impl.serviceutils;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.log.ELKLog;
import com.moseeker.common.log.LogVO;
import com.moseeker.profile.constants.StatisticsForChannelmportVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProfileUtils extends com.moseeker.entity.biz.ProfileUtils {

	/**
	 * 为
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
}
