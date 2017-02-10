package com.moseeker.candidate.service.values;

/**
 * 用户
 * Created by jack on 10/02/2017.
 */
public class User {

    private int ID;

    private volatile boolean persist;

    public User(int ID) {
        this.ID = ID;
    }

    public User getUserFromDB() {
        return this;
    }

    public static User getUserFromDB(int ID) {
        User user = new User(ID);
        user.updatePersist(true);
        return user;
    }

    private void updatePersist(boolean persist) {
        this.persist = persist;
    }

    private void dtoToUser()
}
