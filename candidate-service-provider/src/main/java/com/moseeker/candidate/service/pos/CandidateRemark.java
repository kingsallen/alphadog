package com.moseeker.candidate.service.pos;

import java.util.List;

/**
 * 被员工标注的候选人信息
 * Created by jack on 13/02/2017.
 */
public class CandidateRemark extends Model {

    private int ID;
    private int userID;
    private boolean star;
    private int accountID;
    private int status;

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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static List<CandidateRemark> getCandidateRemarks() {
        return null;
    }

    public static void updateToDB(List<CandidateRemark> crs) {

    }
}
