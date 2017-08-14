package com.moseeker.thrift.gen.common.struct;

/**
 * Created by jack on 10/07/2017.
 */
public class SysBIZException extends BIZException {
    public SysBIZException() {
        code = 99999;
        message = "系统异常！";
    }
}
