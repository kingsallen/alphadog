package com.moseeker.entity.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 职位状态常量
 * Created by jack on 09/11/2017.
 */
public enum TalentpoolEmailType {

    Actived(69, true), Deleted(70, true), TakeOff(71, true), Intervation(72, false), Resume(72, false);

    private static Map<Integer, TalentpoolEmailType> map = new HashMap<>();

    static {
        for (TalentpoolEmailType csat : values()) {
            map.put(csat.getConfig_id(), csat);
        }
    }

    private TalentpoolEmailType(int config_id, boolean status) {
        this.config_id = config_id;
        this.status = status;
    }
    private int config_id;
    private boolean status;

    public int getConfig_id() {
        return config_id;
    }


    public boolean getStatus() {
        return status;
    }

    public static TalentpoolEmailType instanceFromByte(Integer value) {
        if (value != null && map.get(value.intValue()) != null) {
            return map.get(value.intValue());
        } else {
            return TalentpoolEmailType.Actived;
        }
    }
}
