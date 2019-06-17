package com.moseeker.servicemanager.web.controller.useraccounts;

import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
                    map.put((Integer) data.remove("rowNum"), ParamUtils.initModelForm(data, UserEmployeeDO.class));
                } catch (Exception e) {
                    LoggerFactory.getLogger(UserHrAccountParamUtils.class).error(e.getMessage(), e);
                }
            }
        }
        return map;
    }

}
