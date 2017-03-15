package com.moseeker.baseorm.dao.user;

import com.moseeker.baseorm.db.userdb.tables.UserThirdpartyUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserThirdpartyUserRecord;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.ParamIllegalException;
import com.moseeker.common.exception.RecordNotExistException;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by eddie on 2017/3/7.
 */
@Service
public class ThirdPartyUserDao extends BaseDaoImpl<UserThirdpartyUserRecord, UserThirdpartyUser> {
    @Override
    protected void initJOOQEntity() {
        tableLike = UserThirdpartyUser.USER_THIRDPARTY_USER;
    }
}
