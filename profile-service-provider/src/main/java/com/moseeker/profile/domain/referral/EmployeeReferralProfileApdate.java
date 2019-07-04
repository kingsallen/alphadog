package com.moseeker.profile.domain.referral;

import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.ReferralEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.profile.domain.EmployeeReferralProfileNotice;
import com.moseeker.profile.domain.ProfileAttementVO;
import com.moseeker.profile.service.impl.ProfileCompanyTagService;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
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

    public void storeReferralUser(UserUserRecord userRecord, EmployeeReferralProfileNotice profileNotice, ProfilePojo profilePojo,
                                  UserEmployeeDO employeeDO, ProfileAttementVO attementVO){
        int userId = 0;
        int attachementId = 0;
        logger.info("EmployeeReferralProfileApdate storeReferralUser userRecord:{}", userRecord);
        if (userRecord != null) {
            logger.info("recommend userRecord.id:{}", userRecord.getId());
            UserUserRecord userUserRecord = new UserUserRecord();
            userUserRecord.setId(userRecord.getId());
            boolean flag = false;
            if (StringUtils.isBlank(userRecord.getName()) || !userRecord.getName().equals(profileNotice.getName())) {
                userRecord.setName(profileNotice.getName());
                userUserRecord.setName(profileNotice.getName());
                flag = true;
            }
            if (userRecord.getMobile() == null || userRecord.getMobile() == 0) {
                userRecord.setMobile(Long.valueOf(profileNotice.getMobile()));
                userUserRecord.setMobile(Long.valueOf(profileNotice.getMobile()));
                flag = true;
            }
            if (flag) {
                userAccountEntity.updateUserRecord(userUserRecord);
            }
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
            userRecord = profileEntity.storeReferralUser(profilePojo, profileNotice.getEmployeeId(), employeeDO.getCompanyId(), profileNotice.getReferralScene());
            profilePojo.getProfileRecord().setUserId(userRecord.getId());
            userId = userRecord.getId();
            ProfileProfileDO profileDO =profileEntity.getProfileByUserId(userId);
            logger.info("");
            ProfileAttachmentDO attachmentRecord = profileEntity.getProfileAttachmentByProfileId(profileDO.getId());
            if(attachmentRecord!=null) {
                attachementId = attachmentRecord.getId();
                logger.info("EmployeeReferralProfileApdate storeReferralUser attachementId:{}", attachementId);
            }
            int temp= userId;
            tp.startTast(() -> {
                companyTagService.handlerCompanyTagByUserId(temp);
                return true;
            });
        }
        attementVO.setUserId(userId);
        attementVO.setAttachmentId(attachementId);
    }

}
