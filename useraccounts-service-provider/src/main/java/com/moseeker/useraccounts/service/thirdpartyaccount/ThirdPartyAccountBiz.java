package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by zhangdi on 2017/6/16.
 * <p>
 * 第三方账号逻辑处理
 */

@Service
@CounterIface
public class ThirdPartyAccountBiz {

    @Autowired
    HRThirdPartyAccountDao hrThirdPartyAccountDao;

    /**
     * 是否允许执行绑定
     */
    @CounterIface
    public int allowBind(UserHrAccount user, byte channelType, String username) {
        try {

            //主账号或者没有绑定第三方账号，检查公司下该渠道已经绑定过相同的第三方账号
            Query qu = new Query.QueryBuilder().where("company_id", user.getCompany_id())
                    .and("channel", String.valueOf(channelType))
                    .and("username", username)
                    .and(new Condition("binding", Arrays.asList(1, 2, 3), ValueOp.IN))//绑定中或者已经绑定,或者刷新中
                    .buildQuery();
            HrThirdPartyAccountDO data = hrThirdPartyAccountDao.getData(qu);

            //数据库中username是不区分大小写的，如果大小写不同，那么认为不是一个账号
            if (data != null && !username.equals(data.username)) {
                data = null;
            }

            if (data == null || data.getId() == 0) {
                //检查该用户是否绑定了其它相同渠道的账号
                ThirdPartAccountData thirdPartAccount = hrThirdPartyAccountDao.getThirdPartyAccountByUserId((int) user.getId(), channelType);
                if (thirdPartAccount != null && thirdPartAccount.getId() > 0) {
                    if (user.getAccount_type() == 0) {
                        //如果主账号已经绑定该渠道第三方账号，那么绑定人为空,并允许绑定
                        user.setId(0);
                        return ResponseUtils.success(null);
                    } else {
                        //已经绑定该渠道第三方账号，并且不是主账号，那么不允许绑定
                        return ResponseUtils.fail(ConstantErrorCodeMessage.HRACCOUNT_BINDING_LIMIT);
                    }
                } else {
                    return ResponseUtils.success(null);
                }
            } else {
                //公司下已经有人绑定了这个第三方账号，则这个公司谁都不能再绑定这个账号了
                if (data.getBinding() == 1) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.HRACCOUNT_ALREADY_BOUND);
                } else if (data.getBinding() == 2) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.HRACCOUNT_BINDING);
                } else {
                    return ResponseUtils.success(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
    }
}
