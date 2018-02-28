package com.moseeker.position.thrift;

import com.moseeker.position.service.fundationbs.PositionATSService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.ATSPositionServices;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ATSPositionServicesImpl implements ATSPositionServices.Iface{

    @Autowired
    PositionATSService positionATSService;

    @Override
    public Response insertGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        return null;
    }

    @Override
    public Response updateGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        return null;
    }
}
