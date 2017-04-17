package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileEducation;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileEducationDO;

/**
* @author xxx
* ProfileEducationDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileEducationDao extends StructDaoImpl<ProfileEducationDO, ProfileEducationRecord, ProfileEducation> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileEducation.PROFILE_EDUCATION;
   }
}
