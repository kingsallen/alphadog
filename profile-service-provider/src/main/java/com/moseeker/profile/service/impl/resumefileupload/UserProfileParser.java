package com.moseeker.profile.service.impl.resumefileupload;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.profile.utils.OfficeUtils;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.User;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.resumefileupload.iface.AbstractResumeFileParser;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@Component
public class UserProfileParser extends AbstractResumeFileParser {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserAccountEntity userAccountEntity;

    @Resource(name = "cacheClient")
    private RedisClient client;

    @Override
    public boolean checkUser(Integer id) {
        logger.info("UserProfileParser checkUser id:{}", id);
        UserUserRecord userRecord = userAccountEntity.getUserRecordbyId(id);
        logger.info("UserProfileParser checkUser id:{}", userRecord);
        if (userRecord == null || userRecord.getId() <= 0) {
            throw ProfileException.PROFILE_USER_NOTEXIST;
        }
        return true;
    }

    @Override
    protected JSONObject getProfileObject(ProfileObj profileObj, FileNameData fileNameData, ProfileDocParseResult profileDocParseResult) {
        JSONObject jsonObject =  ProfileExtUtils.convertToUserProfileJson(profileObj);
        ProfileExtUtils.createAttachment(jsonObject, fileNameData, Constant.EMPLOYEE_PARSE_PROFILE_DOCUMENT);
        ProfileExtUtils.createUserProfile(jsonObject, profileDocParseResult.getName(), profileDocParseResult.getMobile());
        return jsonObject;
    }

    @Override
    protected String getRedisKey() {
        return KeyIdentifier.WECHAT_UPLOAD_RESUME_FILE.toString();
    }


    @Override
    protected String getHunterRedisKey() {
        return KeyIdentifier.HEADHUNTER_REFERRAL_PROFILE.toString();
    }
    @Override
    protected void toPDF(String suffix, FileNameData fileNameData, Integer id) {
        if(Constant.WORD_DOC.equals(suffix) || Constant.WORD_DOCX.equals(suffix)) {
            String pdfName = fileNameData.getFileName().substring(0,fileNameData.getFileName().lastIndexOf("."))
                    + Constant.WORD_PDF;
            logger.info(".........FileName[" + fileNameData.getFileName() + "] --------------pdf file ");
            logger.info(".........pdfName[" + pdfName + " ]--------------pdf file ");
            int status = OfficeUtils.Word2Pdf(fileNameData.getFileAbsoluteName(),
                    fileNameData.getFileAbsoluteName().replace(fileNameData.getFileName(), pdfName));
            logger.info("........." + pdfName + " --------------pdf file ---> status = " + status);
//            if(status == 1) {
                fileNameData.setFileAbsoluteName(fileNameData.getFileAbsoluteName().replace(fileNameData.getFileName(), pdfName));
                fileNameData.setFileName(pdfName);
                fileNameData.setOriginName(fileNameData.getOriginName().replace(".docx", Constant.WORD_PDF)
                        .replace(".doc", Constant.WORD_PDF));
//            }
        }
    }

    @Override
    protected ProfileDocParseResult setProfileDocParseResult(ProfileDocParseResult profileDocParseResult, User user, Integer userId) {
        UserUserRecord userRecord = userAccountEntity.getUserRecordbyId(userId);
        if (userRecord == null || userRecord.getId() <= 0) {
            throw ProfileException.PROFILE_USER_NOTEXIST;
        }
        boolean validMobile = Pattern.compile("[0-9]*").matcher(userRecord.getUsername()).matches();
        if(userRecord.getUsername()!=null&&validMobile){
            profileDocParseResult.setMobile(userRecord.getUsername());
            profileDocParseResult.setMobileeditable(false);
        }else{
            profileDocParseResult.setMobile(user.getMobile());
        }
        profileDocParseResult.setName(user.getName());
        return profileDocParseResult;
    }

}
