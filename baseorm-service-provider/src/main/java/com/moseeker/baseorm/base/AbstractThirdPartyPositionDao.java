package com.moseeker.baseorm.base;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import org.jooq.UpdatableRecord;
import org.jooq.impl.TableImpl;

import java.util.List;

public abstract class AbstractThirdPartyPositionDao<S, R extends UpdatableRecord<R>> extends JooqCrudImpl<S, R> implements IThirdPartyPositionDao<S>{

    public AbstractThirdPartyPositionDao(TableImpl<R> table, Class<S> sClass) {
        super(table, sClass);
    }

    /**
     * 默认的是调用dao底层的updateData
     * @param s
     * @return
     */
    @Override
    public int updateExtPosition(S s) {
        return updateData(s);
    }

    /**
     * 默认的是调用dao底层的updateDatas
     * @param list 第三方职位-附表数据
     * @return
     */
    @Override
    public int[] updateExtPositions(List<S> list) {
        return updateDatas(list);
    }
}
