package com.moseeker.profile.service;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;

import java.nio.ByteBuffer;

/**
 * 简历接口
 * Created by jack on 07/09/2017.
 */
public interface ProfileService {

    /**
     * 添加或者更新简历。
     * 如果用户还没有简历信息，那么添加；否则更新简历信息。
     * 参数中的简历数据都是驼峰命名方式
     * @param userId 用户编号
     * @param profileParameter 简历数据
     * @return 简历编号
     * @throws CommonException 业务异常
     */
     int upsertProfile(int userId, String profileParameter) throws CommonException;

    /**
     * 手机确认提交简历
     * @param id 用户id（内推为employeeid）
     * @param fileName 文件名
     * @param fileData 文件
     * @return  ProfileDocParseResult
     * @throws ProfileException 业务异常
     */
    @Deprecated
    ProfileDocParseResult parseFileProfile(int id, String fileName, ByteBuffer fileData) throws ProfileException;

    /**
     * 手机确认提交简历
     * @param id 用户id（内推为employeeid）
     * @param name 用户名
     * @param mobile 手机
     * @return  简历id
     * @throws ProfileException 业务异常
     */
     int updateUserProfile(int id, String name, String mobile);


    ProfileDocParseResult parseHunterFileProfile(int headhunterId, String fileName, ByteBuffer fileData);
}
