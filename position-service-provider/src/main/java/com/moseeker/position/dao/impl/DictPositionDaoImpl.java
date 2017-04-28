package com.moseeker.position.dao.impl;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictPosition;
import com.moseeker.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.position.dao.DictPositionDao;

import org.springframework.stereotype.Repository;

/**
 * @author wjf
 */
@Repository
public class DictPositionDaoImpl extends BaseDaoImpl<DictPositionRecord, DictPosition> implements DictPositionDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictPosition.DICT_POSITION;
    }


}
