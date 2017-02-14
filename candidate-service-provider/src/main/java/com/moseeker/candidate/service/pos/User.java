package com.moseeker.candidate.service.pos;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.UserDBDao;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.thrift.TException;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 用户表映射关系
 * Created by jack on 10/02/2017.
 */
public class User extends Model {

    UserDBDao.Iface userDao = ServiceManager.SERVICEMANAGER
            .getService(UserDBDao.Iface.class);

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
    private Date updateTime;

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

    @Override
    public void loadFromDB() {
        this.persist = true;
        QueryUtil query = new QueryUtil();
        query.addEqualFilter("id", String.valueOf(this.ID));
        try {
            UserUserDO userDTO = userDao.getUser(query);
            if(userDTO != null && userDTO.getId() > 0) {
                this.exist = true;
                try {
                    BeanUtils.copyProperties(this, userDTO);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                this.exist = false;
            }
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateToDB() {

    }

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public UserDBDao.Iface getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDBDao.Iface userDao) {
        this.userDao = userDao;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterIP() {
        return registerIP;
    }

    public void setRegisterIP(String registerIP) {
        this.registerIP = registerIP;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivation() {
        return activation;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getNationalCodeID() {
        return nationalCodeID;
    }

    public void setNationalCodeID(int nationalCodeID) {
        this.nationalCodeID = nationalCodeID;
    }

    public int getWechatID() {
        return wechatID;
    }

    public void setWechatID(int wechatID) {
        this.wechatID = wechatID;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public short getSource() {
        return source;
    }

    public void setSource(short source) {
        this.source = source;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public byte getEmailverified() {
        return emailverified;
    }

    public void setEmailverified(byte emailverified) {
        this.emailverified = emailverified;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
