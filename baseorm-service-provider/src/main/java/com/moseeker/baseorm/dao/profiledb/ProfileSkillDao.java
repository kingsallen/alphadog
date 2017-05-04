package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileSkillDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileSkillDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileSkillDao extends JooqCrudImpl<ProfileSkillDO, ProfileSkillRecord> {


    public ProfileSkillDao(TableImpl<ProfileSkillRecord> table, Class<ProfileSkillDO> profileSkillDOClass) {
        super(table, profileSkillDOClass);
    }
}
