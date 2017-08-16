package com.moseeker.profile.service.impl.retriveprofile;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 简历回收业务。用于从第三方平台上拉去的简历信息、用户信息和投递的职位信息，从而生成MoSeeker平台的用户信息、简历信息、投递信息等。
 * 任何一种渠道的回收，处理的任务可能是不一样。简历回收通过参数中的渠道信息，来确定定义好的回收流程。
 * Created by jack on 10/07/2017.
 */
@Service
public class RetriveProfile {

    Logger logger = LoggerFactory.getLogger(RetriveProfile.class);

    @Autowired
    protected Map<String, RetrievalFlow> flowMap;

    /**
     * 简历回收
     * @param parameter 参数
     * @return 执行成功与否
     * @throws CommonException 异常
     */
    @CounterIface
    public boolean retrieve(String parameter) throws CommonException {
        logger.info("parameter1:{}", parameter);
        logger.info("parameter2:{}", EmojiFilter.filterEmoji1(parameter));
        Map<String, Object> paramMap = JSON.parseObject(EmojiFilter.filterEmoji1(parameter));
        if (paramMap.get("channel") != null) {
            int channel = (Integer)paramMap.get("channel");
            ChannelType channelType = ChannelType.instaceFromInteger(channel);
            if (channelType == null) {
                throw ExceptionFactory.buildException(Category.VALIDATION_RETRIEVAL_CHANNEL_NOT_CUSTOMED);
            }
            RetrievalFlow retrievalFlow = flowMap.get(channelType.toString().toLowerCase()+"_retrieval_flow");
            return retrievalFlow.retrieveProfile(paramMap, channelType);
        } else {
            throw ExceptionFactory.buildException(Category.VALIDATION_RETRIEVAL_CHANNEL_NOT_CUSTOMED);
        }
    }
}
