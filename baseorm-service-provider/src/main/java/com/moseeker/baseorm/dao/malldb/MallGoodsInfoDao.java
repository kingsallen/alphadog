package com.moseeker.baseorm.dao.malldb;

import com.google.common.collect.Lists;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.malldb.Tables;
import com.moseeker.baseorm.db.malldb.tables.records.MallGoodsInfoRecord;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import com.moseeker.thrift.gen.mall.struct.MallGoodsInfoForm;
import org.jooq.InsertQuery;
import org.jooq.UpdateQuery;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public int editGood(MallGoodsInfoForm mallGoodsInfoForm) {
        return create.update(MALL_GOODS_INFO)
                .set(MALL_GOODS_INFO.PIC_URL, mallGoodsInfoForm.getPic_url())
                .set(MALL_GOODS_INFO.TITLE, mallGoodsInfoForm.getTitle())
                .set(MALL_GOODS_INFO.CREDIT, mallGoodsInfoForm.getCredit())
                .set(MALL_GOODS_INFO.DETAIL, mallGoodsInfoForm.getDetail())
                .set(MALL_GOODS_INFO.RULE, mallGoodsInfoForm.getRule())
                .where(MALL_GOODS_INFO.ID.eq(mallGoodsInfoForm.getId()))
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
                .orderBy(MALL_GOODS_INFO.STATE.desc(), MALL_GOODS_INFO.UPDATE_TIME.desc())
                .limit(startIndex, pageSize)
                .fetchInto(MallGoodsInfoDO.class);
    }

    public List<MallGoodsInfoDO> getGoodsListByPageAndState(int companyId, int startIndex, int pageSize, int state) {
        return create.selectFrom(MALL_GOODS_INFO)
                .where(MALL_GOODS_INFO.COMPANY_ID.eq(companyId))
                .and(MALL_GOODS_INFO.STATE.eq((byte)state))
                .orderBy(MALL_GOODS_INFO.STATE.desc(), MALL_GOODS_INFO.UPDATE_TIME.desc())
                .limit(startIndex, pageSize)
                .fetchInto(MallGoodsInfoDO.class);
    }

    /**
     * 下单时，商品已兑换次数+1，已兑换数量+兑换数，扣库存
     * @param mallGoodsInfoDO 当前商品信息
     * @param count 兑换数量
     * @param state  商品上下架状态
     * @author  cjm
     * @date  2018/10/21
     * @return 是否更新成功
     */
    public int updateStockAndExchangeNum(MallGoodsInfoDO mallGoodsInfoDO, int count, int state) {
        return create.update(MALL_GOODS_INFO)
                .set(MALL_GOODS_INFO.STOCK, mallGoodsInfoDO.getStock() + count)
                .set(MALL_GOODS_INFO.EXCHANGE_ORDER, mallGoodsInfoDO.getExchange_order() + 1)
                .set(MALL_GOODS_INFO.EXCHANGE_NUM, mallGoodsInfoDO.getExchange_num() + Math.abs(count))
                .where(MALL_GOODS_INFO.STOCK.eq(mallGoodsInfoDO.getStock()))
                .and(MALL_GOODS_INFO.STATE.eq((byte)state))
                .execute();
    }

    public List<MallGoodsInfoRecord> getGoodsListByIds(List<Integer> goodsId) {
        return create.selectFrom(MALL_GOODS_INFO)
                .where(MALL_GOODS_INFO.ID.in(goodsId))
                .fetchInto(MallGoodsInfoRecord.class);
    }

    /**
     * 订单在批量拒绝时将库存、兑换数量、兑换次数返还
     * @param orderList 订单list
     * @param idGoodsMap id/商品 map
     * @author  cjm
     * @date  2018/10/22
     * @return 执行结果，0 执行失败 1 执行成功
     */
    public int[] batchUpdateGoodInfo(List<MallOrderDO> orderList, Map<Integer, MallGoodsInfoRecord> idGoodsMap) {
        List<UpdateQuery<MallGoodsInfoRecord>> batchUpdate = new ArrayList<>();
        for(MallOrderDO mallOrderDO : orderList){
            MallGoodsInfoRecord record  = idGoodsMap.get(mallOrderDO.getGoods_id());
            UpdateQuery<MallGoodsInfoRecord> tempQuery = create.updateQuery(Tables.MALL_GOODS_INFO);
            tempQuery.addConditions(MALL_GOODS_INFO.ID.eq(record.getId()), MALL_GOODS_INFO.STOCK.eq(record.getStock()));
            tempQuery.addValue(MALL_GOODS_INFO.EXCHANGE_NUM, record.getExchangeNum() - mallOrderDO.getCount());
            tempQuery.addValue(MALL_GOODS_INFO.EXCHANGE_ORDER, record.getExchangeOrder() - 1);
            tempQuery.addValue(MALL_GOODS_INFO.STOCK, record.getStock() + mallOrderDO.getCount());
            batchUpdate.add(tempQuery);
        }
        return create.batch(batchUpdate).execute();
    }
}
