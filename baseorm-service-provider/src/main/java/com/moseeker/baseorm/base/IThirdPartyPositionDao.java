package com.moseeker.baseorm.base;

import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;

import java.util.List;

public interface IThirdPartyPositionDao<P> {
    TwoParam<HrThirdPartyPositionDO,P> getData(HrThirdPartyPositionDO thirdPartyPositionDO);
    List<TwoParam<HrThirdPartyPositionDO,P>> getDatas(List<HrThirdPartyPositionDO> list);
    TwoParam<HrThirdPartyPositionDO,P> addData(HrThirdPartyPositionDO thirdPartyPositionDO,P p);
    int updateData(P p);
    int[] updateDatas(List<P> list);

    P setId(HrThirdPartyPositionDO thirdPartyPositionDO,P p);

    default boolean isEmptyPosition(HrThirdPartyPositionDO thirdPartyPositionDO){
        return thirdPartyPositionDO==null || thirdPartyPositionDO.getId()==0;
    }

    default <E> boolean isEmptyList(List<E> list){
        return list==null || list.isEmpty();
    }
}
