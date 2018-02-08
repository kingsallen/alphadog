package com.moseeker.position.service.position;

import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
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

    public static String convertDescription(String accounTabilities, String requirement) {
        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(accounTabilities)) {
            StringBuffer tablities = replaceLineBreakToTagP(accounTabilities);
            if (accounTabilities.contains("职位描述")) {
                descript.append(tablities.toString());
            } else {
                descript.append("<p>职位描述：</p>" + tablities.toString());
            }
        }
        if (StringUtils.isNotNullOrEmpty(requirement)) {
            StringBuffer require = replaceLineBreakToTagP(requirement);
            if (requirement.contains("职位要求")) {
                descript.append(require.toString());
            } else {
                descript.append("<p>职位要求：</p>" + require.toString());
            }
        }

        return descript.toString();
    }

    public static StringBuffer replaceLineBreakToTagP(String str){
        StringBuffer require = new StringBuffer();
        if (str.contains("\n")) {
            String results1[] = str.split("\n");
            for (String result : results1) {
                require.append("<p>  " + result + "</p>");
            }
        } else {
            require.append("<p>  " + str + "</p>");
        }
        return require;
    }
}
