package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BindCheckStep implements BindStep{
    @Autowired
    UserHrAccountDao hrAccountDao;

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    private ChannelType getChannel(int channel) throws BIZException {
        ChannelType channelType = ChannelType.instaceFromInteger(channel);
        if (channelType == null) {
            throw new BIZException(-1, "不支持的渠道类型：" + channel);
        }
        return channelType;
    }

    private UserHrAccountDO getUserHrAccount(int hrId) throws BIZException {
        UserHrAccountDO userHrAccount = hrAccountDao.getValidAccount(hrId);
        if (userHrAccount == null) {
            //没有找到该hr账号
            throw new BIZException(-1, "无效的HR帐号");
        }
        return userHrAccount;
    }

    private HrThirdPartyAccountDO get(UserHrAccountDO hrAccount, HrThirdPartyAccountDO thirdPartyAccount){
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("company_id", hrAccount.getCompanyId());
        qu.and("channel", thirdPartyAccount.getChannel());
        qu.and("username", thirdPartyAccount.getUsername());
        qu.and(new Condition("binding", 0, ValueOp.NEQ));//有效的状态
        List<HrThirdPartyAccountDO> datas = thirdPartyAccountDao.getDatas(qu.buildQuery());

        HrThirdPartyAccountDO data= datas.stream().filter(d->d.getUsername().equals(thirdPartyAccount.getUsername())).findFirst().get();

        if(data == null || data.getId()==0){    //即满足以上条件的第三方账号不存在

        }

        return data;
    }
}
