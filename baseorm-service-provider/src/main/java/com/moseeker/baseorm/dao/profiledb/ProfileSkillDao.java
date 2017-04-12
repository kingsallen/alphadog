package com.moseeker.baseorm.dao.profiledb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.profiledb.tables.ProfileSkill;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileSkillDO;

/**
* @author xxx
* ProfileSkillDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileSkillDao extends StructDaoImpl<ProfileSkillDO, ProfileSkillRecord, ProfileSkill> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = ProfileSkill.PROFILE_SKILL;
   }
}
