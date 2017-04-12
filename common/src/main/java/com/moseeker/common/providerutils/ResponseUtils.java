package com.moseeker.common.providerutils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.ParamNullException;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.demo.struct.DemoStruct;

/**
 * 处理数据格式转换
 */
public class ResponseUtils {

    /**
     * 处理成功响应的数据格式
     *
     * @param hashmap 需要传入一个HashMap类型, TODO: 对其他对象类型支持
     * @return
     */
    public static Response success(Object hashmap) {

        Response response = new Response();
        response.setStatus(0);
        response.setMessage("success");
        //JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        //response.setData(JSON.toJSONString(hashmap, SerializerFeature.WriteDateUseDateFormat));
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
     *
     * @param constantErrorCodeMessage json格式的String字符串"{}", 否则会抛异常
     *                                 eg: syntax error, dao 1, json : JobApplication failed
     * @return
     * @throws ParamNullException
     */
    public static Response fail(String constantErrorCodeMessage) throws ParamNullException {
        Response response = new Response();
        if (StringUtils.isNullOrEmpty(constantErrorCodeMessage)) {
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
        if (StringUtils.isNullOrEmpty(constantErrorCodeMessage)) {
            throw new ParamNullException();
        }
        JSONObject jsonObject = JSONObject.parseObject(constantErrorCodeMessage);
        response.setData(JSON.toJSONString(hashmap));
        response.setStatus(jsonObject.getIntValue("status"));
        response.setMessage(jsonObject.getString("message"));
        return response;
    }

    public static Response fail(int status, String message) throws ParamNullException {
        Response response = new Response();
        if (StringUtils.isNullOrEmpty(message)) {
            throw new ParamNullException();
        }
        response.setData(Constant.NONE_JSON);
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }

    /**
     * object里面包含TBase或其子类的使用这个方法
     *
     * @param object
     * @return
     */
    public static String successStructJson(Object object) {
        Map<String, Object> data = new HashMap();
        data.put("status", 0);
        data.put("message", "success");
        data.put("data", object);
        return BeanUtils.convertStructToJSON(data);
    }

    public static String successJson() {
        Map<String, Object> data = new HashMap();
        data.put("status", 0);
        data.put("message", "success");
        return BeanUtils.toJson(data);
    }

    public static String successJson(Object object) {
        Map<String, Object> data = new HashMap();
        data.put("status", 0);
        data.put("message", "success");
        data.put("data", object);
        return BeanUtils.toJson(data);
    }

    /**
     * object里面包含TBase或其子类的使用这个方法
     *
     * @param object
     * @return
     */
    public static String failStructJson(String constantErrorCodeMessage, Object object) {
        JSONObject jsonObject = JSONObject.parseObject(constantErrorCodeMessage);
        Map<String, Object> data = new HashMap();
        data.put("status", jsonObject.getIntValue("status"));
        data.put("message", jsonObject.getString("message"));
        data.put("data", object);
        return BeanUtils.convertStructToJSON(data);
    }

    public static String failJson(String constantErrorCodeMessage) {
        JSONObject jsonObject = JSONObject.parseObject(constantErrorCodeMessage);
        Map<String, Object> data = new HashMap();
        data.put("status", jsonObject.getIntValue("status"));
        data.put("message", jsonObject.getString("message"));
        return BeanUtils.toJson(data);
    }

    public static String failJson(String constantErrorCodeMessage, Object object) {
        JSONObject jsonObject = JSONObject.parseObject(constantErrorCodeMessage);
        Map<String, Object> data = new HashMap();
        data.put("status", jsonObject.getIntValue("status"));
        data.put("message", jsonObject.getString("message"));
        data.put("data", object);
        return BeanUtils.toJson(data);
    }

    public static String failJson(int status, String message) throws ParamNullException {
        Map<String, Object> data = new HashMap();
        data.put("status", status);
        data.put("message", message);
        data.put("data", Constant.NONE_JSON);
        return BeanUtils.toJson(data);
    }
}
