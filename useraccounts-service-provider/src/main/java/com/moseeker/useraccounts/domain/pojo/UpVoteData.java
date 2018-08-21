package com.moseeker.useraccounts.domain.pojo;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
public class UpVoteData {

    private int receiver;
    private int receiverUpVoteCount;
    private boolean upVote;

    public boolean isUpVote() {
        return upVote;
    }

    public void setUpVote(boolean upVote) {
        this.upVote = upVote;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public int getReceiverUpVoteCount() {
        return receiverUpVoteCount;
    }

    public void setReceiverUpVoteCount(int receiverUpVoteCount) {
        this.receiverUpVoteCount = receiverUpVoteCount;
    }
}
