package com.moseeker.demo.crud;

 public class CommonTest {
//    TestUserDao userDao;
//
//    @Before
//    public void init() {
//        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
//        acac.scan("com.moseeker.demo");
//        acac.scan("com.moseeker.baseorm");
//        acac.scan("com.moseeker.common.aop.iface");
//        acac.refresh();
//        userDao = acac.getBean(TestUserDao.class);
//    }
//
//
//    /*//@Test
//    public void testUpdateMap() throws SQLException {
//        int result = UpdateCreator
//                .set("name", "test_add2_" + System.currentTimeMillis())
//                .set("password", "test_add2_" + System.currentTimeMillis())
//                .where(UpdateCondition.like("name", "test_add_%"))
//                .update(userDao);
//
//        Assert.assertEquals(result, 1);
//    }*/
//

//    //@Test
//    public void testDelete() throws SQLException {
//        int delete = userDao.delete(QueryCondition.like("name", "test_add_%"));
//        Assert.assertEquals(delete, 1);
//    }
//
//
//    //@Test
//    public void testQuery() throws SQLException {
//
//        //实现的SQL
//
//        //select username, count(username) as username_count
//        //from user_user
//        //where
//        //is_disable = 0 and activation = 0 and (source  = 0 or source = 7)
//        //groupby username
//        //orderby username asc;
//
//        CommonQuery commonQuery = QueryCreator
//                .select("username")
//                .select(new Select("username", SelectOp.COUNT))
//                .where(CommonCondition.equal("is_disable", 0))
//                .and(CommonCondition.equal("activation", 0))
//                .and(QueryCondition.equal("source", 0).or(CommonCondition.equal("source", 7)))
//                .groupBy("username")
//                .orderBy("username", Order.ASC)
//                .pageSize(10)
//                .page(1)
//                .getCommonQuery();
//
//        List<CustomUser> objectMap = userDao.getDatas(commonQuery,CustomUser.class);
//
//        List<Map<String, Object>> objectMap2 = DSL.using(DBConnHelper.DBConn.getConn(), SQLDialect.MYSQL)
//                .select(UserUser.USER_USER.USERNAME, UserUser.USER_USER.USERNAME.count().as("username_count"))
//                .from(UserUser.USER_USER)
//                .where(UserUser.USER_USER.IS_DISABLE.equal((byte) 0).and(UserUser.USER_USER.ACTIVATION.equal((byte) 0)).and(UserUser.USER_USER.SOURCE.eq((short) 0).or(UserUser.USER_USER.SOURCE.eq((short) 7))))
//                .groupBy(UserUser.USER_USER.USERNAME)
//                .orderBy(UserUser.USER_USER.USERNAME.asc())
//                .limit(0, 10)
//                .fetchMaps();
//
//        objectMap.forEach(map -> System.out.println(map));
//        System.out.println("----------------");
//        objectMap2.forEach(map -> System.out.println(map));
//
//    }
//
}


