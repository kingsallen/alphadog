package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrAutomaticTagRecord;
import com.moseeker.common.util.StringUtils;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG;

/**
 * Created by zztaiwll on 18/10/22.
 */
@Repository
public class TalentpoolHrAutomaticTagDao extends JooqCrudImpl<TalentpoolHrAutomaticTag,TalentpoolHrAutomaticTagRecord> {
    public TalentpoolHrAutomaticTagDao(){
        super(TALENTPOOL_HR_AUTOMATIC_TAG,TalentpoolHrAutomaticTag.class);
    }
    public TalentpoolHrAutomaticTagDao(TableImpl<TalentpoolHrAutomaticTagRecord> table, Class<TalentpoolHrAutomaticTag> talentpoolHrAutomaticTagClass) {
        super(table, talentpoolHrAutomaticTagClass);
    }
    /*
     按照id列表删除标签
     */
    public  int deleteByIdList(List<Integer> idList){
        int result=create.update(TALENTPOOL_HR_AUTOMATIC_TAG).set(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE,0).where(TALENTPOOL_HR_AUTOMATIC_TAG.ID.in(idList)).execute();
        return result;
    }

    public List<TalentpoolHrAutomaticTag> getHrAutomaticTagListByHrId(int hrId){
        List<TalentpoolHrAutomaticTag> list= create.selectFrom(TALENTPOOL_HR_AUTOMATIC_TAG)
                .where(TALENTPOOL_HR_AUTOMATIC_TAG.HR_ID.eq(hrId)).and(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE.eq(1))
                .orderBy(TALENTPOOL_HR_AUTOMATIC_TAG.UPDATE_TIME.desc())
                .fetchInto(TalentpoolHrAutomaticTag.class);
        return list;
    }

    public List<TalentpoolHrAutomaticTag> getHrAutomaticTagListByHrIdList(List<Integer> hrIdList){
        List<TalentpoolHrAutomaticTag> list= create.selectFrom(TALENTPOOL_HR_AUTOMATIC_TAG)
                .where(TALENTPOOL_HR_AUTOMATIC_TAG.HR_ID.in(hrIdList)).and(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE.eq(1))
                .orderBy(TALENTPOOL_HR_AUTOMATIC_TAG.UPDATE_TIME.desc())
                .fetchInto(TalentpoolHrAutomaticTag.class);
        return list;
    }

    public List<Map<String,Object>> getHrAutomaticTagMapListByHrId(int hrId){
        List<Map<String,Object>> list= create.selectFrom(TALENTPOOL_HR_AUTOMATIC_TAG)
                .where(TALENTPOOL_HR_AUTOMATIC_TAG.HR_ID.eq(hrId)).and(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE.eq(1))
                .orderBy(TALENTPOOL_HR_AUTOMATIC_TAG.UPDATE_TIME.desc())
                .fetchMaps();
        return list;
    }

    public List<Map<String,Object>>  getHrAutomaticTagMapListByHrIdPage(int hrId,int page,int pageSize){
        List<Map<String,Object>> list= create.selectFrom(TALENTPOOL_HR_AUTOMATIC_TAG)
                .where(TALENTPOOL_HR_AUTOMATIC_TAG.HR_ID.eq(hrId)).and(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE.eq(1))
                .orderBy(TALENTPOOL_HR_AUTOMATIC_TAG.UPDATE_TIME.desc())
                .limit((page-1)*pageSize,pageSize)
                .fetchMaps();;
        return list;
    }

    public int getHrAutomaticTagCountByHrId(int hrId){
        int result= create.selectCount().from(TALENTPOOL_HR_AUTOMATIC_TAG)
                .where(TALENTPOOL_HR_AUTOMATIC_TAG.HR_ID.eq(hrId)).and(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE.eq(1))
                .fetchOne().value1();
        return result;
    }

    public TalentpoolHrAutomaticTag getHrAutomaticTagById(int id){
        TalentpoolHrAutomaticTag result= create.selectFrom(TALENTPOOL_HR_AUTOMATIC_TAG)
                .where(TALENTPOOL_HR_AUTOMATIC_TAG.ID.eq(id)).and(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE.eq(1))
                .fetchOneInto(TalentpoolHrAutomaticTag.class);
        return result;
    }

    public int getTagNameCount(int hrId,String name,int tagId){
        int count=create.selectCount().from(TALENTPOOL_HR_AUTOMATIC_TAG).where(TALENTPOOL_HR_AUTOMATIC_TAG.HR_ID.eq(hrId))
                .and(TALENTPOOL_HR_AUTOMATIC_TAG.NAME.eq(name)).and(TALENTPOOL_HR_AUTOMATIC_TAG.ID.notEqual(tagId))
                .and(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE.eq(1))
                .fetchOne().value1();
        return count;
    }
    public String validateTagStatusById(int tagId){
        int count=create.selectCount().from(TALENTPOOL_HR_AUTOMATIC_TAG).where(TALENTPOOL_HR_AUTOMATIC_TAG.ID.notEqual(tagId))
                .and(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE.eq(1))
                .fetchOne().value1();
        if(count==0){
           return "没有找到该标签";
        }
        return "";
    }

    public Map<String,Object> getSingleDataById(int id){
        List<Map<String,Object>> list= create.selectFrom(TALENTPOOL_HR_AUTOMATIC_TAG)
                .where(TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE.eq(1))
                .and(TALENTPOOL_HR_AUTOMATIC_TAG.ID.eq(id))
                .fetchMaps();
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        return list.get(0);
    }

}
