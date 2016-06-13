package com.moseeker.servicemanager.common;

import com.alibaba.fastjson.JSON;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * Created by chendi on 5/23/16.
 */
public class CleanJsonResponseWithParse {
    public int status; // required
    public String message; // required
    public Object data; // optional

    private CleanJsonResponseWithParse(int status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static CleanJsonResponseWithParse convertFrom(Response resp){
        if (resp != null){
            return new CleanJsonResponseWithParse(resp.getStatus(),resp.getMessage(), JSON.parse(resp.getData()));
        }
        return null;
    }
}
