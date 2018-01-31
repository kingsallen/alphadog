package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.base.IThirdPartyPositionDao;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DefaultThirdPartyPositionDao implements IThirdPartyPositionDao<EmptyExtThirdPartyPosition> {

    @Override
    public TwoParam<HrThirdPartyPositionDO, EmptyExtThirdPartyPosition> getData(HrThirdPartyPositionDO thirdPartyPositionDO) {
        if(isEmptyPosition(thirdPartyPositionDO)) {
            return null;
        }
        return new TwoParam<>(thirdPartyPositionDO,EmptyExtThirdPartyPosition.EMPTY);
    }

    @Override
    public List<TwoParam<HrThirdPartyPositionDO, EmptyExtThirdPartyPosition>> getDatas(List<HrThirdPartyPositionDO> list) {
        if(isEmptyList(list)){
            return new ArrayList<>();
        }
        return list.stream().map(p->new TwoParam<>(p,EmptyExtThirdPartyPosition.EMPTY)).collect(Collectors.toList());
    }

    @Override
    public TwoParam<HrThirdPartyPositionDO, EmptyExtThirdPartyPosition> addData(HrThirdPartyPositionDO thirdPartyPositionDO, EmptyExtThirdPartyPosition emptyExtThirdPartyPosition) {
        if(isEmptyPosition(thirdPartyPositionDO)) {
            return null;
        }
        return new TwoParam<>(thirdPartyPositionDO,EmptyExtThirdPartyPosition.EMPTY);
    }

    @Override
    public int updateData(EmptyExtThirdPartyPosition emptyExtThirdPartyPosition) {
        return 1;
    }

    @Override
    public int[] updateDatas(List<EmptyExtThirdPartyPosition> list) {
        int result[] =new int[list.size()];
        Arrays.fill(result,1);
        return result;
    }

    @Override
    public EmptyExtThirdPartyPosition setId(HrThirdPartyPositionDO thirdPartyPositionDO, EmptyExtThirdPartyPosition emptyExtThirdPartyPosition) {
        return emptyExtThirdPartyPosition;
    }

    @Override
    public ChannelType getChannelType() {
        return null;
    }
}
