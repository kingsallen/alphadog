package com.moseeker.position.service.position.base.config;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.position.constants.CheckStrategy;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ATSTransferCheckConfig extends AbstractTransferCheckConfig {
    private static final Map<ChannelType, Map<String, Map<CheckStrategy, String>>> strategy=new HashMap<>();

    @Override
    public Map<ChannelType, Map<String, Map<CheckStrategy, String>>> getStrategy() {
        if(!strategy.isEmpty()){
            return strategy;
        }

        strategy.putAll(
                buildStrategy(ChannelType.JOB51)
                .field(ThirdPartyPosition._Fields.COMPANY_NAME.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field(ThirdPartyPosition._Fields.OCCUPATION.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field(ThirdPartyPosition._Fields.ADDRESS_NAME.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .add());

        strategy.putAll(
                buildStrategy(ChannelType.LIEPIN)
                .field(ThirdPartyPosition._Fields.DEPARTMENT_NAME.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field(ThirdPartyPosition._Fields.OCCUPATION.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .add());

        strategy.putAll(
                buildStrategy(ChannelType.ZHILIAN)
                .field(ThirdPartyPosition._Fields.COMPANY_NAME.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field(ThirdPartyPosition._Fields.OCCUPATION.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field(ThirdPartyPosition._Fields.ADDRESS_NAME.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .add());

        strategy.putAll(
                buildStrategy(ChannelType.VERYEAST)
                .field(ThirdPartyPosition._Fields.COMPANY_NAME.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field(ThirdPartyPosition._Fields.OCCUPATION.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field("indate").strategy(CheckStrategy.REQUIRED).finish()
                .add());

        strategy.putAll(
                buildStrategy(ChannelType.JOB1001)
                .field(ThirdPartyPosition._Fields.COMPANY_NAME.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field(ThirdPartyPosition._Fields.OCCUPATION.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field(ThirdPartyPosition._Fields.ADDRESS_NAME.getFieldName()).strategy(CheckStrategy.REQUIRED).finish()
                .field("subsite").strategy(CheckStrategy.REQUIRED).finish()
                .add());

        return strategy;
    }

    @Override
    public SyncRequestType getSyncRequestType() {
        return SyncRequestType.ATS;
    }
}
