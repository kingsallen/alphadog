package com.moseeker.position.service.position.job58;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.utils.HttpClientUtil;
import com.moseeker.position.utils.Md5Utils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * @author cjm
 * @date 2018-11-22 20:49
 **/
@Component
public class Job58RequestHandler<T> {

    @Autowired
    private Environment env;

    @Autowired
    private HttpClientUtil httpClientUtil;

    public JSONObject sendRequest(T t, String url) throws Exception {
        JSONObject requestMap = JSONObject.parseObject(JSON.toJSONString(t));
        String secret = getChannelSecret();
        List<String> keyList = Md5Utils.mapToList(requestMap);
        String sign = Md5Utils.getMD5SortKeyWithEqual(secret, keyList, requestMap);
        requestMap.put("sig", sign);
//        HttpClientUtil httpClientUtil = new HttpClientUtil();
        String response = httpClientUtil.sendRequest2Job58(url, requestMap);
        if(StringUtils.isNullOrEmpty(response)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HTTP_REQUEST_FAILED);
        }
        return JSONObject.parseObject(response);
    }

    protected String getChannelSecret() {
//        return "53d91dccb8283f86a621c0866a5be5c2";
        return env.getProperty("58job_api_secret");
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

    public String map2xml(Map<String, String> map) throws IOException {
        Document d = DocumentHelper.createDocument();
        Element root = d.addElement("paras");
        Set<String> keys = map.keySet();
        for (String key : keys) {
            root.addElement("param").addAttribute("name", key).addAttribute("value", map.get(key));
        }
        StringWriter sw = new StringWriter();
        XMLWriter xw = new XMLWriter(sw);
        xw.setEscapeText(false);
        xw.write(d);
        return sw.toString();
    }

}
