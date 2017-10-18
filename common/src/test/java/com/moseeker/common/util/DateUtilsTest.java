package com.moseeker.common.util;

import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.joda.time.DateTime;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by jack on 17/10/2017.
 */
public class DateUtilsTest {

    @Test
    public void dateToDateTest() {
        try {
            Date dateTime = DateUtils.shortTimeToDate("2017-10-17 10:11:01");
            Date dateTime1 = DateUtils.shortTimeToDate("2017-9-29 10:22:02");

            for (int i=0; i<10; i++) {
                new Thread(() -> {
                    String shortTime = "2017-10-17 10:11:01";
                    Date date = new Date();
                    try {
                        date = DateUtils.shortTimeToDate(shortTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    assertEquals(date.getTime(), dateTime.getTime());
                }).start();

                new Thread(() -> {
                    String shortTime = "2017-9-29 10:22:02";
                    Date date = new Date();
                    try {
                        date = DateUtils.shortTimeToDate(shortTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    assertEquals(date.getTime(), dateTime1.getTime());
                }).start();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}