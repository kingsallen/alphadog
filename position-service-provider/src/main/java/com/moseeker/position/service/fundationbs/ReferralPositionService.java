package com.moseeker.position.service.fundationbs;

import com.moseeker.baseorm.dao.referraldb.ReferralCompanyConfJooqDao;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.entity.PositionEntity;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.ReferralPositionUpdateDataDO;
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
    public void putReferralPositions(ReferralPositionUpdateDataDO dataDO){
        //如果all_selected=true,将该companyID下所有职位变成内推职位
        int all_selected = dataDO.getAll_selected();
        int companyId = dataDO.getCompany_id();
        List<Integer> pids = dataDO.getPids();
//        if(all_selected && companyId > 0) {
//            pids  =  positionEntity.getPositionIdList(new ArrayList<>(companyId));
//            if(pids.size()>500) {
//                ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_REFERRAL_UPDATE_INVALID);
//            }
//        }
        positionEntity.putReferralPositions(pids);
    }
    @CounterIface
    @Transactional
    public void delReferralPositions(ReferralPositionUpdateDataDO dataDO)  {
        List<Integer> pids = dataDO.getPids();
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
