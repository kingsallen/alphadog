package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolTalent;

import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolUpload;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTalentRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolUploadRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zztaiwll on 18/1/9.
 */
@Service
public class TalentpoolUploadDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolUpload,TalentpoolUploadRecord> {

    public TalentpoolUploadDao(){
        super(TalentpoolUpload.TALENTPOOL_UPLOAD,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolUpload.class);
    }
    public TalentpoolUploadDao(TableImpl<TalentpoolUploadRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolUpload> talentpoolUploadClass) {
        super(table, talentpoolUploadClass);
    }
}
