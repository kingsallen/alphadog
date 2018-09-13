package com.moseeker.position.service.fundationbs;

import com.moseeker.baseorm.dao.referraldb.ReferralCompanyConfJooqDao;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.entity.PositionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public void putReferralPositions(List<Integer> pids, Boolean all_selected,Integer companyId){
        //如果all_selected=true,将该companyID下所有职位变成内推职位
        if(all_selected && companyId > 0) {
            pids  =  positionEntity.getPositionIdList(new ArrayList<>(companyId));
            if(pids.size()>500) {
                ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_REFERRAL_UPDATE_INVALID);
            }
        }
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
}
