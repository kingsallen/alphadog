package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbScratchCard;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbScratchCardRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbScratchCardDO;
import org.jooq.Record2;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HrHbScratchCardDao extends JooqCrudImpl<HrHbScratchCardDO, HrHbScratchCardRecord> {

    public HrHbScratchCardDao() {
        super(HrHbScratchCard.HR_HB_SCRATCH_CARD, HrHbScratchCardDO.class);
    }

    public HrHbScratchCardDao(TableImpl<HrHbScratchCardRecord> table, Class<HrHbScratchCardDO> hrHbScratchCardDOClass) {
        super(table, hrHbScratchCardDOClass);
    }

    public List<Record2<Integer, String>> fetchCardNosByItemIdList(List<Integer> itemIdList) {

        return create.select(HrHbScratchCard.HR_HB_SCRATCH_CARD.HB_ITEM_ID, HrHbScratchCard.HR_HB_SCRATCH_CARD.CARDNO)
                .from(HrHbScratchCard.HR_HB_SCRATCH_CARD)
                .where(HrHbScratchCard.HR_HB_SCRATCH_CARD.HB_ITEM_ID.in(itemIdList))
                .fetch();
    }
}
