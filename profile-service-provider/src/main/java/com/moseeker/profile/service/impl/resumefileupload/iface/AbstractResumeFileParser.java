package com.moseeker.profile.service.impl.resumefileupload.iface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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


    @Resource(name = "cacheClient")
    private RedisClient client;



    Logger logger = LoggerFactory.getLogger(AbstractResumeFileParser.class);

    protected abstract boolean checkUser(Integer id);

    protected abstract JSONObject getProfileObject(ProfileObj profileObj,FileNameData fileNameData,ProfileDocParseResult profileDocParseResult);

    protected abstract String getRedisKey();

    protected abstract String getHunterRedisKey();

    protected abstract void toPDF(String suffix, FileNameData fileNameData, Integer id);

    protected abstract ProfileDocParseResult setProfileDocParseResult(ProfileDocParseResult profileDocParseResult,User user,Integer userId);

    @Override
    public  ProfileDocParseResult parseResume(Integer id, String fileName, ByteBuffer fileData) {
        logger.info("AbstractResumeFileParser parseResume id:{}, fileName:{}", id, fileName);
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
        logger.info("AbstractResumeFileParser parseResult id:{}, fileName:{}, fileNameData:{}", id, fileName, JSON.toJSONString(fileNameData));
        ProfileDocParseResult profileDocParseResult = new ProfileDocParseResult();
        profileDocParseResult.setFile(fileNameData.getFileName());
        // 调用SDK得到结果
        ProfileObj profileObj;
        try {
            profileObj = profileEntity.parseProfile(0,fileName, fileData);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ProfileException.PROFILE_PARSE_TEXT_FAILED;
        }profileDocParseResult = setProfileDocParseResult(profileDocParseResult,profileObj.getUser(),id);
        profileObj.setResumeObj(null);
        JSONObject jsonObject = getProfileObject(profileObj,fileNameData,profileDocParseResult);
        ProfilePojo profilePojo = profileEntity.parseProfile(jsonObject.toJSONString());
        logger.info("AbstractResumeFileParser after parseProfile");
        client.set(AppId.APPID_ALPHADOG.getValue(), getRedisKey(), String.valueOf(id),
                "", profilePojo.toJson(), 24*60*60);
        logger.info("AbstractResumeFileParser after store redis");
        return profileDocParseResult;
    }

    public ProfileDocParseResult parseHunterResume(int headhunterId, String fileName, ByteBuffer fileData){
        logger.info("AbstractResumeFileParser parseResume id:{}, fileName:{}", headhunterId, fileName);
        ProfileDocParseResult profileDocParseResult = new ProfileDocParseResult();
        if (!ProfileDocCheckTool.checkFileName(fileName)) {
            throw ProfileException.REFERRAL_FILE_TYPE_NOT_SUPPORT;
        }
        byte[] dataArray = StreamUtils.ByteBufferToByteArray(fileData);
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        FileNameData fileNameData = StreamUtils.persistFile(dataArray, env.getProperty("profile.persist.url"), suffix);
        fileNameData.setOriginName(fileName);
        toPDF(suffix, fileNameData, headhunterId);
        profileDocParseResult.setFile(fileNameData.getFileName());
        return parseHunterResult(headhunterId, fileName, StreamUtils.byteArrayToBase64String(dataArray), fileNameData);
    }

    private ProfileDocParseResult parseHunterResult(int headhunterId, String fileName, String fileData,
                                              FileNameData fileNameData) throws ProfileException {
        logger.info("AbstractResumeFileParser parseResult headhunterId:{}, fileName:{}, fileNameData:{}", headhunterId, fileName, JSON.toJSONString(fileNameData));
        ProfileDocParseResult profileDocParseResult = new ProfileDocParseResult();
        profileDocParseResult.setFile(fileNameData.getFileName());
        // 调用SDK得到结果
        /*ResumeObj resumeObj;
        try {
            resumeObj = profileEntity.profileParserAdaptor(fileName, fileData);
        } catch (TException | IOException e) {
            logger.error(e.getMessage(), e);
            throw ProfileException.PROFILE_PARSE_TEXT_FAILED;
        }*/
//        logger.info("AbstractResumeFileParser after profileParserAdaptor");
//        ProfileObj profileObj = resumeEntity.handlerParseData(resumeObj,0,fileName);
        logger.info("AbstractResumeFileParser after handlerParseData");
//        profileDocParseResult = setProfileDocParseResult(profileDocParseResult,profileObj.getUser(),headhunterId);
//        profileObj.setResumeObj(null);
//        JSONObject jsonObject = getProfileObject(profileObj,fileNameData,profileDocParseResult);
        JSONObject jsonObject = new JSONObject();
        JSONArray attachments = new JSONArray();
        JSONObject attachment = new JSONObject();
        attachment.put("name", fileNameData.getOriginName());
        attachment.put("path", fileNameData.getFileAbsoluteName());
        attachments.add(attachment);
        jsonObject.put("attachments", attachments);
//        ProfilePojo profilePojo = profileEntity.parseProfile(jsonObject.toJSONString());
        profileDocParseResult.setName(jsonObject.toJSONString());
//        logger.info("AbstractResumeFileParser after parseProfile");
        /*client.set(AppId.APPID_ALPHADOG.getValue(), getHunterRedisKey(), String.valueOf(headhunterId),
                "", profilePojo.toJson(), 24*60*60);*/
//        logger.info("AbstractResumeFileParser after store redis");
        return profileDocParseResult;
    }
}
