package com.moseeker.profile.service.impl.resumefileupload.iface;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.OfficeUtils;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.User;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.domain.ResumeEntity;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.StreamUtils;
import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import java.io.File;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;

@PropertySource("classpath:setting.properties")
public abstract class AbstractResumeFileParser implements resumeFileParser {

    @Autowired
    private Environment env;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    private ResumeEntity resumeEntity;

    @Resource(name = "cacheClient")
    private RedisClient client;



    Logger logger = LoggerFactory.getLogger(AbstractResumeFileParser.class);

    protected abstract boolean checkUser(Integer id);

    protected abstract JSONObject getProfileObject(ProfileObj profileObj,FileNameData fileNameData,ProfileDocParseResult profileDocParseResult);

    protected abstract String getRedisKey();

    protected abstract void toPDF(String suffix, FileNameData fileNameData, Integer id);

    protected abstract ProfileDocParseResult setProfileDocParseResult(ProfileDocParseResult profileDocParseResult,User user,Integer userId);

    @Override
    public  ProfileDocParseResult parseResume(Integer id, String fileName, ByteBuffer fileData) {
        ProfileDocParseResult profileDocParseResult = new ProfileDocParseResult();
        if (!ProfileDocCheckTool.checkFileName(fileName)) {
            throw ProfileException.REFERRAL_FILE_TYPE_NOT_SUPPORT;
        }
        checkUser(id);
        byte[] dataArray = StreamUtils.ByteBufferToByteArray(fileData);
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        FileNameData fileNameData = StreamUtils.persistFile(dataArray, env.getProperty("profile.persist.url"), suffix);
        fileNameData.setOriginName(fileName);
        toPDF(suffix, fileNameData, id);
        profileDocParseResult.setFile(fileNameData.getFileName());
        return parseResult(id, fileName, StreamUtils.byteArrayToBase64String(dataArray), fileNameData);
    }
    private ProfileDocParseResult parseResult(int id, String fileName, String fileData,
                                              FileNameData fileNameData) throws ProfileException {
        ProfileDocParseResult profileDocParseResult = new ProfileDocParseResult();
        profileDocParseResult.setFile(fileNameData.getFileName());
        // 调用SDK得到结果
        ResumeObj resumeObj;
        try {
            resumeObj = profileEntity.profileParserAdaptor(fileName, fileData);
        } catch (TException | IOException e) {
            logger.error(e.getMessage(), e);
            throw ProfileException.PROFILE_PARSE_TEXT_FAILED;
        }
        ProfileObj profileObj = resumeEntity.handlerParseData(resumeObj,0,fileName);
        profileDocParseResult = setProfileDocParseResult(profileDocParseResult,profileObj.getUser(),id);
        profileObj.setResumeObj(null);
        JSONObject jsonObject = getProfileObject(profileObj,fileNameData,profileDocParseResult);
        ProfilePojo profilePojo = profileEntity.parseProfile(jsonObject.toJSONString());
        client.set(AppId.APPID_ALPHADOG.getValue(), getRedisKey(), String.valueOf(id),
                "", profilePojo.toJson(), 24*60*60);
        return profileDocParseResult;
    }

}
