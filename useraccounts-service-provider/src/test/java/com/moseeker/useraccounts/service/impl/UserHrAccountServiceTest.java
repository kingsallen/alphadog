package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.Employee;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeVO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.pojo.EmployeeRank;
import com.moseeker.useraccounts.pojo.EmployeeRankObj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserHrAccountServiceTest {

    @Autowired
    private UserHrAccountService userHrAccountService;


    //@Test
    public void testUserHrAccount() {
        try {
            Response response = userHrAccountService.userHrAccount(1, 1, 1, 20);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getListNum() {
        try {
            System.out.println(userHrAccountService.getListNum("张", 3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void employeeList() {
        try {
            System.out.println(userHrAccountService.employeeList("", 39978, 0, "", "", 1, 10, "2017"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 员工数据导入
     */
    @Test
    public void employeeImport() {
        try {
            List<UserEmployeeDO> list = new ArrayList<>();
            UserEmployeeDO userEmployeeDO = new UserEmployeeDO();
            userEmployeeDO.setCompanyId(3);
            userEmployeeDO.setCname("你好");
            userEmployeeDO.setEmployeeid("1");
            userEmployeeDO.setMobile("18600164078");
            userEmployeeDO.setCustomField("test update");
            list.add(userEmployeeDO);


            userEmployeeDO = new UserEmployeeDO();
            userEmployeeDO.setCompanyId(3);
            userEmployeeDO.setCname("章启虎");
            userEmployeeDO.setEmployeeid("2");
            userEmployeeDO.setMobile("18611164078");
            userEmployeeDO.setCustomField("01082021");
            list.add(userEmployeeDO);


            userEmployeeDO = new UserEmployeeDO();
            userEmployeeDO.setCompanyId(3);
            userEmployeeDO.setCname("你好");
            userEmployeeDO.setEmployeeid("3");
            userEmployeeDO.setMobile("18613264078");
            userEmployeeDO.setCustomField("test");
            list.add(userEmployeeDO);

//            Response response = userHrAccountService.employeeImport(3, list);
//            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    /**
//     * 员工数据导入
//     */
//    @Test
//    public void employeeList() {
//        try {
////            userHrAccountService.employeeList("", 3, 1, "create_time", 1, 0, 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
////            List<UserEmployeeVO> list = userHrAccountService.employeeList("", 3, 1, "create_time", 1, 0, 0);


    /**
     * 检查员工重复(批量导入之前验证)
     */
    @Test
    public void checkBatchInsert() {
        try {
            List<UserEmployeeDO> list = new ArrayList<>();
//            userHrAccountService.repetitionFilter(list, 3);

//            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 主入口
     *
     * @param args
     */
    public static void main(String args[]) {
        String str = "";
        Map dataMap = (Map) JSONObject.parseObject(str,LinkedHashMap.class, Feature.OrderedField);
        List<LinkedHashMap<String,Integer>> data = (List<LinkedHashMap<String,Integer>>) dataMap.get("data");
//        for (Map map : data) {
//            System.out.println(map.get("award"));
//            System.out.println(map.get("employeeId"));
//        }
        System.out.println(dataMap);
    }


}
