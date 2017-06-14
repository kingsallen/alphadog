package com.moseeker.demo.crud;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import org.springframework.stereotype.Service;

/**
 * Created by moseeker on 2017/3/28.
 */
@Service
public class TestUserDao extends JooqCrudImpl<User, UserUserRecord> {

    public TestUserDao() {
        super(UserUser.USER_USER, User.class);
    }
}
