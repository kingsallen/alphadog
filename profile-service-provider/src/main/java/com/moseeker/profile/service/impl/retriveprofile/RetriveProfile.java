package com.moseeker.profile.service.impl.retriveprofile;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.serviceutils.ProfilePojo;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.moseeker.common.exception.Category.PROGRAM_PARAM_NOTEXIST;
import static com.moseeker.common.exception.Category.VALIDATE_FAILED;

/**
 * 简历回收服务
 * Created by jack on 10/07/2017.
 */
@Service
public class RetriveProfile {

    @Autowired
    JobPositionDao positionDao;

    @Autowired
    protected Map<String, RetrievalFlow> flowMap;

    /**
     * 简历回收
     * @param parameter 参数
     * @return 执行成功与否
     * @throws CommonException 异常
     */
    public boolean retrieve(String parameter) throws CommonException {
        RetriveParam param = parseParam(parameter);
        RetrievalFlow retrievalFlow = flowMap.get(param.getChannelType().toString().toLowerCase()+"_retrieval_flow");
        return retrievalFlow.retrieveProfile(param);
    }

    /**
     * 解析参数
     * @param parameter 参数
     * @return 参数结构体
     * @throws BIZException 业务异常
     */
    protected RetriveParam parseParam(String parameter) throws CommonException {
        RetriveParam param = new RetriveParam();

        Map<String, Object> paramMap = JSON.parseObject(parameter);
        int positionId = 0;
        int channel = 0;
        String jobResumeOther = null;
        Map<String, Object> profileMap = null;
        if (paramMap.get("positionId") != null) {
            positionId = BeanUtils.converToInteger(paramMap.get("positionId"));
        }
        if (paramMap.get("channel") != null) {
            channel = BeanUtils.converToInteger(paramMap.get("channel"));
        }
        if (paramMap.get("jobResumeOther") != null) {
            jobResumeOther = JSON.toJSONString(paramMap.get("jobResumeOther"));
            param.setJobResume(jobResumeOther);
        }
        if (paramMap.get("profile") != null) {
            profileMap = (Map<String, Object>) paramMap.get("profile");
        }

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("简历信息", profileMap, null, null);
        validateUtil.addIntTypeValidate("职位编号", positionId, null, null, 1, null);
        validateUtil.addIntTypeValidate("渠道", channel, null, null, 1, 7);
        String checkoutParamResult = validateUtil.validate();
        if (!StringUtils.isNullOrEmpty(checkoutParamResult)) {
            CommonException exception = ExceptionFactory.buildException(VALIDATE_FAILED);
            exception.setMessage(checkoutParamResult);
            throw exception;
        }

        ChannelType channelType = ChannelType.instaceFromInteger(channel);
        if (channelType == null) {
            throw ExceptionFactory.buildException(Category.PROFILE_POSITION_NOTEXIST);
        }
        param.setChannelType(channelType);

        ProfilePojo profilePojo = ProfilePojo.parseProfile(profileMap);
        if (profilePojo == null || profilePojo.getProfileRecord() == null || profilePojo.getUserRecord() == null) {
            throw ExceptionFactory.buildException(PROGRAM_PARAM_NOTEXIST);
        }
        profilePojo.getProfileRecord().setOrigin(channelType.getOrigin(profilePojo.getProfileRecord().getOrigin()));

        param.setProfilePojo(profilePojo);
        profilePojo.getUserRecord().setUsername(String.valueOf(profilePojo.getUserRecord().getMobile()));
        profilePojo.getUserRecord().setPassword("");
        param.setUserUserRecord(profilePojo.getUserRecord());

        JobPositionRecord positionRecord = positionDao.getPositionById(positionId);
        if (positionRecord == null) {
            throw ExceptionFactory.buildException(Category.PROFILE_POSITION_NOTEXIST);
        }
        param.setPositionRecord(positionRecord);

        return param;
    }
}
