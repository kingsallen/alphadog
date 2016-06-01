package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import com.moseeker.useraccounts.dao.UserHrAccountDao;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * HR账号服务
 * <p>
 *
 * Created by zzh on 16/5/31.
 */
@Service
public class UserHrAccountServiceImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Override
    public Response postResource(UserHrAccount userHrAccount) throws TException {
        try {
            // 必填项验证
            Response response = validatePostResource(userHrAccount);
            if (response.status > 0){
                return response;
            }

            // 添加HR用户
            UserHrAccountRecord userHrAccountRecord = (UserHrAccountRecord) BeanUtils.structToDB(userHrAccount,
                    UserHrAccountRecord.class);

            int userHrAccountId = userHrAccountDao.postResource(userHrAccountRecord);
            if (userHrAccountId > 0) {
                Map<String, Object> hashmap = new HashMap<>();
                hashmap.put("userHrAccountId", userHrAccountId);
                return ResponseUtils.success(hashmap); // 返回 userFavoritePositionId
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postUserFavoritePosition UserFavPositionRecord error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Override
    public Response putResource(UserHrAccount userHrAccount) throws TException {
        return null;
    }

    /**
     * HR账号验证
     *
     * */
    private Response validatePostResource(UserHrAccount userHrAccount){

        Response response = new Response(0, "ok");
        if(userHrAccount == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        if(userHrAccount.mobile == null || userHrAccount.mobile.equals("")){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "mobile"));
        }
        return response;
    }
}
