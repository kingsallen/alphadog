package moseeker.baseorm.dao.Profile;

import com.moseeker.baseorm.Thriftservice.ProfileDaoThriftService;
import com.moseeker.baseorm.Thriftservice.UserEmployeeDaoThriftService;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by moseeker on 2017/3/13.
 */
public class ProfileDaoTest {

    ProfileDaoThriftService service;

    public void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.moseeker.baseorm");
        context.refresh();
        service = context.getBean(ProfileDaoThriftService.class);
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
//                    .where(JobApplication.JOB_APPLICATION.COMPANY_ID.eq(UInteger.valueOf(companyId)))
//                    .and(JobApplication.JOB_APPLICATION.SOURCE_ID.eq(UInteger.valueOf(sourceId)))
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

    @Test
    public void testProfileByApplication() throws TException {
        init();
        Response response = service.getResourceByApplication(1,0,3,true);
        System.out.println(response);
    }
}
