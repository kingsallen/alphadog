package com.moseeker.baseorm.dao.hrdb;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrResource;
import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.hrdb.tables.HrTeamMember;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;

import java.sql.Connection;

/**
 * @author xxx HrTeamDao 实现类 （groovy 生成） 2017-03-21
 */
@Repository
public class HrTeamDao extends StructDaoImpl<HrTeamDO, HrTeamRecord, HrTeam> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrTeam.HR_TEAM;
    }


}
