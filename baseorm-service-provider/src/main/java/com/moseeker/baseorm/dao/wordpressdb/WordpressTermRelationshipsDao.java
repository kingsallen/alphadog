package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTermRelationships;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermRelationshipsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermRelationshipsDO;

/**
* @author xxx
* WordpressTermRelationshipsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressTermRelationshipsDao extends StructDaoImpl<WordpressTermRelationshipsDO, WordpressTermRelationshipsRecord, WordpressTermRelationships> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS;
   }
}
