package com.moseeker.useraccounts.service;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCityDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyAddressDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountDepartmentDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountCity;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
import org.apache.commons.lang.time.FastDateFormat;
import org.joda.time.DateTime;
import org.jooq.impl.DefaultDSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.moseeker.useraccounts.service.config.AppConfig;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.IntStream;

//@Configuration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//@PropertySource("classpath:common.properties")
public class TestClass {
//    @Autowired
//    protected DefaultDSLContext create;
//
//    @Value("${rabbitmq.host}")
//    private String host;
//
//    @Autowired
//    HRThirdPartyAccountHrDao dao;
//
//    @Autowired
//    ThirdpartyAccountCityDao cityDao;
//
//    @Autowired
//    ThirdpartyAccountCompanyDao companyDao;
//
//    @Autowired
//    ThirdpartyAccountCompanyAddressDao addressDao;
//
//    @Autowired
//    ThirdpartyAccountDepartmentDao departmentDao;

    private static String hr_id="82752";

//    @Test
    public void test(){
        /*HrThirdPartyAccountDO obj=new HrThirdPartyAccountDO();
        obj.setId(532);
        obj.setBinding((short)0);

        Query query=new Query.QueryBuilder().where("hr_account_id",hr_id).and("status","1").and("channel","1").buildQuery();
        HrThirdPartyAccountHrDO hrDo=dao.getData(query);

        query=new Query.QueryBuilder().where("account_id",hrDo.getThirdPartyAccountId()).buildQuery();
        List<ThirdpartyAccountCityDO> cityDO=cityDao.getDatas(query);
        List<ThirdpartyAccountCompanyDO> companyDO=companyDao.getDatas(query);
        List<ThirdpartyAccountCompanyAddressDO> addressDO=addressDao.getDatas(query);
        List<ThirdpartyAccountDepartmentDO> departmentDO=departmentDao.getDatas(query);


        System.out.println("-------------城市---------------");
        cityDO.stream().forEach(t->System.out.println(JSONObject.toJSONString(cityDO)));
        System.out.println("-------------公司---------------");
        companyDO.stream().forEach(t->System.out.println(JSONObject.toJSONString(companyDO)));
        System.out.println("-------------地址---------------");
        addressDO.stream().forEach(t->System.out.println(JSONObject.toJSONString(addressDO)));
        System.out.println("-------------部门---------------");
        departmentDO.stream().forEach(t->System.out.println(JSONObject.toJSONString(departmentDO)));*/


//        int count=create.executeUpdate(BeanUtils.structToDB(obj, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.getRecordType()));
//        System.out.println(host);


//        List<String> sum=new ArrayList<String>();

//        IntStream.range(0,10).forEach(i->sum.add(i+""));
    }

    public  static void main(String arg[]){

//        DecimalFormat df = new DecimalFormat("000000");
//        System.out.println(df.format("0123"));

        String str="Title\t| Title\n" +
                "Occupation\t| Occupation\n" +
                "Quantity\t| Quantity\n" +
                "Degree\t| Degree\n" +
                "Experience\t| Experience\n" +
                "Salary_low\t| Salary_bottom\n" +
                "Salary_high\t| Salary_top\n" +
                "Description\t| Description\n" +
                "Cities\t| Cities\n" +
                "Email\t| Email\n" +
                "Type_code\t| Work_type\n" +
                "Department\t| Department\n" +
                "Company\t| Company_name";
        String strs[]=str.split("\n");

        String str2="| 数据库中取\n" +
                "| 前台传的companyId查询third_party_account_company的company_name字段\n" +
                "| job_position的degree字段映射51的degree,DegreeChangeUtil.getJob51Degree\n" +
                "| 暂时没有赋值\n" +
                "| 数据库中job_position的accountabilities,requirement\n" +
                "| 数据库中job_position的hrEmail\n" +
                "| 数据库中job_position的experience映射成51的exprience\n" +
                "| 前台传的occipation去dict_51job_occupation查，对应的是code_other字段\t\n" +
                "| 前台传了值>0就用前台,否则用job_position的count\n" +
                "| 前台设置的salaryTop\n" +
                "| 前台设置的salaryBottom\n" +
                "| 数据库中job_position中的title字段\n" +
                "| 数据库中job_position的EmploymentType映射成51的type_code";
        String strs2[]=str2.split("\n");


        Arrays.sort(strs);
//        Arrays.sort(strs2);

        for(int i=0;i<strs.length;i++){
            System.out.println(strs[i]+"\t"+strs2[i]);
        }


//        System.out.println(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));

//        System.out.println(new DateTime().getSecondOfDay());
        /*JSONObject obj=JSONObject.parseObject("{" +

                "    \"data\":{" +
                "        \"company\":[" +
                "            {" +
                "              \"id\": 49," +
                "              \"name\": \"上海大岂网络科技有限公司\"" +
                "            }    " +
                "        ]," +
                "        \"address\":[" +
                "            {" +
                "              \"id\": 352," +
                "              \"name\": \"北京市海淀区海淀大街27号天使大厦4层\"," +
                "              \"city\": \"北京市海淀区\"" +
                "            }" +
                "        ],\"department\":[" +
                "            {" +
                "              \"id\": 7," +
                "              \"name\": \"人事行政\"" +
                "            }    " +
                "        ]," +
                "        \"city\":[" +
                "            {" +
                "              \"id\": 25," +
                "              \"code\": 310000," +
                "              \"name\":\"上海\"," +
                "              \"jobtype\": 0," +
                "              \"remainNum\": 3," +
                "            }" +
                "        ]" +
                "   }" +
                "}");
        System.out.println(obj.get("data"));*/
    }
}
