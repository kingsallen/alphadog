package com.moseeker.useraccounts.service.thirdpartyaccount.util;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BindCheck {
    Logger logger= LoggerFactory.getLogger(BindCheck.class);

    @Autowired
    UserHrAccountDao hrAccountDao;

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    public static ChannelType checkChannel(int channel) throws BIZException {
        ChannelType channelType = ChannelType.instaceFromInteger(channel);
        if (channelType == null) {
            throw new BIZException(-1, "不支持的渠道类型：" + channel);
        }
        return channelType;
    }

    /**
     * 是否为主账号
     * @param hrAccount
     * @return
     */
    public static boolean isMainUserHrAccount(UserHrAccountDO hrAccount){
        if(hrAccount==null)
            throw new NullPointerException();
        if(hrAccount.getAccountType()==0){
            return true;
        }
        return false;
    }

    /**
     * 是否为子账号
     * @param hrAccount
     * @return
     */
    public static boolean isSubUserHrAccount(UserHrAccountDO hrAccount){
        if(hrAccount==null)
            throw new NullPointerException();
        if(hrAccount.getAccountType()!=0){
            return true;
        }
        return false;
    }

    public static boolean isNullAccount(HrThirdPartyAccountDO account){
        return account == null || account.getId() == 0;
    }

    public static boolean isNotNullAccount(HrThirdPartyAccountDO account){
        return account != null && account.getId() > 0;
    }
}
