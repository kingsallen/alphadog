package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTagUser;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrAutomaticTagUserRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolHrAutomaticTagUser.TALENTPOOL_HR_AUTOMATIC_TAG_USER;

/**
 * Created by zztaiwll on 18/10/22.
 */
@Repository
public class TalentpoolHrAutomaticTagUserDao  extends JooqCrudImpl<TalentpoolHrAutomaticTagUser,TalentpoolHrAutomaticTagUserRecord> {
    public TalentpoolHrAutomaticTagUserDao(){
        super(TALENTPOOL_HR_AUTOMATIC_TAG_USER,TalentpoolHrAutomaticTagUser.class);
    }
    public TalentpoolHrAutomaticTagUserDao(TableImpl<TalentpoolHrAutomaticTagUserRecord> table, Class<TalentpoolHrAutomaticTagUser> talentpoolHrAutomaticTagUserClass) {
        super(table, talentpoolHrAutomaticTagUserClass);
    }

    public int deleteByTagIdList(List<Integer> tagIdList){
        int result=create.deleteFrom(TALENTPOOL_HR_AUTOMATIC_TAG_USER).where(TALENTPOOL_HR_AUTOMATIC_TAG_USER.TAG_ID.in(tagIdList)).execute();
        return result;
    }
    public List<TalentpoolHrAutomaticTagUserRecord> getDataByTagIdAndUserId(List<Integer> tagIdList,Set<Integer> userIdSet){
        List<TalentpoolHrAutomaticTagUserRecord> list=create.selectFrom(TALENTPOOL_HR_AUTOMATIC_TAG_USER).where(TALENTPOOL_HR_AUTOMATIC_TAG_USER.TAG_ID.in(tagIdList))
                .and(TALENTPOOL_HR_AUTOMATIC_TAG_USER.USER_ID.in(userIdSet)).fetchInto(TalentpoolHrAutomaticTagUserRecord.class);
        return list;
    }
    public List<TalentpoolHrAutomaticTagUser> getDataByTagIdAndUserId(List<Integer> tagIdList,int userId){
        List<TalentpoolHrAutomaticTagUser> list=create.selectFrom(TALENTPOOL_HR_AUTOMATIC_TAG_USER).where(TALENTPOOL_HR_AUTOMATIC_TAG_USER.TAG_ID.in(tagIdList))
                .and(TALENTPOOL_HR_AUTOMATIC_TAG_USER.USER_ID.eq(userId)).fetchInto(TalentpoolHrAutomaticTagUser.class);
        return list;
    }

    public void deleteList(List<TalentpoolHrAutomaticTagUserRecord> list){
        for(TalentpoolHrAutomaticTagUserRecord record:list){
            create.deleteFrom(TALENTPOOL_HR_AUTOMATIC_TAG_USER).where(TALENTPOOL_HR_AUTOMATIC_TAG_USER.TAG_ID.eq(record.getTagId()))
                    .and(TALENTPOOL_HR_AUTOMATIC_TAG_USER.USER_ID.eq(record.getUserId())).execute();
        }
    }
    public int addList(List<TalentpoolHrAutomaticTagUserRecord> list){
        for(TalentpoolHrAutomaticTagUserRecord record:list){
            create.insertInto(TALENTPOOL_HR_AUTOMATIC_TAG_USER).columns(TALENTPOOL_HR_AUTOMATIC_TAG_USER.TAG_ID,TALENTPOOL_HR_AUTOMATIC_TAG_USER.USER_ID)
                    .values(record.getTagId(),record.getUserId()).execute();
        }
        return 1;
    }
}
