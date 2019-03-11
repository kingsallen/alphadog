package com.moseeker.position.service.third;

import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyCommonInfoDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfo;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyCommonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ThirdPartyAccountInfoService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThirdpartyCommonInfoDao thirdpartyCommonInfoDao;

    @Autowired
    UserEmployeeDao employeeDao;

    Map<ChannelType, AbstractThirdInfoProvider> providers = new HashMap<>();

    @Autowired
    public ThirdPartyAccountInfoService(List<AbstractThirdInfoProvider> providers) {
        this.providers.putAll(providers.stream().collect(Collectors.toMap(p -> p.getChannel(), p -> p)));
    }


    public ThirdPartyAccountInfo getAllInfo(ThirdPartyAccountInfoParam param) throws Exception {
        logger.info("ThirdPartyAccountInfoParam:{}", param);
        ThirdPartyAccountInfo info = new ThirdPartyAccountInfo();

        ChannelType channel = ChannelType.instaceFromInteger(param.getChannel());

        if (channel == null) {
            logger.info("Wrong Channel:{} ", param.getChannel());
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "Wrong Channel");
        }

        if (!providers.containsKey(channel)) {
            logger.info("The Channel:{} does not has the InfoProvider", channel);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "The Channel does not has the InfoProvider");
        }

        AbstractThirdInfoProvider provider = providers.get(channel);
        if (provider == null) {
            logger.error("no third info provider");
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "no third info provider");
        }

        String json = provider.provide(param);

        info.setJson(json);

        logger.info("ThirdPartyInfo json result : " + json);
        return info;
    }

    public int postThirdPartyCommonInfo(ThirdPartyCommonInfo param) throws BIZException {
        if (param.getCompany_id() == 0 || param.getUser_id() == 0
                || StringUtils.isNullOrEmpty(param.getContent())
                || StringUtils.isNullOrEmpty(param.getType())) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        return thirdpartyCommonInfoDao.postThirdPartyCommonInfo(param);

    }

    public List<ThirdPartyCommonInfo> getThirdPartyCommonInfo(ThirdPartyCommonInfo param) throws BIZException {
        if (param.getCompany_id() == 0 && param.getUser_id() == 0) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        List<ThirdPartyCommonInfo> results = thirdpartyCommonInfoDao.getThirdPartyCommonInfo(param);

        if (param.getCompany_id() > 0) {
            List<UserEmployeeDO> employees = employeeDao.getEmployeeBycompanyId(param.getCompany_id());
            List<ThirdPartyCommonInfo> emailInfo = new ArrayList<>();
            out:for (ThirdPartyCommonInfo result : results) {
                for (UserEmployeeDO employee : employees) {
                    if (result.getUser_id() == employee.getSysuserId()){
                        ThirdPartyCommonInfo info = new ThirdPartyCommonInfo();
                        info.setUser_id(result.getUser_id());
                        info.setCompany_id(result.getCompany_id());
                        info.setContent("{\"email\":\""+employee.getEmail()+"\"}");
                        info.setType("email");
                        emailInfo.add(info);
                        continue out;
                    }
                }
            }

            results.addAll(emailInfo);
        }


        return results;

    }
}
