package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileLanguage;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileLanguageDO;

/**
* @author xxx
* ProfileLanguageDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileLanguageDao extends StructDaoImpl<ProfileLanguageDO, ProfileLanguageRecord, ProfileLanguage> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileLanguage.PROFILE_LANGUAGE;
   }
}
