package com.moseeker.profile.service;

import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.vo.CandidateInfo;
import com.moseeker.profile.service.impl.vo.MobotReferralResultVO;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import com.moseeker.profile.service.impl.vo.UploadFilesResult;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.profile.struct.MobotReferralResult;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

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
     * 员工
     * @param filePath
     * @param userId
     * @return
     * @throws ProfileException
     */
    ProfileDocParseResult parseFileProfileByFilePath(String filePath, int userId) throws ProfileException;
    /**
     * 员工上传简历
     * @param employeeId 员工编辑
     * @param fileOriginName 原始文件名称
     * @param fileName 文件名称
     * @param fileAbsoluteName 绝对路径
     * @param fileData 文件base64数据
     * @return 解析结果
     * @throws ProfileException 业务异常
     */
    ProfileDocParseResult parseFileStreamProfile(int employeeId, String fileOriginName, String fileName,
                                                 String fileAbsoluteName, String fileData) throws ProfileException;

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
    int employeeReferralProfile(int employeeId, String name, String mobile, List<String> referralReasons, int position,
                                byte relationship, String referralText,  byte referralType)throws ProfileException, BIZException;

    /**
     * 员工推荐简历(多职位推荐)
     * @param employeeId 员工编号
     * @param name 推荐者名称
     * @param mobile 手机号码
     * @param referralReasons 推荐理由
     * @param positions 职位id集合
     * @param referralType 推荐方式 1 手机端上传 2 电脑端上传 3 推荐关键信息
     * @return 推荐记录编号
     * @throws ProfileException 业务异常
     */
    List<MobotReferralResultVO> employeeReferralProfile(int employeeId, String name, String mobile, List<String> referralReasons, List<Integer> positions,
                                                      byte relationship, String referralText, byte referralType)throws ProfileException, BIZException;

    /**
     * 员工提交候选人关键信息
     * @param employeeId 员工编号
     * @param candidate 候选人信息
     * @return 推荐记录编号
     */
    int postCandidateInfo(int employeeId, CandidateInfo candidate) throws ProfileException, BIZException;

    /**
     * 删除上传的简历数据
     * @param employeeId 员工编号
     * @throws ProfileException 异常信息
     */
    void employeeDeleteReferralProfile(int employeeId) throws ProfileException;

    /**
     * 员工推荐简历，mobot上传简历使用，走内推的员工推荐逻辑
     * @param employeeId 员工编号
     * @param ids 推荐职位ids
     * @return 推荐结果
     * @author  cjm
     * @date  2018/10/29
     */
    Map<String, String> saveMobotReferralProfile(int employeeId, List<Integer> ids) throws BIZException, InterruptedException;

    /**
     * 将推荐理由员工信息等存在redis中，生成虚拟用户并返回虚拟用户id
     * @param employeeId 员工编号
     * @param mobile 被推荐人手机号
     * @param name 被推荐人姓名
     * @param referralReasons 推荐理由
     * @param referralType 推荐类型
     * @param fileName 文件名字
     * @author  cjm
     * @date  2018/10/31
     * @return  虚拟用户或真实用户id
     */
    int saveMobotReferralProfileCache(int employeeId, String name, String mobile, List<String> referralReasons,
                                      byte referralType, String fileName, int relationship, String referralReasonText) throws BIZException;

    /**
     * 点击告诉ta时回填推荐信息，从缓存中取
     * @param employeeId 员工编号
     * @author  cjm
     * @date  2018/10/31
     * @return  缓存的推荐信息
     */
    String getMobotReferralCache(int employeeId) throws BIZException;
}
