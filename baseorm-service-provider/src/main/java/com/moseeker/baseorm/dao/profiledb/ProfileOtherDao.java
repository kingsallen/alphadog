package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.Profiledb;
import com.moseeker.baseorm.db.profiledb.tables.ProfileOther;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;


/**
 * Created by jack on 15/03/2017.
 */
@Repository
public class ProfileOtherDao extends JooqCrudImpl<ProfileOtherDO, ProfileOtherRecord> {

    public ProfileOtherDao() {
        super(ProfileOther.PROFILE_OTHER, ProfileOtherDO.class);
    }

    public ProfileOtherDao(TableImpl<ProfileOtherRecord> table, Class<ProfileOtherDO> profileOtherDOClass) {
        super(table, profileOtherDOClass);
    }

    public int delOtherByProfileId(int profileId) {
        int count = 0;

        count = create.deleteFrom(ProfileOther.PROFILE_OTHER).where(
                ProfileOther.PROFILE_OTHER.PROFILE_ID.equal((int) (profileId))).execute();

        return count;
    }

    public Object customSelect(String tableName, String selectColumn, String fieldName, int profileId) {
        String sql = "select " + selectColumn + " from " + Profiledb.PROFILEDB.getName() + "." + tableName + " where " + fieldName + " = " + profileId + " limit 1 ";
        Record result = create.fetchOne(sql);
        return result == null ? "" : result.get(selectColumn);
    }

    public com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther getProfileOtherByProfileId(Integer profileId) {
        return create.selectFrom(ProfileOther.PROFILE_OTHER).where(ProfileOther.PROFILE_OTHER.PROFILE_ID.eq(profileId)).fetchOneInto(com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther.class);
    }

    public ProfileOtherRecord fetchProfileOther(Integer profileId) {
        if (profileId != null && profileId > 0) {
            return create
                    .selectFrom(ProfileOther.PROFILE_OTHER)
                    .where(ProfileOther.PROFILE_OTHER.PROFILE_ID.eq(profileId))
                    .limit(1)
                    .fetchOne();
        } else {
            return null;
        }
    }
}
