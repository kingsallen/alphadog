package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolPast;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolPastRecord;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;
import java.util.List;

import static com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolPast.TALENTPOOL_PAST;

/**
 * Created by zztaiwll on 18/4/3.
 */
public class TalentpoolPastDao extends JooqCrudImpl<TalentpoolPast,TalentpoolPastRecord> {
    public TalentpoolPastDao(){
        super(TALENTPOOL_PAST,TalentpoolPast.class);
    }
    public TalentpoolPastDao(TableImpl<TalentpoolPastRecord> table, Class<TalentpoolPast> talentpoolPastClass) {
        super(table, talentpoolPastClass);
    }
    /*
     获取曾任职务或者曾任公司
     */
    public List<TalentpoolPast> getPastList(int companyId,int type,int flag){
        List<TalentpoolPast> list=create.selectFrom(TALENTPOOL_PAST).where(TALENTPOOL_PAST.COMPANY_ID.eq(companyId)).and(TALENTPOOL_PAST.TYPE.eq(type)).and(TALENTPOOL_PAST.FLAG.eq(flag)).fetchInto(TalentpoolPast.class);
        return list;
    }
    /*
     查询单个曾任职务或者公司
     */
    public TalentpoolPast getSinglePastt(int companyId,int type,int flag,String name){
        TalentpoolPast resilt=create.selectFrom(TALENTPOOL_PAST).where(TALENTPOOL_PAST.COMPANY_ID.eq(companyId)).and(TALENTPOOL_PAST.TYPE.eq(type))
                .and(TALENTPOOL_PAST.FLAG.eq(flag))
                .and(TALENTPOOL_PAST.NAME.eq(name))
                .fetchOneInto(TalentpoolPast.class);
        return resilt;
    }
    /*
     新增曾任职务或者曾任公司
     */
    public int upsertPast(int companyId,int type,int flag,String name){
        TalentpoolPast talentpoolPast=this.getSinglePastt(companyId,type,flag,name);
        if(talentpoolPast==null){
            int result=create.insertInto(TALENTPOOL_PAST).columns(TALENTPOOL_PAST.COMPANY_ID,TALENTPOOL_PAST.NAME,TALENTPOOL_PAST.TYPE,TALENTPOOL_PAST.FLAG)
                    .values(companyId,name,type,flag).execute();
            return result;
        }else{
            return 1;
        }
    }
}
