package com.moseeker.useraccounts.service.impl.vo;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionChainRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 人脉连连看用户信息vo
 */
public class RadarUserInfo {

    private Integer uid;
    private String name;
    private String nickname;
    private String avatar;
    private Integer degree;
    private Integer order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public RadarUserInfo initFromUserWxUser(Object userWxUser){
        if(userWxUser instanceof UserWxUserRecord){
            UserWxUserRecord userWxUserRecord = (UserWxUserRecord)userWxUser;
            this.avatar = userWxUserRecord.getHeadimgurl();
            this.nickname = userWxUserRecord.getNickname();
            this.uid = userWxUserRecord.getSysuserId();
        }else if(userWxUser instanceof UserWxUserDO){
            UserWxUserDO userWxUserDO = (UserWxUserDO)userWxUser;
            this.avatar = userWxUserDO.getHeadimgurl();
            this.nickname = userWxUserDO.getNickname();
            this.uid = userWxUserDO.getSysuserId();
        }else {
            return null;
        }
        return this;
    }

    /**
     *
     * @param userDO 需要初始化的用户
     * @param chainRecords 人脉连连看记录
     * @return 返回人脉雷达该用户数据
     */
    public RadarUserInfo initFromChainsRecord(UserWxUserDO userDO, List<ReferralConnectionChainRecord> chainRecords) {
        // 获取连连看最长路径，用于定位度数，这里会对记录排序
        List<ReferralConnectionChainRecord> newChainRecords = getNewChainRecords(chainRecords);
        this.setUid(userDO.getSysuserId());
        this.setNickname(userDO.getNickname());
        this.setAvatar(userDO.getHeadimgurl());
        for(int i = 0; i < newChainRecords.size(); i++){
            ReferralConnectionChainRecord connectionChain = newChainRecords.get(i);
            if(connectionChain.getRecomUserId() == userDO.getSysuserId()){
                if(connectionChain.getParentId() == 0){
                    this.setDegree(0);
                    break;
                }else {
                    this.setDegree(i);
                    break;
                }
            }else if(connectionChain.getNextUserId() == userDO.getSysuserId()){
                this.setDegree(i+1);
                break;
            }
        }
        return this;
    }

    private List<ReferralConnectionChainRecord> getNewChainRecords(List<ReferralConnectionChainRecord> chainRecords) {
        List<ReferralConnectionChainRecord> newChainRecords = new ArrayList<>();
        int rootParentId = chainRecords.get(0).getRootParentId();
        Timestamp createTime = null;
        for(ReferralConnectionChainRecord connectionChainRecord : chainRecords){
            if(rootParentId == connectionChainRecord.getId()){
                createTime = connectionChainRecord.getCreateTime();
                break;
            }
        }
        for(ReferralConnectionChainRecord connectionChainRecord : chainRecords){
            if(connectionChainRecord.getCreateTime().equals(createTime)){
                newChainRecords.add(connectionChainRecord);
            }
        }
        // 这里保证链路按度数排序
        return getOrderedChainRecords(newChainRecords);
    }

    private List<ReferralConnectionChainRecord> getOrderedChainRecords(List<ReferralConnectionChainRecord> chainRecords) {
        List<ReferralConnectionChainRecord> orderedChainRecords = new ArrayList<>();
        int parentId = 0;
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getParentId() == 0){
                parentId = chainRecord.getId();
                orderedChainRecords.add(chainRecord);
                break;
            }
        }
        // 递归排序
        return findByParentId(parentId, orderedChainRecords, chainRecords);
    }

    private List<ReferralConnectionChainRecord> findByParentId(int parentId, List<ReferralConnectionChainRecord> orderedChainRecords, List<ReferralConnectionChainRecord> chainRecords) {
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getParentId() == parentId){
                orderedChainRecords.add(chainRecord);
                return findByParentId(chainRecord.getId(), orderedChainRecords, chainRecords);
            }
        }
        return orderedChainRecords;
    }

    /**
     * 填充order字段
     * @param   userDO  用户微信信息
     * @param   chainRecords 连连看已连接记录
     * @author  cjm
     * @date  2018/12/16
     * @return   当前链路用户信息
     */
    public RadarUserInfo fillOrderFromChainsRecord(UserWxUserDO userDO, List<ReferralConnectionChainRecord> chainRecords) {
        int parentId = 0;
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getNextUserId() == userDO.getSysuserId()){
                parentId = chainRecord.getParentId();
                break;
            }
        }
        Integer order = 0;
        order = getOrderByRecurrence(order, parentId, chainRecords);
        setOrder(order);
        return this;
    }

    /**
     * 递归排序 从0开始
     * @param order 当前顺序
     * @param parentId 当前parentId
     * @param chainRecords 已连接链路
     * @author  cjm
     * @date  2018/12/16
     * @return 排序后的序号
     */
    private Integer getOrderByRecurrence(Integer order, int parentId, List<ReferralConnectionChainRecord> chainRecords) {
        if(parentId == 0){
            return ++order;
        }
        for(ReferralConnectionChainRecord chainRecord : chainRecords) {
            if(chainRecord.getId() == parentId){
                return getOrderByRecurrence(++order, chainRecord.getParentId(), chainRecords);
            }
        }
        return order;
    }
}
