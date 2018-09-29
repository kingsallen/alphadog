package com.moseeker.useraccounts.service.impl.ats.employee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.useraccounts.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class EmployeeBatchHandlerTest {

    @Autowired
    EmployeeBatchHandler batchHandler;

    @Test
    public void postPutUserEmployeeBatch() throws Exception {

        String json = "{\n" +
                "  \"appid\": 1,\n" +
                "  \"as_task\": true,\n" +
                "  \"auth_method\": 0,\n" +
                "  \"company_id\": 248355,\n" +
                "  \"cancel_auth\": false,\n" +
                "  \"del_not_include\": true,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"email\": \"xiaoming.wang01@cn.mcd.com\",\n" +
                "      \"custom_field_values\": \"[{\\\"82\\\": [\\\"CN Shanghai\\\"]},{\\\"98\\\": [\\\"Staff\\\"]}]\",\n" +
                "      \"custom_field\": \"10001959\",\n" +
                "      \"departmentname\": \"\",\n" +
                "      \"company_id\": 248355,\n" +
                "      \"cname\": \"\\u738b\\u6653\\u660e\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"email\": \"Junjie.Shen@cn.mcd.com\",\n" +
                "      \"custom_field_values\": \"[{\\\"82\\\": [\\\"CN Shanghai\\\"]},{\\\"98\\\": [\\\"Staff\\\"]}]\",\n" +
                "      \"custom_field\": \"10002569\",\n" +
                "      \"departmentname\": \"\",\n" +
                "      \"company_id\": 248355,\n" +
                "      \"cname\": \"\\u6c88\\u4fca\\u6770\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"email\": \"yining.pan@cn.mcd.com\",\n" +
                "      \"custom_field_values\": \"[{\\\"82\\\": [\\\"CN Shanghai\\\"]},{\\\"98\\\": [\\\"Staff\\\"]}]\",\n" +
                "      \"custom_field\": \"10007321\",\n" +
                "      \"departmentname\": \"\",\n" +
                "      \"company_id\": 248355,\n" +
                "      \"cname\": \"\\u6f58\\u4f0a\\u5b81\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"email\": \"sophia.shi@cn.mcd.com\",\n" +
                "      \"custom_field_values\": \"[{\\\"82\\\": [\\\"CN Shanghai\\\"]},{\\\"98\\\": [\\\"Staff\\\"]}]\",\n" +
                "      \"custom_field\": \"10000251\",\n" +
                "      \"departmentname\": \"\",\n" +
                "      \"company_id\": 248355,\n" +
                "      \"cname\": \"\\u65bd\\u654f\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"email\": \"christine.chen@cn.mcd.com\",\n" +
                "      \"custom_field_values\": \"[{\\\"82\\\": [\\\"CN Shanghai\\\"]},{\\\"98\\\": [\\\"Staff\\\"]}]\",\n" +
                "      \"custom_field\": \"10000272\",\n" +
                "      \"departmentname\": \"\",\n" +
                "      \"company_id\": 248355,\n" +
                "      \"cname\": \"\\u9648\\u96f7\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"email\": \"kiko.huang@cn.mcd.com\",\n" +
                "      \"custom_field_values\": \"[{\\\"82\\\": [\\\"CN Shanghai\\\"]},{\\\"98\\\": [\\\"Staff\\\"]}]\",\n" +
                "      \"custom_field\": \"10006846\",\n" +
                "      \"departmentname\": \"\",\n" +
                "      \"company_id\": 248355,\n" +
                "      \"cname\": \"\\u9ec4\\u6676\\u6676\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"email\": \"guixia.zhang@cn.mcd.com\",\n" +
                "      \"custom_field_values\": \"[{\\\"82\\\": [\\\"CN MCCL Training Learning and Development\\\"]},{\\\"98\\\": [\\\"Staff\\\"]}]\",\n" +
                "      \"custom_field\": \"10000533\",\n" +
                "      \"departmentname\": \"\",\n" +
                "      \"company_id\": 248355,\n" +
                "      \"cname\": \"\\u5f20\\u6842\\u971e\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"email\": \"lynn.yan@cn.mcd.com\",\n" +
                "      \"custom_field_values\": \"[{\\\"82\\\": [\\\"CN MCCL Training Learning and Development\\\"]},{\\\"98\\\": [\\\"Staff\\\"]}]\",\n" +
                "      \"custom_field\": \"10001197\",\n" +
                "      \"departmentname\": \"\",\n" +
                "      \"company_id\": 248355,\n" +
                "      \"cname\": \"\\u95eb\\u82d3\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        UserEmployeeBatchForm form = JSON.parseObject(json,UserEmployeeBatchForm.class);
        batchHandler.postPutUserEmployeeBatch(form);

    }

    private static final String url = "jdbc:mysql://db-d.dqprism.com:3306/userdb?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE&zeroDateTimeBehavior=convertToNull";
    private static final String username = "daqi";
    private static final String password = "5F51692091B4031640E18E7C27430E071BC878C8";

    @Test
    public void generateDoc() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(
                "Select COLUMN_NAME , DATA_TYPE , COLUMN_COMMENT " +
                        "from INFORMATION_SCHEMA.COLUMNS " +
                        "Where table_name = 'user_employee' " +
                        "AND table_schema = 'userdb' ");

        Map<String, DB> map = new TreeMap<>();
        while (rs.next()) {
            String columnName = rs.getString(1);
            String dataType = rs.getString(2);
            String columnComment = rs.getString(3);

            map.put(columnName.replace("_", "").toLowerCase(), new DB(dataType,columnComment));
        }

        JSONObject jsonObject = JSON.parseObject(json);
        for (String key : jsonObject.keySet()) {
            String checkKey = key.replace("_", "").toLowerCase();
            if (map.containsKey(checkKey)) {
                DB db = map.get(checkKey);
                System.out.println("| "+key.replace("_","\\_")+" | " +calcJavaType(db.dataType)+" | "+db.columnComment.replace("_","\\_")+" |");
            } else {
                System.out.println("| "+key.replace("_","\\_")+" |  |  |");

            }
        }

    }

    private String calcJavaType(String dbType){
        switch (dbType.toLowerCase()){
            case "varchar":
                return "String";
            case "int":
                return "int";
            case "decimal":
                return "int";
            case "tinyint":
                return "int";
            case "date":
                return "String";
            case "datetime":
                return "String";
            case "timestamp":
                return "String";
        }
        return "";
    }

    private static class DB{
        String dataType;
        String columnComment;

        public DB(String dataType, String columnComment) {
            this.dataType = dataType;
            this.columnComment = columnComment;
        }
    }

    private String json = "{\n" +
            "      \"activation_code\": \"string\",\n" +
            "      \"address\": \"string\",\n" +
            "      \"auth_level\": \"string\",\n" +
            "      \"auth_method\": \"string\",\n" +
            "      \"award\": 0,\n" +
            "      \"binding_time\": \"string\",\n" +
            "      \"birthday\": \"string\",\n" +
            "      \"cfname\": \"string\",\n" +
            "      \"city\": \"string\",\n" +
            "      \"city_code\": 0,\n" +
            "      \"cname\": \"string\",\n" +
            "      \"company_id\": 0,\n" +
            "      \"companybody\": \"string\",\n" +
            "      \"create_time\": \"string\",\n" +
            "      \"custom_field\": \"string\",\n" +
            "      \"custom_field_values\": \"string\",\n" +
            "      \"degree\": \"string\",\n" +
            "      \"departmentname\": \"string\",\n" +
            "      \"disable\": \"string\",\n" +
            "      \"download_token\": \"string\",\n" +
            "      \"education\": \"string\",\n" +
            "      \"efname\": \"string\",\n" +
            "      \"email\": \"string\",\n" +
            "      \"email_isvalid\": \"string\",\n" +
            "      \"employdate\": \"string\",\n" +
            "      \"employeeid\": \"string\",\n" +
            "      \"ename\": \"string\",\n" +
            "      \"groupname\": \"string\",\n" +
            "      \"hr_wxuser_id\": 0,\n" +
            "      \"id\": 0,\n" +
            "      \"idcard\": \"string\",\n" +
            "      \"is_admin\": \"string\",\n" +
            "      \"is_rp_sent\": \"string\",\n" +
            "      \"job_grade\": \"string\",\n" +
            "      \"last_login_ip\": \"string\",\n" +
            "      \"last_login_time\": \"string\",\n" +
            "      \"login_count\": 0,\n" +
            "      \"managername\": \"string\",\n" +
            "      \"mobile\": \"string\",\n" +
            "      \"password\": \"string\",\n" +
            "      \"position\": \"string\",\n" +
            "      \"position_id\": 0,\n" +
            "      \"register_ip\": \"string\",\n" +
            "      \"register_time\": \"string\",\n" +
            "      \"retiredate\": \"string\",\n" +
            "      \"role_id\": 0,\n" +
            "      \"section_id\": 0,\n" +
            "      \"sex\": \"string\",\n" +
            "      \"source\": \"string\",\n" +
            "      \"status\": 0,\n" +
            "      \"sysuser_id\": 0,\n" +
            "      \"team_id\": 0,\n" +
            "      \"update_time\": \"string\",\n" +
            "      \"wxuser_id\": 0\n" +
            "    }";
}