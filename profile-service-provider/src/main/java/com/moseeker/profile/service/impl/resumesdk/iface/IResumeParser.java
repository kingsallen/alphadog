package com.moseeker.profile.service.impl.resumesdk.iface;

import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.thrift.gen.common.struct.BIZException;

import java.util.List;

public interface IResumeParser {
    /**
     * 解析ResumeSDK简历内容，对应到ProfileObj对象
     * @param moseekerProfile 映射完成后，返回给前端的简历对象，也就是仟寻的简历
     * @param resumeProfile ResumeSDK简历内容
     * @return  ProfileObj对象，也就是仟寻的简历
     * @throws BIZException
     * @throws Exception
     */
    ProfileObj parseResume(ProfileObj moseekerProfile,ResumeObj resumeProfile);

    /**
     * 获取解析过程中的异常信息
     * @return 异常信息集合
     */
    List<ResumeParseException> getExceptions();
}
