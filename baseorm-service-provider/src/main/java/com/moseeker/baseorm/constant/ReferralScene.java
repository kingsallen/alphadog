package com.moseeker.baseorm.constant;

/**
 * @Author: jack
 * @Date: 2018/9/5
 */
public enum ReferralScene {

    ChatBot((byte)2, "员工通过ChatBot内推简历"), Referral((byte)1, "内推"), ;

    private ReferralScene(byte scene, String name) {
        this.scene = scene;
        this.name = name;
    }

    private byte scene;
    private String name;

    public byte getScene() {
        return scene;
    }

    public String getName() {
        return name;
    }
}
