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
    public int upsertProfile(int userId, String profileParameter) throws CommonException;

    /**
     * 员工上传简历文件
     * @param employeeId 员工编号
     * @param fileName 文件名称
     * @param fileData 文件二进制刘
     * @return 解析结果
     * @throws ProfileException 业务异常
     */
    ProfileDocParseResult parseFileProfile(int employeeId, String fileName, ByteBuffer fileData) throws ProfileException;
}
