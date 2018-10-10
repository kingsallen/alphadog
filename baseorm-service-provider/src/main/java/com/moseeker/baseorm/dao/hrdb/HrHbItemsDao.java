package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbItemsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HrHbItemsDao extends JooqCrudImpl<HrHbItemsDO, HrHbItemsRecord> {

    public HrHbItemsDao() {
        super(HrHbItems.HR_HB_ITEMS, HrHbItemsDO.class);
    }

    public HrHbItemsDao(TableImpl<HrHbItemsRecord> table, Class<HrHbItemsDO> hrHbItemsDOClass) {
        super(table, hrHbItemsDOClass);
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> getHbItemsListBybindingIdList(List<Integer> bindingList,int wxUserId){
        List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> list=create.selectFrom(HrHbItems.HR_HB_ITEMS)
                .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.eq(wxUserId)).and(HrHbItems.HR_HB_ITEMS.BINDING_ID.in(bindingList))
                .fetchInto(com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems.class);
        return list;
    }
}
