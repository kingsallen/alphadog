package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermsRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressTermsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressTermsDao extends JooqCrudImpl<WordpressTermsDO, WordpressTermsRecord> {


    public WordpressTermsDao(TableImpl<WordpressTermsRecord> table, Class<WordpressTermsDO> wordpressTermsDOClass) {
        super(table, wordpressTermsDOClass);
    }
}
