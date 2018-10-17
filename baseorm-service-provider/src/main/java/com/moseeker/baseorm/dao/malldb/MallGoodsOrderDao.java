package com.moseeker.baseorm.dao.malldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.malldb.tables.records.MallOrderRecord;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.malldb.tables.MallOrder.MALL_ORDER;
/**
 * @author cjm
 * @date 2018-10-11 18:43
 **/
@Repository
public class MallGoodsOrderDao extends JooqCrudImpl<MallOrderDO, MallOrderRecord> {

    public MallGoodsOrderDao() {
        super(MALL_ORDER, MallOrderDO.class);
    }

    public MallGoodsOrderDao(TableImpl<MallOrderRecord> table, Class<MallOrderDO> mallOrderDOClass) {
        super(table, mallOrderDOClass);
    }

    public int getTotalRowsByCompanyId(int companyId) {
        return create.selectCount()
                .from(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .execute();
    }

    public List<MallOrderDO> getOrdersListByPage(int companyId, int startIndex, int pageSize) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .limit(startIndex, pageSize)
                .fetchInto(MallOrderDO.class);
    }

    public List<MallOrderDO> getOrdersListByEmployeeId(int employeeId) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.EMPLOYEE_ID.eq(employeeId))
                .fetchInto(MallOrderDO.class);
    }

    public int updateOrderStateByIdAndCompanyId(List<Integer> ids, int companyId, int state) {
        return create.update(MALL_ORDER)
                .set(MALL_ORDER.STATE, (byte)state)
                .where(MALL_ORDER.ID.in(ids))
                .and(MALL_ORDER.COMPANY_ID.eq(companyId))
                .execute();
    }

    public List<MallOrderDO> getOrdersByIds(List<Integer> ids) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.ID.in(ids))
                .fetchInto(MallOrderDO.class);
    }

    public int getTotalRowsByCompanyIdAndState(int companyId, byte state) {
        return create.selectCount()
                .from(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .and(MALL_ORDER.STATE.eq(state))
                .execute();
    }

    public List<MallOrderDO> getOrdersListByPageAndState(int companyId, byte state, int startIndex, int pageSize) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .and(MALL_ORDER.STATE.eq(state))
                .limit(startIndex, pageSize)
                .fetchInto(MallOrderDO.class);
    }
}
