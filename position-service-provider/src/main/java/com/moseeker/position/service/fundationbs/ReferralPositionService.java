package com.moseeker.position.service.fundationbs;

import com.moseeker.baseorm.db.referraldb.tables.daos.ReferralCompanyConfJooqDao;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.entity.PositionEntity;
import com.moseeker.thrift.gen.common.struct.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */
@Service
public class ReferralPositionService {

    @Autowired
    PositionEntity positionEntity;


    @Autowired
    ReferralCompanyConfJooqDao referralCompanyConfJooqDao;


    @CounterIface
    @Transactional
    public void putReferralPositions(List<Integer> pids){
        positionEntity.putReferralPositions(pids);
    }
    @CounterIface
    @Transactional
    public void delReferralPositions(List<Integer> pids)  {
        positionEntity.delReferralPositions(pids);
    }

    @CounterIface
    @Transactional
    public void updatePointsConfig(Integer companyId,Integer flag)  {

        ReferralCompanyConf referralCompanyConf = referralCompanyConfJooqDao.findByCompnayId(companyId);
        if(referralCompanyConf != null) {
            referralCompanyConf.setPositionPointsFlag(flag.byteValue());
            referralCompanyConfJooqDao.update(referralCompanyConf);
        }else {
            ReferralCompanyConf referralCompanyConf1  = new ReferralCompanyConf();
            referralCompanyConf1.setPositionPointsFlag(flag.byteValue());
            referralCompanyConf1.setCompanyId(companyId);
            referralCompanyConfJooqDao.insert(referralCompanyConf1);
        }


    }

    @CounterIface
    @Transactional
    public Response getPointsConfig(Integer companyId) {

        ReferralCompanyConf referralCompanyConf = referralCompanyConfJooqDao.findByCompnayId(companyId);

        if(referralCompanyConf != null) {
            return ResponseUtils.success(referralCompanyConf);

        } else {

            ReferralCompanyConf newReferralCompanyConf  = new ReferralCompanyConf();
            newReferralCompanyConf.setPositionPointsFlag((byte)0);
            return ResponseUtils.success(newReferralCompanyConf);
        }

    }
}
