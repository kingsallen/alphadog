package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTerms;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermsDO;

/**
* @author xxx
* WordpressTermsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressTermsDao extends StructDaoImpl<WordpressTermsDO, WordpressTermsRecord, WordpressTerms> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressTerms.WORDPRESS_TERMS;
   }
}
