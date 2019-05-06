package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralUploadFilesRecord;
import org.jooq.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.referraldb.Tables.REFERRAL_UPLOAD_FILES;
import static org.jooq.impl.DSL.using;

@Repository
public class ReferralUploadFilesDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralUploadFilesDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReferralUploadFilesDao(Configuration configuration){
        super(configuration);
    }

    public List<ReferralUploadFilesRecord> fetchByunionid(String unionid, int pagetNo, int pageSize){

        return   using(configuration())
                .selectFrom(REFERRAL_UPLOAD_FILES)
                .where(REFERRAL_UPLOAD_FILES.UNIONID.eq(unionid))
                .orderBy(REFERRAL_UPLOAD_FILES.CREATETIME.desc())
                .limit((pagetNo-1)*pageSize, pageSize)
                .fetchInto(ReferralUploadFilesRecord.class);

    }

    public ReferralUploadFilesRecord fetchByfileId(String id){

        return   using(configuration())
                .selectFrom(REFERRAL_UPLOAD_FILES)
                .where(REFERRAL_UPLOAD_FILES.ID.eq(Integer.parseInt(id)))
                .and(REFERRAL_UPLOAD_FILES.STATUS.eq(0))
                .fetchOne();

    }


    public ReferralUploadFilesRecord insertInto(ReferralUploadFilesRecord referralUploadFilesRecord){

        ReferralUploadFilesRecord referralUploadFilesRecord1 =
                 using(configuration())
                .insertInto(REFERRAL_UPLOAD_FILES)
                .columns(REFERRAL_UPLOAD_FILES.FILEID,REFERRAL_UPLOAD_FILES.UNINAME,
                        REFERRAL_UPLOAD_FILES.UNIONID,
                        REFERRAL_UPLOAD_FILES.TYPE,
                        REFERRAL_UPLOAD_FILES.FILENAME,
                        REFERRAL_UPLOAD_FILES.URL,
                        REFERRAL_UPLOAD_FILES.STATUS)
                 .values(referralUploadFilesRecord.getFileid(),
                         referralUploadFilesRecord.getUniname(),
                         referralUploadFilesRecord.getUnionid(),
                         referralUploadFilesRecord.getType(),
                         referralUploadFilesRecord.getFilename(),
                         referralUploadFilesRecord.getUrl(),
                         referralUploadFilesRecord.getStatus())
                 .returning()
                 .fetchOne();

        return referralUploadFilesRecord1;
    }


}
