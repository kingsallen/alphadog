package com.moseeker.servicemanager.web.controller.useraccounts;

import com.moseeker.common.util.StringUtils;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.parser.Entity;

public class UserHrAccountParamUtils extends ParamUtils {

    public static Map<Integer, UserEmployeeDO> parseUserEmployeeDO(List<HashMap<String, Object>> datas) throws Exception {
        Map<Integer, UserEmployeeDO> map = new LinkedHashMap();
        if (datas != null) {
            for (HashMap<String, Object> data : datas) {
                if (StringUtils.isEmptyObject(data.get("rowNum"))) {
                    throw new Exception("请设置行号!");
                }
                try {
                    map.put((Integer) data.remove("rowNum"), ParamUtils.initModelForm(data, UserEmployeeDO.class));
                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerFactory.getLogger(UserHrAccountParamUtils.class).error(e.getMessage(), e);
                }
            }
        }
        return map;
    }

}
