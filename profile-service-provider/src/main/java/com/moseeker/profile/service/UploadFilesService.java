package com.moseeker.profile.service;

import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.vo.UploadFilesResult;
import com.moseeker.thrift.gen.common.struct.BIZException;

import java.nio.ByteBuffer;
import java.util.List;

public interface UploadFilesService {

    /**
     * 人员上传文件
     * @param unionId 上传人ID
     * @param fileId 上传文件id
     * @param fileName 上传文件名称
     * @param fileData 文件二进制流
     * @return
     */
    UploadFilesResult uploadFiles(int unionId, int fileId, String fileName, ByteBuffer fileData) throws ProfileException;



    /**
     * 下载上传过的文件
     * @param sceneId 文件唯一id
     * @return 文件保存地址
     */
    String downLoadFiles(String sceneId);

    /**
     * 插入上传文件记录
     * @param uploadFilesResult
     * @return
     */
    Integer insertUpFiles(UploadFilesResult uploadFilesResult);

    /**
     * 返回上传文件保存内容详细数据
     *
     * @param sceneId
     * @return
     */
    UploadFilesResult resumeInfo(String sceneId);


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
    UploadFilesResult profile(String sceneId);

    /**
     * 检查指定内推简历的操作是否完成
     * @param employeeId 员工编号
     * @return true 完成 false 未完成
     * @throws ProfileException 业务异常
     */
    boolean getSpecifyProfileResult(int employeeId) throws ProfileException;
}
