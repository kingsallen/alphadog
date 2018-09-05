package com.moseeker.position.service.fundationbs;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.entity.PositionEntity;
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
}
