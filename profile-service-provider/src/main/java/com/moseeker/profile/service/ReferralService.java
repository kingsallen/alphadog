package com.moseeker.profile.service;

import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.vo.CandidateInfo;
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
     * @param referralType 推荐方式 1 手机端上传 2 电脑端上传 3 推荐关键信息
     * @return 推荐记录编号
     * @throws ProfileException 业务异常
     */
    int employeeReferralProfile(int employeeId, String name, String mobile, List<String> referralReasons, int position, byte referralType)
            throws ProfileException;

    /**
     * 员工提交候选人关键信息
     * @param employeeId 员工编号
     * @param candidate 候选人信息
     * @return 推荐记录编号
     */
    int postCandidateInfo(int employeeId, CandidateInfo candidate) throws ProfileException;

    /**
     * 删除上传的简历数据
     * @param employeeId 员工编号
     * @throws ProfileException 异常信息
     */
    void employeeDeleteReferralProfile(int employeeId) throws ProfileException;
}
