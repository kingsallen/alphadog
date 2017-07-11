package com.moseeker.apps.service.profilebz;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * profile服务
 * Created by jack on 08/07/2017.
 */
@Component
public class profilebz {

    @Autowired
    ProfileUtils profileUtils;

    /**
     * 简历回收
     * todo 需要查看接口的执行时间，并预估该接口线上的并发数量
     * @param positionId 职位编号
     * @param profile 简历信息
     * @param channelType 渠道
     */
    public void retrieveProfile(int positionId, String profile, int channelType) throws Exception {
        ProfilePojo parseProfile = parseProfile(profile);
        handlerUserInfo(parseProfile.getUserRecord());
        handlerProfile(parseProfile);
        handlerApplication(positionId, parseProfile.getUserRecord().getId());
        notificationUser(parseProfile.getUserRecord().getId());
    }

    private ProfilePojo parseProfile(String profile) throws Exception {
        Map<String, Object> resume = null;
        resume = JSON.parseObject(profile);
        ProfileProfileRecord profileRecord = profileUtils
                .mapToProfileRecord((Map<String, Object>) resume.get("profile"));
        ProfilePojo profilePojo = ProfilePojo.parseProfile(resume);
        return profilePojo;
    }

    /**
     * 处理用户数据。
     * 如果不存在用户数据，则生成用户数据并返回用户信息；如果存在则直接返回用户信息
     * @param userUserRecord 简历中的用户信息
     * @return 用户信息
     */
    protected UserUserDO handlerUserInfo(UserUserRecord userUserRecord) {

        return null;
    }

    /**
     * 处理profile业务
     * 先做参数校验
     * 如果profile不存在，则添加profile
     * 如果profile存在，则更新profile
     * @param profile profile信息
     * @return
     */
    protected ProfileProfileDO handlerProfile(ProfilePojo profile) {
        return null;
    }

    /**
     * 处理申请
     * @param positionId 职位编号
     * @param userId 用户编号
     * @return
     */
    protected JobApplicationDO handlerApplication(int positionId, int userId) {
        return null;
    }

    /**
     * 将密码发给用户
     * @param userId
     */
    protected void notificationUser(int userId) {

    }
}
