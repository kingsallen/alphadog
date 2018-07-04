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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-05-31 8:39
 **/
@Component
public class LiepinHttpClientUtil {

    @Autowired
    private HRThirdPartyPositionDao hrThirdPartyPositionDao;

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

    public String sendRequest2LiePin(JSONObject liePinJsonObject, String liePinToken, String url) throws Exception {
        // 构造请求数据
        String t = DateUtils.dateToPattern(new Date(), "yyyyMMdd");
        liePinJsonObject.put("t", t);
        String sign = Md5Utils.getMD5SortKey(Md5Utils.mapToList(liePinJsonObject), liePinJsonObject);
        liePinJsonObject.put("sign", sign);
        logger.info("=============liePinJsonObject:{}=============", liePinJsonObject);
        //设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("channel", LiepinPositionOperateConstant.liepinChannel);
        headers.put("token", liePinToken);

        return LiepinHttpClientUtil.sentHttpPostRequest(url, headers, liePinJsonObject);
    }

    /**
     * 验证有效的http请求结果
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/7/2
     */
    public JSONObject requireValidResult(String httpResultJson, int positionId, int channel) throws BIZException {

        if (StringUtils.isBlank(httpResultJson)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_RESPONSE_NULL);
        }

        JSONObject httpResult = JSONObject.parseObject(httpResultJson);

        if (null == httpResult) {

            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_RESPONSE_NULL);

        } else if (httpResult.getIntValue("code") == 1002) {
            logger.info("==============httpResult:{}================", httpResult);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_LIMIT);
        } else if (httpResult.getIntValue("code") != 0) {
            if (httpResult.getIntValue("code") == 1007) {
                // token失效时，只会是因为hr在猎聘修改了用户名密码，因此提示如下，将文案提示与其他渠道保持一致
                String errMsg = "会员名、用户名或密码错误，请重新绑定账号";
                hrThirdPartyPositionDao.updateErrmsg(errMsg, positionId, channel, 0);
                throw ExceptionUtils.getBizException("{'status':-1,'message':'" + errMsg + "'}");
            }
            logger.info("==============httpResult:{}================", httpResult);
            throw ExceptionUtils.getBizException("{'status':-1,'message':'" + httpResult.getString("message") + "'}");
        }
        return httpResult;
    }


}
