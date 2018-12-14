package com.moseeker.useraccounts.pojo.neo4j;

import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;

/**
 * Created by moseeker on 2018/12/7.
 */
@RelationshipEntity(type="Friend")
public class Forward extends Relation{


    @Property
    private int share_chain_id;



    public int getShare_chain_id() {
        return share_chain_id;
    }

    public void setShare_chain_id(int share_chain_id) {
        this.share_chain_id = share_chain_id;
    }
}
