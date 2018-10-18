package com.moseeker.baseorm.dao.malldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.malldb.tables.records.MallGoodsInfoRecord;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.malldb.tables.MallGoodsInfo.MALL_GOODS_INFO;
/**
 * @author cjm
 * @date 2018-10-11 18:42
 **/
@Repository
public class MallGoodsInfoDao extends JooqCrudImpl<MallGoodsInfoDO, MallGoodsInfoRecord> {

    public MallGoodsInfoDao() {
        super(MALL_GOODS_INFO, MallGoodsInfoDO.class);
    }

    public MallGoodsInfoDao(TableImpl<MallGoodsInfoRecord> table, Class<MallGoodsInfoDO> mallGoodsInfoDOClass) {
        super(table, mallGoodsInfoDOClass);
    }

    public int updateStateByCompanyId(int companyId, byte state) {
        return create.update(MALL_GOODS_INFO)
                .set(MALL_GOODS_INFO.STATE, state)
                .where(MALL_GOODS_INFO.COMPANY_ID.eq(companyId))
                .execute();
    }

    public List<MallGoodsInfoDO> getGoodDetailByGoodsIdAndCompanyId(List<Integer> goodsId, int companyId) {
        return create.selectFrom(MALL_GOODS_INFO)
                .where(MALL_GOODS_INFO.ID.in(goodsId))
                .and(MALL_GOODS_INFO.COMPANY_ID.eq(companyId))
                .fetchInto(MallGoodsInfoDO.class);
    }

    public MallGoodsInfoDO getGoodDetailByGoodIdAndCompanyId(int goodId, int companyId) {
        return create.selectFrom(MALL_GOODS_INFO)
                .where(MALL_GOODS_INFO.ID.eq(goodId))
                .and(MALL_GOODS_INFO.COMPANY_ID.eq(companyId))
                .fetchOneInto(MallGoodsInfoDO.class);
    }

    public int updateGoodStock(MallGoodsInfoDO mallGoodsInfoDO, int stock) {
        return create.update(MALL_GOODS_INFO)
                .set(MALL_GOODS_INFO.STOCK, (mallGoodsInfoDO.getStock() + stock))
                .where(MALL_GOODS_INFO.ID.eq(mallGoodsInfoDO.getId()))
                .and(MALL_GOODS_INFO.STOCK.eq(mallGoodsInfoDO.getStock()))
                .execute();
    }

    public int updateGoodStateByIds(List<Integer> ids, int companyId, int state) {
        return create.update(MALL_GOODS_INFO)
                .set(MALL_GOODS_INFO.STATE, (byte)state)
                .where(MALL_GOODS_INFO.ID.in(ids))
                .and(MALL_GOODS_INFO.COMPANY_ID.eq(companyId))
                .execute();
    }

    public int editGood(MallGoodsInfoDO mallGoodsInfoDO) {
        return create.update(MALL_GOODS_INFO)
                .set(MALL_GOODS_INFO.PIC_URL, mallGoodsInfoDO.getPic_url())
                .set(MALL_GOODS_INFO.TITLE, mallGoodsInfoDO.getTitle())
                .set(MALL_GOODS_INFO.CREDIT, mallGoodsInfoDO.getCredit())
                .set(MALL_GOODS_INFO.DETAIL, mallGoodsInfoDO.getDetail())
                .set(MALL_GOODS_INFO.RULE, mallGoodsInfoDO.getRule())
                .where(MALL_GOODS_INFO.ID.eq(mallGoodsInfoDO.getId()))
                .and(MALL_GOODS_INFO.STATE.eq((byte)1))
                .execute();
    }

    public int getTotalRowsByCompanyId(int companyId) {
        return create.selectCount()
                .from(MALL_GOODS_INFO)
                .where(MALL_GOODS_INFO.COMPANY_ID.eq(companyId))
                .fetchOne(0, int.class);
    }

    public int getTotalRowsByCompanyIdAndState(int companyId, int state) {
        return create.selectCount()
                .from(MALL_GOODS_INFO)
                .where(MALL_GOODS_INFO.COMPANY_ID.eq(companyId))
                .and(MALL_GOODS_INFO.STATE.eq((byte)state))
                .fetchOne(0, int.class);
    }

    public List<MallGoodsInfoDO> getGoodsListByPage(int companyId, int startIndex, int pageSize) {
        return create.selectFrom(MALL_GOODS_INFO)
                .where(MALL_GOODS_INFO.COMPANY_ID.eq(companyId))
                .limit(startIndex, pageSize)
                .fetchInto(MallGoodsInfoDO.class);
    }

    public List<MallGoodsInfoDO> getGoodsListByPageAndState(int companyId, int startIndex, int pageSize, int state) {
        return create.selectFrom(MALL_GOODS_INFO)
                .where(MALL_GOODS_INFO.COMPANY_ID.eq(companyId))
                .and(MALL_GOODS_INFO.STATE.eq((byte)state))
                .limit(startIndex, pageSize)
                .fetchInto(MallGoodsInfoDO.class);
    }
}
