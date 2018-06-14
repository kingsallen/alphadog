package com.moseeker.position.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-05-31 8:39
 **/
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClient.class);

    public static String sentHttpPostRequest(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
        logger.info("======url:{},headers:{},params:{}===========", url, headers, params);
        //构建HttpClient实例
        HttpClient client = new DefaultHttpClient();
        //设置请求超时时间
        BufferedReader in = null;
        //指定POST请求
        try{
            HttpPost request = new HttpPost(url);
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.setHeader(entry.getKey(), entry.getValue());
                }
            }
            StringEntity paramEntity = new StringEntity(JSONObject.toJSONString(params), "utf-8");
            paramEntity.setContentEncoding("UTF-8");
            paramEntity.setContentType("application/json");
            //包装请求体
            request.setEntity(paramEntity);
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
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in != null){
                in.close();
            }
        }
        return null;
    }
}
