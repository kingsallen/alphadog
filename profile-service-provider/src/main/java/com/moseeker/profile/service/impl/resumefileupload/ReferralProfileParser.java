package com.moseeker.profile.service.impl.resumefileupload;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.profiledb.ProfileAttachmentDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.profile.User;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.resumefileupload.iface.AbstractResumeFileParser;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.profile.service.impl.vo.FileNameData;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import com.moseeker.profile.utils.OfficeUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ReferralProfileParser extends AbstractResumeFileParser {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeEntity employeeEntity;

    @Autowired
    ProfileAttachmentDao attachmentDao;

    @Resource(name = "cacheClient")
    private RedisClient client;

    ThreadPool tp = ThreadPool.Instance;

    @Override
    protected boolean checkUser(Integer id) {

        logger.info("ReferralProfileParser checkUser id:{}", id);
        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(id);
        logger.info("ReferralProfileParser checkUser employeeDO:{}", employeeDO);
        if (employeeDO == null || employeeDO.getId() <= 0) {
            throw ProfileException.PROFILE_EMPLOYEE_NOT_EXIST;
        }
        return true;
    }

    @Override
    protected JSONObject getProfileObject(ProfileObj profileObj, FileNameData fileNameData, ProfileDocParseResult profileDocParseResult) {
        JSONObject jsonObject =  ProfileExtUtils.convertToReferralProfileJson(profileObj);
        ProfileExtUtils.createAttachment(jsonObject, fileNameData, Constant.USER_PARSE_PROFILE_DOCUMENT);
        ProfileExtUtils.createReferralUser(jsonObject, profileDocParseResult.getName(), profileDocParseResult.getMobile(),profileDocParseResult.getEmail());
        return jsonObject;
    }
    @Override
    protected String getRedisKey() {
        return KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString();
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
            int status = OfficeUtils.Word2Pdf(fileNameData.getFileAbsoluteName(),
                    fileNameData.getFileAbsoluteName().replace(fileNameData.getFileName(), pdfName));
            if(status == 1) {
                fileNameData.setFileAbsoluteName(fileNameData.getFileAbsoluteName().replace(fileNameData.getFileName(), pdfName));
                fileNameData.setFileName(pdfName);
                fileNameData.setOriginName(fileNameData.getOriginName().replace(".docx", Constant.WORD_PDF)
                        .replace(".doc", Constant.WORD_PDF));
            }
        }
    }

    @Override
    protected ProfileDocParseResult setProfileDocParseResult(ProfileDocParseResult profileDocParseResult, User user, Integer userId) {
        profileDocParseResult.setMobile(user.getMobile());
        profileDocParseResult.setName(user.getName());
        return profileDocParseResult;
    }


}
