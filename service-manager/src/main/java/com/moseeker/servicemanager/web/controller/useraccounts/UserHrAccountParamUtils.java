package com.moseeker.servicemanager.web.controller.useraccounts;

import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserHrAccountParamUtils extends ParamUtils {

    public static List<UserEmployeeDO> parseUserEmployeeDO(List<HashMap<String, Object>> datas) throws Exception {
        List<UserEmployeeDO> cs = new ArrayList<>();
        if (datas != null) {
            datas.forEach(userEmployeeDO -> {
                try {
                    UserEmployeeDO c = ParamUtils.initModelForm(userEmployeeDO, UserEmployeeDO.class);
                    cs.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerFactory.getLogger(UserHrAccountParamUtils.class).error(e.getMessage(), e);
                } finally {
                    //do nothing
                }
            });
        }
        return cs;
    }


}
