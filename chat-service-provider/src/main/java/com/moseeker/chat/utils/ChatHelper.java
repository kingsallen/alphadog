package com.moseeker.chat.utils;

import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;

public class ChatHelper {

    /**
     * 聊天取HR名优先级
     * 「对外显示昵称」>「姓名」>「微信昵称」>「手机号（加密）」;
     * @param hrAccountDO
     * @param wxUserDO
     * @return
     */
    public static String hrChatName(UserHrAccountDO hrAccountDO, UserWxUserDO wxUserDO) {
        if (!StringUtils.isNullOrEmpty(hrAccountDO.getRemarkName())) {
            return hrAccountDO.getRemarkName();
        } else if (!StringUtils.isNullOrEmpty(hrAccountDO.getUsername())) {
            return hrAccountDO.getUsername();
        } else if (wxUserDO != null && !StringUtils.isNullOrEmpty(wxUserDO.getNickname())) {
            return wxUserDO.getNickname();
        }
        return StringUtils.isNullOrEmpty(hrAccountDO.getMobile()) && hrAccountDO.getMobile().length()==11
                ? "" : hrAccountDO.getMobile().substring(0,3)+"****"+hrAccountDO.getMobile().substring(7);
    }

}
