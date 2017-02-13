package com.moseeker.candidate.service.values;

/**
 * 用户表映射关系
 * Created by jack on 10/02/2017.
 */
public class User {

    /** 表属性*/
    private int ID;
    private String username; 
    private String password; 
    private boolean enable;
    private int rank; 
    private String registerTime;
    private String registerIP;
    private String lastLoginTime;
    private String lastLoginIP;
    private int loginCount;
    private int mobile; 
    private String email; 
    private boolean activation; 
    private String activationCode;
    private String token; 
    private String name; 
    private String headimg; 
    private int nationalCodeID;
    private int wechatID;
    private String unionid; 
    private short source; 
    private String company; 
    private String position; 
    private int parentid; 
    private String nickname; 
    private byte emailverified;
    private String updateTime;

    private volatile boolean persist;       //是否持久化
    private volatile boolean updatedFlag;       //是否更新

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

    private void updateUpdteFlag(boolean updatedFlag) {
        this.updatedFlag = updatedFlag;
    }

    //private void dtoToUser()
}
