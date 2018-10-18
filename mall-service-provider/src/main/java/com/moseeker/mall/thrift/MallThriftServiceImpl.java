package com.moseeker.mall.thrift;

import com.moseeker.mall.service.MallService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.mall.service.MallService.Iface;
import com.moseeker.thrift.gen.mall.struct.BaseMallForm;
import com.moseeker.thrift.gen.mall.struct.MallRuleForm;
import com.moseeker.thrift.gen.mall.struct.MallSwitchForm;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cjm
 * @date 2018-10-12 16:21
 **/
@Service
public class MallThriftServiceImpl implements Iface{

    @Autowired
    private MallService mallService;

    @Override
    public int getMallSwitch(BaseMallForm baseMallForm) throws BIZException, TException {
        try {
            return mallService.getMallSwitch(baseMallForm);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void openOrCloseMall(MallSwitchForm mallSwitchForm) throws BIZException, TException {
        try {
            mallService.openOrCloseMall(mallSwitchForm);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String getDefaultRule(BaseMallForm baseMallForm) throws BIZException, TException {
        try {
            return mallService.getDefaultRule(baseMallForm);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void updateDefaultRule(MallRuleForm mallRuleForm) throws BIZException, TException {
        try {
            mallService.updateDefaultRule(mallRuleForm);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
}
