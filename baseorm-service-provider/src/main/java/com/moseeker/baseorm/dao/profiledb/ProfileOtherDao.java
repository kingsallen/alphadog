package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.db.profiledb.tables.ProfileOther;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.ProfileOtherDO;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 15/03/2017.
 */
@Repository
public class ProfileOtherDao extends StructDaoImpl<ProfileOtherDO, ProfileOtherRecord, ProfileOther>{
    @Override
    protected void initJOOQEntity() {
        this.tableLike = ProfileOther.PROFILE_OTHER;
    }
}
