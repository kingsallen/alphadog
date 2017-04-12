package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTermTaxonomy;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermTaxonomyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermTaxonomyDO;

/**
* @author xxx
* WordpressTermTaxonomyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressTermTaxonomyDao extends StructDaoImpl<WordpressTermTaxonomyDO, WordpressTermTaxonomyRecord, WordpressTermTaxonomy> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressTermTaxonomy.WORDPRESS_TERM_TAXONOMY;
   }
}
