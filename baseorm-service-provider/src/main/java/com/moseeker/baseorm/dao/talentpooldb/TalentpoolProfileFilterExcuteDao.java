package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileFilterExecute;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileFilterExecuteRecord;
import java.util.List;
import java.util.stream.Collectors;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolProfileFilterExcuteDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute,TalentpoolProfileFilterExecuteRecord> {

    public TalentpoolProfileFilterExcuteDao(){
        super(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute.class);
    }
    public TalentpoolProfileFilterExcuteDao(TableImpl<TalentpoolProfileFilterExecuteRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute> talentpoolProfileFilterExecuteClass) {
        super(table, talentpoolProfileFilterExecuteClass);
    }

    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute> getFilterExecuteByfilterIdList(List<Integer> filterIdList){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute> result=create.selectFrom(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE)
                .where(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE.FILTER_ID.in(filterIdList))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute.class);
        return result;
    }
    public List<TalentpoolProfileFilterExecuteRecord> getFilterExecuteRecordByfilterIdList(List<Integer> filterIdList){
        List<TalentpoolProfileFilterExecuteRecord> result=create.selectFrom(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE)
                .where(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE.FILTER_ID.in(filterIdList))
                .fetch();
        return result;
    }

    public void deleteFilterExcuteRecordList(List<TalentpoolProfileFilterExecuteRecord> filterRecordList){
        if(filterRecordList != null && filterRecordList.size()>0) {
            for(TalentpoolProfileFilterExecuteRecord record : filterRecordList) {
                create.deleteFrom(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE)
                        .where(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE.FILTER_ID.in(record.getFilterId()))
                        .and(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE.EXECUTE_ID.in(record.getExecuteId()))
                        .execute();
            }
        }
    }

    public void deleteFilterExcuteByFilterIdList(List<Integer> filterIdList){
        if(filterIdList != null && filterIdList.size()>0) {
            create.deleteFrom(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE)
                    .where(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE.FILTER_ID.in(filterIdList))
                    .execute();

        }
    }

    public List<Integer> getFilterExcuteByFilterIdListAndExecuterId(List<Integer> filterIdList, int executer_id){
        Result<Record1<Integer>> result = create.select(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE.FILTER_ID)
                .from(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE)
                .where(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE.FILTER_ID.in(filterIdList))
                .and(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE.EXECUTE_ID.eq(executer_id))
                .fetch();
        if (result != null && result.size() > 0) {
            return result.stream().filter(record1 -> record1.value1() != null && record1.value1().intValue() > 0)
                    .map(record1 -> record1.value1()).collect(Collectors.toList());
        }
        return null;
    }

}
