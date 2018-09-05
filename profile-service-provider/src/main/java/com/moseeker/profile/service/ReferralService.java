package com.moseeker.profile.service;

import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/4
 */
public interface ReferralService {

    /**
     * 员工上传简历
     * @param employeeId 员工编号
     * @param fileName 文件名称
     * @param fileData 文件二进制刘
     * @return 解析结果
     * @throws ProfileException 业务异常
     */
    ProfileDocParseResult parseFileProfile(int employeeId, String fileName, ByteBuffer fileData) throws ProfileException;

    /**
     * 员工推荐简历
     * @param employeeId 员工编号
     * @param name 推荐者名称
     * @param mobile 手机号码
     * @param referralReasons 推荐理由
     * @param position 职位编号
     * @return 推荐记录编号
     * @throws ProfileException 业务异常
     */
    int employeeReferralProfile(int employeeId, String name, String mobile, List<String> referralReasons, int position)
            throws ProfileException;
}
