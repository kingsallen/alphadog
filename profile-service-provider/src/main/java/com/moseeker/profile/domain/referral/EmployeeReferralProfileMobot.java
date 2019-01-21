package com.moseeker.profile.domain.referral;

import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.profile.domain.EmployeeReferralProfileNotice;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by moseeker on 2018/11/22.
 */
@Component
public class EmployeeReferralProfileMobot extends EmployeeReferralProfile {

    @Autowired
    ProfileEntity profileEntity;


    @Override
    protected void validateReferralInfo(EmployeeReferralProfileNotice profileNotice) {
    }

    @Override
    protected ProfilePojo getProfilePojo(EmployeeReferralProfileNotice profileNotice) {
        return null;
    }

    @Override
    protected void storeReferralUser(UserUserRecord userRecord, EmployeeReferralProfileNotice profileNotice, ProfilePojo profilePojo,
                                     UserEmployeeDO employeeDO, Integer userId, Integer attachmentId) {
        userId = userRecord.getId();
        ProfileProfileDO profileDO = profileEntity.getProfileByUserId(userId);
        ProfileAttachmentDO attachmentRecord = profileEntity.getProfileAttachmentByProfileId(profileDO.getId());
        attachmentId = attachmentRecord.getId();
    }


}
