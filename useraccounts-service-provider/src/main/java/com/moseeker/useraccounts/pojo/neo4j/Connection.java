package com.moseeker.useraccounts.pojo.neo4j;

import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;

/**
 * Created by moseeker on 2018/12/14.
 */
@RelationshipEntity(type = "Connection")
public class Connection extends Relation {

    @Property
    private int connChainId;

    public int getConnChainId() {
        return connChainId;
    }

    public void setConnChainId(int connChainId) {
        this.connChainId = connChainId;
    }
}
