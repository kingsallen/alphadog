package com.moseeker.profile.service.impl.resumefileupload.iface;

import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import com.moseeker.thrift.gen.common.struct.BIZException;

import java.nio.ByteBuffer;

public interface resumeFileParser {

    /**
     * 解析ResumeSDK简历内容，对应到ProfileObj对象
     * @param id 用户id（内推为employeeid）
     * @param fileName 文件名
     * @param fileData 简历文件内容
     * @return  ProfileDocParseResult对象
     * @throws BIZException
     * @throws Exception
     */
    ProfileDocParseResult parseResume(Integer id, String fileName, ByteBuffer fileData);
}
