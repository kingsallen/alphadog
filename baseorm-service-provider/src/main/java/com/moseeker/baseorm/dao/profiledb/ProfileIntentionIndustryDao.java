package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionIndustry;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionIndustryDO;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* ProfileIntentionIndustryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionIndustryDao extends JooqCrudImpl<ProfileIntentionIndustryDO, ProfileIntentionIndustryRecord> {

    public ProfileIntentionIndustryDao() {
        super(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY, ProfileIntentionIndustryDO.class);
    }

    public ProfileIntentionIndustryDao(TableImpl<ProfileIntentionIndustryRecord> table, Class<ProfileIntentionIndustryDO> profileIntentionIndustryDOClass) {
        super(table, profileIntentionIndustryDOClass);
    }

    public List<ProfileIntentionIndustryRecord> getIntentionIndustries(List<Integer> intentionIds) {
        List<ProfileIntentionIndustryRecord> records = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            SelectWhereStep<ProfileIntentionIndustryRecord> select = create.selectFrom(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY);
            SelectConditionStep<ProfileIntentionIndustryRecord> selectCondition = null;
            if(intentionIds != null && intentionIds.size() > 0) {
                for(int i=0; i<intentionIds.size(); i++) {
                    if(i == 0) {
                        selectCondition = select.where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.equal((int)(intentionIds.get(i))));
                    } else {
                        selectCondition.or(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.equal((int)(intentionIds.get(i))));
                    }
                }
            }
            records = selectCondition.fetch();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                //do nothing
            }
        }

        return records;
    }
}
