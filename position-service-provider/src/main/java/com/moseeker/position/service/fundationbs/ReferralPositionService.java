package com.moseeker.position.service.fundationbs;

import com.moseeker.baseorm.dao.referraldb.ReferralCompanyConfJooqDao;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.entity.PositionEntity;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.ReferralPositionUpdateDataDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
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

    private ThreadPool tp = ThreadPool.Instance;


    @CounterIface
    @Transactional
    public void putReferralPositions(ReferralPositionUpdateDataDO dataDO){

        List<Integer> pids = this.positionIdsHandler(dataDO);

        if(!CollectionUtils.isEmpty(pids)) {

            positionEntity.putReferralPositions(pids);

        }
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
            referralCompanyConf.setUpdateTime(new Timestamp(System.currentTimeMillis()));
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


    public List<Integer> positionIdsHandler(ReferralPositionUpdateDataDO dataDO) {

        List<Integer> result = new ArrayList<>();
        int all_selected = dataDO.getAll_selected();
        int company_id = dataDO.getCompany_id();
        int account_id = dataDO.getAccount_id();
        int account_type = dataDO.getAccount_type();
        String city = dataDO.getCity();
        int candidate_source = dataDO.getCandidate_source();
        int employment_type = dataDO.getEmployment_type();
        String keyWord = dataDO.getKeyWord();

        List<Integer> pids = dataDO.getPids();
        //先判断all_selected
        if(all_selected == 0) {
            return pids;
        }

        if(all_selected == 1 ) {
            //全量新增 要根据筛选条件查询所有positionIds

        }

        //全取消
        if(all_selected == 2) {

            //再判断是否是全取消后 又选中某xie
        }

        //在判断positionIds


        return result;
    }
}
