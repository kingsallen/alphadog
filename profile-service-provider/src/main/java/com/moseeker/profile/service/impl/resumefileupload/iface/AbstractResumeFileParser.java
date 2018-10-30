package com.moseeker.profile.service.impl.resumefileupload.iface;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.UserSource;
import com.moseeker.commonservice.utils.ProfileDocCheckTool;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.User;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.domain.ResumeEntity;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.serviceutils.StreamUtils;
import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

@PropertySource("classpath:setting.properties")
public abstract class AbstractResumeFileParser implements resumeFileParser {

    @Autowired
    private Environment env;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    private ResumeEntity resumeEntity;

    @Autowired
    protected EmployeeEntity employeeEntity;

    @Autowired
    private UserAccountEntity userAccountEntity;

    @Resource(name = "cacheClient")
    private RedisClient client;

    Logger logger = LoggerFactory.getLogger(AbstractResumeFileParser.class);

    protected abstract Map<String, Object> checkUser(Integer id);

    protected abstract JSONObject getProfileObject(ProfileObj profileObj,FileNameData fileNameData,ProfileDocParseResult profileDocParseResult);

    protected abstract String getRedisKey();

    protected abstract ProfileDocParseResult setProfileDocParseResult(ProfileDocParseResult profileDocParseResult,User user,Integer userId);

    public  ProfileDocParseResult parseResume(Integer id, String fileName, ByteBuffer fileData, boolean isMobot) {
        ProfileDocParseResult profileDocParseResult = new ProfileDocParseResult();
        if (!ProfileDocCheckTool.checkFileName(fileName)) {
            throw ProfileException.REFERRAL_FILE_TYPE_NOT_SUPPORT;
        }
        Map<String, Object> map = checkUser(id);
        int companyId = getCompanyId(map);
        int userId = getUserId(map);
        byte[] dataArray = StreamUtils.ByteBufferToByteArray(fileData);
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        FileNameData fileNameData = StreamUtils.persistFile(dataArray, env.getProperty("profile.persist.url"), suffix);
        profileDocParseResult.setFile(fileNameData.getFileName());
        fileNameData.setOriginName(fileName);
        return parseResult(id, fileName, StreamUtils.byteArrayToBase64String(dataArray), fileNameData, companyId, userId, isMobot);

    }

    private int getUserId(Map<String, Object> map) {
        Object obj = map.get("UserEmployeeDO");
        if(obj == null){
            UserUserRecord record = (UserUserRecord)map.get("UserUserRecord");
            return record.getId();
        }else {
            UserEmployeeDO userEmployeeDO = (UserEmployeeDO)obj;
            return userEmployeeDO.getSysuserId();
        }
    }

    private int getCompanyId(Map<String, Object> map) {
        Object obj = map.get("UserEmployeeDO");
        if(obj == null){
            return 0;
        }else {
            UserEmployeeDO userEmployeeDO = (UserEmployeeDO)obj;
            return userEmployeeDO.getCompanyId();
        }
    }

    @Override
    public  ProfileDocParseResult parseResume(Integer id, String fileName, ByteBuffer fileData) {
        return parseResume(id, fileName, fileData, false);

    }
    private ProfileDocParseResult parseResult(int id, String fileName, String fileData, FileNameData fileNameData,
                                              int companyId, int userId, boolean isMobot) throws ProfileException {
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
        initUserIdAndCompanyId(profilePojo, profileDocParseResult, userId, companyId, isMobot);
        client.set(AppId.APPID_ALPHADOG.getValue(), getRedisKey(), String.valueOf(id),
                "", profilePojo.toJson(), 24*60*60);
        return profileDocParseResult;
    }

    /**
     * 将userId放到解析结果里，暂时用于存储虚拟用户的userid
     * @author  cjm
     * @date  2018/10/29
     */
    private void initUserIdAndCompanyId(ProfilePojo profilePojo, ProfileDocParseResult profileDocParseResult, int userId, int companyId, boolean isMobot) {
        UserUserRecord userRecord = userAccountEntity.getCompanyUser(
                profilePojo.getUserRecord().getMobile().toString(), companyId);
        if(isMobot){
            if(userRecord == null){
                userRecord = profileEntity.storeChatBotUser(profilePojo, userId, companyId, UserSource.EMPLOYEE_REFERRAL_CHATBOT, 0);
            }
            profileDocParseResult.setUserId(userRecord.getId());
        }
        profileDocParseResult.setCompanyId(companyId);
    }

}
