package com.moseeker.mall.service;

import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.mall.annotation.OnlySuperAccount;
import com.moseeker.mall.constant.GoodsEnum;
import com.moseeker.mall.constant.MallSwitchState;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.mall.struct.BaseMallForm;
import com.moseeker.thrift.gen.mall.struct.MallRuleForm;
import com.moseeker.thrift.gen.mall.struct.MallSwitchForm;
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

    public int getMallSwitch(BaseMallForm baseMallForm) throws BIZException {
        HrCompanyConfDO hrCompanyConfDO = hrCompanyConfDao.getHrCompanyConfByCompanyId(baseMallForm.getCompany_id());
        if(hrCompanyConfDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }
        return hrCompanyConfDO.getMallSwitch();
    }

    @OnlySuperAccount
    public void openOrCloseMall(MallSwitchForm mallSwitchForm) throws BIZException {
        int companyId = mallSwitchForm.getCompany_id();
        int state = mallSwitchForm.getState();
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

    @OnlySuperAccount
    public String getDefaultRule(BaseMallForm baseMallForm) throws BIZException {
        HrCompanyConfDO hrCompanyConfDO = hrCompanyConfDao.getHrCompanyConfByCompanyId(baseMallForm.getCompany_id());
        if(hrCompanyConfDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }

        return hrCompanyConfDO.getMallGoodsMethodState() == 0 ? "" : hrCompanyConfDO.getMallGoodsMethod();
    }

    @OnlySuperAccount
    public void updateDefaultRule(MallRuleForm mallRuleForm) throws BIZException {
        if(mallRuleForm.getState() == 0){
            // 如果是取消默认规则，将默认规则删除
            mallRuleForm.setRule("");
        }
        int row = hrCompanyConfDao.updateMallDefaultRule(mallRuleForm.getCompany_id(), mallRuleForm.getState(), mallRuleForm.getRule());
        if(row == 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }
    }
}
