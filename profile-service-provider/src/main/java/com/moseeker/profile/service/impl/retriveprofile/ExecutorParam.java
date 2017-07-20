package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.profile.exception.ExceptionFactory;

import java.util.Map;

import static com.moseeker.common.exception.Category.VALIDATE_FAILED;

/**
 * 执行者参数
 * Created by jack on 19/07/2017.
 */
public class ExecutorParam {

    private Map<String, Object> originParam;    //初始参数
    private int positionId;                     //职位编号
    private Map<String, Object> profile;        //profile信息
    private Map<String, Object> user;           //用户信息
    private ChannelType channelType;            //渠道

    public void parseParameter(Map<String, Object> paramMap) throws CommonException {
        setOriginParam(paramMap);

        int positionId = 0;
        Map<String, Object> profileMap = null;

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("简历信息", paramMap.get("profile"), null, null);
        validateUtil.addIntTypeValidate("职位编号", paramMap.get("positionId"), null, null, 1, null);
        String checkoutParamResult = validateUtil.validate();
        if (!StringUtils.isNullOrEmpty(checkoutParamResult)) {
            CommonException exception = ExceptionFactory.buildException(VALIDATE_FAILED);
            exception.setMessage(checkoutParamResult);
            throw exception;
        }
        if (paramMap.get("positionId") != null) {
            positionId = BeanUtils.converToInteger(paramMap.get("positionId"));
            setPositionId(positionId);
        }
        if (paramMap.get("profile") != null) {
            profileMap = (Map<String, Object>) paramMap.get("profile");
            setProfile(profileMap);
            if (profileMap != null && profileMap.get("user") != null) {
                setUser((Map<String, Object>)profileMap.get("user"));
            }
        }
    }

    public Map<String, Object> getOriginParam() {
        return originParam;
    }

    public void setOriginParam(Map<String, Object> originParam) {
        this.originParam = originParam;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public Map<String, Object> getProfile() {
        return profile;
    }

    public void setProfile(Map<String, Object> profile) {
        this.profile = profile;
    }

    public Map<String, Object> getUser() {
        return user;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }
}
