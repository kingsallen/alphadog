package com.moseeker.chat.utils;

import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatHelperTest {

    @Test
    public void hrChatName(){
        UserHrAccountDO hrAccountDO = new UserHrAccountDO();
        UserWxUserDO wxUserDO = new UserWxUserDO();

        String mobile = "15312345678";
        hrAccountDO.setMobile(mobile);
        assert ChatHelper.hrChatName(hrAccountDO,wxUserDO).equals("153****5678");

        String nickname = "微信昵称";
        wxUserDO.setNickname(nickname);
        assert ChatHelper.hrChatName(hrAccountDO,wxUserDO).equals(nickname);


        String username = "用户名";
        hrAccountDO.setUsername(username);
        assert ChatHelper.hrChatName(hrAccountDO,wxUserDO).equals(username);

        String remarkName = "备注名";
        hrAccountDO.setRemarkName(remarkName);
        assert ChatHelper.hrChatName(hrAccountDO,wxUserDO).equals(remarkName);
    }

}