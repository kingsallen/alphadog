package com.moseeker.company.service.impl.CompanySwitchHandler;

import com.moseeker.thrift.gen.company.struct.CompanySwitchVO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCompanySwitchHandler implements CompanySwitchInterface {


    @Autowired
    protected AmqpTemplate amqpTemplate;

    public abstract void rabbitmq(CompanySwitchVO companySwitchVO);

}
