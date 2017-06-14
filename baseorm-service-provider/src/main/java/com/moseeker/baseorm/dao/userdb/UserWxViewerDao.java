package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserWxViewer;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxViewerRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxViewerDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* UserWxViewerDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserWxViewerDao extends JooqCrudImpl<UserWxViewerDO, UserWxViewerRecord> {

    public UserWxViewerDao() {
        super(UserWxViewer.USER_WX_VIEWER, UserWxViewerDO.class);
    }

    public UserWxViewerDao(TableImpl<UserWxViewerRecord> table, Class<UserWxViewerDO> userWxViewerDOClass) {
        super(table, userWxViewerDOClass);
    }
}
