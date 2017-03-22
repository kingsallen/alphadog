package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTermmeta;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermmetaRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermmetaDO;

/**
* @author xxx
* WordpressTermmetaDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressTermmetaDao extends StructDaoImpl<WordpressTermmetaDO, WordpressTermmetaRecord, WordpressTermmeta> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressTermmeta.WORDPRESS_TERMMETA;
   }
}
