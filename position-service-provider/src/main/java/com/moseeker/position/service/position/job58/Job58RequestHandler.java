package com.moseeker.position.service.position.job58;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.constants.position.job58.Job58PositionOperateConstant;
import com.moseeker.position.constants.position.job58.Job58ResponseCode;
import com.moseeker.position.utils.HttpClientUtil;
import com.moseeker.position.utils.Md5Utils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.*;

/**
 * @author cjm
 * @date 2018-11-22 20:49
 **/
@Component
public class Job58RequestHandler<T> {

    private Logger logger = LoggerFactory.getLogger(Job58RequestHandler.class);

    @Autowired
    private HttpClientUtil httpClientUtil;

    public JSONObject sendRequest(T t, String url) throws Exception {
        JSONObject requestMap = JSONObject.parseObject(JSON.toJSONString(t));
        List<String> keyList = Md5Utils.mapToList(requestMap);
        String sign = Md5Utils.getMD5SortKeyWithEqual(Job58PositionOperateConstant.job58SecretKey, keyList, requestMap);
        requestMap.put("sig", sign);
        String response = httpClientUtil.sendRequest2Job58(url, requestMap);
        if(StringUtils.isNullOrEmpty(response)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HTTP_REQUEST_FAILED);
        }
        return JSONObject.parseObject(response);
    }

    public Map<String, String> parseXml2Map(String xml) throws DocumentException {
        Map<String, String> map = new HashMap<>(16);
        InputSource in = new InputSource(new StringReader(xml));
        in.setEncoding("UTF-8");
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Iterator<Element> it = elements.iterator(); it.hasNext(); ) {
            Element element = it.next();
            map.put(element.getName(), element.getTextTrim());
        }
        System.out.println(JSON.toJSONString(map));
        return map;
    }

    public String map2xml(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            sb.append("<para name=\"").append(key).append("\" value=\"").append(map.get(key)).append("\"/>");
        }
        return sb.toString();
    }

    /**
     *  检验58http请求是否成功
     * @param httpResult 58返回json对象
     * @author  cjm
     * @date  2018/11/27
     */
    public void checkValidResponse(JSONObject httpResult) throws BIZException {
        Integer code = httpResult.getIntValue("code");
        if(code != 0){
            Job58ResponseCode job58ResponseCode = Job58ResponseCode.getResponseMsg(code);
            logger.info("请求失败原因:{}", httpResult.getString("message"));
            throw ExceptionUtils.getBizException("{'status':-1,'message':'" + job58ResponseCode.getQianxunMsg() + "'}");
        }
    }
}
