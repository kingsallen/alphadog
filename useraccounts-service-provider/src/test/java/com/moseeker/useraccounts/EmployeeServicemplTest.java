package com.moseeker.useraccounts;

import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyReferralConfDO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.impl.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 用户服务 客户端测试类
 * <p>
 *
 * Created by zzh on 16/5/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class EmployeeServicemplTest {

    @Autowired
    private EmployeeService service;

    @Test
	public  void upsertCompanyReferralConfTest(){
        HrCompanyReferralConfDO confDO = new HrCompanyReferralConfDO();
        confDO.setCompanyId(39978);
        confDO.setLink("link");

	}
}