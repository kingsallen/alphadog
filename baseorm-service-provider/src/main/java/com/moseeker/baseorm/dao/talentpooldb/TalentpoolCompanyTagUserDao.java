package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolCompanyTagUser;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyTagUserRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolCompanyTagUserDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser,TalentpoolCompanyTagUserRecord> {

    public TalentpoolCompanyTagUserDao(){
        super(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser.class);
    }
    public TalentpoolCompanyTagUserDao(TableImpl<TalentpoolCompanyTagUserRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser> talentpoolCompanyTagUserClass) {
        super(table, talentpoolCompanyTagUserClass);
    }

    public Map<Integer, Integer> getTagCountByTagIdList(List<Integer> tagIdList){
        Result<Record2<Integer, Integer>> result=create.select(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.TAG_ID, DSL.count(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.TAG_ID))
                .from(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER)
                .where(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.TAG_ID.in(tagIdList))
                .groupBy(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.TAG_ID)
                .fetch();
        if (result != null && result.size()>0) {
            Map<Integer, Integer> params = new HashMap<>();
            for(Record2<Integer, Integer> record2 : result){
                params.put(record2.value1(),record2.value2());
            }
            return params;
        } else {
            return null;
        }
    }
    /*
     根据tagId删除打标签的用户
     */
    public int deleteByTag(int companyTagId){
        int result=create.deleteFrom(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER).where(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.TAG_ID.eq(companyTagId)).execute();
        return result;
    }
    /*
    批量添加企业标签和人才关系表
     */
    public int batchAddTagAndUser(List<TalentpoolCompanyTagUserRecord> list){
        create.execute("set names utf8mb4");
        create.attach(list);
        int[] result=create.batchInsert(list).execute();
        if(result!=null&&result.length>0){
            return 1;
        }
        return 0;
    }



}
