package com.moseeker.baseorm.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/9/5
 */
public enum ReferralRelationShipType {

    OTHER((byte)0, "其他"), SUPERIOR((byte)1, "上级"), SUBORDINATE((byte)2, "下属"), COLLEAGUE((byte)3, "非上下级的前同事"),
    ALUMNI((byte)4, "校友"), RELATIVES((byte)5, "亲属");

    private ReferralRelationShipType(byte scene, String name) {
        this.scene = scene;
        this.name = name;
    }

    private static  Map<Byte, ReferralRelationShipType> result = new HashMap<>();
    static {
        for (ReferralRelationShipType RelationShipType : values()) {
            result.put(RelationShipType.getScene(), RelationShipType);
        }
    }
    private byte scene;
    private String name;

    public byte getScene() {
        return scene;
    }

    public String getName() {
        return name;
    }

    public static ReferralRelationShipType instanceFromValue(byte value) {
        return result.get(value);
    }
}
