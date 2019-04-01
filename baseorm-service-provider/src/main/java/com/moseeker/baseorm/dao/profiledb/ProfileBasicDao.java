package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileBasic;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* ProfileBasicDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileBasicDao extends JooqCrudImpl<ProfileBasicDO, ProfileBasicRecord> {

    public ProfileBasicDao() {
        super(ProfileBasic.PROFILE_BASIC, ProfileBasicDO.class);
    }

    public ProfileBasicDao(TableImpl<ProfileBasicRecord> table, Class<ProfileBasicDO> profileBasicDOClass) {
        super(table, profileBasicDOClass);
    }

    /**
     * 根据 profileId 集合查询简历基本信息。
     * @param basicIdList 简历编号集合
     * @return 简历基本信息集合
     */
    public List<ProfileBasicRecord> fetchBasicByProfileIdList(List<Integer> basicIdList) {
        if (basicIdList != null && basicIdList.size() > 0) {
            return create.selectFrom(ProfileBasic.PROFILE_BASIC)
                    .where(ProfileBasic.PROFILE_BASIC.PROFILE_ID.in(basicIdList))
                    .fetch();
        }
        return new ArrayList<>();
    }


    public com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileBasic getProfileBasicByProfileId(int profileId){
        return create.selectFrom(ProfileBasic.PROFILE_BASIC)
                .where(ProfileBasic.PROFILE_BASIC.PROFILE_ID.eq(profileId)).limit(1)
                .fetchOneInto(com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileBasic.class);
    }
}
