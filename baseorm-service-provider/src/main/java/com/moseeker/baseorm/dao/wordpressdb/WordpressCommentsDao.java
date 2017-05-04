package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressCommentsRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressCommentsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressCommentsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressCommentsDao extends JooqCrudImpl<WordpressCommentsDO, WordpressCommentsRecord> {


    public WordpressCommentsDao(TableImpl<WordpressCommentsRecord> table, Class<WordpressCommentsDO> wordpressCommentsDOClass) {
        super(table, wordpressCommentsDOClass);
    }
}
