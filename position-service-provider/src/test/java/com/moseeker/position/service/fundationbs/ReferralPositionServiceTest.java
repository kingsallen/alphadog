package com.moseeker.position.service.fundationbs;

import com.google.common.collect.Lists;
import com.moseeker.position.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zztaiwll on 17/10/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ReferralPositionServiceTest {
    @Autowired
    private ReferralPositionService referralPositionService;

    @Test
    @Transactional
    @Rollback(false)
    public void putReferralPositions() throws  Exception{
        List<Integer> pids = Lists.newArrayList(1,2);
        Boolean all_selected = false;
        Integer company_id = 0;
        referralPositionService.putReferralPositions(pids,all_selected,company_id);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void delReferralPositions() throws  Exception{
        List<Integer> pids = Lists.newArrayList(1,2);
        referralPositionService.delReferralPositions(pids);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void updatePointsConf() throws  Exception {
        Integer companyId = 4;
        Integer flag =0   ;
        referralPositionService.updatePointsConfig(companyId,flag);
    }

}
