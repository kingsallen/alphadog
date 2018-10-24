package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTagUser;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrAutomaticTagUserRecord;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
