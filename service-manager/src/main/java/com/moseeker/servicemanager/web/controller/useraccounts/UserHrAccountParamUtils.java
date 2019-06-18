package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import java.util.*;

import org.slf4j.LoggerFactory;

public class UserHrAccountParamUtils extends ParamUtils {

    public static Map<Integer, UserEmployeeDO> parseUserEmployeeDO(List<HashMap<String, Object>> datas) throws Exception {
        Map<Integer, UserEmployeeDO> map = new LinkedHashMap();
        if (datas != null) {
            for (HashMap<String, Object> data : datas) {
                if (data.get("rowNum") == null) {
                    throw new Exception("请设置行号!");
                }
                try {
                    UserEmployeeDO userEmployeeDO = parseEmployee(data);
                    map.put((Integer) data.remove("rowNum"), userEmployeeDO);
                } catch (Exception e) {
                    LoggerFactory.getLogger(UserHrAccountParamUtils.class).error(e.getMessage(), e);
                }
            }
        }
        return map;
    }

    public static List<UserEmployeeDO> parseEmployees(List<Map<String, Object>> datas) throws Exception {
        if (datas != null && datas.size() > 0) {
            List<UserEmployeeDO> userEmployeeDOS = new ArrayList<>(datas.size());
            for (Map<String, Object> data : datas) {
                try {
                    UserEmployeeDO userEmployeeDO = parseEmployee(data);
                    userEmployeeDOS.add(userEmployeeDO);
                } catch (Exception e) {
                    LoggerFactory.getLogger(UserHrAccountParamUtils.class).error(e.getMessage(), e);
                }
            }
            return userEmployeeDOS;
        } else {
            return new ArrayList<>(0);
        }
    }

    private static UserEmployeeDO parseEmployee(Map<String, Object> data) {
        try {
            UserEmployeeDO userEmployeeDO = ParamUtils.initModelForm(data, UserEmployeeDO.class);
            if (data.get("customFieldValues") != null && !data.get("customFieldValues").equals("[]")) {

                JSONArray jsonArray = new JSONArray();
                List<Map<String, String>>  list = (List)data.get("customFieldValues");
                if (list != null && list.size() > 0) {
                    list.forEach(map -> {
                        JSONObject jsonObject = new JSONObject();
                        map.forEach((key, value) -> {
                            if (!value.trim().equals("")) {
                                jsonObject.put(key, value);
                            }
                        });
                        if (jsonObject.size() > 0) {
                            jsonArray.add(jsonObject);
                        }
                    });

                    userEmployeeDO.setCustomFieldValues(jsonArray.toJSONString());
                }
            }
            return userEmployeeDO;
        } catch (Exception e) {
            LoggerFactory.getLogger(UserHrAccountParamUtils.class).error(e.getMessage(), e);
        }
        return null;
    }

}
