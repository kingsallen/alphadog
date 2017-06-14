package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTermTaxonomy;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermTaxonomyRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermTaxonomyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressTermTaxonomyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressTermTaxonomyDao extends JooqCrudImpl<WordpressTermTaxonomyDO, WordpressTermTaxonomyRecord> {

    public WordpressTermTaxonomyDao() {
        super(WordpressTermTaxonomy.WORDPRESS_TERM_TAXONOMY, WordpressTermTaxonomyDO.class);
    }

    public WordpressTermTaxonomyDao(TableImpl<WordpressTermTaxonomyRecord> table, Class<WordpressTermTaxonomyDO> wordpressTermTaxonomyDOClass) {
        super(table, wordpressTermTaxonomyDOClass);
    }
}
