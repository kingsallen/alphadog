package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileBasic;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;

/**
* @author xxx
* ProfileBasicDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileBasicDao extends StructDaoImpl<ProfileBasicDO, ProfileBasicRecord, ProfileBasic> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileBasic.PROFILE_BASIC;
   }
}
