package com.moseeker.baseorm.dao.user;

import com.moseeker.baseorm.db.userdb.tables.UserThirdpartyUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserThirdpartyUserRecord;
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
public class ThirdPartyUserDao extends BaseDaoImpl<UserThirdpartyUserRecord,UserThirdpartyUser> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void initJOOQEntity() {
        tableLike = UserThirdpartyUser.USER_THIRDPARTY_USER;
    }

    public int putThirdPartyUser(ThirdPartyUser user){

        try {
            QueryUtil queryUtil = new QueryUtil();
            queryUtil.addEqualFilter("user_id", String.valueOf(user.getUser_id()));
            queryUtil.addEqualFilter("source_id", String.valueOf(user.getSource_id()));
            queryUtil.addEqualFilter("username", user.getUsername());

            List<UserThirdpartyUserRecord> thirdPartyUsers = getResources(queryUtil);
            if(thirdPartyUsers.size() > 0) {
                UserThirdpartyUserRecord record = BeanUtils.structToDB(user, UserThirdpartyUserRecord.class);
                int i = 0;
                for(UserThirdpartyUserRecord r : thirdPartyUsers) {
                    record.setId(r.getId());
                    Timestamp updateTime = new Timestamp(System.currentTimeMillis());
                    record.setUpdateTime(updateTime);
                    i += putResource(record);
                }
                return i;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return 0;
    }
}
