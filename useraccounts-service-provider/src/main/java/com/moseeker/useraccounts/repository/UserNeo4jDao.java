package com.moseeker.useraccounts.repository;

import com.moseeker.useraccounts.pojo.neo4j.EmployeeCompanyVO;
import com.moseeker.useraccounts.pojo.neo4j.UserNode;
import com.moseeker.useraccounts.pojo.neo4j.UserDepthVO;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

/**
 * Created by moseeker on 2018/12/13.
 */
@Component
public interface UserNeo4jDao extends GraphRepository<UserNode> {

    @Query("merge (u:UserNode:UserUser{user_id:{userId},wxuser_id:{wxuserId},nickname:{nickname},headimgurl:{headimgurl}," +
                   "employee_company:{employeeCompany},employee_id:{employeeId}}) return u")
    List<UserNode> addUserNodeList(@Param("userId") int userId, @Param("wxuserId") int name, @Param("nickname") String nickname,
                                   @Param("headimgurl") String headimgurl,@Param("employeeCompany") String employeeCompany,@Param("employeeId") String employeeId);

    @Query("MATCH (u:UserUser{user_id:{userId}}) return u limit 1")
    UserNode getUserNodeById(@Param("userId") int userId);

    @Query("MATCH (u1:UserUser) where u1.user_id = {userId1} or u1.user_id = {userId2} return u1 limit 2")
    List<UserNode> listUserNodeById(@Param("userId1") int userId1, @Param("userId2") int userId2);

    @Query("MATCH (u:UserUser) where u.user_id in {userIds} set u.employee_company={employeeCompany}")
    void updateUserEmployeeCompanyList(@Param("userIds") List<Integer> idList, @Param("employeeCompany") int employeeCompany);

    @Query("MATCH (u:UserUser) where u.user_id = {userId} set u.employee_company={employeeCompany} return u limit 1")
    UserNode updateUserEmployeeCompany(@Param("userId") Integer userId, @Param("employeeCompany") int employeeCompany);

    @Query("match (c1:UserUser),(c2:UserUser) where c1.user_id={userId} and c2.employee_company <>0 and c2.user_id in {userIdList}" +
                   "   match p=shortestpath((c1)-[*1..3]-(c2)) return c2.user_id as userId,c2.employee_company as companyId")
    List<EmployeeCompanyVO> fetchUserThreeDepthEmployee(@Param("userId") int userId, @Param("userIdList") List<Integer> userIdList);

    @Query("match (u1:UserUser),(u2:UserUser) where u1.user_id = {userId} and u2.user_id in {presenteeUserIds} and u2.employee_company <> {employeeCompany} " +
            "match p =shortestpath((u1)-[*1..3]-(u2)) with u2.user_id as userId,length(p) as depth where depth <=3 return userId, depth")
    List<UserDepthVO> fetchEmployeeThreeDepthUser(@Param("userId") int userId, @Param("presenteeUserIds") List<Integer> presenteeUserIds, @Param("employeeCompany") int employeeCompany);

    @Query("match (u1:UserUser),(u2:UserUser) where u1.user_id = {userId} and u2.user_id in {presenteeUserIds} and u2.employee_company <> {employeeCompany} match p =shortestpath((u1)-[*1..3]-(u2)) " +
                   "  return distinct u2.user_id as userId,length(p) as depth order by depth  ")
    List<UserDepthVO> fetchDepthUserList(@Param("userId") int userId, @Param("presenteeUserIds") List<Integer> presenteeUserIds, @Param("employeeCompany") int employeeCompany);

}
