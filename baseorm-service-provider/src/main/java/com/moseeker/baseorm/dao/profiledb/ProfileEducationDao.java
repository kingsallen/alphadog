package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileEducation;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileEducationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileEducationDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileEducationDao extends JooqCrudImpl<ProfileEducationDO, ProfileEducationRecord> {

    public ProfileEducationDao() {
        super(ProfileEducation.PROFILE_EDUCATION, ProfileEducationDO.class);
    }

    public ProfileEducationDao(TableImpl<ProfileEducationRecord> table, Class<ProfileEducationDO> profileEducationDOClass) {
        super(table, profileEducationDOClass);
    }
}
