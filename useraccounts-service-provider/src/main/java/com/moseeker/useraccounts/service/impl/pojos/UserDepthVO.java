package com.moseeker.useraccounts.service.impl.pojos;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * Created by moseeker on 2018/12/27.
 */
@QueryResult
public class UserDepthVO {

    private int userId;
    private int depth;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
