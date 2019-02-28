package com.moseeker.position.thrift;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.pojo.ReferralPositionMatchInfo;
import com.moseeker.position.service.fundationbs.ReferralPositionService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.ReferralPositionServices;
import com.moseeker.thrift.gen.position.struct.ReferralPositionBonusVO;
import com.moseeker.thrift.gen.position.struct.ReferralPositionMatchDO;
import com.moseeker.thrift.gen.position.struct.ReferralPositionUpdateDataDO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */

@Service
public class ReferralPositionServiceImpl implements ReferralPositionServices.Iface {

    @Autowired
    ReferralPositionService referralPositionService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void putReferralPositions(ReferralPositionUpdateDataDO dataDO) throws TException {
        try {
            referralPositionService.putReferralPositions(dataDO);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void delReferralPositions(ReferralPositionUpdateDataDO dataDO) throws TException {
        try {
            referralPositionService.delReferralPositions(dataDO);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void updatePointsConfig(int companyId, int flag) throws TException {
        referralPositionService.updatePointsConfig(companyId,flag);
    }

    @Override
    public Response getPointsConfig(int companyId) throws TException {
        return referralPositionService.getPointsConfig(companyId);
    }

    @Override
    public Response putReferralPositionBonus(ReferralPositionBonusVO referralPositionBonusVO) throws TException {
        try {
            referralPositionService.putReferralPositionBonus(referralPositionBonusVO);

            return ResponseUtils.success(new JSONObject());

        }catch (Exception e) {
            logger.error(e.getClass().getName(),e);
            return ResponseUtils
                    .fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

        }
    }

    @Override
    public ReferralPositionBonusVO getReferralPositionBonus(int positionId) throws TException {
        ReferralPositionBonusVO vo =  referralPositionService.getReferralPositionBonus(positionId);
        return vo;
    }

    @Override
    public List<ReferralPositionMatchDO> getMatchPositionInfo(int userId, int companyId) throws BIZException, TException {
        List<ReferralPositionMatchInfo> matchList = referralPositionService.fetchPositionMatchByUserId(companyId, userId);
        List<ReferralPositionMatchDO> result = new ArrayList<>();
        if(!StringUtils.isEmptyList(matchList)){
            result = matchList.stream().map(m -> {
                ReferralPositionMatchDO match = new ReferralPositionMatchDO();
                BeanUtils.copyProperties(m, match);
                return match;
            }).collect(Collectors.toList());
        }
        return result;
    }


}
