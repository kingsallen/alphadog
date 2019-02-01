package com.moseeker.company.service.impl.CompanySwitchHandler;

import com.moseeker.thrift.gen.company.struct.CompanySwitchVO;

public abstract class AbstractCompanySwitchHandler implements CompanySwitchInterface {


    public abstract void rabbitmq(CompanySwitchVO companySwitchVO);

}
