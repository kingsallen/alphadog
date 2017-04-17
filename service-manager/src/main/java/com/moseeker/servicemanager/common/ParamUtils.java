package com.moseeker.servicemanager.common;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 主要用于生成form表单
 * <p>Company: MoSeeker</P>
 * <p>date: Aug 22, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @author wjf
 */
public class ParamUtils {

    Logger logger = LoggerFactory.getLogger(ParamUtils.class);

    /**
     * 通用的参数解析方法。将request参数信息解析出来，如果属性名字和参数名称一致，则设法将参数的值赋值给类对象的值。
     *
     * @param request HttpServletRequest
     * @param clazz   转换类类型
     * @return 转换类
     * @throws Exception 异常信息
     */
    public static <T> T initModelForm(HttpServletRequest request, Class<T> clazz)
            throws Exception {
        Map<String, Object> data = parseRequestParam(request);
        T t = initModelForm(data, clazz);
        return t;
    }


    static void pickToMap(Map<String, Object> params, String key, Map<String, String> pickMap) {
        if (params.containsKey(key)) {
            pickMap.put(key, String.valueOf(params.get(key)));
        }
    }

    /**
     * 用于解析常用的查询类。和initModelForm不同的是，会将form表单不存在的属性存入到EqualFilter中
     *
     * @param request HttpServletRequest
     * @param clazz   转换类的类型属性
     * @return 转换后的类
     * @throws Exception 异常
     */
    public static <T extends CommonQuery> CommonQuery initCommonQuery(HttpServletRequest request, Class<T> clazz) throws Exception {
        Map<String, Object> data = parseRequestParam(request);
        QueryUtil commonQuery = new QueryUtil();
        String[] fields = null, sortBy = null, order = null, groups = null;

        Object value;
        for (String key : data.keySet()) {
            value = data.get(key);
            if (key != null && value != null) {
                if ("appid".equals(key.trim())) {
                    commonQuery.putToExtras(key.trim(), value.toString().trim());
                } else if ("nocache".equals(key.trim())) {
                    commonQuery.putToExtras(key.trim(), value.toString().trim());
                } else if ("page".equals(key.trim())) {
                    commonQuery.setPage(Integer.valueOf(value.toString().trim()));
                } else if ("per_page".equals(key.trim())) {
                    commonQuery.setPageSize(Integer.valueOf(value.toString().trim()));
                } else if ("sortby".equals(key.trim())) {
                    sortBy = value.toString().split(",");
                } else if ("order".equals(key.trim())) {
                    order = value.toString().split(",");
                } else if ("fields".equals(key.trim())) {
                    fields = value.toString().split(",");
                } else if ("groups".equals(key.trim())) {
                    groups = value.toString().split(",");
                } else {
                    commonQuery.addEqualFilter(key.trim(), value.toString().trim());
                }
            }
        }

        if (fields != null) {
            for (String field : fields) {
                commonQuery.addToAttributes(new Select(field.trim(), SelectOp.FIELD));
            }
        }

        if (sortBy != null && order != null && sortBy.length == order.length) {
            String field, sort;
            for (int i = 0; i < sortBy.length; i++) {
                field = sortBy[i].trim();
                sort = order[i].trim();
                commonQuery.addToOrders(new OrderBy(field.trim(), "desc".equals(sort) ? Order.DESC : Order.ASC));
            }
        }

        if (groups != null) {
            for (String field : groups) {
                commonQuery.addToGroups(field);
            }
        }
        return commonQuery;
    }

    /**
     * 将request请求中的参数，不管是request的body中的参数还是以getParameter方式获取的参数存入到HashMap并染回该HashMap
     *
     * @param request request请求
     * @return 存储通过request请求传递过来的参数
     * @throws Exception
     */
    public static Params<String, Object> parseRequestParam(HttpServletRequest request) throws Exception {
        Params<String, Object> data = new Params<>();
        data.putAll(initParamFromRequestBody(request));
        data.putAll(initParamFromRequestParameter(request));

        if (data.get("appid") == null) {
            throw new Exception("请设置 appid!");
        }
        return data;
    }

    /**
     * 利用放射的方式将类类型初始化，并根据类属性和参数名相同将参数值赋值给属性
     *
     * @param data  参数
     * @param clazz 类类型
     * @return 赋值后的类对象
     * @throws Exception 异常
     */
    public static <T> T initModelForm(Map<String, Object> data, Class<T> clazz) throws Exception {
        T t = null;
        if (data != null && data.size() > 0) {
            t = clazz.newInstance();
            if (data != null && data.size() > 0) {
                // thrift 都是自动生成的public类型, 故使用getFields,如果不是public的时候, 请不要使用此方法
                Field[] fields = clazz.getDeclaredFields();
                Map<String, Integer> fieldMap = new HashMap<String, Integer>();
                for (int f = 0; f < fields.length; f++) {
                    // 过滤掉转换不需要的字段 metaDataMap
                    if (!"metaDataMap".equals(fields[f].getName())) {
                        fieldMap.put(fields[f].getName(), f);
                    }
                }
                Integer i = null;
                for (Entry<String, Object> entry : data.entrySet()) {
                    if (fieldMap.containsKey(entry.getKey())) {
                        i = fieldMap.get(entry.getKey());
                        if (i == null) {
                            continue;
                        }
                        if (fields[i].getName().equals(entry.getKey())) {
                            String methodName = "set"
                                    + fields[i].getName().substring(0, 1)
                                    .toUpperCase()
                                    + fields[i].getName().substring(1);
                            Method method = clazz.getMethod(methodName,
                                    fields[i].getType());
                            Object cval = BeanUtils.convertTo(
                                    entry.getValue(), fields[i].getType());
                            try {
                                method.invoke(t, cval);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return t;
    }

    /**
     * 解析x-www-form-urlencoded参数
     *
     * @param request 请求
     * @return 参数
     */
    private static Map<String, Object> initParamFromRequestParameter(
            HttpServletRequest request) {
        Map<String, Object> param = new HashMap<>();

        Map<String, String[]> reqParams = request.getParameterMap();
        if (reqParams != null) {
            for (Entry<String, String[]> entry : reqParams.entrySet()) {
                if (entry.getValue() != null && entry.getValue().length > 1) {
                    List<String> values = new ArrayList<>();
                    for (String value : entry.getValue()) {
                        if (value != null) {
                            if (request.getMethod().equals("GET")) {
                                value = new String(value.getBytes("iso8859-1"), request.getCharacterEncoding());
                            }
                            values.add(value);
                        }
                    }
                    param.put(entry.getKey(), values);
                } else {
                    param.put(entry.getKey(), entry.getValue()[0]);
                }
            }
        }
        if (param.size() > 0) {
            param.forEach((key, value) -> {
                LoggerFactory.getLogger(ParamUtils.class).info("----initParamFromRequestParameter key:{}    value:{}", key, value);
            });
        }
        LoggerFactory.getLogger(ParamUtils.class).info("----initParamFromRequestBody:", param.toString());
        return param;
    }

    /**
     * 获取request.reader都到的参数信息。主要用于解析application/json数据
     *
     * @param request 请求
     * @return 参数
     */
    private static Map<String, Object> initParamFromRequestBody(
            HttpServletRequest request) {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (IOException | IllegalStateException e) {
            LoggerFactory.getLogger(ParamUtils.class).error(e.getMessage(), e);
        }
        LoggerFactory.getLogger(ParamUtils.class).info("----initParamFromRequestBody:", jb.toString());
        Map<String, Object> map = JsonToMap.parseJSON2Map(jb.toString());
        return map;
    }

    public static String getLocalHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
        }
        return "unknow";
    }

    public static String getRemoteIp(HttpServletRequest request) {
        String remoteIpForwardedbyLbs = request.getHeader("REMOTE_ADDR");// php
        // 和
        // python
        // tornado不一致，需要实际测试。
        return remoteIpForwardedbyLbs == null ? request.getRemoteAddr()
                : remoteIpForwardedbyLbs;
    }

    public static String getStringRaw(HttpServletRequest request) throws IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }

        return jb.toString();
    }

    public static Map<String, String> getMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String[] value;
        Map<String, String[]> paraMap = request.getParameterMap();
        for (String key : paraMap.keySet()) {
            value = paraMap.get(key);
            map.put(key, value == null ? null : value.length == 0 ? null : value[0]);
        }

        String raw = null;
        try {
            raw = getStringRaw(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (raw != null && raw.trim().length() > 0) {
            if (raw.startsWith("{") && raw.endsWith("}")) {
                //object
                Map<String, String> rawMap = (Map<String, String>) JSON.parse(raw);
                map.putAll(rawMap);
            } else {
                Map<String, String> rawMap = Arrays.stream(StringUtils.split(raw, '&'))
                        .filter(pair -> pair != null && pair.trim().length() > 0)
                        .map(pair -> {
                            String[] p = StringUtils.split(pair, '=');
                            if (p.length == 1) {
                                return new AbstractMap.SimpleEntry<String, String>(p[0], null);
                            } else {
                                return new AbstractMap.SimpleEntry<>(p[0], p[1]);
                            }
                        })
                        .collect(Collectors.toMap(o1 -> o1.getKey(), o2 -> o2.getValue()));
                map.putAll(rawMap);
            }
        }
        return map;
    }
}
