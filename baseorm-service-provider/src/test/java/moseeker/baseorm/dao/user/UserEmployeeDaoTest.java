package moseeker.baseorm.dao.user;

import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eddie on 2017/3/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserEmployeeDaoTest {

    @Autowired
    UserEmployeeDao employeeDao;

    @Test
    public void casBatchInsert() throws BIZException {
        List<UserEmployeeRecord> employees = new ArrayList<>();

        UserEmployeeRecord temp1 = new UserEmployeeRecord();
        temp1.setCompanyId(39978);
        temp1.setCustomField("10000022");
        temp1.setCname("林东建");
        temp1.setAuthMethod((byte) 1);
        employees.add(temp1);

        UserEmployeeRecord temp2 = new UserEmployeeRecord();
        temp2.setCompanyId(39978);
        temp2.setCustomField("10000028");
        temp2.setCname("孙汉文");
        temp2.setEmail("sunhanwen@gmail.com");
        temp2.setAuthMethod((byte) 0);
        employees.add(temp2);

        UserEmployeeRecord temp3 = new UserEmployeeRecord();
        temp3.setCompanyId(39978);
        temp3.setCustomField("10000030");
        temp3.setCname("谢海珍");
        temp3.setAuthMethod((byte) 2);
        employees.add(temp3);

        employeeDao.casBatchInsert(employees);
    }

//    private UserEmployeeDaoThriftService service;
//
//    public void init() {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.scan("com.moseeker.baseorm");
//        context.refresh();
//        service = context.getBean(UserEmployeeDaoThriftService.class);
//    }
//
//    //@Test
//    public void testGetUserEmployee() throws TException {
//        init();
//        QueryUtil queryUtil = new QueryUtil();
//        queryUtil.addEqualFilter("company_id","1");
//        Response response = service.getResources(queryUtil);
//
//        List<UserEmployeeStruct> userEmployeeStruct = JSON.parseArray(response.getData(),UserEmployeeStruct.class);
//
//        Assert.assertTrue(userEmployeeStruct.size() > 0);
//
//        queryUtil = new QueryUtil();
//        response = service.getResources(queryUtil);
//
//        userEmployeeStruct = JSON.parseArray(response.getData(),UserEmployeeStruct.class);
//        Assert.assertTrue(userEmployeeStruct.size() > 0);
//
//        queryUtil = new QueryUtil();
//        queryUtil.addEqualFilter("id","-1");
//        response = service.getResources(queryUtil);
//
//        userEmployeeStruct = JSON.parseArray(response.getData(),UserEmployeeStruct.class);
//        Assert.assertTrue(userEmployeeStruct.size() == 0);
//    }
//
//    //@Test
//    public void testPostPutUserEmployee() throws TException {
//        init();
//
//        List<UserEmployeeStruct> userEmployeeStruct = new ArrayList<>();
//
//        //修改
//        UserEmployeeStruct ues1 = new UserEmployeeStruct();
//        ues1.setCompany_id(57729);
//        ues1.setCustom_field("abcd");
//        ues1.setEname("enafdafdme");
//
//        //添加
//        UserEmployeeStruct ues2 = new UserEmployeeStruct();
//        ues2.setCompany_id(577291);
//        ues2.setCustom_field("abcde");
//        ues2.setEname("enafdafdmee");
//
//        userEmployeeStruct.add(ues1);
//        userEmployeeStruct.add(ues2);
//
//        Response response  = service.postPutUserEmployeeBatch(userEmployeeStruct);
//
//        Assert.assertEquals(response.getData(),""+userEmployeeStruct.size());
//    }
//
//    //@Test
//    public void testDeleteUserEmployee() throws TException {
//        init();
//        Map<String,String> filter = new HashMap<>();
//        filter.put("company","89");
//        filter.put("custom_field","abcd");
//        Response response  = service.delResource(filter);
//
//        System.out.println(response);
//    }
}
