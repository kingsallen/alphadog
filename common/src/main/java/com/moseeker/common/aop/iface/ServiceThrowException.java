package com.moseeker.common.aop.iface;

import com.moseeker.thrift.gen.common.struct.BIZException;

import org.apache.thrift.TException;
import org.springframework.aop.ThrowsAdvice;


/**
 * Created by YYF
 *
 * Date: 2017/7/26
 *
 * Project_name :alphadog
 */
public class ServiceThrowException implements ThrowsAdvice {


    public void afterThrowing(TException e) throws BIZException {
        System.out.println("Test Spring ThrowsAdvice!");
        throw new BIZException(1, e.getMessage());
    }
}
