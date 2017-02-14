package com.moseeker.candidate.service.pos;

import java.util.Date;

/**
 * 候选人职位查看记录
 * Created by jack on 10/02/2017.
 */
public class CandidatePosition extends Model {

    private int positionId; // optional
    private int userId; // optional
    private Date updateTime; // optional
    private boolean isInterested; // optional
    private int candidateCompanyId; // optional
    private int viewNumber; // optional
    private boolean sharedFromEmployee; // optional



    public CandidatePosition(int positionID, int userID) {
        this.positionId = positionID;
        this.userId = userID;
    }

    @Override
    public void loadFromDB() {

    }

    @Override
    public void updateToDB() {

    }

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public void addViewNumber() {
        this.viewNumber ++;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isInterested() {
        return isInterested;
    }

    public void setInterested(boolean interested) {
        isInterested = interested;
    }

    public int getCandidateCompanyId() {
        return candidateCompanyId;
    }

    public void setCandidateCompanyId(int candidateCompanyId) {
        this.candidateCompanyId = candidateCompanyId;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    public boolean isSharedFromEmployee() {
        return sharedFromEmployee;
    }

    public void setSharedFromEmployee(boolean sharedFromEmployee) {
        this.sharedFromEmployee = sharedFromEmployee;
    }
}
