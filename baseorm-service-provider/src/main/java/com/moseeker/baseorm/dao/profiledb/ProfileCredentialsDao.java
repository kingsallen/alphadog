package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileCredentials;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileCredentialsDO;

/**
* @author xxx
* ProfileCredentialsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileCredentialsDao extends StructDaoImpl<ProfileCredentialsDO, ProfileCredentialsRecord, ProfileCredentials> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileCredentials.PROFILE_CREDENTIALS;
   }
}
