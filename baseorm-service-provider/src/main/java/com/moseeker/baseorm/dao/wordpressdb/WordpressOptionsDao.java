package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressOptionsRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressOptionsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressOptionsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressOptionsDao extends JooqCrudImpl<WordpressOptionsDO, WordpressOptionsRecord> {


    public WordpressOptionsDao(TableImpl<WordpressOptionsRecord> table, Class<WordpressOptionsDO> wordpressOptionsDOClass) {
        super(table, wordpressOptionsDOClass);
    }
}
