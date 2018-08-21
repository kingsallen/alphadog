package com.moseeker.baseorm.constant;

/**
 * @Author: jack
 * @Date: 2018/8/16
 */
public enum UpVoteState {

    UpVote(0, "点赞"), UnUpVote(1, "未点赞");

    private int value;
    private String name;

    private UpVoteState(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
