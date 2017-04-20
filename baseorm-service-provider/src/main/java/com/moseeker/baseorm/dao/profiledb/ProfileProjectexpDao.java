package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileProjectexp;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProjectexpDO;

/**
* @author xxx
* ProfileProjectexpDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileProjectexpDao extends StructDaoImpl<ProfileProjectexpDO, ProfileProjectexpRecord, ProfileProjectexp> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileProjectexp.PROFILE_PROJECTEXP;
   }
}
