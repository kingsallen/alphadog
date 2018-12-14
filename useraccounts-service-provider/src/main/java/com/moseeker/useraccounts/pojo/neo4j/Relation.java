package com.moseeker.useraccounts.pojo.neo4j;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Created by moseeker on 2018/12/14.
 */
@RelationshipEntity(type = "relation")
public class Relation {

    @GraphId
    private Long id;
    @StartNode
    private UserNode startNode;
    @EndNode
    private UserNode endNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserNode getStartNode() {
        return startNode;
    }

    public void setStartNode(UserNode startNode) {
        this.startNode = startNode;
    }

    public UserNode getEndNode() {
        return endNode;
    }

    public void setEndNode(UserNode endNode) {
        this.endNode = endNode;
    }
}
