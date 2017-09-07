package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.entity.biz.ProfilePojo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 简历业务
 * Created by jack on 07/09/2017.
 */
@Service
@CounterIface
public class ProfileEntity {

    /**
     * 如果用户已经存在简历，那么则更新简历；如果不存在简历，那么添加简历。
     * @param profileParameter 简历信息
     * @return 格式化的简历信息
     */
    public ProfilePojo parseProfile(String profileParameter) {
        Map<String, Object> paramMap = JSON.parseObject(EmojiFilter.filterEmoji1(EmojiFilter.unicodeToUtf8(profileParameter)));
        return ProfilePojo.parseProfile(paramMap);
    }
}
