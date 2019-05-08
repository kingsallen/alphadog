package com.moseeker.profile.service;

import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.vo.UploadFilesResult;
import com.moseeker.thrift.gen.common.struct.BIZException;

import java.nio.ByteBuffer;
import java.util.List;

public interface UploadFilesService {

    /**
     * 人员上传文件
     * @param fileName 上传文件名称
     * @param fileData 文件二进制流
     * @return
     */
    UploadFilesResult uploadFiles(String fileName, ByteBuffer fileData) throws ProfileException;



    /**
     * 下载上传过的文件
     * @param fileId 文件唯一id
     * @return 文件保存地址
     */
    String downLoadFiles(String fileId);

    /**
     * 插入上传文件记录
     * @param uploadFilesResult
     * @return
     */
    UploadFilesResult insertUpFiles(UploadFilesResult uploadFilesResult);

    /**
     * 返回上传文件保存内容详细数据
     *
     * @param fileId
     * @return
     */
    UploadFilesResult resumeInfo(String fileId);


    /**
     * 分页查看上传文件列表
     * @param unionid 上传人id
     * @param pageSize
     * @param pageNo
     * @return
     * @throws BIZException
     */
    List<UploadFilesResult> getUploadFiles(String unionid, Integer pageSize, Integer pageNo)throws BIZException;

    /**
     * 解析上传文件保存内容详细数据
     *
     * @param sceneId
     * @return
     */
    /*UploadFilesResult profile(String sceneId);*/

    /**
     * 检查指定内推简历的操作是否完成
     * @param employeeId 员工编号
     * @return true 完成 false 未完成
     * @throws ProfileException 业务异常
     */
    boolean getSpecifyProfileResult(int employeeId,String syncId) throws ProfileException;
    /*boolean getSpecifyProfileResult(int employeeId) throws ProfileException;*/

    /**
     * 返回解析对应参数
     * @param employeeId
     * @return
     * @throws ProfileException
     */
    UploadFilesResult checkResult(int employeeId) throws  ProfileException;

    /**
     * 跟据用户id和场景值存放redis的key和value
     * @param userId
     * @param sceneId
     * @return redi是存储结果
     */
    String setRedisKey(String userId, String sceneId);
}
