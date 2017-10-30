package com.moseeker.position.service.position;

import com.moseeker.common.util.EmojiFilter;
import com.moseeker.thrift.gen.position.struct.JobPostrionObj;
import org.eclipse.jetty.util.StringUtil;

import java.util.List;

/**
 *
 * Created by jack on 25/10/2017.
 */
public class PositionUtil {

    /**
     * 过滤职位中信息中的Emoji信息
     * @param jobPositionHandlerDates 职位列表
     */
    public static void refineEmoji(List<JobPostrionObj> jobPositionHandlerDates) {
        if (jobPositionHandlerDates != null && jobPositionHandlerDates.size() > 0) {
            jobPositionHandlerDates.forEach(jobPostrionObj -> {
                if (StringUtil.isNotBlank(jobPostrionObj.getAccountabilities())) {
                    jobPostrionObj.setAccountabilities(EmojiFilter.filterEmoji1(jobPostrionObj.getAccountabilities()));
                }
            });
        }
    }
}
