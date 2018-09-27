package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbItemsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.sum;

@Repository
public class HrHbItemsDao extends JooqCrudImpl<HrHbItemsDO, HrHbItemsRecord> {

    public HrHbItemsDao() {
        super(HrHbItems.HR_HB_ITEMS, HrHbItemsDO.class);
    }

    public HrHbItemsDao(TableImpl<HrHbItemsRecord> table, Class<HrHbItemsDO> hrHbItemsDOClass) {
        super(table, hrHbItemsDOClass);
    }

    public List<HrHbItemsRecord> fetchItemsByWxUserIdList(List<Integer> wxUserIdList, int index, int pageSize) {
        if (wxUserIdList != null && wxUserIdList.size() > 0) {
            return create.selectFrom(HrHbItems.HR_HB_ITEMS)
                    .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.in(wxUserIdList))
                    .limit(index, pageSize)
                    .fetch();
        } else {
            return new ArrayList<>();
        }
    }

    public int countByWxUserIdList(List<Integer> wxUserIdList) {
        if (wxUserIdList != null && wxUserIdList.size() > 0) {
            return create.selectCount()
                    .from(HrHbItems.HR_HB_ITEMS)
                    .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.in(wxUserIdList))
                    .fetchOne().value1();
        } else {
            return 0;
        }
    }

    public double sumRedPacketsByWxUserIdList(List<Integer> wxUserIdList) {
        if (wxUserIdList != null && wxUserIdList.size() > 0) {
            return create.select(sum(HrHbItems.HR_HB_ITEMS.AMOUNT))
                    .from(HrHbItems.HR_HB_ITEMS)
                    .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.in(wxUserIdList))
                    .fetchOne()
                    .value1()
                    .doubleValue();
        } else {
            return 0;
        }
    }
}
