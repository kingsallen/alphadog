package com.moseeker.common.providerutils;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.exception.ParamNullException;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 处理数据格式转换
 *
 */
public class ResponseUtils {

    /**
     * 处理成功响应的数据格式
     * @param hashmap 需要传入一个HashMap类型, TODO: 对其他对象类型支持
     * @return
     */
    public static Response success(Object hashmap) {

        Response response = new Response();
        response.setStatus(0);
        response.setMessage("success");
        response.setData(JSON.toJSONString(hashmap));
        return response;

    }


    public static Response successWithoutStringify(String hashmap) {
        Response response = new Response();
        response.setStatus(0);
        response.setMessage("success");
        response.setData(hashmap);
        return response;
    }

    /**
     * 处理失败响应的数据格式
     * @param constantErrorCodeMessage json格式的String字符串"{}", 否则会抛异常
     *                                 eg: syntax error, pos 1, json : JobApplication failed
     * @return
     * @throws ParamNullException
     */
    public static Response fail(String constantErrorCodeMessage) throws ParamNullException {
        Response response = new Response();
        if(StringUtils.isNullOrEmpty(constantErrorCodeMessage)) {
            throw new ParamNullException();
        }
        JSONObject jsonObject = JSONObject.parseObject(constantErrorCodeMessage);
        response.setData(Constant.NONE_JSON);
        response.setStatus(jsonObject.getIntValue("status"));
        response.setMessage(jsonObject.getString("message"));
        return response;
    }

    public static Response fail(String constantErrorCodeMessage, Map<String, Object> hashmap) {
        Response response = new Response();
        if(StringUtils.isNullOrEmpty(constantErrorCodeMessage)) {
            throw new ParamNullException();
        }
        JSONObject jsonObject = JSONObject.parseObject(constantErrorCodeMessage);
        response.setData(JSON.toJSONString(hashmap));
        response.setStatus(jsonObject.getIntValue("status"));
        response.setMessage(jsonObject.getString("message"));
        return response;
    }
}
