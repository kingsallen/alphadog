package com.moseeker.mall.service;

import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.mall.annotation.OnlyEmployee;
import com.moseeker.mall.constant.GoodsEnum;
import com.moseeker.mall.constant.MallSwitchState;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cjm
 * @date 2018-10-12 16:23
 **/
@Service
public class MallService {

    private Logger logger = LoggerFactory.getLogger(MallService.class);

    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;

    @Autowired
    private GoodsService goodsService;

    @OnlyEmployee
    public int getMallSwitch(int companyId) throws BIZException {
        HrCompanyConfDO hrCompanyConfDO = hrCompanyConfDao.getHrCompanyConfByCompanyId(companyId);
        if(hrCompanyConfDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }
        return hrCompanyConfDO.getMallSwitch();
    }

    public void openOrCloseMall(int companyId, int state) throws BIZException {
        int row = hrCompanyConfDao.updateMallSwitch(companyId, state);
        if(row == 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }
        if(state == MallSwitchState.NEVER_OPEN.getState()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_STATE_UNSUPPORTED);
        }
        if(state == MallSwitchState.CLOSED.getState()){
            int rows = goodsService.updateGoodStateByCompanyId(companyId, GoodsEnum.DOWNSHELF.getState());
            logger.info("===============共下架商品num:{}件", rows);
        }

    }

    public String getDefaultRule(int companyId) throws BIZException {
        HrCompanyConfDO hrCompanyConfDO = hrCompanyConfDao.getHrCompanyConfByCompanyId(companyId);
        if(hrCompanyConfDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }
        return hrCompanyConfDO.getMallGoodsMethod();
    }

    public void updateDefaultRule(int companyId, int state, String rule) throws BIZException {
        int row = hrCompanyConfDao.updateMallDefaultRule(companyId, state, rule);
        if(row == 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }
    }
}
