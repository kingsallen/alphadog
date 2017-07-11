package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author xxx HrTeamDao 实现类 （groovy 生成） 2017-03-21
 */
@Repository
public class HrTeamDao extends JooqCrudImpl<HrTeamDO, HrTeamRecord> {

    public HrTeamDao() {
        super(HrTeam.HR_TEAM, HrTeamDO.class);
    }

    public HrTeamDao(TableImpl<HrTeamRecord> table, Class<HrTeamDO> hrTeamDOClass) {
        super(table, hrTeamDOClass);
    }
}
