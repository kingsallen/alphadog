package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.db.talentpooldb.tables.daos.TalentpoolProfilePoolDao;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

/**
 * @author yehu
 * date 2019-08-05 10:49
 */
@Repository
public class TalentpoolProfilePoolJooqDao extends TalentpoolProfilePoolDao {

    public TalentpoolProfilePoolJooqDao(Configuration configuration){
        super(configuration);
    }
}
