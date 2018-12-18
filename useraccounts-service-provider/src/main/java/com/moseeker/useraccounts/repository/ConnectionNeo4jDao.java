package com.moseeker.useraccounts.repository;

import com.moseeker.useraccounts.pojo.neo4j.Connection;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

/**
 * Created by moseeker on 2018/12/13.
 */
@Component
public interface ConnectionNeo4jDao extends GraphRepository<Connection> {

    @Query("match (u1:UserUser),(u2:UserUser) where u1.user_id={firstUserId} and u2.user_id = {secondUserId} merge (u1)-[f:Connection{conn_chain_id:{connChainId}}]->(u2) return f")
    List<Connection> addConnection(@Param("firstUserId") int firstUserId, @Param("secondUserId") int secondUserId, @Param("conn_chain_id") int connChainId);

    @Query("match (u1:UserUser{user_id:{firstUserId}}),(u2:UserUser{user_id:{secondUserId}}),p=(u1)-[r:Connection]-(u2) where r.position_id ={positionId} return p")
    List<Connection> getTwoUserConn(@Param("firstUserId") int firstUserId, @Param("secondUserId") int secondUserId, @Param("positionId") int positionId);

}
