package com.moseeker.position.service.third;

import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyAddressDao;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoAddress;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdPartyAccountAddressService {
    private static Logger logger= LoggerFactory.getLogger(ThirdPartyAccountAddressService.class);
    @Autowired
    ThirdpartyAccountCompanyAddressDao addressDao;

    //把ThirdpartyAccountCompanyAddressDO转换成传给前台的类型ThirdPartyAccountInfoAddress
    public List<ThirdPartyAccountInfoAddress> getCompanyAddressByAccountId(int accountId)throws TException {
        List<ThirdpartyAccountCompanyAddressDO> addressList=addressDao.getAddressByAccountId(accountId);

        List<ThirdPartyAccountInfoAddress> infoAddressList=new ArrayList<>();

        addressList.forEach(a->{
            ThirdPartyAccountInfoAddress address=new ThirdPartyAccountInfoAddress();
            address.setId(a.getId());
            address.setCity(a.getCity());
            address.setName(a.getAddress());
            infoAddressList.add(address);
        });
        logger.info("infoAddressList:{}",infoAddressList);
        return infoAddressList;
    }

}
