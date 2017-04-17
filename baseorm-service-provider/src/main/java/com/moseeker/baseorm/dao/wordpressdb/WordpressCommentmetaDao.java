package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressCommentmeta;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressCommentmetaRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressCommentmetaDO;

/**
* @author xxx
* WordpressCommentmetaDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressCommentmetaDao extends StructDaoImpl<WordpressCommentmetaDO, WordpressCommentmetaRecord, WordpressCommentmeta> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressCommentmeta.WORDPRESS_COMMENTMETA;
   }
}
