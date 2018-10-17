package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbConfig;
import com.moseeker.baseorm.db.hrdb.tables.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.HrHbScratchCard;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbItemsDO;
import org.joda.time.DateTime;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.sum;

@Repository
public class HrHbItemsDao extends JooqCrudImpl<HrHbItemsDO, HrHbItemsRecord> {

    private final Timestamp HB_START_TIME;
    private final List<Integer> receiveHB;
    private final List<Integer> openCard;

    public HrHbItemsDao() {
        super(HrHbItems.HR_HB_ITEMS, HrHbItemsDO.class);
        HB_START_TIME = new Timestamp(DateTime.parse("2018-07-01").getMillis());
        receiveHB = new ArrayList<Integer>(){{add(100);add(101);}};
        openCard = new ArrayList<Integer>(){{add(1);add(2);add(3);add(4);add(5);add(6);add(7);add(-1);}};
    }

    public HrHbItemsDao(TableImpl<HrHbItemsRecord> table, Class<HrHbItemsDO> hrHbItemsDOClass) {
        super(table, hrHbItemsDOClass);
        HB_START_TIME = new Timestamp(DateTime.parse("2018-07-01").getMillis());
        receiveHB = new ArrayList<Integer>(){{add(100);add(101);}};
        openCard = new ArrayList<Integer>(){{add(1);add(2);add(3);add(4);add(5);add(6);add(7);add(-1);}};
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchItemsByWxUserIdList(
            List<Integer> wxUserIdList, int companyId, int index, int pageSize) {
        if (wxUserIdList != null && wxUserIdList.size() > 0) {
            Result<Record> result =  create.select(HrHbItems.HR_HB_ITEMS.fields())
                    .from(HrHbItems.HR_HB_ITEMS)
                    .innerJoin(HrHbScratchCard.HR_HB_SCRATCH_CARD)
                    .on(HrHbItems.HR_HB_ITEMS.ID.eq(HrHbScratchCard.HR_HB_SCRATCH_CARD.HB_ITEM_ID))
                    .innerJoin(HrHbConfig.HR_HB_CONFIG)
                    .on(HrHbItems.HR_HB_ITEMS.HB_CONFIG_ID.eq(HrHbConfig.HR_HB_CONFIG.ID))
                    .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.in(wxUserIdList))
                    .and((HrHbScratchCard.HR_HB_SCRATCH_CARD.CREATE_TIME.gt(HB_START_TIME)))
                    .and(HrHbConfig.HR_HB_CONFIG.COMPANY_ID.eq(companyId))
                    .orderBy(HrHbScratchCard.HR_HB_SCRATCH_CARD.STATUS.asc(),
                            HrHbItems.HR_HB_ITEMS.OPEN_TIME.desc(),
                            HrHbScratchCard.HR_HB_SCRATCH_CARD.CREATE_TIME.desc())
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
                    .innerJoin(HrHbScratchCard.HR_HB_SCRATCH_CARD)
                    .on(HrHbItems.HR_HB_ITEMS.ID.eq(HrHbScratchCard.HR_HB_SCRATCH_CARD.HB_ITEM_ID))
                    .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.in(wxUserIdList))
                    .and((HrHbScratchCard.HR_HB_SCRATCH_CARD.CREATE_TIME.gt(HB_START_TIME)))
                    .fetchOne().value1();
        } else {
            return 0;
        }
    }

    public double sumRedPacketsByWxUserIdList(List<Integer> wxUserIdList) {
        if (wxUserIdList != null && wxUserIdList.size() > 0) {
            Record1<BigDecimal> bigDecimalRecord1 = create.select(sum(HrHbItems.HR_HB_ITEMS.AMOUNT))
                    .from(HrHbItems.HR_HB_ITEMS)
                    .innerJoin(HrHbScratchCard.HR_HB_SCRATCH_CARD)
                    .on(HrHbItems.HR_HB_ITEMS.ID.eq(HrHbScratchCard.HR_HB_SCRATCH_CARD.HB_ITEM_ID))
                    .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.in(wxUserIdList))
                    .and((HrHbScratchCard.HR_HB_SCRATCH_CARD.CREATE_TIME.gt(HB_START_TIME)))
                    .fetchOne();
            if(bigDecimalRecord1 !=null && bigDecimalRecord1.value1() !=null) {
                return bigDecimalRecord1.value1().doubleValue();
            }
        } else {
            return 0;
        }
        return 0;
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> getHbItemsListBybindingIdList(List<Integer> bindingList,int wxUserId) {
        List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> list = create.selectFrom(HrHbItems.HR_HB_ITEMS)
                .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.eq(wxUserId)).and(HrHbItems.HR_HB_ITEMS.BINDING_ID.in(bindingList))
                .fetchInto(com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems.class);
        return list;
    }

    public double sumOpenedRedPacketsByWxUserIdList(List<Integer> wxUserIdList, int companyId) {
        if (wxUserIdList != null && wxUserIdList.size() > 0) {
            Record1<BigDecimal> bigDecimalRecord1 = create.select(sum(HrHbItems.HR_HB_ITEMS.AMOUNT))
                    .from(HrHbItems.HR_HB_ITEMS)
                    .innerJoin(HrHbScratchCard.HR_HB_SCRATCH_CARD)
                    .on(HrHbItems.HR_HB_ITEMS.ID.eq(HrHbScratchCard.HR_HB_SCRATCH_CARD.HB_ITEM_ID))
                    .innerJoin(HrHbConfig.HR_HB_CONFIG)
                    .on(HrHbItems.HR_HB_ITEMS.HB_CONFIG_ID.eq(HrHbConfig.HR_HB_CONFIG.ID))
                    .where(HrHbItems.HR_HB_ITEMS.WXUSER_ID.in(wxUserIdList))
                    .and((HrHbScratchCard.HR_HB_SCRATCH_CARD.CREATE_TIME.gt(HB_START_TIME)))
                    .and(HrHbScratchCard.HR_HB_SCRATCH_CARD.STATUS.eq(1))
                    .and(HrHbConfig.HR_HB_CONFIG.COMPANY_ID.eq(companyId))
                    .fetchOne();
            if(bigDecimalRecord1 !=null && bigDecimalRecord1.value1() !=null) {
                return bigDecimalRecord1.value1().doubleValue();
            }
        } else {
            return 0;
        }
        return 0;
    }
}
