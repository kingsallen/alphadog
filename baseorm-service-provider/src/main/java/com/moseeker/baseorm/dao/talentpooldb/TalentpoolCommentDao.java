package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolComment;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCommentRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolCommentDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolComment,TalentpoolCommentRecord> {
    public TalentpoolCommentDao(){
        super(TalentpoolComment.TALENTPOOL_COMMENT,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolComment.class);
    }
    public TalentpoolCommentDao(TableImpl<TalentpoolCommentRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolComment> talentpoolCommentClass) {
        super(table, talentpoolCommentClass);
    }
}
