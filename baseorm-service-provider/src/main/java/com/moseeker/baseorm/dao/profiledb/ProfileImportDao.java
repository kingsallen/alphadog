package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileImport;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileImportDO;

/**
* @author xxx
* ProfileImportDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileImportDao extends StructDaoImpl<ProfileImportDO, ProfileImportRecord, ProfileImport> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileImport.PROFILE_IMPORT;
   }
}
