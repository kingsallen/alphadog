package com.moseeker.user.service;

import com.moseeker.user.thrift.service.UserService;
import com.moseeker.user.thrift.struct.User;
import org.apache.thrift.TException;

/**
 * Created by zzh on 16/3/10.
 */
public class UserServiceImpl implements UserService.Iface{

    @Override
    public User getById(int id) throws TException {
        User u = new User();
        u.setId(1);
        u.setEmail("zhaozhonghua@126.com");
        u.setUsername("zhaozhonghua");

        return u;
    }
}
