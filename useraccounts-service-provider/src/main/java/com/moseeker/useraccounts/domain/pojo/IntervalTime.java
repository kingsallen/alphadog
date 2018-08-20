package com.moseeker.useraccounts.domain.pojo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
public class IntervalTime {

    private long startTime;
    private long endTime;

    public IntervalTime(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static IntervalTime buildIntervalTime() {
        long now = System.currentTimeMillis();
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        LocalDateTime currentFriday = nowLocalDateTime.with(DayOfWeek.FRIDAY).withHour(17).withMinute(0).withSecond(0).withNano(0);
        long endTime;
        long startTime;
        if (now > currentFriday.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond()*1000) {
            startTime = currentFriday.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond()*1000;
            endTime = currentFriday.plusDays(7).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond()*1000;
        } else {
            startTime = currentFriday.minusDays(7).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond()*1000;
            endTime = currentFriday.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond()*1000;
        }

        IntervalTime intervalTime = new IntervalTime(startTime, endTime);
        return intervalTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
