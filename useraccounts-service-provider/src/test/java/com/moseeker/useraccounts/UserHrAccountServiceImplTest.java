package com.moseeker.useraccounts;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.hrdb.tables.HrNps;
import com.moseeker.db.hrdb.tables.HrNpsRecommend;
import com.moseeker.db.hrdb.tables.records.HrNpsRecommendRecord;
import com.moseeker.db.hrdb.tables.records.HrNpsRecord;
import com.moseeker.db.userdb.tables.*;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import com.moseeker.useraccounts.thrift.UserHrAccountServiceImpl;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

/**
 * HR账号服务
 * <p>
 * Created by zzh on 16/6/1.
 */
public class UserHrAccountServiceImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = UserHrAccountService.Iface.class.getName();
        ClientConfig<UserHrAccountService.Iface> clientConfig = new ClientConfig<UserHrAccountService.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService");
        clientConfig.setIface(iface);

        UserHrAccountService.Iface userHrAccountService = null;

        try {
            userHrAccountService = clientConfig.createProxy(registryConfig);

            // 添加我感兴趣
            String code = "1234";
//            Response postResource = userHrAccountService.postResource(getUserHrAccount(), code);
            Response postResource = userHrAccountService.userHrAccount(1, 1, 1, 10);

            System.out.println(postResource);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加HR账号
     */
    private static UserHrAccount getUserHrAccount() {
        UserHrAccount userHrAccount = new UserHrAccount();
        userHrAccount.setMobile("18610245972");
        userHrAccount.setSource(2);
        return userHrAccount;
    }


    //@Test
    public void userHrAccount() throws TException {
        UserHrAccountService.Iface user = ServiceManager.SERVICEMANAGER.getService(UserHrAccountService.Iface.class);
        Response response = user.userHrAccount(0, 1, 1, 10);
        System.out.println(response.getData());
    }

    public UserHrAccountService.Iface service;

    @Before
    public void init() {
        service = ServiceManager.SERVICEMANAGER.getService(UserHrAccountService.Iface.class);
    }

    @Test
    public void testNpsStatus() throws TException {
        HrNpsResult result = service.npsStatus(82690, null, null);
        System.out.println(BeanUtils.convertStructToJSON(result));
    }

//    @Test
    public void fakeData(){
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            List<UserHrAccountRecord> userHrAccount = create.select()
                    .from(com.moseeker.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT)
                    .where(com.moseeker.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT.DISABLE.eq(1))
                    .and(com.moseeker.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT.ACTIVATION.eq(Byte.valueOf("1")))
                    .orderBy(com.moseeker.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT.CREATE_TIME.desc())
                    .limit(2000)
                    .fetchInto(UserHrAccountRecord.class);
            for(UserHrAccountRecord record : userHrAccount){
                if(record.getId()%2==0){
                    HrNpsUpdate update = new HrNpsUpdate();
                    update.setUser_id(record.getId());
                    update.setIntention(Byte.valueOf(""+record.getId()%8));
                    update.setAccept_contact((byte) (Math.random()*3));
                    service.npsUpdate(update);
                }else{
                    HrNpsUpdate update = new HrNpsUpdate();
                    update.setUser_id(record.getId());
                    update.setIntention((byte) (record.getId()%3+8));
                    update.setUsername("testUserName");
                    update.setMobile("testMobile");
                    update.setCompany("testCompany");
                    service.npsUpdate(update);
                }
            }
        } catch (Exception e) {

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @Test
    public void testNpsUpdate() throws TException {
        HrNpsUpdate update = new HrNpsUpdate();
        update.setUser_id(82689);
        update.setIntention(Byte.valueOf("8"));
        HrNpsResult result = service.npsUpdate(update);
        System.out.println(BeanUtils.convertStructToJSON(result));
    }

//    @Test
    public void testNpsUpdate2() throws TException {
        HrNpsUpdate update = new HrNpsUpdate();
        update.setUser_id(82689);
        update.setUsername("testUserName");
        update.setMobile("testMobile");
        update.setCompany("testCompany");
        HrNpsResult result = service.npsUpdate(update);
        System.out.println(BeanUtils.convertStructToJSON(result));
    }

//    @Test
    public void testNpsList() throws TException {
        HrNpsStatistic result = service.npsList(null, null, 0, 0);
        System.out.println(BeanUtils.convertStructToJSON(result));
    }
}
