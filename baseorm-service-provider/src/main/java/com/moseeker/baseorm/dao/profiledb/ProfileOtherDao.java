package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileOther;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.text.ParseException;

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
}
