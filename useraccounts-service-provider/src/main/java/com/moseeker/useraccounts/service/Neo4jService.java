package com.moseeker.useraccounts.service;

import com.moseeker.common.exception.CommonException;
import java.util.List;

/**
 * Created by moseeker on 2018/12/14.
 */
public interface Neo4jService {

    /**
     * 职位转发时插入人脉关系
     * @param startUserId   转发人编号
     * @param endUserId     点击人编号
     */
    void addFriendRelation(int startUserId, int endUserId) throws CommonException;

    /**
     * 寻找两个人的最短关系路径
     * @param startUserId   开始人编号
     * @param endUserId     结束人编号
     * @param companyId     员工公司编号
     * @return
     * @throws CommonException
     */
    List<Integer> fetchShortestPath(int startUserId, int endUserId, int companyId)throws CommonException;
}
