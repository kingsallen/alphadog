package moseeker.baseorm.dao.Profile;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.Thriftservice.ProfileProfileDaoThriftService;
import com.moseeker.common.util.HttpClient;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moseeker on 2017/3/13.
 */
public class ProfileProfileDaoTest {

    ProfileProfileDaoThriftService service;

    public void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.moseeker.baseorm");
        context.refresh();
        service = context.getBean(ProfileProfileDaoThriftService.class);
    }

    @Test
    public void testJoin(){
//        Connection conn = null;
//        try {
//            conn = DBConnHelper.DBConn.getConn();
//            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
//            long start = System.currentTimeMillis();
//            System.err.println("start-----------:" + start);
//            List<Map<String, Object>> datas = create
//                    .select()
//                    .from(JobApplication.JOB_APPLICATION)
//                    .leftJoin(JobPosition.JOB_POSITION)
//                    .on(JobApplication.JOB_APPLICATION.POSITION_ID.equal(JobPosition.JOB_POSITION.ID))
//                    .where(JobApplication.JOB_APPLICATION.COMPANY_ID.eq((int)(companyId)))
//                    .and(JobApplication.JOB_APPLICATION.SOURCE_ID.eq((int)(sourceId)))
//                    .and(JobApplication.JOB_APPLICATION.ATS_STATUS.eq(atsStatus))
//                    .fetchInto(com.moseeker.thrift.gen.application.struct.JobApplication.class)
//                    .stream()
//                    .map(application -> getRelatedDataByJobApplication(create, application, recommender))
//                    .collect(Collectors.toList());
//            System.err.println("end-----------:" + (System.currentTimeMillis() - start));
//            return ResponseUtils.success(datas);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
//        } finally {
//            if (conn != null && !conn.isClosed()) {
//                conn.close();
//            }
//        }
    }

    //@Test
    public void testProfileByApplication() throws TException {
        /*init();
        Response response = service.getResourceByApplication(107604,0,0,true,false);
        System.out.println(response);*/
    }

   // @Test
    public void testDownloadProfileByUserId() throws ConnectException {
        Map<String,Object> map = new HashMap<String,Object>(){{
            put("user_id",2590);
            put("password","moseeker.com");
        }};
        String content = HttpClient.sendPost("http://download.moseeker.com/generatebyuserid", JSON.toJSONString(map));

        System.out.println(content);
    }
}
