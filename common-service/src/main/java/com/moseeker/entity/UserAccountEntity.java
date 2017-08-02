package com.moseeker.entity;

import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service
public class UserAccountEntity {

    private static final Logger log = LoggerFactory.getLogger(UserAccountEntity.class);

    @Autowired
    private UserUserDao userDao;

    /**
     * 获取用户的称呼
     * @param userId 用户id
     * @return
     */
    public String genUsername(int userId) {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("id", String.valueOf(userId));
        UserUserDO user;
        String username = "";
        try {
            user = userDao.getData(qu.buildQuery());
            if(user != null && user.getUsername() != null) {
                if(StringUtils.isNotNullOrEmpty(user.getName())) {
                    username = user.getName();
                } else if(StringUtils.isNotNullOrEmpty(user.getNickname())) {
                    username = user.getNickname();
                } else {
                    username = user.getUsername();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return username;
    }
}
