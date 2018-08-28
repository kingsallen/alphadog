package com.moseeker.useraccounts.constant;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
public enum  LeaderBoardType {

    Monthly(1, "月榜"), Quarterly(2, "季榜"), Annually(3, "年榜");

    private LeaderBoardType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    private static Map<Integer, LeaderBoardType> storage = new HashMap<>();
    static {
        for (LeaderBoardType leaderBoardType : values()) {
            storage.put(leaderBoardType.getValue(), leaderBoardType);
        }
    }

    public static LeaderBoardType instanceFromValue(int value) {
        return storage.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    /**
     * 创建索引中的积分时间跨度
     * @return 时间跨度
     */
    public String buildTimeSpan() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        String timeSpan = null;
        switch (this) {
            case Monthly:
                timeSpan = year + "-" + (month < 10 ? ("0"+month) : month);
                break;
            case Quarterly:
                if (month <4) {
                    timeSpan = year +""+ 1;
                } else if (month < 7) {
                    timeSpan = year +""+ 2;
                } else if (month < 10) {
                    timeSpan = year +""+ 3;
                } else {
                    timeSpan = year +""+ 4;
                }
                break;
            case Annually:
                timeSpan = String.valueOf(year);
                break;
            default:
        }
        return timeSpan;
    }
}
