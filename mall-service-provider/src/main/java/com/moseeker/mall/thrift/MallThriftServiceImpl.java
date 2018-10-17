package com.moseeker.mall.thrift;

import com.moseeker.mall.service.MallService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.mall.service.MallService.Iface;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cjm
 * @date 2018-10-12 16:21
 **/
@Service
public class MallThriftServiceImpl implements Iface{

    private final MallService mallService;

    @Autowired
    public MallThriftServiceImpl(MallService mallService) {
        this.mallService = mallService;
    }

    @Override
    public int getMallSwitch(int companyId) throws BIZException, TException {
        try {
            return mallService.getMallSwitch(companyId);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void openOrCloseMall(int companyId, int state) throws BIZException, TException {
        try {
            mallService.openOrCloseMall(companyId, state);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String getDefaultRule(int companyId) throws BIZException, TException {
        try {
            return mallService.getDefaultRule(companyId);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void updateDefaultRule(int companyId, int state, String rule) throws BIZException, TException {
        try {
            mallService.updateDefaultRule(companyId, state, rule);
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
}
