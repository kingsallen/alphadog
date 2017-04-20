package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressOptions;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressOptionsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressOptionsDO;

/**
* @author xxx
* WordpressOptionsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressOptionsDao extends StructDaoImpl<WordpressOptionsDO, WordpressOptionsRecord, WordpressOptions> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressOptions.WORDPRESS_OPTIONS;
   }
}
