package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileAwards;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAwardsDO;

/**
* @author xxx
* ProfileAwardsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileAwardsDao extends StructDaoImpl<ProfileAwardsDO, ProfileAwardsRecord, ProfileAwards> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileAwards.PROFILE_AWARDS;
   }
}
