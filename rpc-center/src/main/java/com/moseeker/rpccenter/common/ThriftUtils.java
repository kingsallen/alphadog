package com.moseeker.rpccenter.common;

import com.moseeker.rpccenter.loadbalance.LoadBalance;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jack on 19/04/2017.
 */
public class ThriftUtils {

    private static Logger logger = LoggerFactory.getLogger(ThriftUtils.class);

    public static void closeClient(TServiceClient client) {
        if (client == null) {
            return;
        }
        try {
            TProtocol proto = client.getInputProtocol();
            if (proto != null) {
                proto.getTransport().close();
            }
        } catch (Throwable e) {
            logger.warn("close input transport fail", e);
        }
        try {
            TProtocol proto = client.getOutputProtocol();
            if (proto != null) {
                proto.getTransport().close();
            }
        } catch (Throwable e) {
            logger.warn("close output transport fail", e);
        }
    }
}
