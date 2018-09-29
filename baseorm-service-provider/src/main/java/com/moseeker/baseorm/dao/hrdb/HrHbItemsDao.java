package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.HrHbScratchCard;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbItemsDO;
import org.joda.time.DateTime;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchItemsByWxUserIdList(List<Integer> wxUserIdList, int index, int pageSize) {
        if (wxUserIdList != null && wxUserIdList.size() > 0) {
            Result<Record> result =  create.select(HrHbItems.HR_HB_ITEMS.fields())
                    .from(HrHbItems.HR_HB_ITEMS)
                    .innerJoin(HrHbScratchCard.HR_HB_SCRATCH_CARD)
                    .on(HrHbItems.HR_HB_ITEMS.ID.eq(HrHbScratchCard.HR_HB_SCRATCH_CARD.HB_ITEM_ID))
                    .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.in(wxUserIdList))
                    .and((HrHbItems.HR_HB_ITEMS.STATUS.in(Constant.receiveHB))
                            .or(HrHbItems.HR_HB_ITEMS.STATUS.in(Constant.openCard)
                                    .and((HrHbScratchCard.HR_HB_SCRATCH_CARD.CREATE_TIME.gt(Constant.HB_START_TIME)))
                            )
                    )
                    .orderBy(HrHbScratchCard.HR_HB_SCRATCH_CARD.STATUS.asc(),
                            HrHbItems.HR_HB_ITEMS.OPEN_TIME.desc(),
                            HrHbItems.HR_HB_ITEMS.UPDATE_TIME.desc())
                    .limit(index, pageSize)
                    .fetch();
            if (result != null && result.size() > 0) {
                return result.into(com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems.class);
            } else {
                return new ArrayList<>();
            }
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
