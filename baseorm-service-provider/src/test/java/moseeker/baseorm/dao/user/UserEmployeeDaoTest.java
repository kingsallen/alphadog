package moseeker.baseorm.dao.user;

/**
 * Created by eddie on 2017/3/9.
 */
public class UserEmployeeDaoTest {

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
