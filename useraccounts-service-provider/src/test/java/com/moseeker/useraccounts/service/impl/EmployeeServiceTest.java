package com.moseeker.useraccounts.service.impl;

import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.employee.struct.*;
import com.moseeker.useraccounts.config.AppConfig;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
//import sun.jvm.hotspot.runtime.Thread;

/**
 * Created by lucky8987 on 17/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class EmployeeServiceTest {

    @Autowired
    EmployeeService service;

    @Autowired
    EmployeeBindByEmail bindByEmail;

    @Autowired
    EmployeeEntity employeeEntity;

//    @Mock
//    SearchengineServices.Iface searchService;

//    @Before
//    public void init() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        ReflectionTestUtils.setField(AopTargetUtils.getTarget(service), "searchService", searchService);
//        Mockito.when(searchService.queryAwardRankingInWx(Mockito.anyList(), Mockito.anyString(), Mockito.anyInt())).thenReturn(ResponseUtils.success(
//                new HashMap<Integer, JSONObject>(){{
//                   put(29157, JSON.parseObject("{\"award\":0,\"ranking\":1}"));
//                }}
//        ));
//    }

    @Test
    public void test() throws TException {
        service.setCacheEmployeeCustomInfo(2192148,248355,"[{\"50\": [\"技术部\"]}]");
    }

    @Test
    public void getEmployee() throws Exception {
        EmployeeResponse employee = service.getEmployee(2376, 2878);
        System.out.println(employee);
    }

    @Test
    public void getEmployeeVerificationConf() throws Exception {
        EmployeeVerificationConfResponse response = service.getEmployeeVerificationConf(30);
        System.out.println(response);
    }

    @Test
    @Transactional
    public void bind() throws Exception {
        BindingParams bp = new BindingParams();
        bp.setEmail("510340677@qq.com");
        bp.setCompanyId(2878);
        bp.setType(BindType.EMAIL);
        bp.setUserId(2376);
        bp.setName("fly");
        Result result = service.bind(bp,1);
        System.out.println(result);
    }

    //@Test
    @Transactional
    public void unbind() throws Exception {
        Result result = service.unbind(12528, 14, 143999,(byte)6);
        System.out.println(result);
    }

    @Test
    public void getEmployeeCustomFieldsConf() throws Exception {
        EmployeeVerificationConfResponse response = service.getEmployeeVerificationConf(650);
        System.out.println(response);
    }

//    @Test
    public void getEmployeeRewards() throws Exception {
        RewardsResponse response = service.getEmployeeRewards(69826, 39978,0,0);
        System.out.println(response);
    }

    //@Test
    @Transactional
    public void setEmployeeCustomInfo() throws Exception {
        Result result = service.setEmployeeCustomInfo(28002227, "[{\"10\":[\"1\"]},{\"8\":[\"\u804c\u4f4d\u4fe1\u606f2\"]}]");
        System.out.println(result);
    }

    @Test
    public void emailActivation() throws Exception {
        Result result = bindByEmail.emailActivation("b9122ca1f6254e74e69f64708a182b53a857b397",1);
        System.out.println(result);
    }

//    @Test
//    public void awardRankingTest() {
//        List<EmployeeAward> response = service.awardRanking(782667, 39978, "2017");
//        System.out.println(JSONObject.toJSONString(response));
//    }

//    @Test
//    @Commit
    public void addAwardTest() throws Exception{
    /**
     * 线程池
     */
     ExecutorService threadPool = new ThreadPoolExecutor(10, 15, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 20; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    UserEmployeePointsRecordDO ueprDo = new UserEmployeePointsRecordDO();
                    ueprDo.setAward(1);
                    ueprDo.setEmployeeId(677720);
                    ueprDo.setReason("加积分");
                    try {
                        employeeEntity.addReward(677720, 96, ueprDo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            threadPool.submit(runnable);
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        employeeEntity.addAwardBefore(677720, 96, 233, 7, 112, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        threadPool.shutdown();
        int retry = 50;
        while(retry > 0){
            try {
                // 每10秒检查一次是否关闭
                if (threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                    break;
                }
                retry--;
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Test
    public void setCacheEmployeeCustomInfo() throws Exception {
        Result result = service.setCacheEmployeeCustomInfo(2376, 2878, "[{\"a\":2}]");
        System.out.println(result);
    }
//    @Test
//    public void convertCandidatePerson(){
//        bindByEmail.convertCandidatePerson(391471,2878);
//    }
//    @Test
//    public void cancelCandidate(){
//        bindByEmail.cancelCandidate(391471,2878);
//    }
}