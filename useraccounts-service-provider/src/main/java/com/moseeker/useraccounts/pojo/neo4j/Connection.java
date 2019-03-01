package com.moseeker.useraccounts.pojo.neo4j;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;

/**
 * Created by moseeker on 2018/12/14.
 */
@RelationshipEntity(type = "Connection")
public class Connection extends Relation {

    @Property
    @Index(unique = true, primary = true)
    private int conn_chain_id;

    @Property
    private int position_id;

    public int getConn_chain_id() {
        return conn_chain_id;
    }

    public void setConn_chain_id(int conn_chain_id) {
        this.conn_chain_id = conn_chain_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }
}
