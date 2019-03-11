package com.moseeker.position.thrift;

import com.moseeker.position.service.third.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.thirdpart.struct.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.moseeker.thrift.gen.thirdpart.service.ThirdPartyAccountInfoService.Iface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThirdPartyAccountInfoServiceImpl implements Iface {

    Logger logger= LoggerFactory.getLogger(ThirdPartyAccountInfoServiceImpl.class);

    @Autowired
    ThirdPartyAccountInfoService service;

    @Override
    public ThirdPartyAccountInfo getAllInfo(ThirdPartyAccountInfoParam param) throws BIZException {
        try {
            ThirdPartyAccountInfo info=service.getAllInfo(param);
            return info;
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new SysBIZException();
        }

    }

    @Override
    public int postThirdPartyCommonInfo(ThirdPartyCommonInfo param) throws BIZException, TException {
        try {
            int result=service.postThirdPartyCommonInfo(param);
            return result;
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new SysBIZException();
        }
    }

    @Override
    public List<ThirdPartyCommonInfo> getThirdPartyCommonInfo(ThirdPartyCommonInfo param) throws BIZException, TException {
        try {
            List<ThirdPartyCommonInfo> result=service.getThirdPartyCommonInfo(param);
            return result;
        }catch (BIZException e){
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new SysBIZException();
        }
    }

}
