package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictIndustry;
import com.moseeker.baseorm.db.dictdb.tables.DictPosition;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.baseorm.db.profiledb.tables.*;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionDO;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author xxx
 *         ProfileIntentionDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class ProfileIntentionDao extends JooqCrudImpl<ProfileIntentionDO, ProfileIntentionRecord> {

    public ProfileIntentionDao() {
        super(ProfileIntention.PROFILE_INTENTION, ProfileIntentionDO.class);
    }

    public ProfileIntentionDao(TableImpl<ProfileIntentionRecord> table, Class<ProfileIntentionDO> profileIntentionDOClass) {
        super(table, profileIntentionDOClass);
    }

    public int updateProfileUpdateTime(HashSet<Integer> intentionIds) {
        int status = 0;

        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        status = create.update(ProfileProfile.PROFILE_PROFILE)
                .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                .where(ProfileProfile.PROFILE_PROFILE.ID
                        .in(create.select(ProfileIntention.PROFILE_INTENTION.PROFILE_ID)
                                .from(ProfileIntention.PROFILE_INTENTION)
                                .where(ProfileIntention.PROFILE_INTENTION.ID.in(intentionIds))))
                .execute();

        return status;
    }

    public int delIntentionsByProfileId(int profileId) {
        int count = 0;
        Result<Record1<Integer>> intentionIds = create.select(ProfileIntention.PROFILE_INTENTION.ID).from(ProfileIntention.PROFILE_INTENTION).where(
                ProfileIntention.PROFILE_INTENTION.PROFILE_ID.equal((int) (profileId))).fetch();

        create.deleteFrom(ProfileIntentionCity.PROFILE_INTENTION_CITY)
                .where(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID
                        .in(intentionIds))
                .execute();

        create.deleteFrom(ProfileIntentionPosition.PROFILE_INTENTION_POSITION)
                .where(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID
                        .in(intentionIds))
                .execute();

        create.deleteFrom(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY)
                .where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID
                        .in(intentionIds))
                .execute();

        count = create.deleteFrom(ProfileIntention.PROFILE_INTENTION).where(
                ProfileIntention.PROFILE_INTENTION.PROFILE_ID.equal(profileId))
                .execute();
        return count;
    }

    public int postIntentions(List<IntentionRecord> intentionRecords) {
        if (intentionRecords != null && intentionRecords.size() > 0) {

            ProfileCompletenessRecord completenessRecord = create.selectFrom(ProfileCompleteness.PROFILE_COMPLETENESS).where(ProfileCompleteness.PROFILE_COMPLETENESS.PROFILE_ID.equal(intentionRecords.get(0).getProfileId())).fetchOne();
            if (completenessRecord == null) {
                completenessRecord = new ProfileCompletenessRecord();
                completenessRecord.setProfileId(intentionRecords.get(0).getProfileId());
            }

            Result<DictCityRecord> cities = create.selectFrom(DictCity.DICT_CITY).fetch();
            Result<DictPositionRecord> positions = create.selectFrom(DictPosition.DICT_POSITION).fetch();
            Result<DictIndustryRecord> industries = create.selectFrom(DictIndustry.DICT_INDUSTRY).fetch();

            List<ProfileIntentionCityRecord> intentionCityRecords = new ArrayList<>();
            List<ProfileIntentionPositionRecord> intentionPositionRecords = new ArrayList<>();
            intentionRecords.forEach(intentionRecord -> {
                intentionRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
                create.attach(intentionRecord);
                intentionRecord.insert();
                if (intentionRecord.getCities().size() > 0) {
                    intentionRecord.getCities().forEach(city -> {
                        city.setProfileIntentionId(intentionRecord.getId());
                        if (!StringUtils.isNullOrEmpty(city.getCityName())) {
                            for (DictCityRecord cityRecord : cities) {
                                if (city.getCityName().equals(cityRecord.getName())) {
                                    city.setCityCode(cityRecord.getCode());
                                    intentionCityRecords.add(city);
                                    break;
                                }
                            }
                        }
                        create.attach(city);
                        city.insert();
                    });
                }
                if (intentionRecord.getPositions().size() > 0) {
                    intentionRecord.getPositions().forEach(position -> {
                        position.setProfileIntentionId(intentionRecord.getId());
                        if (!StringUtils.isNullOrEmpty(position.getPositionName())) {
                            for (DictPositionRecord positionRecord : positions) {
                                if (positionRecord.getName().equals(position.getPositionName())) {
                                    position.setPositionCode(positionRecord.getCode());
                                    intentionPositionRecords.add(position);
                                    break;
                                }
                            }
                        }
                        create.attach(position);
                        position.insert();
                    });
                }
                if (intentionRecord.getIndustries().size() > 0) {
                    intentionRecord.getIndustries().forEach(industry -> {
                        industry.setProfileIntentionId(intentionRecord.getId());
                        if (!StringUtils.isNullOrEmpty(industry.getIndustryName())) {
                            for (DictIndustryRecord industryRecord : industries) {
                                if (industry.getIndustryName().equals(industryRecord.getName())) {
                                    industry.setIndustryCode(industryRecord.getCode());
                                    break;
                                }
                            }
                        }
                        create.attach(industry);
                        industry.insert();
                    });
                }
            });
            int intentionCompleteness = CompletenessCalculator.calculateIntentions(intentionRecords, intentionCityRecords, intentionPositionRecords);
            completenessRecord.setProfileIntention(intentionCompleteness);
        }
        return 0;
    }
}
