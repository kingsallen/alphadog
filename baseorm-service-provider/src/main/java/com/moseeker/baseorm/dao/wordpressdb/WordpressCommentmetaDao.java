package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressCommentmetaRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressCommentmetaDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressCommentmetaDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressCommentmetaDao extends JooqCrudImpl<WordpressCommentmetaDO, WordpressCommentmetaRecord> {


    public WordpressCommentmetaDao(TableImpl<WordpressCommentmetaRecord> table, Class<WordpressCommentmetaDO> wordpressCommentmetaDOClass) {
        super(table, wordpressCommentmetaDOClass);
    }
}
