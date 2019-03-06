package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyCommonInfo;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyCommonInfoRecord;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyCommonInfo;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyCommonInfo.THIRDPARTY_COMMON_INFO;

@Repository
public class ThirdpartyCommonInfoDao extends JooqCrudImpl<ThirdPartyCommonInfo, ThirdpartyCommonInfoRecord> {
    public ThirdpartyCommonInfoDao(TableImpl<ThirdpartyCommonInfoRecord> table, Class<ThirdPartyCommonInfo> thirdPartyCommonInfoClass) {
        super(table, thirdPartyCommonInfoClass);
    }

    public ThirdpartyCommonInfoDao() {
        super(THIRDPARTY_COMMON_INFO, ThirdPartyCommonInfo.class);
    }

    public int postThirdPartyCommonInfo(ThirdPartyCommonInfo param) {
        ThirdpartyCommonInfoRecord record = new ThirdpartyCommonInfoRecord();
        record.from(param);
        return create.insertInto(THIRDPARTY_COMMON_INFO)
                .set(record)
                .onDuplicateKeyUpdate().set(THIRDPARTY_COMMON_INFO.CONTENT,record.getContent()).execute();

    }

    public List<ThirdPartyCommonInfo> getThirdPartyCommonInfo(ThirdPartyCommonInfo param) {

        SelectConditionStep<ThirdpartyCommonInfoRecord> selectWhere = create.selectFrom(THIRDPARTY_COMMON_INFO).where("1=1");
        if(param.getCompany_id()>0){
            selectWhere.and(THIRDPARTY_COMMON_INFO.COMPANY_ID.eq(param.getCompany_id()));
        }

        if(param.getUser_id()>0) {
            selectWhere.and(THIRDPARTY_COMMON_INFO.USER_ID.eq(param.getUser_id()));
        }

        return selectWhere.fetchInto(ThirdPartyCommonInfo.class);
    }
}
