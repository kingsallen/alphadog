package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressComments;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressCommentsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressCommentsDO;

/**
* @author xxx
* WordpressCommentsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressCommentsDao extends StructDaoImpl<WordpressCommentsDO, WordpressCommentsRecord, WordpressComments> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressComments.WORDPRESS_COMMENTS;
   }
}
