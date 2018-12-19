package com.moseeker.useraccounts.pojo.neo4j;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;

/**
 * Created by moseeker on 2018/12/7.
 */
@RelationshipEntity(type="Friend")
public class Forward extends Relation{


    @Property
    @Index(unique = true, primary = true)
    private int share_chain_id;
    @Property
    private int parent_id;
    @Property
    private int position_id;



    public int getShare_chain_id() {
        return share_chain_id;
    }

    public void setShare_chain_id(int share_chain_id) {
        this.share_chain_id = share_chain_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }
}
