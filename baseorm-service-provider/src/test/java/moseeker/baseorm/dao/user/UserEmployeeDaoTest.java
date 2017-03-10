package moseeker.baseorm.dao.user;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.Thriftservice.UserEmployeeDaoThriftService;
import com.moseeker.baseorm.dao.user.UserEmployeeDao;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eddie on 2017/3/9.
 */
public class UserEmployeeDaoTest {

    private UserEmployeeDaoThriftService service;

    public void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.moseeker.baseorm");
        context.refresh();
        service = context.getBean(UserEmployeeDaoThriftService.class);
    }

    @Test
    public void testGetUserEmployee() throws TException {
        init();
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("company_id","1");
        Response response = service.getResources(queryUtil);

        List<UserEmployeeStruct> userEmployeeStruct = JSON.parseArray(response.getData(),UserEmployeeStruct.class);

        Assert.assertTrue(userEmployeeStruct.size() > 0);

        queryUtil = new QueryUtil();
        response = service.getResources(queryUtil);

        userEmployeeStruct = JSON.parseArray(response.getData(),UserEmployeeStruct.class);
        Assert.assertTrue(userEmployeeStruct.size() > 0);

        queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id","-1");
        response = service.getResources(queryUtil);

        userEmployeeStruct = JSON.parseArray(response.getData(),UserEmployeeStruct.class);
        Assert.assertTrue(userEmployeeStruct.size() == 0);
    }

    @Test
    public void testPostPutUserEmployee() throws TException {
//        init();
//        QueryUtil queryUtil = new QueryUtil();
//        queryUtil.addEqualFilter("employeeid","1979062611");
//        Response response = service.getResources(queryUtil);
//
//        List<UserEmployeeStruct> userEmployeeStruct = JSON.parseArray(response.getData(),UserEmployeeStruct.class);
//        for(UserEmployeeStruct struct : userEmployeeStruct){
//            struct.setEname("ddddd");
//        }
//        userEmployeeStruct.get(0).setId(141770);
//        response  = service.postPutResources(userEmployeeStruct);
//
//        System.out.println(userEmployeeStruct.size());
    }

    @Test
    public void testDeleteUserEmployee() throws TException {
        init();
        Map<String,String> filter = new HashMap<>();
        filter.put("company","89");
        filter.put("custom_field","abcd");
        Response response  = service.delResource(filter);

        System.out.println(response);
    }
}
