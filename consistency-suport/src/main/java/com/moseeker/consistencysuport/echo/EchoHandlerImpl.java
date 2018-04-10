package com.moseeker.consistencysuport.echo;

import com.moseeker.consistencysuport.exception.ConsistencyException;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jack on 10/04/2018.
 */
public class EchoHandlerImpl implements EchoHandler {

    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void handlerMessage(String message) throws ConsistencyException {

        latch.countDown();
    }
}
