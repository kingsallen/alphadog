package com.moseeker.position.service.position.job1001;

import com.moseeker.position.service.position.job1001.refresh.handler.AbstractYLParamRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DTParamRefresher extends AbstractYLParamRefresher {
    private Logger logger= LoggerFactory.getLogger(DTParamRefresher.class);

    @Override
    public String getConfigKey() {
        return "dt";
    }
}
