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
                "  appid:4,\n" +
                "  company_id: 133445,\n" +
                "  del_not_include: false,\n" +
                "  as_task: true,\n" +
                "  cancel_auth: true,\n" +
                "  auth_method: 0,\n" +
                "  data:[\n" +
                "\t{\n" +
                "\t\tcompany_id: 133445,\n" +
                "\t\tcname: '袁敏华',\n" +
                "\t\temail: 'Mala.Yuan@cn.mcd.com',\n" +
                "\t\tcustom_field: 100107587,\n" +
                "\t\tcustom_field_values: [\n" +
                "\t\t\t{\n" +
                "\t\t\t  \"82\": [\n" +
                "\t\t\t    \"CN Zhejiang7\"\n" +
                "\t\t\t  ]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t  \"98\": [\n" +
                "\t\t\t    \"Staff7\"\n" +
                "\t\t\t  ]\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\tdepartmentname: ''\n" +
                "\t}\t\n" +
                "  ]\n" +
                "}";

        UserEmployeeBatchForm form = JSON.parseObject(json,UserEmployeeBatchForm.class);
        batchHandler.postPutUserEmployeeBatch(form);

    }

    private static final String url = "jdbc:mysql://db-d.dqprism.com:3306/userdb?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE&zeroDateTimeBehavior=convertToNull";
    private static final String username = "daqi";
    private static final String password = "7f1a45eac5985519829c929e7bbf0557";

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