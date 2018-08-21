package com.moseeker.baseorm.dao.historydb;

import com.moseeker.baseorm.db.historydb.tables.daos.HistoryUserEmployeeUpvoteDao;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryUserEmployeeUpvoteRecord;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.using;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
@Repository
public class CustomHistoryUpVoteDao extends HistoryUserEmployeeUpvoteDao{

    @Autowired
    public CustomHistoryUpVoteDao(Configuration configuration) {
        super(configuration);
    }

    public void batchInsert(List<HistoryUserEmployeeUpvoteRecord> histories) {
        using(configuration())
                .batchInsert(histories)
                .execute();
    }
}
