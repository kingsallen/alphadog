package com.moseeker.position.thrift;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.position.service.third.ThirdPartyAccountInfoService;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
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
    public ThirdPartyAccountInfo getAllInfo(ThirdPartyAccountInfoParam param) throws TException {
        try {
            ThirdPartyAccountInfo info=service.getAllInfo(param);
            return info;
        }catch (TException e){
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new TException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION,e);
        }

    }

    @Override
    public List<ThirdPartyAccountInfoCity> getCity(ThirdPartyAccountInfoParam param) throws TException {
        try{
            HrThirdPartyAccountHrDO hrAccountDoHr=service.getThirdPartyAccount(param);
            return service.getInfoCity(hrAccountDoHr.getThirdPartyAccountId());
        }catch (TException e){
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new TException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION,e);
        }
    }

    @Override
    public List<ThirdPartyAccountInfoAddress> getCompanyAddress(ThirdPartyAccountInfoParam param) throws TException {
        try{
            HrThirdPartyAccountHrDO hrAccountDoHr=service.getThirdPartyAccount(param);
            return service.getInfoCompanyAddress(hrAccountDoHr.getThirdPartyAccountId());
        }catch (TException e){
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new TException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION,e);
        }
    }

    @Override
    public List<ThirdPartyAccountInfoCompany> getCompany(ThirdPartyAccountInfoParam param) throws TException {
        try{
            HrThirdPartyAccountHrDO hrAccountDoHr=service.getThirdPartyAccount(param);
            return service.getInfoCompany(hrAccountDoHr.getThirdPartyAccountId());
        }catch (TException e){
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new TException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION,e);
        }
    }

    @Override
    public List<ThirdPartyAccountInfoDepartment> getDepartment(ThirdPartyAccountInfoParam param) throws TException {
        try{
            HrThirdPartyAccountHrDO hrAccountDoHr=service.getThirdPartyAccount(param);
            return service.getInfoDepartment(hrAccountDoHr.getThirdPartyAccountId());
        }catch (TException e){
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new TException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION,e);
        }
    }


}
