package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressLinksRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressLinksDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressLinksDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressLinksDao extends JooqCrudImpl<WordpressLinksDO, WordpressLinksRecord> {


    public WordpressLinksDao(TableImpl<WordpressLinksRecord> table, Class<WordpressLinksDO> wordpressLinksDOClass) {
        super(table, wordpressLinksDOClass);
    }
}
