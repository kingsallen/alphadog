package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolCompanyTagUser;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyTagUserRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.moseeker.common.util.StringUtils;
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
    public int deleteByTag(List<Integer> companyTagIdList){
        int result=create.deleteFrom(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER).where(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.TAG_ID.in(companyTagIdList)).execute();
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

    public void addTagAndUser(List<TalentpoolCompanyTagUserRecord> list){
        if(!StringUtils.isEmptyList(list)){
            for(TalentpoolCompanyTagUserRecord record:list){
                create.insertInto(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER,TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.TAG_ID ,
                        TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.USER_ID)
                        .values(record.getTagId(), record.getUserId())
                        .onDuplicateKeyUpdate()
                        .set(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.USER_ID,record.getUserId())
                        .execute();
            }
        }
    }
    //删除人才和标签的关系
    public int batchDeleteTagAndUser(List<TalentpoolCompanyTagUserRecord> list){
        if(StringUtils.isEmptyList(list)){
            return 1;
        }
        for(TalentpoolCompanyTagUserRecord record:list){
            create.deleteFrom(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER).where(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.TAG_ID.eq(record.getTagId()))
                    .and(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.USER_ID.eq(record.getUserId())).execute();
        }
        return 1;
    }
    /*
     根据userid集合获取其上标签集合
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser> getTagByUserIdSet(Set<Integer> userIdSet){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser> list=create.selectFrom(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER)
                .where(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.USER_ID.in(userIdSet))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser.class);

        return list;

    }
    /*
     根据user_id删除所有相关的企业标签
     */
    public int deleteByUserId(Set<Integer> userIdSet){
        int result=create.deleteFrom(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER).where(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.USER_ID.in(userIdSet)).execute();
        return result;
    }
    /*
    根据userid和tagId删除user_tag
     */
    public void deleteByUserIdAndTagId(List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser> list){
        for(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser tagUser:list){
            create.deleteFrom(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER)
                    .where(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.USER_ID.eq(tagUser.getUserId()))
                    .and(TalentpoolCompanyTagUser.TALENTPOOL_COMPANY_TAG_USER.TAG_ID.eq(tagUser.getTagId()))
                    .execute();
        }
    }
}
