package com.moseeker.position.utils;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.position.constants.position.liepin.LiepinPositionOperateConstant;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author cjm
 * @date 2018-05-31 8:39
 **/
@Component
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClient.class);

    public static String sentHttpPostRequest(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
        logger.info("======url:{},headers:{},params:{}===========", url, headers, params);
        //构建HttpClient实例
        HttpClient client = new DefaultHttpClient();
        //设置请求超时时间
        BufferedReader in = null;
        //指定POST请求
        try {
            HttpPost request = new HttpPost(url);
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.setHeader(entry.getKey(), entry.getValue());
                }
            }
            StringEntity paramEntity = new StringEntity(JSONObject.toJSONString(params), "utf-8");
            paramEntity.setContentType("application/json");
            paramEntity.setContentEncoding("UTF-8");
            //包装请求体
            request.setEntity(paramEntity);
            //发送请求
            HttpResponse httpResponse = client.execute(request);
            in = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer buffer = new StringBuffer("");
            String line;
            String nl = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                buffer.append(line + nl);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return null;
    }

    public static String sentHttpPostRequest(String url, Map<String, String> headers, String params) throws Exception {
        logger.info("======url:{},headers:{},params:{}===========", url, headers, params);
        //构建HttpClient实例
        HttpClient client = new DefaultHttpClient();
        //设置请求超时时间
        BufferedReader in = null;
        //指定POST请求
        try {
            HttpPost request = new HttpPost(url);
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.setHeader(entry.getKey(), entry.getValue());
                }
            }
            StringEntity paramEntity = new StringEntity(params, "utf-8");
            paramEntity.setContentType("application/json");
            paramEntity.setContentEncoding("UTF-8");
            //包装请求体
            request.setEntity(paramEntity);
            //发送请求
            HttpResponse httpResponse = client.execute(request);
            in = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer buffer = new StringBuffer("");
            String line;
            String nl = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                buffer.append(line + nl);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return null;
    }

    public String sendRequest2LiePin(JSONObject liePinJsonObject, String liePinToken, String url) throws Exception {
        // 构造请求数据
        String t = DateUtils.dateToPattern(new Date(), "yyyyMMdd");
        liePinJsonObject.put("t", t);
        String sign = Md5Utils.getMD5SortKey(Md5Utils.mapToList(liePinJsonObject), liePinJsonObject, LiepinPositionOperateConstant.liepinSecretKey);
        liePinJsonObject.put("sign", sign);
        logger.info("=============liePinJsonObject:{}=============", liePinJsonObject);
        //设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("channel", LiepinPositionOperateConstant.liepinChannel);
        headers.put("token", liePinToken);

        return sentHttpPostRequest(url, headers, liePinJsonObject);
    }

    /**
     * 验证有效的http请求结果
     *
     * @param httpResultJson json格式的返回值
     * @return jsonObject格式的返回值
     * @author cjm
     * @date 2018/7/2
     */
    public JSONObject requireValidResult(String httpResultJson) throws BIZException {

        if (StringUtils.isBlank(httpResultJson)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_RESPONSE_NULL);
        }

        logger.info("==============httpResultJson:{}================", httpResultJson);

        JSONObject httpResult = JSONObject.parseObject(httpResultJson);

        if (null == httpResult) {

            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_RESPONSE_NULL);

        } else if (httpResult.getIntValue("code") == 1002) {

            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_LIMIT);
        } else if (httpResult.getIntValue("code") != 0) {
            if (httpResult.getIntValue("code") == 1007) {
                // token失效时，只会是因为hr在猎聘修改了用户名密码，因此提示如下，将文案提示与其他渠道保持一致
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_PWD_ERROR);
            }
            throw ExceptionUtils.getBizException("{'status':-1,'message':'" + httpResult.getString("message") + "'}");
        }
        return httpResult;
    }

    public String sendRequest2Job58(String url, JSONObject params) {
        //构建HttpClient实例
        HttpClient client = new DefaultHttpClient();
        //设置请求超时时间
        BufferedReader in = null;
        //指定POST请求
        try {
            HttpPost request = new HttpPost(url);

            Set<String> keys = params.keySet();
            List<NameValuePair> formparams = new ArrayList<>();
            for (String key : keys) {
                formparams.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
            }
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            request.setEntity(entity);
            //发送请求
            HttpResponse httpResponse = client.execute(request);
            in = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer buffer = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                buffer.append(line + NL);
            }
            return buffer.toString();

        } catch (Exception e) {
            logger.error("发送请求异常:{}", e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                }catch (Exception e){
                    logger.error("关闭流异常:{}", e.getMessage());
                }

            }
        }
        return null;
    }


}
