package com.moseeker.useraccounts.repository;

import com.moseeker.useraccounts.pojo.neo4j.Forward;
import com.moseeker.useraccounts.pojo.neo4j.Relation;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

/**
 * Created by moseeker on 2018/12/13.
 */
@Component
public interface ForwardNeo4jDao extends GraphRepository<Relation> {

    @Query("match (u1:UserUser),(u2:UserUser) where u1.user_id={firstUserId} and u2.user_id = {secondUserId} merge (u1)-[f:Friend{share_chain_id:{shareChainId}}]->(u2) return f")
    List<Forward> addFriend(@Param("firstUserId") int firstUserId, @Param("secondUserId") int secondUserId, @Param("shareChainId") int shareChainId);

    @Query("match (u1:UserUser{user_id:{firstUserId}}),(u2:UserUser{user_id:{secondUserId}}),p=(u1)-[r:Friend]-(u2) where r.position_id ={positionId} return p")
    List<Forward> getTwoUserFriend(@Param("firstUserId") int firstUserId, @Param("secondUserId") int secondUserId,@Param("positionId") int positionId);

    @Query("match (u1:UserUser{user_id:{firstUserId}}),(u2:UserUser{user_id:{secondUserId}}),p = shortestpath((u1)-[*]-(u2)) " +
                   " where all(x in nodes(p)  where x.user_id={firstUserId} or x.employee_company<>{companyId}) return p")
    List<Relation> getTwoUserShortFriend(@Param("firstUserId") int firstUserId, @Param("secondUserId") int secondUserId, @Param("companyId") int companyId);
}
