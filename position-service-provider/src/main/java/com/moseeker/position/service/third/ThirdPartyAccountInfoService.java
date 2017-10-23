package com.moseeker.position.service.third;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCityDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyAddressDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountDepartmentDao;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyAccountDepartment;
import com.moseeker.thrift.gen.common.struct.BIZException;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdPartyAccountInfoService {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThirdpartyAccountCityDao cityDao;
    @Autowired
    ThirdpartyAccountCompanyDao companyDao;
    @Autowired
    ThirdpartyAccountCompanyAddressDao addressDao;
    @Autowired
    ThirdpartyAccountDepartmentDao departmentDao;
    @Autowired
    DictCityDao dictCityDao;


    @Autowired
    HRThirdPartyAccountHrDao hrThirdPartyAccountHrDao;

    public ThirdPartyAccountInfo getAllInfo(ThirdPartyAccountInfoParam param) throws TException {

        int accountId=getThirdPartyAccount(param).getThirdPartyAccountId();

        ThirdPartyAccountInfo info=new ThirdPartyAccountInfo();
        info.setCity(getInfoCity(accountId));
        info.setAddress(getInfoCompanyAddress(accountId));
        info.setCompany(getInfoCompany(accountId));
        info.setDepartment(getInfoDepartment(accountId));

        return info;
    }

    /**
     * 获取HR账号在某个渠道下的第三方账
     * @param param
     * @return
     * @throws TException 当没有获取HR账号在某个渠道下的第三方账号时，抛出异常
     */
    public HrThirdPartyAccountHrDO getThirdPartyAccount(ThirdPartyAccountInfoParam param) throws TException{
        long hrId=param.getHr_id();
        int channel=param.getChannel();
        logger.info("获取"+hrId+"HR在渠道"+channel+"下的第三方账号");
        HrThirdPartyAccountHrDO hrThirdPartyAccountHrDO=hrThirdPartyAccountHrDao.getData(hrId,channel);
        if(hrThirdPartyAccountHrDO == null || hrThirdPartyAccountHrDO.getId() == 0){
            throw new TException(param.getHr_id()+"账号在渠道"+param.getChannel()+"没有绑定的第三方账号");
        }
        logger.info("获取到的第三方账号为:"+hrThirdPartyAccountHrDO.getThirdPartyAccountId());
        return hrThirdPartyAccountHrDO;
    }


    /**
     * 把ThirdpartyAccountCityDO转换成传给前台的类型ThirdPartyAccountInfoCity
     * @param accountId 第三方账号ID
     * @return ThirdPartyAccountInfoCity列表
     * @throws TException
     */
    public List<ThirdPartyAccountInfoCity> getInfoCity(int accountId) throws TException{
        List<ThirdpartyAccountCityDO> cityList=getCity(accountId);

        //取出第三方账号对应城市的所有code
        List<Integer> codes=new ArrayList<>();
        cityList.forEach(c->codes.add(c.getCode()));

        //根据codes获取城市信息(名称)
        List<DictCityRecord> dictCityList=dictCityDao.getCitiesByCodes(codes);

        List<ThirdPartyAccountInfoCity> infoCity=new ArrayList<>();
        cityList.forEach(c->{
            ThirdPartyAccountInfoCity city=new ThirdPartyAccountInfoCity();
            city.setId(c.getId());
            city.setCode(c.getCode());
            city.setJobType(c.getJobtype());
            city.setRemainNum(c.getRemainNum());
            //遍历城市信息，获取对应code的城市名称
            String citName=dictCityList.stream().filter(dc->dc.getCode().equals(c.getCode())).findFirst().get().getName();
            city.setName(citName);
        });

        return infoCity;
    }
    public List<ThirdpartyAccountCityDO> getCity(int accountId) throws TException {
        List<ThirdpartyAccountCityDO> list=cityDao.getCityByAccountId(accountId);
        return list;
    }

    //把ThirdpartyAccountCompanyAddressDO转换成传给前台的类型ThirdPartyAccountInfoAddress
    public List<ThirdPartyAccountInfoAddress> getInfoCompanyAddress(int accountId)throws TException {
        List<ThirdpartyAccountCompanyAddressDO> addressList=getCompanyAddress(accountId);

        List<ThirdPartyAccountInfoAddress> infoAddressList=new ArrayList<>();

        addressList.forEach(a->{
            ThirdPartyAccountInfoAddress address=new ThirdPartyAccountInfoAddress();
            address.setId(a.getId());
            address.setCity(a.getCity());
            address.setName(a.getAddress());
            infoAddressList.add(address);
        });
        return infoAddressList;
    }
    public List<ThirdpartyAccountCompanyAddressDO> getCompanyAddress(int accountId) throws TException {
        List<ThirdpartyAccountCompanyAddressDO> list=addressDao.getAddressByAccountId(accountId);
        return list;
    }


    //把ThirdpartyAccountCompanyDO转换成传给前台的类型ThirdPartyAccountInfoCompany
    public List<ThirdPartyAccountInfoCompany> getInfoCompany(int accountId) throws TException{
        List<ThirdpartyAccountCompanyDO> companyList=getCompany(accountId);
        List<ThirdPartyAccountInfoCompany> infoCompanyList=new ArrayList<>();

        companyList.forEach(c-> {
            ThirdPartyAccountInfoCompany company=new ThirdPartyAccountInfoCompany();
            company.setId(c.getId());
            company.setName(c.getCompanyName());
            infoCompanyList.add(company);
        });

        return infoCompanyList;
    }
    public List<ThirdpartyAccountCompanyDO> getCompany(int accountId) throws TException {
        List<ThirdpartyAccountCompanyDO> list=companyDao.getCompanyByAccountId(accountId);
        return list;
    }


    //把ThirdpartyAccountDepartmentDO转换成传给前台的类型ThirdPartyAccountInfoDepartment
    public List<ThirdPartyAccountInfoDepartment> getInfoDepartment(int accountId) throws TException{
        List<ThirdpartyAccountDepartmentDO> departmentList=getDepartment(accountId);
        List<ThirdPartyAccountInfoDepartment> infoDepartmentList=new ArrayList<>();

        departmentList.forEach(d->{
            ThirdPartyAccountInfoDepartment department=new ThirdPartyAccountInfoDepartment();
            department.setId(d.getId());
            department.setName(d.getDepartmentName());
            infoDepartmentList.add(department);
        });

        return infoDepartmentList;
    }
    public List<ThirdpartyAccountDepartmentDO> getDepartment(int accountId) throws TException {
        List<ThirdpartyAccountDepartmentDO> list=departmentDao.getDepartmentByAccountId(accountId);
        return list;
    }
}
