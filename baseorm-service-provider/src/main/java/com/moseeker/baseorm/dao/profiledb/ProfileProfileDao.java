package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;

/**
* @author xxx
* ProfileProfileDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileProfileDao extends StructDaoImpl<ProfileProfileDO, ProfileProfileRecord, ProfileProfile> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileProfile.PROFILE_PROFILE;
   }
}
