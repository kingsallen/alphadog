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
     * @param shareChainId  转发分享编号
     */
    void addFriendRelation(int startUserId, int endUserId, int shareChainId) throws CommonException;

    /**
     * 人脉连连看转发时插入人脉关系
     * @param startUserId   转发人编号
     * @param endUserId     点击人编号
     * @param connChainId  转发分享编号
     * @param positionId    连连看职位
     */
    void addConnRelation(int startUserId, int endUserId, int connChainId, int positionId) throws CommonException;


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
