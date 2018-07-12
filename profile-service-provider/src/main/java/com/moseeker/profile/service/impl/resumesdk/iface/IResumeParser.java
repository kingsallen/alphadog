package com.moseeker.profile.service.impl.resumesdk.iface;

import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.thrift.gen.common.struct.BIZException;

public interface IResumeParser {
    /**
     * 解析ResumeSDK简历内容，对应到ProfileObj对象
     * @param moseekerProfile 映射完成后，返回给前端的简历对象，也就是仟寻的简历
     * @param resumeProfile ResumeSDK简历内容
     * @param uid 用户ID
     * @param fileName 传给ResumeSDK的简历文件名
     * @return  ProfileObj对象，也就是仟寻的简历
     * @throws BIZException
     * @throws Exception
     */
    ProfileObj parseResume(ProfileObj moseekerProfile,ResumeObj resumeProfile,int uid,String fileName);
}
