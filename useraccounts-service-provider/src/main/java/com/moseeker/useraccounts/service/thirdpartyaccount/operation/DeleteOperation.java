package com.moseeker.useraccounts.service.thirdpartyaccount.operation;

import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Update;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DeleteOperation {

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    HRThirdPartyAccountHrDao thirdPartyAccountHrDao;

    public int delete(HrThirdPartyAccountDO thirdPartyAccount,UserHrAccountDO hrAccount) throws BIZException {
        if(thirdPartyAccount.getCompanyId()!=hrAccount.getCompanyId()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DEL_NO_AUTHORIZED);
        }

        if(hrAccount.getAccountType() == HRAccountType.SubAccount.getType()){
            HrThirdPartyAccountHrDO relationship = thirdPartyAccountHrDao.getBinder(thirdPartyAccount.getId(),hrAccount.getId());
            if(relationship == null){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DEL_NO_AUTHORIZED);
            }
        }

        int accountId=thirdPartyAccount.getId();
        //解除账号绑定关系，删除第三方账号
        Update update = new Update.UpdateBuilder()
                .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                .buildUpdate();
        thirdPartyAccountHrDao.invalidByThirdPartyAccountId(accountId);
        thirdPartyAccount.setBinding((short) BindingStatus.UNBIND.getValue());
        //设置更新时间
        FastDateFormat sdf = FastDateFormat.getInstance(DateUtils.SHOT_TIME);
        thirdPartyAccount.setUpdateTime(sdf.format(new Date()));

        return thirdPartyAccountDao.updateData(thirdPartyAccount);
    }
}
