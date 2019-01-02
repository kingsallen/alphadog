package com.moseeker.useraccounts.service;

import com.moseeker.common.exception.CommonException;
import com.moseeker.useraccounts.service.impl.pojos.UserDepthVO;
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

    /**
     * 更新UserUser节点的employee_company属性
     * @param userId    节点的userId
     * @param companyId 公司编号
     * @return
     * @throws CommonException
     */
    boolean updateUserEmployeeCompany(int userId, int companyId) throws CommonException;

    /**
     * 批量更新UserUser节点的employee_company属性
     * @param userIds   加点的userId集合
     * @param companyId 公司编号
     * @throws CommonException
     */
    void updateUserEmployeeCompany(List<Integer> userIds, int companyId) throws CommonException;

    /**
     * 获取到候选人三度以内曾经触达到的员工user编号
     * @param userId    候选人编号
     * @param companyId 公司编号
     * @return
     * @throws CommonException
     */
    List<Integer> fetchUserThreeDepthEmployee(int userId, int companyId) throws CommonException;


    /**
     * 获取到员工三度以内曾经触达到的候选人编号
     * @param userId    员工user编号
     * @return
     * @throws CommonException
     */
    List<UserDepthVO> fetchEmployeeThreeDepthUser(int userId) throws CommonException;

    /**
     * 获取员工到候选人的最短路径
     * @param userId    员工的user_id
     * @param companyId 员工认证的公司编号
     * @param userIdList 候选人编号列表
     * @return
     * @throws CommonException
     */
    List<UserDepthVO> fetchDepthUserList(int userId, int companyId, List<Integer> userIdList) throws CommonException;

}
