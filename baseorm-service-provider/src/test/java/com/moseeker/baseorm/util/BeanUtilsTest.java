package com.moseeker.baseorm.util;

import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import org.joda.time.DateTime;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by jack on 26/09/2017.
 */
public class BeanUtilsTest {

    @Test
    public void mapToRecord() throws Exception {

        Map<String, Object> user = new HashMap<>();
        user.put("mobile","15502117047");
        user.put("id","1");
        user.put("password","1111");
        user.put("username","username");
        user.put("headimg","aa.png");
        user.put("activation","1");
        user.put("email","wjf@moseeker.com");
        user.put("countryCode","1");
        user.put("isDisable", 1);
        user.put("activationCode", "11111");
        user.put("emailVerified", "1");
        user.put("lastLoginIp", "225.225.225.225");
        user.put("lastLoginTime", "2017-09-01 12:12:12");
        user.put("nickname", "昵称");
        user.put("parentid", "1");

        UserUserRecord userUserRecord = BeanUtils.MapToRecord(user, UserUserRecord.class);
        assertEquals(15502117047l, userUserRecord.getMobile().longValue());
        assertEquals(1, userUserRecord.getId().intValue());
        assertEquals("1111", userUserRecord.getPassword());
        assertEquals("username", userUserRecord.getUsername());
        assertEquals("aa.png", userUserRecord.getHeadimg());
        assertEquals(1, userUserRecord.getActivation().intValue());
        assertEquals("wjf@moseeker.com", userUserRecord.getEmail());
        assertEquals("1", userUserRecord.getCountryCode());
        assertEquals(1, userUserRecord.getIsDisable().intValue());
        assertEquals("11111", userUserRecord.getActivationCode());
        assertEquals(1, userUserRecord.getEmailVerified().intValue());
        assertEquals("225.225.225.225", userUserRecord.getLastLoginIp());
        assertEquals("2017-09-01 12:12:12", new DateTime(userUserRecord.getLastLoginTime().getTime()).toString("yyyy-MM-dd HH:mm:ss"));
        assertEquals("昵称", userUserRecord.getNickname());
        assertEquals(1, userUserRecord.getParentid().intValue());
    }

}