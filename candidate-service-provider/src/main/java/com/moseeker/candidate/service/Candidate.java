package com.moseeker.candidate.service;

/**
 * 候选人
 * Created by jack on 10/02/2017.
 */
public interface Candidate {

    /**
     * 用户查看职位，可能促使用户成为候选人。接口调用方不需要理会这个接口是否成功，所以不需要返回值
     * @param userID 用户编号
     * @param positionID 职位编号
     * @param fromEmployee 是否来自员工转发
     */
    public void glancePosition(int userID, int positionID, boolean fromEmployee);
}
