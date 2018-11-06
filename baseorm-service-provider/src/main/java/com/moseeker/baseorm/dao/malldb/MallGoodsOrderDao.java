package com.moseeker.baseorm.dao.malldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.malldb.tables.records.MallOrderRecord;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderDO;
import com.moseeker.thrift.gen.mall.struct.MallGoodsOrderUpdateForm;
import com.moseeker.thrift.gen.mall.struct.OrderSearchForm;
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
                .fetchOne(0, int.class);
    }

    public List<MallOrderDO> getOrdersListByPage(int companyId, int startIndex, int pageSize) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .orderBy(MALL_ORDER.CREATE_TIME.desc())
                .limit(startIndex, pageSize)
                .fetchInto(MallOrderDO.class);
    }

    public List<MallOrderDO> getOrdersListByEmployeeId(int employeeId) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.EMPLOYEE_ID.eq(employeeId))
                .orderBy(MALL_ORDER.CREATE_TIME.desc())
                .fetchInto(MallOrderDO.class);
    }

    public int updateOrderStateByIdAndCompanyId(MallGoodsOrderUpdateForm updateForm) {
        return create.update(MALL_ORDER)
                .set(MALL_ORDER.STATE, (byte)updateForm.getState())
                .where(MALL_ORDER.ID.in(updateForm.getIds()))
                .and(MALL_ORDER.COMPANY_ID.eq(updateForm.getCompany_id()))
                .execute();
    }

    public List<MallOrderDO> getOrdersByIds(List<Integer> ids) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.ID.in(ids))
                .orderBy(MALL_ORDER.CREATE_TIME.desc())
                .fetchInto(MallOrderDO.class);
    }

    public int getTotalRowsByCompanyIdAndState(int companyId, byte state) {
        return create.selectCount()
                .from(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .and(MALL_ORDER.STATE.eq(state))
                .fetchOne(0, int.class);
    }

    public List<MallOrderDO> getOrdersListByPageAndState(int companyId, byte state, int startIndex, int pageSize) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .and(MALL_ORDER.STATE.eq(state))
                .orderBy(MALL_ORDER.CREATE_TIME.desc())
                .limit(startIndex, pageSize)
                .fetchInto(MallOrderDO.class);
    }

    public int getTotalRowsByCompanyIdAndKeyword(int companyId, String keyWord) {
        return create.selectCount()
                .from(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .and(MALL_ORDER.TITLE.like(keyWord))
                .or(MALL_ORDER.NAME.like(keyWord))
                .fetchOne(0, int.class);
    }

    public List<MallOrderDO> getOrdersListByPageAndKeyword(OrderSearchForm orderSearchForm, int startIndex) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(orderSearchForm.getCompany_id()))
                .and(MALL_ORDER.TITLE.like(orderSearchForm.getKeyword()))
                .or(MALL_ORDER.NAME.like(orderSearchForm.getKeyword()))
                .orderBy(MALL_ORDER.CREATE_TIME.desc())
                .limit(startIndex, orderSearchForm.getPage_size())
                .fetchInto(MallOrderDO.class);
    }

    public int getTotalRowsByCompanyIdAndStateAndKeyword(int companyId, byte state, String keyWord) {
        return create.selectCount()
                .from(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .and(MALL_ORDER.STATE.eq(state))
                .and(MALL_ORDER.TITLE.like(keyWord))
                .or(MALL_ORDER.NAME.like(keyWord))
                .fetchOne(0, int.class);
    }

    public List<MallOrderDO> getOrdersListByPageAndStateAndKeyword(OrderSearchForm orderSearchForm, int startIndex) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(orderSearchForm.getCompany_id()))
                .and(MALL_ORDER.STATE.eq(orderSearchForm.getState()))
                .and(MALL_ORDER.TITLE.like(orderSearchForm.getKeyword()))
                .or(MALL_ORDER.NAME.like(orderSearchForm.getKeyword()))
                .orderBy(MALL_ORDER.CREATE_TIME.desc())
                .limit(startIndex, orderSearchForm.getPage_size())
                .fetchInto(MallOrderDO.class);
    }

    public List<MallOrderDO> getAllOrderByCompanyId(int companyId) {
        return create.selectFrom(MALL_ORDER)
                .where(MALL_ORDER.COMPANY_ID.eq(companyId))
                .orderBy(MALL_ORDER.CREATE_TIME.desc())
                .fetchInto(MallOrderDO.class);
    }
}
