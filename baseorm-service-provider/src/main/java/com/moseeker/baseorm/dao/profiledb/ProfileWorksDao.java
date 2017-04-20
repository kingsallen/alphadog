package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileWorks;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorksDO;

/**
* @author xxx
* ProfileWorksDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileWorksDao extends StructDaoImpl<ProfileWorksDO, ProfileWorksRecord, ProfileWorks> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileWorks.PROFILE_WORKS;
   }
}
