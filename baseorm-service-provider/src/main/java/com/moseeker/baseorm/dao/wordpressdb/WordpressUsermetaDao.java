package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressUsermetaRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressUsermetaDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressUsermetaDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressUsermetaDao extends JooqCrudImpl<WordpressUsermetaDO, WordpressUsermetaRecord> {


    public WordpressUsermetaDao(TableImpl<WordpressUsermetaRecord> table, Class<WordpressUsermetaDO> wordpressUsermetaDOClass) {
        super(table, wordpressUsermetaDOClass);
    }
}
