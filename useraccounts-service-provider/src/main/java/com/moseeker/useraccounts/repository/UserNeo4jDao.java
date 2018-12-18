package com.moseeker.useraccounts.repository;

import com.moseeker.useraccounts.pojo.neo4j.UserNode;
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


}
