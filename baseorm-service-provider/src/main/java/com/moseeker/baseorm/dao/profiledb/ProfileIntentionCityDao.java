package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionCity;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionCityDO;

/**
* @author xxx
* ProfileIntentionCityDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionCityDao extends StructDaoImpl<ProfileIntentionCityDO, ProfileIntentionCityRecord, ProfileIntentionCity> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileIntentionCity.PROFILE_INTENTION_CITY;
   }
}
