package com.moseeker.position.service.third.base;

import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;

public interface JsonThirdPartyInfoProvider {
    public String provide(ThirdPartyAccountInfoParam param) throws Exception;
}
