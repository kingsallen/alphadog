package com.moseeker.position.service.position.carnoc.refresh.handler;

import com.moseeker.position.service.position.base.refresh.handler.AbstractRegionResultHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import org.springframework.stereotype.Component;

@Component
public class CarnocRegionResultHandler extends AbstractRegionResultHandler implements CarnocResultHandlerAdapter {

    @Override
    protected void setCodeOther(DictCityMapDO cityMap, Region thirdPartyRegion) {
        cityMap.setCodeOther(thirdPartyRegion.textToString());
    }
}
