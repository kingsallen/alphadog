package com.moseeker.common.weixin;

/**
 * @Description TODO
 * @Author Rays
 * @DATE 2019-07-29
 **/
public enum SceneType {

    PCAPPLICATION_(0);//pc端投递后扫码

    private int scene;//场景值

    private SceneType(int scene){
        this.scene = scene;
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }
}
