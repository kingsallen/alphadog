package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictIndustry;
import com.moseeker.baseorm.db.dictdb.tables.DictPosition;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.baseorm.db.profiledb.tables.*;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionDO;
import org.jooq.Param;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import static org.jooq.impl.DSL.*;
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
        List<Integer> profileIdList = create.select(ProfileIntention.PROFILE_INTENTION.PROFILE_ID)
                .from(ProfileIntention.PROFILE_INTENTION)
                .where(ProfileIntention.PROFILE_INTENTION.ID.in(intentionIds))
                .stream()
                .map(integerRecord1 -> integerRecord1.value1())
                .collect(Collectors.toList());

        if (profileIdList != null && profileIdList.size() > 0) {
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            status = create.update(ProfileProfile.PROFILE_PROFILE)
                    .set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
                    .where(ProfileProfile.PROFILE_PROFILE.ID
                            .in(profileIdList))
                    .execute();
        }
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

//    private void insertProfileIntentionWhereNotExist(IntentionRecord intentionRecord, int retryTimes) throws BIZException {
//        if(retryTimes > 3){
//            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_EXHAUSTED);
//        }
//        Param<Integer> profileIdParam = param(ProfileIntention.PROFILE_INTENTION.PROFILE_ID.getName(), intentionRecord.getProfileId());
//        Param<Byte> workStateParam = param(ProfileIntention.PROFILE_INTENTION.WORKSTATE.getName(), intentionRecord.getWorkstate());
//        Param<Byte> workTypeParam = param(ProfileIntention.PROFILE_INTENTION.WORKTYPE.getName(), intentionRecord.getWorktype());
//        Param<Byte> considerParam = param(ProfileIntention.PROFILE_INTENTION.CONSIDER_VENTURE_COMPANY_OPPORTUNITIES.getName(), intentionRecord.getConsiderVentureCompanyOpportunities());
//        Param<Byte> salaryCodeParam = param(ProfileIntention.PROFILE_INTENTION.SALARY_CODE.getName(), intentionRecord.getSalaryCode());
//        Param<String> tagParam = param(ProfileIntention.PROFILE_INTENTION.TAG.getName(), intentionRecord.getTag());
//        ProfileIntentionRecord profileIntention = create.insertInto(
//                ProfileIntention.PROFILE_INTENTION,
//                ProfileIntention.PROFILE_INTENTION.PROFILE_ID,
//                ProfileIntention.PROFILE_INTENTION.WORKSTATE,
//                ProfileIntention.PROFILE_INTENTION.WORKTYPE,
//                ProfileIntention.PROFILE_INTENTION.CONSIDER_VENTURE_COMPANY_OPPORTUNITIES,
//                ProfileIntention.PROFILE_INTENTION.SALARY_CODE,
//                ProfileIntention.PROFILE_INTENTION.TAG
//        ).select(
//                select(
//                        profileIdParam,
//                        workStateParam,
//                        workTypeParam,
//                        considerParam,
//                        salaryCodeParam,
//                        tagParam
//                ).whereNotExists(
//                        selectOne()
//                                .from(ProfileIntention.PROFILE_INTENTION)
//                                .where(ProfileIntention.PROFILE_INTENTION.PROFILE_ID.eq(intentionRecord.getProfileId()))
//                )
//        ).returning().fetchOne();
//
//        if(profileIntention == null){
//            profileIntention = create.selectFrom(ProfileIntention.PROFILE_INTENTION).fetchOne();
//            if(profileIntention == null){
//                insertProfileIntentionWhereNotExist(intentionRecord, ++retryTimes);
//            }else {
//                mappingRecord(intentionRecord, profileIntention);
//                updateRecord(profileIntention);
//                intentionRecord.setId(profileIntention.getId);
//            }
//        }
//    }

    private void mappingRecord(IntentionRecord intentionRecord, ProfileIntentionRecord profileIntention) {
        profileIntention.setConsiderVentureCompanyOpportunities(intentionRecord.getConsiderVentureCompanyOpportunities());
        profileIntention.setSalaryCode(intentionRecord.getSalaryCode());
        profileIntention.setTag(intentionRecord.getTag());
        profileIntention.setWorkstate(intentionRecord.getWorkstate());
        profileIntention.setWorktype(intentionRecord.getWorktype());
    }

    public List<IntentionRecord> fetchByProfileId(Integer profileId) {
        if (profileId != null && profileId > 0) {
            Result<ProfileIntentionRecord> intentionRecordList = create
                    .selectFrom(ProfileIntention.PROFILE_INTENTION)
                    .where(ProfileIntention.PROFILE_INTENTION.PROFILE_ID.eq(profileId))
                    .fetch();
            if (intentionRecordList != null && intentionRecordList.size() > 0) {
                List<Integer> intentionIdList = intentionRecordList
                        .stream()
                        .map(ProfileIntentionRecord::getId)
                        .collect(Collectors.toList());
                List<ProfileIntentionCityRecord> cities = create
                        .selectFrom(ProfileIntentionCity.PROFILE_INTENTION_CITY)
                        .where(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.in(intentionIdList))
                        .fetch();
                List<ProfileIntentionPositionRecord> positions = create
                        .selectFrom(ProfileIntentionPosition.PROFILE_INTENTION_POSITION)
                        .where(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.in(intentionIdList))
                        .fetch();
                List<ProfileIntentionIndustryRecord> industries = create
                        .selectFrom(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY)
                        .where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.in(intentionIdList))
                        .fetch();
                return intentionRecordList
                        .stream()
                        .map(profileIntentionRecord -> {
                            IntentionRecord intentionRecord = new IntentionRecord();
                            BeanUtils.copyProperties(profileIntentionRecord, intentionRecord);
                            List<ProfileIntentionCityRecord> cityRecords = cities
                                    .stream()
                                    .filter(city -> city.getProfileIntentionId().equals(profileIntentionRecord.getId()))
                                    .collect(Collectors.toList());
                            intentionRecord.setCities(cityRecords);

                            List<ProfileIntentionPositionRecord> positionRecords = positions
                                    .stream()
                                    .filter(position -> position.getProfileIntentionId().equals(profileIntentionRecord.getId()))
                                    .collect(Collectors.toList());
                            intentionRecord.setPositions(positionRecords);

                            List<ProfileIntentionIndustryRecord> industryRecords = industries
                                    .stream()
                                    .filter(industry -> industry.getProfileIntentionId().equals(intentionRecord.getId()))
                                    .collect(Collectors.toList());
                            intentionRecord.setIndustries(industryRecords);

                            return intentionRecord;
                        })
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>(0);
    }
}
