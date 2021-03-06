package com.moseeker.profile.domain.referral;

import com.moseeker.baseorm.dao.profiledb.entity.ProfileSaveResult;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.ReferralEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.profile.domain.EmployeeReferralProfileNotice;
import com.moseeker.profile.domain.ProfileAttementVO;
import com.moseeker.profile.service.impl.ProfileCompanyTagService;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by moseeker on 2018/11/22.
 */
@Component
public abstract class EmployeeReferralProfileApdate extends EmployeeReferralProfile {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeReferralProfileApdate.class);
    @Autowired
    private ProfileCompanyTagService companyTagService;

    @Autowired
    private UserAccountEntity userAccountEntity;

    @Autowired
    private ReferralEntity referralEntity;

    @Autowired
    private ProfileEntity profileEntity;

    protected abstract void validateReferralInfo(EmployeeReferralProfileNotice profileNotice);

    protected abstract ProfilePojo getProfilePojo(EmployeeReferralProfileNotice profileNotice);

    @Override
    public void storeReferralUser(UserUserRecord userRecord, EmployeeReferralProfileNotice profileNotice, ProfilePojo profilePojo,
                                  UserEmployeeDO employeeDO, ProfileAttementVO attementVO){
        int userId = 0;
        int attachementId = 0;
        logger.info("EmployeeReferralProfileApdate storeReferralUser userRecord:{}", userRecord);
        if (userRecord != null) {
            logger.info("recommend userRecord.id:{}", userRecord.getId());
            userId = userRecord.getId();
            profilePojo.setUserRecord(userRecord);
            logger.info("EmployeeReferralProfileApdate storeReferralUser userName:{}", userRecord.getUsername());
            if (StringUtils.isBlank(userRecord.getUsername())) {
                if (profilePojo.getProfileRecord() != null) {
                    profilePojo.getProfileRecord().setUserId(userRecord.getId());
                }
                ReferralLog logRecord = referralEntity.fetchReferralLog(profileNotice.getEmployeeId(), userId);
                int id = 0;
                if(logRecord != null){
                    id = logRecord.getAttementId();
                }
                attachementId = profileEntity.mergeProfileReferral(profilePojo, userId, id);
                logger.info("EmployeeReferralProfileApdate storeReferralUser attachementId:{}", attachementId);
                int temp= userId;
                tp.startTast(() -> {
                    companyTagService.handlerCompanyTagByUserId(temp);
                    return true;
                });
            }
        } else {
            ProfileSaveResult result = profileEntity.storeReferralUser(profilePojo, profileNotice.getEmployeeId(), employeeDO.getCompanyId(), profileNotice.getReferralScene());
            logger.info("EmployeeReferralProfileApdate storeReferralUser result:{}", result);
            if (result != null) {
                logger.info("EmployeeReferralProfileApdate storeReferralUser result.userId:{}, result.profileId:{}", result.getProfileRecord().getUserId(), result.getProfileRecord().getId());
                userId = result.getProfileRecord().getUserId();
                profilePojo.getProfileRecord().setUserId(result.getProfileRecord().getUserId());
                logger.info("EmployeeReferralProfileApdate storeReferralUser result.attachments:{}", result.getAttachmentRecords());
                if(result.getAttachmentRecords() != null && result.getAttachmentRecords().size() > 0) {
                    logger.info("EmployeeReferralProfileApdate storeReferralUser result.attachment:{}", result.getAttachmentRecords().get(0));
                    attachementId = result.getAttachmentRecords().get(0).getId();
                    logger.info("EmployeeReferralProfileApdate storeReferralUser attachmentId:{}", attachementId);
                }
                int temp= userId;
                tp.startTast(() -> {
                    companyTagService.handlerCompanyTagByUserId(temp);
                    return true;
                });
            }
        }
        attementVO.setUserId(userId);
        attementVO.setAttachmentId(attachementId);
    }

}
