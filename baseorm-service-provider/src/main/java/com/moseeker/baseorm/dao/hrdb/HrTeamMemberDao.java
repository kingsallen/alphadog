package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrTeamMember;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamMemberRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamMemberDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrTeamMemberDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrTeamMemberDao extends JooqCrudImpl<HrTeamMemberDO, HrTeamMemberRecord> {

    public HrTeamMemberDao() {
        super(HrTeamMember.HR_TEAM_MEMBER, HrTeamMemberDO.class);
    }

    public HrTeamMemberDao(TableImpl<HrTeamMemberRecord> table, Class<HrTeamMemberDO> hrTeamMemberDOClass) {
        super(table, hrTeamMemberDOClass);
    }
}
