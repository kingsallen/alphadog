package com.moseeker.common.weixin;

import com.moseeker.common.constants.ChannelType;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author Rays
 * @DATE 2019-07-29
 **/
public enum SceneType {

    APPLY_FROM_PC("PCAPPLICATION_","pc端投递后生成二维码的场景");//pc端投递后扫码

    private String scene;

    private String desc;

    private static final Map<String, SceneType> intToEnum
            = new HashMap<String, SceneType>();

    static { // Initialize map from constant name to enum constant
        for (SceneType op : values())
            intToEnum.put(op.getScene(), op);
    }

    public static SceneType instaceFromInteger(int value) {
        return intToEnum.get(value);
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    SceneType(String scene, String desc) {
        this.scene = scene;
        this.desc = desc;
    }
}
