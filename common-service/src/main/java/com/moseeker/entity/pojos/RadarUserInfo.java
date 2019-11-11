package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionChainRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.biz.RadarUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 人脉连连看用户信息vo
 */
public class RadarUserInfo implements Comparable<RadarUserInfo>{

    private Integer uid;
    private String name;
    private String nickname;
    private String avatar;
    private Integer degree;
    private List<Integer> pnodes;

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

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public List<Integer> getPnodes() {
        return pnodes;
    }

    public void setPnodes(List<Integer> pnodes) {
        this.pnodes = pnodes;
    }

    public RadarUserInfo initFromUserWxUser(Object userWxUser){
        String avatar;
        String nickname;
        int uid;
        if(userWxUser instanceof UserWxUserRecord){
            UserWxUserRecord userWxUserRecord = (UserWxUserRecord)userWxUser;
            avatar = Optional.ofNullable(userWxUserRecord.getHeadimgurl()).orElse("");
            nickname = userWxUserRecord.getNickname();
            uid = userWxUserRecord.getSysuserId();
        }else if(userWxUser instanceof UserWxUserDO){
            UserWxUserDO userWxUserDO = (UserWxUserDO)userWxUser;
            avatar = Optional.ofNullable(userWxUserDO.getHeadimgurl()).orElse("");
            nickname = userWxUserDO.getNickname();
            uid = userWxUserDO.getSysuserId();
        }else {
            return null;
        }
        this.avatar = avatar;
        this.nickname = nickname;
        this.uid = uid;
        return this;
    }

    public RadarUserInfo initFromUserWxUser(Object userWxUser, UserUserRecord userRecord){
        initFromUserWxUser(userWxUser);
//        if(!StringUtils.isNullOrEmpty(userRecord.getName())){
//            this.nickname = userRecord.getName();
//        }
        return this;
    }

    public RadarUserInfo initFromUserUser(UserUserRecord userUserRecord){
        String avatar;
        String nickname;
        int uid;
        avatar = userUserRecord.getHeadimg();
        nickname = userUserRecord.getNickname();
        uid = userUserRecord.getId();
        this.avatar = avatar;
        this.nickname = nickname;
        this.uid = uid;
        return this;
    }

    /**
     *
     * @param userDO 需要初始化的用户
     * @param userUserRecord
     * @param chainRecords 人脉连连看记录
     * @return 返回人脉雷达该用户数据
     */
    public RadarUserInfo initFromChainsRecord(UserWxUserDO userDO, UserUserRecord userUserRecord, List<ReferralConnectionChainRecord> chainRecords) {
        // 获取连连看最长路径，用于定位度数，这里会对记录排序
        List<ReferralConnectionChainRecord> newChainRecords = RadarUtils.getOrderedChainRecords(chainRecords);
        this.setUid(userDO.getSysuserId());
        this.setNickname(userUserRecord.getNickname());
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

    public RadarUserInfo fillNodesFromChainsRecord(UserWxUserDO userDO, List<ReferralConnectionChainRecord> chainRecords) {
        List<Integer> pnodes = new ArrayList<>();
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getNextUserId() == userDO.getSysuserId() && chainRecord.getState() == 1){
                pnodes.add(chainRecord.getRecomUserId());
            }
        }
        this.setPnodes(pnodes);
        return this;
    }


    @Override
    public int compareTo(@NotNull RadarUserInfo compare) {
        if (this.degree.intValue() != compare.getDegree().intValue()) {
            return this.degree > compare.getDegree() ? 1 : -1;
        } else {
           return -1;
        }
    }
}
