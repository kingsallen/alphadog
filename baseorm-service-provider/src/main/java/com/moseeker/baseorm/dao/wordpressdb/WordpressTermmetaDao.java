package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermmetaRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermmetaDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressTermmetaDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressTermmetaDao extends JooqCrudImpl<WordpressTermmetaDO, WordpressTermmetaRecord> {


    public WordpressTermmetaDao(TableImpl<WordpressTermmetaRecord> table, Class<WordpressTermmetaDO> wordpressTermmetaDOClass) {
        super(table, wordpressTermmetaDOClass);
    }
}
