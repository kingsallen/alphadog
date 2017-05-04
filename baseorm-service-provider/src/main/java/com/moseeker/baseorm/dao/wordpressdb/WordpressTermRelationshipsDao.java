package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermRelationshipsRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermRelationshipsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressTermRelationshipsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressTermRelationshipsDao extends JooqCrudImpl<WordpressTermRelationshipsDO, WordpressTermRelationshipsRecord> {


    public WordpressTermRelationshipsDao(TableImpl<WordpressTermRelationshipsRecord> table, Class<WordpressTermRelationshipsDO> wordpressTermRelationshipsDOClass) {
        super(table, wordpressTermRelationshipsDOClass);
    }
}
