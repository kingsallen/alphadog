package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.useraccounts.service.impl.vo.DefaultBindProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cjm
 * @date 2018-11-28 10:03
 **/
@Component
public class BindProcessorFactory {

    @Autowired
    List<AbstractBindProcessor> bindProcessors;

    @Autowired
    DefaultBindProcessor defaultBindProcessor;

    public AbstractBindProcessor getBindProcessorByChannel(int channel){
        for(AbstractBindProcessor bindProcessor : bindProcessors){
            if(bindProcessor.getChannelType().getValue() == channel){
                return bindProcessor;
            }
        }
        return defaultBindProcessor;
    }
}
