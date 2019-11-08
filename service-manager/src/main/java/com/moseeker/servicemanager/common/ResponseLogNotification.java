package com.moseeker.servicemanager.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ResponseLogNotification {

    private final static int appid = 0;
    private final static String logkey = "LOG";
    private final static String eventkey = "RESTFUL_API_ERROR";
    private static Logger logger = LoggerFactory.getLogger(ResponseLogNotification.class);


    public static String success(HttpServletRequest request, Response response) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        try {
            String jsonresponse = JSON.toJSONString(CleanJsonResponseWithParse.convertFrom(response));
            logRequestResponse(request, jsonresponse);
            return jsonresponse;
        } catch (Exception e) {
            logger.error("controller return response error, url:{}, method:{}, reason:{}", url, method, e);
        }
        return ConstantErrorCodeMessage.PROGRAM_EXCEPTION;

    }

    public static String successJson(HttpServletRequest request, Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "success");
        result.put("status", 0);
        result.put("data", data);
        //转换json的时候去掉thrift结构体中的set方法
        return BeanUtils.convertStructToJSON(result);
    }

    public static String failJson(HttpServletRequest request, String message) {
        return failJson(request, message, null);
    }

    public static String failJson(HttpServletRequest request, String message, Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", message);
        result.put("status", 1);
        result.put("data", data);
        //转换json的时候去掉thrift结构体中的set方法
        return BeanUtils.convertStructToJSON(result);
    }

    public static String failJson(HttpServletRequest request, int status, String message, Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", message);
        result.put("status", status);
        result.put("data", data);
        //转换json的时候去掉thrift结构体中的set方法
        return BeanUtils.convertStructToJSON(result);
    }

    public static String failJson(HttpServletRequest request, Exception e) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        Map<String, Object> result = new HashMap<>();
        if (e instanceof CommonException) {
            result.put("status", ((CommonException) e).getCode());
            result.put("message", e.getMessage());
        } else if (e instanceof BIZException) {
            result.put("status", ((BIZException) e).getCode());
            result.put("message", e.getMessage());
        } else {
            result.put("status", 1);
            result.put("message", "发生异常，请稍候再试!");
        }
        //转换json的时候去掉thrift结构体中的set方法
        logger.error("Controller failJson error, url:{}, method:{}, reason:{}", url, method, e);
        return BeanUtils.convertStructToJSON(result);
    }

    public static String fail(HttpServletRequest request, Response response) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        try {
            String jsonresponse = JSON.toJSONString(CleanJsonResponse.convertFrom(response));
            logRequestResponse(request, jsonresponse);
            return jsonresponse;
        } catch (Exception e) {
            logger.error("Controller response error, url:{}, method:{}, message:{}, reason:{}", url, method, e.getMessage(), e);
        }
        return ConstantErrorCodeMessage.PROGRAM_EXCEPTION;

    }

    public static String fail(HttpServletRequest request, Response response, Exception ex) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        try {
            String jsonresponse = JSON.toJSONString(CleanJsonResponse.convertFrom(response));
            logRequestResponse(request, jsonresponse);
            int appid = 0;
            if (request.getParameter("appid") != null) {
                appid = Integer.parseInt(request.getParameter("appid"));
            }
            logger.error("Controller error, url:{}, method:{}, message:{}", url, method, JSON.toJSONString(response));
            //Notification.sendNotification(appid, eventkey, response.getMessage());
            return jsonresponse;
        } catch (Exception e) {
            logger.error("Controller response error, url:{}, method:{}, message:{}, reason:{}", url, method, e.getMessage(), e);
        }
        return ConstantErrorCodeMessage.PROGRAM_EXCEPTION;

    }

    /*public static String fail(HttpServletRequest request, String message) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        try {
            Response response = new Response();
            response.setStatus(1);
            response.setMessage(message);
            String jsonresponse = JSON.toJSONString(CleanJsonResponse.convertFrom(response));
            logRequestResponse(request, jsonresponse);
            return jsonresponse;
        } catch (Exception e) {
            logger.error("controller response error, url:{}, method:{}, reason:", url, method, e);
        }
        return ConstantErrorCodeMessage.PROGRAM_EXCEPTION;

    }*/

    /**
     * 全局error log整理
     *
     * @param request
     * @param ex
     * @return
     */
    public static String fail(HttpServletRequest request, Exception ex) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        try {
            Response response = new Response();
            response.setStatus(1);
            response.setMessage(ex.getMessage());
            String jsonresponse = JSON.toJSONString(CleanJsonResponse.convertFrom(response));
            logRequestResponse(request, jsonresponse);
            int appid = 0;
            if (request.getParameter("appid") != null) {
                appid = Integer.parseInt(request.getParameter("appid"));
            }
            //进入到fail()方法，所有日志应该为error
            logger.error("controller error, url:{}, method:{}, reason:", url, method, ex);
            //Notification.sendNotification(appid, eventkey, response.getMessage());
            return jsonresponse;
        } catch (Exception e) {
            logger.error("controller response error, url:{}, method:{}, reason:", url, method, e);
        }
        return ConstantErrorCodeMessage.PROGRAM_EXCEPTION;

    }

    public static String failResponse(HttpServletRequest request, String message) {
        try {
            Response response = JSONObject.parseObject(message, Response.class);
            String jsonresponse = JSON.toJSONString(CleanJsonResponse.convertFrom(response));
            logRequestResponse(request, jsonresponse);
            int appid = 0;
            if (request.getParameter("appid") != null) {
                appid = Integer.parseInt(request.getParameter("appid"));
            }
            logger.info(JSON.toJSONString(response));
            //Notification.sendNotification(appid, eventkey, response.getMessage());
            return jsonresponse;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ConstantErrorCodeMessage.PROGRAM_EXCEPTION;

    }

    private static void logRequestResponse(HttpServletRequest request, String response) {
        try {
            ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
            propertiesUtils.loadResource("setting.properties");
            boolean switchLogRedis = propertiesUtils.get("switch_log_redis", Boolean.class);
            if (switchLogRedis) {
                Map<String, Object> reqResp = new HashMap<>();
                reqResp.put("appid", request.getParameter("appid"));
                reqResp.put("request", request.getParameterMap());
                reqResp.put("response", response);
                reqResp.put("remote_ip", ParamUtils.getRemoteIp(request));
                reqResp.put("web_server_ip", ParamUtils.getLocalHostIp());

            }
        } catch (RedisException e) {
            WarnService.notify(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //logger.info("下线日志记录功能");
    }

    /**
     *  为alphacloud提供接口成功调用返回值
     * @param data
     * @return
     */
    public static CleanJsonResponse4Alphacloud success4Alphacloud(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "success");
        result.put("code", "0");
        result.put("data", data);
        //转换json的时候去掉thrift结构体中的set方法
        return CleanJsonResponse4Alphacloud.convertFrom(result);
    }

    /**
     *  为alphacloud提供接口失败调用返回值
     * @param data
     * @return
     */
    public static CleanJsonResponse4Alphacloud fail4Alphacloud(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "fail");
        result.put("code", "1");
        result.put("data", data);
        //转换json的时候去掉thrift结构体中的set方法
        return CleanJsonResponse4Alphacloud.convertFrom(result);
    }

}
