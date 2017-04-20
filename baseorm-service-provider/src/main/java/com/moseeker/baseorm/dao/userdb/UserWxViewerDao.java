package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserWxViewer;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxViewerRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxViewerDO;

/**
* @author xxx
* UserWxViewerDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserWxViewerDao extends StructDaoImpl<UserWxViewerDO, UserWxViewerRecord, UserWxViewer> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserWxViewer.USER_WX_VIEWER;
   }
}
