package com.moseeker.baseorm.dao.hrdb;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Date: 2018/9/26
 * @Author: JackYang
 */
@Repository
public class HrAccountApplicationNotifyDao extends com.moseeker.baseorm.db.hrdb.tables.daos.HrAccountApplicationNotifyDao {


    @Autowired
    public HrAccountApplicationNotifyDao(Configuration configuration) {
        super(configuration);
    }
}
